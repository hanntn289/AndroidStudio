package com.example.btlappchamsocsuckhoe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.agconnect.auth.EmailAuthProvider;
import com.huawei.agconnect.auth.VerifyCodeResult;
import com.huawei.agconnect.auth.VerifyCodeSettings;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.TaskExecutors;

import java.util.Locale;

public class ActivityVerifyCodeHuawei extends AppCompatActivity {
    EditText verifycode;
    TextView btnxacnhan, btnguicode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code_huawei);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        verifycode = findViewById(R.id.verifycode);
        btnxacnhan = findViewById(R.id.btnxacnhan);
        btnguicode = findViewById(R.id.btnguicode);

        btnguicode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyEmail();
            }
        });

        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("verifycode", verifycode.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void verifyEmail() {
        VerifyCodeSettings verifyCodeSettings = VerifyCodeSettings.newBuilder()
                .action(VerifyCodeSettings.ACTION_REGISTER_LOGIN)
                .sendInterval(10)
                .locale(Locale.getDefault())
                .build();

        com.huawei.hmf.tasks.Task<VerifyCodeResult> task = EmailAuthProvider.requestVerifyCode(Activity_register.email.getText().toString(), verifyCodeSettings);
        task.addOnSuccessListener(TaskExecutors.uiThread(), new OnSuccessListener<VerifyCodeResult>() {
            @Override
            public void onSuccess(VerifyCodeResult verifyCodeResult) {
                Toast.makeText(getApplicationContext(), "Please check your e-mail", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(TaskExecutors.uiThread(), new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e("register", e.getLocalizedMessage());
                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}