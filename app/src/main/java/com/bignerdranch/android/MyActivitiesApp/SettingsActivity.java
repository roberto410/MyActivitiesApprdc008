package com.bignerdranch.android.MyActivitiesApp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Robbie Clark on 21/10/2017.
 *
 * This Activity creates the user profile fragment
 */

public class SettingsActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID UpId = (UUID) getIntent().getSerializableExtra(EXTRA_UP_ID);
        return SettingsFragment.newInstance(UpId);
    }

    private static final String EXTRA_UP_ID = "com.bignerdranch.android.MyActivitiesApp.up_id";

    public static Intent newIntent(Context packageContext, UUID upId) {
        Intent intent = new Intent(packageContext, SettingsActivity.class);
        intent.putExtra(EXTRA_UP_ID, upId);
        return intent;
    }
}