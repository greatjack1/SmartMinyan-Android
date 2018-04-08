package com.wyre.smartminyan.Activitys;

import android.support.v4.app.Fragment;

import com.wyre.smartminyan.Fragments.ListFragment;

/**
 * Created by yaakov on 4/3/18.
 */

public class ListActivity extends AbstractFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ListFragment();
    }
}
