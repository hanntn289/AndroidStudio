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
import com.example.btlappchamsocsuckhoe.R;
import com.example.btlappchamsocsuckhoe.model.DBConnect;
import com.example.btlappchamsocsuckhoe.model.User;
import com.example.btlappchamsocsuckhoe.model.theodoi;
import com.huawei.agconnect.auth.AGConnectAuth;

import java.util.ArrayList;

public class Adapter_dstheodoi extends BaseAdapter {

    private Context context;
    private ArrayList<User> v;
    private int layout;
    private DBConnect dbConnect;

    public Adapter_dstheodoi(Context context, int layout, ArrayList<User> v) {
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
        TextView tvten,tinhtrangsk;
        Button btnhuytheodoi;
        public Viewholder() {}
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Adapter_dstheodoi.Viewholder viewholder ;

        if(view == null){
            viewholder = new Adapter_dstheodoi.Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewholder.imgavt = (ImageView) view.findViewById(R.id.imgavt);
            viewholder.tvten = (TextView) view.findViewById(R.id.thoigian);
            viewholder.tinhtrangsk = (TextView) view.findViewById(R.id.tvtinhtrangsk);
            viewholder.btnhuytheodoi = (Button) view.findViewById(R.id.btnbotheodoi);

            view.setTag(viewholder);
        }
        else {
            viewholder = (Adapter_dstheodoi.Viewholder) view.getTag();
        }

        if(v.get(i).getAvata_url() != null)  Glide.with(context).load(v.get(i).getAvata_url()).into(viewholder.imgavt);
        else viewholder.imgavt.setImageResource(R.drawable.anhavata);
        viewholder.tvten.setText(v.get(i).getTen());
        viewholder.tinhtrangsk.setText(v.get(i).getTinhtrang());

        viewholder.btnhuytheodoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                //xóa người dùng khỏi bảng theodoi
                dbConnect = new DBConnect(context);
                theodoi theodoi = new theodoi();
                theodoi.setMy_id(AGConnectAuth.getInstance().getCurrentUser().getUid());
                theodoi.setFollow_id(v.get(i).getId());
                dbConnect.delete(theodoi);
                context.startActivity(new Intent(context, Activity_dstheodoi.class));
            }
        });


        return view;
    }
}
