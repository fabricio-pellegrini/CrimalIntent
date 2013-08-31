package com.pellegrini.crimalintent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.pellegrini.crimalintent.model.Crime;
import com.pellegrini.crimalintent.model.CrimeLab;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Fabricio on 30/08/13.
 */
public class CrimePagerActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private ArrayList<Crime> mCrimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        mCrimes = CrimeLab.instance(this).getCrimes();

        FragmentManager lFragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter( new FragmentStatePagerAdapter(lFragmentManager) {
            @Override
            public Fragment getItem(int i) {
                Crime lCrime = mCrimes.get(i);
                return CrimeFragment.newInstance(lCrime.getId());
            }

            @Override
            public int getCount() {
                return  mCrimes.size();
            }
        });

        mViewPager.setOnPageChangeListener( new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                Crime lCrime = mCrimes.get(i);
                if(lCrime.getTitle() != null) {
                    setTitle(lCrime.getTitle());
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        UUID lUUID = (UUID) getIntent()
                .getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);

        for( Crime lCrime : mCrimes) {

            if(lCrime.getId().equals(lUUID)) {
                mViewPager.setCurrentItem(mCrimes.indexOf(lCrime));
            }
        }



    }
}
