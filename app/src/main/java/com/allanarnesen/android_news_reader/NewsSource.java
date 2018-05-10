package com.allanarnesen.android_news_reader;

/**
 * Created by allan on 10.05.2018.
 *
 * Simple class describing a News Source
 */

public class NewsSource {
    public int Source_Id;



    public String Source_Name;
    public String Source_URL;

    public NewsSource(){

    }

    public NewsSource(int Source_Id, String Source_Name, String Source_URL){
        this.Source_Id = Source_Id;
        this.Source_Name = Source_Name;
        this.Source_URL = Source_URL;
    }

    public int getSource_Id() {
        return Source_Id;
    }

    public void setSource_Id(int source_Id) {
        Source_Id = source_Id;
    }

    public String getSource_Name() {
        return Source_Name;
    }

    public void setSource_Name(String source_Name) {
        Source_Name = source_Name;
    }

    public String getSource_URL() {
        return Source_URL;
    }

    public void setSource_URL(String source_URL) {
        Source_URL = source_URL;
    }
}
