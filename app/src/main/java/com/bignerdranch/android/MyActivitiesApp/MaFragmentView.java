package com.bignerdranch.android.MyActivitiesApp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import java.io.File;
import java.util.UUID;


/**
 * Created by rdc008 on 24/08/2017.
 *
 * This fragment shows an old activity view. It shows and stores data in the database
 */

public class MaFragmentView extends Fragment {

    private static final String ARG_MA_ID = "Ma_id";
    private static final int REQUEST_PHOTO= 2;
    private static final String EXTRA_MAP_LAT = "com.bignerdranch.android.MyActivitiesApp.map_lat";
    private static final String EXTRA_MAP_LONG = "com.bignerdranch.android.MyActivitiesApp.map_long";


    private Ma mMa;
    private File mPhotoFile;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mSaveButton;
    private Button mDeleteButton;
    private ImageButton mPhotoButton;
    private ImageView mPhotoView;
    private EditText mComment;
    private EditText mDuration;
    private Button mPlaceButton;
    private EditText mTypeText;

    public static MaFragmentView newInstance(UUID MaId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_MA_ID, MaId);

        MaFragmentView fragment = new MaFragmentView();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID maId = (UUID) getArguments().getSerializable(ARG_MA_ID);
        mMa = MaLab.getMa(getActivity()).getMa(maId);
        mPhotoFile = MaLab.getMa(getActivity()).getPhotoFile(mMa);
    }

    @Override
    public void onPause() {
        super.onPause();
        MaLab.getMa(getActivity())
                .updateMa(mMa);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ma_view, container, false);


        mTypeText = v.findViewById(R.id.ma_type);
        mTypeText.setText(mMa.getType());
        mTypeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mMa.setType(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

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
        mPlaceButton.setText("Lat: " + mMa.getLatitude() + " Long: " + mMa.getLongitude());
        mPlaceButton.setEnabled(true);
        mPlaceButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                Bundle extras = new Bundle();
                extras.putString(EXTRA_MAP_LAT, mMa.getLatitude());
                extras.putString(EXTRA_MAP_LONG, mMa.getLongitude());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });


        mSaveButton = v.findViewById(R.id.ma_save);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MaLab.getMa(getActivity()).updateMa(mMa);
                Intent intent = new Intent(v.getContext(), MaListActivity.class);
                getActivity().finish();
                startActivity(intent);
            }
        });

        mDeleteButton = v.findViewById(R.id.ma_delete);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
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
