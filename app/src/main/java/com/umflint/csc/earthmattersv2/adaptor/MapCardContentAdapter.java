package com.umflint.csc.earthmattersv2.adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.umflint.csc.earthmattersv2.R;
import com.umflint.csc.earthmattersv2.model.EventModel;
import com.umflint.csc.earthmattersv2.model.MapModel;
import com.umflint.csc.earthmattersv2.utilities.CalendarPopulator;
import com.umflint.csc.earthmattersv2.utilities.Utilities;
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
    private Utilities utilities = new Utilities();
    private boolean isAdmin;
    private FirebaseDatabase database;
    private StorageReference storageReference;
    private String mapId;



    public MapCardContentAdapter(Class<MapModel> modelClass, int modelLayout,
                                 Class<MapCardViewHolder> viewHolderClass, DatabaseReference ref, Context context, Activity activity,
                                 String coverName) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
        this.activity = activity;
        this.coverName = coverName;
        isAdmin = utilities.checkAuth();
        database = utilities.getDataBase();
        storageReference = utilities.getStorageRef();

    }
    @Override
    protected void populateViewHolder(final MapCardViewHolder viewHolder, final MapModel model, final int position) {
        try{
            StorageReference imageRef = storage.getReferenceFromUrl(model.getMapOne());
            mapId = model.getMapId();
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

        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(isAdmin){
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this Map?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    database.getReference().child("Events").child(coverName).child("Maps")
                                            .child(mapId).removeValue();
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                return false;
            }
        });
    }
}
