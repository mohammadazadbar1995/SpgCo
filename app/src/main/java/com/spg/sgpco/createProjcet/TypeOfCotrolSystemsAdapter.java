package com.spg.sgpco.createProjcet;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.service.ResponseModel.SystemsItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by m.azadbar on 9/21/2017.
 */

public class TypeOfCotrolSystemsAdapter extends RecyclerView.Adapter<TypeOfCotrolSystemsAdapter.ViewHolder> {


    private OnItemClickListener listener;
    private ArrayList<SystemsItem> list;


    TypeOfCotrolSystemsAdapter(ArrayList<SystemsItem> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data, parent, false);
        return new ViewHolder(itemView);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SystemsItem object = list.get(position);
        holder.tvTitle.setText(object.getTitle());
        holder.bind(position, object, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<SystemsItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        BaseTextView tvTitle;
        @BindView(R.id.rlItem)
        BaseRelativeLayout rlItem;
        @BindView(R.id.row)
        BaseRelativeLayout row;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position, SystemsItem systemsItem, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(position, systemsItem));
        }

    }


    public interface OnItemClickListener {
        void onItemClick(int position, SystemsItem systemsItem);

    }
}
