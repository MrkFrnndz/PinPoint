package com.example.markfernandez.pinpoint.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AstroNuts on 1/13/2017.
 */
public class UserPost {
   // String postEmotion;
    String authorName;
    String userId;
    int postEmotion;
    String postDescription;
    HashMap<String, Object> dateCreated;
    double lat;


    public UserPost() {
    }




    public UserPost(String authorName, String userId, int postEmotion, String postDescription,String dateCreated, double lat) {
        this.authorName = authorName;
        this.userId = userId;
        this.postEmotion = postEmotion;
        this.postDescription = postDescription;
        this.lat = lat;
        HashMap<String, Object> timestampObj = new HashMap<>();
        timestampObj.put("dateCreated", ServerValue.TIMESTAMP);
        this.dateCreated = timestampObj;

    }

    public String getAuthorName() { return authorName; }

    public String getUserId(){ return userId; }

    public int getPostEmotion() {
        return postEmotion;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public HashMap<String, Object> getDateCreated() {
//        //If there is a dateCreated object already, then return that
//        if (dateCreated != null) {
//            return dateCreated;
//        }
//        //Otherwise make a new object set to ServerValue.TIMESTAMP
//        HashMap<String, Object> dateCreated = new HashMap<String, Object>();
//        dateCreated.put("dateCreated", ServerValue.TIMESTAMP);

        return dateCreated;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Exclude
    public long getDateCreatedLong(){
        return (long)dateCreated.get("dateCreated");

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("postEmotion", postEmotion);
        result.put("authorName",authorName);
        result.put("postDescription", postDescription);
        result.put("dateCreated", dateCreated);
        result.put("lat",lat);

        return result;
    }
}


