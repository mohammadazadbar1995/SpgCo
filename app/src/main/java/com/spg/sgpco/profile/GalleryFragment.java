package com.spg.sgpco.profile;

import android.content.Intent;
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
import com.spg.sgpco.login.LoginActivity;
import com.spg.sgpco.register.VerifyCodeActivity;
import com.spg.sgpco.service.Request.GalleryService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.ResponseModel.GalleryItem;
import com.spg.sgpco.service.ResponseModel.GalleryResponse;
import com.spg.sgpco.utils.EqualSpacingItemDecoration;
import com.spg.sgpco.utils.PreferencesData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class GalleryFragment extends Fragment implements BackPressedFragment, GalleryAdapter.OnItemClickListener {


    Unbinder unbinder;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.rvShowContent)
    RecyclerView rvShowContent;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    private GalleryResponse response;
    private GridLayoutManager layoutManager;
    private GalleryAdapter adapter;

    public GalleryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_content, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvCenterTitle.setText(getString(R.string.gallery_photo));

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

        GalleryService.getInstance().getGalleryList(getResources(), new ResponseListener<GalleryResponse>() {
            @Override
            public void onGetErrore(String error) {
                if (rvShowContent == null){
                    return;
                }
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);

            }

            @Override
            public void onSuccess(GalleryResponse response) {
                if (rvShowContent == null){
                    return;
                }
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess() && response.getResult().size() > 0) {
                    GalleryFragment.this.response = response;
                    setAdapter();
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

    private void setAdapter() {

        if (adapter == null) {
            adapter = new GalleryAdapter(getActivity(), response.getResult(), this);
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
    public void onItemClick(int position, GalleryItem galleryItem) {

        if (getActivity() == null) {
            return;
        }

        GalleryItemFragment galleryItemFragment = new GalleryItemFragment();
        FragmentManager fragMgr = getActivity().getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMgr.beginTransaction();
        String fragmentTag = ShowContentItemFragment.class.getName();
        Bundle bundle1 = new Bundle();
        bundle1.putParcelable("galleryItem", galleryItem);
        galleryItemFragment.setArguments(bundle1);
        fragTrans.add(R.id.frameLayout, galleryItemFragment, fragmentTag);
        fragTrans.addToBackStack(fragmentTag);
        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragTrans.commit();
    }


}
