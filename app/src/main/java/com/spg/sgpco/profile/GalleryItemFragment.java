package com.spg.sgpco.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.activity.BackPressedFragment;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.service.Request.GalleryItemService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.ResponseModel.GalleryItem;
import com.spg.sgpco.service.ResponseModel.GalleryItemList;
import com.spg.sgpco.service.ResponseModel.GalleryItemResponse;
import com.spg.sgpco.utils.EqualSpacingItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class GalleryItemFragment extends Fragment implements BackPressedFragment {


    Unbinder unbinder;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.rvShowContent)
    RecyclerView rvShowContent;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;

    private GalleryItem galleryItem;
    private GridLayoutManager layoutManager;
    private GalleryItemAdapter adapter;


    public GalleryItemFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_show_content, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle b = getArguments();
        if (b != null) {
            galleryItem = b.getParcelable("galleryItem");
        }

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

        GalleryItemService.getInstance().getGalleryItem(getResources(), String.valueOf(galleryItem.getId()), new ResponseListener<GalleryItemResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error);
            }

            @Override
            public void onSuccess(GalleryItemResponse response) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess() && response.getResult() != null) {
                    tvCenterTitle.setText(response.getResult().getPost().getPost_title());
                    setAdapter(response.getResult().getGallery());
                }
            }
        });
    }

    private void setAdapter(ArrayList<GalleryItemList> gallery) {
        if (adapter == null) {
            adapter = new GalleryItemAdapter(getActivity(), gallery);
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

}
