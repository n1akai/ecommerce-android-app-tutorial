package com.n1akai.e_commercetutorial.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.n1akai.e_commercetutorial.R;
import com.n1akai.e_commercetutorial.models.Make;

import java.util.List;

public class MakeSpinnerAdapter extends BaseAdapter {

    Context context;
    List<Make> makeList;

    public MakeSpinnerAdapter(Context context, List<Make> makeList) {
        this.context = context;
        this.makeList = makeList;
    }

    @Override
    public int getCount() {
        return makeList.size();
    }

    @Override
    public Make getItem(int position) {
        return makeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }

        TextView text = (TextView) convertView;
        text.setText(getItem(position).getLabel());

        return convertView;
    }
}
