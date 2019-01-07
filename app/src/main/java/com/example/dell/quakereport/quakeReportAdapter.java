package com.example.dell.quakereport;

import android.content.Context;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class quakeReportAdapter extends ArrayAdapter<quakeReport> {
    public String Location_seperator=" of ";
    String primaryLoc,offsetLoc;
    String originalLoc;

    public quakeReportAdapter(@NonNull Context context, int resource, @NonNull List<quakeReport> quakeReports) {
        super(context, resource, quakeReports);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem= convertView;
        if(listItem==null)
        {
            listItem= LayoutInflater.from(getContext()).inflate(R.layout.listitem ,parent,false);
        }
        quakeReport currentquakeReport=getItem(position);

        //set Magnitute---------------------------------------------
        TextView magnitude=listItem.findViewById(R.id.mag);
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentquakeReport.getMag());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);



        double magDouble=currentquakeReport.getMag();
        String formattedMag=convertMag(magDouble);
        magnitude.setText(formattedMag);

        //set primary and offset location---------------------------
        originalLoc=currentquakeReport.getLoc();

        splitLocation();

        TextView offset=listItem.findViewById(R.id.offsetLoc);
        offset.setText(offsetLoc);
        TextView primary=listItem.findViewById(R.id.primaryLoc);
        primary.setText(primaryLoc);

        //set date---------------------------------------------------
        TextView date=listItem.findViewById(R.id.date);
        date.setText(currentquakeReport.getDate());

        //set time---------------------------------------------------
        TextView time=listItem.findViewById(R.id.time);
        time.setText(currentquakeReport.getTime());

        return listItem;
    }
    public void splitLocation()
    {
        if(originalLoc.contains(Location_seperator))
        {
            String parts[]=originalLoc.split(Location_seperator);
            offsetLoc=parts[0]+Location_seperator;
            primaryLoc=parts[1];
        }
        else
        {
            offsetLoc="Near the";
            primaryLoc=originalLoc;
        }

    }
    public String convertMag(double a)
    {
        java.text.DecimalFormat decimalFormat=new java.text.DecimalFormat("0.0");
        return decimalFormat.format(a);
    }
    public int getMagnitudeColor(double mag)
    {
        int colorInt;
        if(mag>=0 && mag<2)
        {
            colorInt=ContextCompat.getColor(getContext(),R.color.magnitude1);
        }
        else if(mag>=2 && mag<3)
        {
            colorInt=ContextCompat.getColor(getContext(),R.color.magnitude2);
        }
        else if(mag>=3 && mag<4)
        {
            colorInt=ContextCompat.getColor(getContext(),R.color.magnitude3);
        }
        else if(mag>=4 && mag<5)
        {
            colorInt=ContextCompat.getColor(getContext(),R.color.magnitude4);
        }
        else if(mag>=5 && mag<6)
        {
            colorInt=ContextCompat.getColor(getContext(),R.color.magnitude5);
        }
        else if(mag>=6 && mag<7)
        {
            colorInt=ContextCompat.getColor(getContext(),R.color.magnitude6);
        }
        else if(mag>=7 && mag<8)
        {
            colorInt=ContextCompat.getColor(getContext(),R.color.magnitude7);
        }
        else if(mag>=8 && mag<9)
        {
            colorInt=ContextCompat.getColor(getContext(),R.color.magnitude8);
        }
        else if(mag>=9 && mag<10)
        {
            colorInt=ContextCompat.getColor(getContext(),R.color.magnitude9);
        }
        else
        {
            colorInt=ContextCompat.getColor(getContext(),R.color.magnitude10plus);
        }


        return colorInt;
    }
}
