package com.campbellapps.christiancampbell.peoplemonv1.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by christiancampbell on 11/7/16.
 */

public class Account {

    @SerializedName("FullName")
    private String Fullname;

    @SerializedName("Password")
    private String password;

    @SerializedName("Email")
    private String email;

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

    public Account(){

    }

    public Account(String email, String Fullname, String password, String image, String ApiKey){
        this.Fullname = Fullname;
        this.password = password;
        this.email = email;
        this.ApiKey = "iOSandroid301november2016";
        this.image = "";
    }

    public Account(String grant, String Fullname, String password){
        this.Fullname = Fullname;
        this.password = password;
        this.grant = "password";
    }


    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
}
