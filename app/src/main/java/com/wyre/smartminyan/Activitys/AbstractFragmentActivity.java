package com.wyre.smartminyan.Activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.wyre.smartminyan.R;

/**
 * Created by yaakov on 4/2/18.
 */


public abstract class AbstractFragmentActivity extends AppCompatActivity {
    /**
     * A abstract methods that is used for a sub class to create its own implementation of creating a fragment
     *
     * @return The fragment that was created
     */
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        //get a referance to the support version of the fragment manager
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }


    }
}

