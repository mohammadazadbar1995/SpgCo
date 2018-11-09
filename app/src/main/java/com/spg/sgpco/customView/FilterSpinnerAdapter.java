package com.spg.sgpco.customView;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.spg.sgpco.R;
import com.spg.sgpco.model.Range;

import java.util.ArrayList;

/**
 * Created by a.Raghibdoust on 10/11/2017.
 */

public class FilterSpinnerAdapter extends ArrayAdapter<Range> {

    private Context context;
    ArrayList<Range> list;
    int selectedIndex;
    public FilterSpinnerAdapter(Context ctx, ArrayList<Range> itemList) {

        super(ctx, 0, itemList);
        this.context=ctx;
        this.list =itemList;

    }
    public int getSelectedIndex() {
        return selectedIndex;

    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
        notifyDataSetChanged();

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
        }
        TextView txt= (TextView) convertView.findViewById(R.id.spinnertitle);
        txt.setText(list.get(position).getText());
        txt.setGravity(Gravity.CENTER);
        int padding = (int) getContext().getResources().getDimension(R.dimen.input_field_padding);
        txt.setPadding(padding, padding, padding, padding);


        txt.setTextColor(Color.parseColor("#1171d0"));
        txt.setSingleLine(true);
        txt.setEllipsize(TextUtils.TruncateAt.END);
        txt.setSingleLine(true);

        return convertView;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String text = list.get(position).getText();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
        }
        TextView txt= (TextView) convertView.findViewById(R.id.spinnertitle);
        txt.setText(text);



        txt.setPadding(0, 30, 0, 30);
        txt.setGravity(Gravity.CENTER);
        txt.setTextColor(Color.parseColor("#1171d0"));
        txt.setBackgroundColor(Color.parseColor("#FFFFFF"));
        return convertView;

    }


}
