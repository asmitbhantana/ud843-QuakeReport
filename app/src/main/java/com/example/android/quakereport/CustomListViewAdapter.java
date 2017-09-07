package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
            Log.d("Asmit","Layout inflater is null");
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

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) mag.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(mData.getMag());
        Log.d("Asmit","COlor is "+magnitudeCircle);
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        TextView time = convertView.findViewById(R.id.time_view_text);
        time.setText(mData.getTime());

        TextView direction = convertView.findViewById(R.id.direction_view);
        direction.setText(mData.getDirection());

        Log.d("Asmit","getView "+position);
        return convertView;
    }

    private int getMagnitudeColor(double mDataMag) {

        int magnitudeColorResourceId;
        if(mDataMag<=2){
            magnitudeColorResourceId = R.color.magnitude1;
        }else if(mDataMag>2&&mDataMag<=3){
             magnitudeColorResourceId = R.color.magnitude2;
        }else if(mDataMag>3&&mDataMag<=4){
            magnitudeColorResourceId =  R.color.magnitude3;
        }else if(mDataMag>4&&mDataMag<=5){
            magnitudeColorResourceId =  R.color.magnitude4;
        }else if(mDataMag>5&&mDataMag<=6){
            magnitudeColorResourceId =  R.color.magnitude5;
        }else if(mDataMag>6&&mDataMag<=7){
            magnitudeColorResourceId =  R.color.magnitude6;
        }else if(mDataMag>7&&mDataMag<=8){
            magnitudeColorResourceId =  R.color.magnitude7;
        }else if(mDataMag>8&&mDataMag<=9){
            magnitudeColorResourceId =  R.color.magnitude8;
        }else if(mDataMag>9&&mDataMag<=10){
            magnitudeColorResourceId =  R.color.magnitude9;
        }

        else {
            magnitudeColorResourceId =  R.color.colorPrimaryDark;
        }


        return  ContextCompat.getColor(getContext(), magnitudeColorResourceId);

    }

}
