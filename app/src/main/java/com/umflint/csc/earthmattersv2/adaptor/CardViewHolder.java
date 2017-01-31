package com.umflint.csc.earthmattersv2.adaptor;

import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umflint.csc.earthmattersv2.R;

/**
 * Created by Benjamin on 11/22/2016.
 */

public class CardViewHolder extends RecyclerView.ViewHolder {

    public TextView eventNameTextView;
    public ImageView eventImageView;
    public View view;
    public CardViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        eventImageView = (ImageView) itemView.findViewById(R.id.cardFragmentEventImageView);
        eventNameTextView = (TextView) itemView.findViewById(R.id.cardFragmentEventTextView);
    }

}
