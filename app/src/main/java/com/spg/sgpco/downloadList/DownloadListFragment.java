package com.spg.sgpco.downloadList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseFragment;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.service.Request.GetDownloadListService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.ResponseModel.DownloadListItem;
import com.spg.sgpco.service.ResponseModel.DownloadListResponse;
import com.spg.sgpco.utils.PreferencesData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class DownloadListFragment extends BaseFragment implements DownloadListAdapter.OnItemClickListener {


    Unbinder unbinder;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.rvDownloadList)
    RecyclerView rvDownloadList;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;

    private DownloadListAdapter adapter;

    public DownloadListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_download_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvCenterTitle.setText(getResources().getString(R.string.download_list));
        getDownloadList();

        return view;

    }

    private void getDownloadList() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);
        GetDownloadListService.getInstance().getDownloadList(getResources(), new ResponseListener<DownloadListResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);

            }

            @Override
            public void onSuccess(DownloadListResponse response) {
                if (getView() != null && getActivity() != null) {
                    roundedLoadingView.setVisibility(View.GONE);
                    enableDisableViewGroup(root, true);
                    if (response.isSuccess()) {
                        setAdapter(response);
                    }
                }

            }

            @Override
            public void onUtorized() {
                if (getActivity() != null) {
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    PreferencesData.isLogin(getActivity(), false);
                    startActivity(intent);
                }

            }
        });

    }


    private void setAdapter(DownloadListResponse response) {
        adapter = new DownloadListAdapter(response.getResult(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvDownloadList.setHasFixedSize(true);
        rvDownloadList.setLayoutManager(layoutManager);
        rvDownloadList.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onItemClick(int position, DownloadListItem downloadListItem) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(downloadListItem.getLink()));
        startActivity(browserIntent);

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

        if (getContext() == null) {
            return;
        }
        CustomDialog customDialog = new CustomDialog(getContext());
        customDialog.setOkListener(getString(R.string.retry_text), view -> {
            customDialog.dismiss();
            roundedLoadingView.setVisibility(View.VISIBLE);
            getDownloadList();

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
