# VTok

VTok is an early-stage Android project exploring a customized TikTok experience using the open-source MargyT APK patcher.

## Current state

- The Compose app in `app/` is a UI prototype. It does **not** provide TikTok sign-in, feed, posting, likes, comments, or account sync.
- The `Build MargyT-based APK for VTok evaluation` workflow downloads a pinned TikTok 47.5.3 universal APK, verifies its SHA-256, runs MargyT, and uploads the result as a temporary GitHub Actions artifact.
- A successful patcher run is not proof of installation, account login, or runtime compatibility. Test the resulting APK on a spare device before relying on it.
- No TikTok APK is included in this repository. Only use an APK you are legally entitled to use and do not commit it to Git.

## Build a MargyT-patched APK

1. Open [the MargyT build workflow](https://github.com/ramzilzokirov-lgtm/VTok/actions/workflows/build-margyt.yml).
2. Select **Run workflow** and keep the default APK URL unless you have a different direct HTTPS URL for the exact same build.
3. Start the workflow and wait for it to finish. The workflow accepts only the APK whose SHA-256 is `6670ca3f93bfea379658cad3c27f05d3a380b23a0d82569cce684b7453e8ff3e`; other files fail closed rather than being patched by accident.
4. Download `vtok-margyt-based-evaluation` from the run's artifacts.
5. Check `build-report.txt` and both SHA-256 files. The artifact is retained for 14 days.

The workflow fetches the upstream MargyT source from its original repository, checks its license notices and Python syntax, runs the patcher with a muted gold accent, and verifies the resulting APK archive. It does not publish a GitHub Release or install the APK on a device. The default download endpoint is external; the SHA-256 pin is the integrity check that confirms it returned the intended TikTok 47.5.3 APK.

## Country / region selector from MargyT

The evaluation build runs the upstream MargyT patcher, which includes its in-app SIM/carrier/system-region selector. After a successful patch, look under TikTok's **Settings and privacy → MargyT** (the exact row label follows the upstream build) for the region settings. The selected region changes local region signals used by TikTok; it does not change the public IP address, the account's server-side registration/store region, or guarantee access to every regional feature. A VPN/proxy is a separate network setting. The patcher's compatibility checks must pass for the selected APK.

## UI work in the current prototype

The settings screen has a local country/region preference, a Nothing-inspired glyph toggle, and an OLED-shift preview. These settings are implemented in the standalone Compose prototype only: the region choice is stored locally and does **not** alter TikTok's SIM/carrier hooks, account region, or network IP. The OLED shift currently moves the prototype feed content by 1 dp every 45 seconds; it does not modify the separately patched TikTok UI. Do not treat these preview settings as proof that the corresponding MargyT hooks have been transferred to TikTok.

## Upstream attribution and license

This project uses and evaluates [MargyT](https://git.narez.xyz/narezany/MargyT), licensed under GPL-3.0 with additional terms in its `ADDITIONAL-TERMS.md`. Any redistributed modified version must comply with the GPL and those additional terms, including keeping the author's legal-notice section visible and working and clearly stating that the modified build is not from the MargyT author. VTok must not present the MargyT name or icon as its own branding.

The original TikTok app and its services are proprietary and are not part of this repository. VTok is not affiliated with TikTok or the MargyT author.
