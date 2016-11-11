package com.campbellapps.christiancampbell.peoplemonv1.Stages;

import android.app.Application;

import com.campbellapps.christiancampbell.peoplemonv1.PeoplemonApplication;
import com.campbellapps.christiancampbell.peoplemonv1.R;
import com.campbellapps.christiancampbell.peoplemonv1.Riggers.VerticalSlideRigger;

/**
 * Created by christiancampbell on 11/7/16.
 */

public class RegisterStage extends IndexedStage {
    private final VerticalSlideRigger rigger;

    public RegisterStage(Application context){
        super(RegisterStage.class.getName());
        this.rigger = new VerticalSlideRigger(context);
    }

    public RegisterStage(){
        this(PeoplemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.register_view; // returns our xml layout. Says which view the user is going to see. stage, xml, java
    }

    @Override
    public Rigger getRigger() {
        return rigger; //returns our rigger that is already defined.
    }
}
