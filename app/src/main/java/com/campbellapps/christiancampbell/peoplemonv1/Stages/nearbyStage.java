package com.campbellapps.christiancampbell.peoplemonv1.Stages;

import android.app.Application;

import com.campbellapps.christiancampbell.peoplemonv1.PeoplemonApplication;
import com.campbellapps.christiancampbell.peoplemonv1.R;
import com.campbellapps.christiancampbell.peoplemonv1.Riggers.SlideRigger;
import com.google.android.gms.maps.MapView;

/**
 * Created by christiancampbell on 11/11/16.
 */

public class nearbyStage extends IndexedStage {
    private final SlideRigger rigger;

    public nearbyStage(Application context) {
        super(MapView.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public nearbyStage() {
        this(PeoplemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.nearby_radar; // returns our xml layout. Says which view the user is going to see. stage, xml, java
    }

    @Override
    public Rigger getRigger() {
        return rigger; //returns our rigger that is already defined.
    }
}
