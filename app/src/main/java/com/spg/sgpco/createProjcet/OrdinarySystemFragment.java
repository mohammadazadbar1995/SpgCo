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
import com.spg.sgpco.activity.HomeFragment;
import com.spg.sgpco.activity.MainActivitySecond;
import com.spg.sgpco.baseView.BaseFragment;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.customView.CustomInsertDataView;
import com.spg.sgpco.customView.CustomNoReduisEditText;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.enums.TypeEnum;
import com.spg.sgpco.service.Request.OrdinaryProjectService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.RequestModel.CreateOrdinaryProjectReq;
import com.spg.sgpco.service.RequestModel.OrdinarySystem;
import com.spg.sgpco.service.ResponseModel.CreateOrdinaryProjectResponse;
import com.spg.sgpco.service.ResponseModel.SettingResultItem;
import com.spg.sgpco.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class OrdinarySystemFragment extends BaseFragment {


    public ArrayList<SettingResultItem> floorList;
    Unbinder unbinder;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.gender_of_floor_layout)
    CustomInsertDataView genderOfFloorLayout;
    @BindView(R.id.edtMetr)
    CustomNoReduisEditText edtMetr;
    @BindView(R.id.edtColdArea)
    CustomNoReduisEditText edtColdArea;
    @BindView(R.id.btnCreate)
    BaseTextView btnCreate;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;

    private SettingResultItem genderFloor;
    private String title;
    private int customerId;
    private int cityId;
    private int projectTypeId;
    private int systemsTypeId;
    private int heatSourceId;
    private String descreption;

    public OrdinarySystemFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ordinary_project, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvCenterTitle.setText(getResources().getString(R.string.ordinary_system));

        genderOfFloorLayout.setImgInfo(R.drawable.ic_person_gray);
        genderOfFloorLayout.setTxtTitle("");
        genderOfFloorLayout.setHint(getString(R.string.gendar_of_floor));

        Bundle b = getArguments();
        if (b != null) {
            title = b.getString("title");
            customerId = b.getInt("customer_id");
            cityId = b.getInt("city_id");
            projectTypeId = b.getInt("project_type_id");
            systemsTypeId = b.getInt("systems_type_id");
            heatSourceId = b.getInt("heat_source_id");
            descreption = b.getString("description");
        }


        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.gender_of_floor_layout, R.id.btnCreate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gender_of_floor_layout:
                TypeProjectFragment typeProjectList = new TypeProjectFragment();
                typeProjectList.setTargetFragment(this, Constants.GENDER_FLOOR);
                typeProjectList.listTypeProjects = floorList;
                typeProjectList.typeEnum = TypeEnum.GENDER_FLOOR;
                loadFragment(typeProjectList, TypeProjectFragment.class.getName());
                break;
            case R.id.btnCreate:
                if (isValideData()) {
                    createOrdinaryProjectRequest();
                }
                break;
        }

    }

    private void createOrdinaryProjectRequest() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        CreateOrdinaryProjectReq req = new CreateOrdinaryProjectReq();
        req.setName(title);
        req.setCustomer_id(customerId);
        req.setCity_id(cityId);
        req.setProject_type_id(projectTypeId);
        req.setSystems_type_id(systemsTypeId);
        req.setHeat_source_id(heatSourceId);
        req.setDescription(descreption);
        OrdinarySystem ordinarySystem = new OrdinarySystem();
        ordinarySystem.setMetr(edtMetr.getValueInt());
        ordinarySystem.setCold_area(edtColdArea.getValueInt());
        ordinarySystem.setFloor_type_id(genderFloor.getId());
        req.setOrdinary_system(ordinarySystem);
        OrdinaryProjectService.getInstance().createOrdinaryProject(getResources(), req, new ResponseListener<CreateOrdinaryProjectResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.VISIBLE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(CreateOrdinaryProjectResponse response) {
                roundedLoadingView.setVisibility(View.VISIBLE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess()) {
                    if (getActivity() != null) {
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentManager fragMgr = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragTrans = fragMgr.beginTransaction();
                        fragTrans.add(R.id.frameLayout, homeFragment, HomeFragment.class.getName());
                        fragTrans.addToBackStack(HomeFragment.class.getName());
                        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragTrans.commit();
                        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        if (getActivity() instanceof MainActivitySecond)
                            ((MainActivitySecond) getActivity()).getNavigation().setSelectedItemId(R.id.tab_home);
                    }
                }
            }
        });
    }


    private boolean isValideData() {
        ArrayList<String> errorMsgList = new ArrayList<>();

        if (genderFloor == null) {
            String message = getResources().getString(R.string.gendar_of_floor);
            genderOfFloorLayout.setError(message);
            errorMsgList.add(message);
        }

        if (edtMetr.getError() != null) {
            String message = getResources().getString(R.string.enter_metr);
            errorMsgList.add(message);
        }

        if (edtColdArea.getError() != null) {
            String message = getResources().getString(R.string.enter_cold_area);
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
            fragTrans.addToBackStack(fragmentTag);
            fragTrans.add(R.id.frameLayout, fragment, fragmentTag);
            fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragTrans.commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.GENDER_FLOOR) {
                genderFloorSelected(data.getParcelableExtra("Gender_Project"));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void genderFloorSelected(SettingResultItem gender) {
        genderOfFloorLayout.setVisibility(View.VISIBLE);
        genderFloor = gender;
        if (genderFloor != null) {
            genderOfFloorLayout.setValue(genderFloor.getTitle());
        } else {
            genderOfFloorLayout.setValue("انتخاب کنید");
        }
    }

    public void showErrorDialog(String description) {
        if (getContext() != null) {
            CustomDialog customDialog = new CustomDialog(getContext());
            customDialog.setOkListener(getString(R.string.retry_text), view -> {
                customDialog.dismiss();
                roundedLoadingView.setVisibility(View.VISIBLE);
                createOrdinaryProjectRequest();
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