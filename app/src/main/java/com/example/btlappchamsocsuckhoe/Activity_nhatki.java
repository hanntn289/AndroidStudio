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

import com.example.btlappchamsocsuckhoe.model.DBConnect;
import com.example.btlappchamsocsuckhoe.adapter.Adapter_nhatki;
import com.example.btlappchamsocsuckhoe.model.*;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.cloud.database.CloudDBZoneObjectList;
import com.huawei.agconnect.cloud.database.CloudDBZoneQuery;
import com.huawei.agconnect.cloud.database.CloudDBZoneSnapshot;
import com.huawei.agconnect.cloud.database.exceptions.AGConnectCloudDBException;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;

import java.util.ArrayList;

public class Activity_nhatki extends AppCompatActivity implements View.OnClickListener {
    private ImageView btntrangcanhan;
    private ImageView btntrangdangtheodoi;
    private ImageView btntrangnhatki;
    private TextView btntrangcanhan1;
    private TextView btntrangdangtheodoi1;
    private TextView btntrangnhatki1;
    private DBConnect dbConnect;
    private ListView listView;
    private Adapter_nhatki adapter_nhatki;
    private ArrayList<nhatki> ds_nhatki;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhatki);
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
        listView = (ListView) findViewById(R.id.listviewnhatki);
        ds_nhatki = new ArrayList<>();
        laydulieu_hiendanhsach();

        dbConnect = new DBConnect(Activity_nhatki.this);

        btntrangcanhan.setOnClickListener(this);
        btntrangdangtheodoi.setOnClickListener(this);
        btntrangnhatki.setOnClickListener(this);
        btntrangcanhan1.setOnClickListener(this);
        btntrangdangtheodoi1.setOnClickListener(this);
        btntrangnhatki1.setOnClickListener(this);

    }
    private  void laydulieu_hiendanhsach(){
        dbConnect = new DBConnect(Activity_nhatki.this);
        Task<CloudDBZoneSnapshot<nhatki>> queryTask1 = dbConnect.mCloudDBZone.executeQuery(
                CloudDBZoneQuery.where(nhatki.class).equalTo("id_User", AGConnectAuth.getInstance().getCurrentUser().getUid()),
                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY);
        queryTask1.addOnSuccessListener(new OnSuccessListener<CloudDBZoneSnapshot<nhatki>>() {
            @Override
            public void onSuccess(CloudDBZoneSnapshot<nhatki> nhatkiCloudDBZoneSnapshot) {
                CloudDBZoneObjectList<nhatki> nhatkiCursor = nhatkiCloudDBZoneSnapshot.getSnapshotObjects();
                try {
                    while (nhatkiCursor.hasNext()) {
                        ds_nhatki.add(nhatkiCursor.next());
                    }
                    adapter_nhatki.notifyDataSetChanged();
                } catch (AGConnectCloudDBException e) {
                    Log.e("TAG", "processQueryResult: " + e.getMessage());
                } finally {
                    nhatkiCloudDBZoneSnapshot.release();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Log.e("Error",e.toString());
            }
        });
        adapter_nhatki = new Adapter_nhatki(this, R.layout.item_nhatki, ds_nhatki);
        listView.setAdapter(adapter_nhatki);
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