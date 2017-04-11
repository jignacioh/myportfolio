package com.udacity.porfolioapp.activity;

import android.support.v7.app.AppCompatActivity;


/**
 * Created by jhurtace on 20/03/2017.
 */
public class BaseActivity extends AppCompatActivity {


    public boolean isOrientationHorizontal(int orientation) {
        if(getResources().getConfiguration().orientation == orientation){
            return true;
        } else{
            return false;
        }
    }
    public String getValueString(int idString){
        return this.getResources().getString(idString);
    }
}
