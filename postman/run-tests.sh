#!/bin/zsh
# Runs the Postman collection with Newman: starts heygen-service, runs every request, then stops the app.
# The API key is read hidden and passed only through a temporary 0600 env file that is deleted on exit.
set -u
cd "${0:A:h}/.."

PORT=${PORT:-8080}
MVN=${MVN:-mvn}
command -v "$MVN" >/dev/null || MVN=~/.maven/maven-3.9.15/bin/mvn

if [[ -z "${HEYGEN_API_KEY:-}" ]]; then
  read -rs "HEYGEN_API_KEY?HeyGen API key (hidden, paste with Cmd+V): "
  echo
fi
export HEYGEN_API_KEY

ENV_FILE=$(mktemp -t heygen-env)
chmod 600 "$ENV_FILE"
APP_PID=""
cleanup() {
  rm -f "$ENV_FILE"
  [[ -n "$APP_PID" ]] && kill "$APP_PID" 2>/dev/null
  # spring-boot:run forks the app JVM, so also stop whatever is listening on the port
  lsof -ti "tcp:$PORT" -sTCP:LISTEN 2>/dev/null | xargs kill 2>/dev/null
  for i in {1..15}; do
    lsof -ti "tcp:$PORT" -sTCP:LISTEN >/dev/null 2>&1 || return 0
    sleep 1
  done
  echo "heygen-service is still listening on port $PORT"
  return 0
}
trap cleanup EXIT INT TERM

python3 -I - "$ENV_FILE" "$PORT" <<'EOF'
import json, os, sys
env = json.load(open("postman/local.postman_environment.json"))
for v in env["values"]:
    if v["key"] == "heygenApiKey":
        v["value"] = os.environ["HEYGEN_API_KEY"]
    if v["key"] == "serviceUrl":
        v["value"] = f"http://localhost:{sys.argv[2]}"
json.dump(env, open(sys.argv[1], "w"))
EOF

mkdir -p target
echo "Starting heygen-service on port $PORT (log: target/postman-app.log)..."
"$MVN" -q spring-boot:run -Dspring-boot.run.arguments=--server.port=$PORT > target/postman-app.log 2>&1 &
APP_PID=$!
for i in {1..90}; do
  curl -s -o /dev/null "http://localhost:$PORT" && break
  if ! kill -0 "$APP_PID" 2>/dev/null; then echo "App failed to start; see target/postman-app.log"; exit 1; fi
  sleep 1
done

npx -y newman@6 run postman/heygen-service.postman_collection.json -e "$ENV_FILE" --reporters cli
