package com.example.sohyeon.dowazo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.telephony.SmsManager;


import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.sohyeon.dowazo.ButtonMainActivity.ambulance;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link dowazowidgetConfigureActivity dowazowidgetConfigureActivity}
 */
public class dowazowidget extends AppWidgetProvider {

    private static final String TAG = "dowazowidget";
    public static String PENDING_ACTION = "com.example.testsetonclickpendingintent.Pending_Action";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
    }


    @Override
    public void onEnabled(Context context) {
        Log.i(TAG, "=============onEnabled()==========");
        super.onEnabled(context);
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context,appWidgetManager,appWidgetIds);
        final int N = appWidgetIds.length;


        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            Intent intent = new Intent(context, ButtonMainActivity.class);
            intent.putExtra("widget",119);
            PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.dowazowidget);
            views.setOnClickPendingIntent(R.id.button_widget, pi);
            appWidgetManager.updateAppWidget(appWidgetId, views);
            Log.d("Widget","실행");
        }
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            dowazowidgetConfigureActivity.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}
