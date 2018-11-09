package com.spg.sgpco.about;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class AboutSgpFragment extends Fragment {


    public AboutSgpFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about_sgp, container, false);


        return view;

    }


}
