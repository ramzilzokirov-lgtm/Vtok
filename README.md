# VTok

VTok is an Android prototype for a TikTok-inspired client with custom interface ideas.

## Current status

This repository currently contains an initial Android Compose UI and a GitHub Actions debug-APK workflow. It is **not yet connected to TikTok**: the real feed, account login, likes, comments, follows, upload and profile sync are not implemented.

TikTok's production app source is proprietary. VTok will not pretend that a mock screen is a working TikTok client or bypass login, API access controls, or platform security. Integration must use documented/authorized TikTok interfaces or another permitted integration route.

## Build

Open Actions > VTok Android APK > Run workflow. The APK will be available as the VTok-debug-apk artifact after a successful run.

## Planned work

- Define a permitted TikTok integration route and supported features.
- Replace prototype feed with real authorized content where the API permits it.
- Add theme controls and accessibility settings.
- Add plugin and badge designs only after their implementation and security model are defined.
