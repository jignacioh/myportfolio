package com.udacity.porfolioapp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.DetailsMovieFragment;

import java.util.ArrayList;

/**
 * Created by Juan PC
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final DetailsMovieFragment.Callbacks mCallbacks;
    public TextView tvTrailer;
    public LinearLayout llHeaderTittle;
    private View itemView;
    public TrailerViewHolder(View itemView, DetailsMovieFragment.Callbacks mCallbacks) {
        super(itemView);
        this.itemView=itemView;
        this.mCallbacks=mCallbacks;
        tvTrailer = (TextView) itemView.findViewById(R.id.tvLink);
        llHeaderTittle = (LinearLayout) itemView.findViewById(R.id.llTrailerContent);
        llHeaderTittle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mCallbacks.onTrailerSelected((ArrayList<Object>) itemView.getTag(),getAdapterPosition(),itemView);
    }

    public TextView getTvTrailer() {
        return tvTrailer;
    }

    public void setTvTrailer(TextView tvTrailer) {
        this.tvTrailer = tvTrailer;
    }

    public LinearLayout getLlHeaderTittle() {
        return llHeaderTittle;
    }

    public void setLlHeaderTittle(LinearLayout llHeaderTittle) {
        this.llHeaderTittle = llHeaderTittle;
    }
}
