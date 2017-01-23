package com.udacity.porfolioapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.udacity.porfolioapp.listener.OnChildClickListener;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.model.AppObject;
import com.udacity.porfolioapp.ui.adapter.AppRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnChildClickListener {
    private GridLayoutManager lLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("App Portafolio");
        List<AppObject> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(MainActivity.this, 1);

        RecyclerView rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        AppRecyclerViewAdapter rcAdapter = new AppRecyclerViewAdapter(MainActivity.this, rowListItem);
        rView.setItemAnimator(new DefaultItemAnimator());
        rView.setAdapter(rcAdapter);

    }

    private List<AppObject> getAllItemList() {
        List<AppObject> allItems = new ArrayList<>();
        allItems.add(new AppObject("POPULAR MOVIES", R.drawable.logo_android));
        allItems.add(new AppObject("STOCK HAWK",  R.drawable.logo_android));
        allItems.add(new AppObject("BUILD IT BIGGER",  R.drawable.logo_android));
        allItems.add(new AppObject("MAKE YOUR APP MATERIAL", R.drawable.logo_android));
        allItems.add(new AppObject("GO UBIQUOUS", R.drawable.logo_android));
        allItems.add(new AppObject("CAPSTONE MY OWN APP", R.drawable.logo_android));

        return allItems;
    }

    @Override
    public void onAppClick(int type) {
        if (type==0){
            Intent intent=new Intent(this,ListMoviesActivity.class);
            startActivity(intent);
        }
    }
}
