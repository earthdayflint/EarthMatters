package com.umflint.csc.earthmattersv2.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umflint.csc.earthmattersv2.R;

/**
 * Created by Benjamin on 1/23/2017.
 */

public class BoothCardViewHolder extends RecyclerView.ViewHolder{

    public TextView boothNameTextView;
    public TextView boothDescriptionTextView;
    public TextView boothNumberTextView;

    public View view;
    public BoothCardViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        boothNameTextView = (TextView) itemView.findViewById(R.id.boothCardNameFragmentTextView);
        boothDescriptionTextView = (TextView) itemView.findViewById(R.id.boothDescriptionFragmentTextView);
        boothNumberTextView = (TextView) itemView.findViewById(R.id.boothCardNumberFragmentTextView);
    }
}
