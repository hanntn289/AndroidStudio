package com.example.btlappchamsocsuckhoe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.btlappchamsocsuckhoe.adapter.Adapter_yeucau;
import com.example.btlappchamsocsuckhoe.model.DBConnect;
import com.example.btlappchamsocsuckhoe.model.User;
import com.example.btlappchamsocsuckhoe.model.yeucau;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.cloud.database.CloudDBZoneObjectList;
import com.huawei.agconnect.cloud.database.CloudDBZoneQuery;
import com.huawei.agconnect.cloud.database.CloudDBZoneSnapshot;
import com.huawei.agconnect.cloud.database.exceptions.AGConnectCloudDBException;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;

import java.util.ArrayList;

public class Activity_dsyeucau extends AppCompatActivity implements View.OnClickListener {
    private ImageView btntrangcanhan;
    private ImageView btntrangdangtheodoi;
    private ImageView btntrangnhatki;
    private TextView btntrangcanhan1;
    private TextView btntrangdangtheodoi1;
    private TextView btntrangnhatki1;
    private DBConnect dbConnect;
    private ListView listView;
    private Adapter_yeucau adapter_yeucau;
    private ArrayList<User> dsyeucau_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsyeucau);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        anhxa();
    }

    private void anhxa(){
        btntrangcanhan = (ImageView) findViewById(R.id.btntrangcanhan);
        btntrangdangtheodoi = (ImageView) findViewById(R.id.btntrangdangtheodoi);
        btntrangnhatki = (ImageView) findViewById(R.id.btntrangnhatki);
        btntrangcanhan1= (TextView) findViewById(R.id.btntrangcanhan1);
        btntrangdangtheodoi1 = (TextView) findViewById(R.id.btntrangdangtheodoi1);
        btntrangnhatki1 = (TextView) findViewById(R.id.btntrangnhatki1);
        listView = (ListView) findViewById(R.id.listviewdsnguoiyeucautheodoi);
        dsyeucau_user = new ArrayList<>();
        laydulieu_hiendanhsach();

        dbConnect = new DBConnect(Activity_dsyeucau.this);

        btntrangcanhan.setOnClickListener(this);
        btntrangdangtheodoi.setOnClickListener(this);
        btntrangnhatki.setOnClickListener(this);
        btntrangcanhan1.setOnClickListener(this);
        btntrangdangtheodoi1.setOnClickListener(this);
        btntrangnhatki1.setOnClickListener(this);

    }
    private  void laydulieu_hiendanhsach(){
        dbConnect = new DBConnect(Activity_dsyeucau.this);
        Task<CloudDBZoneSnapshot<yeucau>> queryTask = dbConnect.mCloudDBZone.executeQuery(
                CloudDBZoneQuery.where(yeucau.class).equalTo("id_nguoi_nhan", AGConnectAuth.getInstance().getCurrentUser().getUid()),
                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY);
        queryTask.addOnSuccessListener(new OnSuccessListener<CloudDBZoneSnapshot<yeucau>>() {
            @Override
            public void onSuccess(CloudDBZoneSnapshot<yeucau> yeucauCloudDBZoneSnapshot) {
                CloudDBZoneObjectList<yeucau> yeucauCursor = yeucauCloudDBZoneSnapshot.getSnapshotObjects();
                try {
                    ArrayList<String> yeucau = new ArrayList<>();
                    while (yeucauCursor.hasNext()) {
                        yeucau.add(yeucauCursor.next().getId_nguoi_gui());
                    }
                        Task<CloudDBZoneSnapshot<User>> queryTask1 = dbConnect.mCloudDBZone.executeQuery(
                                CloudDBZoneQuery.where(User.class).in("id", yeucau.toArray(new String[yeucau.size()])),
                                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY);
                        queryTask1.addOnSuccessListener(new OnSuccessListener<CloudDBZoneSnapshot<User>>() {
                            @Override
                            public void onSuccess(CloudDBZoneSnapshot<User> userCloudDBZoneSnapshot) {
                                CloudDBZoneObjectList<User> usrCursor = userCloudDBZoneSnapshot.getSnapshotObjects();
                                try {
                                    while (usrCursor.hasNext()) {
                                        dsyeucau_user.add(usrCursor.next());
                                    }
                                    adapter_yeucau.notifyDataSetChanged();
                                } catch (AGConnectCloudDBException e) {
                                    Log.e("TAG", "processQueryResult: " + e.getMessage());
                                } finally {
                                    userCloudDBZoneSnapshot.release();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Log.e("Error",e.toString());
                            }
                        });
                } catch (AGConnectCloudDBException e) {
                    Log.e("TAG", "processQueryResult: " + e.getMessage());
                } finally {
                    yeucauCloudDBZoneSnapshot.release();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e("Error",e.toString());
            }
        });

        adapter_yeucau = new Adapter_yeucau(this, R.layout.item_yeucautheodoi, dsyeucau_user);
        listView.setAdapter(adapter_yeucau);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btntrangcanhan1:
            case R.id.btntrangcanhan:{
                startActivity(new Intent(this, Activity_trangcanhan.class));
                break;
            }
            case R.id.btntrangdangtheodoi1:
            case R.id.btntrangdangtheodoi:{
                startActivity(new Intent(this, Activity_dstheodoi.class));
                break;
            }
            case R.id.btntrangnhatki1:
            case R.id.btntrangnhatki:{
                startActivity(new Intent(this, Activity_nhatki.class));
                break;
            }
            default:{
                break;
            }
        }
    }
}