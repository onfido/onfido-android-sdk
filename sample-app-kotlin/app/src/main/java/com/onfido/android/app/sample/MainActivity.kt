package com.onfido.android.app.sample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.onfido.android.sdk.capture.ExitCode
import com.onfido.android.sdk.capture.Onfido
import com.onfido.android.sdk.capture.OnfidoConfig
import com.onfido.android.sdk.capture.OnfidoFactory
import com.onfido.android.sdk.capture.errors.OnfidoException
import com.onfido.android.sdk.capture.ui.camera.face.stepbuilder.FaceCaptureStepBuilder
import com.onfido.android.sdk.capture.ui.options.FlowStep
import com.onfido.android.sdk.capture.upload.Captures

class MainActivity : AppCompatActivity() {
    private var client: Onfido? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)
        findViewById<View>(R.id.next).setOnClickListener { v: View? -> startFlow() }
        client = OnfidoFactory.create(this).client
    }

    private fun startFlow() {
        val flowStepsWithOptions = arrayOf(
            FlowStep.WELCOME,
            FlowStep.CAPTURE_DOCUMENT,
            FaceCaptureStepBuilder.forPhoto().build(),
            FlowStep.FINAL
        )
        startFlow(flowStepsWithOptions)
    }

    private fun startFlow(flowSteps: Array<FlowStep>) {
        // TODO Call your backend to get `sdkToken` https://github.com/onfido/onfido-android-sdk#41-sdk-token
        val sdkToken = "YOUR_SDK_TOKEN"
        val onfidoConfig = OnfidoConfig.builder(this@MainActivity)
            .withSDKToken(sdkToken)
            .withCustomFlow(flowSteps)
            .build()
        client!!.startActivityForResult(this@MainActivity, 1, onfidoConfig)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        client!!.handleActivityResult(resultCode, data, object : Onfido.OnfidoResultListener {
            override fun userExited(exitCode: ExitCode) {
                showToast("User cancelled.")
            }

            override fun userCompleted(captures: Captures) {
                startCheck(captures)
            }

            override fun onError(e: OnfidoException) {
                e.printStackTrace()
                showToast("Unknown error")
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun startCheck(captures: Captures) {
        //Call your back end to initiate the check https://github.com/onfido/onfido-android-sdk#2-creating-a-check
    }
}