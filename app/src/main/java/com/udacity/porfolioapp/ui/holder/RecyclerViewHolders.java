package com.udacity.porfolioapp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.porfolioapp.listener.OnChildClickListener;
import com.udacity.porfolioapp.R;

/**
 * Created by Juan PC
 */

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView tvNameApp;
    public ImageView ivImageApp;
    OnChildClickListener onChildClickListener;
    public RecyclerViewHolders(View itemView, OnChildClickListener onChildClickListener) {
        super(itemView);
        this.onChildClickListener=onChildClickListener;
        itemView.setOnClickListener(this);
        tvNameApp = (TextView) itemView.findViewById(R.id.tvNameApp);
        ivImageApp = (ImageView) itemView.findViewById(R.id.ivImageApp);
    }


    @Override
    public void onClick(View view) {
        onChildClickListener.onAppClick(0);
        Toast.makeText(view.getContext(), tvNameApp.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
