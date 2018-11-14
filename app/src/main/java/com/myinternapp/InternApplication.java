package com.myinternapp;

import android.app.Application;

import com.myinternapp.networking.NetworkSetup;

public class InternApplication extends Application {

    private static InternApplication instance = null;

    private NetworkSetup networkSetup = null;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public static InternApplication getApp() {
        if(instance == null){
            instance = new InternApplication();
        }
        return instance;
    }

    public NetworkSetup getNetworkSetup() {
        if(networkSetup ==null){
            networkSetup = new NetworkSetup(getApp());
        }
        return networkSetup;
    }

}
