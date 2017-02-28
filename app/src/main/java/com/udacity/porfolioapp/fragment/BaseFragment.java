package com.udacity.porfolioapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.porfolioapp.activity.BaseContextApplication;
import com.udacity.porfolioapp.model.DaoSession;
import com.udacity.porfolioapp.model.Movie;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Juan PC on 22/01/2017.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public DaoSession getAppDaoSession(){
        return ((BaseContextApplication)getActivity().getApplication()).getDaoSession();
    }
    public List<Movie> getFavoritesMovies() {
        QueryBuilder<Movie> qb = getAppDaoSession().getMovieDao().queryBuilder();
        return qb.list();
    }

}
