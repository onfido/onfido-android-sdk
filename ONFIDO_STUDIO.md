## Overview

[Onfido Studio](https://developers.onfido.com/guide/onfido-studio-product) is a drag and drop interface enabling you to build an optimised route to verify each end user, by defining and configuring different paths, as well as incorporating a combination of signals, in a single identity verification flow.

## Integrating with Android SDK

The Onfido Android SDK provides a drop-in set of screens and tools for Android applications to
capture identity documents and selfie photos and videos for the purpose of identity verification.

The SDK communicates directly and dynamically with active workflows to show the relevant screens to
ensure the correct capture and upload of user information. As a result, the SDK flow will vary
depending on the workflow configuration. You won't need to specify any steps directly in the SDK integration as these will be overridden when the workflow run ID is passed into the SDK initialisation.

## Getting started

The SDK supports API level 21 and
above ([distribution stats](https://developer.android.com/about/dashboards/index.html)).

Our configuration is currently set to the following:

- `minSdkVersion = 21`
- `targetSdkVersion = 31`
- `android.useAndroidX=true`
- `Kotlin = 1.7.10+`

```gradle
compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
}
```

### 1. Add the SDK dependency

`onfido-workflow` is the recommended integration option.

```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation 'com.onfido.sdk:onfido-workflow:x.y.z'
}
```

#### Multi-APK split

C++ code needs to be compiled for each of the CPU architectures (known as "ABIs") present on the
Android environment. Currently, the SDK supports the following ABIs:

* `armeabi-v7a`: Version 7 or higher of the ARM processor. Most recent Android phones use this
* `arm64-v8a`: 64-bit ARM processors. Found on new generation devices
* `x86`: Most tablets and emulators
* `x86_64`: Used by 64-bit tablets

The SDK binary contains a copy of the native `.so` file for each of these four platforms. You can
considerably reduce the size of your `.apk` by applying APK split by ABI, editing
your `build.gradle` to the following:

```gradle
android {
  splits {
    abi {
        enable true
        reset()
        include 'x86', 'x86_64', 'arm64-v8a', 'armeabi-v7a'
        universalApk false
    }
  }
}
```

Read
the [Android documentation](http://tools.android.com/tech-docs/new-build-system/user-guide/apk-splits)
for more information.

### 2. Build the configuration object

To initialise the SDK, you must provide a workflowRunId, obtained by [creating a workflow run](https://documentation.onfido.com/#create-workflow-run), and an sdkToken, 
obtained by [generating an SDK token](https://documentation.onfido.com/#generate-sdk-token).

```kotlin
const val REQUEST_CODE = 0x05

private lateinit var onfidoWorkflow: OnfidoWorkflow

fun onCreate(savedInstanceState: Bundle?) {
    val workflowConfig = WorkflowConfig.Builder(
        workflowRunId = "<WORKFLOW_RUN_ID>",
        sdkToken = "<SDK_TOKEN>"
    ).build()
}
```

### 3. Start the flow

```kotlin
onfidoWorkflow = OnfidoWorkflow.create(this)
startActivityForResult(onfidoWorkflow.createIntent(workflowConfig), REQUEST_CODE)
```

## Handling callbacks

To receive the result from a completed workflow, you should override the method `onActivityResult` on your
Activity/Fragment. The following code is provided as an example:

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    onfidoWorkflow.handleActivityResult(resultCode, data, object : OnfidoWorkflow.ResultListener {
        override fun onUserCompleted() {
            // Called when the entire workflow run has reached the terminal node.
        }

        override fun onUserExited(exitCode: ExitCode) {
            // Called when the user has exited the flow prematurely.
        }

        override fun onException(exception: OnfidoWorkflow.WorkflowException) {
            // Called when the flow has ended with an exception
        }
    })
}
```

| ATTRIBUTE       | NOTES                                                                                                                                                                                                                                                                                                                                |
|-----------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| onUserCompleted | The end user completed all interactive tasks in the workflow. If you have configured [webhooks](https://documentation.onfido.com/#webhooks), a notification will be sent to your backend confirming the workflow run has finished. You do not need to create a check using your backend as this is handled directly by the Workflow. |
| onUserExited    | The flow was exited prematurely by the user. The reason can be an ExitCode e.g. `USER_CONSENT_DENIED`                                                                                                                                                                                                                                |
| onException     | In case of an unexpected error, the onException method will be invoked with a relevant error message in the `WorkflowException` object. Error messages are not in a presentable format to the end user and are not localised.                                                                                                        |

## Error handling

The `exception` object returned as part of `onException(exception)` is of type `WorkflowException`.
It's a sealed class with multiple cases depending on the exception type.

```kotlin

override fun onException(exception: OnfidoWorkflow.WorkflowException) {
    when (exception) {
        is WorkflowInsufficientVersionException ->
        // This happens when you are using an older version of the Android SDK and trying to access a new functionality from workflow. You can fix this by updating the SDK
        is WorkflowInvalidSSLCertificateException ->
        // When network requests fail because SSL certificate is invalid
        is WorkflowTokenExpiredException ->
        // When SDK token is expired and needs to be refreshed
        is WorkflowUnknownCameraException ->
        // When some unknown camera exception happens.
        is WorkflowUnknownResultException ->
        // When an corrupted intent result is passed to the SDK
        is WorkflowUnsupportedTaskException ->
        // This happens when you are using an older version of the Android SDK. You can fix this by updating the SDK
        is WorkflowHttpException ->
        // This happens when the SDK receives an error from an API call see [https://documentation.onfido.com/#errors](https://documentation.onfido.com/#errors) for more information
        is WorkflowUnknownException ->
        // This happens when an unexpected error occurs. Please contact [android-sdk@onfido.com](mailto:android-sdk@onfido.com?Subject=ISSUE%3A) when this happens
        else -> 
        // Necessary because of Kotlin
    }
}

```

## Customizing the SDK

Studio uses the same appearance and localization objects as a standard integration. You can see how
to create them here: [Appearance](https://github.com/onfido/onfido-android-sdk#ui-customization)
and [Localization](https://github.com/onfido/onfido-android-sdk#localization).

Note: For localisation you need to use `withLocale(locale: Locale)` method of
the `WorkflowConfig.Builder` to set the preferred locale.
