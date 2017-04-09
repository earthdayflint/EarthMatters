package com.umflint.csc.earthmattersv2.adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.umflint.csc.earthmattersv2.model.BoothModel;
import com.umflint.csc.earthmattersv2.utilities.Utilities;

/**
 * Created by Benjamin on 1/23/2017.
 */

public class BoothCardContentAdapter extends FirebaseRecyclerAdapter <BoothModel, BoothCardViewHolder> {
    private static final String TAG = BoothCardContentAdapter.class.getSimpleName();
    private Context context;
    private Activity activity;
    private Utilities utilities = new Utilities();
    private boolean isAdmin;
    private FirebaseDatabase database;
    private StorageReference storageReference;
    private String boothId;
    private String coverName;

    public BoothCardContentAdapter(Class<BoothModel> modelClass, int modelLayout,
                                   Class<BoothCardViewHolder> viewHolderClass, Query ref,
                                   Context context, final Activity activity, String coverName) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.coverName = coverName;
        this.context = context;
        this.activity = activity;
        isAdmin = utilities.checkAuth();
        database = utilities.getDataBase();

        storageReference = utilities.getStorageRef();

    }
    @Override
    protected void populateViewHolder(final BoothCardViewHolder viewHolder, final BoothModel model, final int position) {
        viewHolder.boothNameTextView.setText(model.getBoothName());
        boothId = model.getBoothId();
        viewHolder.boothDescriptionTextView.setText(model.getBoothDescription());
        viewHolder.boothNumberTextView.setText("Booth #" + Long.toString(model.getBoothNumber()));

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + model.getBoothWebsite()));
                activity.startActivity(newsIntent);
            }
        });

        viewHolder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(isAdmin){
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this Booth?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    database.getReference().child("Events").child(coverName).
                                            child("Booths").child(model.getBoothId()).removeValue();
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
    };
}
