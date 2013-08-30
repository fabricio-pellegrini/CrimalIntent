package com.pellegrini.crimalintent.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Fabricio on 26/08/13.
 */
public class CrimeLab {

    private ArrayList<Crime> mCrimes;

    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    private CrimeLab(Context pAppContext) {
        mAppContext = pAppContext;
        mCrimes = new ArrayList<Crime>();

        for(int i = 1; i <= 100; i++){
            Crime c = new Crime();
            c.setTitle("Crime #"+i);
            c.setSolved(i % 2 == 0);
            mCrimes.add(c);
        }
    }

    public static CrimeLab instance(Context pAppContext) {
        if(sCrimeLab == null)
            sCrimeLab = new CrimeLab(pAppContext.getApplicationContext());
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID pId) {
        for(Crime c : mCrimes) {
            if(c.getId().equals(pId))
                return c;
        }
        return null;
    }
}
