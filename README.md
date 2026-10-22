# VTok

VTok is an Android project intended to customize the TikTok experience, not a replacement social network pretending to be TikTok.

## Reality check

The current app is an early Android Compose shell. It is **not an official TikTok APK, is not connected to a TikTok account, and does not yet provide the real For You feed, likes, comments, follows, upload, or account sync**. No successful APK build is claimed until GitHub Actions finishes successfully.

TikTok's production Android source code is proprietary. The repository cannot become a modified official TikTok client from an empty project alone. To work on a real existing client, the original APK/source must be supplied and assessed for what can be modified lawfully and technically. Without it, development can continue only through supported integrations and original code.

TikTok's official Display API can show an authorizing user's profile and public videos after the developer app is approved and the user grants the required scopes. It does not provide a general replacement for TikTok's native For You feed or all native interaction features. See [Display API overview](https://developers.tiktok.com/docs/en/display-api-overview) and [Get started](https://developers.tiktok.com/docs/en/display-api-get-started).

## Build and verify

GitHub Actions builds a debug APK on pushes to `main`, pull requests, and manual runs:

1. Open **Actions → VTok Android APK**.
2. Open the newest run and verify that **Compile and verify APK** is green.
3. Download the **VTok-debug-apk** artifact from that run.

The workflow installs Gradle 8.9 explicitly, validates the Gradle project, compiles the app and verifies that a non-empty APK exists before publishing the artifact. A green build proves only that this project compiles; it does not mean the official TikTok service integration is complete.

## Planned work

- Confirm the intended route: modify a user-provided original TikTok APK (if technically and legally feasible), or build an authorized companion/integration.
- Add real, consent-based TikTok authentication and Display API support if the developer app is approved and credentials are configured.
- Add configurable muted themes, animations, profile customization, badges and a documented plugin interface without falsely presenting unfinished features as functional.
- Add signed update delivery only after release signing, integrity checks and rollback behavior are defined.


## MargyT source audit

The supplied MargyT source archive has been reviewed. The compatibility and licensing gate, port sequence, and known limitations are documented in [docs/MARGYT_PORT_PLAN.md](docs/MARGYT_PORT_PLAN.md). This is an audit/plan commit, **not** a completed TikTok APK modification. The exact compatible original APK is still required before patching and smoke testing.
