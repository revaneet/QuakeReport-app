package com.example.dell.quakereport;


import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<quakeReport>> {

    static final String USGS_REQUEST_URL="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    static  final int EARTHQUAKE_LOADER_INT= 1;
    quakeReportAdapter reportAdapter;
    TextView memptyState;
    ProgressBar mprogressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ArrayList<quakeReport> alReport=QueryUtils.fetchEarthquakeData(USGS_REQUEST_URL);

        ListView earthquakeListView=findViewById(R.id.listView);
        memptyState=findViewById(R.id.emptyState);

        earthquakeListView.setEmptyView(memptyState);

        mprogressBar=findViewById(R.id.progressBar);

//        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
//        task.execute(USGS_REQUEST_URL);

        //check network connectivity
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
        {
            LoaderManager loaderManager= getLoaderManager();
            loaderManager.initLoader(EARTHQUAKE_LOADER_INT,null,this);
        }
        else {
            mprogressBar.setVisibility(View.GONE);
            memptyState.setText("Check your internet connection.");
        }

        reportAdapter=new quakeReportAdapter(this,0,new ArrayList<quakeReport>());


        earthquakeListView.setAdapter(reportAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                quakeReport currentEarthquake = reportAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });


    }

    @Override
    public Loader<ArrayList<quakeReport>> onCreateLoader(int i, Bundle bundle) {

        return new EarthquakeLoader(this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<quakeReport>> loader, ArrayList<quakeReport> data) {
        mprogressBar.setVisibility(View.GONE);
        reportAdapter.clear();
        if(data!=null && !data.isEmpty()) {
            reportAdapter.addAll(data);
        }
        memptyState.setText("Sorry ! No earthquakes found.");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<quakeReport>> loader) {
        reportAdapter.clear();

    }

    //    public class EarthquakeAsyncTask extends AsyncTask<String ,Void,ArrayList<quakeReport>>
//    {
//
//
//        @Override
//        protected ArrayList<quakeReport> doInBackground(String... urls) {
//            if (urls.length < 1 || urls[0] == null) {
//                return null;
//            }
//
//            ArrayList<quakeReport> result = QueryUtils.fetchEarthquakeData(urls[0]);
//            return result;
//
//        }
//        @Override
//        protected void onPostExecute(ArrayList<quakeReport> data) {
//            reportAdapter.clear();
//            if(data!=null && !data.isEmpty())
//            {
//                reportAdapter.addAll(data);
//            }
//
//        }
//    }

}
