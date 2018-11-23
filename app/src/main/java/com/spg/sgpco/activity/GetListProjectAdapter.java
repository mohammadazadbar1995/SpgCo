package com.spg.sgpco.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
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
        holder.tvTitle.setText(object.getId() + " " + object.getName());
        holder.tvCustomer.setText(res.getString(R.string.customer) + ": " + object.getCustomer());
        holder.tvSystem.setText(res.getString(R.string.systems) + ": " + object.getSystems_type());
        holder.tvCity.setText(res.getString(R.string.city) + ": " + object.getCity());
        holder.tvDescription.setText(res.getString(R.string.description_text) + ": " + object.getDescription());
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
