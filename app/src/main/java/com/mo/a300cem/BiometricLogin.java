package com.mo.a300cem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BiometricLogin extends AppCompatActivity implements BiometricCallback  {

    private Button button;
    BiometricManager mBiometricManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biometric_login);

        mBiometricManager = new BiometricManager.BiometricBuilder(BiometricLogin.this)
                .setTitle(getString(R.string.biometric_title))
                .setSubtitle(getString(R.string.biometric_subtitle))
                .setDescription(getString(R.string.biometric_description))
                .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                .build();
                //start authentication
        mBiometricManager.authenticate(BiometricLogin.this);

    }

    @Override
    public void onSdkVersionNotSupported() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBiometricAuthenticationNotSupported() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(getApplicationContext(),
                getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationFailed() {
// Toast.makeText(getApplicationContext(), getString(R.string.biometric_failure),Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationCancelled() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_cancelled),
                Toast.LENGTH_LONG).show();
        mBiometricManager.cancelAuthentication();
    }
    @Override
    public void onAuthenticationSuccessful() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_success),
                Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
// Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
// Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_LONG).show();
    }
}
