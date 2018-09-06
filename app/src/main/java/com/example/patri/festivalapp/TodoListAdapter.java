package com.example.patri.festivalapp;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class TodoListAdapter extends BaseAdapter {
    private final ArrayList<PackingListItemDB> packingList;
    private final LayoutInflater inflator;
    private PackingListItemDB packingItem;


    public TodoListAdapter(ArrayList<PackingListItemDB> packingList, Context context) {
        this.packingList = packingList;
        inflator = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return packingList.size();
    }

    @Override
    public Object getItem(int position) {
        return packingList.get(position);
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
        packingItem = (PackingListItemDB) getItem(position);
        Log.e("Item", packingItem.getName());
        holder.done_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                packingItem.setChecked(isChecked);

                Log.e("Position", String.valueOf(position));
                Log.e("Change", packingItem.getName() +" "+ packingItem.isChecked());

                if (isChecked) {
                    holder.task_view.setPaintFlags(holder.task_view.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.task_view.setPaintFlags(0);
                }
            }
        });

        holder.task_view.setText(packingItem.getName());
        holder.done_box.setChecked(packingItem.isChecked());

        return convertView;
    }

    static class ViewHolder {
        TextView task_view;
        CheckBox done_box;
    }
}
