package com.nobleness.pigubank;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AccountAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private final ArrayList<Integer> arrayListAccId;
	private final ArrayList<String> arrayListAccMain;
	private final ArrayList<String> arrayListAccName;
	
	protected static final String ADAPTER_LOG = "AccountAdapter Log";
	
	public AccountAdapter(
			Context context,
            ArrayList<Integer> arrayListAccId,
            ArrayList<String> arrayListAccMain,
            ArrayList<String> arrayListAccName) {
		super(context, R.layout.accounts_buttons, arrayListAccName);
		this.context = context;
		this.arrayListAccId = arrayListAccId;
		this.arrayListAccMain = arrayListAccMain;
		this.arrayListAccName = arrayListAccName;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) convertView = inflater.inflate(
                R.layout.accounts_buttons, parent, false);

		Button bN = (Button) convertView.findViewById(R.id.AccountBNedit);
		bN.setText(arrayListAccName.get(position));
		bN.setOnClickListener(new View.OnClickListener() {	
			@Override
			public void onClick(View v) {

				try {

					Activity piguAct = (PIGuActivity) context;
					SharedPreferences appPref;
					appPref = (piguAct).getSharedPreferences(
                            PIGuActivity.APP_PREFERENCES, Context.MODE_PRIVATE);
					AccountDatabaseHelper accDb = new AccountDatabaseHelper(context);
					Account editAcc = accDb.getAccount(arrayListAccId.get(position));
					Editor editAccPref = appPref.edit();
                    editAccPref.putBoolean(
                            PIGuActivity.APP_PREFERENCES_LAUNCHED_EDIT_ACCOUNT_DIALOG, true);
					editAccPref.putString(
                            PIGuActivity.APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_ID,
                            Integer.toString(arrayListAccId.get(position)));
					editAccPref.putString(
                            PIGuActivity.APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_NAME,
                            editAcc.getAccountName());
					editAccPref.putString(
                            PIGuActivity.APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_BALANCE,
                            editAcc.getAccountBalance());
					editAccPref.putString(
                            PIGuActivity.APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_MAIN,
                            editAcc.getAccountMain());
					editAccPref.putString(
                            PIGuActivity.APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_CREDIT,
                            editAcc.getAccountCredit());
					editAccPref.putString(
                            PIGuActivity.APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_CREDITLIMIT,
                            editAcc.getAccountCreditLimit());
					editAccPref.commit();
					accDb.close();
				} catch (Exception e) {
					Log.e(ADAPTER_LOG, "Exception putting strings into " +
                            "application preferences in adapter view");
				}
				Intent intent = new Intent(context, AccountDialogActivity.class);
				context.startActivity(intent);
			}
		});
		TextView tVmain = (TextView) convertView.findViewById(R.id.AccountTVmain);
		tVmain.setVisibility(View.GONE);
		ImageView iVmain = (ImageView) convertView.findViewById(R.id.AccountIVmain);
		iVmain.setVisibility(View.GONE);
		if (arrayListAccMain.get(position).contains("true")) {
			tVmain.setVisibility(View.VISIBLE);
			iVmain.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
}
