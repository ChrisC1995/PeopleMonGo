package com.campbellapps.christiancampbell.peoplemonv1.Stages;

import com.davidstemmer.screenplay.stage.XmlStage;

/**
 * Created by christiancampbell on 11/7/16.
 */

public abstract class IndexedStage extends XmlStage {

    public final String id; // gives an id to reference as we go back and forth.

    protected IndexedStage(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexedStage that = (IndexedStage) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
