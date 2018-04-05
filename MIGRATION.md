# Onfido Android SDK Migration Guide

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
