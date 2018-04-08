package com.wyre.smartminyan.Infastructure;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by yaakov on 4/2/18.
 */

public class MinyanApplication extends Application {
    @Override public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }
}
