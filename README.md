# Onfido Android SDK

[![Download](https://maven-badges.herokuapp.com/maven-central/com.onfido.sdk.capture/onfido-capture-sdk/badge.png)](https://search.maven.org/artifact/com.onfido.sdk.capture/onfido-capture-sdk/)
![Build Status](https://app.bitrise.io/app/0d3fe90349e46fbe/status.svg?token=6GpMhK-XJU_9kWRuHzkLmA&branch=master)

## Table of contents

* [Overview](#overview)
* [Getting started](#getting-started)
* [Handling callbacks](#handling-callbacks)
* [Custom Callbacks](#custom-callbacks)
* [Customizing the SDK](#customizing-the-sdk)
* [Creating checks](#creating-checks)
* [User Analytics](#user-analytics)
* [Going live](#going-live)
* [Cross platform frameworks](#cross-platform-frameworks)
* [Migrating](#migrating)
* [Security](#security)
* [Accessibility](#accessibility)
* [Licensing](#licensing)
* [More information](#more-information)

## Overview

The Onfido Android SDK provides a drop-in set of screens and tools for Android applications to capture identity documents and selfie photos, videos and motion captures for the purpose of identity verification. 

It offers a number of benefits to help you create the best identity verification experience for your customers:

- Carefully designed UI to guide your customers through the entire photo, video or motion capture process
- Modular design to help you seamlessly integrate the photo, video or motion capture process into your application flow
- Advanced image quality detection technology to ensure the quality of the captured images meets the requirement of the Onfido identity verification process, guaranteeing the best success rate
- Direct image upload to the Onfido service, to simplify integration

⚠️ Note: The SDK is only responsible for capturing and uploading photos, videos and motion captures. You still need to access the [Onfido API](https://documentation.onfido.com/) to manage applicants and perform checks.

![Various views from the SDK](screenshots.jpg "")
![Various views from the SDK](gifs.gif "")

## Getting started

The SDK supports API level 21 and above ([distribution stats](https://developer.android.com/about/dashboards/index.html)).

[Version 7.4.0](https://github.com/onfido/onfido-android-sdk/releases/tag/7.4.0) was the last version that supported API level 16 and above.

Our configuration is currently set to the following:

- `minSdkVersion = 21`
- `targetSdkVersion = 31`
- `android.useAndroidX=true`
- `Kotlin = 1.3+`
```
  compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
  }
```

⚠️ The following content assumes you're using our API v3 versions for backend calls. If you are currently using API v2 please refer to [this migration guide](https://developers.onfido.com/guide/api-v2-to-v3-migration-guide) for more information.
> ℹ️
>
> If you are integrating using Onfido Studio please see our** [Studio integration guide](ONFIDO_STUDIO.md)

### 1. Obtain an API token

In order to start integrating, you will need an [API token](https://documentation.onfido.com/#api-tokens).

You can use our [sandbox](https://documentation.onfido.com/#sandbox-testing) environment to test your integration. To use the sandbox, you'll need to generate a sandbox API token in your [Onfido Dashboard](https://onfido.com/dashboard/api/tokens).

⚠️ Note: You must never use API tokens in the frontend of your application or malicious users could discover them in your source code. You should only use them on your server.

#### 1.1 Regions

Onfido offers region-specific environments. Refer to the [Regions](https://documentation.onfido.com/#regions) section in our API documentation for token format and API base URL information.

### 2. Add the SDK dependency

Starting from version `4.2.0`, Onfido offers a modularized SDK. You can integrate it in 2 different ways:

1. `onfido-capture-sdk`
2. `onfido-capture-sdk-core`

#### 2.1 `onfido-capture-sdk`

This is the **recommended** integrated option.

This is a complete solution, focusing on input quality. It features advanced on-device, real-time glare and blur detection as well as auto-capture (passport only) on top of a set of basic image validations.

```gradle
repositories {
  mavenCentral()
}

dependencies {
  implementation 'com.onfido.sdk.capture:onfido-capture-sdk:x.y.z'
}
```

Due to the advanced validation support (in C++ code) we recommend that the integrator app performs [multi-APK split](#211-multi-apk-split) to optimize the app size for individual architectures.

##### 2.1.1 Multi-APK split

C++ code needs to be compiled for each of the CPU architectures (known as "ABIs") present on the Android environment. Currently, the SDK supports the following ABIs:

* `armeabi-v7a`: Version 7 or higher of the ARM processor. Most recent Android phones use this
* `arm64-v8a`: 64-bit ARM processors. Found on new generation devices
* `x86`: Most tablets and emulators
* `x86_64`: Used by 64-bit tablets

The SDK binary contains a copy of the native `.so` file for each of these four platforms.
You can considerably reduce the size of your `.apk` by applying APK split by ABI, editing your `build.gradle` to the following:

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
Read the [Android documentation](http://tools.android.com/tech-docs/new-build-system/user-guide/apk-splits) for more information.

Average size (with Proguard enabled):

| ABI         |  Size   |
| ----------- | :-----: |
| armeabi-v7a | 7.25 Mb  |
| arm64-v8a   | 8.13 Mb  |

#### 2.2 `onfido-capture-sdk-core`

This is a lighter version. It provides a set of basic image validations, mostly completed on the backend. There are no real-time validations on-device so ABI split is not needed.

```gradle
repositories {
  mavenCentral()
}

dependencies {
  implementation 'com.onfido.sdk.capture:onfido-capture-sdk-core:x.y.z'
}
```

Average size (with Proguard enabled):

| ABI         |  Size   |
| ----------- | :-----: |
| universal   | 4.71 Mb  |


**Note**: The average sizes were measured by building the minimum possible wrappers around our SDK,
using the following [stack](https://github.com/bitrise-io/bitrise.io/blob/master/system_reports/linux-docker-android-lts.log).
Different versions of the dependencies, such as Gradle or NDK, may result in slightly different values.

⚠️ In order to improve the security of our clients, we upgraded our infrastructure and SDK client SSL configurations to support TLSv1.2 only.
According to the relevant [Google documentation](https://developer.android.com/reference/javax/net/ssl/SSLSocket.html), this support comes enabled by default on every device running Android API 20+.
If you need to support older devices, we need to access Google Play Services to install the latest security updates which enable this support.
If you don't use Google Play Services on your integration yet, we require you to add the following dependency:

```gradle
compile ('com.google.android.gms:play-services-base:x.y.z') {
           exclude group: 'com.android.support' // to avoid conflicts with your current support library
}
```

### 3. Create an applicant

To create an applicant from your backend server, make a request to the ['create applicant' endpoint](https://documentation.onfido.com/#create-applicant), using a valid API token.

**Note**: Different report types have different minimum requirements for applicant data. For a Document or Facial Similarity report the minimum applicant details required are `first_name` and `last_name`.

```shell
$ curl https://api.onfido.com/v3/applicants \
    -H 'Authorization: Token token=<YOUR_API_TOKEN>' \
    -d 'first_name=John' \
    -d 'last_name=Smith'
```

The JSON response will return an `id` field containing a UUID that identifies the applicant. Once you pass the applicant ID to the SDK, documents, photos, videos and motion captures uploaded by that instance of the SDK will be associated with that applicant.

### 4. Configure the SDK with a token

You'll need to generate and include an SDK token every time you initialize the SDK.
To generate an SDK token, make a request to the ['generate SDK token' endpoint](https://documentation.onfido.com/#generate-web-sdk-token).

```shell
$ curl https://api.onfido.com/v3/sdk_token \
  -H 'Authorization: Token token=<YOUR_API_TOKEN>' \
  -F 'applicant_id=<YOUR_APPLICANT_ID>' \
  -F 'application_id=<YOUR_APPLICATION_ID>'
```

| Parameter           |  Notes   |
| ------------ | --- |
| `applicant_id` | **required** <br /> Specifies the applicant for the SDK instance. |
| `application_id` | **required** <br /> The application ID that was set up during development. For Android, this is usually in the form `com.example.yourapp`. Make sure to use a valid `application_id` or you'll receive a 401 error. |

⚠️ SDK tokens expire after 90 minutes.

##### 4.1 `tokenExpirationHandler`

You can use the optional `tokenExpirationHandler` parameter in the SDK token configurator function to generate and pass a new SDK token when it expires. This ensures the SDK continues its flow even after an SDK token has expired.

For example:

##### Kotlin

```kotlin

class ExpirationHandler : TokenExpirationHandler {

        override fun refreshToken(injectNewToken: (String?) -> Unit) {
            TODO("<Your network request logic to retrieve SDK token goes here>")
            injectNewToken("<NEW_SDK_TOKEN>") // if you pass `null` the sdk will exit with token expired error
        }
    }

val config = OnfidoConfig.builder(context)
    .withSDKToken("<YOUR_SDK_TOKEN_HERE>", tokenExpirationHandler = ExpirationHandler()) // ExpirationHandler is optional
```

##### Java

```java

class ExpirationHandler implements TokenExpirationHandler {

    @Override
    public void refreshToken(@NotNull Function1<? super String, Unit> injectNewToken) {
        //Your network request logic to retrieve SDK token goes here
        injectNewToken.invoke("<NEW_SDK_TOKEN>"); // if you pass `null` the sdk will exit with token expired error
    }
}

OnfidoConfig.Builder config = new OnfidoConfig.Builder(context)
                .withSDKToken("<YOUR_SDK_TOKEN>", new ExpirationHandler()); // ExpirationHandler is optional
```

**Note:** If you want to use `tokenExpirationHandler` you should pass a concrete class instance, you should not pass an **anonymous** or **activity** class instance.

### 5. Instantiate the client

To use the SDK, you need to obtain an instance of the client object.

```java
final Context context = ...;
Onfido onfido = OnfidoFactory.create(context).getClient();
```

### 6. Start the flow

```java
// start the flow. 1 should be your request code (customize as needed)
onfido.startActivityForResult(this,         /*must be an Activity or Fragment (support library)*/
                              1,            /*this request code will be important for you on onActivityResult() to identify the onfido callback*/
                              config);
```


## Handling callbacks

To receive the result from the flow, you should override the method `onActivityResult` on your Activity or Fragment. Typically, on success, you would [create a check](#creating-checks) on your backend server.

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    ...
    onfido.handleActivityResult(resultCode, data, new Onfido.OnfidoResultListener() {
        @Override
        public void userCompleted(Captures captures) {
        }

        @Override
        public void userExited(ExitCode exitCode) {
        }

        @Override
        public void onError(OnfidoException exception) {
        }
    });
}
```

| Attribute     |    Notes    |
| -----|-------|
| `userCompleted` | User completed the flow. You can now [create a check](#creating-checks) on your backend server. The `captures` object contains information about the document and face captures made during the flow.|
| `userExited` | User left the SDK flow without completing it. Some images may have already been uploaded. The `exitCode` object contains information about the reason for exit. |
| `onError` | Some error happened. |

**`captures`**

Sample of a `captures` instance returned by a flow with `FlowStep.CAPTURE_DOCUMENT`, `FlowStep.CAPTURE_FACE` and  `FlowStep.PROOF_OF_ADDRESS`:
```
Document:
        Front: DocumentSide(id=document_id, side=FRONT, type=DRIVING_LICENCE)
        Back: DocumentSide(id=document_id, side=BACK, type=DRIVING_LICENCE)
        Type: DRIVING_LICENCE
            
Face:
        Face(id=face_id, variant=PHOTO) 
        
Proof of address:
        Poa(id=poa_id, type=UTILITY_BILL, issuing_country=UK)    
```
**Note**: `type` property refers to `DocumentType`, variant refers to `FaceCaptureVariant`

**Note**: As part of `userCompleted` method, the `DocumentType` property can only contain the values which are supported by Onfido API. Please check out [our API documentation](https://documentation.onfido.com/#document-types)

**`exitCode`**

Potential `exitCode` reasons:

| `exitCode`                            |
| ------------------------------------- |
| USER_LEFT_ACTIVITY                    |
| USER_CONSENT_DENIED                   |
| CAMERA_PERMISSION_DENIED (Deprecated) |


## Customizing the SDK

You can also read our [SDK customization guide](https://developers.onfido.com/guide/sdk-customization).

### Flow customization

You can customize the flow of the SDK via the `withCustomFlow(FlowStep[])` method. You can remove, add and shift around steps of the SDK flow.

```java
final FlowStep[] defaultStepsWithWelcomeScreen = new FlowStep[]{
    FlowStep.WELCOME,                       //Welcome step with a step summary, optional
    FlowStep.CAPTURE_DOCUMENT,              //Document capture step
    FlowStep.CAPTURE_FACE,                  //Face capture step
    FlowStep.PROOF_OF_ADDRESS,              //Proof of address capture step
    FlowStep.FINAL                          //Final screen step, optional
};

final OnfidoConfig config = OnfidoConfig.builder()
    .withCustomFlow(defaultStepsWithWelcomeScreen)
    .withSDKToken("<YOUR_SDK_TOKEN>")
    .build();
```

#### Exiting the flow

You can call the `exitWhenSentToBackground()` method of the `OnfidoConfig.Builder`, to automatically exit the flow if the user sends the app to background.
This exit action will invoke the [`userExited(ExitCode exitCode)` callback](#handling-callbacks).

#### Welcome step

The welcome screen displays a summary of the capture steps the user will pass through. These steps can be specified to match the flow required. This is an optional screen.

#### Consent step

This step contains a screen to collect US end users' privacy consent for Onfido. It contains the consent language required when you offer your service to US users as well as links to Onfido's policies and terms of use. This is an optional screen.

The user must click "Accept" to move past this step and continue with the flow. The content is available in English only, and is not translatable.

⚠️ This step doesn't automatically inform Onfido that the user has given their consent. At the end of the SDK flow, you still need to set the API parameter `privacy_notices_read_consent_given` outside of the SDK flow when [creating a check](#creating-checks).

If you choose to disable this step, you must incorporate the required consent language and links to Onfido's policies and terms of use into your own application's flow before your end user starts interacting with the Onfido SDK.

For more information about this step, and how to collect user consent, please visit [Onfido Privacy Notices and Consent](http://developers.onfido.com/guide/onfido-privacy-notices-and-consent).

#### Document capture step

In this step, a user can select the issuing country and document type before taking the photo. This selection screen is optional, and will only be shown to the end user if a specific country and document type is not configured for the SDK. By default, the country will be pre-selected based on the end-user’s region settings on their device, but the end-user can select another country from the list.

Country and document type selection are optional. This screen will only show to the end user if specific options are not configured to the SDK.

You can configure the document step to capture single document types with specific properties, as well as customize the screen to display only a limited list of document types using the `DocumentCaptureStepBuilder` class's functions for the corresponding document types.

| Document Type           | Configuration function  | Configurable Properties        |
| ----------------------- | ----------------------- | ----------------------------   |
| Passport                | forPassport()           |                                |
| National Identity Card  | forNationalIdentity()   | - country<br> - documentFormat |
| Driving Licence         | forDrivingLicence()     | - country<br> - documentFormat |
| Residence Permit        | forResidencePermit()    | - country                      |
| Visa                    | forVisa()               | - country                      |
| Work Permit             | forWorkPermit()         | - country                      |
| Generic                 | forGenericDocument()    | - country                      |

**Note** `GENERIC` document type doesn't offer an optimised capture experience for a desired document type.

- **Document type**

The list of document types visible for the user to select can be shown or hidden using this option. If only one document type is specified, users will not see the selection screen and will be taken directly to the capture screen. Please see a more detailed guide [here](https://documentation.onfido.com/sdk/android/#document-capture-step).

Each document type has its own configuration class.

##### Customizing the document type selection screen

You can also customize the screen to display only a limited list of document types, using the configuration function to specify the ones you want to show.

⚠️ Currently you can only include PASSPORT, NATIONAL_IDENTITY_CARD, DRIVING_LICENCE, RESIDENCE_PERMIT in the list.

For example, to hide the Driving Licence Document type:

##### Java

```java
List<DocumentType> documentTypes = new ArrayList<>();
documentTypes.add(DocumentType.PASSPORT);
documentTypes.add(DocumentType.NATIONAL_IDENTITY_CARD);
documentTypes.add(DocumentType.RESIDENCE_PERMIT);

onfidoConfigBuilder.withAllowedDocumentTypes(documentTypes);
```

##### Kotlin

```kotlin
val documentTypes = listOf(
    DocumentType.PASSPORT,
    DocumentType.NATIONAL_IDENTITY_CARD,
    DocumentType.RESIDENCE_PERMIT
)

onfidoConfigBuilder.withAllowedDocumentTypes(documentTypes)
```

- **Document country**

The configuration function allows you to specify the document's country of origin. If a document country is specified for a document type, the selection screen is displayed with preselected country.

**Note**: You can specify country for all document types except `Passport`. This is because passports have the same format worldwide so the SDK does not require this additional information.     

For example to only capture UK driving licences:

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

- **Document format**

You can specify the format of a document as `Card` or `Folded`. `Card` is the default document format value for all document types.

If `Folded` is configured a specific template overlay is shown to the user during document capture.

**Note**: You can specify `Folded` document format for French driving licence, South African national identity and Italian national identity only. If you configure the SDK with an unsupported
country configuration the SDK will throw a `InvalidDocumentFormatAndCountryCombinationException`.

For example to only capture folded French driving licences:

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

⚠️ Not all document - country combinations are supported. Unsupported documents will not be verified. If you decide to bypass the default country selection screen by replacing the `FlowStep.CAPTURE_DOCUMENT` with a `CaptureScreenStep`, please make sure that you are specifying a supported document.
We provide an up-to-date list of our [supported documents](https://onfido.com/supported-documents/).

#### Face capture step

In this step a user can use the front camera to capture their face in the form of photo, video or motion capture.

The Face step has 3 variants:
1. To configure for photo use `FlowStep.CAPTURE_FACE` or `FaceCaptureStepBuilder.forPhoto()`.
2. To configure for video use `FaceCaptureStepBuilder.forVideo()`.
3. To configure for motion use `FaceCaptureStepBuilder.forMotion()`.

Motion variant may not be supported in certain devices due to device capabilities and Google Play Services availability.
In that case, if capture fallback provided using the `withCaptureFallback` function, it will fallback to specified capture variant which can be either photo or video.
Otherwise, the flow will end with `OnfidoException` resulting in `onError` callback.

In the following example, motion variant is configured with photo capture callback. 

```java
FlowStep faceCaptureStep = FaceCaptureStepBuilder.forMotion()
                .withCaptureFallback(
                    FaceCaptureStepBuilder.forPhoto()
                        .withIntro(showIntro)
                )
                .build();
```

**Introduction screen**

By default both face and video variants show an introduction screen. This is an optional screen. You can disable it using the `withIntro(false)` function.
Customization of introduction screen for motion variant is not available.

```java
FlowStep faceCaptureStep = FaceCaptureStepBuilder.forVideo()
                .withIntro(false)
                .build();
```

**Confirmation screen**

By default both face and video variants show a confirmation screen. To not display the recorded video on the confirmation screen, you can hide it using the `withConfirmationVideoPreview` function.

```java
FlowStep faceCaptureStep = FaceCaptureStepBuilder.forVideo()
                .withConfirmationVideoPreview(false)
                .build();
```

**Errors**

The Face step can be configured to allow only for one variant. A custom flow **cannot** contain multiple variants of the face capture. If more than one type of `FaceCaptureStep` are added to the same custom flow, a custom `IllegalArgumentException` will be thrown at the beginning of the flow,
with the message `"You are not allowed to define more than one FaceCaptureVariant in a flow."`.

#### Proof of address step

In the Proof of Address step, a user picks the issuing country and type of document that proves their address before capturing the document with their phone camera or uploading it.

#### Finish step

The final screen displays a completion message to the user and signals the end of the flow. This is an optional screen.

#### Enabling NFC extraction

Some passports and ID cards contain a chip which can be accessed using NFC. The SDK provides a set of screens to extract the information contained in the chip to verify the original document is present.

##### Prerequisites

NFC dependencies are not included in the SDK to avoid increasing the SDK size when the NFC feature is disabled. To use the NFC feature, you need to include the following dependencies (with the specified versions) in your build script:

```
implementation "net.sf.scuba:scuba-sc-android:0.0.23"
implementation "org.jmrtd:jmrtd:0.7.34"
implementation "com.madgag.spongycastle:prov:1.58.0.0"
```

##### SDK integration

##### Kotlin
```kotlin
val config = OnfidoConfig.builder(context)
    .withNFCReadFeature()
    .build()
```

##### Java
```java
OnfidoConfig config = OnfidoConfig.builder(context)
    .withNFCReadFeature()
    .build()
```

You also need to add the following Proguard rules to your `proguard-rules.pro` file:

```
-keep class org.jmrtd.** { *; }
-keep class net.sf.scuba.** {*;}
-keep class org.bouncycastle.** {*;}
-keep class org.spongycastle.** {*;}
-keep class org.ejbca.** {*;}

-dontwarn kotlin.time.jdk8.DurationConversionsJDK8Kt
-dontwarn org.ejbca.**
-dontwarn org.bouncycastle.**
-dontwarn org.spongycastle.**

-dontwarn module-info
-dontwarn org.jmrtd.**
-dontwarn net.sf.scuba.**
```

You can find further details in our [NFC for Document Report](https://developers.onfido.com/guide/document-report-nfc) guide.

### UI customization

For visualizations of the available options please see our [SDK customization guide](https://developers.onfido.com/guide/sdk-customization#android).

**Colors**

You can define custom colors inside your own `colors.xml` file:

* `onfidoColorPrimary`: Defines the background color of the `Toolbar` which guides the user through the flow

* `onfidoColorPrimaryDark`: Defines the color of the status bar above the `Toolbar`

* `onfidoTextColorPrimary`: Defines the color of the title on the `Toolbar`

* `onfidoTextColorSecondary`: Defines the color of the subtitle on the `Toolbar`

* `onfidoColorAccent`: Defines the color of the `FloatingActionButton` which allows the user to move between steps, as well as some details on the alert dialogs shown during the flow

* `onfidoPrimaryButtonColor`: Defines the background color of the primary action buttons (e.g. proceed to the next flow step, confirm picture/video, etc),
the color of the text on the secondary action buttons (e.g. retake picture/video) and the background color of some icons and markers during the flow

* `onfidoPrimaryButtonColorPressed`: Defines the background color of the primary action buttons when pressed

* `onfidoPrimaryButtonTextColor`: Defines the color of the text inside the primary action buttons

* `onfidoSecondaryButtonTextColor`: Defines the color of the text inside and border color of secondary action buttons

**Widgets**

You can customize the appearance of some widgets in your `dimens.xml` file by overriding:

* `onfidoButtonCornerRadius`: Defines the radius dimension of all the corners of primary and secondary buttons

**Typography**

You can customize the fonts by providing [font XML resources](https://developer.android.com/guide/topics/ui/look-and-feel/fonts-in-xml) to the theme by setting `OnfidoActivityTheme` to one of the following:

* `onfidoFontFamilyTitleAttr`: Defines the `fontFamily` attribute that is used for text which has typography type `Title`

* `onfidoFontFamilyBodyAttr`: Defines the `fontFamily` attribute that is used for text which has typography type `Body`

* `onfidoFontFamilySubtitleAttr`: Defines the `fontFamily` attribute that is used for text which has typography type `Subtitle`

* `onfidoFontFamilyButtonAttr`: Defines the `fontFamily` attribute that is applied to all primary and secondary buttons

* `onfidoFontFamilyToolbarTitleAttr`: Defines the `fontFamily` attribute that is applied to the title and subtitle displayed inside the `Toolbar`

* `*onfidoFontFamilyDialogButtonAttr`: Defines the `fontFamily` attribute that is applied to the buttons inside `AlertDialog` and `BottomSheetDialog`

For example:

In your application's `styles.xml`:
```xml
<style name="OnfidoActivityTheme" parent="OnfidoBaseActivityTheme">
        <item name="onfidoFontFamilyTitleAttr">@font/montserrat_semibold</item>
        <item name="onfidoFontFamilyBodyAttr">@font/font_montserrat</item>

        <!-- You can also make the dialog buttons follow another fontFamily like a regular button -->
        <item name="onfidoFontFamilyDialogButtonAttr">?onfidoFontFamilyButtonAttr</item>

        <item name="onfidoFontFamilySubtitleAttr">@font/font_montserrat</item>
        <item name="onfidoFontFamilyButtonAttr">@font/font_montserrat</item>
        <item name="onfidoFontFamilyToolbarTitleAttr">@font/font_montserrat_semibold</item>
</style>
```

### Localization

The Onfido Android SDK supports and maintains translations for the following locales:

- English    (en) 🇬🇧
- Spanish    (es) 🇪🇸
- French     (fr) 🇫🇷
- German     (de) 🇩🇪
- Italian    (it) 🇮🇹
- Portuguese (pt) 🇵🇹
- Dutch      (nl) 🇳🇱
- Polish     (pl) 🇵🇱
- Romanian   (ro) 🇷🇴
- Czech      (cs) 🇨🇿

**Custom language**

The Android SDK also allows for the selection of a specific custom language for locales that Onfido does not currently support. You can have an additional XML strings file inside your resources folder for the desired locale (for example, `res/values-it/onfido_strings.xml` for 🇮🇹 translation), with the content of our [strings.xml](strings.xml) file, translated for that locale.

When adding custom translations, please make sure you add the whole set of keys we have on [strings.xml](strings.xml). In particular, `onfido_locale`, which identifies the current locale being added, must be included.
The value for this string should be the [ISO 639-1](http://www.loc.gov/standards/iso639-2/php/code_list.php) 2-letter language code corresponding to the translation being added.
Examples:
    - When adding a translations file inside `values-ru` (russian translation), the `onfido_locale` key should have `ru` as its value
    - When adding a translations file inside `values-en-rUS` (american english translation), the `onfido_locale` key should have `en` as its value

Without `onfido_locale` correctly included, we won't be able to determine which language the user is likely to use when doing the video liveness challenge. It may result in our inability to correctly process the video, and the check may fail.

By default, we infer the language to use from the device settings. However, you can also use the `withLocale(Locale)` method of the `OnfidoConfig.Builder` to select a specific language.

**Note**: If the strings translations change it will result in a minor version change. If you have custom translations you're responsible for testing your translated layout.

If you want a locale translated you can get in touch with us at [android-sdk@onfido.com](mailto:android-sdk@onfido.com).

## Custom Callbacks

### Media Callbacks BETA

#### Introduction
Onfido provides the possibility to integrate with our Smart Capture SDK, without the requirement of using this data only through the Onfido API. Media callbacks enable you to control the end user data collected by the SDK after the end user has submitted their captured media. As a result, you can leverage Onfido’s advanced on-device technology, including image quality validations, while still being able to handle end users’ data directly. This unlocks additional use cases, including compliance requirements and multi-vendor configurations, that require this additional flexibility.
This feature must be enabled for your account. Please contact your Onfido Solution Engineer or Customer Success Manager.

#### Implementation
To use this feature use `.withMediaCallback` and provide the callbacks for `DocumentResult`, `SelfieResult` and `LivenessResult`.

##### Java
```java
onfidoConfigBuilder.withMediaCallback(new CustomMediaCallback());
private static class CustomMediaCallback implements MediaCallback {

    @Override
    public void onMediaCaptured(@NonNull MediaResult result) {
        if (result instanceof DocumentResult) {
            // Your callback code here
        } else if (result instanceof LivenessResult) {
            // Your callback code here
        } else if (result instanceof SelfieResult) {
            // Your callback code here
        }
    }
}
```

**⚠️ Note:** Don’t forget to set the inner class to static if outer class is not `Serializable`

##### Kotlin:
```kotlin
onfidoConfigBuilder
    .withMediaCallback { mediaResult ->
        when(mediaResult){
            is DocumentResult -> // Your callback code here
            is SelfieResult -> // Your callback code here
            is LivenessResult -> // Your callback code here
        }
    }
```
**⚠️ Note:** Don’t forget to use a nested class if the outer class is not `Serializable`

#### User data
The callbacks return an object including the information that the SDK normally sends directly to Onfido. The callbacks are invoked when the end user confirms submission of their image through the SDK’s user interface.
Note: Currently, end user data will still automatically be sent to the Onfido backend. You are not required to use Onfido to process this data.
##### Documents
For documents the callback returns a `DocumentResult` object:
```json5
{
    fileData: MediaFile
    documentMetadata: DocumentMetadata
}
```
The `DocumentMetadata` object contains the metadata of the captured document.

```json5
{
   side: String,
   type: String,
   issuingCountry: String
}
```

**⚠️ Note:** `issuingCountry` is optional based on end-user selection, and can be `null`

**⚠️ Note:** If a document was scanned using NFC, the callback will only return the `MediaFile`.

##### Live photos and videos
For live photos the callback returns a `SelfieResult` object:
```json5
{
  fileData: MediaFile
}
```

For live videos the callback returns a `LivenessResult` object:
```json5
{
  fileData: MediaFile
}
```

The `MediaFile` object contains the raw data and MIME type of the captured photo or video.
```json5
{
  fileData: ByteArray,
  fileType: String
}
```
##### Create a check with Onfido
After receiving the user data from the SDK, you can choose to [create a check](#creating-checks) with Onfido. In this case, you don’t need to re-upload the end user data as it is sent automatically from the SDK to the Onfido backend.

Please see our [API documentation](https://documentation.onfido.com/#create-check) for more information on how to create a check.


## Creating checks

The SDK is responsible for the capture of identity documents and selfie photos, videos and motion captures. It doesn't perform any checks against the Onfido API. You need to access the [Onfido API](https://documentation.onfido.com/) in order to manage applicants and perform checks.

For a walkthrough of how to create a check with a Document and Facial Similarity report using the Android SDK read our [Mobile SDK Quick Start guide](https://developers.onfido.com/guide/mobile-sdk-quick-start).

Read our API documentation for further details on how to [create a check](https://documentation.onfido.com/#create-check) with the Onfido API.

**Note**: If you're testing with a sandbox token, please be aware that the results are pre-determined. You can learn more about [sandbox responses](https://documentation.onfido.com/#pre-determined-responses).

**Note**: If you're using API v2, please refer to the [API v2 to v3 migration guide](https://developers.onfido.com/guide/v2-to-v3-migration-guide#checks-in-api-v3) for more information.

### Setting up webhooks

Reports may not return results straightaway. You can set up [webhooks](https://documentation.onfido.com/#webhooks) to be notified upon completion of a check or report, or both.

## User Analytics

The SDK allows you to track a user's progress through the SDK via an overrideable hook. This gives insight into how your users make use of the SDK screens.

### Overriding the hook

In order to expose a user's progress through the SDK an hook method must be overridden using `OnfidoConfig.Builder`. You can do this when initializing the Onfido SDK. For example: 

Java:
```java
// Place your listener in a separate class file or make it a static class

class OnfidoEventListener implements OnfidoAnalyticsEventListener {

    private final Context applicationContext;
    private final Storage storage;
        
    OnfidoEventListener(Context applicationContext, Storage storage) {
        this.applicationContext = applicationContext;
        this.storage = storage;
    }

    @Override
    public void onEvent(@NonNull OnfidoAnalyticsEvent event) {
        // Your tracking or persistence code
        // You can persist the events to storage and track them once the SDK flow is completed or exited with an error
        // This appraoch can help to scope your potential network calls to the licecycle of your activity or fragment
        // storage.addToList("onfidoEvents", event);
    }
}
private static final int ONFIDO_FLOW_REQUEST_CODE = 100;
OnfidoConfig onfidoConfig = OnfidoConfig.builder(applicationContext)
    .withAnalyticsEventListener(new OnfidoEventListener(applicationContext, storage))
    .build();
Onfido.startActivityForResult(this, ONFIDO_FLOW_REQUEST_CODE, onfidoConfig);
```

Kotlin:
```kotlin
// Place your listener in a separate class file

class OnfidoEventListener(
    private val applicationContext: Context,
    private val storage: Storage
) : OnfidoAnalyticsEventListener {

    override fun onEvent(event: OnfidoAnalyticsEvent) {
        // Your tracking or persistence code
        // You can persist the events to storage and track them once the SDK flow is completed or exited with an error
        // This appraoch can help to scope your potential network calls to the licecycle of your activity or fragment
        // storage.addToList("onfidoEvents", event)
    }
}

companion object {
    private const val ONFIDO_FLOW_REQUEST_CODE = 100
}

val onfidoConfig = OnfidoConfig.builder(applicationContext)
    .withAnalyticsEventListener(new OnfidoEventListener(applicationContext, storage))
    .build()
Onfido.startActivityForResult(this, ONFIDO_FLOW_REQUEST_CODE, onfidoConfig)
```

The code inside the overridden method will now be called when a particular event is triggered, usually when the user reaches a new screen. Please use a static or separate class instead of a lambda or an anonymous inner class to avoid leaking the outer class, e.g. Activity or Fragment. Also refrain from using Activity or Fragment context references in your listener to prevent memory leaks and crashes. If you need access to a context object, you can inject your application context in the constructor of your listener as shown in the above example. As a better appraoch, you can wrap your application context in a  single-responsibility class (such as `Storage` or `APIService`) and  inject it in your listener, as shown in the example.

**Note:**

`UserEventHandler` is deprecated now, if you are upgrading from a previous Onfido SDK version, please migrate to `OnfidoAnalyticsEventListener` and remove you existing listener (`Onfido.userEventHandler`) otherwise you will get duplicated events (from both the legacy event handler and the new event listener). 


For a full list of events see [TRACKED_EVENTS.md](TRACKED_EVENTS.md).

| property | description |
| ---- | ----- |
|`type` | **OnfidoAnalyticsEventType** <br /> Indicates the type of event. Potential values (enum instances) are `FLOW`, `SCREEN`, `ACTION`, `ERROR`.|
| `properties` | **Map<OnfidoAnalyticsPropertyKey, String?>** <br /> Contains details of an event. For example, you can get the name of the visited screen using the `SCREEN_NAME` property. The current potential property keys are: `SCREEN_NAME`, `SCREEN_MODE`, `DOCUMENT_TYPE`, `COUNTRY_CODE`, `VIDEO_CHALLENGE_TYPE`, `IS_AUTOCAPTURE`.|

### Properties

#### SCREEN_NAME
The name of the visited screen, e.g. `WELCOME`, `DOCUMENT_CAPTURE`, etc. 

#### SCREEN_MODE
Screen orientation in json, potential values are "portrait" or "landscape".

#### DOCUMENT_TYPE
Type of the selected document for capture, e.g. `passport`, `national_id`, `driving_licence`, etc. 

#### COUNTRY_CODE
The 2-letter iso code of the selected country, e.g. `US`, `UK`, `DE`, etc,  

#### DOCUMENT_FORMAT
Format of the document to capture, used in the `DOCUMENT_CAPTURE` event. Possible values are `card` and `folded`.

#### COUNTRY_CODE
The ISO code of the selected country, used in the `COUNTRY_SELECTION` event. 

#### VIDEO_CHALLENGE_TYPE
Type of the displayed liveness video challenge, e.g. `recite`, `movement`.

#### IS_AUTOCAPTURE
Whether or auto-capture was used.  


### Using the data

You can use the data to keep track of how many users reach each screen in your flow. You can do this by storing the number of users that reach each screen and comparing that to the number of users who reached the `Welcome` screen.

## Going live

Once you are happy with your integration and are ready to go live, please contact [Client Support](mailto:client-support@onfido.com) to obtain a live API token. You'll have to replace the sandbox tokens in your code with live tokens.

Check the following before you go live:

- you have set up [webhooks](https://documentation.onfido.com/#webhooks) to receive live events
- you have entered correct billing details inside your [Onfido Dashboard](https://onfido.com/dashboard/)

## Cross platform frameworks

We provide integration guides and sample applications to help customers integrate the Onfido Android SDK with applications built using the following cross-platform frameworks:

- [Xamarin](https://github.com/onfido/onfido-xamarin-sample-app)
- [React Native](https://github.com/onfido/onfido-sdk-react-native-sample-app)

We don't have out-of-the-box packages for such integrations yet, but these projects show complete examples of how our Android SDK can be successfully integrated in projects targeting these frameworks.
Any issues or questions about the existing integrations should be raised on the corresponding repository and questions about further integrations should be sent to [android-sdk@onfido.com](mailto:android-sdk@onfido.com).

## Migrating

You can find the migration guide in the [MIGRATION.md](MIGRATION.md) file.

## Security

### Certificate Pinning

You can pin any communication between our SDK and server through the `.withCertificatePinning()` method in
our `OnfidoConfig.Builder` configuration builder. This method accepts as a parameter an `Array<String>` with sha-1/sha-256 hashes of the certificate's public keys.

For more information about the hashes, please email [android-sdk@onfido.com](mailto:android-sdk@onfido.com).

## Accessibility

The Onfido Android SDK has been optimised to provide the following accessibility support by default:

- Screen reader support: accessible labels for textual and non-textual elements available to aid TalkBack navigation, including dynamic alerts
- Dynamic font size support: all elements scale automatically according to the device's font size setting
- Sufficient color contrast: default colors have been tested to meet the recommended level of contrast
- Sufficient touch target size: all interactive elements have been designed to meet the recommended touch target size

Refer to our [accessibility statement](https://developers.onfido.com/guide/sdk-accessibility-statement) for more details.

## Licensing

Due to API design constraints, and to avoid possible conflicts during the integration, we bundle some of our 3rd party dependencies as repackaged versions of the original libraries.
For those, we include the licensing information inside our `.aar`, namely on the `res/raw/onfido_licenses.json`.
This file contains a summary of our bundled dependencies and all the licensing information required, including links to the relevant license texts contained in the same folder.
Integrators of our library are then responsible for keeping this information along with their integrations.

## More information

### Sample App

We have included a [sample app](sample-app) to show how to integrate the Onfido SDK.

### API Documentation

Further information about the Onfido API is available in our [API reference](https://documentation.onfido.com).

### Support

Please open an issue through [GitHub](https://github.com/onfido/onfido-android-sdk/issues). Please be as detailed as you can. Remember **not** to submit your token in the issue. Also check the closed issues to see whether it has been previously raised and answered.

If you have any issues that contain sensitive information please send us an email with the `ISSUE:` at the start of the subject to [android-sdk@onfido.com](mailto:android-sdk@onfido.com).

Previous version of the SDK will be supported for a month after a new major version release. Note that when the support period has expired for an SDK version, no bug fixes will be provided, but the SDK will keep functioning (until further notice).

Copyright 2018 Onfido, Ltd. All rights reserved.
