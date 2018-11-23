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


    private Context context;

    TermostaticItemAdapter(Context context, ArrayList<ThermostaticSystemItem> list) {
        this.list = list;
        this.context = context;
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
        holder.tvTitle.setText(object.getFloor_type_title() + "_" + object.getType_of_space_title() + "_" + object.getMetr()
                + "_" + object.getCold_area());

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


    }

}
