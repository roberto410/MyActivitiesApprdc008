package com.bignerdranch.android.MyActivitiesApp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Robbie Clark on 31/08/2017.
 *
 * This is the activity class for viewing an old activity
 */

public class MaActivityView extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        UUID MaId = (UUID) getIntent().getSerializableExtra(EXTRA_MA_ID);
        return MaFragmentView.newInstance(MaId);
    }

    private static final String EXTRA_MA_ID = "com.bignerdranch.android.MyActivitiesApp.ma_id";

    public static Intent newIntent(Context packageContext, UUID maId) {
        Intent intent = new Intent(packageContext, MaActivityView.class);
        intent.putExtra(EXTRA_MA_ID, maId);
        return intent;
    }
}