package com.nobleness.pigubank;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends PIGuActivity {

	SharedPreferences appPref;
    //fragment page
    FragmentPagerAdapter adapterViewPager;
    ViewPager vpPager;

  	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Log.i(PIGuActivity.APP_LOG, "In Activity, Overview");
        setContentView(R.layout.home);
		appPref = getSharedPreferences(PIGuActivity.APP_PREFERENCES, Context.MODE_PRIVATE);

        //Load fragments
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager = (ViewPager) findViewById(R.id.vpPager);
        vpPager.setAdapter(adapterViewPager);
	}

  	@Override
  	protected void onResume(){
  		super.onResume();
        vpPager.getAdapter().notifyDataSetChanged();
  	}

  	@Override
  	protected void onPause() {
  		super.onPause();
  		Intent widgetIntent = new Intent(this, WidgetActivity.class);
  		widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
  		int[] ids = {R.xml.widget_info};
  		widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
  		sendBroadcast(widgetIntent);

  	}

  	@Override
  	protected void onStop() {
  		super.onStop();
  		updateLaunchDialogInOVbole(false);
  	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            MenuInflater menuInflate = getMenuInflater();
            menuInflate.inflate(R.menu.menu, menu);
        } catch (Exception e) {
            Log.e(PIGuActivity.APP_LOG, "Failed to load Menu");
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.budgets_menu_item:
                try {
                    startActivity(new Intent(this, BudgetActivity.class));
                } catch (Exception e) {
                    Log.e(PIGuActivity.APP_LOG, "Error starting Budget Activity");
                }
                return true;
            case R.id.accounts_menu_item:
                try {
                    startActivity(new Intent(this, AccountsActivity.class));
                } catch (Exception e) {
                    Log.e(PIGuActivity.APP_LOG, "Error starting Accounts Activity");
                }
                return true;
            case R.id.histoy_menu_item:
                try {
                    startActivity(new Intent(this, HistoryActivity.class));
                } catch (Exception e) {
                    Log.e(PIGuActivity.APP_LOG, "Error starting History Activity");
                }
                return true;
            case R.id.about_menu_item:
                try {
                    startActivity(new Intent(this, AboutActivity.class));
                } catch (Exception e) {
                    Log.e(PIGuActivity.APP_LOG, "Error starting About Activity");
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

	private void updateLaunchDialogInOVbole(boolean strBole) {
		Editor boleEdit = appPref.edit();
		boleEdit.putBoolean(PIGuActivity.APP_PREFERENCES_LAUNCHED_DIALOG_FROM_OVERVIEW, strBole);
		boleEdit.commit();
	}

	public void onAddSubtractButtonClick (View view) {
        ArrayList<Integer> arrayListAccId;
        arrayListAccId = new ArrayList<Integer>();
        AccountDatabaseHelper accDb = new AccountDatabaseHelper(this);
        List<Account> accList = accDb.getAllAccounts();
        for (Account acc : accList) {
            //if accounts is 0 do nothing
            int accID = acc.getID();
            arrayListAccId.add(accID);
        }
        accDb.close();
        if (arrayListAccId.size() > 0) {
            updateLaunchDialogInOVbole(true);
            startActivity(new Intent(HomeActivity.this, DialogActivity.class));
        } else {
            Toast toast = Toast.makeText(this, R.string.toast_no_accounts, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
	}

    // Process swipe view fragment

    public static class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        // returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0: //Fragment #0 - this will show the Budget fragment
                    return BudgetFragment.newInstance();
                case 1: //Fragment #1 - this will show the Account fragment
                    return AccountFragment.newInstance();
                default:
                    return null;
            }
        }

        //Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Budgets";
                case 1:
                    return "Accounts";
                default:
                    return null;
            }
        }

    }
}
