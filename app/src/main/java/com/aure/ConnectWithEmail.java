package com.aure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aure.UiModels.LoginModel;
import com.aure.UiModels.Utils.NetworkUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ConnectWithEmail extends AppCompatActivity {

    TextView dontHaveAccount,forgotPassword;
    LinearLayout loginButton;
    TextInputLayout emailLayout,passwordLayout;
    TextInputEditText emailEdit,passwordEdit;
    String mEmail,mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_with_email);
        initView();
    }

    private void initView(){
        dontHaveAccount = findViewById(R.id.login_dont_have_account);
        loginButton = findViewById(R.id.login_with_email_button);
        emailEdit = findViewById(R.id.login_email);
        passwordEdit = findViewById(R.id.login_password);
        forgotPassword = findViewById(R.id.login_forgot_password);
        emailLayout = findViewById(R.id.login_email_layout);
        passwordLayout = findViewById(R.id.login_password_layout);

        Spannable forgotPasswordSpan = new SpannableString("Forgot Password?");
        forgotPasswordSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#fa2d65")), 7, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Spannable dontHaveAccountSpan = new SpannableString("D'ont have an account? Sign up");
        dontHaveAccountSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#fa2d65")), 23, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        dontHaveAccount.setText(dontHaveAccountSpan);

        forgotPassword.setText(forgotPasswordSpan);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ConnectWithEmail.this,ResetPassword.class));

            }
        });

        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ConnectWithEmail.this,SignUpWithEmail.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEmail = emailEdit.getText().toString().trim();
                mPassword = passwordEdit.getText().toString().trim();

                if(isValidForm()){
                    if(!new NetworkUtils(ConnectWithEmail.this).isNetworkAvailable()){
                        Toast.makeText(ConnectWithEmail.this, "No Network", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        LoginModel loginModel = new LoginModel(mEmail,mPassword,ConnectWithEmail.this);
                        loginModel.LoginUser();
                        loginModel.setLoginListener(new LoginModel.LoginListener() {
                            @Override
                            public boolean isLoginSuccessful(String email) {
                                Intent intent = new Intent(ConnectWithEmail.this,MainActivity.class);
                                intent.putExtra("email",email);
                                startActivity(intent);
                                finish();
                                return true;
                            }

                            @Override
                            public boolean isLoginFailed(String message) {
                                Toast.makeText(ConnectWithEmail.this, message, Toast.LENGTH_SHORT).show();
                                return true;
                            }
                        });
                    }

                }
            }
        });

    }

    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.pink));
        }
    }

    private boolean isValidForm() {
        boolean valid = true;
        if (TextUtils.isEmpty(mEmail)) {
            emailLayout.setError("Required");
            valid = false;
            return valid;
        }
        if (TextUtils.isEmpty(mPassword)) {
            passwordLayout.setError("Required");
            valid = false;
            return valid;
        }
        return valid;
    }
}
