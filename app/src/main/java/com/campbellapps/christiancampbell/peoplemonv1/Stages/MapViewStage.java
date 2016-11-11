package com.campbellapps.christiancampbell.peoplemonv1.Stages;

import android.app.Application;

import com.campbellapps.christiancampbell.peoplemonv1.PeoplemonApplication;
import com.campbellapps.christiancampbell.peoplemonv1.R;
import com.campbellapps.christiancampbell.peoplemonv1.Riggers.SlideRigger;
import com.google.android.gms.maps.MapView;

/**
 * Created by christiancampbell on 11/7/16.
 */

public class MapViewStage extends IndexedStage {
    private final SlideRigger rigger;

    public MapViewStage(Application context){
        super(MapView.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public MapViewStage(){
        this(PeoplemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.map_view; // returns our xml layout. Says which view the user is going to see. stage, xml, java
    }

    @Override
    public Rigger getRigger() {
        return rigger; //returns our rigger that is already defined.
    }
}
