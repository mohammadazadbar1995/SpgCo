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
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.service.ResponseModel.GalleryItem;
import com.spg.sgpco.service.ResponseModel.ShowContentItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by m.azadbar on 9/21/2017.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {


    private ArrayList<GalleryItem> list;
    private final OnItemClickListener listener;
    private Context context;

    GalleryAdapter(Context context, ArrayList<GalleryItem> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_content, parent, false);
        return new ViewHolder(itemView);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Resources res = holder.itemView.getContext().getResources();
        GalleryItem object = list.get(position);
        holder.tvTitle.setText(object.getTitle());
        Glide.with(context).load(object.getImage()).placeholder(R.drawable.placeholder).centerCrop().into(holder.imageItem);
        holder.bind(position, object, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<GalleryItem> searchResult) {
        this.list = searchResult;
        notifyDataSetChanged();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageItem)
        BaseImageView imageItem;
        @BindView(R.id.tvTitle)
        BaseTextView tvTitle;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position, GalleryItem galleryItem, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(position, galleryItem));
        }

    }

    void clearList() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, GalleryItem galleryItem);
    }
}
