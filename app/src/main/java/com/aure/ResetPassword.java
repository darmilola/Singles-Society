package com.aure;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aure.UiModels.ForgotPasswordModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ResetPassword extends AppCompatActivity {

    TextInputEditText newPassword, retypePassword;
    LinearLayout reset;
    String userIdHash;
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initView();
    }

    private void initView(){
        newPassword = findViewById(R.id.reset_password_text);
        retypePassword = findViewById(R.id.reset_password_text_retype);
        reset = findViewById(R.id.reset_password_button);
        backButton = findViewById(R.id.resetPasswordBackButton);
      //  Intent intent = getIntent();
     //   String action = intent.getAction();
     //   Uri data = intent.getData();

      //  userIdHash = data.getQueryParameter("hash");

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidForm()) {
                    ForgotPasswordModel forgotPasswordModel = new ForgotPasswordModel(newPassword.getText().toString(),userIdHash,ResetPassword.this,1 );
                    forgotPasswordModel.ResetPassword();
                    forgotPasswordModel.setListener(new ForgotPasswordModel.getLinkListener() {
                        @Override
                        public void isSuccess() {
                            showAlert();
                        }

                        @Override
                        public void isFailure() {
                            Toast.makeText(ResetPassword.this, "Error Occurred please try again", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void showAlert(){
        new AlertDialog.Builder(ResetPassword.this)
                .setTitle("Password Reset")
                .setMessage("Your Password has been reset successfully, you can now login")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ResetPassword.this,ConnectWithEmail.class));
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.special_activity_background));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }


    private boolean isValidForm(){
        boolean isValid = true;

        if(TextUtils.isEmpty(newPassword.getText().toString().trim())){
            newPassword.setError("Required");
            isValid = false;
            return isValid;
        }
        if(TextUtils.isEmpty(retypePassword.getText().toString().trim())){
            retypePassword.setError("Required");
            isValid = false;
            return isValid;
        }

        if(!newPassword.getText().toString().trim().equalsIgnoreCase(retypePassword.getText().toString().trim())){
            Toast.makeText(ResetPassword.this, "Password does not match", Toast.LENGTH_SHORT).show();
            isValid = false;
            return isValid;
        }
        return isValid;
    }
}