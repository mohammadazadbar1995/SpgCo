package com.spg.sgpco.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseImageView;
import com.spg.sgpco.service.ResponseModel.GalleryItemList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by m.azadbar on 9/21/2017.
 */

public class GalleryItemAdapter extends RecyclerView.Adapter<GalleryItemAdapter.ViewHolder> {


    private ArrayList<GalleryItemList> list;
    private Context context;

    GalleryItemAdapter(Context context, ArrayList<GalleryItemList> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_galley_item, parent, false);
        return new ViewHolder(itemView);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        GalleryItemList object = list.get(position);
        Glide.with(context).load(object.getImage()).placeholder(R.drawable.placeholder).centerCrop().into(holder.imageItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<GalleryItemList> searchResult) {
        this.list = searchResult;
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageItem)
        BaseImageView imageItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }


}
