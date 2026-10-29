# VTok

VTok is an early-stage Android project exploring a customized TikTok experience using the open-source MargyT APK patcher.

## Current state

- The Compose app in `app/` is a UI prototype. It does **not** provide TikTok sign-in, feed, posting, likes, comments, or account sync.
- The `Build MargyT-based APK for VTok evaluation` workflow patches a user-supplied compatible TikTok APK and uploads the result as a temporary GitHub Actions artifact.
- A successful patcher run is not proof of installation, account login, or runtime compatibility. Test the resulting APK on a spare device before relying on it.
- No TikTok APK is included in this repository. Only use an APK you are legally entitled to use and do not commit it to Git.

## Build a patcher artifact

1. Open **Actions → Build MargyT-based APK for VTok evaluation**.
2. Select **Run workflow**.
3. Enter a direct HTTPS URL for a compatible universal `arm64-v8a` APK (not an app bundle or split APK).
4. Wait for the workflow to finish and download `vtok-margyt-based-evaluation` from the run's artifacts.
5. Check `build-report.txt` and both SHA-256 files. The artifact is retained for 14 days.

The workflow fetches the upstream MargyT source from its original repository, checks its license notices and Python syntax, runs the patcher with a muted gold accent, and verifies the resulting APK archive. It does not publish a GitHub Release or install the APK on a device.

## Upstream attribution and license

This project uses and evaluates [MargyT](https://git.narez.xyz/narezany/MargyT), licensed under GPL-3.0 with additional terms in its `ADDITIONAL-TERMS.md`. Any redistributed modified version must comply with the GPL and those additional terms, including keeping the author's legal-notice section visible and working and clearly stating that the modified build is not from the MargyT author. VTok must not present the MargyT name or icon as its own branding.

The original TikTok app and its services are proprietary and are not part of this repository. VTok is not affiliated with TikTok or the MargyT author.
