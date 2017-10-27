package com.bignerdranch.android.MyActivitiesApp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import java.util.UUID;



/**
 * Created by Robbie Clark on 21/10/2017.
 *
 * This fragment displays the user profile
 */

public class SettingsFragment extends Fragment {

    private static final String ARG_UP_ID = "Up_id";

    private UserProfile mUp;
    private EditText mTextName;
    private EditText mTextEmail;
    private EditText mTextComment;
    private EditText mTextGender;
    private EditText mTextID;



    public static SettingsFragment newInstance(UUID UpId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_UP_ID, UpId);

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID upId = (UUID) getArguments().getSerializable(ARG_UP_ID);
        mUp = MaLab.getMa(getActivity()).getUserProfile(upId);
    }


    @Override
    public void onPause() {
        super.onPause();
        MaLab.getMa(getActivity())
                .updateUserProfile(mUp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle onSavedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        mTextName = v.findViewById(R.id.settings_name);
        mTextName.setText(mUp.getmName());
        mTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUp.setmName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        mTextEmail = v.findViewById(R.id.settings_email);
        mTextEmail.setText(mUp.getmEmail());
        mTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUp.setmEmail(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        mTextComment = v.findViewById(R.id.settings_comment);
        mTextComment.setText(mUp.getmComment());
        mTextComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUp.setmComment(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        mTextGender = v.findViewById(R.id.settings_gender);
        mTextGender.setText(mUp.getmGender());
        mTextGender.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUp.setmGender(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        mTextID = v.findViewById(R.id.settings_id);
        mTextID.setText(mUp.getmIdnum());
        mTextID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mUp.setmIdnum(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });



        return v;
    }








}
