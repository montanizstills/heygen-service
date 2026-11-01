#!/bin/zsh
# Saves your HeyGen API key to .env (gitignored, readable only by you), checks it with HeyGen, then clears the clipboard.
# Rerun it to rotate the key; other lines in .env are kept.
set -u
umask 077
cd "${0:A:h}/.."

read -rs "KEY?Paste your HeyGen API key with Cmd+V (hidden, no arrow keys), then Enter: "
echo
KEY=${KEY//[[:space:]]/}
if [[ -z "$KEY" ]]; then
  echo "No key entered; .env unchanged."
  exit 1
fi

touch .env
chmod 600 .env
grep -vE '^[[:space:]]*(export[[:space:]]+)?HEYGEN_API_KEY=' .env > .env.tmp || true
print -r -- "HEYGEN_API_KEY=$KEY" >> .env.tmp
mv .env.tmp .env
chmod 600 .env
echo "Saved to $(pwd)/.env (permissions 600, gitignored)."

STATUS=$(curl -s -o /dev/null -w "%{http_code}" -H "x-api-key: $KEY" https://api.heygen.com/v3/users/me)
unset KEY
case "$STATUS" in
  200) echo "HeyGen accepted the key." ;;
  401) echo "HeyGen rejected the key (401). Check it and rerun this script." ;;
  *)   echo "Could not confirm the key with HeyGen (HTTP $STATUS)." ;;
esac

printf '' | pbcopy && echo "Clipboard cleared."
