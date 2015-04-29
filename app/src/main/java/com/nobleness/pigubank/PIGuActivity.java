package com.nobleness.pigubank;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class PIGuActivity extends FragmentActivity {
	
	@SuppressLint("Registered")
	public static final NumberFormat curencyFormat =
            new DecimalFormat("#,###,##0.00");
	public static final String curSymbol =
            NumberFormat.getCurrencyInstance().getCurrency().getSymbol();
	
	public static final String APP_LOG =
            "PIGu application logs";
	public static final String APP_PREFERENCES =
            "AppPrefs";
	public static final String
            APP_PREFERENCES_LAUNCHED_DIALOG_FROM_OVERVIEW =
            "prefBole";
	public static final String
            APP_PREFERENCES_LAUNCHED_ADD_ACCOUNT_DIALOG =
            "addAccDialogBoleon";
    public static final String
            APP_PREFERENCES_LAUNCHED_EDIT_ACCOUNT_DIALOG =
            "editAccDialogBoolean";
	public static final String
            APP_PREFERENCES_LAUNCHED_ADD_BUDGET_DIALOG =
            "editAccDialogBoleon";
	public static final String
            APP_PREFERENCES_CALC_DIALOG_ACCOUNT_ID =
            "editAccSelectedAccountId";
	public static final String
            APP_PREFERENCES_CALC_DIALOG_BUDGET_ID =
            "editAccSelectedBudgetId";

	public static final String
            APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_ID =
            "EditAccId";
	public static final String
            APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_NAME =
            "EditAccName";
	public static final String
            APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_BALANCE =
            "EditAccBalance";
	public static final String
            APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_MAIN =
            "EditAccAsMain";
	public static final String
            APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_CREDIT =
            "EditAccAsCredit";
	public static final String
            APP_PREFERENCES_ACCOUNT_FOR_EDIT_ACC_CREDITLIMIT =
            "EditAccCreditLimit";

	public static final String
            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_ID =
            "EditBgtId";
	public static final String
            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_NAME =
            "EditBgtName";
	public static final String
            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_LIMIT =
            "EditBgtLimit";
	public static final String
            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_AMOUNT_SPENT =
            "EditBgtAmountSpent";
	public static final String
            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ =
            "EditBgtFreq"; // int
	public static final String
            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_TYPE =
            "EditBgtFreqType"; // int spinner
	public static final String
            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_WEEK_DAY =
            "EditBgtFreqWeekDay"; // int spinner
    public static final String
            APP_PREFERENCES_BIDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BUTTON_BOOLEAN =
            "EditBgtFreqMonthDateBole"; // date button boolean
    public static final String
            APP_PREFERENCES_BIDGET_FOR_EDIT_BGT_FREQ_MONTH_DAY_BUTTON_BOOLEAN =
            "EditBgtFreqMonthDayBole"; // day button boolean
	public static final String
            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE =
            "EditBgtFreqMonthDate"; // int spinner
	public static final String
            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_DATE_BEFORE_AFTER =
            "EditBgtFreqMonthDateBeforeAfter"; // int spinner
	public static final String
            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_WEEK =
            "EditBgtFreMonthWeek"; // int spinner
	public static final String
            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_MONTH_WEEK_DAY =
            "EditBgtFreqMonthWeekDay"; // int spinner
	public static final String
            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_YEAR_MONTH_DATE =
            "EditBgtFreqYearMonthDate"; // int spinner
	public static final String
            APP_PREFERENCES_BUDGET_FOR_EDIT_BGT_FREQ_YEAR_MONTH =
            "EditBgtFreqYearMonth"; // int spinner
	
	
	SharedPreferences appPref;

	@Override 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i(APP_LOG, "In top level activity log");
		appPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }
  	
  	public void currencyTextChangeListener(final EditText et, TextWatcher eTwatch){
		et.addTextChangedListener(eTwatch = new TextWatcher(){
			String curValueCalcET = et.getText().toString();		
			@SuppressLint("InflateParams")
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {			
					
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
					//Nothing to do
			}
			
			@Override
			public void afterTextChanged(Editable s) {		
				if ( curValueCalcET.length() > et.getText().toString().length() ) {
					et.removeTextChangedListener((TextWatcher) this);
					Double parsed = Double.parseDouble(s.toString().replaceAll(",", ""));
					String format = curencyFormat.format(parsed/10);
					curValueCalcET = format;
					et.setText(format);
					et.setSelection(format.length());
					et.addTextChangedListener((TextWatcher) this);
					}
					else if(!s.toString().equals(curValueCalcET)) {
						et.removeTextChangedListener((TextWatcher) this);
						Double parsed = Double.parseDouble(s.toString().replaceAll(",", ""));
						String format = curencyFormat.format(parsed*10);
						curValueCalcET = format;
						if (curValueCalcET.length() == 12) {
							longMessageTop("You Rich Mo Fo!!!");
						}
						et.setText(format);
						et.setSelection(format.length());
						et.addTextChangedListener((TextWatcher) this);
					}
			}
		});
	}
	
	public void longMessageTop(String string) {
		Toast toast = Toast.makeText(this, string, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.TOP, 0, 0);
		toast.show();
	}

    /*
	public void loadMainBalance(TextView balanceTV) {
		AccountDatabaseHelper accDb = new AccountDatabaseHelper(this);
		List<Account> accList = accDb.getAllAccounts();
		balanceTV.setText("0.00");
			for (Account acc : accList) {
					if (acc.getAccountMain().contains("true")) {
						balanceTV.setText(acc.getAccountBalance());
					} else {
						// do nothing
					}	
				}
			accDb.close();
	}
	*/
	public void removeTextChangeListener(EditText et, TextWatcher tw) {
		et.removeTextChangedListener(tw);
	}
	
	public void focusChangeOpenSoftKeyboard(final EditText et, final Dialog dialog) {
		et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) dialog.getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
				
			}
		});
	}
}
  	

  	
