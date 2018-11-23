package com.spg.sgpco.createProjcet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.service.ResponseModel.CitiesListItem;
import com.spg.sgpco.service.ResponseModel.ListCitiesItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by m.azadbar on 9/21/2017.
 */

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {


    private final OnItemClickListener listener;
    private final ArrayList<CitiesListItem> list;

    private Context context;

    CityAdapter(Context context, ArrayList<CitiesListItem> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
        this.context = context;
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

        CitiesListItem object = list.get(position);
        holder.tvTitle.setText(object.getCity());
        holder.bind(position, object, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
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

        public void bind(int position, CitiesListItem city, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(position, city));
        }

    }


    public interface OnItemClickListener {
        void onItemClick(int position, CitiesListItem city);

    }
}
