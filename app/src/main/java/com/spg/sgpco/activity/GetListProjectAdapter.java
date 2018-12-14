package com.spg.sgpco.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseLinearLayout;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.service.ResponseModel.ProjectListResultItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by m.azadbar on 9/21/2017.
 */

public class GetListProjectAdapter extends RecyclerView.Adapter<GetListProjectAdapter.ViewHolder> {


    private final OnItemClickListener listener;
    private final ArrayList<ProjectListResultItem> list;


    private Context context;

    GetListProjectAdapter(Context context, ArrayList<ProjectListResultItem> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project_list, parent, false);
        return new ViewHolder(itemView);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Resources res = holder.itemView.getResources();
        ProjectListResultItem object = list.get(position);
        if (!TextUtils.isEmpty(object.getName())) {
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.tvTitle.setText(res.getString(R.string.name_project) + " " + object.getName());
        } else {
            holder.tvTitle.setVisibility(View.GONE);

        }
        if (!TextUtils.isEmpty(object.getCustomer())) {
            holder.tvCustomer.setText(res.getString(R.string.customer) + ": " + object.getCustomer());
        } else {
            holder.tvCustomer.setText(res.getString(R.string.customer) + ": " + res.getString(R.string.unknown));
        }
        if (!TextUtils.isEmpty(object.getSystems_type())) {
            holder.tvSystem.setText(res.getString(R.string.systems) + ": " + object.getSystems_type());
        } else {
            holder.tvSystem.setText(res.getString(R.string.systems) + ": " + res.getString(R.string.unknown));
        }
        if (!TextUtils.isEmpty(object.getCity())) {
            holder.tvCity.setText(res.getString(R.string.city) + ": " + object.getCity());
        } else {
            holder.tvCity.setText(res.getString(R.string.city) + ": " + res.getString(R.string.unknown));
        }

        if (!TextUtils.isEmpty(object.getDescription())) {
            holder.tvDescription.setVisibility(View.VISIBLE);
            holder.tvDescription.setText(res.getString(R.string.description_text) + ": " + object.getDescription());
        } else {
            holder.tvDescription.setVisibility(View.GONE);
        }

        if (position % 2 == 0) {
            holder.back.setBackground(res.getDrawable(R.drawable.ripplewhite_no_radius));
        } else {
            holder.back.setBackground(res.getDrawable(R.drawable.rippleligh_gray_no_radius));
        }

        if (position == list.size() - 1) {
            RelativeLayout.LayoutParams par = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            par.setMargins(0, 0, 0, res.getDimensionPixelSize(R.dimen.size_width));
            holder.back.setLayoutParams(par);
        } else {
            RelativeLayout.LayoutParams par = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            par.setMargins(0, 0, 0, 0);
            holder.back.setLayoutParams(par);
        }
        holder.bind(position, object, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        BaseTextView tvTitle;
        @BindView(R.id.tvCustomer)
        BaseTextView tvCustomer;
        @BindView(R.id.tvSystem)
        BaseTextView tvSystem;
        @BindView(R.id.tvCity)
        BaseTextView tvCity;
        @BindView(R.id.tvDescription)
        BaseTextView tvDescription;
        @BindView(R.id.root)
        BaseRelativeLayout root;
        @BindView(R.id.back)
        BaseLinearLayout back;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position, ProjectListResultItem lists, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(position, lists));
        }

    }


    public interface OnItemClickListener {
        void onItemClick(int position, ProjectListResultItem lists);

    }
}
