/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;


import android.content.Context;

import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;

import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;


import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Data>>{

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String dataUrl = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    ListView earthquakeListView = null;
    public ArrayList<Data> earthquakeList= new ArrayList<>();
    public CustomListViewAdapter customListViewAdapter;
    TextView noDataTextView = null;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        earthquakeListView = (ListView) findViewById(R.id.list);
        noDataTextView = (TextView) findViewById(R.id.no_data_text);
        progressBar  = (ProgressBar) findViewById(R.id.loadingProgressBar);

        if (!isOnline()) {
           NoInternetFrament internetFrament = new NoInternetFrament();
            FragmentManager fragmentManager = getSupportFragmentManager();
            internetFrament.show(fragmentManager,"Asmit");
            progressBar.setVisibility(View.INVISIBLE);
        } else {

            progressBar.setVisibility(View.VISIBLE);

            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(1,null,this);


            customListViewAdapter = new CustomListViewAdapter(this.getApplicationContext(), earthquakeList);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(customListViewAdapter);

            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(earthquakeList.get(i).getUrl()));
                    startActivity(intent);
                }
            });



        }


    }


    private boolean isOnline() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetInfo = mConnectivityManager.getActiveNetworkInfo();
        if(mNetInfo!=null&&mNetInfo.isConnected()){
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public Loader<List<Data>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeDataLoaders(this,dataUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Data>> loader, List<Data> data) {
        customListViewAdapter.clear();
        if (data!=null && !data.isEmpty()){
            customListViewAdapter.addAll(data);

        }
        earthquakeListView.setEmptyView(noDataTextView);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Data>> loader) {
        customListViewAdapter.clear();
    }


    static class EarthquakeDataLoaders extends AsyncTaskLoader<List<Data>>{

        private String url;

        public EarthquakeDataLoaders(Context context,String url) {
            super(context);
            this.url = url;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public List<Data> loadInBackground() {
            Utils utils = new Utils();
            return utils.fetchData(url);
        }
    }

}

