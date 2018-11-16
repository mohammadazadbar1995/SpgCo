package com.spg.sgpco.addCustomer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseImageView;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.database.Customer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by m.azadbar on 9/21/2017.
 */

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {


    private final OnItemClickListener listener;
    private final List<Customer> list;

    private Context context;

    CustomerAdapter(Context context, List<Customer> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);
        return new ViewHolder(itemView);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Resources res = holder.itemView.getContext().getResources();
        Customer object = list.get(position);
        holder.tvTitle.setText(object.getName());
        holder.bind(position, object, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgDelete)
        BaseImageView imgDelete;
        @BindView(R.id.tvTitle)
        BaseTextView tvTitle;
        @BindView(R.id.row)
        BaseRelativeLayout row;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position, Customer customer, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(position, customer));
            imgDelete.setOnClickListener(v -> listener.onDeleteItem(position, customer));
        }

    }

    void clearList() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Customer customer);

        void onDeleteItem(int position, Customer customer);
    }
}
