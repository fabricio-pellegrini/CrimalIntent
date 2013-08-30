package com.pellegrini.crimalintent;

import android.support.v4.app.Fragment;

/**
 * Created by Fabricio on 26/08/13.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
