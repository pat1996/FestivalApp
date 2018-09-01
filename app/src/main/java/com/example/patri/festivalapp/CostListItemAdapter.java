package com.example.patri.festivalapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CostListItemAdapter extends ArrayAdapter<Cost> {

    private ArrayList<Cost> costs;
    private LayoutInflater myInflater;
    private int myViewResourceId;


    public CostListItemAdapter(@NonNull Context context, int resourceId, @NonNull ArrayList<Cost> objects) {
        super(context, resourceId, objects);
        this.costs = objects;
        myInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        myViewResourceId = resourceId;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView = myInflater.inflate(myViewResourceId, null);


        if(convertView==null) {
            LayoutInflater inflater =(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.cost_list_single_entry,null);
        }

        Cost cost = costs.get(position);
        if(cost!=null ) {
            TextView tvName = (TextView) convertView.findViewById(R.id.name);
            TextView tvPrice = (TextView) convertView.findViewById(R.id.price);

            tvName.setText(cost.getName());
            tvPrice.setText(""+cost.getPrice()+"€");

        }
        return convertView;
    }
}
