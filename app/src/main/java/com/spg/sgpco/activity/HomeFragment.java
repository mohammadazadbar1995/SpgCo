package com.spg.sgpco.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseFragment;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.createProjcet.CreateProjectFragment;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.service.Request.DeleteProjectService;
import com.spg.sgpco.service.Request.GetProjectListService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.RequestModel.DeleteProjectReq;
import com.spg.sgpco.service.ResponseModel.DeleteProjectResponse;
import com.spg.sgpco.service.ResponseModel.GetProjectListResponse;
import com.spg.sgpco.service.ResponseModel.ProjectListResultItem;
import com.spg.sgpco.service.ResponseModel.SettingAllResponse;
import com.spg.sgpco.utils.PreferencesData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.tvEmptyView)
    BaseTextView tvEmptyView;
    @BindView(R.id.rootEmptyView)
    BaseRelativeLayout rootEmptyView;
    @BindView(R.id.fabButton)
    FloatingActionButton fabButton;
    private ProjectListResultItem list;
    private GetListProjectAdapter adapter;
    public SettingAllResponse allResponseProject;


    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvCenterTitle.setText(getString(R.string.projects));
        fabButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.redColor)));


        getProjectListRequest();
        return view;

    }

    private void getProjectListRequest() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, true);

        GetProjectListService.getInstance().getProjectList(getResources(), new ResponseListener<GetProjectListResponse>() {
            @Override
            public void onGetErrore(String error) {

                if (rvProject == null) {
                    return;
                }
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(GetProjectListResponse response) {
                if (rvProject == null) {
                    return;
                }
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess() && response.getResult().size() > 0) {
                    rootEmptyView.setVisibility(View.GONE);
                    setAdapter(response);
                } else {
                    rootEmptyView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onUtorized() {
                if (getActivity() == null){
                    return;
                }
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                PreferencesData.isLogin(getActivity(), false);
                startActivity(intent);
            }
        });

    }

    private void setAdapter(GetProjectListResponse response) {
        adapter = new GetListProjectAdapter(getActivity(), response.getResult(), this);
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
            CreateProjectFragment createProjectFragment1 = new CreateProjectFragment();
            FragmentManager fragMgr = getActivity().getSupportFragmentManager();
            FragmentTransaction fragTrans = fragMgr.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putInt("itemId", list.getId());
            bundle.putParcelable("allResponseProject", allResponseProject);
            createProjectFragment1.setArguments(bundle);
            createProjectFragment1.isUpdate = true;
            fragTrans.add(R.id.frameLayout, createProjectFragment1, CreateProjectFragment.class.getName());
            fragTrans.addToBackStack(CreateProjectFragment.class.getName());
            fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragTrans.commit();
        }

    }

    @Override
    public void onDelete() {
        deleteProjectRequest();
    }

    @Override
    public void onPdfItem() {
        if (getActivity() != null) {
            ShowProjectWebViewFragment showProjectWebViewFragment = new ShowProjectWebViewFragment();
            Bundle bundle = new Bundle();
            bundle.putString("link", list.getLink());
            showProjectWebViewFragment.setArguments(bundle);
            FragmentManager fragMgr = getActivity().getSupportFragmentManager();
            FragmentTransaction fragTrans = fragMgr.beginTransaction();
            PreferencesData.isShowPdf(getActivity(), true);
//            fragTrans.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
//            fragTrans.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
            fragTrans.add(R.id.frameLayout, showProjectWebViewFragment, ShowProjectWebViewFragment.class.getName());
            fragTrans.addToBackStack(ShowProjectWebViewFragment.class.getName());
            fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

            fragTrans.commit();
        }

    }

    private void deleteProjectRequest() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, true);

        DeleteProjectReq req = new DeleteProjectReq();
        req.setProject_id(list.getId());
        DeleteProjectService.getInstance().deleteProject(getResources(), req, new ResponseListener<DeleteProjectResponse>() {
            @Override
            public void onGetErrore(String error) {
                if (rvProject == null) {
                    return;
                }
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, false);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(DeleteProjectResponse response) {
                if (rvProject == null) {
                    return;
                }
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, false);
                if (response.isSuccess()) {
                    getProjectListRequest();
                    Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onUtorized() {
                if (getActivity() == null){
                    return;
                }
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                PreferencesData.isLogin(getActivity(), false);
                startActivity(intent);
            }
        });

    }


    @OnClick(R.id.fabButton)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), Activity.class);
        intent.putExtra("two", "two");
        intent.putExtra("responseAllProject", allResponseProject);
        PreferencesData.isList(getActivity(), true);
        PreferencesData.isShowPdf(getActivity(), false);
        startActivity(intent);
    }
}
