package com.aure;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.aure.UiModels.ForgotPasswordModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class VerificationEmail extends AppCompatActivity {

    TextInputEditText emailText;
    MaterialButton getResetLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_email);
        initView();
    }

    private void initView(){
        emailText = findViewById(R.id.forgot_password_email);
        getResetLink = findViewById(R.id.forgot_password_reset_link);

        getResetLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailText.getText().toString().trim().equalsIgnoreCase("")){
                    emailText.setError("Required");
                }
                else{
                    ForgotPasswordModel forgotPasswordModel = new ForgotPasswordModel(emailText.getText().toString().trim(),VerificationEmail.this);
                    forgotPasswordModel.getResetLink();
                    forgotPasswordModel.setGetLinkListener(new ForgotPasswordModel.getLinkListener() {
                        @Override
                        public void isSuccess() {
                             showAlert();
                        }

                        @Override
                        public void isFailure() {
                            Toast.makeText(VerificationEmail.this, "Error Occurred please try again", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    private void showAlert(){
        new AlertDialog.Builder(VerificationEmail.this)
                .setTitle("Reset Link Sent")
                .setMessage("A password reset link has been forwarded to the provided email address if it exist in our database")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}