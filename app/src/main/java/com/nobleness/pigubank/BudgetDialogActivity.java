package com.nobleness.pigubank;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class BudgetDialogActivity extends PIGuActivity {
SharedPreferences appPref;
	
	static final int BGT_DIALOG_ID = 0;
	boolean prefBole;
	TextWatcher bgtDialogETwatch;
	
	AlertDialog bgtDialog = null;
	
	static EditText eTbgtName =  null;
	static TextView tVcurSymbol = null;
	static EditText eTlimit = null;
	static TextView tVcurSymbolSpent = null;
	static EditText eTspent = null;
	static EditText eTfreq = null;
	static Spinner sPfreqType = null;
	
	// week day
	static TextView tVfreqOn = null;
	static Spinner sPfreqWeekDay = null;
	
	// buttons to switch betweem date of month or weekday
	//static TextView tVfreqOrChange = null;
	static Button bNfreqDay = null;
	static Button bNfreqDate = null;
	
	// date of month
	static TextView tVfreqDateOfMonth = null;
	static Spinner sPfreqMonthDate = null;
	static Spinner sPfreqMonthDateBeforeAfter = null;
	
	// day of month
	static Spinner sPfreqMonthWeek = null;
	static Spinner sPfreqMonthWeekDay = null;
	
	// month of year
	static TextView tVfreqYearMonthDate = null;
	static Spinner sPfreqYearMonthDate = null;
	static TextView tVfreqYearMonth = null;
	static Spinner sPfreqYearMonth = null;
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(APP_LOG, "BudgetDialogActivity created");
		appPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
		prefBole = appPref.getBoolean(APP_PREFERENCES_LAUNCHED_ADD_BUDGET_DIALOG, false);
		showDialog(BGT_DIALOG_ID);
		disableDialogButtons();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		Log.i(APP_LOG, "BudgetDialogActivity closed due to pause");
		appPref.edit().clear();
		appPref.edit().commit();
        BudgetDialogActivity.this.finish();
	}
	
	public void enableDialogButtons()  {
		bgtDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(true);
		bgtDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
	}
	
	public void disableDialogButtons() {
		bgtDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);
		bgtDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
	}
	
	protected void enableDateButton() {
		tVfreqDateOfMonth.setVisibility(View.GONE);
		sPfreqMonthDate.setVisibility(View.GONE);
		sPfreqMonthDate.setSelection(0);
		sPfreqMonthDateBeforeAfter.setVisibility(View.GONE);
		sPfreqMonthDateBeforeAfter.setSelection(0);
		sPfreqMonthWeek.setVisibility(View.VISIBLE);
		sPfreqMonthWeekDay.setVisibility(View.VISIBLE);
		bNfreqDate.setEnabled(true);
		bNfreqDay.setEnabled(false);
        Editor editor = appPref.edit();
        editor.putString(APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BUTTON_BOOLEAN,
                "true");
        editor.putString(APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DAY_BUTTON_BOOLEAN,
                "");
        editor.commit();
	}
	
	protected void enableDayButton() {
		tVfreqDateOfMonth.setVisibility(View.VISIBLE);
		sPfreqMonthDate.setVisibility(View.VISIBLE);
		sPfreqMonthDateBeforeAfter.setVisibility(View.VISIBLE);
		sPfreqMonthWeek.setVisibility(View.GONE);
		sPfreqMonthWeek.setSelection(0);
		sPfreqMonthWeekDay.setVisibility(View.GONE);
		sPfreqMonthWeekDay.setSelection(0);
		bNfreqDate.setEnabled(false);
		bNfreqDay.setEnabled(true);
        Editor editor = appPref.edit();
        editor.putString(APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BUTTON_BOOLEAN,
                "");
        editor.putString(APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DAY_BUTTON_BOOLEAN,
                "true");
        editor.commit();
	}
	
	// visiblity methods
	protected void mkVisibleWeekDay() {
		tVfreqOn.setVisibility(View.VISIBLE);
		sPfreqWeekDay.setVisibility(View.VISIBLE);
		
		//tVfreqOrChange.setVisibility(View.GONE);
		bNfreqDay.setVisibility(View.GONE);
		bNfreqDate.setVisibility(View.GONE);
		tVfreqDateOfMonth.setVisibility(View.GONE);
		sPfreqMonthDate.setVisibility(View.GONE);
		sPfreqMonthDate.setSelection(0);
		sPfreqMonthDateBeforeAfter.setVisibility(View.GONE);
		sPfreqMonthDateBeforeAfter.setSelection(0);
		sPfreqMonthWeek.setVisibility(View.GONE);
		sPfreqMonthWeek.setSelection(0);
		sPfreqMonthWeekDay.setVisibility(View.GONE);
		sPfreqMonthWeekDay.setSelection(0);
		tVfreqYearMonthDate.setVisibility(View.GONE);
		sPfreqYearMonthDate.setVisibility(View.GONE);
		sPfreqYearMonthDate.setSelection(0);
		tVfreqYearMonth.setVisibility(View.GONE);
		sPfreqYearMonth.setVisibility(View.GONE);
		sPfreqYearMonth.setSelection(0);
	}
	
	protected void mkVisibleMonth() {
		//tVfreqOrChange.setVisibility(View.VISIBLE);
		bNfreqDay.setVisibility(View.VISIBLE);
		bNfreqDate.setVisibility(View.VISIBLE);
		tVfreqOn.setVisibility(View.GONE);
		sPfreqWeekDay.setVisibility(View.GONE);
		sPfreqWeekDay.setSelection(0);
		if (appPref.getString(
                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BUTTON_BOOLEAN, "")
                == "true"){
            enableDateButton();
		} else if (appPref.getString(
                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DAY_BUTTON_BOOLEAN, "")
                == "true"){
			enableDayButton();
		} else {
            bNfreqDate.setEnabled(true);
            bNfreqDay.setEnabled(true);
        }
		tVfreqYearMonthDate.setVisibility(View.GONE);
		sPfreqYearMonthDate.setVisibility(View.GONE);
		sPfreqYearMonthDate.setSelection(0);
		tVfreqYearMonth.setVisibility(View.GONE);
		sPfreqYearMonth.setVisibility(View.GONE);
		sPfreqYearMonth.setSelection(0);
	}
	
	protected void mkVisibleYearDate() {
		tVfreqOn.setVisibility(View.GONE);
		sPfreqWeekDay.setVisibility(View.GONE);
		sPfreqWeekDay.setSelection(0);
		//tVfreqOrChange.setVisibility(View.GONE);
		bNfreqDay.setVisibility(View.GONE);
		bNfreqDate.setVisibility(View.GONE);
		tVfreqDateOfMonth.setVisibility(View.GONE);
		sPfreqMonthDate.setVisibility(View.GONE);
		sPfreqMonthDate.setSelection(0);
		sPfreqMonthDateBeforeAfter.setVisibility(View.GONE);
		sPfreqMonthDateBeforeAfter.setSelection(0);
		sPfreqMonthWeek.setVisibility(View.GONE);
		sPfreqMonthWeek.setSelection(0);
		sPfreqMonthWeekDay.setVisibility(View.GONE);
		sPfreqMonthWeekDay.setSelection(0);
		
		tVfreqYearMonthDate.setVisibility(View.VISIBLE);
		sPfreqYearMonthDate.setVisibility(View.VISIBLE);
		tVfreqYearMonth.setVisibility(View.VISIBLE);
		sPfreqYearMonth.setVisibility(View.VISIBLE);
	}
	
	protected void mkVisibleAllGone() {
		tVfreqOn.setVisibility(View.GONE);
		sPfreqWeekDay.setVisibility(View.GONE);
		sPfreqWeekDay.setSelection(0);
		//tVfreqOrChange.setVisibility(View.GONE);
		bNfreqDay.setVisibility(View.GONE);
		bNfreqDate.setVisibility(View.GONE);
		tVfreqDateOfMonth.setVisibility(View.GONE);
		sPfreqMonthDate.setVisibility(View.GONE);
		sPfreqMonthDate.setSelection(0);
		sPfreqMonthDateBeforeAfter.setVisibility(View.GONE);
		sPfreqMonthDateBeforeAfter.setSelection(0);
		sPfreqMonthWeek.setVisibility(View.GONE);
		sPfreqMonthWeek.setSelection(0);
		sPfreqMonthWeekDay.setVisibility(View.GONE);
		sPfreqMonthWeekDay.setSelection(0);
		tVfreqYearMonthDate.setVisibility(View.GONE);
		sPfreqYearMonthDate.setVisibility(View.GONE);
		sPfreqYearMonthDate.setSelection(0);
		tVfreqYearMonth.setVisibility(View.GONE);
		sPfreqYearMonth.setVisibility(View.GONE);
		sPfreqYearMonth.setSelection(0);
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		
		switch (id) {
		case BGT_DIALOG_ID:
			final AlertDialog bgtDialog = (AlertDialog) dialog;
			
			eTbgtName = (EditText) bgtDialog.findViewById(R.id.bgtDialogETbgtName);
			tVcurSymbol = (TextView) bgtDialog.findViewById(R.id.bgtDialogTVcurSymbol);
			eTlimit = (EditText) bgtDialog.findViewById(R.id.bgtDialogETlimit);
			tVcurSymbolSpent = (TextView) bgtDialog.findViewById(R.id.bgtDialogTVcurSymbolSpent);
			eTspent = (EditText) bgtDialog.findViewById(R.id.bgtDialogETamountSpent);
			eTfreq = (EditText) bgtDialog.findViewById(R.id.bgtDialogETfreq);
			sPfreqType = (Spinner) bgtDialog.findViewById(R.id.bgtDialogSPfreqType);
			
			tVfreqOn = (TextView) bgtDialog.findViewById(R.id.bgtDialogTVon);
			sPfreqWeekDay = (Spinner) bgtDialog.findViewById(R.id.bgtDialogSPweekDay);
			//tVfreqOrChange = (TextView) bgtDialog.findViewById(R.id.bgtDialogTVorChange);
			bNfreqDay = (Button) bgtDialog.findViewById(R.id.bgtDialogBNday);
			bNfreqDate = (Button) bgtDialog.findViewById(R.id.bgtDialogBNdate);
			tVfreqDateOfMonth = (TextView) bgtDialog.findViewById(R.id.bgtDialogTVdateOfMonth);
			sPfreqMonthDate = (Spinner) bgtDialog.findViewById(R.id.bgtDialogSPdateOfMonth);
			sPfreqMonthDateBeforeAfter = (Spinner) bgtDialog.findViewById(
                    R.id.bgtDialogSPmonthDateBeforeAfter);
			sPfreqMonthWeek = (Spinner) bgtDialog.findViewById(R.id.bgtDialogSPmonthWeek);
			sPfreqMonthWeekDay = (Spinner) bgtDialog.findViewById(R.id.bgtDialogSPmonthWeekDay);
			tVfreqYearMonthDate = (TextView) bgtDialog.findViewById(R.id.bgtDialogTVyearMonthDate);
			sPfreqYearMonthDate = (Spinner) bgtDialog.findViewById(R.id.bgtDialogSPyearMonthDate);
			tVfreqYearMonth = (TextView) bgtDialog.findViewById(R.id.bgtDialogTVonYearMonth);
			sPfreqYearMonth = (Spinner) bgtDialog.findViewById(R.id.bgtDialogSPonYearMonth);
			
			// set frequency type spinner
			final Spinner fTypeSpin = sPfreqType;
			ArrayAdapter<?> fTypeAdapter = ArrayAdapter.createFromResource(this,
					R.array.freqType, android.R.layout.simple_spinner_item);
			fTypeAdapter.setDropDownViewResource(
					android.R.layout.simple_spinner_dropdown_item);	
			
			// listen to freq type spinner selections
			fTypeSpin.setOnItemSelectedListener(
					new AdapterView.OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?>
						parent, View itemSelected,
							int selectedItemPosition, long selectedId)
							{	
								switch(selectedItemPosition) {
									case 0:
										// prompt to select freq (again) and hide options
										mkVisibleAllGone();
										disableDialogButtons();
										break;									
									case 1:
										// week
										mkVisibleWeekDay();
										enableDialogButtons();
										break;
									case 2:
										// month
										mkVisibleMonth();
										enableDialogButtons();
										break;
									case 3:
										// year
										mkVisibleYearDate();
										enableDialogButtons();
										break;
								}
								Editor editor = appPref.edit();
								editor.putString(APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_TYPE, 
										Integer.toString(selectedItemPosition));
								editor.commit();	
							}
						public void onNothingSelected(AdapterView<?> parent) {
						}
					});
			
			// set frequency day of week spinner
			final Spinner fWeekDaySpin = sPfreqWeekDay;
			ArrayAdapter<?> fWeekDayAdapter = ArrayAdapter.createFromResource(this,
					R.array.freqWeekDay, android.R.layout.simple_spinner_item);
			fWeekDayAdapter.setDropDownViewResource(
					android.R.layout.simple_spinner_dropdown_item);
			
			// listen to freq day of week spinner selections
			fWeekDaySpin.setOnItemSelectedListener(
					new AdapterView.OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?>
						parent, View itemSelected,
							int selectedItemPosition, long selectedId)
							{	
								Editor editor = appPref.edit();
								editor.putString(APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_WEEK_DAY, 
										Integer.toString(selectedItemPosition));
								editor.commit();
							}	
					
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
			
			// set frequency date of month spinner
			final Spinner fMonthDateSpin = sPfreqMonthDate;
			ArrayAdapter<?> fMonthDateAdapter = ArrayAdapter.createFromResource(this,
					R.array.freqMonthDate, android.R.layout.simple_spinner_item);
			fMonthDateAdapter.setDropDownViewResource(
					android.R.layout.simple_spinner_dropdown_item);
			
			// listen to freq date of month spinner selections
			fMonthDateSpin.setOnItemSelectedListener(
					new AdapterView.OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?>
						parent, View itemSelected,
							int selectedItemPosition, long selectedId)
							{	
								Editor editor = appPref.edit();
								editor.putString(
                                        APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE,
										Integer.toString(selectedItemPosition));
								editor.commit();
							}	
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
			
			// set frequency month date before and after spinner
			final Spinner fMonthDateBefAfSpin = sPfreqMonthDateBeforeAfter;
			ArrayAdapter<?> fMonthDateBefAfAdapter = ArrayAdapter.createFromResource(this,
					R.array.freqMonthDateBeforeAfter, android.R.layout.simple_spinner_item);
			fMonthDateBefAfAdapter.setDropDownViewResource(
					android.R.layout.simple_spinner_dropdown_item);
			
			// listen to freq month date before and after spinner selections
			fMonthDateBefAfSpin.setOnItemSelectedListener(
					new AdapterView.OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?>
						parent, View itemSelected,
							int selectedItemPosition, long selectedId)
							{	
								Editor editor = appPref.edit();
								editor.putString(
                                        APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BEFORE_AFTER,
										Integer.toString(selectedItemPosition));
								editor.commit();
							}	
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
			
			// set frequency month week spinner
			final Spinner fMonthWeekSpin = sPfreqMonthWeek;
			ArrayAdapter<?> fMonthWeekAdapter = ArrayAdapter.createFromResource(this,
					R.array.freqWeekDay, android.R.layout.simple_spinner_item);
			fMonthWeekAdapter.setDropDownViewResource(
					android.R.layout.simple_spinner_dropdown_item);
			
			// listen to freq month week spinner selections
			fMonthWeekSpin.setOnItemSelectedListener(
					new AdapterView.OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?>
						parent, View itemSelected,
							int selectedItemPosition, long selectedId)
							{	
								Editor editor = appPref.edit();
								editor.putString(
                                        APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_WEEK,
										Integer.toString(selectedItemPosition));
								editor.commit();
							}	
					
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
			
			// set frequency month weekday spinner
			final Spinner fMonthWeekDaySpin = sPfreqMonthWeekDay;
			ArrayAdapter<?> fMonthWeekDayAdapter = ArrayAdapter.createFromResource(this,
					R.array.freqMonthWeekDay, android.R.layout.simple_spinner_item);
			fMonthWeekDayAdapter.setDropDownViewResource(
					android.R.layout.simple_spinner_dropdown_item);
			
			// listen to freq month weekday spinner selections
			fMonthWeekDaySpin.setOnItemSelectedListener(
					new AdapterView.OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?>
						parent, View itemSelected,
							int selectedItemPosition, long selectedId)
							{	
								Editor editor = appPref.edit();
								editor.putString(
                                        APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_WEEK_DAY,
										Integer.toString(selectedItemPosition));
								editor.commit();
							}	
					
				public void onNothingSelected(AdapterView<?> parent) {
				}
			});
			
			// set frequency year month date spinner
			final Spinner fYearMonthDateSpin = sPfreqYearMonthDate;
			ArrayAdapter<?> fYearMonthDateAdapter = ArrayAdapter.createFromResource(this,
					R.array.freqMonthDate, android.R.layout.simple_spinner_item);
			fYearMonthDateAdapter.setDropDownViewResource(
					android.R.layout.simple_spinner_dropdown_item);
			
			// listen to freq year month date spinner selections 
			fYearMonthDateSpin.setOnItemSelectedListener(
					new AdapterView.OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?>
						parent, View itemSelected,
							int selectedItemPosition, long selectedId)
						{
							Editor editor = appPref.edit();
							editor.putString(
                                    APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_YEAR_MONTH_DATE,
									Integer.toString(selectedItemPosition));
							editor.commit();
						}
					public void onNothingSelected(AdapterView<?> parent) {
					}
			});	
			
			// set frequency year month spinner
			final Spinner fYearMonthSpin = sPfreqYearMonth;
			ArrayAdapter<?> fYearMonthAdapter = ArrayAdapter.createFromResource(this,
					R.array.freqYearMonth, android.R.layout.simple_spinner_item);
			fYearMonthAdapter.setDropDownViewResource(
					android.R.layout.simple_spinner_dropdown_item);
			
			// listen to freq year month spinner selections 
			fYearMonthSpin.setOnItemSelectedListener(
					new AdapterView.OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?>
						parent, View itemSelected,
							int selectedItemPosition, long selectedId)
						{
							Editor editor = appPref.edit();
							editor.putString(APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_YEAR_MONTH, 
									Integer.toString(selectedItemPosition));
							editor.commit();
						}
					public void onNothingSelected(AdapterView<?> parent) {
					}
			});		
			
			// listener to day button
			final Button fDayButton = bNfreqDay;
			fDayButton.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					enableDateButton();
					}
				}
			);
			
			// listener to date button
			
			final Button fDateButton = bNfreqDate;
			fDateButton.setOnClickListener(new OnClickListener() {
				
				public void onClick(View arg0) {
					enableDayButton();
					}
				}
			);
			
			//set details to appear in fields depending on creating a new bgt amount or editing one
			if (prefBole == true) {
				eTbgtName.setText("New Budget");
                eTbgtName.setSelectAllOnFocus(true);
                bgtDialog.getWindow().setSoftInputMode(
                       WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				tVcurSymbol.setText(curSymbol);
				eTlimit.setText(R.string.zero_bal);
				tVcurSymbolSpent.setText(curSymbol);
				eTspent.setText(R.string.zero_bal);
				eTfreq.setText("01");
                eTfreq.setSelectAllOnFocus(true);
				fTypeSpin.setSelection(0);
			} else {
				Log.i(APP_LOG,
                        "Strings values in app prefs before preparing dialog: "
                                + "\nBGT_NAME: " +
						appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_NAME,
                                "") + " \nBGT_LIMIT: " +
						appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_LIMIT,
                                "") + " \nBGT_AMOUNT_SPENT: " +
						appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_AMOUNT_SPENT,
                                "") + " \nBGT_FREQ: " +
						appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ,
                                "") + " \nBGT_FREQ_TYPE: " +
						appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_TYPE,
                                "") + " \nBGT_FREQ_WEEK_DAY: " +
                        appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BUTTON_BOOLEAN,
                                "") + " \nBGT_FREQ_MONTH_DATE_BUTTON_BOOLEAN: " +
                        appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DAY_BUTTON_BOOLEAN,
                                "") + " \nBGT_FREQ_MONTH_DAY_BUTTON_BOOLEAN: " +
						appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_WEEK_DAY,
                                "") + " \nBGT_FREQ_MONTH_DATE: " +
						appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE,
                                "") + " \nBGT_FREQ_MONTH_DATE_BEFORE_AFTER: " +
						appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BEFORE_AFTER,
                                "") + " \nBGT_FREQ_MONTH_WEEK: " +
						appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_WEEK,
                                "") + " \nBGT_FREQ_MONTH_WEEK_DAY: " +
						appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_WEEK_DAY, "")
                                + " \nBGT_FREQ_YEAR_MONTH_DATE: " +
						appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_YEAR_MONTH_DATE, "")
                                + " \nBGT_FREQ_YEAR_MONTH" +
						appPref.getString(
                                APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_YEAR_MONTH, "")
						);
			
				eTbgtName.setText(appPref.getString(
                        APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_NAME, ""));
				tVcurSymbol.setText(curSymbol);
				eTlimit.setText(appPref.getString(
                        APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_LIMIT, ""));
				tVcurSymbolSpent.setText(curSymbol);
				eTspent.setText(appPref.getString(
                        APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_AMOUNT_SPENT, ""));
				eTfreq.setText(appPref.getString(
                        APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ, ""));
				fTypeSpin.setSelection(Integer.parseInt(appPref.getString(
                        APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_TYPE, "0")));
				switch (Integer.parseInt(appPref.getString(
                        APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_TYPE, "0"))) {
						case 0:
							break;
						case 1:
							fWeekDaySpin.setSelection(
                                    Integer.parseInt(appPref.getString(
                                            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_WEEK_DAY,
                                            "0")));
							break;
						case 2:
							fMonthDateSpin.setSelection(
                                    Integer.parseInt(appPref.getString(
                                            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE,
                                            "0")));
							fMonthDateBefAfSpin.setSelection(Integer.parseInt(appPref.getString(
                                    APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BEFORE_AFTER,
                                    "0")));
							fMonthWeekSpin.setSelection(
                                    Integer.parseInt(appPref.getString(
                                            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_WEEK,
                                            "0")));
							fMonthWeekDaySpin.setSelection(
                                    Integer.parseInt(appPref.getString(
                                            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_WEEK_DAY,
                                            "0")));
							break;
						case 3:
							fYearMonthDateSpin.setSelection(
                                    Integer.parseInt(appPref.getString(
                                            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_YEAR_MONTH_DATE,
                                            "0")));
							fYearMonthSpin.setSelection(
                                    Integer.parseInt(appPref.getString(
                                            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_YEAR_MONTH,
                                            "0")));
							break;
					}
				}	
				

            /*
            eTbgtName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				
				@Override   
				public void onFocusChange(View v, boolean hasFocus) {



					if (hasFocus)
                        eTbgtName.setSelectAllOnFocus(true);
                        eTbgtName.setSelection(eTbgtName.getText().toString().length());
                        //bgtDialog.getWindow().setSoftInputMode(
                        //   WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

				}
			});
            */
		
			currencyTextChangeListener(eTlimit, bgtDialogETwatch);
			eTlimit.setSelection(eTlimit.getText().toString().length());
			currencyTextChangeListener(eTspent, bgtDialogETwatch);
			eTspent.setSelection(eTspent.getText().toString().length());
			return;
		}
	}

	@SuppressLint("InflateParams")
	@Override
	protected Dialog onCreateDialog(int id) {
		
		//AlertDialog bgtDialog = null;
		
		switch (id) {
		case BGT_DIALOG_ID:
			LayoutInflater inflater = LayoutInflater.from(this);
			View dialogView = inflater.inflate(R.layout.bgt_dialog, null);
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
					new ContextThemeWrapper(this, R.style.CustomAlertDialog));
			dialogBuilder.setView(dialogView);
			TextView customTitle = new TextView(this);
			if (prefBole == true) {
			customTitle.setText(R.string.dialog_bgt_add_title);
			} else {
				customTitle.setText(R.string.dialog_bgt_edit_title);
			}
			customTitle.setTypeface(Typeface.SERIF, Typeface.BOLD);
			customTitle.setTextSize(20);
			customTitle.setGravity(Gravity.CENTER);
			dialogBuilder.setCustomTitle(customTitle);
			
			// cancel/neutral button
			dialogBuilder.setNeutralButton(R.string.dialog_bgt_cancel, 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							appPref.edit().clear();
							appPref.edit().commit();
							BudgetDialogActivity.this.finish();
						}
					}); 
			
			// positive button
			dialogBuilder.setPositiveButton(R.string.dialog_bgt_save, 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							removeTextChangeListener(eTlimit, bgtDialogETwatch);	
							String strName = eTbgtName.getText().toString();
							String strLimit = eTlimit.getText().toString();
							String strSpent = eTspent.getText().toString();
							String strFreq = eTfreq.getText().toString();
							String strFreqType = Integer.toString(
                                    sPfreqType.getSelectedItemPosition());
							
							String strFreqWeekDay = Integer.toString(
                                    sPfreqWeekDay.getSelectedItemPosition());
                            String strFreqMonthDateButtonBole;
                            if (bNfreqDate.isEnabled()){
                                strFreqMonthDateButtonBole = "true";
                            } else {
                                strFreqMonthDateButtonBole = "";
                            }
                            String strFreqMonthDayButtonBole;
                            if (bNfreqDay.isEnabled()){
                                strFreqMonthDayButtonBole = "true";
                            } else {
                                strFreqMonthDayButtonBole = "";
                            }
							String strFreqMonthDate = Integer.toString(
                                    sPfreqMonthDate.getSelectedItemPosition());
							String strFreqMonthDateBeforeAfter = Integer.toString(
                                    sPfreqMonthDateBeforeAfter.getSelectedItemPosition());
							String strFreqMonthWeek = Integer.toString(
                                    sPfreqMonthWeek.getSelectedItemPosition());
							String strFreqMonthWeekDay = Integer.toString(
                                    sPfreqMonthWeekDay.getSelectedItemPosition());
							
							String strFreqYearMonthDate = Integer.toString(
                                    sPfreqYearMonthDate.getSelectedItemPosition());
							String strFreqYearMonth = Integer.toString(
                                    sPfreqYearMonth.getSelectedItemPosition());
							
							BudgetDatabaseHelper bgtDb = new BudgetDatabaseHelper(
                                    BudgetDialogActivity.this);
							if (prefBole == true) {
								Log.i(APP_LOG, "Inserting new budget into database..");
								bgtDb.addBudget(new Budget( 
										strName,
										strLimit,
										strSpent,
										strFreq,
										strFreqType,
										strFreqWeekDay,
                                        strFreqMonthDateButtonBole,
                                        strFreqMonthDayButtonBole,
										strFreqMonthDate,
										strFreqMonthDateBeforeAfter,
										strFreqMonthWeek,
										strFreqMonthWeekDay,
										strFreqYearMonthDate,
										strFreqYearMonth
										));
								bgtDb.close();
							} else {
								Log.i(APP_LOG, "Updating existing budget into database..");
								int editAccPos = Integer.parseInt(
                                        appPref.getString(APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_ID,
                                                ""));
								Log.i(APP_LOG, "String Values: " + 
										strName + " " +
										strLimit + " " +
										strSpent + " " +
										strFreq + " " +
										strFreqType + " " +
										strFreqWeekDay + " " +
                                        strFreqMonthDateButtonBole + " " +
                                        strFreqMonthDayButtonBole + " " +
										strFreqMonthDate + " " +
										strFreqMonthDateBeforeAfter + " " +
										strFreqMonthWeek + " " +
										strFreqMonthWeekDay + " " +
										strFreqYearMonthDate + " " +
										strFreqYearMonth
										);
								Budget editAcc = bgtDb.getBudget(editAccPos);
								// apply new settings
								editAcc.setBudgetName(strName);
								editAcc.setBudgetLimit(strLimit);
								editAcc.setBudgetAmountSpent(strSpent);
								editAcc.setBudgetFreq(strFreq);
								editAcc.setBudgetFreqType(strFreqType);

                                switch (Integer.parseInt(strFreqType)) {
                                    case 0:
                                        editAcc.setBudgetFreqWeekDay("0");
                                        editAcc.setBudgetFreqMonthDateButtonBole("");
                                        editAcc.setBudgetFreqMonthDayButtonBole("");
                                        editAcc.setBudgetFreqMonthDate("0");
                                        editAcc.setBudgetFreqMonthDateBeforeAfter(
                                                "0");
                                        editAcc.setBudgetFreqMonthWeek("0");
                                        editAcc.setBudgetFreqMonthWeekDay("0");
                                        editAcc.setBudgetFreqYearMonthDate("0");
                                        editAcc.setBudgetFreqYearMonth("0");
                                        break;
                                    case 1:
                                        editAcc.setBudgetFreqWeekDay(strFreqWeekDay);
                                        editAcc.setBudgetFreqMonthDateButtonBole("");
                                        editAcc.setBudgetFreqMonthDayButtonBole("");
                                        editAcc.setBudgetFreqMonthDate("0");
                                        editAcc.setBudgetFreqMonthDateBeforeAfter(
                                                "0");
                                        editAcc.setBudgetFreqMonthWeek("0");
                                        editAcc.setBudgetFreqMonthWeekDay("0");
                                        editAcc.setBudgetFreqYearMonthDate("0");
                                        editAcc.setBudgetFreqYearMonth("0");
                                        break;
                                    case 2:
                                        editAcc.setBudgetFreqWeekDay("0");
                                        if (strFreqMonthDateButtonBole == "true") {
                                            editAcc.setBudgetFreqMonthDateButtonBole(
                                                    appPref.getString(
                                                            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BUTTON_BOOLEAN,
                                                            "true"));
                                            editAcc.setBudgetFreqMonthDayButtonBole(
                                                    appPref.getString(
                                                            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DAY_BUTTON_BOOLEAN,
                                                            "false"));
                                            editAcc.setBudgetFreqMonthDate("0");
                                            editAcc.setBudgetFreqMonthDateBeforeAfter(
                                                    "0");
                                            editAcc.setBudgetFreqMonthWeek(strFreqMonthWeek);
                                            editAcc.setBudgetFreqMonthWeekDay(strFreqMonthWeekDay);
                                            editAcc.setBudgetFreqYearMonthDate("0");
                                            editAcc.setBudgetFreqYearMonth("0");
                                        } else if (strFreqMonthDayButtonBole == "true"){
                                            editAcc.setBudgetFreqMonthDateButtonBole(
                                                    appPref.getString(
                                                            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BUTTON_BOOLEAN,
                                                            "false"));
                                            editAcc.setBudgetFreqMonthDayButtonBole(
                                                    appPref.getString(
                                                            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DAY_BUTTON_BOOLEAN,
                                                            "true"));
                                            editAcc.setBudgetFreqMonthDate(strFreqMonthDate);
                                            editAcc.setBudgetFreqMonthDateBeforeAfter(
                                                    strFreqMonthDateBeforeAfter);
                                            editAcc.setBudgetFreqMonthWeek("0");
                                            editAcc.setBudgetFreqMonthWeekDay("0");
                                            editAcc.setBudgetFreqYearMonthDate("0");
                                            editAcc.setBudgetFreqYearMonth("0");
                                        }
                                        break;
                                    case 3:
                                        editAcc.setBudgetFreqWeekDay("0");
                                        editAcc.setBudgetFreqMonthDateButtonBole("");
                                        editAcc.setBudgetFreqMonthDayButtonBole("");
                                        editAcc.setBudgetFreqMonthDate("0");
                                        editAcc.setBudgetFreqMonthDateBeforeAfter(
                                                "0");
                                        editAcc.setBudgetFreqMonthWeek("0");
                                        editAcc.setBudgetFreqMonthWeekDay("0");
                                        editAcc.setBudgetFreqYearMonthDate(strFreqYearMonthDate);
                                        editAcc.setBudgetFreqYearMonth(strFreqYearMonth);
                                        break;
                                }
								bgtDb.updateBudget(editAcc);
								bgtDb.close();
								
								}
							appPref.edit().clear();
							appPref.edit().commit();
							BudgetDialogActivity.this.finish();
						}
					});
			
			//Negative button
			if (prefBole != true) {
			dialogBuilder.setNegativeButton(R.string.dialog_bgt_delete, 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							BudgetDatabaseHelper bgtDb = new BudgetDatabaseHelper(
                                    BudgetDialogActivity.this);
							int delAccPos = Integer.parseInt(appPref.getString(
                                    APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_ID, ""));
							Budget delAcc = bgtDb.getBudget(delAccPos);
							bgtDb.deleteBudget(delAcc);
							bgtDb.close();
							appPref.edit().clear();
							appPref.edit().commit();
							BudgetDialogActivity.this.finish();
						}
					});
			}
			
			// dismiss behaviour
			dialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					appPref.edit().clear();
					appPref.edit().commit();
					BudgetDialogActivity.this.finish();
					
				}
			});
			
			bgtDialog = dialogBuilder.create();
		}
		return bgtDialog;
	}
}
