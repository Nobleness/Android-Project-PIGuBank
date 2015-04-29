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

public class AccountsActivity extends PIGuActivity {

	ArrayList<Integer> arrayListAccId;
	ArrayList<String> arrayListAccMain;
	ArrayList<String> arrayListAccName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(APP_LOG, "In Activity, Accounts");
		setContentView(R.layout.accounts);
		updateAccountsList();
	}	
	
	@Override
	protected void onResume() {
		super.onResume();
		updateAccountsList();
		Editor updateBolean = appPref.edit();
		updateBolean.putBoolean(APP_PREFERENCES_LAUNCHED_ADD_ACCOUNT_DIALOG, false);
		updateBolean.commit();
	}

    @Override
    protected void onPause() {
        super.onPause();
        Boolean prefBole1 =
                appPref.
                getBoolean(APP_PREFERENCES_LAUNCHED_ADD_ACCOUNT_DIALOG, false);
        Boolean prefBole2 =
                appPref.
                getBoolean(APP_PREFERENCES_LAUNCHED_EDIT_ACCOUNT_DIALOG, false);
        if (prefBole1 == true || prefBole2 == true ) {
            // do nothing
        } else{
            AccountsActivity.this.finish();
        }
    }

	public void updateAccountsList() {
		
		// open database connection
		AccountDatabaseHelper accDb = new AccountDatabaseHelper(this);
		arrayListAccId = new ArrayList<Integer>();
		arrayListAccMain = new ArrayList<String>();
		arrayListAccName = new ArrayList<String>();
		List<Account> accList = accDb.getAllAccounts();
		for (Account acc : accList) {
			int accId = acc.getID();
			arrayListAccId.add(accId);
			String strAccMain = acc.getAccountMain();
			arrayListAccMain.add(strAccMain);
			String strAccName = acc.getAccountName();
			arrayListAccName.add(strAccName);
			Log.i(APP_LOG," ACCOUNT ID= " +
                    Integer.toString(accId) + "ACCOUNT MAIN?= "+
                    strAccMain + "ACCOUNT NAME= "+ strAccName);
		}
		accDb.close();
		Log.i(APP_LOG, "The total number of accounts is= " + arrayListAccId.size());
		
		// build account buttons
		arrayListAccId.toArray();
		arrayListAccMain.toArray();
		arrayListAccName.toArray();
		ListView accListView = (ListView) 
		findViewById(R.id.AccountsLVaccountButtons);
		AccountAdapter accListAdapter = new AccountAdapter(this,
                arrayListAccId, arrayListAccMain, arrayListAccName);
		accListView.setAdapter(accListAdapter);
	}
	
	public void onClickAddAccountButton(View view) {
		Editor updateBolean = appPref.edit();
		updateBolean.putBoolean(APP_PREFERENCES_LAUNCHED_ADD_ACCOUNT_DIALOG, true);
		updateBolean.commit();
		startActivity(new 
		Intent(AccountsActivity.this, AccountDialogActivity.class));
	}
	
}
