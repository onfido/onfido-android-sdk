# Change Log
All notable changes to this project will be documented in this file.


## [0.3.1]

### Added
-Internal: if the captured document is not a passport, its backside is also requested.


## [0.3.0]

### Added
- Public: `startActivityForResult(Activity, int requestCode, OnfidoConfig)` and `handleActivityResult(int requestCode, int resultCode, Intent, OnfidoResultListener)` have both been added to simplify the callback process.
- Public: `createIntent(OnfidoConfig, int requestCode)` has been added to replace `createIntent(OnfidoConfig config)`

### Changed
- Public: Face capture is now uploaded to the live photo endpoint, with validation included.
- Public: `createIntent(OnfidoConfig config)` has been deprecated.
- Public: Removed fabric, appsee and crashlytics.
- Public: Replace the full google play services with just the vision module.

### Removed
- Public: The check is no longer initiated by the sdk.
    - `extractCheckResult(Intent)` has been removed
    - `OnfidoConfig#withAsyncCheck(boolean)` and `Onfido#withSyncWaitTime(int)` have been removed since they are related to check initiation
    - `FlowStep.SYNC_LOADING` has been removed as one of the possible steps.
- Public: The deprecated `withCustomFlow(FlowAction[])` has been removed


## [0.2.2]

### Changed
- Internal: Upgraded onfido api wrapper to get a fix on document validation, due to an api signature change.


## [0.2.1]

### Changed
- Internal: Camera resolution has been optimized. It now tries to capture a document with a height resolution of 720p. Before it was capturing the highest resolution available.


## [0.2.0]

### Added
- Public: Documents are now validated when uploaded.

### Changed
- Internal: onfido-api-client was updated in order to use the document validation


## [0.1.1]

### Added
- Capture Screen: Continuous Focus Mode is now always active.
- Capture Screen: It's possible to trigger a manual auto focus cycle by tapping on the screen.
- Capture Screen: A manual auto focus cycle is triggered before taking a picture.
- Capture Screen: Added an exposure metering area equal to the rectangle that encompasses the overlay shape.

### Fixed
- Capture Screen: Corrected the size and ratio of the camera preview and improved its resolution, notable effect on the face capture screen.
- Capture Screen: Removed copy that said an automatic capture would be triggered.
- Fixed a crash bug that happened whenever the user pressed back going from the face to the document capture screen in one go.
- Fixed a bug that did not permit the camera to be used by other apps when the sdk was running in the background.

### Changed
- Face Capture Screen: Increased the oval size and removed aliasing effect.


## [0.1.0]

### Added
- **Breaking**: Fabric is now included in the library in order to log crashes, this might require changes in the build process.
- Public: It's now possible to customize the SDK flow although with some restrictions.
- Public: It' now possible to include customizable information screens anywhere in the flow.

## [0.0.5]

### Fixed
- Build: Gradle script now supports proper publishing
- UI: Date picker now has a max date
- UI: Date picker style is now consistent with the other input fields (First and last name)
- UI: Date format is now displayed according to the device regional settings
- UI: Fixed the zooming of the face capture, it's now properly centered
- UI: Fixed the button radius, changed it according to UI spec

### Changed
- Dependencies: Bumped the version of `onfido-api-client` to 0.3.5

### Added
- Build: Now supports local maven in order to test locally developed dependencies
- Public: Made the `NextActionListener` interface public to allow clients to use the `MessageFragment` in their own code
- UI: created a `MessageDarkButtonStyle` and two button backgrounds, dark and light


The standard for this change log can be found [here](http://keepachangelog.com/).
