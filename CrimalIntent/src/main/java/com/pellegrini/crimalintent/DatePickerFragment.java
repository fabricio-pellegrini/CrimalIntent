package com.pellegrini.crimalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Fabricio on 01/09/13.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "extra_date";

    private Date mDate;

    public static DatePickerFragment newInstance(Date pDate) {
        Bundle args = new Bundle();

        args.putSerializable(EXTRA_DATE, pDate);
        DatePickerFragment lDatePickerFragment = new DatePickerFragment();
        lDatePickerFragment.setArguments(args);
        return lDatePickerFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDate = (Date) getArguments().getSerializable(EXTRA_DATE);

        //Create a Calendar to get the year, month and day
        Calendar lCalendar = Calendar.getInstance();

        lCalendar.setTime(mDate);
        int lYear = lCalendar.get(Calendar.YEAR);
        int lMonth = lCalendar.get(Calendar.MONTH);
        int lDay = lCalendar.get(Calendar.DAY_OF_MONTH);

        View lView = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date, null);

        DatePicker lDatePicker = (DatePicker) lView.findViewById(R.id.dialog_date_datePicker);
        lDatePicker.init(lYear, lMonth, lDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker pDatePicker, int pYear, int pMonth, int pDay) {

                mDate = new GregorianCalendar(pYear, pMonth, pDay).getTime();

                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(lView)
                .setTitle(R.string.date_picker_title)
                //.setPositiveButton(android.R.string.ok, null)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface pDialogInterface, int i) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }

    private void sendResult(int pResultCode) {
        if(getTargetFragment() == null)
            return;

        Intent lIntent = new Intent();
        lIntent.putExtra(EXTRA_DATE, mDate);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), pResultCode, lIntent);
    }
}