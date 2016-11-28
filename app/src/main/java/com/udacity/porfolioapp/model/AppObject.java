package com.udacity.porfolioapp.model;

/**
 * Created by clapj on 1/11/2016.
 */

public class AppObject {
    private String nameApp;
    private Integer idImageDefaultApp;

    public AppObject(String nameApp,Integer idImageDefaultApp) {
        this.idImageDefaultApp = idImageDefaultApp;
        this.nameApp = nameApp;
    }

    public String getNameApp() {
        return nameApp;
    }

    public void setNameApp(String nameApp) {
        this.nameApp = nameApp;
    }

    public Integer getIdImageDefaultApp() {
        return idImageDefaultApp;
    }

    public void setIdImageDefaultApp(Integer idImageDefaultApp) {
        this.idImageDefaultApp = idImageDefaultApp;
    }
}
