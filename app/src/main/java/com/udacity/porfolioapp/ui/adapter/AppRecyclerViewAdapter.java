package com.udacity.porfolioapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.porfolioapp.MainActivity;
import com.udacity.porfolioapp.OnChildClickListener;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.ui.holder.RecyclerViewHolders;
import com.udacity.porfolioapp.model.AppObject;

import java.util.List;

/**
 * Created by clapj on 1/11/2016.
 */

public class AppRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolders> {

    private List<AppObject> itemList;
    private OnChildClickListener onChildClickListener;

    public AppRecyclerViewAdapter(OnChildClickListener onChildClickListener, List<AppObject> itemList) {
        this.itemList = itemList;
        this.onChildClickListener = onChildClickListener;
    }

    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_app_item, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView,onChildClickListener);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolders holder, int position) {
        holder.tvNameApp.setText(itemList.get(position).getNameApp());
        holder.ivImageApp.setImageResource(itemList.get(position).getIdImageDefaultApp());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}