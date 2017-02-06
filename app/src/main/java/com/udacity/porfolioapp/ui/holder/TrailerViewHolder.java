package com.udacity.porfolioapp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.porfolioapp.R;

/**
 * Created by Juan PC
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tvTrailer;
    public LinearLayout llHeaderTittle;
    public TrailerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        tvTrailer = (TextView) itemView.findViewById(R.id.tvLink);
        llHeaderTittle = (LinearLayout) itemView.findViewById(R.id.llHeaderTittle);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), tvTrailer.getText().toString(), Toast.LENGTH_SHORT).show();
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
