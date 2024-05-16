# Onfido Smart Capture Android SDK

[![Download](https://maven-badges.herokuapp.com/maven-central/com.onfido.sdk.capture/onfido-capture-sdk/badge.png)](https://search.maven.org/artifact/com.onfido.sdk.capture/onfido-capture-sdk/)
![Build Status](https://app.bitrise.io/app/0d3fe90349e46fbe/status.svg?token=6GpMhK-XJU_9kWRuHzkLmA&branch=master)

## Table of contents

* [1. Overview](#overview)
* [2. Adding the SDK dependency](#adding-the-sdk-dependency)
* [3. Initializing the SDK](#initializing-the-sdk)
* [4. Completing a session](#completing-a-session)
* [Advanced flow customization](#advanced-flow-customization)
* [Advanced callbacks](#advanced-callbacks)
* [User Analytics](#user-analytics)
* [Cross platform frameworks](#cross-platform-frameworks)
* [Migrating](#migrating)
* [Security](#security)
* [Accessibility](#accessibility)
* [Licensing](#licensing)
* [More information](#more-information)
* [Raising support issues](#support)

## Overview

The Onfido Smart Capture SDKs provide a set of screens and functionalities that enable applications to implement user identity verification flows. Each SDK contains:

- Carefully designed UX to guide your customers through the different photo or video capture processes
- Modular design to help you seamlessly integrate the different photo or video capture processes into your application's flow
- Advanced image quality detection technology to ensure the quality of the captured images meets the requirement of the Onfido identity verification process, guaranteeing the best success rate
- Direct image upload to the Onfido service, to simplify integration
- A suite of advanced fraud detection signals to protect against malicious users

All Onfido Smart Capture SDKs are orchestrated using [Onfido Studio](https://developers.onfido.com/guide/onfido-studio-product) workflows, with only minor customization differences between the available platforms.

The Onfido Android SDK is specifically designed for integrating Android applications.

![Various views from the SDK](screenshots.jpg)
![Various views from the SDK](gifs.gif)

### Environments and testing with the SDK

Two environments exist to support the Onfido SDK integrations:

- 'sandbox' - to be used for testing with sample documents
- 'live' - to be used only with real documents and in production apps

The environment being used is determined by the API token that is used to generate the necessary [SDK token](#sdk-authentication).

### Going Live

Once you are satisfied with your integration and are ready to go live, please contact [client-support@onfido.com](mailto:client-support@onfido.com) to obtain a live API token. You will have to replace the sandbox token in your code with the live token.

Check that you have entered correct billing details inside your [Onfido Dashboard](https://onfido.com/dashboard/), before going live.

## Adding the SDK dependency 

The SDK supports API level 21 and above ([refer to the distribution stats](https://developer.android.com/about/dashboards/index.html)).

[Version 7.4.0](https://github.com/onfido/onfido-android-sdk/releases/tag/7.4.0) of the SDK was the last version that supported API level 16 and above.

Our configuration is currently set to the following:

- `minSdkVersion = 21`
- `targetSdkVersion = 31`
- `android.useAndroidX=true`
- `Kotlin = 1.7.10+`
```
  compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
  }
```

Starting from version `4.2.0` of the Android SDK, Onfido caters to different integration needs by offering a comprehensive "full" version, as well as a more lightweight "core" version. As such, you can integrate the SDK in one of two ways:

1. `onfido-capture-sdk`
2. `onfido-capture-sdk-core`

### 1. `onfido-capture-sdk`

This is the **recommended** integration option.

This is a complete solution, focusing on input quality. It features advanced on-device, real-time glare and blur detection as well as auto-capture (passport only) on top of a set of basic image validations.

```gradle
repositories {
  mavenCentral()
}

dependencies {
  implementation 'com.onfido.sdk.capture:onfido-capture-sdk:x.y.z'
}
```

Due to the advanced validation support (in C++ code), we recommend that the integrator app performs [multi-APK split](#multi-apk-split) to optimize the app size for individual architectures.

Average size (with Proguard enabled):

| ABI         |   Size   |
|-------------|:--------:|
| armeabi-v7a | 13.31 Mb  |
| arm64-v8a   | 13.25 Mb |
| universal   | 21.20 Mb |

### 2. `onfido-capture-sdk-core`

This is a lighter version. It provides a set of basic image validations, mostly completed on the backend. There are no real-time validations on-device for document capture.
However, since our face capture utilizes NDK for advanced face recognition and yaw calculation, we still recommend [multi-APK split](#multi-apk-split) to optimize the app size for individual architectures.


```gradle
repositories {
  mavenCentral()
}

dependencies {
  implementation 'com.onfido.sdk.capture:onfido-capture-sdk-core:x.y.z'
}
```

Average size (with Proguard enabled):

| ABI              |   Size   |
|------------------|:--------:|
| core-armeabi-v7a | 10.20 Mb  |
| core-arm64-v8a   | 9.97 Mb  |
| core-universal   | 14.82 Mb |


**Note**: The average sizes were measured by building the minimum possible wrappers around our SDK,
using the following [stack](https://github.com/bitrise-io/bitrise.io/blob/master/system_reports/linux-docker-android-lts.log).
Different versions of the dependencies, such as Gradle or NDK, may result in slightly different values.

**Note**: To improve the security of our clients, our infrastructure and SDK client SSL configurations support TLSv1.2+ only.
According to the relevant [Google documentation](https://developer.android.com/reference/javax/net/ssl/SSLSocket.html), this support comes enabled by default on every device running Android API 20+.
If you need to support older devices, we need to access Google Play Services to install the latest security updates which enable this support.
If you don't use Google Play Services on your integration yet, we require you to add the following dependency:

```gradle
compile ('com.google.android.gms:play-services-base:x.y.z') {
           exclude group: 'com.android.support' // to avoid conflicts with your current support library
}
```

### Multi-APK split

C++ code needs to be compiled for each of the CPU architectures (known as "ABIs") present on the Android environment. Currently, the SDK supports the following ABIs:

* `armeabi-v7a`: Version 7 or higher of the ARM processor. Most recent Android phones use this
* `arm64-v8a`: 64-bit ARM processors. Found on new generation devices
* `x86`: Used by most tablets and emulators
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

**Note**: To further reduce the footprint of the SDK beyond the lightweight `onfido-capture-sdk-core`, you can integrate the Android SDK as a Dynamic Feature Module. You'll find more details about this in the dedicated section below. 

## Initializing the SDK

The Android SDK has multiple initialization and customization options that provide flexibility to your integration, while remaining easy to integrate.

### Defining a workflow

Onfido Studio is the platform used to create highly reusable identity verification workflows for use with the Onfido SDKs. For an introduction to working with workflows, please refer to our [Getting Started guide](https://developers.onfido.com/guide/general-introduction), or the Onfido Studio [product guide](https://developers.onfido.com/guide/onfido-studio-product).

SDK sessions are orchestrated by a session-specific `workflow_run_id`, itself derived from a `workflow_id`, the unique identifier of a given workflow.

For details on how to generate a `workflow_run_id`, please refer to the `POST /workflow_runs/` endpoint definition in the Onfido [API reference](https://documentation.onfido.com/#workflow-runs).

<Callout type="warning">

> **Note** that in the context of the SDK, the `workflow_run_id` property is referred to as `workflowRunId`.

</Callout>

#### Applicant ID reuse

When defining workflows and creating identity verifications, we highly recommend saving the `applicant_id` against a specific user for potential reuse. This helps to keep track of users should you wish to run multiple identity verifications on the same individual, or in scenarios where a user returns to and resumes a verification flow.

### SDK authentication

The SDK is authenticated using SDK tokens. As each SDK token must be specific to a given applicant and session, a new token must be generated each time you initialize the Onfido Android SDK.

For details on how to generate SDK tokens, please refer to `POST /sdk_token/` definition in the Onfido [API reference](https://documentation.onfido.com/#generate-sdk-token).

**Note**: You must never use API tokens in the frontend of your application as malicious users could discover them in your source code. You should only use them on your server.

#### `tokenExpirationHandler`

It's important to note that SDK tokens expire after 90 minutes.

With this in mind, we recommend you use the optional `tokenExpirationHandler` parameter in the SDK token configuration function to generate and pass a new SDK token when it expires. This ensures the SDK continues its flow even after an SDK token has expired. You should inject a new token in 10 seconds after the callback is triggered, otherwise the flow will finish with a `TokenExpiredException` error.

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

### Build a configuration object

To use the SDK, you need to obtain an instance of the client object, using your generated SDK token and workflow run ID.

##### Kotlin

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

### Start the flow

##### Kotlin

```kotlin
onfidoWorkflow = OnfidoWorkflow.create(this)
startActivityForResult(onfidoWorkflow.createIntent(workflowConfig), REQUEST_CODE)
```

### Custom Application Class

**Note**: You can skip this step if you don't have any custom application class.

**Note**: Following the release of [version 17.0.0](https://documentation.onfido.com/sdk/android/#no--1700---1800), the Android SDK runs in a separate process. This means that when the SDK gets started, a new application instance will be created. To prevent re-executing the initializations you have in the Android application class, you can use the `isOnfidoProcess` extension function and return from `onCreate` as shown below.

This will prevent initialization-related crashes such as: [`FirebaseApp is not initialized in this process`](https://github.com/firebase/firebase-android-sdk/issues/4693)

The `isOnfidoProcess` extension function has been integrated into the Application class to prevent accidental reinitialization of instances within custom application classes. This feature is especially useful for optimizing the Onfido initialization process. Be aware that the Onfido process uses your custom Application class for its own initialization. If you decide to use `isOnfidoProcess` to selectively skip the initialization of certain instances during the Onfido process, be cautious not to access these uninitialized instances elsewhere in your Application class, such as in the `onTrimMemory` method, to avoid unexpected behavior. Furthermore, instances initialized by providers like Firebase will not be reinitialized in the Onfido process. If you wish to use such instances within the Onfido process, you'll need to manually initialize them. More info can be found [here](https://firebase.google.com/docs/reference/android/com/google/firebase/FirebaseApp#initializeApp(android.content.Context)).

##### Kotlin

```kotlin
class YourCustomApplication : MultiDexApplication() {
	override fun onCreate() {
	    super.onCreate()
	    if (isOnfidoProcess()) {
	        return
	    }
	    
	    // Your custom initialization calls ...
	 }
}

```

##### Java

```java
public class YourCustomApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        if (Onfido.Companion.isOnfidoProcess(this)) {
            return;
        }

        // Your custom initialization calls ...
    }
}
```

### Dynamic Feature Module (Beta)

You can also integrate Onfido's Android SDK using a Dynamic Feature Module. The advantage of this configuration is that it reduces the SDK size to essentialy zero, as it is only initialized at run time.

To configure the Dynamic Feature Module, follow these steps:

1. Create a Dynamic Feature module
2. Import the Onfido SDK `onfido-workflow` or `onfido-capture-sdk-core` as a library in the dynamic module
   * Customers can decide how the module will be installed and this won’t affect Onfido SDK behaviour
   * Configure [install-time delivery](https://developer.android.com/guide/playcore/feature-delivery/install-time)
   * Configure [conditional delivery](https://developer.android.com/guide/playcore/feature-delivery/conditional)
   * Configure [on-demand delivery](https://developer.android.com/guide/playcore/feature-delivery/on-demand)
3. Import the Onfido SDK API modules in your base-module (or any other module where you had Onfido configurations previously)
   * For Onfido Studio `onfido-workflow-api`
   * For manual verification configuations only `onfido-public-api`
4. Before launching the Onfido SDK, make sure your dynamic feature is [installed and ready](https://developer.android.com/guide/playcore/feature-delivery/on-demand#manage_installed_modules) to be used
5. Launch the Onfido SDK normally. The API will take care of launching the SDK correctly, if the Workflow or Capture libraries are pulled in a Dynamic Feature Module

### UI customization

The Android SDK supports the customization of colors, buttons, icons, fonts, widgets and strings used in the SDK flow.

#### Appearance and Colors

You can customize colors and other appearance attributes by overriding Onfido themes (`OnfidoActivityTheme` 
and `OnfidoDarkTheme`) in your `themes.xml` or `styles.xml` files.
Make sure to set `OnfidoBaseActivityTheme` as the parent of `OnfidoActivityTheme` and  `OnfidoBaseDarkTheme` as the parent of `OnfidoDarkTheme` in your style definition.

All colors referenced in the themes should be defined in your `colors.xml` file.  Alternatively, you can use hexadecimal 
color values directly in the themes. When customizing fonts, all referenced fonts must be added to your project first. 
Further instructions for adding fonts can be found in the [Android documentation](https://developer.android.com/develop/ui/views/text-and-emoji/fonts-in-xml).


For example, you can add these themes to your `themes.xml` to change the toolbar background and primary buttons' 
color:

```xml
<!-- Light theme -->
<style name="OnfidoActivityTheme" parent="OnfidoBaseActivityTheme">
    <item name="onfidoColorToolbarBackground">@color/brand_dark_blue</item>
    <item name="onfidoColorActionMain">@color/brand_accent_color</item>
</style>

<!-- Dark theme -->
<style name="OnfidoDarkTheme" parent="OnfidoBaseDarkTheme">
    <item name="onfidoColorToolbarBackground">@color/brand_dark_blue</item>
    <item name="onfidoColorActionMain">@color/brand_accent_color</item>
</style>
```

For a complete list and visualizations of the customizable attributes, refer to our [SDK customization guide](https://developers.onfido.com/guide/sdk-customization).

#### Dark theme

Starting from [version 19.1.0](https://documentation.onfido.com/sdk/android/#no--1900---1910), the Android SDK supports the dark theme. By default, the user's active device theme will be 
automatically applied to the Onfido SDK. However, you can opt out from dynamic theme switching at run time 
and instead set a theme statically at the build time as shown below. In this case, the flow will always be displayed 
in the selected theme regardless of the user's device theme.

To force select dark theme:

`onfidoConfigBuilder.withTheme(OnfidoTheme.DARK)`

To force select light theme:

`onfidoConfigBuilder.withTheme(OnfidoTheme.LIGHT)`

You can also automatically use the user's device theme:

`onfidoConfigBuilder.withTheme(OnfidoTheme.AUTOMATIC)`

#### Widgets

You can customize the appearance of some widgets in your `dimens.xml` file by overriding `onfidoButtonCornerRadius`, which defines the radius dimension of all the corners of primary and secondary buttons.

#### Typography

You can customize the SDK's fonts by providing [font XML resources](https://developer.android.com/guide/topics/ui/look-and-feel/fonts-in-xml) to the theme by setting the `OnfidoActivityTheme` attribute.

For example:

In your application's `styles.xml` file:
```xml
<style name="OnfidoActivityTheme" parent="OnfidoBaseActivityTheme">
        <item name="onfidoFontFamilyTitle">@font/montserrat_semibold</item>
        <item name="onfidoFontFamilyBody">@font/font_montserrat</item>

        <!-- You can also make the dialog buttons follow another fontFamily like a regular button -->
        <item name="onfidoFontFamilyDialogButton">?onfidoFontFamilyButton</item>

        <item name="onfidoFontFamilySubtitle">@font/font_montserrat</item>
        <item name="onfidoFontFamilyButton">@font/font_montserrat</item>
        <item name="onfidoFontFamilyToolbarTitle">@font/font_montserrat_semibold</item>
</style>
```

### Language localization

The Onfido SDK supports and maintains translations for over 40 languages.

To configure a specific localization, you can use the `withLocale(Locale)` method of the `OnfidoConfig.Builder` to select a language. For example, to configure for French:

```java
final OnfidoConfig config = OnfidoConfig.builder()
    .withLocale(fr)
    .build();
```

**Note**: If no language is selected, the SDK will detect and use the end user's device language setting. If the device's language is not supported, the SDK will default to English (`en_US`).

For the full list of languages supported by Onfido, please refer to our [SDK customization guide](https://developers.onfido.com/guide/sdk-customization#language-customization).


**Custom language**

The Android SDK also allows for the selection of a specific custom language for locales that Onfido does not currently support. You can have an additional XML strings file inside your resources folder for the desired locale (for example, `res/values-it/onfido_strings.xml` for 🇮🇹 translation), with the content of our [strings.xml](strings.xml) file, translated for that locale.

When adding custom translations, please make sure you add the whole set of keys we have on [strings.xml](strings.xml). 

**Note**: If the strings translations change, it will result in a minor version change. If you have custom translations, you're responsible for testing your translated layout.

If you want a locale translated, you can get in touch with us at [android-sdk@onfido.com](mailto:android-sdk@onfido.com).

### NFC capture using Onfido Studio

Recent passports, national identity cards and residence permits contain a chip that can be accessed using Near Field Communication (NFC). The Onfido SDKs provide a set of screens and functionalities to extract this information, verify its authenticity and provide the resulting verification as part of a Document report.

From version [18.1.0](https://github.com/onfido/onfido-android-sdk/blob/master/MIGRATION.md#1800---1900) onwards of the Onfido Android SDK, NFC is enabled by default and offered to end users when both the document and the device support NFC.

For more information on how to configure NFC and the list of supported documents, please refer to the [NFC for Document Report](https://developers.onfido.com/guide/document-report-nfc) guide.

## Completing a session

### Handling callbacks

When the Onfido SDK session concludes, a range of callback functions may be triggered.

For advanced callbacks used for user analytics and returning submitted media, please refer to the [Advanced Callbacks](#advanced-callbacks) section of this document.

Available callback functions include:

| Attribute     |    Notes    |
| -----|-------|
| `onUserCompleted` | Callback that fires when all interactive tasks in the workflow have been completed. If you have configured [webhooks](https://documentation.onfido.com/#webhooks), a notification will be sent to your backend confirming the workflow run has finished. You do not need to create a check using your backend as this is handled directly by the workflow. |
| `onUserExited` | Callback that fires when the workflow was exited prematurely by the user. The reason can be an exitCode, e.g `USER_CONSENT_DENIED`. |
| `onException` | In case of an unexpected error, the onException method will be invoked with a relevant error message in the `WorkflowException` object. Error messages are not in a presentable format to the end user and are not localized. |

To receive the result from the flow, you should override the method `onActivityResult` on your Activity or Fragment. The following code is provided as an example:

##### Kotlin

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    onfidoWorkflow.handleActivityResult(resultCode, data, object : OnfidoWorkflow.ResultListener {
        override fun onUserCompleted() {
            // Called when the entire workflow run has reached the terminal node.
        }

        override fun onUserExited(exitCode: ExitCode) {
            // Called when the user has exited the flow before reaching the terminal node.
        }

        override fun onException(exception: OnfidoWorkflow.WorkflowException) {
            // Called when the flow has ended with an exception
        }
    })
}
```

### Generating verification reports

While the SDK is responsible for capturing and uploading the user's media and data, identity verification reports themselves are generated based on workflows created using [Onfido Studio](https://developers.onfido.com/guide/onfido-studio-product).

For a step-by-step walkthrough of creating an identity verification using Onfido Studio and our SDKs, please refer to our [Quick Start Guide](https://developers.onfido.com/guide/quick-start-guide).

If your application initializes the Onfido Android SDK using the options defined in the [Advanced customization](#advanced-flow-customization) section of this document, you may [create checks](https://documentation.onfido.com/#create-check) and [retrieve report results](https://documentation.onfido.com/#retrieve-report) manually using the Onfido API.
You may also configure [webhooks](https://documentation.onfido.com/#webhooks) to be notified asynchronously when the report results have been generated.

## Advanced flow customization

This section on 'Advanced customization' refers to the process of initializing the Onfido Android SDK without the use of Onfido Studio. This process requires a manual definition of the verification steps and their configuration.

The `FlowStep` parameter is mutually exclusive with `workflowRunId`, requiring an alternative method of instantiating the client and starting the flow.

**Note** that this initialization process is **not recommended** as the majority of new features are exclusively released for Studio workflows.

### Instantiate the client

To use the SDK, you need to obtain an instance of the client object.

```java
final Context context = ...;
Onfido onfido = OnfidoFactory.create(context).getClient();
```

### Start the flow

You can then use the client object to start the flow:

```java
// start the flow. 1 should be your request code (customize as needed)
onfido.startActivityForResult(this,         /*must be an Activity or Fragment (support library)*/
                              1,            /*this request code will be important for you on onActivityResult() to identify the onfido callback*/
                              config);
```

### Flow customization

You can customize the flow of the SDK via the `withCustomFlow(FlowStep[])` method. You can add, remove and shift around steps of the SDK flow.

The possible flow steps include:

| Step | Description |
| --------- | --------- |
| `FlowStep.WELCOME` | Welcome screen shown to the user with preliminary instructions. [Customization options](#welcome-step) include modification to the text elements and instructions shown to the user. |
| `FlowStep.CAPTURE_DOCUMENT` | Set of screens that control the capture via photo of the user's document. Numerous [customization options](#document-capture-step) are available to define the document list presented to the user and the overall capture experience. |
| `FlowStep.CAPTURE_FACE` | Set of screens that control the capture of a selfie, video or motion of the user. The [customization options](#face-capture-step) allow the selection of the capture variant. |
| `FlowStep.PROOF_OF_ADDRESS` | Screen where the user selects the issuing country and type of document that [verifies their address](#proof-of-address-step). |
| `FlowStep.FINAL` | Screen shown to the user at the end of the flow. [Customization options](#finish-step) include modifications to the text elements shown to the user. |

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

You can call the `exitWhenSentToBackground()` method of the `OnfidoConfig.Builder`, to automatically exit the flow if the user sends the app to the background.
This exit action will invoke the [`userExited(ExitCode exitCode)` callback](#handling-callbacks).

#### Welcome step

This step is the introduction screen of the SDK. It introduces the process and prepares the user for the steps they will need to complete.

While this screen is **optional**, we only recommend its removal if you already have your own identity verification welcome screen in place.

#### Consent step

This step contains the consent language required when you offer your service to US users, as well as links to Onfido's policies and terms of use. For applicants created with a [`location`](https://documentation.onfido.com/#location-create-applicant) parameter value of the United States, consent collection is **mandatory**.

The user must click "Accept" to move past this step and continue with the flow. The content is available in English only, and is not translatable.

**Note**: This step does not automatically inform Onfido that the user has given their consent:
- When creating checks using API v3.3 or lower, you need to set the value of the API parameter `privacy_notices_read_consent_given` (now deprecated) at the end of the SDK flow when [creating a check](https://documentation.onfido.com/v3.3/#create-check)
- From API v3.4 onwards, user consent is confirmed when [creating](https://documentation.onfido.com/#create-applicant) or [updating](https://documentation.onfido.com/#update-applicant) an applicant using the [consents](https://documentation.onfido.com/#consents) parameter

If you choose to disable Onfido’s SDK Consent step, you **must** still incorporate the required consent language and links to Onfido's policies and terms of use into your own application's flow before your users start interacting with the Onfido SDK.

For more information about this step, and how to collect user consent, please visit [Onfido Privacy Notices and Consent](http://developers.onfido.com/guide/onfido-privacy-notices-and-consent).

#### Document capture step

In the Document Capture step, an end user can select the issuing country and document type before capture. In a very limited number of cases, the end user may also be asked if they have a card or paper version of their document.

This information is used to optimize the capture experience, as well as inform the end user about which documents they are allowed to use.

This selection screen is dynamic, and will be automatically hidden where the end user is not required to indicate which
document will be captured.

You can specify allowed issuing countries and document types for the document capture step in one of three ways: 
*   If you are using Onfido Studio, this is configured within a Document Capture task, documented in the [Studio Product Guide](https://developers.onfido.com/guide/onfido-studio-product#document-capture-task)
*   Otherwise, the recommended approach is to apply this configuration globally in your [Dashboard](https://dashboard.onfido.com/) under Accounts \ Supported Documents, or hard code it into your SDK integration. Both of these options are documented below.

##### Country and document type selection by Dashboard 

Configuring the issuing country and document type selection step using your Dashboard is the **recommended method** of integration (available from [iOS SDK](https://documentation.onfido.com/sdk/ios/) version 28.0.0 and [Android SDK](https://documentation.onfido.com/sdk/android/) version 16.0.0 onwards), as this configuration is also applied to your Document Reports. Any document that has been uploaded by an end user against your guidance will result in a Document Report sub-result of "rejected" and be flagged as `Image Integrity` > `Supported Document`.

_We will be rolling out Dashboard-based configuration of allowed documents soon. In the meantime, contact [support@onfido.com](support@onfido.com) or your Customer Support Manager to request access to this feature_.

*   Open the Accounts tab on your [Dashboard](https://dashboard.onfido.com/), then click Supported Documents
*   You will be presented with a list of all available countries and their associated supported documents. Make your selection, then click Save Change. 

![Dashboard country and document selection](dashboard-supported-docs.png "")

**Please note the following SDK behaviour**:
*   Hard coding any document type and issuing country configuration in your SDK integration will fully override the Dashboard-based settings
*   Currently, only passport, national ID card, driving licence and residence permit are visible for document selection by the end user in the SDK. For the time being, if you nominate other document types in your Dashboard (visa, for example), these will not be displayed in the user interface
*   If you need to add other document types to the document selection screen, you can mitigate this limitation in the near-term, using the Custom Document feature
*   If for any reason the configuration fails or is not enabled, the SDK will fallback to display the selection screen for the complete list of documents supported within the selection screens

##### Country and document type customization by SDK integration code

You can configure the document step to capture single document types with specific properties, as well as customize the screen to display only a limited list of document types using the `DocumentCaptureStepBuilder` class's functions for the corresponding document types.

| Document Type           | Configuration function  | Configurable Properties        |
| ----------------------- | ----------------------- | ----------------------------   |
| Passport                | forPassport()           |                                |
| National Identity Card  | forNationalIdentity()   | - country<br> - documentFormat |
| Driving Licence         | forDrivingLicence()     | - country<br> - documentFormat |
| Residence Permit        | forResidencePermit()    | - country                      |
| Visa                    | forVisa()               | - country                      |
| Work Permit             | forWorkPermit()         | - country                      |
| Generic                 | forGenericDocument()    | - country<br> - documentPages  |

**Note**: `GENERIC` document type doesn't offer an optimized capture experience for a desired document type.

- **Document type**

The list of document types visible for the user to select can be shown or hidden using this option. If only one document type is specified, users will not see the selection screen and will be taken directly to the capture screen.

Each document type has its own configuration class.

##### Customizing the issuing country and document type selection screen with pre-selected documents

You can also customize the screen to display only a limited list of document types, using the configuration function to specify the ones you want to show.

**Note**: Currently you can only include `PASSPORT`, `NATIONAL_IDENTITY_CARD`, `DRIVING_LICENCE`, `RESIDENCE_PERMIT` in the list.

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

The configuration function allows you to specify the document's country of origin. If a document country is specified for a document type, the selection screen is displayed with the preselected country.

**Note**: You can specify a country for all document types except `PASSPORT`. This is because passports have the same format worldwide so the SDK does not require this additional information.     

For example, to only capture UK driving licences:

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

You can specify the format of a document as `CARD` or `FOLDED`. `CARD` is the default document format value for all document types.

If `FOLDED` is configured, a specific template overlay is shown to the user during document capture.

**Note**: You can specify `FOLDED` document format for French driving licence, South African national identity and Italian national identity **only**. If you configure the SDK with an unsupported
document format, the SDK will throw a `InvalidDocumentFormatAndCountryCombinationException`.

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

**Note**: Not all document-country combinations are supported. Unsupported documents will not be verified. If you decide to bypass the default country selection screen by replacing the `FlowStep.CAPTURE_DOCUMENT` with a `CaptureScreenStep`, please make sure that you are specifying a supported document.
A complete list of all supported documents can be found [here](https://onfido.com/supported-documents/).

#### Face capture step

In this step, a user can use the front camera to capture their face in the form of a selfie photo, video or motion capture.

The Face step has 3 variants:
1. To configure for photo, use `FaceCaptureStepBuilder.forPhoto()`
2. To configure for video, use `FaceCaptureStepBuilder.forVideo()`
3. To configure for motion, use `FaceCaptureStepBuilder.forMotion()`

**Motion**

Motion supports audio recording, but it is disabled by default. In order to enable it use `.withAudio(true)`.

```java
FlowStep faceCaptureStep = FaceCaptureStepBuilder.forMotion()
                .withAudio(true)
                .build();
```

**Introduction screen**

By default, all variants show an introduction screen. This is an optional screen only for the photo variant. You can disable it using the `withIntro(false)` function.

```java
FlowStep faceCaptureStep = FaceCaptureStepBuilder.forPhoto()
                .withIntro(false)
                .build();
```

Please note that you can only hide the intro video (not the whole screen) in the video variant by using the `withIntro(false)` function.

Customization of the introduction screen for the motion variant is not available.

**Confirmation screen**

By default, both photo and video variants show a confirmation screen. To not display the recorded video on the confirmation screen, you can hide it using the `withConfirmationVideoPreview` function.

```java
FlowStep faceCaptureStep = FaceCaptureStepBuilder.forVideo()
                .withConfirmationVideoPreview(false)
                .build();
```

**Errors**

The Face step can be configured to allow for only one variant. A custom flow **cannot** contain multiple variants of the face capture. If more than one type of `FaceCaptureStep` are added to the same custom flow, a custom `IllegalArgumentException` will be thrown at the beginning of the flow,
with the message `"You are not allowed to define more than one FaceCaptureVariant in a flow."`.

#### Proof of address step

In the Proof of Address step, a user picks the issuing country and type of document that proves their address before capturing the document with their phone camera or uploading it.

#### Finish step

The final screen displays a completion message to the user and signals the end of the flow. This is an **optional** screen.

#### NFC capture

Recent passports, national identity cards and residence permits contain a chip that can be accessed using Near Field Communication (NFC). The Onfido SDKs provide a set of screens and functionalities to extract this information, verify its authenticity and provide the results as part of a Document report.

With version [18.1.0](https://github.com/onfido/onfido-android-sdk/blob/master/MIGRATION.md#1800---1900) of the Onfido Android SDK, NFC is enabled by default and offered to end users when both the document and the device support NFC.

For more information on how to configure NFC and the list of supported documents, please refer to the [NFC for Document Report](https://developers.onfido.com/guide/document-report-nfc) guide.

##### Disabling NFC and excluding dependencies

As NFC is enabled by default and library dependencies are included in the build automatically, the following section details the steps required to disable NFC and remove any libraries from the build process:

Call `disableNFC()` while configuring `OnfidoConfig`:

```kotlin
val config = OnfidoConfig.builder(this@MainActivity)
    .withSDKToken(“<YOUR_SDK_TOKEN_HERE>”)
    .disableNFC() //Disable NFC feature
    .withCustomFlow(flowSteps)
    .build()
```

Exclude dependencies required for NFC from your build:

```gradle
dependencies {
  implementation 'com.onfido.sdk.capture:onfido-capture-sdk:x.y.z' {
    exclude group: 'net.sf.scuba', module: 'scuba-sc-android'
    exclude group: 'org.jmrtd', module: 'jmrtd'
    exclude group: 'com.madgag.spongycastle', module: 'prov'
  }
}
```

If your application already uses the same libraries that the Onfido SDK needs for the NFC feature, you may encounter some dependency conflicts that will impact and could interfere with the NFC capture in our SDK. In such cases, we propose using the dependency resolution strategy below, by adding the following lines to your `build.gradle` file:

```gradle
implementation ("com.onfido.sdk:onfido-<variant>:19.1.0"){
     exclude group: "org.bouncycastle"
 }
 implementation ("the other library that conflicts with Onfido on BouncyCastle") {
     exclude group: "org.bouncycastle"
 }
 
 implementation "org.bouncycastle:bcprov-jdk15to18:1.69"
 implementation "org.bouncycastle:bcutil-jdk15to18:1.69"
```

## Advanced callbacks

### Handling callbacks

When the Onfido SDK session concludes, a range of callback functions may be triggered.

To receive the result from the flow, you should override the method `onActivityResult` on your Activity or Fragment. Typically, on success, you would create a check on your backend server.

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

Available callback functions include:

| Attribute     |    Notes    |
| -----|-------|
| `userCompleted` | User completed the flow. You can now create a check on your backend server. The `captures` object contains information about the document and face captures made during the flow.|
| `userExited` | User left the SDK flow without completing it. Some images may have already been uploaded. The `exitCode` object contains information about the reason for exit. |
| `onError` | User couldn't complete the flow because some error occurred. |

**`captures`**

Sample of a `captures` instance returned by a flow with `FlowStep.CAPTURE_DOCUMENT`, `FlowStep.CAPTURE_FACE` and  `FlowStep.PROOF_OF_ADDRESS`:
```
Document:
        Front: DocumentSide(id=document_id, side=FRONT, type=DRIVING_LICENCE, nfcSupported=false)
        Back: DocumentSide(id=document_id, side=BACK, type=DRIVING_LICENCE, nfcSupported=false)
        Type: DRIVING_LICENCE
            
Face:
        Face(id=face_id, variant=PHOTO) 
        
Proof of address:
        Poa(id=poa_id, type=UTILITY_BILL, issuing_country=UK)    
```
**Note**: the `type` property refers to `DocumentType`, variant refers to `FaceCaptureVariant`

**Note**: As part of the `userCompleted` method, the `DocumentType` property can only contain the values which are supported by the Onfido API. Please refer to [our API documentation](https://documentation.onfido.com/#document-types)

**`exitCode`**

Potential `exitCode` reasons include:

| `exitCode`                            |
| ------------------------------------- |
| USER_LEFT_ACTIVITY                    |
| USER_CONSENT_DENIED                   |
| REQUIRED_NFC_FLOW_NOT_COMPLETED       |
| CAMERA_PERMISSION_DENIED (Deprecated) |

### Custom media callbacks

<Callout>

> The following custom callback features must be enabled for your account before they can be used. For more information, please contact your Onfido Solution Engineer or Customer Success Manager.

</Callout>

#### Introduction

Custom media callbacks allow you to control the data collected by the Onfido SDK by using callbacks that are invoked when the end user submits their captured media. The callbacks provide all of the information that would normally be sent directly to the Onfido API and expect a promise in response, controlling what the SDK does next.

As a result, you can leverage Onfido's advanced on-device technology, including image quality validations, while still being able to handle end users’ data directly. This unlocks additional use cases, including compliance requirements and multi-vendor configurations, that require this additional flexibility.

#### Implementation
Once custom media callbacks are enabled for your account, use `.withMediaCallback` and provide the callbacks for `DocumentResult`, `SelfieResult` and `LivenessResult`.

**Note**: From version 18.0.0 onwards, for any usage of the `MediaCallback`, implement `Parcelable` instead of `Serializable`.

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

##### Kotlin
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

#### User data
The callbacks return an object including the information that the SDK normally sends directly to the Onfido API. The callbacks are invoked when the end user confirms submission of their image through the SDK’s user interface.

**Note**: Currently, end user data will still automatically be sent to the Onfido backend. You are not required to use Onfido to process this data.

#### Documents

For documents, the callback returns a `DocumentResult` object:
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

**Note**: `issuingCountry` is optional based on end-user selection, and can be `null`

**Note**: If a document was scanned using NFC, the callback will only return the `MediaFile`

#### Live photos and videos

For live photos, the callback returns a `SelfieResult` object:
```json5
{
  fileData: MediaFile
}
```

For live videos, the callback returns a `LivenessResult` object:
```json5
{
  fileData: MediaFile
}
```

The `MediaFile` object contains the raw data, file type and the file name of the captured photo or video.
```json5
{
  fileData: ByteArray,
  fileType: String,
  fileName: String
}
```

### User Analytics

The SDK allows you to track a user's progress through the SDK via an overridable hook. This gives insight into how your users make use of the SDK screens.

#### Overriding the hook

In order to expose a user's progress through the SDK, a hook method must be overridden using `OnfidoConfig.Builder`. You can do this when initializing the Onfido SDK. For example: 

##### Java

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
        // This approach can help to scope your potential network calls to the lifecycle of your activity or fragment
        // storage.addToList("onfidoEvents", event);
    }
}
private static final int ONFIDO_FLOW_REQUEST_CODE = 100;
OnfidoConfig onfidoConfig = OnfidoConfig.builder(applicationContext)
    .withAnalyticsEventListener(new OnfidoEventListener(applicationContext, storage))
    .build();
Onfido.startActivityForResult(this, ONFIDO_FLOW_REQUEST_CODE, onfidoConfig);
```

##### Kotlin

```kotlin
// Place your listener in a separate class file

class OnfidoEventListener(
    private val applicationContext: Context,
    private val storage: Storage
) : OnfidoAnalyticsEventListener {

    override fun onEvent(event: OnfidoAnalyticsEvent) {
        // Your tracking or persistence code
        // You can persist the events to storage and track them once the SDK flow is completed or exited with an error
        // This approach can help to scope your potential network calls to the lifecycle of your activity or fragment
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

The code inside the overridden method will now be called when a particular event is triggered, usually when the user reaches a new screen. Please use a static or separate class instead of a lambda or an anonymous inner class to avoid leaking the outer class, e.g. Activity or Fragment. Also refrain from using Activity or Fragment context references in your listener to prevent memory leaks and crashes. If you need access to a context object, you can inject your application context in the constructor of your listener as shown in the above example. As a better approach, you can wrap your application context in a  single-responsibility class (such as `Storage` or `APIService`) and  inject it in your listener, as shown in the example.

**Notes**:

* From versions [16.0.0](https://documentation.onfido.com/sdk/android/#no--1600---2023-02-21) onwards, `UserEventHandler` has been deprecated and **removed** from the SDK. If you are upgrading from a previous Onfido SDK version, please migrate to `OnfidoAnalyticsEventListener`
* From version 18.0.0 onwards, for any usage of the `OnfidoEventListener`, implement `Parcelable` instead of `Serializable`

For a full list of events, see [TRACKED_EVENTS.md](TRACKED_EVENTS.md).

| property     | description                                                                                                                                                                                                                                                                                                                                         |
|--------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `type`       | **OnfidoAnalyticsEventType** <br /> Indicates the type of event. Potential values (enum instances) are `FLOW`, `SCREEN`, `VIEW`, `ACTION`, `ERROR`.                                                                                                                                                                                                 |
| `properties` | **Map<OnfidoAnalyticsPropertyKey, String?>** <br /> Contains details of an event. For example, you can get the name of the visited screen using the `SCREEN_NAME` property. The current potential property keys are: `SCREEN_NAME`, `SCREEN_MODE`, `DOCUMENT_TYPE`, `COUNTRY_CODE`, `DOCUMENT_FORMAT`, `VIDEO_CHALLENGE_TYPE` and `IS_AUTOCAPTURE`. |

#### Properties

* `SCREEN_NAME`: The name of the visited screen (e.g. `WELCOME`, `DOCUMENT_CAPTURE`, etc.)
* `SCREEN_MODE`: Screen orientation in json, potential values are `portrait` or `landscape`
* `DOCUMENT_TYPE`: Type of the selected document for capture (e.g. `passport`, `national_id`, `driving_licence`, etc.)
* `COUNTRY_CODE`: The 2-letter ISO code of the selected country (e.g. `US`, `UK`, `DE`, etc.), used in the `COUNTRY_SELECTION` event
* `DOCUMENT_FORMAT`: Format of the document to capture, used in the `DOCUMENT_CAPTURE` event. Possible values are `card` and `folded`
* `VIDEO_CHALLENGE_TYPE`: Type of the displayed liveness video challenge (e.g. `recite`, `movement`)
* `IS_AUTOCAPTURE`: Whether or auto-capture was used

#### Using the data

You can use the data to keep track of how many users reach each screen in your flow. You can do this by storing the number of users that reach each screen and comparing that to the number of users who reached the `Welcome` screen.

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

The Onfido Android SDK has been optimized to provide the following accessibility support by default:

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

## Support

Should you encounter any technical issues during integration, please contact Onfido's Customer Support team via [email](mailto:support@onfido.com), including the word ISSUE at the start of the subject line. 

Alternatively, you can search the support documentation available via the customer experience portal, [public.support.onfido.com](https://public.support.onfido.com/s/).

We recommend you update your SDK to the latest version release as frequently as possible. Customers on newer versions of the Onfido SDK consistently see better performance across user onboarding and fraud mitigation, so we strongly advise keeping your SDK integration up-to-date.

You can review our full SDK versioning policy [here](https://developers.onfido.com/guide/sdk-version-releases).

Copyright 2024 Onfido, Ltd. All rights reserved.
