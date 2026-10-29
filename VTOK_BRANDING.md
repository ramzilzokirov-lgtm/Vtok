# VTok branding and upstream attribution

## VTok identity

- Project name: VTok.
- Accent target: muted gold (`#FFC857`) on graphite/dark surfaces.
- Do not use the MargyT name or icon as VTok's own app name or identity.
- Do not claim that the current Compose shell is a functional TikTok client.

## MargyT-derived builds

The patcher is sourced from [the original MargyT repository](https://git.narez.xyz/narezany/MargyT) and is licensed under GPL-3.0 with the additional terms in `ADDITIONAL-TERMS.md`.

Any derived build must:
- retain the MargyT author's visible, working legal-notice section in the mod settings at the required prominence;
- clearly state in that same section that the build is modified and is not released by the MargyT author;
- preserve applicable source and license obligations;
- avoid using MargyT's name or icon as VTok's own branding.

The current workflow produces a **VTok-based evaluation artifact** using the upstream patcher. It does not establish that the APK has been installed or tested on a device. Do not call a build complete until Actions passes and runtime testing has been performed.
