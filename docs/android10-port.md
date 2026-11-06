# Phoshdroid Android 10 (API 29) experimental port

This branch is an **experimental source port**, NOT a working or device-tested APK.
It runs postmarketOS + Phosh inside an Android app, without replacing EMUI.
Google Mobile Services, root, and bootloader unlocking are not required.

## Source changes

- minSdk and Termux module minimum SDK set to API 29.
- API 29 uses Display.getRealSize rather than WindowManager.currentWindowMetrics.
- Android 10 requests legacy read/write storage permissions before starting the Linux session.
- Android 11+ retains existing all-files-access behavior.
- proot skips /sdcard bind when permission has not been granted.
- Missing Linux archives and failed extraction no longer mark bootstrap successful.
- postmarketOS config is written into the extracted Linux rootfs.
- Linux archive build scripts produce gzip tar files named bootstrap.bin and rootfs.bin.
- Fixed the original hardcoded pmbootstrap rootfs path.

## Real build prerequisites

Clone recursively, use JDK 17, Android SDK 35 and Android NDK 27.x.
Building the full installable APK also requires the actual ARM64 Linux files:

```sh
git submodule update --init --recursive
./rootfs/build-bootstrap.sh
./rootfs/build-rootfs.sh  # or package-rootfs.sh with a prebuilt pmbootstrap rootfs
./gradlew --no-daemon :app:assembleDebug
```

The repository intentionally ignores large `app/src/main/assets/*.bin` files.
`AssetExtractor` expects BOTH archives to be **gzip-compressed tar**, not zstd/plain tar.

## Validation and limitations

GitHub Actions only compiles app source and runs unit tests; it does not publish an APK.
Android 10 / Huawei P Smart S / EMUI 12 behavior remains unverified.
External storage behavior with targetSdk 35 requires a device test.
Calls, mobile data controls, camera, SMS and other phone hardware are not supplied to Phosh.
Do not flash or modify the phone bootloader; Android remains installed and intact.
