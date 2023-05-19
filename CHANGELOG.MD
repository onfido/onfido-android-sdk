# Change Log

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

**Note**: If the strings translations change it will result in a MINOR version change, therefore you
are responsible for testing your translated layout in case you are using custom
translations. [More on language localisation](README.md#4-localisation)

## [17.0.0] - 2023-05-09

### Added

- After the release of version 17.0.0, Onfido Android SDK runs in a separate process. This means that when the callback is triggered, the body of that callback will run in Onfido SDK process rather than the main application process. Therefore, any actions performed in the callback, such as accessing memory or modifying UI, will be executed within the SDK process. It is important to keep this in mind when designing and implementing callbacks in order to ensure the proper functioning and performance of the SDK. - After the release of version 17.0.0, Onfido Android SDK runs in a separate process. This means that when the callback is triggered, the body of that callback will run in the Onfido SDK process rather than the main application process. Therefore, any actions performed in the callback, such as accessing memory or modifying UI, will be executed within the SDK process. It is important to keep this in mind when designing and implementing callbacks in order to ensure the proper functioning and performance of the SDK. Having Onfido SDK run in its own process adds extra reliability to customer applications as it avoids crashes on the customer app. It also brings better error logging support.
- Add explicit support for the Nynorsk language

### Changed

- Initial release for the 4:3 aspect ratio
- Increase upload timeout to avoid Network Error message in slow connections
- Provided the application context via `TokenExpirationHandler`, `MediaCallback` and `OnfidoAnalyticsEventListener` callbacks

## [16.3.2] - 2023-04-28

### Fixed

- Fixed document media ids not being send properly which affects autofill task in studio

## [16.3.1] - 2023-04-17

### Changed

- Added default value to `DocumentSide#nfcSupported` to support backward compatibility.

## [16.3.0] - 2023-04-13

### Changed

- Enhanced security around document payload tampering

### Fixed

- Fix duplicated FACIAL_INTRO event 
- Fixed the invalid picture size exception
- Fixed theme hierarchy
- Fixed the crash that occurs during dismissing progress dialog
- Enable color customisation for Motion upload ProgressBar

## [16.2.0] - 2023-03-24

### Added

- Provided Motion public events
- Added Generic document support

### Changed

- Handle audio conflicts when there is an audio playing before start recording
- Handle mic conflicts when another app is using mic before starting recording
- Sync NFC supported documents logic with other platforms

### Fixed

- Fixed colour inconsistence of icons on document selection screen
- Fixed the issue related to the `userExited` callback when Onfido SDK launching is canceled early.
- Fixed loop in back navigation in Capture screen when permissions are granted via settings screen

## [16.1.0] - 2023-03-06

### Changed

- Handle audio and mic conflicts if there is audio playing or if the mic is in use when we start recording
- Added translations for audio in Motion strings
- Updated MLKit Face Detection library
- Added a MLKit Face Detection Module check in addition to the Google Play Services check in order to fallback to Selfie/Video
- Updated the supported document file to include Honduras National ID

### Fixed

- Fixed Hebrew and Indonesian language translation issues
- Fixed a crash when no camera is available on the device
- Fixed a crash when the app is brought to the background and restored after a while
- Fixed a Studio Workflow-related crash

## [16.0.1] - 2023-02-27

### Fixed

- Fixed the Workflow-related crash

## [16.0.0] - 2023-02-21

### Added

- Support audio recording in Motion
- Created a secure module to extend Onfido SDK device intelligence
- Onfido Secure SDK artifact has been removed. Please use the new device intelligence module.

### Changed

- Removed depreceated UserEventHandler. Please use OnfidoConfig.builder(context).withAnalyticsEventListener().
- Improve picture frame selection algorithm
- Added user analytics listener to WorkflowConfig builder. Similar to the normal flow, you can now attach your analytics listener to your config using WorkflowConfig.builder(context).withAnalyticsEventListener(listener).

### Fixed

- Navigate to Permissions denied screen instead of getting back to Intro screen when not all required permissions granted

## [15.4.1] - 2023-01-27

### Changed

- Improve picture frame selection algorithm

## [14.1.1] - 2023-01-27

### Changed

- Improve picture frame selection algorithm

## [13.3.1] - 2023-01-27

### Changed

- Improve picture frame selection algorithm

## [15.4.0] - 2023-01-26

### Fixed

- Fix a crash due to missing init method in ViewModelProvider

## [15.3.0] - 2023-01-09

### Added

- Fetching the supported documents from backend so it can be controlled from the dashboard

## [14.1.0] - 2022-12-23

### Changed

- Implemented a new enhanced resolution selection algorithm to take document photos with a better resolution.
- Improved camera focus. Our camera implementation now waits for the camera to focus before taking the document photo to prevent having blurry photos.
- Fixed displaying photo captured feedback too early. This issue was misguiding users so that they were moving their document or their device away while the photo capture was in progress.
- Increased image compression quality parameter.
- Disabled photo cropping for foldable devices to prevent having improperly cropped document images.

## [15.2.0] - 2022-12-22

### Changed

- Implemented a new enhanced resolution selection algorithm to take document photos with a better resolution.
- Improved camera focus. Our camera implementation now waits for the camera to focus before taking the document photo to prevent having blurry photos.
- Fixed displaying photo captured feedback too early. This issue was misguiding users so that they were moving their document or their device away while the photo capture was in progress.
- Increased image compression quality parameter.
- Disabled photo cropping for foldable devices to prevent having improperly cropped document images.

## [13.3.0] - 2022-12-22

### Changed

- Implemented a new enhanced resolution selection algorithm to take document photos with a better resolution.
- Improved camera focus. Our camera implementation now waits for the camera to focus before taking the document photo to prevent having blurry photos.
- Fixed displaying photo captured feedback too early. This issue was misguiding users so that they were moving their document or their device away while the photo capture was in progress.
- Increased image compression quality parameter.
- Disabled photo cropping for foldable devices to prevent having improperly cropped document images.

## [15.1.0] - 2022-12-22

### Added

- Extended localisation support to 44 languages.
- Added RTL languages support

## [15.0.0] - 2022-12-16

**Note:** This version contains breaking changes and is not backwards-compatible. Migration notes can be found in [MIGRATION.md](MIGRATION.md)

### Added

- Support Hungarian Address card as Proof of Address
- Create Onfido Studio documentation file
- Support Motion in Workflow(Studio)
- Support Motion experiment (A/B testing) in Workflow(Studio)

### Changed

- Added analytics events for the NFC flow
- Update readme to include Motion documentation
- Studio/Workflow is moved to a standalone module (Breaking change)

### Fixed

- Fix weird activity labels
- Fixed an issue where the capture screen seems frozen after the app is sent to background and resumed
- Fixed a bug where the capture button is displayed on top of the confirmation screen buttons

## [14.0.0] - 2022-11-21

### Added

- Removed all references to wording around beta for the NFC feature. NFC is no longer beta and generally available.
- Enable capture fallback option for Motion

### Changed

- Accessibility improvements for Motion
- Removed documentType and isAutocapture from the public properties list for the Document Capture events (analytics)
- Added a new analytics event: DocumentConfirmationWarning
- Fixed how the DocumentUploadCompleted analytics event is tracked

### Fixed

- Fixed `ClassCastException` when Camera not found
- Fixed an issue where the capture screen seems frozen after the app is sent to background and resumed
- Fixed the IllegalStateException crash, which occurs during navigation
- Fixed the NPE crash, which occurs during stopping the camera recording
- Fixed the crash when MLKit models cannot be downloaded. If this is the case we now detect this, and skip the validation.
- Fixed a bug where the capture button is displayed on top of the confirmation screen buttons
- Fixed Exif data related crashes

## [13.2.0] - 2022-10-10

### Added

- Introduced a selfie preview screen that allows users to review the exact photo that will be uploaded

### Changed

- General improvements in the Polish, Romanian and Czech language localisation
- Added Image Quality Service validations when NFC is enabled
- Improved logging for NFC

## [13.1.0] - 2022-09-16

### Added

- Added the experimental Face Capture variant `MOTION`

### Changed

- Updated public documentation to include proof of address info in captures object 
- Enabled bypassing selfie validation in `DEBUG` mode
- Added Czech, Polish and Romanian Language Support
- Improved the MRZ detection mechanism so that validation warnings are shown less

### Fixed

- Provided Proof of address captures info in the `userCompleted` callback

## [13.0.1] - 2022-10-31

### Fixed

- Fixed Exif data related crashes
- Fixed the IllegalStateException crash, which occurs during navigation
- Fixed the NPE crash, which occurs during stopping the camera recording
- Fixed the crash when MLKit models cannot be downloaded. If this is the case we now detect this, and skip the validation.
- Fixed `ClassCastException` when Camera not found

## [13.0.0] - 2022-09-07

### Added

- Add onfidoSecondaryButtonTextColor for secondary button title and border color customisation
- UI: Face alignment additional feedback for accessibility
- Improve NFC instructions screen

### Changed

- Allow enterprise features and mediacallback in orchesteratrion workflow builder
- Removed unused strings with translations

### Fixed

- UI: Fix submitting proof of address uploading issue
- Disable image quality validations before submission on the backside of Romanian National Identity Cards (fixing issues with blank backsides)

## [12.3.1] - 2022-08-18

### Fixed

- Disable image quality validations before submission on the backside of Romanian National Identity Cards (fixing issues with blank backsides)

## [12.3.0] - 2022-08-01

### Added

- Report to the integrator that Face Detection failed or is impossible
- Added Talk Back accessibility support
- UI: Ensuring a relevant warning is shown during document upload when the user is from a restricted country

### Changed

- Combined country and document type selection in one single screen (country first, document type second)
- Updated supported documents (Zambia NIC added, Albania Residence Permits removed, others)
- Introduced a new analytics event listener and deprecated the legacy UserEventHandler
- Disabled some functionalities that require Google Play Service when Google Play Service is unavailable

### Fixed

- UI: Ensured bulleted points are loaded correctly for the FlowActions that have been passed in
- Fixed NPE during document capture
- Fixed NPE during selfie capture
- Fixed NPE on the confirmation screen
- Fixed connectivity issue during document capture

## [12.2.2] - 2022-07-19

### Fixed

- UI: Ensured bulleted points are loaded correctly for the FlowActions that have been passed in.

## [12.2.1] - 2022-07-14

### Added

- UI: Ensures a relevant warning is shown during document upload when the user is from a restricted country

## [12.2.0] - 2022-06-01

### Changed

- Upgraded the Android Gradle Plugin to v7.3.1

### Fixed

- Fixed green video preview issue in some Xiaomi devices
- Fixed duplicated BaseContextWrappingDelegate class issue
- Add required metadata for Applicant Fraud Report when using NFC or document liveness
- Fixed camera error handling issue

## [12.1.0] - 2022-05-10

### Added

- Added support for capture of documents for proof of address report

## [12.0.0] - 2022-05-04

### Changed

- Removed the option to implement the user consent screen directly in your configuration during SDK initialization. It is now controlled by the Onfido backend. Please see our [Onfido privacy notices and consent migration guide](https://developers.onfido.com/guide/migration-guide-onfido-privacy-notices-and-consent) for further information.

## [11.5.0] - 2022-04-21

### Added

- Includes the Alpha release of the Onfido next generation facial similarity product. To join the Alpha program customers need to get in touch with their sales representative

## [10.3.5] - 2022-03-29

### Fixed

- Fixed the face orientation issue during Liveness video recording

## [11.4.1] - 2022-03-28

### Fixed

- Fixed the face orientation issue during Liveness video recording

## [11.4.0] - 2022-03-15

### Added

- UI: Add document type configurability on UI selection screen

### Changed

- Improved accessible capture
- Remove the usage of Android ID

### Fixed

- Fix duplicate module issue when using Proguard with already minified 3rd party libraries

## [10.3.4] - 2022-03-15

### Changed

- Improved accessible capture
- Remove the usage of Android ID

## [11.3.0] - 2022-02-15

### Changed

- UI: Updated barcode detection user experience

## [11.2.0] - 2022-01-31

### Changed

- Disabled accessibility if there's no spoken accessibility enabled
- UI: The shutter button is always visible and no longer hidden while waiting for auto-capture
- Filtered out countries that don't support the selected document type
- Changed video bitrate configuration for document capture flow

### Fixed

- Fixed a rare crash that happens when retrieving from the user consent URL
- Fixed MLKit related crash
- Fixed native library crashes

## [11.1.0] - 2022-01-17

### Changed

- UI: Added helpful feedback when NFC scan fails

### Fixed

- Fixed the document overlay size problem for passport document capture
- UI: Fixed a UI glitch in the document confirmation screen related to the activity lifecycle

## [10.3.3] - 2022-01-12

### Fixed

- Fixed the document overlay size problem that related passport document type
- UI: Fixed a ui glitch in document confirmation screen related to activity lifecycle

## [11.0.0] - 2022-01-10

### Added

- Added the Dutch locale

### Changed

- Upgraded compileSdkVersion and targetSdkVersion to version 31
- Added more instructions in the NFC scan screen
- Removed support for deprecated Mobile Token
- Updated document video recording instruction text

### Fixed

- Fixed an NPE when recapturing a document
- Fixed an NPE when starting liveness video recording
- Fixed: initializing the SDK with Locale.ROOT causes a crash

## [10.3.2] - 2021-12-15

### Fixed

- Fixed an NPE when recapturing a document
- Fixed video preview cut-off
- Fixed an NPE when starting liveness video recording

## [10.3.1] - 2021-11-16

### Changed

- Improved auto-capture performance
- Added `withNFCReadBetaFeature()` to `OnfidoConfig.Builder` for enabling NFC
- Removed traces of old support library references
- Marked extension functions as internal to avoid polluting integrators' namespace
- Onfido SDK now is built using Android Gradle Plugin 7.0.3
- Added NFC integration documentation

## [10.3.0] - 2021-10-25

### Added

- Screen reader users will have document positioning and auto capture
- Added face detection to document capture

### Changed

- UI: Added flip back arrow to back side of document capture

### Fixed

- UI: Fixed issue that liveness digit challenge does not have delay before button appears
- Fixed crash issue - `NullPointerException: volumeView` must not be `null`
- UI: Fixed issue that `exitWhenSentToBackground` exits flow when it switches from front of document to back of document
- Excluded META-INF/com.google.dagger_dagger.version from being packaged inside Onfido SDK
- Made microphone feature optional

## [10.2.0] - 2021-09-21

### Changed

- Updated document capture experience for better image quality (multiframe)
- Restart video recording if face out of frame
- Updated welcome screen instructions

### Fixed

- Bug fix to prevent SDK to use the sdk token from the previous session
- Fix to track DOCUMENT_CAPTURE_FRONT event twice

## [10.1.0] - 2021-09-08

### Changed

- Added `MissingOnfidoConfigException` error message if `OnfidoConfig` is not specified when
  starting the flow
- Applied new watermark position at the bottom of all screens
- Upgraded Kotlin version to v1.5.21
- Changed analytic event for passport from DOCUMENT_CAPTURE_FRONT to DOCUMENT_CAPTURE
- UI: Made live and post-capture alerts align to the top of the frame
- Implemented disabling mobile SDK analytics as an enterprise feature
- Migrated from RxJava 2 to RxJava 3
- Upgraded to AppCompat version 1.3.1
- Enabled R8

### Fixed

- Fixed some issues causing NPEs in CameraSource

## [10.0.0] - 2021-07-12

### Added

- UI: Added IT and PT locale support

### Changed

- UI: Split head turn instructions into 2 lines, with the arrow in between
- UI: Integrated image quality service
- UI: Improved designs and copies across the SDK. Please visit our [MIGRATION.md](MIGRATION.md) for the copy updates
- Removed deprecated methods and constructors of DocumentType class
- UI: Removed timer icon from subtitle in welcome screen
- UI: Properly set titles as accessibility headings
- Live capture warnings like "Glare detected" shouldn't steal focus from the user's current selection during Voice Over or Accessibility mode
- UI: Existing strings have been updated and new ones have been added. For more information, please visit our [MIGRATION.md](MIGRATION.md)
- UI: Screen reader should read back button with screen title
- UI: Made country selection list items to be treated as buttons

### Fixed

- Fixed low image quality issue that was happening on certain Xiaomi devices
- Fixed disappearing passport capture instruction text during on device validations
- Fixed a bug that was not triggering VIDEO_FACIAL_CAPTURE_STEP_0, VIDEO_FACIAL_CAPTURE_STEP_1 events in UserEventHandler during face liveness flow

## [10.1.0] - 2021-09-08

### Changed

- Added `MissingOnfidoConfigException` error message if `OnfidoConfig` is not specified when
  starting the flow
- Applied new watermark position at the bottom of all screens
- Upgraded Kotlin version to v1.5.21
- Changed analytic event for passport from DOCUMENT_CAPTURE_FRONT to DOCUMENT_CAPTURE
- UI: Made live and post-capture alerts align to the top of the frame
- Implemented disabling mobile SDK analytics as an enterprise feature
- Migrated from RxJava 2 to RxJava 3
- Upgraded to AppCompat version 1.3.1
- Enabled R8

### Fixed

- Fixed some issues causing NPEs in CameraSource

## [10.0.0] - 2021-07-12

### Added

- UI: Added IT and PT locale support

### Changed

- UI: Split head turn instructions into 2 lines, with the arrow in between
- UI: Integrated image quality service
- UI: Improved designs and copies across the SDK. Please visit our [MIGRATION.md](MIGRATION.md) for the copy updates
- Removed deprecated methods and constructors of DocumentType class
- UI: Removed timer icon from subtitle in welcome screen
- UI: Properly set titles as accessibility headings
- Live capture warnings like "Glare detected" shouldn't steal focus from the user's current selection during Voice Over or Accessibility mode
- UI: Existing strings have been updated and new ones have been added. For more information, please visit our [MIGRATION.md](MIGRATION.md)
- UI: Screen reader should read back button with screen title
- UI: Made country selection list items to be treated as buttons

### Fixed

- Fixed low image quality issue that was happening on certain Xiaomi devices
- Fixed disappearing passport capture instruction text during on device validations
- Fixed a bug that was not triggering VIDEO_FACIAL_CAPTURE_STEP_0, VIDEO_FACIAL_CAPTURE_STEP_1 events in UserEventHandler during face liveness flow

## [9.3.1] - 2021-05-25

### Fixed

- Fixed an issue that was not allowing the SDK version to be sent for analytics

## [9.3.0] - 2021-05-04

### Added

- UI: Add ability to customize the button border radius, through the `onfidoButtonCornerRadius` dimension resource

### Changed

- Migrated from Bintray to Maven Central
- Updated list of supported countries

## [9.2.0] - 2021-03-15

### Changed

- Now forcing user to retake image when blurry

## [9.1.0] - 2021-02-25

### Added

- Implemented optional user consent screen

### Fixed

- Fixed default language code for German

## [9.0.0] - 2021-02-15

### Changed

- Bumped Okhttp version to 4.9.0, Retrofit version to 2.9.0

## [8.1.0] - 2021-02-03

### Changed

- Migrate from Firebase ML to Google ML Kit
- Use java.security.SecureRandom instead of java.util.Random
- Disabled jetifier
- Fix wrong function name for the residence permit document type in the README

### Fixed

- Fix localisation bug for appcompat version 1.1.0 and 1.2.0

## [8.0.0] - 2020-12-16

### Added

- Added an option to hide the recorded video on the confirmation screen. For more information, please visit our [README](README.md#face-capture-step)

### Changed

- Dropped support for Android 4.x
- Now cropping document images before sending them to the backend
- UI: Updated Final Screen design

## [7.4.0] - 2020-10-20

### Added

- Added support for South African ID folded paper document capture

### Changed

- Renamed most of the localisation keys. Now, names are more explicit in which screens they are being used. Please visit our [MIGRATION.md](MIGRATION.md#730---740)
- Remove duplication in the README file

## [7.3.0] - 2020-08-27

### Added

- Added config to enable manual liveness capture

### Changed

- UI: Removed Singapore endonym

## [7.2.0] - 2020.07.06

### Changed:
- Improved US Driver Licence auto-capture performance

### Fixed:
- Fixed an issue which was causing a false positive and improved MRZ detection on passports
- Fixed some rare crashes related to CameraAPI

### Removed:
- Internal: Removed the zxing library dependency

## [7.1.0] - 2020.06.25

### Added:
- UI: Added co-branding feature - [enterprise]

### Removed:
- Internal: Removed `RxJavaPlugins.setErrorHandler` usage that was causing `IllegalStateException` when `RxJavaPlugins.setErrorHandler` used on host app

### Fixed:
- UI: Fixed infinite loader issue that was happening during video upload operation

## [7.0.0] - 2020-06-15

### Changed:
- UI: Changed Onfido watermark design
- UI: Realtime glare detection is disabled for the backside of Romanian national identity card
- UI: Updated auto-capture manual fallback alert
- Internal: Migrated to AndroidX. For more information, please visit our [MIGRATION.md](MIGRATION.md)
- Internal: Bump OkHttp version to 3.12.12 to avoid potential Android 11 issues

### Fixed:
- UI: Fixed Xiaomi specific zoom issue on the video preview

## [6.0.0] - 2020-04-30

### Added:
- UI: Added play and pause to the liveness intro auto-play view in order to improve accessibility
- UI: Added German language support
- UI: Added dynamic enterprise feature configuration (e.g. hiding Onfido logo) - [enterprise]
- Added information on api/token regions to documentation

### Changed:
- Now using [API v3](https://documentation.onfido.com/v3/) for communication with the backend
- Internal: Extended basic device information logging to all relevant API requests

### Removed:
- Removed out-of-the-box Portuguese (`pt`) translation

### Fixed:
- UI: Fixed document template overlay and edge detection message overlapping issue
- UI: Fixed a bug that caused the `no face found` warning to not display on the selfie capture screen
- Fixed localization problems on liveness instructions
- Fixed supported folded document types explanation on README
- Fixed a bug that threw `InvalidDocumentFormatAndCountryCombinationException` on `NationalIdentityCaptureStepBuilder` and `DrivingLicenceCaptureStepBuilder` when configured with country code and document format except CountryCode.FR and CountryCode.IT
- Fixed a bug that was causing sending not supported document type property to the Onfido backend.

## [5.6.0] - 2020-03-16

### Added:
- Added `Fragment` support to be able to start the SDK using a `Fragment` instance
- Added integrator defined event hook to allow integrators to collect user analytics
- Added `DocumentCaptureStepBuilder` and `FaceCaptureStepBuilder` to create `FlowStep` in order to customise SDK flow. For more information, please visit our [README.md](README.md#customising-sdk)
- UI: Now showing error message when passport MRZ is cut off in captured image

### Changed:
- Updated code snippets and descriptions related to API V3 in README
- Changed 'mobile sdk token' expression with 'mobile token' in README to prevent confusion
- Updated full and core SDK implementation code snippets in README
- Internal: Updated the following network libraries on `onfido-api-client`:
  - `com.squareup.retrofit2:retrofit:2.1.0 -> com.squareup.retrofit2:retrofit:2.6.4`
  - `com.squareup.okhttp3:okhttp:3.3.0 -> com.squareup.okhttp3:okhttp:3.12.8`
  - `com.jakewharton.retrofit:retrofit2-rxjava2-adapter-> io.reactivex.rxjava2:rxandroid:2.1.1`
- UI: Now using grey Onfido logo with higher contrast for accessibility
- UI: Screen reader order has been changed for better accessibility
- UI: Document guide overlay will be kept on the screen for longer

### Deprecated:
- The `CaptureScreenStep` class deprecated. For more information, please visit our [MIGRATION.md](MIGRATION.md)
- The `FaceCaptureStep` class deprecated. For more information, please visit our [MIGRATION.md](MIGRATION.md)

### Fixed:
- Fixed wrong document type in `Captures` class for preselected `DocumentType.RESIDENCE_PERMIT`
- Fixed the `TokenExpirationHandler` class's java compatibility

## [5.5.1] - [enterprise] - 2020-03-06

### Added:
- Internal: Added ENTERPRISE_README.md
- Internal: Added `publish-configuration.properties` file to manage publishing artifact

### Changed:
- UI: Onfido logo hidden instead using transparent `onfido_ic_watermark`

## [5.5.0] - 2020-02-17

### Changed:
- UI: Improved UX on liveness digit challenge

## [5.4.0] - 2020-01-23

### Added:
- `Face` property added into `Captures` class
- Added `sample-app` directory, in order to show sample usage of the sdk
- UI: Added folded document template for French driving license and Italian identity document
- UI: Updated [README.md](README.md) to clarify mobile token usage

### Changed:
- Internal: Updated Kotlin version to `1.3.50`
- UI: Changed camera overlay background color's opacity to enhance accessibility support

## [5.3.3] - 2020-01-08

### Fixed:
- Fixed a crash when using sdkToken with proguard enabled

## [5.3.2] - [enterprise] - 2019-12-18

### Added:
- Migration guide updated regarding to `OnfidoCertificatePinningSettings` changes

## [5.3.1] - 2019-12-16

### Fixed:
- UI: Fixed a crash when inflating the `onfido_ic_watermark`
- UI: Fixed a bug that was preventing the Onfido logo to be visible

## [5.3.0] - 2019-11-28

### Added:
- `DocumentType.GENERIC` type added. For more information, please visit our [README.md](README.md#document-capture-step)

### Changed:
- UI: Liveness intro video accessibility string improved
- UI: Added folded paper document support for French driving license and Italian identity document

## [5.2.0] - 2019-11-12

### Added:
- UI: `java.lang.Deprecated` annotation added additionally for the deprecated functions
- Internal: Improved RxJava2 error handling

### Changed:
- UI: Updated README to clarify `APPLICATION_ID` term and how to obtain sandbox token
- UI: Manual capture button showed after the user click retake or back button for passport and US DL
- UI: For passports and US Driving Licenses (DL) the manual capture button is shown after the user clicks on retake or on the back button.
- UI: The manual fallback countdown changed
- Internal: Changed token expiration identifier for the `onfido-api-client`

## [5.1.0] - 2019-10-14

### Added:
- Added SDK token support for US region
- Added ability to refresh SDK token when it expired. For more information, please visit our [README.md](README.md#4.1 SDK Token)
- Internal: Basic device information started to logging

### Changed:
- `onfido-api-client` dependency now bundled with the SDK, instead of getting downloaded as a transitive dependency.
- Added option to disable liveness intro video (`FaceCaptureStep(FaceCaptureVariantVideo(showIntroVideo = false))`)

### Removed:
- Removed `check()` and `checkStatus()` functions from `OnfidoAPI`

## [5.0.1] - 2019-09-06

### Changed:
- Improvements on our source code obfuscation strategy

## [5.0.0] - 2019-08-20

### Added:
- Added SDK token support (EU region)

### Changed:
- Removed Applicant parameter from `OnfidoResultListener` callback methods
- Internal: ApplicantId became mandatory parameter for 'OnfidoConfig' for `mobileToken` preferences

### Removed
- Removed the `withUSDLAutocapture()` method from the `OnfidoConfig.Builder`. The autocapture of the United States' driving license is now enabled by default
- Removed applicant creation using the SDK, along with the `withApplicant(Applicant)` method of the `OnfidoConfig.Builder`.
The applicant should always from now on be created outside of the SDK and its id used to initialise the flow `withApplicant(String)` on the `OnfidoConfig.Builder`

## [4.11.1] - 2019-11-07
**Note** Changes in this version are the changes applied on [v5.0.1](CHANGELOG.MD#501) to 4.11.0

### Added:
- Internal: Improved RxJava2 error handling

### Changed:
- Improvements on our source code obfuscation strategy

## [4.11.0] - 2019-08-12
*No changes since the previous release candidate version*

## [4.11.0-rc.1] - 2019-08-09

### Added:
- Internal: Support new token format
- Added certificate pinning support. For more information, please visit our [README.md](README.md#security)

### Changed:
- Internal: Performance improvements to our liveness face tracking feature

## [4.10.0] - 2019-07-18

### Added:
- Added support for Gradle 5 and Android Studio Gradle plugin 3.4.x

### Changed:
- UI: Improved the video capture challenge generation and added error handling

## [4.9.0] - 2019-06-11

### Added:
- Added United States' driver's license autocapture as an experimental feature. Can be enabled by calling `withUSDLAutocapture()` in the `OnfidoConfig.Builder`

### Changed:
- UI: Optimised liveness intro videos resolution and duration, reducing overall size

### Fixed:
- Fixed a crash when the host app was being killed by the system after sent to background and then recovered from, e.g., the recent apps list
- Fixed a crash rarely happening when a user navigates through the liveness challenges flow

## [4.8.1] - 2019-06-20

### Changed:
- Internal: Removed the strict requirement for the device to support autofocus in order to run the SDK.

### Fixed:
- Fixed a bug that was causing the `onfidoPrimaryButtonTextColor` attribute to have no effect in the main button customisation

## [4.8.0] - 2019-04-04

### Added:
- Added the ability to skip the selfie intro screen by adding the `FaceCaptureVariantPhoto` that can be passed
as an argument to a `FaceCaptureStep`
- Added the ability for integrators to specify a `Locale` for the flow to be displayed with, instead of inferring it from the device settings
- Added the ability for integrators to enable an "exit when sent to background" mode in the SDK flow through the `exitWhenSentToBackground()` in the `OnfidoConfig.Builder`.
This mode enforces that the flow will automatically exit through the `userExited()` callback whenever the app is sent to background mid-flow
- Added support for preselection of work permit documents, through the `DocumentType.WORK_PERMIT` enum value. This is a beta feature

### Changed:
- Internal: Upgraded OpenCV version to 3.4.5
- Internal: Now asking for the capture of both sides of the `DocumentType.NATIONAL_IDENTITY_CARD` from `CountryCode.IN` (indian national identity card)

### Fixed:
- Fixed crash when initialising the capture screen and system returned a null camera to the SDK
- Fixed crash in the liveness confirmation screen, related with the usage of a vector drawable as the background of a button
- Fixed a crash when initialising the capture screen and we are not able to retrieve the camera parameters from the system
- Fixed crash happening when the activity `android:label` property is required to be non-blank

## [4.7.0] - 2019-03-05

### Changed:
- Improved the documentation about adding custom translations and the `onfido_locale` string
- UI: Linked text views to the Onfido design system

### Fixed:
- Fixed crash when the SDK was wrongly doing operations with unknown request codes when transitioning from the capture screen to the flow screen
- Fixed crash when the SDK tries to access a view after the app was sent to background
- Fixed a crash when uploading the liveness video when the filename contains characters not supported by OkHttp
- Fixed a crash happening when face detection or tracking started on a device/emulator without Play Services
- Fixed a crash when reporting an error during the video recording back to the user while the error callback is `null`
- Fixed crash when checking for front camera support for selfie/video purposes throws a `RuntimeException`
- UI: Fixed the issue of text getting cropped when going over 2 lines in bullet views.
The text can now go up to 3 lines and shows an ellipsis if the content is longer than that.

## [4.6.0] - 2019-02-14

### Added:
- Added support for preselection of Visa documents, through the `DocumentType.VISA` enum value
- UI: Redesigned the liveness introduction screen to show an example video of the actions to perform

### Changed:
- UI: Redesigned the secondary action button to make it customisable
- UI: Added improvements to font size display when device accessibility features are enabled

## [4.5.1] - 2019-01-28
*No changes since the previous release candidate version*

## [4.5.1-rc.2] - 2019-01-25

### Changed:
- UI: Removed all the hardcoded colour codes and now referencing the resource for every UI element with our primary UI colour
- UI: Redesign according to the Onfido rebranding
- Internal: Improved the face detection algorithm during liveness pre-recording to better match the oval on the screen

### Fixed:
- Fixed crash when requesting camera focus after the host app was killed by the system
- Fixed crash when trying to fetch the dimensions of a view on the capture screen before it was measured for the first time

## [4.5.1-rc.1] - 2018-12-20

### Fixed:
- Fixed crash when initialising our face detector
- Internal: Fixed bug when launching two consecutive flows with two distinct tokens

## [4.5.0] - 2018-12-12

### Added:
- Added the ability to preselect just a document type, without specifying any info about the country of origin nor ask the user to select it
- UI: Added video review feature after live video recording
- UI: Added face detection and automatic recording on liveness capture
- UI: Added live face tracking during liveness challenges recording

### Changed:
- UI: Revamped liveness control buttons to provide a more explicit and easy to follow flow
- Internal: Simplified analytics by removing lifecycle-aware events
- Internal: Improved the repackaging method for our 3rd party dependencies
- Internal: Updated the map for the supported countries for each document

### Fixed:
- Fixed crash spotted when displaying the liveness challenges
- Fixed a crash when the search view on the country selection screen could not be properly loaded
- Fixed conflict between Kotlin enums and the jetify process required to work with AndroidX

### Deprecated:
- Deprecated the custom `MessageScreenStep`, previously used to introduce custom messages in the flow

## [4.4.0] - 2018-09-06

### Added:
- Added the ability to customise action buttons and icons colors
- Added instructions for integrators to get notified about releases
- UI: Added permission request and recovery screens for camera and microphone permissions
- Internal: Now bundling Proguard rules with the SDK, removing the need for the host application to specify those rules for themselves

### Changed:
- UI: Revamped flow final screen
- UI: Revamped the flat action button according to a new, more accessible design specification
- UI: Revamped liveness control buttons to provide a more explicit and easy to follow flow
- Internal: Changed our analytics solution from an external provider to an in-house service

### Fixed:
- Fixed build time warnings

## [4.3.0] - 2018-07-17

### Added:
- Internal: Added the language displayed by the SDK as a parameter on the live video upload, for speech analysis purposes

### Changed:
- UI: Moved the camera and microphone permissions request from the flow start to the capture screen
- Internal: Changed country suggestion to fetch the country code from the SIM country instead of geo IP
- Internal: Reduced frame size for glare detection, in order to make it more accurate

### Fixed:
- Fixed a crash happening when the host app forces a support library version below ours `27.1.0`
- UI: Fixed a bug which allowed users to dismiss the bottom sheet on the country selection screen
- UI: Fixed a bug of misalignment of video capture instructions happening when a longer than usual custom translation is provided
- UI: Fixed a bug which caused a crop on the document validation bubble when non-regular font sizes are set

## [4.2.0] - 2018-06-25

### Added:
- Added error state for multiple faces detected during a face capture step
- Added a storage space monitoring strategy during liveness video recording
- UI: Added the Onfido logo on the bottom of every bulleted message screen
- Internal: Added tracking for the unreadable barcode warning

### Changed:
- UI: Changed post upload validation errors UI to appear as an overlay bubble instead of a system dialog

### Removed
- Removed the need for the `WRITE_EXTERNAL_STORAGE` permission, by writing liveness recording videos to the internal storage

### Fixed
- Fixed bug which caused an unexpected behaviour when pressing back during a preselected document as first flow step
- Fixed bug causing a crash when an unexpected error body is returned from the API
- UI: Fixed bug during autocapture causing the information bottom sheet to enter an inconsistent state whenever the app was sent to background after the manual fallback threshold was triggered

## [4.1.0] - 2018-05-17

### Added
- Added post capture barcode detection for United States driving license captures
- Added `strings.xml` as a public file, in order to enable custom localisation. More information can be found in our [README.md](README.md#4-localisation)
- Added out-of-the-box Portuguese (`pt`) translation

### Changed
- Internal: Added `onfido` prefix and lint rule for every resource
- UI: Changed glare bubble layout to comply with the new post capture validation bubble

### Fixed
- Fixed a crash happening when the first camera frame was being processed before the camera screen UI was drawn.
- Fixed a bug happening when the first flow step is a capture step and the back button is pressed, causing the user to exit the flow.

## [4.0.0] - 2018-04-23
**Note:** This version contains breaking changes and is not backwards-compatible. Migration notes can be found in [MIGRATION.md](MIGRATION.md)

### Changed
- Updated dependencies to the following:
  - `compileSdkVersion = 27`
  - `targetSdkVersion = 27`
  - `Android Support Library = 27.1.0`

### Fixed
- Fixed crash happening when a corrupted flow result `Intent` is passed to the `handleActivityResult()` method on an `Onfido` instance.
- Internal: Fixed bug hiding the flow toolbar title when the SDK process was recreated after being killed by the system

## [3.0.0] - 2018-04-05

**Note:** This version contains breaking changes and is not backwards-compatible. Migration notes can be found in [MIGRATION.md](MIGRATION.md)

### Added
- UI: Added post capture blur detection for every document type
- UI: Added zoom & pan feature for document and face on confirmation screen
- Added cross platform frameworks section in [README.md](README.md)
- Added support for Singaporean residence permits as identity documents
- Added `onError(OnfidoException exception, Applicant applicant)` callback on the `Onfido` interface,
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
- Added ability to change `onfido` colors without having to change the host app default color values.

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
- Added `withApplicant(String id)` method to `OnfidoConfig.Builder`, in order to allow users to start a flow with a previously-created `Applicant`.
- Documentation: Added SDK localisation instructions in the `README.md`.

### Deprecated
- Deprecated `withApplicant(Applicant applicant)` method on `OnfidoConfig.Builder`, in favor of newly-created `withApplicant(String id)`.
From now on, applicants should not be created inside the SDK, but using the backend instead. Flow should be started using the applicant's id only.

### Changed
- Reduced SDK size

## [2.0.0]

**Note:** This version contains breaking changes and is not backwards-compatible. Migration notes can be found in [MIGRATION.md](MIGRATION.md)

### Added
- Added `FaceCaptureStep(FaceCaptureVariant variant)`, which is a custom object to add a face capture step to the flow,
with a variant of either `FaceCaptureVariant.PHOTO` or `FaceCaptureVariant.VIDEO`. Currently, the previous `FlowStep.CAPTURE_FACE`
is still available and is equivalent to `new FaceCaptureStep(FaceCaptureVariant.PHOTO)`
- UI: Added new video face capture screen as an alternative to photo face capture screen
- Permissions: We now require the `android.permission.RECORD_AUDIO` permission, in order to capture audio from video Face captures
- UI: Added Spanish translation

### Removed
- Removed `FlowStep.MESSAGE_FACE_VERIFICATION`, which is now automatically added before any face capture with the variant `FaceCaptureVariant.PHOTO`

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
- Removed `allowMetrics(boolean)` method from the `OnfidoConfig.Builder` object
- Removed previously deprecated `FlowStep.MESSAGE_IDENTIFY_VERIFICATION` enum instance

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
- Removed unused `OnfidoConfig` object from callbacks `userCompleted()` and `userExited()` which signal whether the user completed the proposed flow or not

### Added
- Added a `Captures` object on the `userCompleted()` callback method, which contains information about the document captures made during the flow
- Internal: Added two parameters, `sdk_source` and `sdk_version`, specifying the sdk name and version to every document or face upload calls using the API

## [0.8.0]

### Changed
- Deprecated `MESSAGE_IDENTIFY_VERIFICATION` `FlowStep`, since it is too specific for the purpose of the SDK, which should stay as generic as possible
- Changed the default flow, accessible through `FlowStep.getDefaultFlow()` to include a welcome step, also accessible as `FlowStep.WELCOME`
- Document capture step, accessible through `FlowStep.CAPTURE_DOCUMENT` now features 3 different screens. First, a document type selection screen is shown, followed by a country selection screen
for the document origin country to be chosen. Finally, the camera screen for the document capture
- Redesign of the message screen which results from a `MessageScreenStep`

- Internal: `FlowStep.WELCOME` and `FlowStep.MESSAGE_FACE_VERIFICATION` now have a bullet points layout with new copy
- Internal: Added a toolbar as part of the flow UI, with a title describing the current step and the ability to return to the previous step
- Internal: Added bottom sheet on Country Selection screen showing instructions for when the user can not find the origin country of his/her document
- Internal: Updated Kotlin version to `1.1.1`

### Fixed
- Internal: Crash on NullPointerException when trying to upload document which came as `null` from the camera. A `null` check is now performed after the picture is taken, and an error
message is shown in case it is `null`
- Internal: If country and document type selected is India and national id card, only the front of the document will be asked of the user. There was a mistake where this behaviour was happening with the driver's license instead.

### Removed
- Removed `FlowStep.MESSAGE` step. Developers should add a `MessageScreenStep(String title, String subtitle, String nextButtonText)` to a custom flow instead, specifying
which information they want to show on the screen.
- Internal: Removed unused `.png` drawables
- Internal: Removed unneeded `theme` from the `<application>` tag, which could cause conflicts with the host app's `theme`

### Added
- Added the ability for host applications to override the flow components' colors
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
- Fixed a bug on the confirmation screen where button layout would break when font size was set to the highest size
- Fixed a bug which caused the capture and confirmation screens camera view aspect ratio to be different
- Fixed a bug happening when an upload error had no hash to be parsed

## [0.7.0-rc.2] - 2018-02-16

### Removed
- Internal: Removed play services dependency (with respect to 0.7.0-rc.1)

## [0.7.0-rc.1]

### Added
- introduced allowMetrics, which allows the developer to choose whether SDK-only metrics may be taken.
- introduced CaptureScreenStep, which allows preselection of the document type on the flow configuration, hiding the document type selection view during the flow.
- `createIntent` was undeprecated, this is helpful when initiating the sdk from fragments.

### Removed
- deprecated methods were removed

### Changed
- `requestCode` was removed from `handleActivityResult(...)` since handling it should be the host app's responsibility.

## [0.6.0]

### Fixed
- Internal: Fixed crash bug that could happen if the user clicked on the confirm button too quickly (easier to reproduce on older phones)
- Fixed Glide conflict. Glide is now a standard dependency (instead of prebundled).

### Removed
- Removed launcher icon from resources and manifest


## [0.5.0]

### Changed
- The SDK flow now exits with a code, depending on the way that the user abandoned it.

### Fixed
- Crash bug fixed when the user denies camera permission.
- Internal: Fixed crash bug caused when the main activity got recreated and its applicant became null, which caused exceptions.
- Internal: Fixed the crash problem when another appp is using the camera in the background and the user opens the capture step at the same time. This fix informs the user of the problem, in hope the user will correct the situation.


## [0.4.0]

### Added
- Internal: A confirmation step shows up before sending the photo for validation.
- Added both `withBaseUrl(String)` and `withToken(String)`, which allow to customize the base url used when communicating to the backend and the possibility to set the authorization token used when communicating with said back-end.

### Changed
- Internal: Changed copy of validation error dialog.
- Deprecated `getOnfidoConfigFrom(Intent)`, `getApplicantFrom(Intent)`, `createOnfidoApiClient()` in favour of the more comprehensive api `handleActivityResult(...)`.

### Fixed
- Removed the colorAccent resource, which was causing dialog buttons to disappear.
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
- `startActivityForResult(Activity, int requestCode, OnfidoConfig)` and `handleActivityResult(int requestCode, int resultCode, Intent, OnfidoResultListener)` have both been added to simplify the callback process.
- `createIntent(OnfidoConfig, int requestCode)` has been added to replace `createIntent(OnfidoConfig config)`

### Changed
- Face capture is now uploaded to the live photo endpoint, with validation included.
- `createIntent(OnfidoConfig config)` has been deprecated.
- Removed fabric, appsee and crashlytics.
- Replace the full google play services with just the vision module.

### Removed
- The check is no longer initiated by the sdk.
    - `extractCheckResult(Intent)` has been removed
    - `OnfidoConfig#withAsyncCheck(boolean)` and `Onfido#withSyncWaitTime(int)` have been removed since they are related to check initiation
    - `FlowStep.SYNC_LOADING` has been removed as one of the possible steps.
- The deprecated `withCustomFlow(FlowAction[])` has been removed


## [0.2.2]

### Changed
- Internal: Upgraded onfido api wrapper to get a fix on document validation, due to an api signature change.


## [0.2.1]

### Changed
- Internal: Camera resolution has been optimized. It now tries to capture a document with a height resolution of 720p. Before it was capturing the highest resolution available.


## [0.2.0]

### Added
- Documents are now validated when uploaded.

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
- It's now possible to customize the SDK flow although with some restrictions.
- It' now possible to include customizable information screens anywhere in the flow.

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
- Made the `NextActionListener` interface public to allow clients to use the `MessageFragment` in their own code
- UI: created a `MessageDarkButtonStyle` and two button backgrounds, dark and light
