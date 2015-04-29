package com.nobleness.pigubank;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccountDatabaseHelper extends SQLiteOpenHelper {
	
		static final int DATABASE_VERSION = 1;
		static final String DATABASE_NAME = "AccountReader.db";
		
		static final String TABLE_NAME_ACCOUNTS = "accounts";
		
		static final String COLUMN_NAME_ACCOUNT_ID = "accountid";
		static final String COLUMN_NAME_ACCOUNT_NAME = "accountname";
		static final String COLUMN_NAME_ACCOUNT_BALANCE = "accountbalance";
		static final String COLUMN_NAME_ACCOUNT_MAIN = "accountmain";
		static final String COLUMN_NAME_ACCOUNT_CREDIT = "accountcredit";
		static final String COLUMN_NAME_ACCOUNT_CREDITLIMIT = "accountcreditlimit";
				
		//create tables
		final String SQL_CREATE_ENTRIES = 
				"CREATE TABLE " + TABLE_NAME_ACCOUNTS + "(" 
				+ COLUMN_NAME_ACCOUNT_ID + " INTEGER PRIMARY KEY,"
				+ COLUMN_NAME_ACCOUNT_NAME + " TEXT," 
				+ COLUMN_NAME_ACCOUNT_BALANCE + " TEXT,"
				+ COLUMN_NAME_ACCOUNT_MAIN + " TEXT,"
				+ COLUMN_NAME_ACCOUNT_CREDIT + " TEXT,"
				+ COLUMN_NAME_ACCOUNT_CREDITLIMIT + " TEXT" 
				+ ")";
		
		//drop table
		private static final String SQL_DELETE_ENTRIES = 
				"DROP TABLE IF EXISTS " + TABLE_NAME_ACCOUNTS;
	
	public AccountDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null,DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
	
//adding new account
	public void addAccount(Account account) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_ACCOUNT_NAME, account.getAccountName());
		values.put(COLUMN_NAME_ACCOUNT_BALANCE, account.getAccountBalance());
		values.put(COLUMN_NAME_ACCOUNT_MAIN, account.getAccountMain());
		values.put(COLUMN_NAME_ACCOUNT_CREDIT, account.getAccountCredit());
		values.put(COLUMN_NAME_ACCOUNT_CREDITLIMIT, account.getAccountCreditLimit());
		
		db.insert(TABLE_NAME_ACCOUNTS, null, values);
		db.close();
	}
	
//get single account 
	public Account getAccount(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(
				TABLE_NAME_ACCOUNTS, new String[] { 
						COLUMN_NAME_ACCOUNT_ID,
						COLUMN_NAME_ACCOUNT_NAME,
						COLUMN_NAME_ACCOUNT_BALANCE,
						COLUMN_NAME_ACCOUNT_MAIN,
						COLUMN_NAME_ACCOUNT_CREDIT,
						COLUMN_NAME_ACCOUNT_CREDITLIMIT }, 
						COLUMN_NAME_ACCOUNT_ID + "=?",
						new String[] { String.valueOf(id) }, null,null,null,null);
		if (cursor != null)
			cursor.moveToFirst();
						
		Account account = new Account(Integer.parseInt(
				cursor.getString(0)),
				cursor.getString(1), 
				cursor.getString(2),
				cursor.getString(3),
				cursor.getString(4),
				cursor.getString(5));
		return account;					
	}
	
// get all accounts
	public List<Account> getAllAccounts() {
		List<Account> accountList = new ArrayList<Account>();
		// Select all query
		String selectQuery = "SELECT * FROM " + TABLE_NAME_ACCOUNTS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Account account = new Account();
				account.setID(Integer.parseInt(cursor.getString(0)));
				account.setAccountName(cursor.getString(1));
				account.setAccountBalance(cursor.getString(2));
				account.setAccountMain(cursor.getString(3));
				account.setAccountCredit(cursor.getString(4));
				account.setAccountCreditLimit(cursor.getString(5));
				// add account to list
				accountList.add(account);
			} while (cursor.moveToNext());
		}
		return accountList;
	}

// get all account names
	public List<Account> getAllAccountNames() {
		List<Account> accountNameList = new ArrayList<Account>();
		// select query
		String selectQuery = "SELECT * FROM " + TABLE_NAME_ACCOUNTS;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// loop through all rows 
		if (cursor.moveToFirst()) {
			do {
				Account account = new Account();
				account.setAccountName(cursor.getString(1));
				// add account name to list
				accountNameList.add(account);
			} while (cursor.moveToNext());
		}
		return accountNameList;
	}
	

	
	// update single account
	public int updateAccount(Account account) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_ACCOUNT_NAME, account.getAccountName());
		values.put(COLUMN_NAME_ACCOUNT_BALANCE, account.getAccountBalance());
		values.put(COLUMN_NAME_ACCOUNT_MAIN, account.getAccountMain());
		values.put(COLUMN_NAME_ACCOUNT_CREDIT, account.getAccountCredit());
		values.put(COLUMN_NAME_ACCOUNT_CREDITLIMIT, account.getAccountCreditLimit());
		
		// update row
		return db.update(TABLE_NAME_ACCOUNTS, values, 
				COLUMN_NAME_ACCOUNT_ID + " = ?",
				new String[] { String.valueOf(account.getID()) });
	}
// deleting a singe account
	public void deleteAccount(Account account){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME_ACCOUNTS, COLUMN_NAME_ACCOUNT_ID + " = ?", 
				new String[] { String.valueOf(account.getID()) });
		db.close();
	}
	
// get account count
	public int getAccountsCount() {
		String countQuery = "SELECT * FROM " + TABLE_NAME_ACCOUNTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		return cursor.getCount();
	}
	
}
















