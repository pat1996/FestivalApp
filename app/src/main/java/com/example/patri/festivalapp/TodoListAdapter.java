package com.example.patri.festivalapp;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoListAdapter extends BaseAdapter {
    private final LayoutInflater inflator;
    PackingListItemDB packing_list;


    public TodoListAdapter(PackingListItemDB packingListItemDB, Context context) {
        this.packing_list = packingListItemDB;


        inflator = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

       return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TodoListAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = inflator.inflate(R.layout.list_layout, parent, false);
            holder = new TodoListAdapter.ViewHolder();
            holder.task_view = (TextView) convertView.findViewById(R.id.list_task_view);
            holder.done_box = (CheckBox) convertView.findViewById(R.id.list_checkbox);
            convertView.setTag(holder);

        } else {
            holder = (TodoListAdapter.ViewHolder) convertView.getTag();
        }
        packing_list = (PackingListItemDB) getItem(position);
        holder.done_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                packing_list.setChecked(isChecked);

                if (isChecked) {
                    holder.task_view.setPaintFlags(holder.task_view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }else{
                    holder.task_view.setPaintFlags(0);
                }
            }
        });

        holder.task_view.setText(packing_list.getName());
        holder.done_box.setChecked(packing_list.isChecked());

        return convertView;
    }
    static class ViewHolder {
        TextView task_view;
        CheckBox done_box;
    }
}
