package com.example.dell.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<quakeReport>> {
    private String mUrl;
    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<quakeReport> loadInBackground() {
        if(mUrl==null)
        {
            return null;
        }
        ArrayList<quakeReport> result = QueryUtils.fetchEarthquakeData(mUrl);
        return result;
    }
}
