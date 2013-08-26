package com.pellegrini.crimalintent.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Fabricio on 24/08/13.
 */
public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        //Generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String pTitle) {
        this.mTitle = pTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date pDate) {
        mDate = pDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean pSolved) {
        mSolved = pSolved;
    }
}