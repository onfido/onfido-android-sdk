# Onfido Android SDK

[![Download](https://api.bintray.com/packages/onfido/maven/onfido-capture-sdk/images/download.svg)](https://bintray.com/onfido/maven/onfido-capture-sdk/_latestVersion)
[![Build Status](https://www.bitrise.io/app/0d3fe90349e46fbe/status.svg?token=6GpMhK-XJU_9kWRuHzkLmA&branch=master)](https://www.bitrise.io/app/0d3fe90349e46fbe)

## Table of contents

* [Overview](#overview)
* [Getting started](#getting-started)
* [Handling callbacks](#handling-callbacks)
* [Customising SDK](#customising-sdk)
* [Creating checks](#creating-checks)
* [Going live](#going-live)
* [Cross platform frameworks](#cross-platform-frameworks)
* [Migrating](#migrating)
* [More information](#more-information)

## Overview

This SDK provides a drop-in set of screens and tools for Android applications to allow capturing of identity documents and face photos/live videos for the purpose of identity verification. The SDK offers a number of benefits to help you create the best onboarding/identity verification experience for your customers:

- Carefully designed UI to guide your customers through the entire photo/video-capturing process
- Modular design to help you seamlessly integrate the photo/video-capturing process into your application flow
- Advanced image quality detection technology to ensure the quality of the captured images meets the requirement of the Onfido identity verification process, guaranteeing the best success rate
- Direct image upload to the Onfido service, to simplify integration\*

\* Note: the SDK is only responsible for capturing and uploading photos/videos. You still need to access the [Onfido API](https://documentation.onfido.com/) to create and manage checks.

![Various views from the SDK](screenshots.png "")

## Getting started

The SDK supports API level 16 and above ([distribution stats](https://developer.android.com/about/dashboards/index.html)).

Our configuration is currently set to the following:

- `minSdkVersion = 16`
- `compileSdkVersion = 27`
- `targetSdkVersion = 27`
- `Android Support Library = 27.1.0`
- `Kotlin = 1.1+`

### 1. Obtaining tokens

In order to start integration, you will need the **API token** and the **mobile SDK token**. You can use our [sandbox](https://documentation.onfido.com/#testing) environment to test your integration, and you will find these two sandbox tokens inside your [Onfido Dashboard](https://onfido.com/dashboard/api/tokens).

**Warning:** You **MUST** use the **mobile SDK token** and not the **API token** when configuring the SDK itself.

### 2. Adding the SDK dependency

Starting on version [INSERT-CORE-RELEASE-VERSION], we now offer a modularised SDK, which means you can integrate it in two different ways:

```gradle
repositories {
  jcenter()
}

dependencies {
  implementation 'com.onfido.sdk.capture:onfido-capture-sdk:+'
  // OR
  implementation 'com.onfido.sdk.capture:onfido-capture-sdk-core:+'
}
```

#### 2.1 `onfido-capture-sdk`
Complete, input quality-focused solution suited for better captures and faster IDV flows. This is the **recommended** integrated option.
This version provides advanced on-device, real-time glare and blur detection as well as auto-capture (passport only) on top of a set of basic image validations.

Due to the advanced validation support stated above, in the form of C++ code, we recommend that the integrator app performs [multi-APK split](#211-multi-apk-split)
to optimise the app size for individual architectures.

##### 2.1.1 Multi-APK split

C++ code needs to be compiled for each of the CPU architectures (known as "ABIs") present on the Android environment. Currently, the SDK supports the following ABIs:

* `armeabi-v7a`: Version 7 or higher of the ARM processor. Most recent Android phones use this
* `arm64-v8a`: 64-bit ARM processors. Found on new generation devices
* `x86`: Most tablets and emulators
* `x86_64`: Used by 64-bit tablets

The SDK binary contains a copy of the native `.so` file for each of these four platforms.
You can considerably reduce the size of your `.apk` by applying APK split by ABI, editing your `build.gradle` as the following:

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
More information on the [Android documentation](http://tools.android.com/tech-docs/new-build-system/user-guide/apk-splits)

Average size (with Proguard enabled):

| ABI         |  Size   |
| ----------- | :-----: |
| armeabi-v7a | 6 Mb    |
| arm64-v8a   | 6.8 Mb  |
| x86         | 14.3 Mb |
| x86_64      | 15.9 Mb |


#### 2.2 `onfido-capture-sdk-core`
Lighter, app size-friendly version. This version provides a set of basic image validations mostly provided by the backend.
Since there are no real-time validations on-device, ABI split is not needed.

Average size (with Proguard enabled):

| ABI         |  Size   |
| ----------- | :-----: |
| universal   | 3.6 Mb  |

Notes:

Until these packages get approved to be included in JCenter, the following snippet must be used to instruct gradle to search for them on Bintray:

```gradle
repositories {
  maven {
    url  "https://dl.bintray.com/onfido/maven"
  }
}
```

**Warning:** In order to improve the security of our clients, we upgraded our infrastructure and SDK client SSL configurations to support TLSv1.2 only.
According to the relevant [Google documentation](https://developer.android.com/reference/javax/net/ssl/SSLSocket.html), this support comes enabled by default on every device running
Android API 20+.
In case you need to support devices older than that in your integration with the Onfido Android SDK, we need to access Google Play Services to install the latest security updates, which enable this support.
As such, if you don't use Google Play Services on your integration yet, we require you to add the following dependency:

```gradle
compile ('com.google.android.gms:play-services-base:x.y.z') {
           exclude group: 'com.android.support' // to avoid conflicts with your current support library
}
```

### 3. Instantiating the client

To use the SDK, you need to obtain an instance of the client object:

```java
final Context context = ...;
Onfido onfido = OnfidoFactory.create(context).getClient();
```

### 4. Creating an applicant

You must create an Onfido [applicant](https://documentation.onfido.com/#applicants) before you start the flow.

You must create applicants on your server. For a document or face check, the minimum applicant details required are `first_name` and `last_name`:

```shell
$ curl https://api.onfido.com/v2/applicants \
    -H 'Authorization: Token token=YOUR_API_TOKEN' \
    -d 'first_name=Theresa' \
    -d 'last_name=May'
```

The JSON response has an `id` field containing an UUID that identifies the applicant. Once you pass the applicant ID to the SDK, documents and live photos/videos uploaded by that instance of the SDK will be associated with that applicant.

### 5. Creating the SDK configuration

Create an `OnfidoConfig` using your sandbox mobile SDK token, along with the applicant id:

```java
final OnfidoConfig config = OnfidoConfig.builder()
            .withToken("YOUR_MOBILE_TOKEN")
            .withApplicant("YOUR_APPLICANT_ID")
            .build();
```

### 6. Starting the flow

```java
// start the flow. 1 should be your request code (customise as needed)
onfido.startActivityForResult(this,         /*must be an activity*/
                              1,            /*this request code will be important for you on onActivityResult() to identity the onfido callback*/
                              config);
```

Congratulations! You have successfully started the flow. Carry on reading the next sections to learn how to:

- Handle callbacks
- Customise the SDK
- Create checks

## Handling callbacks

To receive the result from the flow, you should override the method `onActivityResult`. Typically, on success, you would [create a check](#creating-checks) on your backend server.

```java
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

When the user has successfully completed the flow, and the captured photos/videos have been uploaded, the `userCompleted` method will be invoked. The `Captures` object contains information about the document and face captures made during the flow, while the `Applicant` object contains information about the newly created applicant object, or will be `null` in case an `applicantId` was used to initialize the flow through `withApplicant(String id)`.
With the applicant id, you can then [create a check](#creating-checks) for the user via your backend. On the other hand, if the user exits the flow without completing it, the `userExited` method will be invoked. Note that some images may have already been uploaded by this stage.

## Customising SDK

### 1. Flow customisation

You can customise the flow of the SDK via the `withCustomFlow(FlowStep[])` method. You can remove, add and shift around steps of the SDK flow.

```java
final FlowStep[] defaultStepsWithWelcomeScreen = new FlowStep[]{
    FlowStep.WELCOME,                       //Welcome step with a step summary, Optional
    FlowStep.CAPTURE_DOCUMENT,              //Document Capture Step
    FlowStep.CAPTURE_FACE,                  //Face Capture Step
    FlowStep.FINAL                          //Final Screen Step, Optional
};

final OnfidoConfig config = OnfidoConfig.builder()
    .withCustomFlow(defaultStepsWithWelcomeScreen)
    .withApplicant(applicantId)
    .build();
```

#### Document Capture Step
In this step the user can pick which type of document to capture, the document origin country, and then use the phone camera to capture it.

You can also specify a particular document type and country that the user is allowed to upload by replacing this step with a `CaptureScreenStep` containing the desired type and country code:

```java
final FlowStep[] flowStepsWithOptions = new FlowStep[]{
                FlowStep.WELCOME,
                new CaptureScreenStep(DocumentType.NATIONAL_IDENTITY_CARD, "IND"),
                FlowStep.CAPTURE_FACE,
                FlowStep.FINAL
        };
```

This way, the document type and country selection screens will not be visible prior to capturing the document.

#### Welcome Step
In this step the user is presented with a summary of the capture steps he/she is about to pass through.

#### Face Capture Step
In this step the user can capture either a photo of his/her face, or a live video by using the front camera. In case of choosing the second option,
the user will be prompted to perform some simple challenges. The photo option can be instantiated with `FlowStep.CAPTURE_FACE` or
`new FaceCaptureStep(FaceCaptureVariant.PHOTO)` and the video option with `new FaceCaptureStep(FaceCaptureVariant.VIDEO)`.
In case both types of `FaceCaptureStep` are added to the same custom flow, a custom `IllegalArgumentException` will be thrown at the beginning of the flow,
with the message `"Custom flow cannot contain both video and photo variants of face capture"`.

#### Message Screen Step (Optional)
This screen can be used to create a customised information step. It can be inserted anywhere in the flow multiple times.
It can be instantiated with the following constructor:`MessageScreenStep(String, String, String)`

#### Final Screen Step (Optional)
This is a form of **Message Screen Step**. It should be used at the end of the flow, but it's not necessary.

#### Identity Verification Intro Step (Optional)
This is a form of **Message Screen Step**. It explains to the user the purpose of the identity verification flow.

### 2. Theme customisation

In order to enhance the user experience on the transition between your application and the SDK, you can provide some customisation by defining certain colors inside your own `colors.xml` file:

`onfidoColorPrimary`: Defines the background color of the `Toolbar` which guides the user through the flow

`onfidoColorPrimaryDark`: Defines the color of the status bar above the `Toolbar`

`onfidoTextColorPrimary`: Defines the color of the title on the `Toolbar`

`onfidoTextColorSecondary`: Defines the color of the subtitle on the `Toolbar`

`onfidoColorAccent`: Defines the color of the `FloatingActionButton` which allows the user to move between steps, as well as some details on the
alert dialogs shown during the flow

### 4. Localisation

Onfido Android SDK already comes with out-of-the-box translations for the following locales:
- English (en) :uk:
- Spanish (es) :es:
- French  (fr) :fr:
- Portuguese (pt) 🇵🇹

In case you would like us to add translations for some other locales we don't provide yet, please contact us through [android-sdk@onfido.com](mailto:android-sdk@onfido.com).

You could also provide custom translations for locales that we don't currently support, by having an additional XML strings file inside your resources folder for the desired locale (e.g. `res/values-it/onfido_strings.xml` for :it: translation), with the content of our [strings.xml](strings.xml) file, translated for that locale.

**Note**: If the strings translations change it will result in a MINOR version change, therefore you are responsible for testing your translated layout in case you are using this feature. If you want a locale translated you can also get in touch with us at [android-sdk@onfido.com](mailto:android-sdk@onfido.com).

## Creating checks

As the SDK is only responsible for capturing and uploading photos/videos, you would need to start a check on your backend server using the [Onfido API](https://documentation.onfido.com/).

### 1. Obtaining an API token

All API requests must be made with an API token included in the request headers. You can find your API token (not to be mistaken with the mobile SDK token) inside your [Onfido Dashboard](https://onfido.com/dashboard/api/tokens).

Refer to the [Authentication](https://documentation.onfido.com/#authentication) section in the API documentation for details. For testing, you should be using the sandbox, and not the live, token.

### 2. Creating a check

You will need to create an *express* check by making a request to the [create check endpoint](https://documentation.onfido.com/#create-check), using the applicant id. If you are just verifying a document, you only have to include a [document report](https://documentation.onfido.com/#document-report) as part of the check. On the other hand, if you are verifying a document and a face photo/live video, you will also have to include a [facial similarity report](https://documentation.onfido.com/#facial-similarity) with the corresponding variants: `standard` for the photo option and `video` for the video option.

```shell
$ curl https://api.onfido.com/v2/applicants/YOUR_APPLICANT_ID/checks \
    -H 'Authorization: Token token=YOUR_API_TOKEN' \
    -d 'type=express' \
    -d 'reports[][name]=document' \
    -d 'reports[][name]=facial_similarity' \
    -d 'reports[][variant]=standard'
```

Note: you can also submit the POST request in JSON format.

You will receive a response containing the check id instantly. As document and facial similarity reports do not always return actual [results](https://documentation.onfido.com/#results) straightaway, you need to set up a webhook to get notified when the results are ready.

Finally, as you are testing with the sandbox token, please be aware that the results are pre-determined. You can learn more about sandbox responses [here](https://documentation.onfido.com/#sandbox-responses).

### 3. Setting up webhooks

Refer to the [Webhooks](https://documentation.onfido.com/#webhooks) section in the API documentation for details.

## Going live

Once you are happy with your integration and are ready to go live, please contact [client-support@onfido.com](mailto:client-support@onfido.com) to obtain live versions of the API token and the mobile SDK token. We will have to replace the sandbox tokens in your code with the live tokens.

A few things to check before you go live:

- Make sure you have set up webhooks to receive live events
- Make sure you have entered correct billing details inside your [Onfido Dashboard](https://onfido.com/dashboard/)

## Cross platform frameworks

We provide integration guides and sample applications to help customers integrate the Onfido Android SDK with applications built using the following cross-platform frameworks:

- [Xamarin](https://github.com/onfido/onfido-xamarin-sample-app)
- [React Native](https://github.com/onfido/onfido-sdk-react-native-sample-app)

We don't have out-of-the-box packages for such integrations yet, but these projects show complete examples of how our Android SDK can be successfully integrated in projects targeting these frameworks.
Any issue or question about the existing integrations should be raised on the corresponding repository and questions about further integrations should be sent to [android-sdk@onfido.com](mailto:android-sdk@onfido.com).

## Migrating

You can find the migration guide in [MIGRATION.md](MIGRATION.md) file.

## More information

Further information about the underlying Onfido API is available in our documentation [here](https://onfido.com/documentation).

### Support

Please open an issue through [GitHub](https://github.com/onfido/onfido-android-sdk/issues). Please be as detailed as you can. Remember **not** to submit your token in the issue. Also check the closed issues to check whether it has been previously raised and answered.

If you have any issues that contain sensitive information please send us an email with the ISSUE: at the start of the subject to [android-sdk@onfido.com](mailto:android-sdk@onfido.com).

Previous version of the SDK will be supported for a month after a new major version release. Note that when the support period has expired for an SDK version, no bug fixes will be provided, but the SDK will keep functioning (until further notice).

Copyright 2018 Onfido, Ltd. All rights reserved.
