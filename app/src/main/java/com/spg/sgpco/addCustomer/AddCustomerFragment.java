package com.spg.sgpco.addCustomer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.baseView.BaseToolbar;
import com.spg.sgpco.customView.CustomEditText;
import com.spg.sgpco.database.AppDatabase;
import com.spg.sgpco.database.Customer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class AddCustomerFragment extends Fragment implements CustomerAdapter.OnItemClickListener {


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
    List<Customer> customers;
    private Customer customer = new Customer();
    private CustomerAdapter adapter;

    public AddCustomerFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_customer, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvCenterTitle.setText(getResources().getString(R.string.create_project));

        setAdapter();
        return view;

    }

    private void setAdapter() {

        AppDatabase database = AppDatabase.getInMemoryDatabase(getContext());
        customers = database.customerDao().customer();
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
    public void onItemClick(int position, Customer estate) {

    }

    @Override
    public void onDeleteItem(int position, Customer customer) {
        AppDatabase database = AppDatabase.getInMemoryDatabase(getContext());
        database.customerDao().delete(customer);
        Toast.makeText(getContext(), "مشتری مورد نظر حذف شد", Toast.LENGTH_SHORT).show();
        adapter.clearList();
        customers.addAll(database.customerDao().customer());
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.btnCreate)
    public void onViewClicked() {
        if (edtAddCustomer.getError() != null) {
            Toast.makeText(getActivity(), getResources().getString(R.string.please_enter_name), Toast.LENGTH_SHORT).show();
        } else {
            AppDatabase database = AppDatabase.getInMemoryDatabase(getContext());
            customer.setName(edtAddCustomer.getValueString());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            String date = simpleDateFormat.format(new Date());
            customer.setDate(date);
            database.customerDao().insertCustomer(customer);
            edtAddCustomer.setClearBody("");
            adapter.clearList();
            customers.addAll(database.customerDao().customer());
            adapter.notifyDataSetChanged();
        }
    }
}
