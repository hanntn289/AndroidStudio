package com.example.btlappchamsocsuckhoe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.AGConnectAuthCredential;
import com.huawei.agconnect.auth.EmailAuthProvider;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;

public class Activity_login extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private TextView register,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        if (AGConnectAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), Activity_dstheodoi.class));
            finish();
        }
        email = findViewById(R.id.etxemail);
        password = findViewById(R.id.etxpassword);
        login = findViewById(R.id.btnlogin);
        register = findViewById(R.id.btnlogin_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_login.this, Activity_register.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginProcess();
            }
        });

    }

    private void loginProcess() {
        final ProgressDialog pd = new ProgressDialog(Activity_login.this);
        pd.setMessage("Please wait...");
        pd.show();
        AGConnectAuthCredential credential = EmailAuthProvider
                .credentialWithPassword(email.getText().toString(), password.getText().toString());
        AGConnectAuth.getInstance().signIn(credential)
                .addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                    @Override
                    public void onSuccess(SignInResult signInResult) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Activity_login.this, Activity_login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        pd.dismiss();
                        Log.e("Login", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
/*
cayqua53@gmail.com
cong123456
 */