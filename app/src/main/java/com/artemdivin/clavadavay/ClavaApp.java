package com.artemdivin.clavadavay;

import android.app.Application;

import io.realm.Realm;

public class ClavaApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
