package com.campbellapps.christiancampbell.peoplemonv1;

import android.app.Application;

import com.campbellapps.christiancampbell.peoplemonv1.Stages.MapViewStage;

import flow.Flow;
import flow.History;

/**
 * Created by christiancampbell on 11/7/16.
 */

public class PeoplemonApplication extends Application {
    private static PeoplemonApplication application;
    public final Flow mainFlow =
            new Flow(History.single(new MapViewStage())); // flow works off of stack information which are in History. stage calls xml file which calls java file. .single means only thing in this history.

    public static final String API_BASE_URL = "https://efa-peoplemon-api.azurewebsites.net:443/";


    @Override
    public void onCreate() {
        super.onCreate(); // always call super

        application = this;
    }

    public static PeoplemonApplication getInstance(){
        return application;
    }

    public static Flow getMainFlow(){
        return getInstance().mainFlow;
    }
}
