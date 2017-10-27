package com.bignerdranch.android.MyActivitiesApp;

import android.support.v4.app.Fragment;

/**
 * Created by rdc008 on 24/08/2017.
 *
 * This activity creates the list view fragment and is also the main activity
 */

public class MaListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new MaListFragment();
    }
}
