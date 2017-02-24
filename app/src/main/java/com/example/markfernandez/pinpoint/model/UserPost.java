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

    String authorImage,authorName,userId,postDescription;
    int postEmotion,postLikes;
    HashMap<String,Object> dateCreated;
    double lat,lng;


    public UserPost(){

    }

    public UserPost(String authorImage, String authorName, String userId, String postDescription, int postEmotion, HashMap<String, Object> dateCreated, double lat, double lng) {
        this.authorImage = authorImage;
        this.authorName = authorName;
        this.userId = userId;
        this.postDescription = postDescription;
        this.postEmotion = postEmotion;

        HashMap<String, Object> timestampObj = new HashMap<>();
        timestampObj.put("dateCreated", ServerValue.TIMESTAMP);
        this.dateCreated = timestampObj;

        this.lat = lat;
        this.lng = lng;
    }

    public int getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(int postLikes) {
        this.postLikes = postLikes;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public int getPostEmotion() {
        return postEmotion;
    }

    public void setPostEmotion(int postEmotion) {
        this.postEmotion = postEmotion;
    }

    public HashMap<String, Object> getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(HashMap<String, Object> dateCreated) {
        this.dateCreated = dateCreated;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Exclude
    public long getDateCreatedLong(){
        return (long)dateCreated.get("dateCreated");

    }
    long dateCreatedLong;
    public void setDateCreatedLong(long dateCreatedLong){
        this.dateCreatedLong = dateCreatedLong;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("postEmotion", postEmotion);
        result.put("authorImage",authorImage);
        result.put("authorName",authorName);
        result.put("postDescription", postDescription);
        result.put("dateCreated", dateCreated);
        result.put("lat",lat);
        result.put("lng",lng);

        return result;
    }

// String postEmotion;
//    String authorName;
//    String userId;
//    int postEmotion;
//    String postDescription;
//    HashMap<String, Object> dateCreated;
//    double lat;
//    double lng;
//
//
//    public UserPost() {
//    }
//
//
//
//
//    public UserPost(String authorName, String userId, int postEmotion, String postDescription,String dateCreated,double lat, double lng) {
//        this.authorName = authorName;
//        this.userId = userId;
//        this.postEmotion = postEmotion;
//        this.postDescription = postDescription;
//        HashMap<String, Object> timestampObj = new HashMap<>();
//        timestampObj.put("dateCreated", ServerValue.TIMESTAMP);
//        this.dateCreated = timestampObj;
//        this.lat = lat;
//        this.lng = lng;
//
//    }
//
//    public String getAuthorName() { return authorName; }
//
//    public String getUserId(){ return userId; }
//
//    public int getPostEmotion() {
//        return postEmotion;
//    }
//
//    public String getPostDescription() {
//        return postDescription;
//    }
//
//    public HashMap<String, Object> getDateCreated() {
////        //If there is a dateCreated object already, then return that
////        if (dateCreated != null) {
////            return dateCreated;
////        }
////        //Otherwise make a new object set to ServerValue.TIMESTAMP
////        HashMap<String, Object> dateCreated = new HashMap<String, Object>();
////        dateCreated.put("dateCreated", ServerValue.TIMESTAMP);
//
//        return dateCreated;
//    }
//
//    public double getLat() {
//        return lat;
//    }
//
////    public void setLat(double lat) {
////        this.lat = lat;
////    }
//
//    public double getLng() {
//        return lng;
//    }
//
////    public void setLng(double lng) {
////        this.lng = lng;
////    }
//
//    @Exclude
//    public long getDateCreatedLong(){
//        return (long)dateCreated.get("dateCreated");
//
//    }
//
//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("userId", userId);
//        result.put("postEmotion", postEmotion);
//        result.put("authorName",authorName);
//        result.put("postDescription", postDescription);
//        result.put("dateCreated", dateCreated);
//        result.put("lat",lat);
//        result.put("lng",lng);
//
//        return result;
//    }
}


