# Onfido Android SDK Migration Guide

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
