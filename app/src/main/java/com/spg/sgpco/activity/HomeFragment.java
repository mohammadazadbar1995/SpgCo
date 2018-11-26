package com.spg.sgpco.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseFragment;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.createProjcet.CreateProjectFragment;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.service.Request.GetProjectListService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.ResponseModel.GetProjectListResponse;
import com.spg.sgpco.service.ResponseModel.ProjectListResultItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class HomeFragment extends BaseFragment implements GetListProjectAdapter.OnItemClickListener, OpenEditFragment {


    Unbinder unbinder;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.rvProject)
    RecyclerView rvProject;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    private ProjectListResultItem list;


    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvCenterTitle.setText(getString(R.string.dashbpard));

        getProjectListRequest();
        return view;

    }

    private void getProjectListRequest() {
//        roundedLoadingView.setVisibility(View.VISIBLE);
//        enableDisableViewGroup(root, false);

        GetProjectListService.getInstance().getProjectList(getResources(), new ResponseListener<GetProjectListResponse>() {
            @Override
            public void onGetErrore(String error) {
//                roundedLoadingView.setVisibility(View.GONE);
//                enableDisableViewGroup(root, true);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(GetProjectListResponse response) {
//                roundedLoadingView.setVisibility(View.GONE);
//                enableDisableViewGroup(root, true);
                if (response.isSuccess() && response.getResult().size() > 0) {
                    setAdapter(response);
                }
            }
        });

    }

    private void setAdapter(GetProjectListResponse response) {
        GetListProjectAdapter adapter = new GetListProjectAdapter(getActivity(), response.getResult(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvProject.setHasFixedSize(true);
        rvProject.setLayoutManager(layoutManager);
        rvProject.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                getProjectListRequest();
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
    public void onItemClick(int position, ProjectListResultItem lists) {
        this.list = lists;
        if (getContext() != null) {
            SelectedDialog selectedDialog = new SelectedDialog(getContext());
            selectedDialog.setItem(lists);
            selectedDialog.setListener(this);
            selectedDialog.show();
        }

    }

    @Override
    public void onEdit() {

        if (getActivity() != null) {
            if (getActivity() instanceof MainActivitySecond)
                ((MainActivitySecond) getActivity()).getNavigation().setSelectedItemId(R.id.tab_create_project);

            CreateProjectFragment createProjectFragment1 = new CreateProjectFragment();
            FragmentManager fragMgr = getActivity().getSupportFragmentManager();
            FragmentTransaction fragTrans = fragMgr.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putInt("itemId", list.getId());
            createProjectFragment1.isUpdate = true;
            fragTrans.add(R.id.frameLayout, createProjectFragment1, CreateProjectFragment.class.getName());
            fragTrans.addToBackStack(CreateProjectFragment.class.getName());
            fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragTrans.commit();
        }

    }
}
