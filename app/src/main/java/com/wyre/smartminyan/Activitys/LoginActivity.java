package com.wyre.smartminyan.Activitys;

import android.support.v4.app.Fragment;

import com.wyre.smartminyan.Fragments.LoginFragment;

/**
 * Created by yaakov on 4/2/18.
 */

public class LoginActivity extends AbstractFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }
}
