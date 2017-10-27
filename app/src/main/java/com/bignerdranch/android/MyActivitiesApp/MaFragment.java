package com.bignerdranch.android.MyActivitiesApp;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.io.File;
import java.util.UUID;

import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;



/**
 * Created by rdc008 on 24/08/2017.
 *
 * This fragment shows the new activity view. It stores collected data in the database
 */

public class MaFragment extends Fragment {

    private static final String ARG_MA_ID = "Ma_id";
    private static final int REQUEST_PHOTO= 2;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1001;
    private static final String TAG = "LocatrFragment";


    private Ma mMa;
    private File mPhotoFile;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mSaveButton;
    private Button mCancelButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private EditText mComment;
    private EditText mDuration;
    private Button mPlaceButton;
    private GoogleApiClient mClient;
    private String mPlaceButtonSting;


    public static MaFragment newInstance(UUID MaId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MA_ID, MaId);

        MaFragment fragment = new MaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID maId = (UUID) getArguments().getSerializable(ARG_MA_ID);
        mMa = MaLab.getMa(getActivity()).getMa(maId);
        mPhotoFile = MaLab.getMa(getActivity()).getPhotoFile(mMa);

        if (checkPlayServices()) {
            mClient = new GoogleApiClient.Builder(getActivity())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {
                            Log.d(TAG, "onConnected ran");
                            getLocation();
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            Log.d(TAG, "onConnectionFailed: " + connectionResult);
                        }
                    })

                    .build();
        }
    }


    @Override
    public void onStart() {
        super.onStart();

        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        mClient.disconnect();
    }

    @Override
    public void onPause() {
        super.onPause();
        MaLab.getMa(getActivity())
                .updateMa(mMa);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getContext());
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }



    protected void getLocation() {
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mClient, request, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    Log.i(TAG, "Got a fix: " + location);
                    Double slat = location.getLatitude();
                    Double sloc = location.getLongitude();
                    mMa.setLongitude(sloc.toString());
                    mMa.setLatitude(slat.toString());
                    mPlaceButton = getView().findViewById(R.id.ma_place);
                    mPlaceButtonSting = "Lat: " + location.getLatitude() + " Long: " + location.getLongitude();
                    mPlaceButton.setText(mPlaceButtonSting);


                }
            });
        }
        else {
            mMa.setLongitude("No GPS");
            mMa.setLatitude("No Permissions");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ma, container, false);


        Spinner spinner = v.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMa.setType(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(),
                R.array.activity_type_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        mTitleField = v.findViewById(R.id.ma_title);
        mTitleField.setText(mMa.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMa.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mComment = v.findViewById(R.id.ma_comment);
        mComment.setText(mMa.getComment());
        mComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMa.setComment(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mDuration = v.findViewById(R.id.ma_duration);
        mDuration.setText(mMa.getDuration());
        mDuration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMa.setDuration(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        mDateButton = v.findViewById(R.id.ma_date);
        mDateButton.setText(mMa.getDate().toString());
        mDateButton.setEnabled(false);

        mPlaceButton = v.findViewById(R.id.ma_place);
        mPlaceButton.setText(mPlaceButtonSting);
        mPlaceButton.setEnabled(mClient.isConnected());


        mSaveButton = v.findViewById(R.id.ma_save);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MaLab.getMa(getActivity()).updateMa(mMa);
                Intent intent = new Intent(v.getContext(), MaListActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        mCancelButton = v.findViewById(R.id.ma_cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MaLab.getMa(getActivity()).deleteMa(mMa);
                Intent intent = new Intent(v.getContext(), MaListActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });



        PackageManager packageManager = getActivity().getPackageManager();


        mPhotoButton = v.findViewById(R.id.ma_camera);
        final Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        boolean canTakePhoto = mPhotoFile != null &&
                captureImage.resolveActivity(packageManager) != null;
        mPhotoButton.setEnabled(canTakePhoto);
        if (canTakePhoto) {
            Uri uri = FileProvider.getUriForFile(getContext(), getContext().getApplicationContext().getPackageName() + ".com.bignerdranch.android.MyActivitiesApp", mPhotoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(captureImage, REQUEST_PHOTO);
            }
        });

        mPhotoView = v.findViewById(R.id.ma_photo);
        updatePhotoView();


        return v;
    }

    public Ma getMa() {
        return mMa;
    }


    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists()) {
            mPhotoView.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(
                    mPhotoFile.getPath(), getActivity());
            mPhotoView.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        else if (requestCode == REQUEST_PHOTO) {
            updatePhotoView();
        }

    }




}
