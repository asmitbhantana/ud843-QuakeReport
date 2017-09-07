package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by asmit on 9/4/17.
 */

public class CustomListViewAdapter extends ArrayAdapter<Data>{
    private ArrayList<Data> datas;
    private Context context;
    LayoutInflater inflater = null;
    public CustomListViewAdapter(@NonNull Context context, @NonNull ArrayList<Data> objects) {
        super(context, 0, objects);
        this.context = context;
        this.datas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if(convertView == null){
           inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView = inflater.inflate(R.layout.custom_item_list,parent,false);
        }

        

        TextView date = convertView.findViewById(R.id.date_text);

        Data mData = datas.get(position);
        date.setText(mData.getDate());

        TextView location = convertView.findViewById(R.id.location_text);
        location.setText(mData.getPlace());


        TextView mag = convertView.findViewById(R.id.mag_text);
        mag.setText(String.valueOf(mData.getMag()));


        Log.d("Asmit","getView "+position);
        return convertView;
    }


    //
//    public CustomListViewAdapter(Context context,ArrayList<Data> dataArrayList){
//        this.context = context;
//        this.datas = dataArrayList;
//    }
//    @Override
//    public int getCount() {
//        return datas.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return datas.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return i;
//    }
//
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View viewRow = inflater.inflate(R.layout.custom_item_list,viewGroup,false);
//        TextView date = viewRow.findViewById(R.id.date_text);
//        Data mData = datas.get(i);
//        date.setText(mData.getDate());
//
//        TextView location = viewRow.findViewById(R.id.location_text);
//        location.setText(mData.getPlace());
//
//
//        TextView mag = viewRow.findViewById(R.id.mag_text);
//        mag.setText("C");
//
//
//        Log.d("Asmit","getView "+i);
//        return viewRow;
//    }




}
