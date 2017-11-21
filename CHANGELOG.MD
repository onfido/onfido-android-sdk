# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [2.0.0]

**Note:** This version contains breaking changes and is not backwards-compatible. Migration notes can be found in [MIGRATION.md](MIGRATION.md)

### Added
- Public: Added `FaceCaptureStep(FaceCaptureVariant variant)`, which is a custom object to add a face capture step to the flow,
with a variant of either `FaceCaptureVariant.PHOTO` or `FaceCaptureVariant.VIDEO`. Currently, the previous `FlowStep.CAPTURE_FACE`
is still available and is equivalent to `new FaceCaptureStep(FaceCaptureVariant.PHOTO)`
- UI: Added new video face capture screen as an alternative to photo face capture screen
- Permissions: We now require the `android.permission.RECORD_AUDIO` permission, in order to capture audio from video Face captures 
- UI: Added Spanish translation


### Removed
- Public: Removed `FlowStep.MESSAGE_FACE_VERIFICATION`, which is now automatically added before any face capture with the variant `FaceCaptureVariant.PHOTO`

### Changed
- UI: Changed countries' names on document country selection screen so that every country's name is presented in its native language.

## [1.0.0]

**Note:** This version contains breaking changes and is not backwards-compatible. Migration notes can be found in [MIGRATION.md](MIGRATION.md)

### Added
- UI: Added live edge detection for passport captures
- UI: Added residence permit card as an identity verification method 

### Changed
- UI: Glare detection changed from post-capture dialog warning to real-time feedback via a bubble notification
- UI: Dialogs cancellation feature has been removed, user has to explicitly take an action
- Internal: Improved analytics by introducing more granular tracking

### Removed
- Public: Removed `allowMetrics(boolean)` method from the `OnfidoConfig.Builder` object
- Public: Removed previously deprecated `FlowStep.MESSAGE_IDENTIFY_VERIFICATION` enum instance

## [0.9.2]

### Changed
- Internal: Now supporting Android Support Library in version 24 and above

### Fixed
- Internal: Fixed an old bug where the camera preview was stretched on some devices
- Internal: Fixed toolbar color customisation on document and face capture confirmation screens

## [0.9.1]

### Added
- Internal: Introduced glare detection feature for documents, bundled as JNI code. Check out README.md for more information
- UI: Added a dialog warning the user when glare is detected on a document after taking a photo

### Changed
- UI: Enhanced document, live photo and confirmation screens
- Internal: Changed analytics to a more client-oriented approach

## [0.9.0]

### Changed
- Internal: Changed API client to use Onfido API v2 instead of v1

### Removed
- Public: Removed unused `OnfidoConfig` object from callbacks `userCompleted()` and `userExited()` which signal whether the user completed the proposed flow or not

### Added
- Public: Added a `Captures` object on the `userCompleted()` callback method, which contains information about the document captures made during the flow
- Internal: Added two parameters, `sdk_source` and `sdk_version`, specifying the sdk name and version to every document or face upload calls using the API

## [0.8.0]

### Changed
- Public: Deprecated `MESSAGE_IDENTIFY_VERIFICATION` `FlowStep`, since it is too specific for the purpose of the SDK, which should stay as generic as possible
- Public: Changed the default flow, accessible through `FlowStep.getDefaultFlow()` to include a welcome step, also accessible as `FlowStep.WELCOME`
- Public: Document capture step, accessible through `FlowStep.CAPTURE_DOCUMENT` now features 3 different screens. First, a document type selection screen is shown, followed by a country selection screen
for the document origin country to be chosen. Finally, the camera screen for the document capture
- Public: Redesign of the message screen which results from a `MessageScreenStep`

- Internal: `FlowStep.WELCOME` and `FlowStep.MESSAGE_FACE_VERIFICATION` now have a bullet points layout with new copy
- Internal: Added a toolbar as part of the flow UI, with a title describing the current step and the ability to return to the previous step
- Internal: Added bottom sheet on Country Selection screen showing instructions for when the user can not find the origin country of his/her document
- Internal: Updated Kotlin version to `1.1.1`

### Fixed
- Internal: Crash on NullPointerException when trying to upload document which came as `null` from the camera. A `null` check is now performed after the picture is taken, and an error
message is shown in case it is `null`
- Internal: If country and document type selected is India and national id card, only the front of the document will be asked of the user. There was a mistake where this behaviour was happening with the driver's license instead.

### Removed
- Public: Removed `FlowStep.MESSAGE` step. Developers should add a `MessageScreenStep(String title, String subtitle, String nextButtonText)` to a custom flow instead, specifying
which information they want to show on the screen.
- Internal: Removed unused `.png` drawables
- Internal: Removed unneeded `theme` from the `<application>` tag, which could cause conflicts with the host app's `theme`

### Added
- Public: Added the ability for host applications to override the flow components' colors
- Internal: Added the document type and country to the document capture screen, and only the document type on passport captures

## [0.7.1]

### Changed
- Internal: If country and document type selected is India and national ID card, only the front of the document will be asked of the user.
- Internal: Changed side information on document captures to show only on back side captures from two-sided card documents (not Passport)

### Fixed
- Internal: Crash On RuntimeException for "Could not find requested camera". A message is now presented to the user in such cases, letting him know the camera is not available.
- Internal: Crash On RuntimeException for "takePicture failed". A message is now presented to the user when this happens.
- Internal: Crash on IllegalArgumentException for "meteringAreas is invalid NumFocusAreas must < 4". For devices that have a limit on the number of areas a safeguard clause was added to the code.

### Removed
- Internal: Glide was removed as a dependency. This reduced the method count to a value lower than on 0.6.0 (if code was run through Proguard).

## [0.7.0-rc.1]

### Added
- Public: introduced allowMetrics, which allows the developer to choose whether SDK-only metrics may be taken.
- Public: introduced CaptureScreenStep, which allows preselection of the document type on the flow configuration, hiding the document type selection view during the flow.
- Public: `createIntent` was undeprecated, this is helpful when initiating the sdk from fragments.

### Removed
- Public: deprecated methods were removed

### Changed
- Public: `requestCode` was removed from `handleActivityResult(...)` since handling it should be the host app's responsibility.

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
