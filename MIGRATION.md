# Onfido Android SDK Migration Guide

## '5.6.0' -> '6.0.0'

### Breaking changes
- Removed out-of-the-box Portuguese (`pt`) translation. If you would like to keep supporting Portuguese by providing your own XML files, please see [README](https://github.com/onfido/onfido-android-sdk#4-localisation)
- SDK will return `DocumentType.UNKNOWN` to mirror the Onfido API response as part of the `Captures` object which is provided by `handleActivityResult` if `residence permit` or `generic` is selected

### Added strings:
- `onfido_accessibility_video_pause`
- `onfido_accessibility_video_play`

## '5.5.0' -> '5.6.0'

#### Deprecation
- The `CaptureScreenStep` class is deprecated. We now recommend `DocumentCaptureStepBuilder` to customise document capture steps

#####before

###### Java

```java
new CaptureScreenStep(DocumentType.NATIONAL_IDENTITY_CARD, CountryCode.GB);
```

###### Kotlin

```kotlin
CaptureScreenStep(DocumentType.NATIONAL_IDENTITY_CARD, CountryCode.GB)
```

#####after

###### Java

```java
DocumentCaptureStepBuilder.forNationalIdentity()
                .withCountry(CountryCode.GB)
                .build();
```

###### Kotlin

```kotlin
DocumentCaptureStepBuilder.forNationalIdentity()
                .withCountry(CountryCode.GB)
                .build()
```

- The `FaceCaptureStep` class is deprecated. We now recommend `FaceCaptureStepBuilder` to customise face capture steps

#####before

###### Java

```java
FlowStep selfieCaptureStep = new FaceCaptureStep(new FaceCaptureVariantPhoto(false));

FlowStep videoCaptureStep = new FaceCaptureStep(new FaceCaptureVariantVideo(false));
```

###### Kotlin

```kotlin
val selfieCaptureStep = FaceCaptureStep(FaceCaptureVariantPhoto(false))

val videoCaptureStep = FaceCaptureStep(FaceCaptureVariantVideo(false))
```

#####after

###### Java

```java
FlowStep selfieCaptureStep = FaceCaptureStepBuilder.forPhoto()
                .withIntro(false)
                .build();

FlowStep videoCaptureStep = FaceCaptureStepBuilder.forVideo()
                .withIntro(false)
                .build();
```

###### Kotlin

```kotlin
val selfieCaptureStep = FaceCaptureStepBuilder.forPhoto()
                .withIntro(false)
                .build()

        val videoCaptureStep = FaceCaptureStepBuilder.forVideo()
                .withIntro(false)
                .build()
```

### Added strings:
- `onfido_mrz_not_detected_title`
- `onfido_mrz_not_detected_subtitle`

## `5.3.3` -> `5.4.0`

### Added strings:
- `onfido_italian_id_capture_title`
- `onfido_french_driving_license_capture_title`
- `onfido_folded_paper_option`
- `onfido_plastic_card_option`
- `onfido_driving_license_type_selection_title`
- `onfido_national_identity_type_selection_title`
- `onfido_folded_paper_front_capture_title`
- `onfido_folded_paper_front_capture_subtitle`
- `onfido_folded_paper_back_capture_title`
- `onfido_folded_paper_back_capture_subtitle`
- `onfido_folded_paper_confirmation_title`
- `onfido_upload_photo`
- `onfido_retake_photo`

#### Deprecation
- Deprecated properties of `DocumentType` class

## `4.5.0-F5` -> `5.3.2` - [enterprise]

### Breaking changes
- Removed `OnfidoCertificatePinningSettings` class which has `ONFIDO_API` parameter to provide root certificate's hash value.
For more information, please visit our [README.md](README.md#certificate-pinning) 

## `5.2.0` -> `5.3.0`

### Added strings:
- `onfido_label_doc_type_generic_up`

### Changed Strings:
- `onfido_accessibility_liveness_video_example`

## `5.1.0` -> `5.2.0`

### Added strings:
- `onfido_accessibility_liveness_face_detected`

## `5.0.1` -> `5.1.0`

### Added strings:
- `onfido_accessibility_camera_document_capture_view`
- `onfido_accessibility_face_confirmation_view`
- `onfido_accessibility_document_confirmation_view`
- `onfido_accessibility_liveness_confirmation_view`
- `onfido_accessibility_video_preview_recorded`
- `onfido_accessibility_liveness_digits`
- `onfido_accessibility_liveness_move`
- `onfido_accessibility_then`
- `onfido_accessibility_liveness_left`
- `onfido_accessibility_liveness_right`
- `onfido_accessibility_liveness_play_pause`
- `onfido_accessibility_take_picture`

## `5.0.0` -> `5.0.1`

### Changed
- No longer obfuscating `com.monadtek.mvp` package

## `4.11.0` -> `5.0.0`

### Applicant removal and Token change

#### Breaking change 
- Applicant class removed from the SDK
- Applicant parameter removed from the `OnfidoResultListener` callback methods
- Removed deprecated `withApplicant(Applicant)` method on the `OnfidoConfig.Builder` class as SDK no longer creates applicants

#### Deprecation
- The `withApplicant(String)` and  the `withToken(String)` methods are deprecated  from the `OnfidoConfig.Builder`, we now recommend that create a [SDK Token](README.md#41-sdk-token) on your backend which contains `applicantId` and use `withSDKToken(String)` method to initialise the OnfidoConfig 



##### Before

The initialisation of the SDK by passing the applicant ID and the (static) mobile token:
```
val OnfidoConfig config = OnfidoConfig.builder()
            .withToken("YOUR_MOBILE_TOKEN")
            .withApplicant("YOUR_APPLICANT_ID")
            .build();
```

or if you were using the deprecated `withApplicant(Applicant)` method:
```
val applicant = Applicant.builder()  
        .withFirstName("Your first name")  
        .withLastName("Your first name")  
        .build()  
  
val onfidoConfig = OnfidoConfig.builder()  
        .withApplicant(applicant)
        .withToken("YOUR_MOBILE_TOKEN")
        .build();
```

The SDK callback where the `Applicant` type object was passed:
```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    ...
    onfido.handleActivityResult(resultCode, data, new Onfido.OnfidoResultListener() {
        @Override
        public void userCompleted(Applicant applicant, Captures captures) {
            //communicate with your backend and initiate the check
        }

        @Override
        public void userExited(ExitCode exitCode, Applicant applicant) {
            //User left the sdk flow without completing it
        }

        @Override
        public void onError(OnfidoException exception, @Nullable Applicant applicant) {
            // An exception occurred during the flow
        }
    });
}
```

#####  After

Neither the (static) mobile token nor the Applicant ID are expected anymore, you are now expected to pass the SDK token which is generated by calling the Onfido API:
```
val sdkToken: String = createSdkToken() // https://github.com/onfido/onfido-android-sdk/blob/master/README.md#41-sdk-token 
  
val onfidoConfig = OnfidoConfig.builder(context)  
        .withSDKToken(sdkToken) 
        .build()
```

The SDK callback no longer passes an `Applicant` object:
```
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    ...
    onfido.handleActivityResult(resultCode, data, new Onfido.OnfidoResultListener() {
        @Override
        public void userCompleted(Captures captures) {
            //communicate with your backend and initiate the check
        }

        @Override
        public void userExited(ExitCode exitCode) {
            //User left the sdk flow without completing it
        }

        @Override
        public void onError(OnfidoException exception) {
            // An exception occurred during the flow
        }
    });
}
```

### US Driver's license optional feature

#### Breaking change
- Removed `withUSDLAutocapture()` method from the `OnfidoConfig.Builder`. Autocapture enabled by default for the US driving licences. 


### Added strings:
- `onfido_autocapture_manual_fallback_title`
- `onfido_autocapture_manual_fallback_description`

### Removed strings:
- `onfido_autocapture_info`
- `onfido_press_button_capture`
- `onfido_barcode_error_subtitle`
- `onfido_barcode_error_third_title`

### Changed Strings:
- `onfido_barcode_error_title`

## `4.8.0` -> `4.11.0`

### Added strings:
- `onfido_accessibility_liveness_video_example`
- `onfido_accessibility_camera_face_capture_view`


## `4.7.0` -> `4.8.0`

### Added strings:
- `onfido_label_doc_type_work_permit_up`
- `onfido_message_side_document_front_generic`
- `onfido_message_side_document_back_generic`
- `onfido_message_check_readability_subtitle_generic`
- `onfido_message_document_capture_info_front_generic`
- `onfido_message_document_capture_info_back_generic`
- `onfido_confirm_generic_document`

### Removed strings:
- `onfido_label_doc_type_passport`
- `onfido_label_doc_type_driving_license`
- `onfido_label_doc_type_id_card`
- `onfido_label_doc_type_visa`

## `4.6.0` -> `4.7.0`

### Changed Strings:
- `onfido_country_selection_toolbar_title`
- `onfido_unsupported_document_description`

## `4.5.1` -> `4.6.0`

### Added Strings:
- `onfido_label_doc_type_visa`
- `onfido_label_doc_type_visa_up`
- `onfido_message_document_visa`
- `onfido_message_check_readability_subtitle_visa`
- `onfido_confirm_visa`
- `onfido_liveness_intro_subtitle`
- `onfido_liveness_intro_step_1_title`
- `onfido_liveness_intro_step_2_title`
- `onfido_liveness_intro_loading_video`
- `onfido_reload`
- `onfido_unable_load_unstable_network`
- `onfido_unable_load_offline`

### Removed strings:
- `onfido_next`
- `onfido_liveness_intro_title`
- `onfido_liveness_intro_subtitle_1_action`
- `onfido_liveness_intro_subtitle_2_actions`
- `onfido_liveness_intro_subtitle_some_actions`
- `onfido_liveness_intro_third_subtitle_1_action`
- `onfido_liveness_intro_third_subtitle_2_actions`
- `onfido_liveness_intro_third_subtitle_some_actions`
- `onfido_liveness_challenge_open_mouth_title`
- `onfido_liveness_challenge_next`
- `onfido_liveness_challenge_stop`
- `onfido_stop`
- `onfido_liveness_challenge_recording`
- `onfido_video_recorded`
- `onfido_camera_access_recover_instructions_subtitle`

## `3.0.0` -> `4.0.0`
- Changed the `Applicant` parameter on the `userCompleted(Applicant applicant, Captures captures)` callback to be a non-nullable field,
meaning that we guarantee this field will always contain information about the applicant whenever this callback is called. Any null check being applied may now be deleted.
- Changed the `Applicant` parameter on the `onError(OnfidoException exception, @Nullable Applicant applicant)` callback to be a nullable value,
meaning that depending on the error originating the callback, the applicant details might be `null`. Therefore, developers should add the correspondent null check before accessing its information.

## `2.4.0` -> `3.0.0`
- Added `onError(OnfidoException exception, Applicant applicant)` method on the `Onfido` object, 
used to get the result of the identity verification flow. This callback will be called whenever an exception that the end-user 
should not be able to overcome by itself occurs during the flow.
The new method should be implemented and the exception handled accordingly.

- Upgraded our infrastructure and SDK client SSL configurations to support TLSv1.2 only. According to the relevant [Google documentation](https://developer.android.com/reference/javax/net/ssl/SSLSocket.html), this support comes enabled by default on every device running 
Android API 20+. In case you need to support devices older than that in your integration with the Onfido Android SDK, we need to access Google Play Services to install the latest security updates, which enable this support.
  As such, if you don't use Google Play Services on your integration yet, we require you to add the following dependency:
  
  ```gradle
  compile ('com.google.android.gms:play-services-base:x.y.z') {
             exclude group: 'com.android.support' // to avoid conflicts with your current support library
  }
  ```

## `2.0.0` -> `2.1.0`

### Deprecations
- Deprecated `withApplicant(Applicant applicant)` method. We now recommend that you create an Onfido applicant yourself on your backend and 
the `withApplicant(String id)` method should be called with the id of the created applicant.

## `1.0.0` -> `2.0.0`

### Breaking changes
- Removed `FlowStep.MESSAGE_FACE_VERIFICATION`, which is now automatically added before any face capture with the variant `FaceCaptureVariant.PHOTO`.
This way, any inclusion of this step in a custom flow should be removed

## `0.9.2` -> `1.0.0`

### Breaking changes

- Removed the `allowMetrics(boolean)` method from the `OnfidoConfig.Builder` object. Every call to this method should be deleted
- Removed the previously deprecated `FlowStep.MESSAGE_IDENTIFY_VERIFICATION` enum instance, as it was too specific for our generic flow intentions. 
Every previous inclusion of this object on a flow should be replaced by a custom `MessageScreenStep`
