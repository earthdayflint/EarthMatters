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
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.umflint.csc.earthmattersv2.R;
import com.umflint.csc.earthmattersv2.model.EventModel;
import com.umflint.csc.earthmattersv2.utilities.CalendarPopulator;
import com.umflint.csc.earthmattersv2.utilities.Utilities;
import com.umflint.csc.earthmattersv2.ux.ExpandedCardActivity;

public class ContentAdapter extends FirebaseRecyclerAdapter<EventModel, CardViewHolder>{

    private static final String TAG = ContentAdapter.class.getSimpleName();
    private Context context;
    private Activity activity;
    private DatabaseReference ref;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseDatabase database;
    private CalendarPopulator calendarPopulator;
    private Utilities utilities = new Utilities();
    private boolean isAdmin;
    private StorageReference storageReference;


    public ContentAdapter(Class<EventModel> modelClass, int modelLayout,
                          Class<CardViewHolder> viewHolderClass, Query ref, Context context, Activity activity) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
        this.activity = activity;
        calendarPopulator = new CalendarPopulator(activity);
        isAdmin = utilities.checkAuth();
        database = utilities.getDataBase();
        storageReference = utilities.getStorageRef();

    }
    @Override
    protected void populateViewHolder(final CardViewHolder viewHolder, final EventModel model, final int position) {

        StorageReference storageRef = storage.getReferenceFromUrl(context.getString(R.string.firebaseBucket));
        viewHolder.eventNameTextView.setText(model.getEventName());
        StorageReference imageRef = storageRef.child(model.getCoverName());
        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(viewHolder.eventImageView.getContext()).load(uri.toString()).into(viewHolder.eventImageView);
            }
        });

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ExpandedCardActivity.class);
                intent.putExtra(activity.getString(R.string.extrasEventName),model.getEventName());
                intent.putExtra(activity.getString(R.string.extrasCoverName), model.getCoverName());
                intent.putExtra(activity.getString(R.string.extrasLocation), model.getLocation());
                intent.putExtra(activity.getString(R.string.extrasStartDate), model.getStartDate());
                intent.putExtra(activity.getString(R.string.extrasEndDate), model.getEndDate());
                intent.putExtra(activity.getString(R.string.extrasDescription),model.getDescription());
                activity.startActivity(intent);
            }
        });

        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(isAdmin){
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this event?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    database.getReference().child("Events").child(model.getCoverName()).removeValue();
                                    storageReference.child(model.getCoverName()).delete();
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