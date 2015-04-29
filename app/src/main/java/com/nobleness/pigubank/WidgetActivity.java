package com.nobleness.pigubank;

import java.util.ArrayList;
import java.util.List;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetActivity extends AppWidgetProvider {
	AppWidgetManager appWidgetManager;
	
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
		Intent serviceIntent = new Intent(context, WidgetUpdateService.class);
		context.startService(serviceIntent);
		}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Intent serviceIntent = new Intent(context, WidgetUpdateService.class);
		context.stopService(serviceIntent);
		super.onDeleted(context, appWidgetIds);
	}
	
	public static class WidgetUpdateService extends Service {
		WidgetUpdateTask updater;
		private static final String APP_LOG = "WidgetUpdateService";
		
		@Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			updater = new WidgetUpdateTask();
			updater.execute(startId);
			return START_REDELIVER_INTENT;
		}
				
		@Override
		public void onDestroy() {
			updater.cancel(true);
			super.onDestroy();
		}
		
		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}
		
		private class WidgetUpdateTask extends AsyncTask<Integer, Void, Boolean> {

			@Override
			protected Boolean doInBackground(Integer... startIds) {
				return widgetUpdate(startIds[0]);
			}
		}
		
		private boolean widgetUpdate(int startId) {
			boolean succeeded = false;
			Context context = WidgetUpdateService.this;
			String packageName = context.getPackageName();
			Log.d(APP_LOG, "package name: " + packageName);
					
			RemoteViews widgetViews = new RemoteViews(
					context.getPackageName(), R.layout.widget);
			
			//load currency symbol to widget
			widgetViews.setTextViewText(R.id.widgetTVsymbol, PIGuActivity.curSymbol);
			
			//load current balance to widget
			AccountDatabaseHelper accDb = new AccountDatabaseHelper(this);
            ArrayList<Integer> arrayListAccId;
            arrayListAccId = new ArrayList<Integer>();
			List<Account> accList = accDb.getAllAccounts();
			String balance = "0.00";
			for (Account acc : accList) {
					if (acc.getAccountMain().contains("true")) {
						balance = acc.getAccountBalance();
					} else {
						// do nothing
					}
                int accID = acc.getID();
                arrayListAccId.add(accID);
				}
            accDb.close();

			widgetViews.setTextViewText(R.id.widgetTVbalance, balance);

            //Click handling
            if (arrayListAccId.size() > 0) {
                Intent launchAppIntent = new Intent(context, DialogActivity.class);
                PendingIntent launchAppPendingIntent = PendingIntent.getActivity(
                context, 0, launchAppIntent, 0);
                widgetViews.setOnClickPendingIntent(R.id.widget_view, launchAppPendingIntent);
            } else {
                // do nothing
                Intent launchAppIntent = new Intent(context, HomeActivity.class);
                PendingIntent launchAppPendingIntent = PendingIntent.getActivity(
                context, 0, launchAppIntent, 0);
                widgetViews.setOnClickPendingIntent(R.id.widget_view, launchAppPendingIntent);
            }

            try {
				//get the android component name for the widget provider
				ComponentName componentWidget = new ComponentName(context, WidgetActivity.class);
				
				//get instanceof the app widget provider
				AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
				
				//update the widget
				appWidgetManager.updateAppWidget(componentWidget, widgetViews);
				
				//update if succeeded
				succeeded = true;
				
				
			} catch (Exception e) {
				Log.e(APP_LOG, "Widget Failure", e);
			}
			
			if (!WidgetUpdateService.this.stopSelfResult(startId)) {
				Log.e(APP_LOG, "Failed to stop service");
			}
			
			return succeeded;
		}
	}
}
