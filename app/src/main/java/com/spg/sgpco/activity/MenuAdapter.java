package com.spg.sgpco.activity;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.spg.sgpco.R;
import com.spg.sgpco.model.MyMenuItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by m.azadbar on 9/21/2017.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {


    private Activity context;
    private ArrayList<MyMenuItem> list;
    private final OnItemClickListener listener;

    public MenuAdapter(Activity context, ArrayList<MyMenuItem> list, OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_main_card, parent, false);


        return new ViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MyMenuItem obj = list.get(position);

        holder.txtTitle.setText(obj.getTitle());
        holder.imageView.setImageResource(obj.getDrawableId());
        holder.rlLayout.setCardBackgroundColor(obj.getBackColor());
        holder.bind(list.get(position), listener);

        setFadeAnimation(holder.rlLayout);
        setFadeAnimation(holder.txtTitle);
    }


    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        view.startAnimation(anim);
    }


    @Override
    public int getItemCount() {

        return list.size();
    }


    public interface OnItemClickListener {
        void onItemClick(MyMenuItem item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.imgMenu)
        ImageView imageView;
        @BindView(R.id.rlLayout)
        CardView rlLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(final MyMenuItem item, final OnItemClickListener listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
