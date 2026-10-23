# heygen-service

Spring Boot (Java 17) service wrapping the HeyGen API for generating avatar videos and photos for starWithMe course content.

Extracted from [starWithMe](https://github.com/montanizstills/starWithMe) at commit 1818f38 (branch `dev`); history before the split lives there.

Requires `HEYGEN_API_KEY` in the environment.

Uses the HeyGen **v3** API (v1/v2 were retired 2026-11-01). Run `mvn test` for offline request-shape tests; tests that call HeyGen run only when `HEYGEN_API_KEY` is set.
