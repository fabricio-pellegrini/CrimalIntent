package com.pellegrini.crimalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.pellegrini.crimalintent.model.Crime;
import com.pellegrini.crimalintent.model.CrimeLab;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Fabricio on 24/08/13.
 */
public class CrimeFragment extends Fragment {

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

    public static final String EXTRA_CRIME_ID = "EXTRA_CRIME_ID";

    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;

    public static CrimeFragment newInstance(UUID pCrimeId){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, pCrimeId);
        CrimeFragment lCrimeFragment = new CrimeFragment();
        lCrimeFragment.setArguments(args);

        return lCrimeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID lUUID = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.instance(getActivity()).getCrime(lUUID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText) v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                mCrime.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mDateButton = (Button) v.findViewById(R.id.crime_date);

        updateDate();
        //mDateButton.setEnabled(false);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                FragmentManager lFragmentManager = getActivity()
                        .getSupportFragmentManager();
                //DatePickerFragment dialog = new DatePickerFragment();
                DatePickerFragment dialog =  DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                dialog.show(lFragmentManager, DIALOG_DATE);
            }
        });


        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isSolved());
        mSolvedCheckBox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton pCompoundButton, boolean b) {
                    mCrime.setSolved(b);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int pRequestCode, int pResultCode, Intent pData) {
        if(pResultCode != Activity.RESULT_OK)
            return;
        if(pRequestCode == REQUEST_DATE) {
            Date lDate = (Date) pData.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(lDate);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(DateFormat.getDateFormat(
                getActivity().getApplicationContext()).format(mCrime.getDate()));
    }
}
