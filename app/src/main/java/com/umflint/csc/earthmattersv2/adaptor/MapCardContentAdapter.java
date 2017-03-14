package com.umflint.csc.earthmattersv2.adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.umflint.csc.earthmattersv2.R;
import com.umflint.csc.earthmattersv2.model.EventModel;
import com.umflint.csc.earthmattersv2.model.MapModel;
import com.umflint.csc.earthmattersv2.utilities.CalendarPopulator;
import com.umflint.csc.earthmattersv2.ux.ExpandedCardActivity;

import java.util.ArrayList;

/**
 * Created by Benjamin on 3/14/2017.
 */

public class MapCardContentAdapter extends FirebaseRecyclerAdapter<MapModel, MapCardViewHolder> {

    private static final String TAG = ContentAdapter.class.getSimpleName();
    private Context context;
    private Activity activity;
    private String coverName;
    private FirebaseStorage storage = FirebaseStorage.getInstance();


    public MapCardContentAdapter(Class<MapModel> modelClass, int modelLayout,
                                 Class<MapCardViewHolder> viewHolderClass, DatabaseReference ref, Context context, Activity activity,
                                 String coverName) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
        this.activity = activity;
        this.coverName = coverName;
    }
    @Override
    protected void populateViewHolder(final MapCardViewHolder viewHolder, final MapModel model, final int position) {
        try{
            StorageReference imageRef = storage.getReferenceFromUrl(model.getMapOne());
            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(viewHolder.imageView.getContext()).load(uri.toString()).into(viewHolder.imageView);
                }
            });
        }catch (Exception e){
            Log.d("EXCEPTION TAG", "null");
        }


        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo implement map popup
            }
        });
    }
}
