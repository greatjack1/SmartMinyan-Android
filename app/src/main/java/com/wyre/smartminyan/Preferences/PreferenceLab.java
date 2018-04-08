package com.wyre.smartminyan.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.wyre.smartminyan.Models.User;

/**
 * Created by yaakov on 4/2/18.
 */

public class PreferenceLab {
    private final String RAN_FIRST = "ranfirst";
    private final String USER = "USER";
    private static PreferenceLab mLab;
    private Context mContext;
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    private PreferenceLab(Context context){
        mContext = context.getApplicationContext();
        mPrefs = mContext.getSharedPreferences("smartminyan-prefs",0);
        mEditor = mPrefs.edit();
    }
    public static PreferenceLab getPreferenceLab(Context context){
     if(mLab==null){
         mLab = new PreferenceLab(context);
     }
     return mLab;
    }
    public void setRanFirst(boolean ran){
        mEditor.putBoolean(RAN_FIRST,ran);
        mEditor.commit();
    }
    public boolean getRanFirst(){
        return mPrefs.getBoolean(RAN_FIRST,false);
    }
    public void setUser(User user){
        mEditor.putString(USER,new Gson().toJson(user));
        mEditor.commit();
    }
    public User getUser(){
        String string = mPrefs.getString(USER,null);
        if(string!=null){
            return new Gson().fromJson(string,User.class);
        }
        return null;
    }
}
