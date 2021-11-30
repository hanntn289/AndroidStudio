package com.example.btlappchamsocsuckhoe.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.btlappchamsocsuckhoe.Activity_dstheodoi;
import com.example.btlappchamsocsuckhoe.Activity_dsyeucau;
import com.example.btlappchamsocsuckhoe.R;
import com.example.btlappchamsocsuckhoe.model.DBConnect;
import com.huawei.agconnect.auth.AGConnectAuth;
import com.example.btlappchamsocsuckhoe.model.*;


import java.util.ArrayList;

public class Adapter_yeucau extends BaseAdapter {

    private Context context;
    private ArrayList<User> v;
    private int layout;
    private DBConnect dbConnect;

    public Adapter_yeucau(Context context, int layout, ArrayList<User> v) {
        this.context = context;
        this.v = v;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return v.size();
    }

    @Override
    public Object getItem(int i) {
        return v.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class Viewholder{
        ImageView imgavt;
        TextView  tvten;
        Button btndongykb;
        Button btnhuydongykb;
        public Viewholder() {}
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewholder viewholder ;

        if(view == null){
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewholder.imgavt = (ImageView) view.findViewById(R.id.imgavt);
            viewholder.tvten = (TextView) view.findViewById(R.id.thoigian);
            viewholder.btnhuydongykb = (Button)view.findViewById(R.id.btnhuydongykb);
            viewholder.btndongykb = (Button)view.findViewById(R.id.btndongykb);
            view.setTag(viewholder);
        }
        else {
            viewholder = (Viewholder) view.getTag();
        }

        dbConnect = new DBConnect(context);
        if(v.get(i).getAvata_url() != null)  Glide.with(context).load(v.get(i).getAvata_url()).into(viewholder.imgavt);
        else viewholder.imgavt.setImageResource(R.drawable.anhavata);
        viewholder.tvten.setText(v.get(i).getTen());

        viewholder.btndongykb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                dbConnect = new DBConnect(context);
                //đồng ý thêm vào bảng theo dõi
                theodoi theodoi = new theodoi();
                theodoi.setMy_id(v.get(i).getId());
                theodoi.setFollow_id(AGConnectAuth.getInstance().getCurrentUser().getUid());
                dbConnect.add(theodoi);
                dbConnect = new DBConnect(context);
                //xóa tên trong bảng yêu cầu
                yeucau yeucau = new yeucau();
                yeucau.setId_nguoi_gui(v.get(i).getId());
                yeucau.setId_nguoi_nhan(AGConnectAuth.getInstance().getCurrentUser().getUid());
                dbConnect.delete(yeucau);
                context.startActivity(new Intent(context, Activity_dsyeucau.class));
            }
        });

        viewholder.btnhuydongykb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                //xóa tên trong bảng yêu cầu
                yeucau yeucau = new yeucau();
                yeucau.setId_nguoi_gui(v.get(i).getId());
                yeucau.setId_nguoi_nhan(AGConnectAuth.getInstance().getCurrentUser().getUid());
                dbConnect.delete(yeucau);
                context.startActivity(new Intent(context, Activity_dsyeucau.class));
            }
        });
        return view;
    }

}
