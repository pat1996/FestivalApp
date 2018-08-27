package com.example.patri.festivalapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class CountdownWidgetActivity extends AppWidgetProvider {

    @Override
    public void onUpdate (Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    @Override
    public void onReceive (Context context, Intent intent){
        super.onReceive(context,intent);
    }
}
