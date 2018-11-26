package com.spg.sgpco.addCustomer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseFragment;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.createProjcet.StateFragment;
import com.spg.sgpco.customView.CustomEditText;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.database.Customer;
import com.spg.sgpco.dialog.CustomDialog;
import com.spg.sgpco.service.Request.CreateCustomerService;
import com.spg.sgpco.service.Request.DeleteCustomerService;
import com.spg.sgpco.service.Request.GetListCustomerService;
import com.spg.sgpco.service.Request.ResponseListener;
import com.spg.sgpco.service.Request.UpdateCustomerService;
import com.spg.sgpco.service.RequestModel.CreateCustomerReq;
import com.spg.sgpco.service.RequestModel.DeleteCustomerReq;
import com.spg.sgpco.service.RequestModel.UpdateCustomerReq;
import com.spg.sgpco.service.ResponseModel.CreateCustomerResponse;
import com.spg.sgpco.service.ResponseModel.CustomerItem;
import com.spg.sgpco.service.ResponseModel.DeleteCustomerResponse;
import com.spg.sgpco.service.ResponseModel.GetListCustomerResponse;
import com.spg.sgpco.service.ResponseModel.UpdateCustomerResponse;

import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class AddCustomerFragment extends BaseFragment implements CustomerAdapter.OnItemClickListener {


    public boolean isCreateProjectCustomer;
    Unbinder unbinder;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.edtAddCustomer)
    CustomEditText edtAddCustomer;
    @BindView(R.id.btnCreate)
    BaseTextView btnCreate;
    @BindView(R.id.rlCreate)
    BaseRelativeLayout rlCreate;
    @BindView(R.id.rvCustomer)
    RecyclerView rvCustomer;
    List<CustomerItem> customers;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    private Customer customer = new Customer();
    private CustomerAdapter adapter;
    private int DELETE_ITEM = 1;
    private int GET_LIST = 0;
    private int CREATE_CUSTOMER = 2;
    private CustomerItem customerItem;
    private boolean isUpdate = false;
    private int UPDATE_CUSTOMER = 3;

    public AddCustomerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_customer, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvCenterTitle.setText(getResources().getString(R.string.create_project));

//        setAdapter();
        getCustomerList();
        btnCreate.setText(getResources().getString(R.string.create));
        return view;

    }

    private void getCustomerList() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);
        GetListCustomerService.getInstance().getListCustomer(getResources(), new ResponseListener<GetListCustomerResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error, GET_LIST);

            }

            @Override
            public void onSuccess(GetListCustomerResponse response) {

                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess()) {
                    setAdapter(response);
                    btnCreate.setText(getResources().getString(R.string.create));

                }
            }
        });

    }


    private void setAdapter(GetListCustomerResponse response) {
        customers = response.getResult();
        adapter = new CustomerAdapter(getContext(), customers, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvCustomer.setHasFixedSize(true);
        rvCustomer.setLayoutManager(layoutManager);
        rvCustomer.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onItemClick(int position, CustomerItem updateCustomer) {
        if (isCreateProjectCustomer){
            Intent intent = new Intent(getContext(), AddCustomerFragment.class);
            intent.putExtra("Customer", customers.get(position));
            if (getTargetFragment() != null) {
                getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
            }
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
            }
        }else {
            customerItem = updateCustomer;
            updateCustomer(updateCustomer);
        }

    }

    private void updateCustomer(CustomerItem updateCustomer) {
        edtAddCustomer.setBody(updateCustomer.getName());
        btnCreate.setText(getResources().getString(R.string.update_customer));
        isUpdate = true;

    }

    @Override
    public void onDeleteItem(int position, CustomerItem customer) {

        customerItem = customer;
        deleteItemRequest(customer);
    }

    private void deleteItemRequest(CustomerItem customer) {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(root, false);

        DeleteCustomerReq req = new DeleteCustomerReq();
        req.setCustomer_id(customer.getId());
        DeleteCustomerService.getInstance().deleteCustomer(getResources(), req, new ResponseListener<DeleteCustomerResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error, DELETE_ITEM);
            }

            @Override
            public void onSuccess(DeleteCustomerResponse response) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess() && !TextUtils.isEmpty(response.getMessage())) {
                    Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
//                    adapter.clearList();
                    getCustomerList();
                    adapter.notifyDataSetChanged();
                    btnCreate.setText(getResources().getString(R.string.create));
                    isUpdate = false;
                }
            }
        });


    }

    @OnClick(R.id.btnCreate)
    public void onViewClicked() {

        if (isUpdate) {
            if (edtAddCustomer.getError() != null) {
                Toast.makeText(getActivity(), getResources().getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
            } else {
                updateCustomerRequest();
            }
        } else {

            if (edtAddCustomer.getError() != null) {
                Toast.makeText(getActivity(), getResources().getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
            } else {
                createCustomerRequest();
            }
        }

    }

    private void updateCustomerRequest() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(roundedLoadingView, false);

        UpdateCustomerReq req = new UpdateCustomerReq();
        req.setCustomer_id(customerItem.getId());
        req.setCustomer_name(edtAddCustomer.getValueString());

        UpdateCustomerService.getInstance().updateCustomer(getResources(), req, new ResponseListener<UpdateCustomerResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error, UPDATE_CUSTOMER);
            }

            @Override
            public void onSuccess(UpdateCustomerResponse response) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isStatus() && !TextUtils.isEmpty(response.getMessage())) {
                    Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                    getCustomerList();
                    adapter.notifyDataSetChanged();
                    edtAddCustomer.setBody("");
                    btnCreate.setText(getResources().getString(R.string.create));
                    isUpdate = false;
                }
            }
        });
    }

    private void createCustomerRequest() {
        roundedLoadingView.setVisibility(View.VISIBLE);
        enableDisableViewGroup(roundedLoadingView, false);
        CreateCustomerReq req = new CreateCustomerReq();
        req.setCustomer_name(edtAddCustomer.getValueString());
        CreateCustomerService.getInstance().createCustomer(getResources(), req, new ResponseListener<CreateCustomerResponse>() {
            @Override
            public void onGetErrore(String error) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                showErrorDialog(error, CREATE_CUSTOMER);
            }

            @Override
            public void onSuccess(CreateCustomerResponse response) {
                roundedLoadingView.setVisibility(View.GONE);
                enableDisableViewGroup(root, true);
                if (response.isSuccess() && response.getMessage() != null) {
                    Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
//                    adapter.clearList();
                    getCustomerList();
                    adapter.notifyDataSetChanged();
                    edtAddCustomer.setBody("");
                    isUpdate = false;
                }
            }
        });
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

    public void showErrorDialog(String description, int type) {

        if (getContext() == null) {
            return;
        }
        CustomDialog customDialog = new CustomDialog(getContext());
        customDialog.setOkListener(getString(R.string.retry_text), view -> {
            customDialog.dismiss();
            roundedLoadingView.setVisibility(View.VISIBLE);
            if (type == GET_LIST) {
                getCustomerList();
            } else if (type == DELETE_ITEM) {
                deleteItemRequest(customerItem);
            } else if (type == CREATE_CUSTOMER) {
                createCustomerRequest();
            }else if (type == UPDATE_CUSTOMER){
                updateCustomerRequest();
            }

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
