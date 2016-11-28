package com.udacity.porfolioapp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.porfolioapp.R;

/**
 * Created by clapj on 14/11/2016.
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tvTrailer;

    public TrailerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        tvTrailer = (TextView) itemView.findViewById(R.id.tvLink);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), tvTrailer.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
