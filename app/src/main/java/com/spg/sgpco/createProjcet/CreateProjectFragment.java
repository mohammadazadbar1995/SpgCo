package com.spg.sgpco.createProjcet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spg.sgpco.R;
import com.spg.sgpco.activity.BackPressedFragment;
import com.spg.sgpco.addCustomer.AddCustomerFragment;
import com.spg.sgpco.baseView.BaseFragment;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.customView.CustomInsertDataView;
import com.spg.sgpco.customView.CustomMultiLineEditText;
import com.spg.sgpco.customView.CustomNoReduisEditText;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.enums.ControlSystemEnum;
import com.spg.sgpco.enums.TypeEnum;
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.service.Request.GetProjectDataItemService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.ResponseModel.CitiesListItem;
import com.spg.sgpco.service.ResponseModel.CustomerItem;
import com.spg.sgpco.service.ResponseModel.ListCitiesItem;
import com.spg.sgpco.service.ResponseModel.SettingAllResponse;
import com.spg.sgpco.service.ResponseModel.SettingResultItem;
import com.spg.sgpco.service.ResponseModel.SystemsItem;
import com.spg.sgpco.service.ResponseModel.UpdateProjectResponse;
import com.spg.sgpco.utils.Constants;
import com.spg.sgpco.utils.PreferencesData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class CreateProjectFragment extends BaseFragment implements BackPressedFragment {


    public boolean isUpdate = false;
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
    public SettingAllResponse response;

    private SettingResultItem listProjectTypes;
    private ListCitiesItem listStates;
    private CitiesListItem listCities;
    private CustomerItem customerItem;
    private SettingResultItem heatSource;
    private SystemsItem systemsItems;
    private int controlOfSystem;
    private int itemId;
    private UpdateProjectResponse updateResponse;
    private String link;


    public CreateProjectFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_project, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvCenterTitle.setText(getResources().getString(R.string.create_project));

        typeProjectLayout.setImgInfo(R.drawable.ic_type_project);
        typeProjectLayout.setTxtTitle("");
        typeProjectLayout.setHint(getString(R.string.select_type_name_project));

        provinceLayout.setImgInfo(R.drawable.ic_city_project);
        provinceLayout.setTxtTitle("");
        provinceLayout.setHint(getString(R.string.select_province));


        cityLayout.setImgInfo(R.drawable.ic_city_project);
        cityLayout.setTxtTitle("");
        cityLayout.setHint(getString(R.string.select_city));


        addCustomerLayout.setImgInfo(R.drawable.ic_custome_project);
        addCustomerLayout.setTxtTitle("");
        addCustomerLayout.setHint(getString(R.string.select_customer));


        heatSourceLayout.setImgInfo(R.drawable.ic_heat_project);
        heatSourceLayout.setTxtTitle("");
        heatSourceLayout.setHint(getString(R.string.heat_source));


        systemControlTypeLayout.setImgInfo(R.drawable.ic_heat_project);
        systemControlTypeLayout.setTxtTitle("");
        systemControlTypeLayout.setHint(getString(R.string.system_control_type));

        btnCreate.setText(getString(R.string.next_page));
        btnCreate.setVisibility(View.GONE);

//        if (getActivity() != null) {
//            response = ((MainActivitySecond) getActivity()).getResponseAll();
//        }
        if (isUpdate) {
            Bundle b = getArguments();
            if (b != null) {
                itemId = b.getInt("itemId");
                link = b.getString("link");
                response = b.getParcelable("allResponseProject");
                getProjectDataReqeust();
            }
        } else {

        }

        return view;

    }

    private void getProjectDataReqeust() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        GetProjectDataItemService.getInstance().getProjectDataItem(getResources(), itemId, new ResponseListener<UpdateProjectResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(UpdateProjectResponse response) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);

                if (response.isSuccess() && response.getResult() != null) {
                    CreateProjectFragment.this.updateResponse = response;
                    setDataInView(response);
                }
            }

            @Override
            public void onUtorized() {
                if (getActivity() == null) {
                    return;
                }
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                PreferencesData.isLogin(getActivity(), false);

                startActivity(intent);
            }
        });
    }

    private void setDataInView(UpdateProjectResponse response) {
        listProjectTypes = response.getResult().getProject_type();
        listCities = response.getResult().getCity();
        for (int i = 0; i < this.response.getResult().getList_cities().size(); i++) {
            if (this.response.getResult().getList_cities().get(i).getState_id() == response.getResult().getState_id()) {
                provinceLayout.setValue(this.response.getResult().getList_cities().get(i).getState());
                listStates = this.response.getResult().getList_cities().get(i);
            }
        }
        customerItem = response.getResult().getCustomer();
        heatSource = response.getResult().getHeat_source();
        systemsItems = response.getResult().getSystems_type();

        btnCreate.setVisibility(View.VISIBLE);
        systemControlTypeLayout.setEnabled(false);
        systemControlTypeLayout.setClickable(false);
        edtName.setBody(response.getResult().getTitle());

        if (response.getResult().getProject_type() != null) {
            typeProjectLayout.setValue(response.getResult().getProject_type().getTitle());
        } else {
            typeProjectLayout.reset();
        }

        if (response.getResult().getCity() != null) {
            cityLayout.setValue(response.getResult().getCity().getCity());
        } else {
            cityLayout.reset();
        }
        if (response.getResult().getCustomer() != null) {
            addCustomerLayout.setValue(response.getResult().getCustomer().getName());
        } else {
            addCustomerLayout.reset();
        }
        if (response.getResult().getHeat_source() != null) {
            heatSourceLayout.setValue(response.getResult().getHeat_source().getTitle());
        } else {
            heatSourceLayout.reset();
        }
        if (response.getResult().getSystems_type() != null) {
            controlOfSystem = response.getResult().getSystems_type().getId();
            systemControlTypeLayout.setValue(response.getResult().getSystems_type().getTitle());
        } else {
            controlOfSystem = 1;
            systemControlTypeLayout.reset();

        }

        edtBugReport.setBody(response.getResult().getDescription());
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
                    loadFragment(stateFragment, StateFragment.class.getName());

                }
                break;
            case R.id.city_layout:
                if (listStates != null) {
                    if (listStates.getCities_list() != null && listStates.getCities_list().size() > 0) {
                        CityFragment cityFragment = new CityFragment();
                        cityFragment.setTargetFragment(this, Constants.CITY_CODE);
                        cityFragment.citiesItems = listStates.getCities_list();
                        loadFragment(cityFragment, CityFragment.class.getName());
                    }
                } else {
                    Toast.makeText(getActivity(), getString(R.string.first_select_state), Toast.LENGTH_SHORT).show();
                }

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
                if (response.getResult().getSystems() != null && response.getResult().getSystems().size() > 0) {
                    TypeOfControlSystemFragment typeOfControlSystemFragment = new TypeOfControlSystemFragment();
                    typeOfControlSystemFragment.setTargetFragment(this, Constants.TYPE_OF_CONTROL_SYSTEMS);
                    typeOfControlSystemFragment.systemsItems = response.getResult().getSystems();
                    loadFragment(typeOfControlSystemFragment, TypeOfControlSystemFragment.class.getName());
                }
                break;
            case R.id.edtBugReport:
                break;
            case R.id.btnCreate:
                if (isValidData()) {
                    if (controlOfSystem == ControlSystemEnum.ORDINARY_SYSTEM.getMethodCode()) {
                        OrdinarySystemFragment ordinarySystemFragment = new OrdinarySystemFragment();
                        ordinarySystemFragment.floorList = response.getResult().getList_floor();
                        ordinarySystemFragment.isUpdate = isUpdate;
                        Bundle bundle = new Bundle();
                        bundle.putString("title", edtName.getValueString());
                        bundle.putInt("customer_id", customerItem.getId());
                        bundle.putInt("city_id", Integer.valueOf(listCities.getId()));
                        bundle.putInt("state_id", listStates.getState_id());
                        bundle.putInt("project_type_id", listProjectTypes.getId());
                        bundle.putInt("systems_type_id", systemsItems.getId());
                        bundle.putInt("heat_source_id", heatSource.getId());
                        bundle.putString("description", edtBugReport.getValue());
                        bundle.putString("link", link);
                        if (isUpdate) {
                            bundle.putParcelable("updateSystemsOrdinary", updateResponse.getResult());
                        }
                        ordinarySystemFragment.setArguments(bundle);
                        loadFragment(ordinarySystemFragment, OrdinarySystemFragment.class.getName());
                    } else if (controlOfSystem == ControlSystemEnum.THERMOSTATIC_SYSTEM.getMethodCode()) {
                        ThermostaticSystemFragment thermostaticSystemFragment = new ThermostaticSystemFragment();
                        thermostaticSystemFragment.floorList = response.getResult().getList_floor();
                        thermostaticSystemFragment.typeSpace = response.getResult().getType_of_space();
                        thermostaticSystemFragment.isUpdate = isUpdate;
                        Bundle bundle = new Bundle();
                        bundle.putString("title", edtName.getValueString());
                        bundle.putInt("customer_id", customerItem.getId());
                        bundle.putInt("city_id", Integer.valueOf(listCities.getId()));
                        bundle.putInt("state_id", listStates.getState_id());
                        bundle.putInt("project_type_id", listProjectTypes.getId());
                        bundle.putInt("systems_type_id", systemsItems.getId());
                        bundle.putInt("heat_source_id", heatSource.getId());
                        bundle.putString("description", edtBugReport.getValue());
                        bundle.putString("link", link);
                        if (isUpdate) {
                            bundle.putParcelable("updateSystemsThermostatic", updateResponse.getResult());
                        }
                        thermostaticSystemFragment.setArguments(bundle);
                        loadFragment(thermostaticSystemFragment, ThermostaticSystemFragment.class.getName());
                    }
                }

                break;
        }
    }

    private boolean isValidData() {
        ArrayList<String> errorMsgList = new ArrayList<>();
        if (edtName.getError() != null) {
            String message = getResources().getString(R.string.enter_your_project_name);
            errorMsgList.add(message);
        }

        if (listProjectTypes == null) {
            String message = getResources().getString(R.string.select_type_name_project);
            typeProjectLayout.setError(message);
            errorMsgList.add(message);
        }


        if (listStates == null) {
            String message = getResources().getString(R.string.select_province);
            provinceLayout.setError(message);
            errorMsgList.add(message);
        }


        if (listCities == null) {
            String message = getResources().getString(R.string.select_your_city);
            cityLayout.setError(message);
            errorMsgList.add(message);
        }

        if (customerItem == null) {
            String message = getResources().getString(R.string.select_customer);
            addCustomerLayout.setError(message);
            errorMsgList.add(message);
        }

        if (heatSource == null) {
            String message = getResources().getString(R.string.selectd_heat_source);
            heatSourceLayout.setError(message);
            errorMsgList.add(message);
        }

        if (systemsItems == null) {
            String message = getResources().getString(R.string.select_type_control_system);
            systemControlTypeLayout.setError(message);
            errorMsgList.add(message);
        }

        if (errorMsgList.size() > 0) {
            showInfoDialog(getString(R.string.fill_following), errorMsgList);
            return false;
        }
        return true;
    }

    public void loadFragment(Fragment fragment, String fragmentTag) {
        if (getActivity() != null) {
            FragmentManager fragMgr = getActivity().getSupportFragmentManager();
            FragmentTransaction fragTrans = fragMgr.beginTransaction();
//            fragTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            fragTrans.addToBackStack(fragmentTag);
            fragTrans.add(R.id.frameLayout, fragment, fragmentTag);
            fragTrans.commit();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.TYPE_PROJECT_CODE) {
                typeSelected(data.getParcelableExtra("TypeProject"));
            } else if (requestCode == Constants.STATE_CODE) {
                StateSelected(data.getParcelableExtra("State"));
            } else if (requestCode == Constants.CITY_CODE) {
                citySelected(data.getParcelableExtra("City"));
            } else if (requestCode == Constants.ADD_CUSTOMER) {
                customerSelected(data.getParcelableExtra("Customer"));
            } else if (requestCode == Constants.HEAT_SOURCE) {
                heatSourceSelected(data.getParcelableExtra("HeatSource"));
            } else if (requestCode == Constants.TYPE_OF_CONTROL_SYSTEMS) {
                typeOfControlSystems(data.getParcelableExtra("SystemsItems"));
            }
// else if (requestCode == Constants.REQUEST_IMAGE_CAPTURE) {
//                getImageFromCamera();
//            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void typeOfControlSystems(SystemsItem system) {
        systemControlTypeLayout.setVisibility(View.VISIBLE);
        systemsItems = system;
        if (systemsItems != null) {
            systemControlTypeLayout.setValue(systemsItems.getTitle());
        } else {
            systemControlTypeLayout.setValue("انتخاب کنید");
        }
        btnCreate.setVisibility(View.VISIBLE);
        controlOfSystem = systemsItems.getId();
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
            cityLayout.setEnabled(true);
        } else {
            typeProjectLayout.reset();
        }

        citySelected(null);
    }

    private void citySelected(CitiesListItem citiesListItem) {
        listCities = citiesListItem;
        if (listCities != null) {
            cityLayout.setVisibility(View.VISIBLE);
            cityLayout.setValue(listCities.getCity());
        } else {
            if (listStates != null) {
                cityLayout.setVisibility(View.VISIBLE);
                cityLayout.setHint(getString(R.string.select_city));
                cityLayout.reset();

            } else {
                cityLayout.setValue(getString(R.string.select_city));
            }
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

    public void showErrorDialog(String description) {

        if (getActivity() != null) {
            CustomDialog customDialog = new CustomDialog(getActivity());
            customDialog.setOkListener(getString(R.string.retry_text), view -> {
                customDialog.dismiss();
                roundedLoadingView.setVisibility(View.VISIBLE);
                getProjectDataReqeust();
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
    public void onPopBackStack() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
