package com.bignerdranch.android.MyActivitiesApp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * Created by rdc008 on 24/08/2017.
 *
 *This fragment has a recycler view and is used to display all the old activities on the main activity
 */

public class MaListFragment extends Fragment {
    private RecyclerView mMaRecyclerView;
    private MaAdapter mAdapter;
    private UserProfile mUserProfile;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 100;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        }
     }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ma_list, container, false);

        mMaRecyclerView = view.findViewById(R.id.ma_recycler_view);
        mMaRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return  view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_ma_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_ma:
                Ma ma = new Ma();
                MaLab.getMa(getActivity()).addMa(ma);
                Intent intent = MaActivity
                        .newIntent(getActivity(), ma.getId());
                startActivity(intent);
                return true;

            case R.id.menu_item_settings_ma:
                UserProfile TempUserProfile = MaLab.getMa(getActivity()).getUserProfile(UUID.fromString("4187e5aa-b8cd-11e7-abc4-cec278b6b50a"));
                if (TempUserProfile == null) {
                    mUserProfile = new UserProfile(UUID.fromString("4187e5aa-b8cd-11e7-abc4-cec278b6b50a"));
                }
                else {
                    mUserProfile = TempUserProfile;
                }

                MaLab.getMa(getActivity()).addUserProfile(mUserProfile);
                Intent settingsIntent = SettingsActivity
                       .newIntent(getActivity(), mUserProfile.getmId());
                startActivity(settingsIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateUI() {
        MaLab maLab = MaLab.getMa(getActivity());
        List<Ma> mas = maLab.getMas();

        if (mAdapter == null) {
            mAdapter = new MaAdapter(mas);
            mMaRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class MaHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private TextView mLocationTextView;
        private String mlocationTextViewString;
        private Ma mMa;

        public MaHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.list_item_ma_title_text_view);
            mDateTextView = itemView.findViewById(R.id.list_item_ma_date_text_view);
            mLocationTextView = itemView.findViewById(R.id.list_item_ma_location_text_view);
        }

        public void bindMa(Ma ma) {
            mMa = ma;
            mTitleTextView.setText(mMa.getTitle());
            mDateTextView.setText(mMa.getDate().toString());
            mlocationTextViewString = "Lat: " + mMa.getLatitude() + " Long: " + mMa.getLongitude();
            mLocationTextView.setText(mlocationTextViewString);
        }

        @Override
        public void onClick(View v) {
            Intent intent = MaActivityView.newIntent(getActivity(), mMa.getId());
            startActivity(intent);
        }
    }

    private class MaAdapter extends RecyclerView.Adapter<MaHolder> {
        private List<Ma> mMas;

        public MaAdapter(List<Ma> mas) {
            mMas = mas;
        }

        @Override
        public MaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_ma, parent, false);
            return new MaHolder(view);
        }

        @Override
        public void onBindViewHolder(MaHolder holder, int position) {
            Ma ma = mMas.get(position);
            holder.bindMa(ma);
        }

        @Override
        public int getItemCount() {
            return mMas.size();
        }
    }
}
