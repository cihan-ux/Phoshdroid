# Huawei P Smart S · Android 10 / EMUI 12 · Phoshdroid Roadmap

Date: 2026-11-06  
Development branch: `android10-compat`  
Goal: usable, responsive, stable Phosh/postmarketOS mobile session inside Android, **without root, GMS, bootloader unlocking, or replacing EMUI**.

## Observed device result (user-reported)

The user reports **6/6 successful** functional checks on Huawei P Smart S, Android 10:

- Touch swipe and Phosh unlock
- Console terminal launch
- Firefox internet access
- Files access to Android storage
- On-screen keyboard typing
- Exit and relaunch

Two user-provided photos show the Phosh lock screen and application grid, including the Termux:X11 extra-keys bar.

**Important evidence distinction:** The installed APK filename, signing key, versionCode and Git commit have not been confirmed. Do **not** attribute the device pass specifically to `8a3f70b` or any Android10-compat CI artifact until provenance is verified.

## CI baseline

- Android 10 source compatibility: https://github.com/cihan-ux/Phoshdroid/actions/runs/42236088684 — succeeded.
- Experimental Android 10 APK: https://github.com/cihan-ux/Phoshdroid/actions/runs/42236088827 — succeeded, artifact `Phoshdroid-Android10-EXPERIMENTAL-UNVERIFIED` (not yet attributed to the photographed device).
- Build artifact uses upstream 0.0.13 Linux payloads. It is not a tested or published release.

## Priority roadmap

### P0 · Establish a traceable device baseline

- Record installed APK identity (`versionName`, `versionCode`, package name, APK SHA-256 if available) and whether it came from upstream, fork CI, or another build.
- Add a device report: Android release, EMUI version, ABI, available RAM/storage, rootless status, source and result of the six checks.
- Re-run checks on the **fork CI APK** only after backing up app data and explaining that signing-key changes may prevent an in-place upgrade. Never uninstall existing working app without explicit approval.
- Measure first-launch / relaunch and record errors; avoid unsubstantiated performance claims.

**Done when:** a build identity and one repeatable device report are committed, with no false attribution.

### P1 · Touch UI polish / mobile ergonomics

- Add an option to hide/show the Termux:X11 extra-key toolbar, default hidden for the Phosh mobile presentation; do not remove keyboard access.
- Keep Android IME available through a persistent accessible action and verify text input in Console, Firefox and search.
- Review safe-area padding for waterdrop notch and gesture navigation; avoid hard-coded universal margins where device metrics are available.
- Verify orientation and display resize on Android 10 without a restart loop.

**Done when:** lock screen, app drawer, keyboard, touch gestures, and app switching work through 10 consecutive cycles with toolbar hidden.

### P2 · Session stability, RAM and battery

- Capture 10 consecutive cold/relaunch cycles, 10 lock/unlock cycles, and Android app background/resume behavior.
- Track private process memory and rootfs disk use with Android tools (if available), plus battery drain/thermal observations over a representative session.
- Make stale-socket cleanup idempotent; investigate crashes only from logs.
- Preserve user data and never kill Android telephony/system services.

**Done when:** no reproducible startup regressions and no silent app/data-loss path in these smoke tests.

### P3 · Media and Android integration (optional features)

- Prototype playback through an explicit audio bridge (e.g. PulseAudio → Android AudioTrack), with clean enable/disable behavior.
- Test media playback, headphone routing, pause/resume, and permissions on device.
- Treat microphone, camera, notifications, Bluetooth, phone calls and SMS as separate future investigations. Do not claim these work simply because the Phosh UI shows icons.

**Done when:** audio playback works with a documented fallback and does not interfere with Android audio.

### P4 · Build reproducibility and release discipline

- Maintain a source-compile test and a distinct full-APK pipeline; verify gzip tar payloads before packaging.
- Produce artifact name containing `android10`, commit SHA and `UNVERIFIED` until device testing is complete.
- Record checksum and build provenance, and publish release notes listing known incompatibilities.
- Never merge to `main`, publish a public release or alter repo visibility without explicit user authorization.

**Done when:** clean CI reproduces an installable artifact and the exact artifact passes the six core tests.

## Immediate order

1. P0 installed-build identification and regression baseline.
2. P1 hideable extra-keys bar with preserved keyboard function.
3. P2 launch/resume/memory measurements.
4. P3 audio feasibility, if device stability holds.
5. P4 reusable, correctly labeled APK distribution.

## Safety constraints

- Remain a normal Android app; **no flash, root, bootloader changes or Android OS replacement**.
- No remote access or data collection required.
- Do not assume GMS is installed.
- Keep the functional APK and data untouched until backup and signature compatibility are known.
- A screenshot is evidence of a rendered UI, not evidence of full Linux hardware support.
