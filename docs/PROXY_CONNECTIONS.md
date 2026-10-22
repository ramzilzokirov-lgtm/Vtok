# VTok connection proxy

This document describes the optional proxy settings module planned for VTok. A proxy is a transport setting; it does not remove TikTok account bans, regional account restrictions, or server-side enforcement.

## Supported modes

- SOCKS5
- HTTP CONNECT / HTTP proxy
- Disabled (default)

## Required UI

The settings page must expose enable/disable, protocol, host, port, optional username/password, a connection-test action, and a clear status/error message. Credentials must not be logged or sent to VTok's own servers.

## Security and implementation notes

- Do not ship a public proxy endpoint or hard-coded credentials.
- Validate host and port; use a timeout for connection tests.
- Store credentials only in Android Keystore-backed encrypted preferences.
- Never claim that a proxy is active unless the network stack used by the target requests actually uses it.
- Android's process-wide ProxySelector does not guarantee that every SDK/native socket in a modified third-party app honors the setting. Verify the exact networking stack before calling this feature functional.
- Prefer an explicitly configured proxy over silently intercepting traffic. Do not disable TLS certificate validation.
- Display a warning that a proxy operator may observe connection metadata and that HTTPS content should remain TLS-protected.

## Current limitation

The current VTok repository is a Compose shell, not the original TikTok APK. This plan alone does not route TikTok traffic and is not an operational bypass. Integration and end-to-end testing require a compatible target build and a networking integration point that is actually used by its requests.
