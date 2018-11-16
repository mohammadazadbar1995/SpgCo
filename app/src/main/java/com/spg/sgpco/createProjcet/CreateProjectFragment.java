package com.spg.sgpco.createProjcet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.customView.CustomInsertDataView;
import com.spg.sgpco.customView.CustomMultiLineEditText;
import com.spg.sgpco.customView.CustomNoReduisEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class CreateProjectFragment extends Fragment {


    Unbinder unbinder;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.edtName)
    CustomNoReduisEditText edtName;
    @BindView(R.id.type_project_layout)
    CustomInsertDataView typeProjectLayout;
    @BindView(R.id.province_layout)
    CustomInsertDataView provinceLayout;
    @BindView(R.id.city_layout)
    CustomInsertDataView cityLayout;
    @BindView(R.id.add_customer_layout)
    CustomInsertDataView addCustomerLayout;
    @BindView(R.id.heatـsource_layout)
    CustomInsertDataView heatSourceLayout;
    @BindView(R.id.system_control_type_layout)
    CustomInsertDataView systemControlTypeLayout;
    @BindView(R.id.edtBugReport)
    CustomMultiLineEditText edtBugReport;
    @BindView(R.id.btnCreate)
    BaseTextView btnCreate;

    public CreateProjectFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_project, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvCenterTitle.setText(getResources().getString(R.string.create_project));

        typeProjectLayout.setImgInfo(R.drawable.ic_person_gray);
        typeProjectLayout.setTxtTitle("");
        typeProjectLayout.setValue(getString(R.string.select_type_name_project));

        provinceLayout.setImgInfo(R.drawable.ic_person_gray);
        provinceLayout.setTxtTitle("");
        provinceLayout.setValue(getString(R.string.select_province));


        cityLayout.setImgInfo(R.drawable.ic_person_gray);
        cityLayout.setTxtTitle("");
        cityLayout.setValue(getString(R.string.select_city));


        addCustomerLayout.setImgInfo(R.drawable.ic_person_gray);
        addCustomerLayout.setTxtTitle("");
        addCustomerLayout.setValue(getString(R.string.select_customer));


        heatSourceLayout.setImgInfo(R.drawable.ic_person_gray);
        heatSourceLayout.setTxtTitle("");
        heatSourceLayout.setValue(getString(R.string.heat_source));


        systemControlTypeLayout.setImgInfo(R.drawable.ic_person_gray);
        systemControlTypeLayout.setTxtTitle("");
        systemControlTypeLayout.setValue(getString(R.string.system_control_type));


        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.type_project_layout, R.id.province_layout, R.id.city_layout, R.id.add_customer_layout, R.id.heatـsource_layout, R.id.system_control_type_layout, R.id.edtBugReport, R.id.btnCreate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.type_project_layout:
                break;
            case R.id.province_layout:
                break;
            case R.id.city_layout:
                break;
            case R.id.add_customer_layout:
                break;
            case R.id.heatـsource_layout:
                break;
            case R.id.system_control_type_layout:
                break;
            case R.id.edtBugReport:
                break;
            case R.id.btnCreate:
                break;
        }
    }
}
