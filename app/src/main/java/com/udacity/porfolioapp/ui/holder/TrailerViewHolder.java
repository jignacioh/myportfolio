package com.udacity.porfolioapp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.DetailMovieFragment;

import java.util.ArrayList;

/**
 * Created by Juan PC
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final DetailMovieFragment.Callbacks mCallbacks;
    public TextView tvTrailer;
    public LinearLayout llHeaderTittle;
    private View itemView;
    public TrailerViewHolder(View itemView, DetailMovieFragment.Callbacks mCallbacks) {
        super(itemView);
        this.itemView=itemView;
        this.mCallbacks=mCallbacks;
        tvTrailer = (TextView) itemView.findViewById(R.id.tvLink);
        llHeaderTittle = (LinearLayout) itemView.findViewById(R.id.llTrailerContent);
        llHeaderTittle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mCallbacks.onItemSelected((ArrayList<Object>) itemView.getTag(),getAdapterPosition(),itemView);
        //Toast.makeText(view.getContext(), tvTrailer.getText().toString(), Toast.LENGTH_SHORT).show();
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
