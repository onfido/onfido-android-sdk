package com.onfido.android.app.sample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.onfido.android.sdk.capture.ExitCode;
import com.onfido.android.sdk.capture.Onfido;
import com.onfido.android.sdk.capture.OnfidoConfig;
import com.onfido.android.sdk.capture.OnfidoFactory;
import com.onfido.android.sdk.capture.errors.OnfidoException;
import com.onfido.android.sdk.capture.ui.options.FlowStep;
import com.onfido.android.sdk.capture.upload.Captures;

public class MainActivity extends AppCompatActivity {

    private Onfido client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        findViewById(R.id.next).setOnClickListener(v -> startFlow());

        client = OnfidoFactory.create(this).getClient();
    }

    private void startFlow() {
        final FlowStep[] flowStepsWithOptions = new FlowStep[]{
                FlowStep.WELCOME,
                FlowStep.USER_CONSENT,
                FlowStep.CAPTURE_DOCUMENT,
                FlowStep.CAPTURE_FACE,
                FlowStep.FINAL
        };

        startFlow(flowStepsWithOptions);
    }

    private void startFlow(final FlowStep[] flowSteps) {
        // TODO Call your backend to get `sdkToken` https://github.com/onfido/onfido-android-sdk#41-sdk-token
        String sdkToken = "YOUR_SDK_TOKEN";

        OnfidoConfig onfidoConfig =
                OnfidoConfig.builder(MainActivity.this)
                        .withSDKToken(sdkToken)
                        .withCustomFlow(flowSteps)
                        .build();

        client.startActivityForResult(MainActivity.this, 1, onfidoConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        client.handleActivityResult(resultCode, data, new Onfido.OnfidoResultListener() {
            @Override
            public void userExited(@NonNull ExitCode exitCode) {
                showToast("User cancelled.");
            }

            @Override
            public void userCompleted(@NonNull Captures captures) {
                startCheck(captures);
            }

            @Override
            public void onError(OnfidoException e) {
                e.printStackTrace();
                showToast("Unknown error");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void startCheck(Captures captures) {
        //Call your back end to initiate the check https://github.com/onfido/onfido-android-sdk#2-creating-a-check
    }
}
