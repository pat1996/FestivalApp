package com.example.patri.festivalapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class CountdownWidgetActivity extends AppWidgetProvider {
    String daysInWidget;

    @Override
    public void onUpdate (Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    @Override
    public void onReceive (Context context, Intent intent){
        RemoteViews rViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        if (intent.hasExtra("countdownWidget")){
            daysInWidget = intent.getStringExtra("countdownWidget");
            rViews.setTextViewText(R.id.widget_text_view,daysInWidget);
            updateWidget(context,rViews);

        }
        super.onReceive(context,intent);
    }

    public void updateWidget(Context context, RemoteViews rViews){
        ComponentName widgetCo = new ComponentName(context, CountdownWidgetActivity.class);
        AppWidgetManager.getInstance(context).updateAppWidget(widgetCo,rViews);

    }
}