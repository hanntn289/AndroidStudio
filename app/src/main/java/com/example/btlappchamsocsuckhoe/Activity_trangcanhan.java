package com.example.btlappchamsocsuckhoe;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import com.example.btlappchamsocsuckhoe.model.*;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.btlappchamsocsuckhoe.model.DBConnect;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.huawei.agconnect.cloud.database.CloudDBZoneObjectList;
import com.huawei.agconnect.cloud.database.CloudDBZoneQuery;
import com.huawei.agconnect.cloud.database.CloudDBZoneSnapshot;
import com.huawei.agconnect.cloud.database.exceptions.AGConnectCloudDBException;
import com.huawei.agconnect.cloud.storage.core.AGCStorageManagement;
import com.huawei.agconnect.cloud.storage.core.StorageReference;
import com.huawei.agconnect.cloud.storage.core.UploadTask;
import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class Activity_trangcanhan extends AppCompatActivity implements View.OnClickListener {
    private TextView btncapnhat;
    private ImageView btntrangcanhan;
    private ImageView btntrangdangtheodoi;
    private ImageView btntrangnhatki;
    private TextView btntrangcanhan1;
    private TextView btntrangdangtheodoi1;
    private TextView btntrangnhatki1;
    private TextView thoigian;
    private ImageView anhbia;
    private ImageView anhdaidien;
    private EditText edittennguoidung, edittgioitinh, edittuoi, editchieucao, editcannang, editthannhiet, edithuyetap, editnhiptim;
    private StorageReference reference;
    private DBConnect dbConnect;
    private TextView idnguoidung;
    private Uri mImageUri;
    private int check;
    User user;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangcanhan);
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
        anhxa();
        Laydulieu();

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void anhxa(){
        btntrangcanhan = (ImageView) findViewById(R.id.btntrangcanhan);
        btntrangdangtheodoi = (ImageView) findViewById(R.id.btntrangdangtheodoi);
        btntrangnhatki = (ImageView) findViewById(R.id.btntrangnhatki);
        btntrangcanhan1= (TextView) findViewById(R.id.btntrangcanhan1);
        btntrangdangtheodoi1 = (TextView) findViewById(R.id.btntrangdangtheodoi1);
        btntrangnhatki1 = (TextView) findViewById(R.id.btntrangnhatki1);
        anhbia = (ImageView) findViewById(R.id.anhbia);
        anhdaidien = (ImageView) findViewById(R.id.avata);
        btncapnhat = (TextView) findViewById(R.id.btncapnhat);
        edittgioitinh = (EditText) findViewById(R.id.edittgioitinh);
        edittuoi = (EditText) findViewById(R.id.edittuoi);
        editchieucao = (EditText) findViewById(R.id.editchieucao);
        editcannang = (EditText) findViewById(R.id.editcannang);
        editthannhiet = (EditText) findViewById(R.id.editthannhiet);
        edithuyetap = (EditText) findViewById(R.id.edithuyetap);
        editnhiptim = (EditText) findViewById(R.id.editnhiptim);
        edittennguoidung = (EditText) findViewById(R.id.edittennguoidung);
        idnguoidung = (TextView) findViewById(R.id.idnguoidung);
        thoigian = findViewById(R.id.thoigian);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy-HH:mm"); //dd/MM/yyyy-HH:mm
        String currentDateandTime = sdf.format(new Date());
        thoigian.setText(currentDateandTime);

        user = new User();

        dbConnect = new DBConnect(Activity_trangcanhan.this);

        btntrangcanhan.setOnClickListener(this);
        btntrangdangtheodoi.setOnClickListener(this);
        btntrangnhatki.setOnClickListener(this);
        btntrangcanhan1.setOnClickListener(this);
        btntrangdangtheodoi1.setOnClickListener(this);
        btntrangnhatki1.setOnClickListener(this);
        anhbia.setOnClickListener(this);
        anhdaidien.setOnClickListener(this);
        btncapnhat.setOnClickListener(this);

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
            case R.id.btncapnhat:{
                nhatki nhatki = new nhatki();
                nhatki.setId(UUID.randomUUID().toString());
                nhatki.setId_User(AGConnectAuth.getInstance().getCurrentUser().getUid());
                nhatki.setThoi_gian(thoigian.getText().toString());
                user.setId(AGConnectAuth.getInstance().getCurrentUser().getUid());
                if(editcannang.getText().toString() != null) {
                    user.setCannang(editcannang.getText().toString());
                    nhatki.setCannang(editcannang.getText().toString());
                }
                else Toast.makeText(this, "chua nhap can nang", Toast.LENGTH_SHORT).show();

                if(edittgioitinh.getText().toString() != null) {
                    user.setGioitinh(edittgioitinh.getText().toString());
                }
                else Toast.makeText(this, "chua nhap gioi tinh", Toast.LENGTH_SHORT).show();

                if(editchieucao.getText().toString() != null){
                    user.setChieucao(editchieucao.getText().toString());
                    nhatki.setChieucao(editchieucao.getText().toString());
                }
                else Toast.makeText(this, "chua nhap chieu cao", Toast.LENGTH_SHORT).show();

                if(editnhiptim.getText().toString() != null){
                    user.setNhiptim(editnhiptim.getText().toString());
                    nhatki.setNhiptim(editnhiptim.getText().toString());
                }
                else Toast.makeText(this, "chua nhap nhip tim", Toast.LENGTH_SHORT).show();

                if(edittuoi.getText().toString() != null){
                    user.setTuoi(edittuoi.getText().toString());
                }
                else Toast.makeText(this, "chua nhap tuoi", Toast.LENGTH_SHORT).show();

                if(editthannhiet.getText().toString() != null){
                    user.setNhietdo(editthannhiet.getText().toString());
                    nhatki.setNhietdo(editthannhiet.getText().toString());
                }
                else Toast.makeText(this, "chua nhap than nhiet", Toast.LENGTH_SHORT).show();

                if(edithuyetap.getText().toString() != null){
                    user.setHuyetap(edithuyetap.getText().toString());
                    nhatki.setHuyetap(edithuyetap.getText().toString());
                }
                else Toast.makeText(this, "chua nhap huyet ap", Toast.LENGTH_SHORT).show();


                user.setTen(edittennguoidung.getText().toString());
                user.setTinhtrang(getTinhTrang());
                nhatki.setTinh_trang(getTinhTrang());
                dbConnect.add(user);
                dbConnect.add(nhatki);
                Toast.makeText(this, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.anhbia:{
                PopupMenu popupMenu = new PopupMenu(this, anhbia);
                popupMenu.getMenuInflater().inflate(R.menu.them_sua_anh, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.menuthayanh){
//                            Intent intent = new Intent(Intent.ACTION_PICK);
//                            intent.setType("image/*");
//                            startActivityForResult(intent, 8888);
                             CropImage.activity()
                                    .setAspectRatio(4,3)
                                    .start(Activity_trangcanhan.this);
                             check = 1;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                break;
            }
            case R.id.avata:{
                PopupMenu popupMenu = new PopupMenu(this, anhdaidien);
                popupMenu.getMenuInflater().inflate(R.menu.them_sua_anh, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.menuthayanh){
//                            Intent intent = new Intent(Intent.ACTION_PICK);
//                            intent.setType("image/*");
//                            startActivityForResult(intent, 7777);
                            CropImage.activity()
                                    .setAspectRatio(1,1)
                                    .start(Activity_trangcanhan.this);
                            check = 2;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                break;
            }
            default:{
                break;
            }
        }
    }


    private void Laydulieu(){
        dbConnect = new DBConnect(Activity_trangcanhan.this);

        Task<CloudDBZoneSnapshot<User>> queryTask = dbConnect.mCloudDBZone.executeQuery(
                CloudDBZoneQuery.where(User.class).equalTo("id", AGConnectAuth.getInstance().getCurrentUser().getUid()),
                CloudDBZoneQuery.CloudDBZoneQueryPolicy.POLICY_QUERY_FROM_CLOUD_ONLY);
        queryTask.addOnSuccessListener(new OnSuccessListener<CloudDBZoneSnapshot<User>>() {
            @Override
            public void onSuccess(CloudDBZoneSnapshot<User> userCloudDBZoneSnapshot) {
                CloudDBZoneObjectList<User> usrCursor = userCloudDBZoneSnapshot.getSnapshotObjects();
                try {
                    if (usrCursor.hasNext()) {
                        user = usrCursor.next();
                        idnguoidung.setText("id: " + user.getId());
                        if(user.getAnhbia_url() != null){
                            Glide.with(Activity_trangcanhan.this).load(user.getAnhbia_url()).into(anhbia);
                        }
                        if (user.getAvata_url() != null){
                            Glide.with(Activity_trangcanhan.this).load(user.getAvata_url()).into(anhdaidien);
                        }
                        if(user.getTen() != null){
                            edittennguoidung.setText(user.getTen());
                        }
                        if(user.getTuoi() != null){
                            edittuoi.setText(String.valueOf(user.getTuoi()));
                        }
                        if(user.getCannang() != null){
                            editcannang.setText(String.valueOf(user.getCannang()));
                        }
                        if(user.getChieucao() != null){
                            editchieucao.setText(String.valueOf(user.getChieucao()));
                        }
                        if(user.getNhietdo() != null){
                            editthannhiet.setText(String.valueOf(user.getNhietdo()));
                        }
                        if(user.getNhiptim() != null){
                            editnhiptim.setText(user.getNhiptim());
                        }
                        if(user.getHuyetap() != null){
                            edithuyetap.setText(user.getHuyetap());
                        }
                        if(user.getGioitinh() != null){
                            edittgioitinh.setText(user.getGioitinh());
                        }
                    }
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && check == 2) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImageUri = result.getUri();
            anhdaidien.setImageURI(mImageUri);
            uploadImage_avt();
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && check == 1) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            mImageUri = result.getUri();
            anhbia.setImageURI(mImageUri);
            uploadImage_anhbia();
        }
    }

    private void uploadImage_avt(){

        if (mImageUri != null){
            reference = AGCStorageManagement.getInstance().getStorageReference("avt\\"
                    + UUID.randomUUID().toString() +".png");
            UploadTask task = reference.putFile(new File( mImageUri.getPath() ));
            task.addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.UploadResult>(){
                @Override
                public void onSuccess(UploadTask.UploadResult uploadResult) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            user.setAvata_url(uri.toString());
                            dbConnect.add(user);
                        }
                    });

                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.UploadResult>() {
                @Override
                public void onComplete(Task<UploadTask.UploadResult> task) {
                }
            });

        } else {
            Toast.makeText(Activity_trangcanhan.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadImage_anhbia(){

        if (mImageUri != null){
            reference = AGCStorageManagement.getInstance().getStorageReference("anhbia\\"
                    + UUID.randomUUID().toString() +".png");
            UploadTask task = reference.putFile(new File( mImageUri.getPath() ));
            task.addOnFailureListener(new OnFailureListener(){
                @Override
                public void onFailure(@NonNull Exception exception) {
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.UploadResult>(){
                @Override
                public void onSuccess(UploadTask.UploadResult uploadResult) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            user.setAnhbia_url(uri.toString());
                            dbConnect.add(user);
                        }
                    });

                }
            }).addOnCompleteListener(new OnCompleteListener<UploadTask.UploadResult>() {
                @Override
                public void onComplete(Task<UploadTask.UploadResult> task) {
                }
            });

        } else {
            Toast.makeText(Activity_trangcanhan.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }
    private String getTinhTrang(){
        String tinhtrang = "khoe manh";
        return tinhtrang;
    }

}