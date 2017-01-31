package com.umflint.csc.earthmattersv2.utilities;

import android.app.Activity;
import android.content.Intent;
import android.provider.CalendarContract;

/**
 * Created by Benjamin on 11/22/2016.
 */

public class CalendarPopulator {

    private Activity activity;

    public CalendarPopulator(Activity activity){
        this.activity = activity;
    }

    public void addToCalendar(long startDate, long endDate, String eventName, String eventDesciption, String location){
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startDate);
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,endDate);
        intent.putExtra(CalendarContract.Events.TITLE, eventName);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, eventDesciption);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
        activity.startActivity(intent);
    }
}
