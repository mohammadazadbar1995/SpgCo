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
import com.spg.sgpco.activity.ShowProjectWebViewFragment;
import com.spg.sgpco.baseView.BaseFragment;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.customView.CustomInsertDataView;
import com.spg.sgpco.customView.CustomNoReduisEditText;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.enums.TypeEnum;
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.service.Request.OrdinaryProjectService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.Request.UpdateOrdinaryProjectService;
import com.spg.sgpco.service.RequestModel.CreateOrdinaryProjectReq;
import com.spg.sgpco.service.RequestModel.OrdinarySystem;
import com.spg.sgpco.service.RequestModel.UpdateOrdinaryProjectReq;
import com.spg.sgpco.service.ResponseModel.CreateOrdinaryProjectResponse;
import com.spg.sgpco.service.ResponseModel.SettingResultItem;
import com.spg.sgpco.service.ResponseModel.SucessUpdateResponse;
import com.spg.sgpco.service.ResponseModel.UpdateProjectResult;
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

public class OrdinarySystemFragment extends BaseFragment implements BackPressedFragment {


    public ArrayList<SettingResultItem> floorList;
    public boolean isUpdate;
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
    private int stateId;
    private int projectTypeId;
    private int systemsTypeId;
    private int heatSourceId;
    private String descreption;
    private UpdateProjectResult updateSystemsOrdinary;

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
            stateId = b.getInt("state_id");
            projectTypeId = b.getInt("project_type_id");
            systemsTypeId = b.getInt("systems_type_id");
            heatSourceId = b.getInt("heat_source_id");
            descreption = b.getString("description");
            updateSystemsOrdinary = b.getParcelable("updateSystemsOrdinary");
        }

        if (isUpdate) {
            setDataInView();
        }

        return view;

    }

    private void setDataInView() {
        genderFloor = updateSystemsOrdinary.getOrdinary_system().getFloor_type();
        genderOfFloorLayout.setValue(genderFloor.getTitle());
        edtMetr.setBody(updateSystemsOrdinary.getOrdinary_system().getMetr());
        edtColdArea.setBody(updateSystemsOrdinary.getOrdinary_system().getCold_area());
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
                    if (isUpdate) {
                        updateSystemRequest();
                    } else {
                        createOrdinaryProjectRequest();
                    }

                }
                break;
        }

    }


    private void updateSystemRequest() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);


        UpdateOrdinaryProjectReq req = new UpdateOrdinaryProjectReq();
        req.setProject_id(updateSystemsOrdinary.getId());
        req.setTitle(title);
        req.setCustomer_id(customerId);
        req.setCity_id(cityId);
        req.setState_id(stateId);
        req.setProject_type_id(projectTypeId);
        req.setSystems_type_id(systemsTypeId);
        req.setHeat_source_id(heatSourceId);
        req.setContent(descreption);
        OrdinarySystem ordinarySystem = new OrdinarySystem();
        ordinarySystem.setMetr(edtMetr.getValueInt());
        ordinarySystem.setCold_area(edtColdArea.getValueInt());
        ordinarySystem.setFloor_type_id(genderFloor.getId());
        req.setOrdinary_system(ordinarySystem);
        UpdateOrdinaryProjectService.getInstance().updateOrdinaryProject(getResources(), req, new ResponseListener<SucessUpdateResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(SucessUpdateResponse response) {
                roundedLoadingView.setVisibility(View.VISIBLE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess()) {
                    if (getActivity() != null) {
                        getActivity().finish();
                        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();

                    }
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

    private void createOrdinaryProjectRequest() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        CreateOrdinaryProjectReq req = new CreateOrdinaryProjectReq();
        req.setName(title);
        req.setCustomer_id(customerId);
        req.setCity_id(cityId);
        req.setState_id(stateId);
        req.setProject_type_id(projectTypeId);
        req.setSystems_type_id(systemsTypeId);
        req.setHeat_source_id(heatSourceId);
        req.setContent(descreption);
        OrdinarySystem ordinarySystem = new OrdinarySystem();
        ordinarySystem.setMetr(edtMetr.getValueInt());
        ordinarySystem.setCold_area(edtColdArea.getValueInt());
        ordinarySystem.setFloor_type_id(genderFloor.getId());
        req.setOrdinary_system(ordinarySystem);
        PreferencesData.isShowPdf(getActivity(), false);
        OrdinaryProjectService.getInstance().createOrdinaryProject(getResources(), req, new ResponseListener<CreateOrdinaryProjectResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(CreateOrdinaryProjectResponse response) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess()) {
                    if (getActivity() != null) {
                        ShowProjectWebViewFragment showProjectWebViewFragment = new ShowProjectWebViewFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("link", response.getResult().getCode());
                        showProjectWebViewFragment.setArguments(bundle);
                        FragmentManager fragMgr = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragTrans = fragMgr.beginTransaction();
//                        fragTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

                        fragTrans.add(R.id.frameLayout, showProjectWebViewFragment, ShowProjectWebViewFragment.class.getName());
                        fragTrans.addToBackStack(ShowProjectWebViewFragment.class.getName());
                        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();

                        fragTrans.commit();

//                        HomeFragment homeFragment = new HomeFragment();
//                        FragmentManager fragMgr = getActivity().getSupportFragmentManager();
//                        FragmentTransaction fragTrans = fragMgr.beginTransaction();
//                        fragTrans.add(R.id.frameLayout, homeFragment, HomeFragment.class.getName());
//                        fragTrans.addToBackStack(HomeFragment.class.getName());
//                        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//                        fragTrans.commit();
//                        Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
//                        if (getActivity() instanceof MainActivitySecond)
//                            ((MainActivitySecond) getActivity()).getNavigation().setSelectedItemId(R.id.tab_home);
                    }
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

    @Override
    public void onPopBackStack() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
