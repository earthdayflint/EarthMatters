package com.umflint.csc.earthmattersv2.adaptor;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.umflint.csc.earthmattersv2.R;

/**
 * Created by Benjamin on 3/14/2017.
 */

public class MapCardViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public View view;

    public MapCardViewHolder(View itemView) {
        super(itemView);
        view = itemView;

        imageView = (ImageView) itemView.findViewById(R.id.cardFragmentMapImageView);

    }
}
