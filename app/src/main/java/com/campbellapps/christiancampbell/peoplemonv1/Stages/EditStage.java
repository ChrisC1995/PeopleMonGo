package com.campbellapps.christiancampbell.peoplemonv1.Stages;

import android.app.Application;

import com.campbellapps.christiancampbell.peoplemonv1.PeoplemonApplication;
import com.campbellapps.christiancampbell.peoplemonv1.R;
import com.campbellapps.christiancampbell.peoplemonv1.Riggers.SlideRigger;

/**
 * Created by christiancampbell on 11/10/16.
 */

public class EditStage extends IndexedStage {
    private final SlideRigger rigger;

    public EditStage(Application context){
        super(EditStage.class.getName());
        this.rigger = new SlideRigger(context);
    }

    public EditStage(){
        this(PeoplemonApplication.getInstance());
    }

    @Override
    public int getLayoutId() {
        return R.layout.edit_view; // returns our xml layout. Says which view the user is going to see. stage, xml, java
    }

    @Override
    public Rigger getRigger() {
        return rigger; //returns our rigger that is already defined.
    }
}
