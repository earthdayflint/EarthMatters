package com.umflint.csc.earthmattersv2.utilities;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Benjamin on 11/22/2016.
 */

public class Utilities {

    private FirebaseUser administrator;

    public Utilities(){

    }

    public long tranformDateForCalendar(String sDate){
        Date date = new Date();

        long lDate;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.US);

        try {
            date = sdf.parse(sDate);
            Log.d("DATE", date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lDate = date.getTime();

        return lDate;
    }

    public String transFormDateForUser(String sDate){
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        try {
            date = sdf.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        sdf.applyPattern("dd MMM yyyy");
        sDate = sdf.format(date);

        return sDate;
    }

    public boolean checkAuth() {
        Log.d("ADMIN:", "check");
        administrator = FirebaseAuth.getInstance().getCurrentUser();
        if (administrator != null) {
            return true;
        }
        return false;
    }

    public String formatURL(String url){
        return url.replaceFirst("^(https://|http://)","");
    }
}
