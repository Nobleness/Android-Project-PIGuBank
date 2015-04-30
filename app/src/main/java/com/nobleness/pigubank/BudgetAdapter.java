package com.nobleness.pigubank;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class BudgetAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final ArrayList<Integer> arrayListBgtId;
	private final ArrayList<String> arrayListBgtName;
	
	public BudgetAdapter(
			Context context, 
			ArrayList<Integer> arrayListBgtId, 
			ArrayList<String> arrayListBgtName) {
		super(context, R.layout.budgets_buttons, arrayListBgtName);
		this.context = context;
		this.arrayListBgtId = arrayListBgtId;
		this.arrayListBgtName = arrayListBgtName;
	}
	

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) convertView = 
				inflater.inflate(R.layout.budgets_buttons, parent, false);
		Button bN = (Button) convertView.findViewById(R.id.BudgetBnEdit);
		bN.setText(arrayListBgtName.get(position));
		bN.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {
				
				Activity piguAct = (PIGuActivity) context;
				SharedPreferences appPref;
				appPref = (piguAct).getSharedPreferences(
						PIGuActivity.APP_PREFERENCES, 
						Context.MODE_PRIVATE);
				BudgetDatabaseHelper bgtDb = new BudgetDatabaseHelper(context);
				Budget editBgt = bgtDb.getBudget(arrayListBgtId.get(position));
				Editor editBgtPref = appPref.edit();
				editBgtPref.putString(
						PIGuActivity.APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_ID, 
						Integer.toString(arrayListBgtId.get(position)));
				editBgtPref.putString(
						PIGuActivity.APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_NAME, 
						editBgt.getBudgetName());
				editBgtPref.putString(
						PIGuActivity.APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_LIMIT, 
						editBgt.getBudgetLimit());
				editBgtPref.putString(
						PIGuActivity.APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_AMOUNT_SPENT, 
						editBgt.getBudgetAmountSpent());
				editBgtPref.putString(
						PIGuActivity.APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ, 
						editBgt.getBudgetFreq());
				editBgtPref.putString(
						PIGuActivity.APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_TYPE, 
						editBgt.getBudgetFreqType());
				editBgtPref.putString(
						PIGuActivity.APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_WEEK_DAY, 
						editBgt.getBudgetFreqWeekDay());
                editBgtPref.putString(
                        PIGuActivity.
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BUTTON_BOOLEAN,
                        editBgt.getBudgetFreqMonthDateButtonBole());
                editBgtPref.putString(
                        PIGuActivity.
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DAY_BUTTON_BOOLEAN,
                        editBgt.getBudgetFreqMonthDayButtonBole());
				editBgtPref.putString(
						PIGuActivity.APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE, 
						editBgt.getBudgetFreqMonthDate());
				editBgtPref.putString(
						PIGuActivity.
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BEFORE_AFTER,
						editBgt.getBudgetFreqMonthDateBeforeAfter());
				editBgtPref.putString(
						PIGuActivity.APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_WEEK, 
						editBgt.getBudgetFreqMonthWeek());
				editBgtPref.putString(
						PIGuActivity.APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_WEEK_DAY, 
						editBgt.getBudgetFreqMonthWeekDay());
				editBgtPref.putString(
						PIGuActivity.APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_YEAR_MONTH_DATE, 
						editBgt.getBudgetFreqYearMonthDate());
				editBgtPref.putString(
						PIGuActivity.APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_YEAR_MONTH, 
						editBgt.getBudgetFreqYearMonth());
				editBgtPref.commit();
				bgtDb.close();
					
				Intent intent = new Intent(context, BudgetDialogActivity.class);
				context.startActivity(intent);
			}
		});

		return convertView;
	}
}
