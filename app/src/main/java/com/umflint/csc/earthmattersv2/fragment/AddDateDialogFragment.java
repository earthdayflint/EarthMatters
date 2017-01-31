package com.umflint.csc.earthmattersv2.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.umflint.csc.earthmattersv2.R;
import com.umflint.csc.earthmattersv2.ux.CreateEventActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Copenblend on 11/2/2016.
 */

public class AddDateDialogFragment extends DialogFragment {

    private int dateChooser; //startDate = 0 endDate = 1
    private Date date = null;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.fragment_add_date, null);

        final DatePicker startDatePicker = (DatePicker) rootView.findViewById(R.id.startDatePicker);


        builder.setView(rootView)
                .setTitle("Add Time/Date")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CreateEventActivity createEventActivity = (CreateEventActivity) getActivity();
                        if(dateChooser == 0){
                            createEventActivity.setStartDateString(formatDate(startDatePicker.getMonth()+1, startDatePicker.getDayOfMonth(), startDatePicker.getYear()));
                        }else{
                            createEventActivity.setEndDateString(formatDate(startDatePicker.getMonth()+1, startDatePicker.getDayOfMonth(), startDatePicker.getYear()));
                        }

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }

    public String formatDate(int month, int day, int year){
        String sYear = Integer.toString(year);
        String sMonth = Integer.toString(month);
        String sDay = Integer.toString(day);

        if(sMonth.length() != 2){
            sMonth = "0" + sMonth;
        }
        if(sDay.length() != 2){
            sDay = "0" + sDay;
        }

        String sDate = sYear + sMonth + sDay;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);

        try {
            date = sdf.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //sdf.applyPattern("dd MMM yyyy");
        sDate = sdf.format(date);

        return sDate;
    }

    public void setDateChooser (int dateChooser){
        this.dateChooser = dateChooser;
    }
}
