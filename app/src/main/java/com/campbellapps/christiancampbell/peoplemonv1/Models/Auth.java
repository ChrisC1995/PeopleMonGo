package com.campbellapps.christiancampbell.peoplemonv1.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by christiancampbell on 11/7/16.
 */

public class Auth {

    @SerializedName("FullName")
    private String Fullname;

    @SerializedName("token")
    private String token;

    @SerializedName("expiration")
    private Date expiration;

    @SerializedName("AvatarBase64")
    private String image;

    @SerializedName("ApiKey")
    private String ApiKey;

    @SerializedName("grant_type")
    private String grant;

    @SerializedName("email")
    private String email;

    @SerializedName("Latitude")
    private double latitude;

    @SerializedName("Longitude")
    private double longitude;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("CaughtUserId")
    private String CaughtUserId;

    @SerializedName("RadiusInMeters")
    private int RadiusInMeters;

    @SerializedName("UserId")
    private String id;

    @SerializedName("Created")
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCaughtUserId() {
        return CaughtUserId;
    }

    public void setCaughtUserId(String caughtUserId) {
        CaughtUserId = caughtUserId;
    }

    public Integer getRadiusInMeters() {
        return RadiusInMeters;
    }

    public void setRadiusInMeters(Integer radiusInMeters) {
        RadiusInMeters = radiusInMeters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Auth(String CaughtUserId, int radiusInMeters){
        this.CaughtUserId = CaughtUserId;
        this.RadiusInMeters = radiusInMeters;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getApiKey() {
        return ApiKey;
    }

    public void setApiKey(String apiKey) {
        ApiKey = apiKey;
    }

    @SerializedName("password")
    private String password;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName(".expires")
    private Date expires;

    public Auth(){

    }

    public Auth(String grant, String email, String password){
        this.email = email;
        this.password = password;
        this.grant = grant;
    }

    public Auth(String email, String Fullname, String password, String image, String ApiKey){
        this.Fullname = Fullname;
        this.password = password;
        this.email = email;
        this.ApiKey = "iOSandroid301november2016";
        this.image = "";
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Auth(Double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Auth(String Fullname, String image){
        this.Fullname = Fullname;
        this.image = image;
    }


    public String getGrant() {
        return grant;
    }

    public void setGrant(String grant) {
        this.grant = grant;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public String getAccessToken() {

        return accessToken;
    }

    public void setAccessToken(String access_token) {
        this.accessToken = access_token;
    }
}
