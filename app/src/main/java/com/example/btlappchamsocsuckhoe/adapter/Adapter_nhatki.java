package com.example.btlappchamsocsuckhoe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.btlappchamsocsuckhoe.R;
import com.example.btlappchamsocsuckhoe.model.*;

import com.example.btlappchamsocsuckhoe.model.DBConnect;

import java.util.ArrayList;

public class Adapter_nhatki extends BaseAdapter {
    private Context context;
    private ArrayList<nhatki> v;
    private int layout;
    private DBConnect dbConnect;

    public Adapter_nhatki(Context context, int layout, ArrayList<nhatki> v) {
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
        TextView thoigian,tinhtrangsk, chitiet;
        public Viewholder() {}
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Adapter_nhatki.Viewholder viewholder ;

        if(view == null){
            viewholder = new Adapter_nhatki.Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewholder.thoigian = (TextView) view.findViewById(R.id.thoigian);
            viewholder.tinhtrangsk = (TextView) view.findViewById(R.id.tvtinhtrang);
            viewholder.chitiet = (TextView) view.findViewById(R.id.tvchitietsk);

            view.setTag(viewholder);
        }
        else {
            viewholder = (Adapter_nhatki.Viewholder) view.getTag();
        }

        dbConnect = new DBConnect(context);

        viewholder.thoigian.setText(v.get(i).getThoi_gian());
        viewholder.tinhtrangsk.setText(v.get(i).getTinh_trang());

//        viewholder.chitiet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        return view;
    }
}
