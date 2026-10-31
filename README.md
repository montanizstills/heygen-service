# heygen-service

Spring Boot (Java 17) service wrapping the HeyGen API for generating avatar videos and photos for starWithMe course content.

Extracted from [starWithMe](https://github.com/montanizstills/starWithMe) at commit 1818f38 (branch `dev`); history before the split lives there.

Requires `HEYGEN_API_KEY` in the environment.

Uses the HeyGen **v3** API (v1/v2 were retired 2026-11-01). Run `mvn test` for offline request-shape tests; tests that call HeyGen run only when `HEYGEN_API_KEY` is set.

## Postman tests

`postman/` holds a collection and a local environment. Import both into Postman, and put your key in the environment's `heygenApiKey` **current value** only, so it is never exported or committed. Or run everything from the terminal:

```bash
postman/run-tests.sh
```

It asks for the key (hidden), starts the service, runs the collection with Newman, and stops the service. HeyGen checks request bodies before credits, so the create requests pass on a 402 `insufficient_credit` too: the body was accepted.
