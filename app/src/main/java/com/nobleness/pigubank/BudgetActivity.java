package com.nobleness.pigubank;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class BudgetActivity extends PIGuActivity {

	ArrayList<Integer> arrayListBgtId;
	ArrayList<String> arrayListBgtName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(APP_LOG, "In Activity, Budgets");
		setContentView(R.layout.budgets);
		updateBudgetsList();
	}	
	
	@Override
	protected void onResume() {
		super.onResume();
		updateBudgetsList();
		Editor updateBolean = appPref.edit();
		updateBolean.putBoolean(APP_PREFERENCES_LAUNCHED_ADD_BUDGET_DIALOG, false);
		updateBolean.commit();
	}

    @Override
    protected void onPause() {
        super.onPause();
    }
	
	public void updateBudgetsList() {
		Log.i(APP_LOG, "running method updateBudgetsList()");
		// open database connection
		BudgetDatabaseHelper bgtDb = new BudgetDatabaseHelper(this);
		arrayListBgtId = new ArrayList<Integer>();
		arrayListBgtName = new ArrayList<String>();
		List<Budget> bgtList = bgtDb.getAllBudgets();
		for (Budget bgt : bgtList) {
			int bgtId = bgt.getID();
			arrayListBgtId.add(bgtId);
			String strBgtName = bgt.getBudgetName();
			arrayListBgtName.add(strBgtName);
			}
		bgtDb.close();
		
		// build budget buttons
		arrayListBgtId.toArray();
		arrayListBgtName.toArray();
		ListView bgtListView = (ListView) findViewById(R.id.BudgetsLVbudgetButtons);
		BudgetAdapter bgtListAdapter = new BudgetAdapter(
				this,
				arrayListBgtId,
				arrayListBgtName);
		bgtListView.setAdapter(bgtListAdapter);
	}
	
	public void onClickAddBudgetButton(View view) {
		Editor updateBolean = appPref.edit();
		updateBolean.putBoolean(APP_PREFERENCES_LAUNCHED_ADD_BUDGET_DIALOG, true);
		updateBolean.putString(APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BUTTON_BOOLEAN,
				"");
		updateBolean.putString(APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DAY_BUTTON_BOOLEAN,
				"");
		updateBolean.commit();
		startActivity(new Intent(BudgetActivity.this, BudgetDialogActivity.class));	
	}
}
