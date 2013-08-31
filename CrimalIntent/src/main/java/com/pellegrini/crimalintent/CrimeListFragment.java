package com.pellegrini.crimalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.pellegrini.crimalintent.model.Crime;
import com.pellegrini.crimalintent.model.CrimeLab;

import java.util.ArrayList;

/**
 * Created by Fabricio on 26/08/13.
 */
public class CrimeListFragment extends ListFragment {

    private ArrayList<Crime> mCrimes;
    private static final String TAG = "CrimeListFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.crimes_title);

        mCrimes = CrimeLab.instance(getActivity()).getCrimes();

        CrimeAdapter lAdapter = new CrimeAdapter(mCrimes);

        setListAdapter(lAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Crime lCrime = (Crime) (getListAdapter()).getItem(position);
        Crime lCrime = ((CrimeAdapter)getListAdapter()).getItem(position);
        Log.d(TAG, lCrime.getTitle() + " was clicked");

        Intent lIntent = new Intent(getActivity(), CrimePagerActivity.class);

        lIntent.putExtra(CrimeFragment.EXTRA_CRIME_ID, lCrime.getId());
        startActivity(lIntent);
    }


    private class CrimeAdapter extends ArrayAdapter<Crime> {

        public CrimeAdapter(ArrayList<Crime> pCrimes) {
            super(getActivity(), 0, mCrimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_crime, null);
            }

            Crime lCrime = getItem(position);

            TextView lTitleTextView =
                    (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            lTitleTextView.setText(lCrime.getTitle());

            TextView lDateTextView =
                    (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
            lDateTextView.setText(lCrime.getDate().toString());

            CheckBox lSolvedCheckBox =
                    (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            lSolvedCheckBox.setChecked(lCrime.isSolved());

            return convertView;
        }
    }
}
