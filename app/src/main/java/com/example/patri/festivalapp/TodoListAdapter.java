package com.example.patri.festivalapp;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class TodoListAdapter extends BaseAdapter {
    public List<PacklistItem> packlistItems;
    private final LayoutInflater inflator;
    private PacklistItem task;

    public TodoListAdapter(List<PacklistItem> packlistItems, Context context) {
        this.packlistItems = packlistItems;
        inflator = LayoutInflater.from(context);
    }

    static class ViewHolder {
        TextView task_view;
        CheckBox done_box;
    }


    @Override
    public int getCount() {
        return packlistItems.size();
    }
    @Override
    public Object getItem(int position) {
        return packlistItems.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = inflator.inflate(R.layout.list_layout, parent, false);
            holder = new ViewHolder();
            holder.task_view = (TextView) convertView.findViewById(R.id.list_task_view);
            holder.done_box = (CheckBox) convertView.findViewById(R.id.list_checkbox);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        task = (PacklistItem) getItem(position);
        holder.done_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                packlistItems.get(position).setIsDone(isChecked);

                if (isChecked) {
                    holder.task_view.setPaintFlags(holder.task_view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.task_view.setPaintFlags(0);
                }
            }
        });

        holder.task_view.setText(task.getTaskContent());
        holder.done_box.setChecked(task.isDone());

        return convertView;
    }
}
