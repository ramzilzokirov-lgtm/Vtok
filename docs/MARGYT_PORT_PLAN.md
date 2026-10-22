# MargyT source assessment and VTok port plan

Status: source archive received and inspected; no MargyT code has been copied into this repository yet.

## Source examined

The supplied `MargyT-main.zip` contains a TikTok APK patching toolchain, injected Java classes, plugin loader, badge UI/data, streak reminders, a badge server, tests, and build scripts. Its build entry point is `build.sh`, which calls `python3 -m margyt` and requires a separately obtained compatible **universal arm64-v8a APK**. The project README says the mod replaces the official package and requires uninstalling the official app first.

The source's current `VERSION` is 2.1.0. The README discusses TikTok version 46.9.42; compatibility with a newer APK is not established by this archive alone.

## License gate — mandatory

The archive contains GPL-3.0 in `LICENSE` and project-specific additional terms in `ADDITIONAL-TERMS.md`. Those terms require, among other things:
- retaining the MargyT author/project/donation notices in the settings screen with equal prominence;
- clearly identifying the product as modified and not authored by the MargyT author;
- not using “Margy”, “MargyT”, or the MargyT icon as the derivative project's own name/identity;
- keeping a derivative work's source open under GPL-3.0 with these additional terms.

Before copying or adapting any source file, preserve these notices and license files and confirm the intended distribution complies with all applicable terms. The VTok name can remain, but attribution must not be hidden or reduced.

## Proposed port sequence

1. **Compatibility gate:** obtain the exact official universal APK intended for testing; record its version, ABI, package ID, SHA-256, and source. Do not use split APKs or bundles with this build script unless the tool is explicitly extended to support them.
2. **Baseline build:** run the MargyT test suite and patcher against that APK without custom feature changes. Save logs and output hashes. The source archive alone is not evidence that a target APK builds.
3. **Safe branding/attribution:** change the mod identity to VTok while preserving MargyT's required visible attribution and GPL terms. Keep the official TikTok package identity only if the legal and installation implications are accepted.
4. **Feature port in small commits:** themes/accent colors and settings UI first; then profile badges; then plugin manager and documentation; then update checking; then streak reminders; finally additional animation/profile customization. Keep each feature behind a tested switch where appropriate.
5. **Security checks:** plugins run inside TikTok's process without a sandbox, according to the supplied plugin documentation. Show an explicit warning before install, validate archive entries and size limits, and do not describe static inspection as a sandbox. Keep badge assignment authorization on the server; never trust an owner/admin flag supplied by the client.
6. **Build and smoke tests:** test install/launch, login, For You feed, video playback, profile, likes/comments/follows, upload if available, and update/install behavior on a test device. Record failures rather than presenting them as working.

## Current limitations

- The supplied ZIP is a source tree, not an original TikTok APK.
- TikTok's APK is proprietary and version-specific; this repo does not contain TikTok's source code.
- A rebuilt APK is signed with a different key from the official app, so it generally cannot update the official installation in place. The official app may need to be uninstalled first, risking local app data.
- Obfuscation and server-side feature flags can make individual patches version-sensitive. A successful compile does not prove login, feed, or account actions still work.
- This branch documents the audit/port plan only. It does not yet contain a modified TikTok APK or a completed source port.
