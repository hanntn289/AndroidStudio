package com.example.btlappchamsocsuckhoe;

import androidx.annotation.Nullable;
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

import com.example.btlappchamsocsuckhoe.model.DBConnect;
import com.example.btlappchamsocsuckhoe.model.User;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.auth.EmailUser;
import com.huawei.agconnect.auth.SignInResult;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;

public class Activity_register extends AppCompatActivity implements View.OnClickListener {
    EditText username, password;
    static EditText email;
    TextView btnlogin_register, btnregister;
    String verifyCode;
    ProgressDialog pd;
    DBConnect dbConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        anhxa();
    }

    private void anhxa(){
        username = findViewById(R.id.etxfullname);
        email = findViewById(R.id.etxemail);
        password = findViewById(R.id.etxpassword);
        btnlogin_register = findViewById(R.id.btnlogin_register);
        btnregister = findViewById(R.id.btnregister);

        btnregister.setOnClickListener(this);
        btnlogin_register.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnregister:{
                startActivityForResult(new Intent(this, ActivityVerifyCodeHuawei.class), 8888);
                break;
            }
            case R.id.btnlogin_register:{
                startActivity(new Intent(this, Activity_login.class));
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 8888 && resultCode == RESULT_OK && data != null){
            verifyCode = data.getStringExtra("verifycode");
            register();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void register(){
        pd = new ProgressDialog(Activity_register.this);
        pd.setMessage("Please wait...");
        pd.show();
        //Huawei
        EmailUser emailUser = new EmailUser.Builder()
                .setEmail(email.getText().toString())
                .setPassword(password.getText().toString())
                .setVerifyCode(verifyCode)
                .build();

        AGConnectAuth.getInstance().createUser(emailUser)
                .addOnSuccessListener(new OnSuccessListener<SignInResult>() {
                    @Override
                    public void onSuccess(SignInResult signInResult) {
                        Toast.makeText(getApplicationContext(), "User successfully created.", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                        dbConnect = new DBConnect(Activity_register.this);
                        User user = new User();
                        user.setId(AGConnectAuth.getInstance().getCurrentUser().getUid());
                        user.setTen(username.getText().toString());
                        dbConnect.add(user);
                        startActivity(new Intent(getApplicationContext(), Activity_dstheodoi.class));
                        finish();
                    }
                }) .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                pd.dismiss();
                Log.e("login error",e.getMessage()+"|"+email.getText().toString()+"|"+password.getText().toString()+"|"+verifyCode);
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}