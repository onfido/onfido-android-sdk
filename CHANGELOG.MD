# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

**Note**: If the strings translations change it will result in a MINOR version change, therefore you are responsible for testing your translated layout in case you are using custom translations. [More on language localisation](README.md#4-localisation)

## [4.11.0]
*No changes since the previous release candidate version*

## [4.11.0-rc.1]

### Added:
- Internal: Support new token format
- Public: Added certificate pinning support. For more information, please visit our [README.md](README.md#security)

### Changed:
- Internal: Performance improvements to our liveness face tracking feature

## [4.10.0]

### Added:
- Public: Added support for Gradle 5 and Android Studio Gradle plugin 3.4.x

### Changed:
- UI: Improved the video capture challenge generation and added error handling

## [4.9.0]

### Added:
- Public: Added United States' driver's license autocapture as an experimental feature. Can be enabled by calling `withUSDLAutocapture()` in the `OnfidoConfig.Builder`

### Changed:
- UI: Optimised liveness intro videos resolution and duration, reducing overall size

### Fixed:
- Public: Fixed a crash when the host app was being killed by the system after sent to background and then recovered from, e.g., the recent apps list
- Public: Fixed a crash rarely happening when a user navigates through the liveness challenges flow

## [4.8.1]

### Changed:
- Internal: Removed the strict requirement for the device to support autofocus in order to run the SDK.

### Fixed:
- Public: Fixed a bug that was causing the `onfidoPrimaryButtonTextColor` attribute to have no effect in the main button customisation

## [4.8.0]

### Added:
- Public: Added the ability to skip the selfie intro screen by adding the `FaceCaptureVariantPhoto` that can be passed
as an argument to a `FaceCaptureStep`
- Public: Added the ability for integrators to specify a `Locale` for the flow to be displayed with, instead of inferring it from the device settings
- Public: Added the ability for integrators to enable an "exit when sent to background" mode in the SDK flow through the `exitWhenSentToBackground()` in the `OnfidoConfig.Builder`.
This mode enforces that the flow will automatically exit through the `userExited()` callback whenever the app is sent to background mid-flow
- Public: Added support for preselection of work permit documents, through the `DocumentType.WORK_PERMIT` enum value. This is a beta feature

### Changed:
- Internal: Upgraded OpenCV version to 3.4.5
- Internal: Now asking for the capture of both sides of the `DocumentType.NATIONAL_IDENTITY_CARD` from `CountryCode.IN` (indian national identity card)

### Fixed:
- Public: Fixed crash when initialising the capture screen and system returned a null camera to the SDK
- Public: Fixed crash in the liveness confirmation screen, related with the usage of a vector drawable as the background of a button
- Public: Fixed a crash when initialising the capture screen and we are not able to retrieve the camera parameters from the system
- Public: Fixed crash happening when the activity `android:label` property is required to be non-blank

## [4.7.0]

### Changed:
- Public: Improved the documentation about adding custom translations and the `onfido_locale` string
- UI: Linked text views to the Onfido design system

### Fixed:
- Public: Fixed crash when the SDK was wrongly doing operations with unknown request codes when transitioning from the capture screen to the flow screen
- Public: Fixed crash when the SDK tries to access a view after the app was sent to background
- Public: Fixed a crash when uploading the liveness video when the filename contains characters not supported by OkHttp
- Public: Fixed a crash happening when face detection or tracking started on a device/emulator without Play Services
- Public: Fixed a crash when reporting an error during the video recording back to the user while the error callback is `null`
- Public: Fixed crash when checking for front camera support for selfie/video purposes throws a `RuntimeException`
- UI: Fixed the issue of text getting cropped when going over 2 lines in bullet views.
The text can now go up to 3 lines and shows an ellipsis if the content is longer than that.

## [4.6.0]

### Added:
- Public: Added support for preselection of Visa documents, through the `DocumentType.VISA` enum value
- UI: Redesigned the liveness introduction screen to show an example video of the actions to perform

### Changed:
- UI: Redesigned the secondary action button to make it customisable
- UI: Added improvements to font size display when device accessibility features are enabled

## [4.5.1]
*No changes since the previous release candidate version*

## [4.5.1-rc.2]

### Changed:
- UI: Removed all the hardcoded colour codes and now referencing the resource for every UI element with our primary UI colour
- UI: Redesign according to the Onfido rebranding
- Internal: Improved the face detection algorithm during liveness pre-recording to better match the oval on the screen

### Fixed:
- Public: Fixed crash when requesting camera focus after the host app was killed by the system
- Public: Fixed crash when trying to fetch the dimensions of a view on the capture screen before it was measured for the first time

## [4.5.1-rc.1]

### Fixed:
- Public: Fixed crash when initialising our face detector
- Internal: Fixed bug when launching two consecutive flows with two distinct tokens

## [4.5.0]

### Added:
- Public: Added the ability to preselect just a document type, without specifying any info about the country of origin nor ask the user to select it
- UI: Added video review feature after live video recording
- UI: Added face detection and automatic recording on liveness capture
- UI: Added live face tracking during liveness challenges recording

### Changed:
- UI: Revamped liveness control buttons to provide a more explicit and easy to follow flow
- Internal: Simplified analytics by removing lifecycle-aware events
- Internal: Improved the repackaging method for our 3rd party dependencies
- Internal: Updated the map for the supported countries for each document

### Fixed:
- Public: Fixed crash spotted when displaying the liveness challenges
- Public: Fixed a crash when the search view on the country selection screen could not be properly loaded
- Public: Fixed conflict between Kotlin enums and the jetify process required to work with AndroidX

### Deprecated:
- Public: Deprecated the custom `MessageScreenStep`, previously used to introduce custom messages in the flow

## [4.4.0]

### Added:
- Public: Added the ability to customise action buttons and icons colors
- Public: Added instructions for integrators to get notified about releases
- UI: Added permission request and recovery screens for camera and microphone permissions
- Internal: Now bundling Proguard rules with the SDK, removing the need for the host application to specify those rules for themselves

### Changed:
- UI: Revamped flow final screen
- UI: Revamped the flat action button according to a new, more accessible design specification
- UI: Revamped liveness control buttons to provide a more explicit and easy to follow flow
- Internal: Changed our analytics solution from an external provider to an in-house service

### Fixed:
- Public: Fixed build time warnings

## [4.3.0] - 2018-07-17

### Added:
- Internal: Added the language displayed by the SDK as a parameter on the live video upload, for speech analysis purposes

### Changed:
- UI: Moved the camera and microphone permissions request from the flow start to the capture screen
- Internal: Changed country suggestion to fetch the country code from the SIM country instead of geo IP
- Internal: Reduced frame size for glare detection, in order to make it more accurate

### Fixed:
- Public: Fixed a crash happening when the host app forces a support library version below ours `27.1.0`
- UI: Fixed a bug which allowed users to dismiss the bottom sheet on the country selection screen
- UI: Fixed a bug of misalignment of video capture instructions happening when a longer than usual custom translation is provided
- UI: Fixed a bug which caused a crop on the document validation bubble when non-regular font sizes are set

## [4.2.0] - 2018-06-25

### Added:
- Public: Added error state for multiple faces detected during a face capture step
- Public: Added a storage space monitoring strategy during liveness video recording
- UI: Added the Onfido logo on the bottom of every bulleted message screen
- Internal: Added tracking for the unreadable barcode warning

### Changed:
- UI: Changed post upload validation errors UI to appear as an overlay bubble instead of a system dialog

### Removed
- Public: Removed the need for the `WRITE_EXTERNAL_STORAGE` permission, by writing liveness recording videos to the internal storage

### Fixed
- Public: Fixed bug which caused an unexpected behaviour when pressing back during a preselected document as first flow step
- Public: Fixed bug causing a crash when an unexpected error body is returned from the API
- UI: Fixed bug during autocapture causing the information bottom sheet to enter an inconsistent state whenever the app was sent to background after the manual fallback threshold was triggered

## [4.1.0] - 2018-05-17

### Added
- Public: Added post capture barcode detection for United States driving license captures
- Public: Added `strings.xml` as a public file, in order to enable custom localisation. More information can be found in our [README.md](README.md#4-localisation)
- Public: Added out-of-the-box Portuguese (`pt`) translation

### Changed
- Internal: Added `onfido` prefix and lint rule for every resource
- UI: Changed glare bubble layout to comply with the new post capture validation bubble

### Fixed
- Public: Fixed a crash happening when the first camera frame was being processed before the camera screen UI was drawn.
- Public: Fixed a bug happening when the first flow step is a capture step and the back button is pressed, causing the user to exit the flow.

## [4.0.0] - 2018-04-23
**Note:** This version contains breaking changes and is not backwards-compatible. Migration notes can be found in [MIGRATION.md](MIGRATION.md)

### Changed
- Public: Updated dependencies to the following:
  - `compileSdkVersion = 27`
  - `targetSdkVersion = 27`
  - `Android Support Library = 27.1.0`

### Fixed
- Public: Fixed crash happening when a corrupted flow result `Intent` is passed to the `handleActivityResult()` method on an `Onfido` instance.
- Internal: Fixed bug hiding the flow toolbar title when the SDK process was recreated after being killed by the system

## [3.0.0] - 2018-04-05

**Note:** This version contains breaking changes and is not backwards-compatible. Migration notes can be found in [MIGRATION.md](MIGRATION.md)

### Added
- UI: Added post capture blur detection for every document type
- UI: Added zoom & pan feature for document and face on confirmation screen
- Public: Added cross platform frameworks section in [README.md](README.md)
- Public: Added support for Singaporean residence permits as identity documents
- Public: Added `onError(OnfidoException exception, Applicant applicant)` callback on the `Onfido` interface,
used to track flow completion

### Changed
- UI: Changed confirmation screen layout and buttons
- UI: Refactored colours across UI elements on the whole flow.
- Internal: Upgraded API client to make use of enhanced document detection feature on backend
- Internal: Restricted support for TLS 1.2 only on every network call, improving communication security

### Fixed
- Internal: Fixed crash on returning from the capture screen to the country list and to the document selection right after
- Internal: Fixed crash occasionally happening when closing the camera view (https://github.com/onfido/onfido-android-sdk/issues/12)
- Internal: Fixed crash when requesting focus repeatedly on capture screen
- Internal: Fixed crash occasionally happening when opening the camera view (https://github.com/onfido/onfido-android-sdk/issues/10)
- Internal: Fixed crash occasionally happening when starting the camera preview (https://github.com/onfido/onfido-android-sdk/issues/11)
- Internal: Fixed crash occasionally happening when the host app background process is killed while the SDK was running (https://github.com/onfido/onfido-android-sdk/issues/9)

## [2.4.0]

### Added
- UI: Added auto-capture feature for passport
- UI: Added loader on country suggestion (in case of slow network)
- UI: Added French language support
- Internal: Added new translations mechanism

### Fixed
- Internal: Fixed bug on choosing the video recording resolution
- Internal: Fixed crash when sending the SDK to background with the `Don't keep activities` option enabled

### Removed
- UI: Removed the open mouth challenge from the set of available video capture challenges

## [2.3.0]

### Added
- UI: Added country suggestion and search feature on the country selection screen.
- Public: Added ability to change `onfido` colors without having to change the host app default color values.

## Changed
- UI: Improved validation errors copy for document and face uploads.

### Removed
- Internal: Removed Play Services Vision dependency
- UI: Removed country availability text on document selection screen.

## [2.2.0]

### Changed
- Internal: Recorded face videos are now erased from device after the flow ends.
- UI: Replaced every FAB on the UI with a flat button.
- UI: Changed face capture intro screen (copy) and introduced icons instead of the previous arrows.

### Fixed
- UI: Fixed bug causing overlapping screens when using 26+ support library versions

## [2.1.0]

### Added
- Public: Added `withApplicant(String id)` method to `OnfidoConfig.Builder`, in order to allow users to start a flow with a previously-created `Applicant`.
- Documentation: Added SDK localisation instructions in the `README.md`.

### Deprecated
- Public: Deprecated `withApplicant(Applicant applicant)` method on `OnfidoConfig.Builder`, in favor of newly-created `withApplicant(String id)`.
From now on, applicants should not be created inside the SDK, but using the backend instead. Flow should be started using the applicant's id only.

### Changed
- Public: Reduced SDK size

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

## [0.7.0-rc.3] - 2018-03-28

### Fixed
- Public: Fixed a bug on the confirmation screen where button layout would break when font size was set to the highest size
- Public: Fixed a bug which caused the capture and confirmation screens camera view aspect ratio to be different
- Public: Fixed a bug happening when an upload error had no hash to be parsed

## [0.7.0-rc.2] - 2018-02-16

### Removed
- Internal: Removed play services dependency (with respect to 0.7.0-rc.1)

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
