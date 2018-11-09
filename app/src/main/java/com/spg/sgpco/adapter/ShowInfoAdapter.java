package com.spg.sgpco.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by m.azadbar on 9/21/2017.
 */

public class ShowInfoAdapter extends RecyclerView.Adapter<ShowInfoAdapter.ViewHolder> {


    private ArrayList<String> list;


    public ShowInfoAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_info, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String object = list.get(position);
        if (object != null) {
            holder.tvTitle.setText(object);
        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        BaseTextView tvTitle;
        @BindView(R.id.root)
        BaseRelativeLayout root;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(int position, final OnItemClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClickMax(position));
        }

    }

    public interface OnItemClickListener {
        void onItemClickMax(int position);
    }

}
