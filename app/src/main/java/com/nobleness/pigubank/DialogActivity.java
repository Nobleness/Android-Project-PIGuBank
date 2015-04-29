package com.nobleness.pigubank;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class DialogActivity extends PIGuActivity {
	
	SharedPreferences appPref;
	
	static final int CALC_DIALOG_ID = 0;
	TextWatcher calcDialogEtWatch;
	static EditText calcDialogEt = null;
	static Spinner calcDialogSPacc = null;
	static Spinner calcDialogSPbgt = null;
	
	ArrayList<Integer> alistAccId;
	ArrayList<String> alistAccName;
	ArrayList<Integer> alistBgtId;
	ArrayList<String> alistBgtName;
	
	AlertDialog calcDialog = null;
	boolean prefBole;
	
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(APP_LOG, "DialogActivity created");
		appPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
		prefBole = appPref.getBoolean(APP_PREFERENCES_LAUNCHED_DIALOG_FROM_OVERVIEW, false);
		showDialog(CALC_DIALOG_ID);
		disableDialogButtons();
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		Log.i(APP_LOG, "DialogActivity closed due to pause");
		
		Intent widgetIntent = new Intent(this, WidgetActivity.class);
  		widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
  		int[] ids = {R.xml.widget_info};
  		widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
  		sendBroadcast(widgetIntent);
		DialogActivity.this.finish();
	}
	
	public void enableDialogButtons() {
		calcDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(true);
		calcDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
	}
	
	public void disableDialogButtons(){
		calcDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setEnabled(false);
		calcDialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
	}
	
	public void removeTextChangeListener(final EditText et) {
		et.removeTextChangedListener(calcDialogEtWatch);
	}
	
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		
		switch (id) {
		case CALC_DIALOG_ID:
			AlertDialog calcDialog = (AlertDialog) dialog;
			TextView calcTV = (TextView) calcDialog.findViewById(R.id.calc_dialogTV);
			calcTV.setText(curSymbol);
			calcDialogEt = (EditText) calcDialog.findViewById(R.id.calc_dialogET);
			calcDialogEt.setText(R.string.zero_bal);
			calcDialogEt.setSelection(calcDialogEt.getText().toString().length());
			currencyTextChangeListener(calcDialogEt, calcDialogEtWatch);
			focusChangeOpenSoftKeyboard(calcDialogEt, calcDialog);
			calcDialogSPacc = (Spinner) calcDialog.findViewById(R.id.calcDialogSPacc);
			calcDialogSPbgt = (Spinner) calcDialog.findViewById(R.id.calcDialogSPbgt);
			
			AccountDatabaseHelper accDb = new AccountDatabaseHelper(this);
			alistAccId = new ArrayList<Integer>();
			alistAccName = new ArrayList<String>();
			int accMainIdPosition = 0;
			List<Account> accList = accDb.getAllAccounts();
			for (Account acc : accList) {
				int accId = acc.getID();
				alistAccId.add(accId);
				String strAccName = acc.getAccountName();
				alistAccName.add(strAccName);
				String accMain = acc.getAccountMain();
				if (accMain.contains("true")) {
					Log.i(APP_LOG, "Account ID of Main Account: " + Integer.toString(accId));
					accMainIdPosition = accId - 1;
					}
				}
			accDb.close();	
			
			final Spinner accSpin = calcDialogSPacc;
			ArrayAdapter<String> accSpinAdapter = new ArrayAdapter<String>(this, 
					android.R.layout.simple_spinner_dropdown_item, alistAccName);
			accSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			accSpin.setAdapter(accSpinAdapter);
			accSpin.setSelection(accMainIdPosition);
			
			accSpin.setOnItemSelectedListener(
					new AdapterView.OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?>
						parent, View itemSelected,
							int selectedItemPosition, long selectedId) {
								Editor editor = appPref.edit();
								int recalculatePos = selectedItemPosition + 1;
								editor.putString(APP_PREFERENCES_CALC_DIALOG_ACCOUNT_ID, 
										Integer.toString(recalculatePos));
								Log.i(APP_LOG, "Selected Position: " + recalculatePos);
								editor.commit();
							}
					public void onNothingSelected(AdapterView<?> parent) {
						// do nothing
					}
				});
			
			BudgetDatabaseHelper bgtDb = new BudgetDatabaseHelper(this);
			alistBgtId = new ArrayList<Integer>();
			alistBgtName = new ArrayList<String>();
			String strUnselBudget = "Select Budget";
			String strNoBudget = "None";
			alistBgtName.add(strUnselBudget);
			alistBgtName.add(strNoBudget);
			List<Budget> bgtList = bgtDb.getAllBudgets();
			for (Budget bgt : bgtList) {
				int bgtId = bgt.getID();
				alistBgtId.add(bgtId);
				String strBgtName = bgt.getBudgetName();
				alistBgtName.add(strBgtName);
				}
			bgtDb.close();
			
			final Spinner bgtSpin = calcDialogSPbgt;
			ArrayAdapter<String> bgtSpinAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item, alistBgtName);
			bgtSpinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			bgtSpin.setAdapter(bgtSpinAdapter);
			bgtSpin.setSelection(0);
			
			bgtSpin.setOnItemSelectedListener(
					new AdapterView.OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?>
						parent, View itemSelected,
							int selectedItemPosition, long selectedId) {
							disableDialogButtons();
								if (selectedItemPosition != 0) {
									enableDialogButtons();
									if (selectedItemPosition != 1) {
										Editor editor = appPref.edit();
										int recalculatePos = selectedItemPosition - 1;
										editor.putString(APP_PREFERENCES_CALC_DIALOG_BUDGET_ID, 
												Integer.toString(recalculatePos));
										editor.commit();
										enableDialogButtons();
									}
								}
							}
						public void onNothingSelected(AdapterView<?> parent) {
							//do nothing
						}
			});
			
			return;
		}
	}
	

	@SuppressLint("InflateParams")
	@Override
	protected Dialog onCreateDialog(int id) {
		
		switch (id) {
		case CALC_DIALOG_ID:
			LayoutInflater inflater = LayoutInflater.from(this);
			View dialogview = inflater.inflate(R.layout.calc_dialog, null);
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
					new ContextThemeWrapper(this, R.style.CustomAlertDialog));
			dialogBuilder.setView(dialogview);
			TextView customTitle = new TextView(this);
			customTitle.setText(R.string.dialog_wgt_title);
			customTitle.setTypeface(Typeface.SERIF, Typeface.BOLD);
			customTitle.setTextSize(20);
			customTitle.setGravity(Gravity.CENTER);
			dialogBuilder.setCustomTitle(customTitle);

            if (prefBole == true) {
                // Do nothing
                } else {
                    dialogBuilder.setNeutralButton(R.string.dialog_wgt_button_open,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   startActivity(new Intent(DialogActivity.this,
                                           HomeActivity.class));
                                   DialogActivity.this.finish();
                                }
                            });
                }
			dialogBuilder.setPositiveButton(R.string.dialog_wgt_button_add, 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
								removeTextChangeListener(calcDialogEt);
								String calcEtString = calcDialogEt.getText().toString();
								Double resultCalcEtDouble = 
										Double.parseDouble(calcEtString.replaceAll(",", ""));
								String curBalString = "0.00";
								
								// calculate changes to account balance
								AccountDatabaseHelper accDb = 
										new AccountDatabaseHelper(DialogActivity.this);
								int parseSelAccId = 
										Integer.parseInt(
												appPref.getString(
														APP_PREFERENCES_CALC_DIALOG_ACCOUNT_ID, "1"));
								List<Account> accList = accDb.getAllAccounts();
								for (Account acc : accList) {
									int accId = acc.getID();
									String strAccId = Integer.toString(accId);
									Log.i(APP_LOG, strAccId );
										
										if (accId == parseSelAccId) {
											curBalString = acc.getAccountBalance();
											Double resultCurBalDouble = 
													Double.parseDouble(
															curBalString.replaceAll(",", ""));
											Double sumResultDouble = 
													resultCurBalDouble + resultCalcEtDouble;
											String strFormatedResult = 
													curencyFormat.format(sumResultDouble);
											acc.setAccountBalance(strFormatedResult);
											accDb.updateAccount(acc);
										} else {
											// do nothing
										}
									}
								accDb.close();
								
								// calculate changes to budget balance
								BudgetDatabaseHelper bgtDb = 
										new BudgetDatabaseHelper(DialogActivity.this);
								int parseSelBgtId = 
										Integer.parseInt(
												appPref.getString(
														APP_PREFERENCES_CALC_DIALOG_BUDGET_ID, "0"));
								List<Budget> bgtList = bgtDb.getAllBudgets();
								for(Budget bgt : bgtList) {
									int bgtId = bgt.getID();
									String strBgtId = Integer.toString(bgtId);
									Log.i(APP_LOG, strBgtId);
									if (bgtId == parseSelBgtId) {
										curBalString = bgt.getBudgetAmountSpent();
										Double resultCurBalDouble = 
												Double.parseDouble(curBalString.replaceAll(",", ""));
										Double sumResultDouble = 
												resultCurBalDouble + resultCalcEtDouble;
										String strFormatedResult = 
												curencyFormat.format(sumResultDouble);
										bgt.setBudgetAmountSpent(strFormatedResult);
										bgtDb.updateBudget(bgt);
									}
								}
								bgtDb.close();
								
						DialogActivity.this.finish();
							
						}
					});
			
			dialogBuilder.setNegativeButton(R.string.dialog_wgt_button_subtract, 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							removeTextChangeListener(calcDialogEt);
							String calcEtString = calcDialogEt.getText().toString();
							Double resultCalcEtDouble = 
									Double.parseDouble(calcEtString.replaceAll(",", ""));
							String curBalString = "0.00";
							
							AccountDatabaseHelper accDb = 
									new AccountDatabaseHelper(DialogActivity.this);
							int parseSelAccId = 
									Integer.parseInt(
											appPref.getString(
													APP_PREFERENCES_CALC_DIALOG_ACCOUNT_ID, "1"));
							List<Account> accList = accDb.getAllAccounts();
							for (Account acc : accList) {
								int accId = acc.getID();
								String strAccId = Integer.toString(accId);
								Log.i(APP_LOG, strAccId );
									
									if (accId == parseSelAccId) {
										curBalString = acc.getAccountBalance();
										Double resultCurBalDouble = 
												Double.parseDouble(curBalString.replaceAll(",", ""));
										Double sumResultDouble = 
												resultCurBalDouble - resultCalcEtDouble;
										String strFormatedResult = 
												curencyFormat.format(sumResultDouble);
										acc.setAccountBalance(strFormatedResult);
										accDb.updateAccount(acc);
									} else {
										// do nothing
									}
								}
							accDb.close();
							
							// calculate changes to budget balance
							BudgetDatabaseHelper bgtDb = 
									new BudgetDatabaseHelper(DialogActivity.this);
							int parseSelBgtId = 
									Integer.parseInt(appPref.getString(
											APP_PREFERENCES_CALC_DIALOG_BUDGET_ID, "0"));
							List<Budget> bgtList = bgtDb.getAllBudgets();
							for(Budget bgt : bgtList) {
								int bgtId = bgt.getID();
								String strBgtId = Integer.toString(bgtId);
								Log.i(APP_LOG, strBgtId);
								if (bgtId == parseSelBgtId) {
									curBalString = bgt.getBudgetAmountSpent();
									Double resultCurBalDouble = 
											Double.parseDouble(curBalString.replaceAll(",", ""));
									Double sumResultDouble = 
											resultCurBalDouble - resultCalcEtDouble;
									String strFormatedResult = 
											curencyFormat.format(sumResultDouble);
									bgt.setBudgetAmountSpent(strFormatedResult);
									bgtDb.updateBudget(bgt);
								}
							}
							bgtDb.close();
						DialogActivity.this.finish();	
						}
					});
			
			dialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
				
				public void onDismiss(DialogInterface dialog){
				DialogActivity.this.finish();
				}
			}); 
			calcDialog = dialogBuilder.create();
		break;
		}
		
		return calcDialog;
	}
		
}
