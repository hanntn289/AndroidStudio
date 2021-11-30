package com.example.btlappchamsocsuckhoe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlappchamsocsuckhoe.adapter.Adapter_dstheodoi;
import com.example.btlappchamsocsuckhoe.model.DBConnect;
import com.example.btlappchamsocsuckhoe.model.User;
import com.example.btlappchamsocsuckhoe.model.theodoi;
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

public class Activity_dstheodoi extends AppCompatActivity implements View.OnClickListener {
    private ImageView btntrangcanhan;
    private ImageView btntrangdangtheodoi;
    private ImageView btntrangnhantin;
    private ImageView btntrangnhatki;
    private TextView btntrangcanhan1;
    private TextView btntrangdangtheodoi1;
    private TextView btntrangnhantin1;
    private TextView btntrangnhatki1;
    private TextView btntrangyeucautheodoi;
    private EditText editidnguoithan;
    private TextView btnxacnhan2;
    private DBConnect dbConnect;
    private ListView listView;
    private Adapter_dstheodoi adapter_dstheodoi;
    private ArrayList<User> dstheodoi_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dstheodoi);
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
        btntrangyeucautheodoi = (TextView) findViewById(R.id.btntrangyeucautheodoi);
        editidnguoithan = (EditText) findViewById(R.id.editidnguoithan);
        btnxacnhan2 = (TextView) findViewById(R.id.btnxacnhan2);
        listView = (ListView) findViewById(R.id.listviewdsnguoitheodoi);
        dstheodoi_user = new ArrayList<>();
        laydulieu_hiendanhsach();

        dbConnect = new DBConnect(Activity_dstheodoi.this);

        btntrangcanhan.setOnClickListener(this);
        btntrangdangtheodoi.setOnClickListener(this);
        btntrangnhatki.setOnClickListener(this);
        btntrangcanhan1.setOnClickListener(this);
        btntrangdangtheodoi1.setOnClickListener(this);
        btntrangnhatki1.setOnClickListener(this);
        btntrangyeucautheodoi.setOnClickListener(this);
        btnxacnhan2.setOnClickListener(this);

    }
    private  void laydulieu_hiendanhsach(){
        dbConnect = new DBConnect(Activity_dstheodoi.this);
        Task<CloudDBZoneSnapshot<theodoi>> queryTask = dbConnect.mCloudDBZone.executeQuery(
                CloudDBZoneQuery.where(theodoi.class).equalTo("My_id", AGConnectAuth.getInstance().getCurrentUser().getUid()),
                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY);
        queryTask.addOnSuccessListener(new OnSuccessListener<CloudDBZoneSnapshot<theodoi>>() {
            @Override
            public void onSuccess(CloudDBZoneSnapshot<theodoi> theodoiCloudDBZoneSnapshot) {
                CloudDBZoneObjectList<theodoi> theodoiCursor = theodoiCloudDBZoneSnapshot.getSnapshotObjects();
                try {
                    ArrayList<String> theodois = new ArrayList<>();
                    while (theodoiCursor.hasNext()) {
                        theodoi theodoi = theodoiCursor.next();
                        theodois.add(theodoi.getFollow_id());
                    }
                    Task<CloudDBZoneSnapshot<User>> queryTask1 = dbConnect.mCloudDBZone.executeQuery(
                            CloudDBZoneQuery.where(User.class).in("id", theodois.toArray(new String[theodois.size()])),
                            CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY);
                    queryTask1.addOnSuccessListener(new OnSuccessListener<CloudDBZoneSnapshot<User>>() {
                        @Override
                        public void onSuccess(CloudDBZoneSnapshot<User> userCloudDBZoneSnapshot) {
                            CloudDBZoneObjectList<User> usrCursor = userCloudDBZoneSnapshot.getSnapshotObjects();
                            try {
                                while (usrCursor.hasNext()) {
                                    dstheodoi_user.add(usrCursor.next());
                                }
                                adapter_dstheodoi.notifyDataSetChanged();
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
                    theodoiCloudDBZoneSnapshot.release();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e("Error",e.toString());
            }
        });

        adapter_dstheodoi = new Adapter_dstheodoi(this, R.layout.item_dstheodoi, dstheodoi_user);
        listView.setAdapter(adapter_dstheodoi);
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
            case R.id.btntrangyeucautheodoi:{
                startActivity(new Intent(this, Activity_dsyeucau.class));
                break;
            }
            case R.id.btnxacnhan2:{
                if(editidnguoithan.getText() == null){
                    Toast.makeText(this, "Chua nhap id nguoi than", Toast.LENGTH_SHORT).show();
                }
                else {
                    yeucau yeucau = new yeucau();
                    yeucau.setId_nguoi_gui(AGConnectAuth.getInstance().getCurrentUser().getUid());
                    yeucau.setId_nguoi_nhan(editidnguoithan.getText().toString());
                    dbConnect.add(yeucau);
                    editidnguoithan.setText("");
                }
                break;
            }
            default:{
                break;
            }
        }
    }

}