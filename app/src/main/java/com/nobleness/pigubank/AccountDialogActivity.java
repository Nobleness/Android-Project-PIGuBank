package com.nobleness.pigubank;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AccountDialogActivity extends PIGuActivity {

SharedPreferences appPref;
	
	static final int ACC_DIALOG_ID = 0;
	boolean prefBole;
	TextWatcher accDialogETwatch;
	OnCheckedChangeListener cbChangeWatch;
	
	static EditText accDialogETaccName =  null;
	static EditText accDialogETbalance = null;
	static EditText accDialogETcredit = null;
	static CheckBox accDialogCBcredit = null;
	static CheckBox accDialogCBoverview = null;
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(APP_LOG, "AccountDialogActivity created");
		appPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
		prefBole = appPref.getBoolean(APP_PREFERENCES_LAUNCHED_ADD_ACCOUNT_DIALOG, false);
		showDialog(ACC_DIALOG_ID);
	}
	
	@Override
	protected void onPause(){
		super.onPause();
		Log.i(APP_LOG, "AccountDialogActivity closed due to pause");
		appPref.edit().clear();
		appPref.edit().commit();
        AccountDialogActivity.this.finish();
	}

    @Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		
		switch (id) {
		case ACC_DIALOG_ID:
			final AlertDialog accDialog = (AlertDialog) dialog;
			
			accDialogETaccName = (EditText) accDialog.findViewById(R.id.accETaccName);
			accDialogETbalance = (EditText) accDialog.findViewById(R.id.accDialogETbalance);
			TextView accDialogTVbalanceSymbol =
                    (TextView) accDialog.findViewById(R.id.accDialogTVbalanceSymbol);
			TextView accDialogTVcreditSymbol =
                    (TextView) accDialog.findViewById(R.id.accDialogTVcreditSymbol);
			accDialogCBoverview = (CheckBox) accDialog.findViewById(R.id.accDialogCBoverview);
			accDialogCBcredit = (CheckBox) accDialog.findViewById(R.id.accDialogCBcredit);
			accDialogETcredit = (EditText) accDialog.findViewById(R.id.accDialogETcredit);
			
			//set details to appear in fields depending on creating a new account or editing one
			if (prefBole == true) {
				accDialogETaccName.setText("New Account");
				accDialogETaccName.setSelectAllOnFocus(true);
				accDialogETbalance.setText(R.string.zero_bal);
				accDialogTVbalanceSymbol.setText(curSymbol);
				accDialogTVcreditSymbol.setText(curSymbol);
			} else {
				Log.i(APP_LOG, "Strings values in app prefs before preparing dialog: " +
                        appPref.getString(
                                APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_ID, "") + " " +
						appPref.getString(
                                APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_NAME, "") + " " +
						appPref.getString(
                                APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_BALANCE, "") + " " +
						appPref.getString(
                                APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_MAIN, "") + " " +
						appPref.getString(
                                APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_CREDIT, "") + " " +
						appPref.getString(
                                APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_CREDITLIMIT, "")
						);
				accDialogETaccName.setText(appPref.getString(
                        APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_NAME, ""));
				accDialogETbalance.setText(appPref.getString(
                        APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_BALANCE, ""));
				accDialogTVbalanceSymbol.setText(curSymbol);
				if (appPref.getString(
                        APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_MAIN, "").contains("true")) {
						accDialogCBoverview.setChecked(true);
					} else if (appPref.getString(
                        APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_MAIN, "").contains("false")) {
						accDialogCBoverview.setChecked(false);
					}
				if (appPref.getString(
                        APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_CREDIT, "").contains("true")) {
					accDialogCBcredit.setChecked(true);
					accDialogETcredit.setText(appPref.getString(
                            APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_CREDITLIMIT, ""));
					accDialogETcredit.setEnabled(true);
					} else if (appPref.getString(
                        APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_CREDIT, "").contains("false")){
						accDialogCBcredit.setChecked(false);
					}
				accDialogTVcreditSymbol.setText(curSymbol);
			}
			accDialogETaccName.setSelection(accDialogETaccName.getText().toString().length());
			accDialogETaccName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				
				@Override   
				public void onFocusChange(View v, boolean hasFocus) {
					if (hasFocus) accDialog.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
					
				}
			});
		
			currencyTextChangeListener(accDialogETbalance, accDialogETwatch);
			accDialogETbalance.setSelection(accDialogETbalance.getText().toString().length());
			
			// check box listener
				accDialogCBcredit.setOnCheckedChangeListener(
						cbChangeWatch = new CompoundButton.OnCheckedChangeListener() {
					
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						
						if (isChecked == true) {
							accDialogETbalance.setImeOptions(5);
							accDialogETcredit.setEnabled(true);
							accDialogETcredit.setText(R.string.zero_bal);
                            currencyTextChangeListener(accDialogETcredit, accDialogETwatch);
							accDialogETcredit.setSelection(
                                    accDialogETcredit.getText().toString().length());
						} 
						
						if (isChecked == false) {
							try {
							accDialogETbalance.setImeOptions(6);
							removeTextChangeListener(accDialogETcredit, accDialogETwatch);
							accDialogETcredit.setText("");
							accDialogETcredit.setEnabled(false);
							} catch (Exception e) {
								Log.e(APP_LOG, "Exception removing watch from CheckBox");
							}
						}
						
					}
				});
				return;
		}
	}
	

	@SuppressLint("InflateParams")
	@Override
	protected Dialog onCreateDialog(int id) {
		
		AlertDialog accDialog = null;
		
		switch (id) {
		case ACC_DIALOG_ID:
			
			
			
			LayoutInflater inflater = LayoutInflater.from(this);
			View dialogView = inflater.inflate(R.layout.acc_dialog, null);
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(
					new ContextThemeWrapper(this, R.style.CustomAlertDialog));
			dialogBuilder.setView(dialogView);
			TextView customTitle = new TextView(this);
			if (prefBole == true) {
			customTitle.setText(R.string.dialog_acc_add_title);
			} else {
				customTitle.setText(R.string.dialog_acc_edit_title);
			}
			customTitle.setTypeface(Typeface.SERIF, Typeface.BOLD);
			customTitle.setTextSize(20);
			customTitle.setGravity(Gravity.CENTER);
			dialogBuilder.setCustomTitle(customTitle);
			
			// cancel/neutral button
			dialogBuilder.setNeutralButton(R.string.dialog_acc_cancel, 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {

							AccountDialogActivity.this.finish();
						}
					}); 
			
			// positive button
			dialogBuilder.setPositiveButton(R.string.dialog_acc_save, 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							removeTextChangeListener(accDialogETbalance, accDialogETwatch);
							removeTextChangeListener(accDialogETcredit, accDialogETwatch);		
							String strName = accDialogETaccName.getText().toString();
							String strBalance = accDialogETbalance.getText().toString();
							String strOverview = "false";
							if (accDialogCBoverview.isChecked()){
								AccountDatabaseHelper accDb =
                                        new AccountDatabaseHelper(AccountDialogActivity.this);
								List<Account> accList = accDb.getAllAccounts();
								for (Account acc : accList) {									
									acc.setAccountMain("false");	
									accDb.updateAccount(acc);
								}
								accDb.close();
								strOverview = "true"; 
							}
							String strCredit;
							String strCreditLimit;
							if (accDialogCBcredit.isChecked()){
								strCredit = "true";
								strCreditLimit = accDialogETcredit.getText().toString();
								} else { 
									strCredit = "false"; 
									strCreditLimit = "";
							}
							AccountDatabaseHelper accDb =
                                    new AccountDatabaseHelper(AccountDialogActivity.this);
							if (prefBole == true) {
								Log.i(APP_LOG, "Inserting new account into database..");
								accDb.addAccount(new Account( 
										strName,
										strBalance,
										strOverview,
										strCredit,
										strCreditLimit
										));
								accDb.close();
							} else {
								Log.i(APP_LOG, "Updating existing account into database..");
								int editAccPos = Integer.parseInt(
                                        appPref.getString(
                                                APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_ID, ""));
								Log.i(APP_LOG, "String Values: " + 
										strName + " " +
										strBalance + " " +
										strOverview + " " +
										strCredit + " " +
										strCreditLimit
										);
								Account editAcc = accDb.getAccount(editAccPos);
								editAcc.setAccountName(strName);
								editAcc.setAccountBalance(strBalance);
								editAcc.setAccountMain(strOverview);
								editAcc.setAccountCredit(strCredit);
								editAcc.setAccountCreditLimit(strCreditLimit);
								accDb.updateAccount(editAcc);
								accDb.close();
								}
							
							AccountDialogActivity.this.finish();
						}
					});
			
			if (prefBole == true) {
				// do nothing
			} else {
			
			//Negative button
			dialogBuilder.setNegativeButton(R.string.dialog_acc_delete, 
					new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							AccountDatabaseHelper accDb = new AccountDatabaseHelper(
                                    AccountDialogActivity.this);
							int delAccPos = Integer.parseInt(
                                    appPref.getString(APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_ID, ""));
							Account delAcc = accDb.getAccount(delAccPos);
							accDb.deleteAccount(delAcc);
							accDb.close();
							AccountDialogActivity.this.finish();
						}
					});
			}
			
			// dismiss behaviour
			dialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
				
				@Override
				public void onDismiss(DialogInterface dialog) {
					AccountDialogActivity.this.finish();
					
				}
			});
			
			accDialog = dialogBuilder.create();
		}
		return accDialog;
	}
		
}
