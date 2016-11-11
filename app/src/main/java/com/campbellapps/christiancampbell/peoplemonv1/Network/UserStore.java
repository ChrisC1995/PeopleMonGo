package com.campbellapps.christiancampbell.peoplemonv1.Network;

import android.content.Context;
import android.content.SharedPreferences;

import com.campbellapps.christiancampbell.peoplemonv1.PeoplemonApplication;
import com.campbellapps.christiancampbell.peoplemonv1.Components.Constants;

import java.util.Date;

import static android.R.id.edit;

/**
 * Created by christiancampbell on 10/31/16.
 */

public class UserStore {
    private static UserStore ourInstance = new UserStore(); //holds reference to itself. only place created.

    public static UserStore getInstance(){
        return ourInstance;

    }

    private SharedPreferences sharedPrefs = PeoplemonApplication.getInstance().getSharedPreferences("BudgetPrefs", Context.MODE_PRIVATE);

    public String getToken(){
        String theToken = sharedPrefs.getString(Constants.token, null);
        return theToken;
    }

    public void setToken(String token){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(Constants.token, token);
        editor.apply();
    }


    public Date getTokenExpiration(){
        Long expiration = sharedPrefs.getLong(Constants.tokenExpiration, 0);
        Date date = new Date(expiration);
        if (date.before(new Date())){
            return null;
        }
        return date;
    }

    public void setTokenExpriation(Date expriation){
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putLong(Constants.tokenExpiration, expriation.getTime());
        editor.apply();
    }

}
