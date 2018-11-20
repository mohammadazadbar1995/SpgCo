package com.spg.sgpco.createProjcet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.addCustomer.AddCustomerFragment;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.customView.CustomInsertDataView;
import com.spg.sgpco.customView.CustomMultiLineEditText;
import com.spg.sgpco.customView.CustomNoReduisEditText;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.enums.TypeEnum;
import com.spg.sgpco.service.Request.GetAllSettingService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.ResponseModel.CitiesListItem;
import com.spg.sgpco.service.ResponseModel.CustomerItem;
import com.spg.sgpco.service.ResponseModel.ListCitiesItem;
import com.spg.sgpco.service.ResponseModel.SettingAllResponse;
import com.spg.sgpco.service.ResponseModel.SettingResultItem;
import com.spg.sgpco.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


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
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    private SettingAllResponse response;

    private SettingResultItem listProjectTypes;
    private ListCitiesItem listStates;
    private CitiesListItem listCities;
    private CustomerItem customerItem;
    private SettingResultItem heatSource;


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


        requestGetAllSetting();

        return view;

    }


    private void requestGetAllSetting() {

        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        GetAllSettingService.getInstance().getAllSetting(getResources(), new ResponseListener<SettingAllResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(SettingAllResponse response) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                CreateProjectFragment.this.response = response;
            }
        });
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
                if (response.getResult().getList_project_type() != null && response.getResult().getList_project_type().size() > 0) {
                    TypeProjectFragment typeProjectList = new TypeProjectFragment();
                    typeProjectList.setTargetFragment(this, Constants.TYPE_PROJECT_CODE);
                    typeProjectList.listTypeProjects = response.getResult().getList_project_type();
                    typeProjectList.typeEnum = TypeEnum.TYPE_PROJECT;
                    loadFragment(typeProjectList, TypeProjectFragment.class.getName());
                }
                break;
            case R.id.province_layout:
                if (response.getResult().getList_cities() != null && response.getResult().getList_cities().size() > 0) {
                    StateFragment stateFragment = new StateFragment();
                    stateFragment.setTargetFragment(this, Constants.STATE_CODE);
                    stateFragment.stateLists = response.getResult().getList_cities();
                    loadFragment(stateFragment, TypeProjectFragment.class.getName());
                }
                break;
            case R.id.city_layout:
                break;
            case R.id.add_customer_layout:
                AddCustomerFragment addCustomerFragment = new AddCustomerFragment();
                addCustomerFragment.setTargetFragment(this, Constants.ADD_CUSTOMER);
                addCustomerFragment.isCreateProjectCustomer = true;
                loadFragment(addCustomerFragment, AddCustomerFragment.class.getName());
                break;
            case R.id.heatـsource_layout:
                if (response.getResult().getHeat_source() != null && response.getResult().getHeat_source().size() > 0) {
                    TypeProjectFragment typeProjectList = new TypeProjectFragment();
                    typeProjectList.setTargetFragment(this, Constants.HEAT_SOURCE);
                    typeProjectList.listTypeProjects = response.getResult().getHeat_source();
                    typeProjectList.typeEnum = TypeEnum.HEAT_SOURCE;
                    loadFragment(typeProjectList, TypeProjectFragment.class.getName());
                }
                break;
            case R.id.system_control_type_layout:
                break;
            case R.id.edtBugReport:
                break;
            case R.id.btnCreate:
                break;
        }
    }

    public void loadFragment(Fragment fragment, String fragmentTag) {
        if (getActivity() != null) {
            FragmentManager fragMgr = getActivity().getSupportFragmentManager();
            FragmentTransaction fragTrans = fragMgr.beginTransaction();
            fragTrans.addToBackStack(fragmentTag);
            fragTrans.add(R.id.frameLayout, fragment, fragmentTag);
            fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragTrans.commit();
        }
    }


    public void showErrorDialog(String description) {
        if (getContext() != null) {
            CustomDialog customDialog = new CustomDialog(getContext());
            customDialog.setOkListener(getString(R.string.retry_text), view -> {
                customDialog.dismiss();
                roundedLoadingView.setVisibility(View.VISIBLE);
                requestGetAllSetting();
            });
            customDialog.setCancelListener(getString(R.string.cancel), view -> customDialog.dismiss());
            customDialog.setIcon(R.drawable.ic_error);
            if (description != null) {
                customDialog.setDescription(description);
            }

            customDialog.setDialogTitle(getString(R.string.communicationError));
            customDialog.show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.TYPE_PROJECT_CODE) {
                typeSelected(data.getParcelableExtra("TypeProject"));
            } else if (requestCode == Constants.STATE_CODE) {
                StateSelected(data.getParcelableExtra("State"));
            } else if (requestCode == Constants.ADD_CUSTOMER) {
                customerSelected(data.getParcelableExtra("Customer"));
            } else if (requestCode == Constants.HEAT_SOURCE) {
                heatSourceSelected(data.getParcelableExtra("HeatSource"));
            }
// else if (requestCode == Constants.REQUEST_IMAGE_CAPTURE) {
//                getImageFromCamera();
//            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void heatSourceSelected(SettingResultItem heatSources) {
        heatSourceLayout.setVisibility(View.VISIBLE);
        heatSource = heatSources;
        if (heatSource != null) {
            heatSourceLayout.setValue(heatSource.getTitle());
        } else {
            heatSourceLayout.setValue("انتخاب کنید");
        }
    }

    private void customerSelected(CustomerItem customer) {
        addCustomerLayout.setVisibility(View.VISIBLE);
        customerItem = customer;
        if (customerItem != null) {
            addCustomerLayout.setValue(customerItem.getName());
        } else {
            addCustomerLayout.setValue("انتخاب کنید");
        }
    }

    private void StateSelected(ListCitiesItem state) {
        provinceLayout.setVisibility(View.VISIBLE);
        listStates = state;
        if (listStates != null) {
            provinceLayout.setValue(listStates.getState());
        } else {
            typeProjectLayout.setValue("انتخاب کنید");
        }
    }


    private void typeSelected(SettingResultItem typeProject) {
        typeProjectLayout.setVisibility(View.VISIBLE);
        listProjectTypes = typeProject;
        if (listProjectTypes != null) {
            typeProjectLayout.setValue(listProjectTypes.getTitle());
        } else {
            typeProjectLayout.setValue("انتخاب کنید");
        }
    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setEnabled(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }

}
