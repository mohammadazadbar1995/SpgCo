package com.spg.sgpco.createProjcet;

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
import com.spg.sgpco.service.RequestModel.ThermostaticSystemItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by m.azadbar on 9/21/2017.
 */

public class TermostaticItemAdapter extends RecyclerView.Adapter<TermostaticItemAdapter.ViewHolder> {


    private final ArrayList<ThermostaticSystemItem> list;
    private final boolean isUpdate;


    private OnItemClickListener listener;


    private Context context;

    TermostaticItemAdapter(Context context, ArrayList<ThermostaticSystemItem> list, boolean isUpdate, OnItemClickListener listener) {
        this.list = list;
        this.context = context;
        this.isUpdate = isUpdate;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thermostatic, parent, false);
        return new ViewHolder(itemView);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Resources res = holder.itemView.getResources();
        ThermostaticSystemItem object = list.get(position);

        holder.typeSpaceId.setText(res.getString(R.string.type_space) + ": " +
                object.getType_of_space_title());

        holder.genderFloorId.setText(res.getString(R.string.gender_floor) + ": " +
                object.getFloor_type_title());

        holder.meter.setText(res.getString(R.string.meter) + ": " +
                object.getMetr());

        holder.meterCold.setText(res.getString(R.string.cold_area) + ": " +
                object.getCold_area());

        holder.bind(position, object, listener);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.typeSpaceId)
        BaseTextView typeSpaceId;
        @BindView(R.id.genderFloorId)
        BaseTextView genderFloorId;
        @BindView(R.id.meter)
        BaseTextView meter;
        @BindView(R.id.meterCold)
        BaseTextView meterCold;
        @BindView(R.id.imgDelete)
        BaseImageView imgDelete;
        @BindView(R.id.imgEdit)
        BaseImageView imgEdit;
        @BindView(R.id.rlItem)
        BaseRelativeLayout rlItem;
        @BindView(R.id.row)
        BaseRelativeLayout row;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position, ThermostaticSystemItem item, final OnItemClickListener listener) {
            imgDelete.setOnClickListener(v -> listener.onItemClick(position, item));
            row.setOnClickListener(v -> listener.onRowClick(position, item));
        }

    }

    public interface OnItemClickListener {
        void onItemClick(int position, ThermostaticSystemItem item);
        void onRowClick(int position, ThermostaticSystemItem item);
    }
}
