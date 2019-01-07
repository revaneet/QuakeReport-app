package com.example.dell.quakereport;

public class quakeReport {

    String loc,date,time,url;
    double mag;
    public quakeReport(double a , String b , String c,String d,String e)
    {
        mag=a;
        loc=b;
        date=c;
        time=d;
        url=e;
    }

    public double getMag() {
        return mag;
    }

    public String getLoc() {
        return loc;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUrl(){ return url; }
}
