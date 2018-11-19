package com.spg.sgpco.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.activity.BackPressedFragment;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.Request.ShowContentService;
import com.spg.sgpco.service.ResponseModel.ShowContentItem;
import com.spg.sgpco.service.ResponseModel.ShowContentResponse;
import com.spg.sgpco.utils.EqualSpacingItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class ShowContentFragment extends Fragment implements BackPressedFragment, ShowContentAdapter.OnItemClickListener {


    Unbinder unbinder;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.rvShowContent)
    RecyclerView rvShowContent;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    private ShowContentResponse response;
    private GridLayoutManager layoutManager;
    private ShowContentAdapter adapter;

    public ShowContentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_content, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvCenterTitle.setText(getString(R.string.posts));

        int culomnCount = getResources().getInteger(R.integer.coulem_count_recycle_view);
        layoutManager = new GridLayoutManager(getActivity(), culomnCount, GridLayoutManager.VERTICAL, false);
        rvShowContent.setLayoutManager(layoutManager);
        rvShowContent.addItemDecoration(new EqualSpacingItemDecoration(getResources().getDimensionPixelSize(R.dimen.coulem_offset_recycle_view), EqualSpacingItemDecoration.GRID));


        getShowContentRequest();
        return view;

    }

    private void getShowContentRequest() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        ShowContentService.getInstance().getShowContent(getResources(), new ResponseListener<ShowContentResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);

            }

            @Override
            public void onSuccess(ShowContentResponse response) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess() && response.getResult().size() > 0) {
                    ShowContentFragment.this.response = response;
                    setAdapter();
                }
            }
        });
    }

    private void setAdapter() {

        if (adapter == null) {
            adapter = new ShowContentAdapter(getActivity(), response.getResult(), this);
            rvShowContent.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void showErrorDialog(String description) {

        if (getActivity() == null) {
            return;
        }
        CustomDialog customDialog = new CustomDialog(getActivity());
        customDialog.setOkListener(getString(R.string.retry_text), view -> {
            customDialog.dismiss();
            roundedLoadingView.setVisibility(View.VISIBLE);
            getShowContentRequest();
        });
        customDialog.setCancelListener(getString(R.string.cancel), view -> customDialog.dismiss());
        customDialog.setIcon(R.drawable.ic_error);
        if (description != null) {
            customDialog.setDescription(description);
        }

        customDialog.setDialogTitle(getString(R.string.communicationError));
        customDialog.show();

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

    @Override
    public void onItemClick(int position, ShowContentItem showContentItem) {

        if (getActivity() == null) {
            return;
        }

        ShowContentItemFragment showContentItemFragment = new ShowContentItemFragment();
        FragmentManager fragMgr = getActivity().getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMgr.beginTransaction();
        String fragmentTag = ShowContentItemFragment.class.getName();
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("showContentItem", showContentItem);
        showContentItemFragment.setArguments(bundle1);
        fragTrans.add(R.id.frameLayout, showContentItemFragment, fragmentTag);
        fragTrans.addToBackStack(fragmentTag);
        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragTrans.commit();
    }


}
