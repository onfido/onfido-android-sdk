# Onfido Android SDK

[![Download](https://api.bintray.com/packages/onfido/maven/onfido-capture-sdk/images/download.svg)](https://bintray.com/onfido/maven/onfido-capture-sdk/_latestVersion)
![Build Status](https://app.bitrise.io/app/0d3fe90349e46fbe/status.svg?token=6GpMhK-XJU_9kWRuHzkLmA&branch=master)

## Table of contents

* [Overview](#overview)
* [Getting started](#getting-started)
* [Handling callbacks](#handling-callbacks)
* [Customising SDK](#customising-sdk)
* [Creating checks](#creating-checks)
* [User Analytics](#user-analytics)
* [Going live](#going-live)
* [Cross platform frameworks](#cross-platform-frameworks)
* [Migrating](#migrating)
* [Security](#security)
* [Accessibility](#accessibility)
* [Getting notified about releases](#getting-notified-about-releases)
* [Licensing](#licensing)
* [More information](#more-information)

## Overview

This SDK provides a drop-in set of screens and tools for Android applications to allow capturing of identity documents and face photos/live videos for the purpose of identity verification. The SDK offers a number of benefits to help you create the best onboarding/identity verification experience for your customers:

- Carefully designed UI to guide your customers through the entire photo/video-capturing process
- Modular design to help you seamlessly integrate the photo/video-capturing process into your application flow
- Advanced image quality detection technology to ensure the quality of the captured images meets the requirement of the Onfido identity verification process, guaranteeing the best success rate
- Direct image upload to the Onfido service, to simplify integration\*

\* Note: the SDK is only responsible for capturing and uploading photos/videos. You still need to access the [Onfido API](https://documentation.onfido.com/) to create and manage checks.

![Various views from the SDK](screenshots.jpg "")
![Various views from the SDK](gifs.gif "")

## Getting started

The SDK supports API level 16 and above ([distribution stats](https://developer.android.com/about/dashboards/index.html)).

Our configuration is currently set to the following:

- `minSdkVersion = 16`
- `compileSdkVersion = 28`
- `targetSdkVersion = 28`
- `android.useAndroidX=true`
- `Kotlin = 1.3+`

### 1. Obtaining an API token

In order to start integration, you will need the **API token**. You can use our [sandbox](https://documentation.onfido.com/#sandbox-testing) environment to test your integration, and you will find these two sandbox tokens inside your [Onfido Dashboard](https://onfido.com/dashboard/api/tokens). You can create sandbox tokens inside your Onfido Dashboard.

#### 1.1 Regions

Onfido offers region-specific environments. Refer to the [Regions](https://documentation.onfido.com/#regions) section in the API documentation for token format and API base URL information.

### 2. Adding the SDK dependency

Starting on version `4.2.0`, we now offer a modularised SDK, which means you can integrate it in two different ways:

#### 2.1 `onfido-capture-sdk`
Complete, input quality-focused solution suited for better captures and faster IDV flows. This is the **recommended** integrated option.
This version provides advanced on-device, real-time glare and blur detection as well as auto-capture (passport only) on top of a set of basic image validations.

```gradle
repositories {
  jcenter()
}

dependencies {
  implementation 'com.onfido.sdk.capture:onfido-capture-sdk:+'
}
```

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
| armeabi-v7a | 5.43 Mb  |
| arm64-v8a   | 6.30 Mb  |

#### 2.2 `onfido-capture-sdk-core`
Lighter, app size-friendly version. This version provides a set of basic image validations mostly provided by the backend.
Since there are no real-time validations on-device, ABI split is not needed.

```gradle
repositories {
  jcenter()
}

dependencies {
  implementation 'com.onfido.sdk.capture:onfido-capture-sdk-core:+'
}
```

Average size (with Proguard enabled):

| ABI         |  Size   |
| ----------- | :-----: |
| universal   | 2.98 Mb  |

The sizes stated above were measured by building the minimum possible wrappers around our SDK,
using the following [stack](https://github.com/bitrise-io/bitrise.io/blob/master/system_reports/linux-docker-android-lts.log).
Different versions of dependencies such as Gradle or NDK may result in slightly different values.

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

### 3. Creating an applicant

You must create an Onfido [applicant](https://documentation.onfido.com/#applicants) before you start the flow.

You must create applicants on your server. For a document or face check, the minimum applicant details required are `first_name` and `last_name`:

```shell
$ curl https://api.onfido.com/v3/applicants \
    -H 'Authorization: Token token=YOUR_API_TOKEN' \
    -d 'first_name=Theresa' \
    -d 'last_name=May'
```

The JSON response has an `id` field containing an UUID that identifies the applicant. Once you pass the applicant ID to the SDK, documents and live photos/videos uploaded by that instance of the SDK will be associated with that applicant.

**Note**: If you're using API v2, please check out [API v2 to v3 migration guide](https://developers.onfido.com/guide/v2-to-v3-migration-guide#applicant-creation) to understand which changes need to be applied before starting to use API v3.

### 4. Configuring SDK with Tokens

We now support two token mechanisms:

`SDK token`   
`Mobile token`

We strongly recommend using a **SDK token**. It provides a more secure means of integration, as the token is temporary and applicant id-bound. Note that, if you're using an SDK token, you shouldn't call **withApplicantId** function.

#### 4.1 SDK Token

You will need to generate and include a short-lived JSON Web Token (JWT) every time you initialise the SDK. To generate an SDK Token you should perform a request to the SDK Token endpoint in the Onfido API:

To generate an SDK Token you should perform a request to the SDK Token endpoint in the Onfido API:

```
$ curl https://api.onfido.com/v3/sdk_token \
  -H 'Authorization: Token token=YOUR_API_TOKEN' \
  -F 'applicant_id=YOUR_APPLICANT_ID' \
  -F 'application_id=YOUR_APPLICATION_ID'
```

Make a note of the token value in the response, as you will need it later on when initialising the SDK.

**Warning:** SDK tokens expire 90 minutes after creation. The SDK token configurator function has an optional `tokenExpirationHandler` parameter. It will be called when sdk token expires and you can use it to pass a new one.

**Note**: If you're using API v2, please check out [API v2 to v3 migration guide](https://developers.onfido.com/guide/v2-to-v3-migration-guide) to understand which changes need to be applied before starting to use API v3.

##### Example Usage

##### Kotlin

```kotlin

class ExpirationHandler : TokenExpirationHandler {

        override fun refreshToken(injectNewToken: (String?) -> Unit) {
            TODO("Your network request logic to retrieve SDK token goes here")
            injectNewToken("NEW_SDK_TOKEN") // if you pass `null` sdk exit with token expired error
        }
    }

val config = OnfidoConfig.builder(context)
    .withSDKToken("YOUR_SDK_TOKEN_HERE", tokenExpirationHandler = ExpirationHandler()) // ExpirationHandler is optional
```

##### Java

```java

class ExpirationHandler implements TokenExpirationHandler {

    @Override
    public void refreshToken(@NotNull Function1<? super String, Unit> injectNewToken) {
        //Your network request logic to retrieve SDK token goes here
        injectNewToken.invoke("NEW_SDK_TOKEN"); // if you pass `null`  sdk exit with token expired error
    }
}

OnfidoConfig.Builder config = new OnfidoConfig.Builder(context)
                .withSDKToken("YOUR_SDK_TOKEN", new ExpirationHandler()); // ExpirationHandler is optional
```

**Note:** If you want to use `tokenExpirationHandler` you should pass concrete class instance, you should not pass an **anonymous** or **activity** class instance.  

#### 4.2 Mobile Token

**Note:**  Mobile token usage is still supported, but it **will be removed** in the future. If you are starting a project, we would strongly recommend that you use SDK tokens instead.

In order to start integration, you will need the **API token** and the **mobile token**. You can use our [sandbox](https://documentation.onfido.com/#sandbox-testing) environment to test your integration, and you will find these two sandbox tokens inside your [Onfido Dashboard](https://onfido.com/dashboard/api/tokens).

**Warning:** You **MUST** use the **mobile token** and not the **API token** when configuring the SDK itself.

##### Example Usage

##### Kotlin

```kotlin
val config = OnfidoConfig.builder(context)
    .withToken("YOUR_MOBILE_TOKEN_HERE")
    .withApplicant("YOUR_APPLICANT_ID_HERE")
```

##### Java

```java
OnfidoConfig.Builder config = new OnfidoConfig.Builder(this)
                    .withToken("YOUR_MOBILE_TOKEN_HERE")
                    .withApplicant("YOUR_APPLICANT_ID_HERE");
```

### 5. Instantiating the client

To use the SDK, you need to obtain an instance of the client object:

```java
final Context context = ...;
Onfido onfido = OnfidoFactory.create(context).getClient();
```

### 6. Starting the flow

```java
// start the flow. 1 should be your request code (customise as needed)
onfido.startActivityForResult(this,         /*must be an Activity or Fragment (support library)*/
                              1,            /*this request code will be important for you on onActivityResult() to identity the onfido callback*/
                              config);
```

Congratulations! You have successfully started the flow. Carry on reading the next sections to learn how to:

- Handle callbacks
- Customise the SDK
- Create checks

## Handling callbacks

To receive the result from the flow, you should override the method `onActivityResult` on your Activity/Fragment. Typically, on success, you would [create a check](#creating-checks) on your backend server.

```java
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

When the user has successfully completed the flow, and the captured photos/videos have been uploaded, the `userCompleted` method will be invoked. The `Captures` object contains information about the document and face captures made during the flow.
With the applicant id, you can then [create a check](#creating-checks) for the user via your backend. On the other hand, if the user exits the flow without completing it, the `userExited` method will be invoked. Note that some images may have already been uploaded by this stage.

Sample of a `Captures` instance returned by a flow with `FlowStep.CAPTURE_DOCUMENT` and `FlowStep.CAPTURE_FACE`:
```
Document:
        Front: DocumentSide(id=document_id, side=FRONT, type=DRIVING_LICENCE)
        Back: DocumentSide(id=document_id, side=BACK, type=DRIVING_LICENCE)
        Type: DRIVING_LICENCE
Face:
    Face(id=face_id, variant=PHOTO)
```
**Note** `type` property refers to `DocumentType`, variant refers to `FaceCaptureVariant`

As part of `userCompleted` method, the `DocumentType` property can only contain the values which are supported by Onfido backend APIs. Please check out [our API documentation](https://documentation.onfido.com/#document-types)

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
    .withSDKToken("YOUR_SDK_TOKEN")
    .build();
```

Also, by calling the `exitWhenSentToBackground()` method of the `OnfidoConfig.Builder`, you can determine that the flow should be exited whenever the user sends the app to background.
This exit action will invoke the `userExited(ExitCode exitCode)` callback described in the [handling callbacks section](#handling-callbacks).

#### Document Capture Step
In this step the user can pick which type of document to capture, the document origin country, and then use the phone camera to capture it.

To customise a document capture step you can use `DocumentCaptureStepBuilder` class's functions for the corresponding document types.

| Document Type         | Configuration function| Configurable Properties    |
|-----------------------|-----------------------|----------------------------|
| Passport              | forPassport()         |                            |
| National Identity Card| forNationalIdentity() | - country<br> - documentFormat |
| Driving Licence       | forDrivingLicence()   | - country<br> - documentFormat |
| Residence Permit      | forDrivingLicence()   | - country                  |
| Visa                  | forVisa()             | - country                  |
| Work Permit           | forWorkPermit()       | - country                  |
| Generic               | forGenericDocument()  | - country                  |

**Note** `GENERIC` document type doesn't offer an optimised capture experience for a desired document type.

##### Configuring Country

This configuration allows you to specify country of origin of document

**Note**: You can specify country for all document types except **Passport**.       

If you would like to specify country for driving license

##### Java

```Java
FlowStep drivingLicenceCaptureStep = DocumentCaptureStepBuilder.forDrivingLicence()
                .withCountry(CountryCode.GB)
                .build();
```

##### Kotlin

```kotlin
val drivingLicenceCaptureStep = DocumentCaptureStepBuilder.forDrivingLicence()
                .withCountry(CountryCode.GB)
                .build()
```

##### Configuring Document Format

This configuration allows you to specify format of document such as ***Card*** and ***Folded***

**Note**: You can specify ***folded*** document format for only French driving licence and Italian national identity. If you would configure the SDK with unsupported
country configuration the SDK will throw `InvalidDocumentFormatAndCountryCombinationException`   

If you would like to specify country and document format for driving license

##### Java

```java
FlowStep drivingLicenceCaptureStep = DocumentCaptureStepBuilder.forDrivingLicence()
                .withCountry(CountryCode.FR)
                .withDocumentFormat(DocumentFormat.FOLDED)
                .build();
```

##### Kotlin

```kotlin
val drivingLicenceCaptureStep = DocumentCaptureStepBuilder.forDrivingLicence()
                .withCountry(CountryCode.FR)
                .withDocumentFormat(DocumentFormat.FOLDED)
                .build()
```

Please be aware that we don't support every document - country combination, and unsupported documents will not be verified. So if you decide to bypass the default country selection screen by replacing the `FlowStep.CAPTURE_DOCUMENT` with a `CaptureScreenStep`, please make sure that you are specifying a supported document.
We provide an up-to-date list of the documents we support [here](https://onfido.com/supported-documents/)

#### Welcome Step
In this step the user is presented with a summary of the capture steps he/she is about to pass through.

#### Face Capture Step
In this step the user can capture either a photo of his/her face, or a live video by using the front camera. In case of choosing the second option,
the user will be prompted to perform some simple challenges. The photo option can be instantiated with `FlowStep.CAPTURE_FACE` or
`FaceCaptureStepBuilder.forPhoto()` and the video option with `FaceCaptureStepBuilder.forVideo()`.

By default both face and video variants show an introduction screen. You can disable the intro using `withIntro(false)` function.

```java
FlowStep faceCaptureStep = FaceCaptureStepBuilder.forVideo()
                .withIntro(false)
                .build();
```

In case both types of `FaceCaptureStep` are added to the same custom flow, a custom `IllegalArgumentException` will be thrown at the beginning of the flow,
with the message `"Custom flow cannot contain both video and photo variants of face capture"`.

#### Final Screen Step (Optional)
It should be used at the end of the flow, but it's not necessary.

#### Identity Verification Intro Step (Optional)
It explains to the user the purpose of the identity verification flow.

### 2. Theme customisation

In order to enhance the user experience on the transition between your application and the SDK, you can provide some customisation by defining certain colors inside your own `colors.xml` file:

`onfidoColorPrimary`: Defines the background color of the `Toolbar` which guides the user through the flow

`onfidoColorPrimaryDark`: Defines the color of the status bar above the `Toolbar`

`onfidoTextColorPrimary`: Defines the color of the title on the `Toolbar`

`onfidoTextColorSecondary`: Defines the color of the subtitle on the `Toolbar`

`onfidoColorAccent`: Defines the color of the `FloatingActionButton` which allows the user to move between steps, as well as some details on the
alert dialogs shown during the flow

`onfidoPrimaryButtonColor`: Defines the background color of the primary action buttons (e.g. proceed to the next flow step, confirm picture/video, etc),
the color of the text on the secondary action buttons (e.g. retake picture/video) and the background color of some icons and markers during the flow

`onfidoPrimaryButtonColorPressed`: Defines the background color of the primary action buttons when pressed

`onfidoPrimaryButtonTextColor`: Defines the color of the text inside the primary action buttons

### 4. Localisation

Onfido Android SDK already comes with out-of-the-box translations for the following locales:
- English (en) :uk:
- Spanish (es) :es:
- French  (fr) :fr:
- German  (de) :de:

In case you would like us to add translations for some other locales we don't provide yet, please contact us through [android-sdk@onfido.com](mailto:android-sdk@onfido.com).

You could also provide custom translations for locales that we don't currently support, by having an additional XML strings file inside your resources folder for the desired locale (e.g. `res/values-it/onfido_strings.xml` for :it: translation), with the content of our [strings.xml](strings.xml) file, translated for that locale.

By default, we infer the language to use from the device settings.
However, you can also use the `withLocale(Locale)` method of the `OnfidoConfig.Builder` to select a specific language.

**Notes**:
- If the strings translations change it will result in a MINOR version change, therefore you are responsible for testing your translated layout in case you are using this feature. If you want a locale translated you can also get in touch with us at [android-sdk@onfido.com](mailto:android-sdk@onfido.com).
- When adding custom translations, please make sure you add the whole set of keys we have on [strings.xml](strings.xml).
In particular, `onfido_locale`, which identifies the current locale being added, must be included.
The value for this string should be the [ISO 639-1](http://www.loc.gov/standards/iso639-2/php/code_list.php) 2-letter language code corresponding to the translation being added.
Examples:
    - When adding a translations file inside `values-ru` (russian translation), the `onfido_locale` key should have `ru` as its value.
    - When adding a translations file inside `values-en-rUS` (american english translation), the `onfido_locale` key should have `en` as its value.

Without this string correctly translated, we won't be able to determine which language the user is likely to use when doing the video liveness challenge. It may result in our inability to correctly process the video, and the check may fail.

### 5. Enterprise Features

If your account is configured with custom enterprise features and you are using an SDK token, you can call the `withEnterpriseFeatures(EnterpriseFeatures)` method of the`OnfidoConfig.Builder` and apply the desired features via an EnterpriseFeatures object

#### hideOnfidoLogo

Enable the hideOnfidoLogo feature by constructing an EnterpriseFeatures object with the first parameter set to true `new EnterpriseFeatures(true)`
This will provide you with a white labeled version of the SDK where the "powered by Onfido" logo will not be displayed anywhere

## Creating checks

As the SDK is only responsible for capturing and uploading photos/videos, you would need to start a check on your backend server using the [Onfido API](https://documentation.onfido.com/).

### 1. Obtaining an API token

All API requests must be made with an API token included in the request headers. You can find your API token (not to be mistaken with the mobile token) inside your [Onfido Dashboard](https://onfido.com/dashboard/api/tokens).

Refer to the [Authentication](https://documentation.onfido.com/#authentication) section in the API documentation for details. For testing, you should be using the sandbox, and not the live, token.

### 2. Creating a check

You will need to create a check by making a request to the [create check endpoint](https://documentation.onfido.com/#create-check), using the applicant id. If you are just verifying a document, you only have to include a [document report](https://documentation.onfido.com/#document-report) as part of the check. On the other hand, if you are verifying a document and a face photo/live video, you will also have to include a [facial similarity report](https://documentation.onfido.com/#facial-similarity-reports) with the corresponding values: `facial_similarity_photo` for the photo option and `facial_similarity_video` for the video option.

```shell
$ curl https://api.onfido.com/v3/checks \
    -H 'Authorization: Token token=YOUR_API_TOKEN' \
    -d 'applicant_id=YOUR_APPLICANT_ID' \
    -d 'report_names=[document,facial_similarity_photo]'
```

Note: you can also submit the POST request in JSON format.

You will receive a response containing the check id instantly. As document and facial similarity reports do not always return actual [results](https://documentation.onfido.com/#results) straightaway, you need to set up a webhook to get notified when the results are ready.

Finally, as you are testing with the sandbox token, please be aware that the results are pre-determined. You can learn more about sandbox responses [here](https://documentation.onfido.com/#pre-determined-responses).

**Note**: If you're using API v2, please check out [API v2 to v3 migration guide](https://developers.onfido.com/guide/v2-to-v3-migration-guide#checks-in-api-v3) to understand which changes need to be applied before starting to use API v3.

### 3. Setting up webhooks

Refer to the [Webhooks](https://documentation.onfido.com/#webhooks) section in the API documentation for details.

## User Analytics

The SDK allows you to track the user's journey through the verification process via an overrideable hook. This is meant to give some insight into how your user's make use of the SDK screens.

### Overriding the hook
In order to expose the user's progress through the SDK an hook method must be overridden in the `UserEventHandler.kt` object that's stored in the `Onfido.kt` interface. This can be done anywhere within your application and might look something like the following:

Java:
```java
Onfido.Companion.setUserEventHandler(new UserEventHandler() {
    @Override
    public void handleEvent(@NotNull String eventName, @NotNull Properties eventProperties) {
        // Your code here
    }
});
```

Kotlin:
```kotlin
Onfido.userEventHandler = object: UserEventHandler() {
    override fun handleEvent(eventName: String, eventProperties: Properties) {
        // Your code here
    }
}
```

The code inside of the overridden method will now be called when a particular event is triggered, usually when the user reaches a new screen. For a full list of events see [TRACKED_EVENTS.md](TRACKED_EVENTS.md).

The parameters being passed in are as follows:
- `eventName`: A `String` indicating the type of event. This value will always return user's visiting SDK screen name. In the future more event types, such as other user actions inside the SDK screens, may become available for tracking.
- `properties`: A `Map` object containing the specific details of an event. This will contain things such as the `name` of the screen visited.

### Using the data

Currently we recommend using the above hook to keep track of how many user's reach each screen in your flow. This can be done by storing the count of users that reach each screen and comparing them to the amount of user's who've made it to the `Welcome` screen.

## Going live

Once you are happy with your integration and are ready to go live, please contact [client-support@onfido.com](mailto:client-support@onfido.com) to obtain live versions of the API token and the mobile token. We will have to replace the sandbox tokens in your code with the live tokens.

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

## Security

This section is dedicated to every security aspect of the SDK

### Certificate Pinning
We provide integrators the ability to pin any communications between our SDK and server, through a `.withCertificatePinning()` method in
our `OnfidoConfig.Builder` configuration builder. This method accepts as parameter an `Array<String>` with sha-1/sha-256 hashes of certificates' public keys.
In case you are interested in using this feature, for more information about the hashes, please reach out to us at [android-sdk@onfido.com](mailto:android-sdk@onfido.com).

## Accessibility

The Onfido Android SDK has been optimised to provide the following accessibility support by default:

- Screen reader support: accessible labels for textual and non-textual elements available to aid TalkBack navigation, including dynamic alerts
- Dynamic font size support: all elements scale automatically according to the device's font size setting
- Sufficient color contrast: default colors have been tested to meet the recommended level of contrast
- Sufficient touch target size: all interactive elements have been designed to meet the recommended touch target size

## Getting notified about releases

In case you want to get notified about our releases, feel free to access our [Bintray page](https://bintray.com/onfido/maven/onfido-capture-sdk) and click the `Watch` button.

## Licensing

Due to API-design constraints, and to avoid possible conflicts during the integration, we bundle some of our 3rd party dependencies as repackaged versions of the original libraries.
For those, we include the licensing information inside our `.aar`, namely on the `res/raw/onfido_licenses.json`.
This file contains a summary of our bundled dependencies and all the licensing information required, including links to the relevant license texts contained in the same folder.
Integrators of our library are then responsible for keeping this information along with their integrations.

## More information

### Sample App

We have included sample app to show how to integrate the Onfido SDK. Please check out [our sample app](sample-app)

### API Documentation
Further information about the underlying Onfido API is available in our documentation [here](https://onfido.com/documentation).

### Support

Please open an issue through [GitHub](https://github.com/onfido/onfido-android-sdk/issues). Please be as detailed as you can. Remember **not** to submit your token in the issue. Also check the closed issues to check whether it has been previously raised and answered.

If you have any issues that contain sensitive information please send us an email with the ISSUE: at the start of the subject to [android-sdk@onfido.com](mailto:android-sdk@onfido.com).

Previous version of the SDK will be supported for a month after a new major version release. Note that when the support period has expired for an SDK version, no bug fixes will be provided, but the SDK will keep functioning (until further notice).

Copyright 2018 Onfido, Ltd. All rights reserved.
