# React Native Set Up

## Common Gotchas

### Dependencies

React Native has some dependency conflicts with the Android SDK, so you'll need to include some overrides in your `build.gradle` dependencies block

```gradle
dependencies {
    // (all of your other dependencies)

    compile "com.onfido.sdk.capture:onfido-capture-sdk:4.2.0"
    // The Onfido SDK and React Native have some dependency conflicts, so we
    // need to manually specify some versions to manually resolve this conflict
    implementation "com.squareup.okhttp3:logging-interceptor:3.8.0"
    implementation "com.squareup.okhttp3:okhttp:3.8.0"
}
```
