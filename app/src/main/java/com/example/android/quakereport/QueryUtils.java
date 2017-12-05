package com.example.android.quakereport; /**
 * Created by asmit on 9/7/17.
 */



import android.os.AsyncTask;
import android.util.Log;

import com.example.android.quakereport.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public final class QueryUtils {

    public QueryUtils() {
    }
    public static ArrayList<Data> extractEarthquakes(String JSON_RESPONSE) {
       ArrayList<Data> earthquakes = new ArrayList<>();


        try {
                JSONObject mJason = new JSONObject(JSON_RESPONSE);
                JSONArray feature = mJason.getJSONArray("features");
                Log.d("Asmit", String.valueOf(feature.length()));
                for(int i=0;i<feature.length();i++){
                    Data data =new Data();
                    JSONObject mFeatures = feature.getJSONObject(i);
                    JSONObject properties = mFeatures.getJSONObject("properties");



                    String url = properties.getString("url");
                    String place = properties.getString("place");

                    int splitPoint = getLocationDividingPoint(place);
                    Log.d("Asmit", "Splittt "+String.valueOf(splitPoint));
                    String direction = place.substring(0,splitPoint+3);

                    String location = place.substring(splitPoint+3,place.length());

                    double mag = properties.getDouble("mag");

                    String date = dateInWords(properties.getLong("time"));
                    String time = timeInWords(properties.getLong("time"));

                    Log.d("Asmit",i + "place = "+place +"date= "+date + "mag= "+mag);
                    data.setDate(date);
                    data.setUrl(url);
                    data.setTime(time);
                    data.setPlace(location);
                    data.setDirection(direction);
                    data.setMag(mag);
                    earthquakes.add(i,data);

                }
        } catch (JSONException e) {
            Log.d("Asmit","Error in jason parsing");

        }


        return earthquakes;




    }
    public static String dateInWords(long time){
        SimpleDateFormat date = new SimpleDateFormat("EEE, MMM d, ''yy");
        return date.format(time);
    }
    public static  String timeInWords(long time) {
        SimpleDateFormat mTime = new SimpleDateFormat("h:mm a");
        return mTime.format(time);
    }

    public static int getLocationDividingPoint(String location){
        String of = "of";
        if (location.contains(of)) {
            return location.lastIndexOf(of);
        }
        return 5;
    }

}

class Utils{

    public ArrayList<Data> fetchData(String requestUrl){
            URL url = createUrl(requestUrl);
            if (url!=null)
            {
               String jsonResponse = makeHttpRequest(url);

               ArrayList<Data> parsedList= QueryUtils.extractEarthquakes(jsonResponse);
                return parsedList;

            }

            return null;

    }

    private String makeHttpRequest(URL url) {
        String jsonResponse = " ";
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            //This code is for requesting the data from url provided
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("Asmit","This is a connection problems!");
        }


        // If the request was successful (response code 200),
        try {
            if(httpURLConnection.getResponseCode() == 200 ){
                //InputStream reads the response by a char at a time
                 inputStream =  httpURLConnection.getInputStream();
                 Log.d("Asmit","Result from InputStream"+inputStream.toString());
                 jsonResponse = readFromStream(inputStream);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) {
        if(inputStream==null){
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader reader  = new BufferedReader(inputStreamReader);
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (line!=null){
            stringBuilder.append(line);
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return stringBuilder.toString();

    }

    private URL createUrl(String requestUrl) {
        if (requestUrl.length()>1){

            try {
                URL url = new URL(requestUrl);
                return url;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        }
        return null;

    }


}

