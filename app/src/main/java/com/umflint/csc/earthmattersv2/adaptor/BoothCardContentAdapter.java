package com.umflint.csc.earthmattersv2.adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.umflint.csc.earthmattersv2.R;
import com.umflint.csc.earthmattersv2.model.BoothModel;
import com.umflint.csc.earthmattersv2.utilities.Utilities;
import com.umflint.csc.earthmattersv2.ux.ExpandedCardActivity;

/**
 * Created by Benjamin on 1/23/2017.
 */

public class BoothCardContentAdapter extends FirebaseRecyclerAdapter <BoothModel, BoothCardViewHolder> {
    private static final String TAG = BoothCardContentAdapter.class.getSimpleName();
    private Context context;
    private Activity activity;

    public BoothCardContentAdapter(Class<BoothModel> modelClass, int modelLayout,
                          Class<BoothCardViewHolder> viewHolderClass, DatabaseReference ref,
                                   Context context, Activity activity) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        this.context = context;
        this.activity = activity;
    }
    @Override
    protected void populateViewHolder(final BoothCardViewHolder viewHolder, final BoothModel model, final int position) {
        viewHolder.boothNameTextView.setText(model.getSubEventName());
        viewHolder.boothDescriptionTextView.setText(model.getSubEventDescription());
        viewHolder.boothNumberTextView.setText("Booth #" + Long.toString(model.getSubEventBoothNumber()));

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://" + model.getSubEventWebSite()));
                activity.startActivity(newsIntent);
            }
        });
    };
}
