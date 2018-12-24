package com.spg.sgpco.downloadList;

import android.annotation.SuppressLint;
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
import com.spg.sgpco.service.ResponseModel.DownloadListItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by m.azadbar on 9/21/2017.
 */

public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.ViewHolder> {


    private final OnItemClickListener listener;
    private final ArrayList<DownloadListItem> list;


    DownloadListAdapter(ArrayList<DownloadListItem> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download_list, parent, false);
        return new ViewHolder(itemView);

    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Resources res = holder.itemView.getResources();
        DownloadListItem object = list.get(position);
        holder.tvTitle.setText(object.getTitle());
        holder.imgDelete.setColorFilter(res.getColor(R.color.black));
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

        public void bind(int position, DownloadListItem downloadListItem, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(position, downloadListItem));
        }

    }

    void clearList() {
        if (list != null) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, DownloadListItem downloadListItem);

    }
}
