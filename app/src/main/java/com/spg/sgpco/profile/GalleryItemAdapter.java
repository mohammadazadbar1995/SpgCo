package com.spg.sgpco.profile;

import android.annotation.SuppressLint;
import android.content.Context;
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
    private final OnItemClickListener listener;

    GalleryItemAdapter(Context context, ArrayList<GalleryItemList> list, OnItemClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
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
        holder.bind(position, object, listener);
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


        public void bind(int position, GalleryItemList item, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(position, item));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, GalleryItemList item);
    }
}
