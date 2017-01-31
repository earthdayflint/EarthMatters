package com.umflint.csc.earthmattersv2.utilities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.umflint.csc.earthmattersv2.R;

/**
 * Created by Benjamin on 11/22/2016.
 */

public class EventMap {

    private Activity activity;
    public EventMap(Activity activity){
        this.activity = activity;
    }

    public void getDirections(String location){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + location);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage(activity.getString(R.string.mapPackage));
        if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(mapIntent);
        }
    }
}
