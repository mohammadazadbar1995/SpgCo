package com.spg.sgpco.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.dialog.TypeNameDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class HomeFragmentFragment extends Fragment {


    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.fabfilter)
    FloatingActionButton fabfilter;
    Unbinder unbinder;

    public HomeFragmentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fabfilter)
    public void onViewClicked() {

        if (getActivity() != null) {
            TypeNameDialog typeNameDialog = new TypeNameDialog(getActivity());
            typeNameDialog.show();
        }

    }
}
