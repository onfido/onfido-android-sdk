# Change Log
All notable changes to this project will be documented in this file.

## [0.6.0]

### Fixed
- Internal: Fixed crash bug that could happen if the user clicked on the confirm button too quickly (easier to reproduce on older phones)
- Public: Fixed Glide conflict. Glide is now a standard dependency (instead of prebundled).

### Removed
- Public: Removed launcher icon from resources and manifest


## [0.5.0]

### Changed
- Public: The SDK flow now exits with a code, depending on the way that the user abandoned it.

### Fixed
- Public: Crash bug fixed when the user denies camera permission.
- Internal: Fixed crash bug caused when the main activity got recreated and its applicant became null, which caused exceptions.
- Internal: Fixed the crash problem when another appp is using the camera in the background and the user opens the capture step at the same time. This fix informs the user of the problem, in hope the user will correct the situation.


## [0.4.0]

### Added
- Internal: A confirmation step shows up before sending the photo for validation.
- Public: Added both `withBaseUrl(String)` and `withToken(String)`, which allow to customize the base url used when communicating to the backend and the possibility to set the authorization token used when communicating with said back-end.

### Changed
- Internal: Changed copy of validation error dialog.
- Public: Deprecated `getOnfidoConfigFrom(Intent)`, `getApplicantFrom(Intent)`, `createOnfidoApiClient()` in favour of the more comprehensive api `handleActivityResult(...)`.

### Fixed
- Public: Removed the colorAccent resource, which was causing dialog buttons to disappear.
- Internal: Auto focus before capture has been removed, due to problems found on some devices.
- Internal: Fixed crash on devices without flash mode.
- Internal: Removed unused image assets.
- Internal: autoFocus crash bug exception fixed.
- Internal: Fixed null pointer exception crash when focus mode gets reverted in certain scenarios.


## [0.3.1]

### Added
- Internal: if the captured document is not a passport, its backside is also requested.


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
