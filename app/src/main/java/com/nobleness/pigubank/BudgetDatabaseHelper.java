package com.nobleness.pigubank;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BudgetDatabaseHelper extends SQLiteOpenHelper {
	
	static final int DATABASE_VERSION = 2;
	static final String DATABASE_NAME = "BudgetReader.db";
	
	static final String TABLE_NAME_BUDGETS = "budgets";
	
	static final String COLUMN_NAME_BUDGET_ID = "budgetid";
	static final String COLUMN_NAME_BUDGET_NAME = "budgetname";
	static final String COLUMN_NAME_BUDGET_LIMIT = "budgetlimit";
	static final String COLUMN_NAME_BUDGET_AMOUNT_SPENT = "budgetamountspent";
	static final String COLUMN_NAME_BUDGET_FREQ = "budgetfreq";
	static final String COLUMN_NAME_BUDGET_FREQTYPE = "budgetfreqtype";
	static final String COLUMN_NAME_BUDGET_FREQWEEKDAY = "budgetfreqweekday";
    static final String COLUMN_NAME_BUDGET_FREQMONTHBUTTONDATE = "budgetfreqmonthbuttondate";
    static final String COLUMN_NAME_BUDGET_FREQMONTHBUTTONDAY = "budgetfreqmonthbuttonday";
	static final String COLUMN_NAME_BUDGET_FREQMONTHDATE = "budgetfreqmonthdate";
	static final String COLUMN_NAME_BUDGET_FREQMONTHDATEBEFOREAFTER = "budgetfreqmonthdatebeforeafter";
	static final String COLUMN_NAME_BUDGET_FREQMONTHWEEK = "budgetfreqmonthweek";
	static final String COLUMN_NAME_BUDGET_FREQMONTHWEEKDAY = "budgetfreqmonthweekday";
	static final String COLUMN_NAME_BUDGET_FREQYEARMONTHDATE = "budgetfreqyearmonthdate";
	static final String COLUMN_NAME_BUDGET_FREQYEARMONTH = "budgetfreqyearday";
	
	
	//create tables
	final String SQL_CREATE_ENTRIES = 
			"CREATE TABLE " + TABLE_NAME_BUDGETS + "(" 
			+ COLUMN_NAME_BUDGET_ID + " INTEGER PRIMARY KEY,"
			+ COLUMN_NAME_BUDGET_NAME + " TEXT," 
			+ COLUMN_NAME_BUDGET_LIMIT + " TEXT,"
			+ COLUMN_NAME_BUDGET_AMOUNT_SPENT + " TEXT,"
			+ COLUMN_NAME_BUDGET_FREQ + " TEXT,"
			+ COLUMN_NAME_BUDGET_FREQTYPE + " TEXT,"
			+ COLUMN_NAME_BUDGET_FREQWEEKDAY + " TEXT,"
            + COLUMN_NAME_BUDGET_FREQMONTHBUTTONDATE + " TEXT,"
            + COLUMN_NAME_BUDGET_FREQMONTHBUTTONDAY + " TEXT,"
			+ COLUMN_NAME_BUDGET_FREQMONTHDATE + " TEXT,"
			+ COLUMN_NAME_BUDGET_FREQMONTHDATEBEFOREAFTER + " TEXT,"
			+ COLUMN_NAME_BUDGET_FREQMONTHWEEK + " TEXT,"
			+ COLUMN_NAME_BUDGET_FREQMONTHWEEKDAY + " TEXT,"
			+ COLUMN_NAME_BUDGET_FREQYEARMONTHDATE + " TEXT,"
			+ COLUMN_NAME_BUDGET_FREQYEARMONTH + " TEXT"
			+ ")";
	
	//drop table
	private static final String SQL_DELETE_ENTRIES = 
			"DROP TABLE IF EXISTS " + TABLE_NAME_BUDGETS;

	public BudgetDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null,DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_ENTRIES);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DELETE_ENTRIES);
		onCreate(db);
	}
	
//adding new budget
	public void addBudget(Budget budget) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_BUDGET_NAME, budget.getBudgetName());
		values.put(COLUMN_NAME_BUDGET_LIMIT, budget.getBudgetLimit());
		values.put(COLUMN_NAME_BUDGET_AMOUNT_SPENT, budget.getBudgetAmountSpent());
		values.put(COLUMN_NAME_BUDGET_FREQ, budget.getBudgetFreq());
		values.put(COLUMN_NAME_BUDGET_FREQTYPE, budget.getBudgetFreqType());
		values.put(COLUMN_NAME_BUDGET_FREQWEEKDAY, budget.getBudgetFreqWeekDay());
        values.put(COLUMN_NAME_BUDGET_FREQMONTHBUTTONDATE, budget.getBudgetFreqMonthDateButtonBole());
        values.put(COLUMN_NAME_BUDGET_FREQMONTHBUTTONDAY, budget.getBudgetFreqMonthDayButtonBole());
		values.put(COLUMN_NAME_BUDGET_FREQMONTHDATE, budget.getBudgetFreqMonthDate());
		values.put(COLUMN_NAME_BUDGET_FREQMONTHDATEBEFOREAFTER, budget.getBudgetFreqMonthDateBeforeAfter());
		values.put(COLUMN_NAME_BUDGET_FREQMONTHWEEK, budget.getBudgetFreqMonthWeek());
		values.put(COLUMN_NAME_BUDGET_FREQMONTHWEEKDAY, budget.getBudgetFreqMonthWeekDay());
		values.put(COLUMN_NAME_BUDGET_FREQYEARMONTHDATE, budget.getBudgetFreqYearMonthDate());
		values.put(COLUMN_NAME_BUDGET_FREQYEARMONTH, budget.getBudgetFreqYearMonth());
		
		db.insert(TABLE_NAME_BUDGETS, null, values);
		db.close();
	}
	
//get single budget 
	public Budget getBudget(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(
				TABLE_NAME_BUDGETS, new String[] { 
						COLUMN_NAME_BUDGET_ID,
						COLUMN_NAME_BUDGET_NAME,
						COLUMN_NAME_BUDGET_LIMIT,
						COLUMN_NAME_BUDGET_AMOUNT_SPENT,
						COLUMN_NAME_BUDGET_FREQ,
						COLUMN_NAME_BUDGET_FREQTYPE,
						COLUMN_NAME_BUDGET_FREQWEEKDAY,
                        COLUMN_NAME_BUDGET_FREQMONTHBUTTONDATE,
                        COLUMN_NAME_BUDGET_FREQMONTHBUTTONDAY,
						COLUMN_NAME_BUDGET_FREQMONTHDATE,
						COLUMN_NAME_BUDGET_FREQMONTHDATEBEFOREAFTER,
						COLUMN_NAME_BUDGET_FREQMONTHWEEK,
						COLUMN_NAME_BUDGET_FREQMONTHWEEKDAY,
						COLUMN_NAME_BUDGET_FREQYEARMONTHDATE,
						COLUMN_NAME_BUDGET_FREQYEARMONTH}, 
						COLUMN_NAME_BUDGET_ID + "=?",
						new String[] { String.valueOf(id) }, null,null,null,null);
		if (cursor != null)
			cursor.moveToFirst();
						
		Budget budget = new Budget(Integer.parseInt(
				cursor.getString(0)),
				cursor.getString(1), 
				cursor.getString(2),
				cursor.getString(3),
				cursor.getString(4),
				cursor.getString(5),
				cursor.getString(6),
				cursor.getString(7),
				cursor.getString(8),
				cursor.getString(9),
				cursor.getString(10),
				cursor.getString(11),
				cursor.getString(12),
                cursor.getString(13),
                cursor.getString(14));
		return budget;					
	}
	
// get all budgets
	public List<Budget> getAllBudgets() {
		List<Budget> budgetList = new ArrayList<Budget>();
		// Select all query
		String selectQuery = "SELECT * FROM " + TABLE_NAME_BUDGETS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Budget budget = new Budget();
				budget.setID(Integer.parseInt(cursor.getString(0)));
				budget.setBudgetName(cursor.getString(1));
				budget.setBudgetLimit(cursor.getString(2));
				budget.setBudgetAmountSpent(cursor.getString(3));
				budget.setBudgetFreq(cursor.getString(4));
				budget.setBudgetFreqType(cursor.getString(5));
				budget.setBudgetFreqWeekDay(cursor.getString(6));
				budget.setBudgetFreqMonthDateButtonBole(cursor.getString(7));
                budget.setBudgetFreqMonthDayButtonBole(cursor.getString(8));
                budget.setBudgetFreqMonthDate(cursor.getString(9));
				budget.setBudgetFreqMonthDateBeforeAfter(cursor.getString(10));
				budget.setBudgetFreqMonthWeek(cursor.getString(11));
				budget.setBudgetFreqMonthWeekDay(cursor.getString(12));
				budget.setBudgetFreqYearMonthDate(cursor.getString(13));
				budget.setBudgetFreqYearMonth(cursor.getString(14));
				// add budget to list
				budgetList.add(budget);
			} while (cursor.moveToNext());
		}
		return budgetList;
	}

// get all budget names
	public List<Budget> getAllBudgetNames() {
		List<Budget> budgetNameList = new ArrayList<Budget>();
		// select query
		String selectQuery = "SELECT * FROM " + TABLE_NAME_BUDGETS;
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		// loop through all rows 
		if (cursor.moveToFirst()) {
			do {
				Budget budget = new Budget();
				budget.setBudgetName(cursor.getString(1));
				// add budget name to list
				budgetNameList.add(budget);
			} while (cursor.moveToNext());
		}
		return budgetNameList;
	}
	

	
	// update single budget
	public int updateBudget(Budget budget) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_BUDGET_NAME, budget.getBudgetName());
		values.put(COLUMN_NAME_BUDGET_LIMIT, budget.getBudgetLimit());
		values.put(COLUMN_NAME_BUDGET_AMOUNT_SPENT, budget.getBudgetAmountSpent());
		values.put(COLUMN_NAME_BUDGET_FREQ, budget.getBudgetFreq());
		values.put(COLUMN_NAME_BUDGET_FREQTYPE, budget.getBudgetFreqType());
		values.put(COLUMN_NAME_BUDGET_FREQWEEKDAY, budget.getBudgetFreqWeekDay());
        values.put(COLUMN_NAME_BUDGET_FREQMONTHBUTTONDATE, budget.getBudgetFreqMonthDateButtonBole());
        values.put(COLUMN_NAME_BUDGET_FREQMONTHBUTTONDAY, budget.getBudgetFreqMonthDayButtonBole());
		values.put(COLUMN_NAME_BUDGET_FREQMONTHDATE, budget.getBudgetFreqMonthDate());
		values.put(COLUMN_NAME_BUDGET_FREQMONTHDATEBEFOREAFTER, budget.getBudgetFreqMonthDateBeforeAfter());
		values.put(COLUMN_NAME_BUDGET_FREQMONTHWEEK, budget.getBudgetFreqMonthWeek());
		values.put(COLUMN_NAME_BUDGET_FREQMONTHWEEKDAY, budget.getBudgetFreqMonthWeekDay());
		values.put(COLUMN_NAME_BUDGET_FREQYEARMONTHDATE, budget.getBudgetFreqYearMonthDate());
		values.put(COLUMN_NAME_BUDGET_FREQYEARMONTH, budget.getBudgetFreqYearMonth());
		
		// update row
		return db.update(TABLE_NAME_BUDGETS, values, 
				COLUMN_NAME_BUDGET_ID + " = ?",
				new String[] { String.valueOf(budget.getID()) });
	}
// deleting a singe budget
	public void deleteBudget(Budget budget){
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NAME_BUDGETS, COLUMN_NAME_BUDGET_ID + " = ?", 
				new String[] { String.valueOf(budget.getID()) });
		db.close();
	}
	
// get budget count
	public int getBudgetsCount() {
		String countQuery = "SELECT * FROM " + TABLE_NAME_BUDGETS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		cursor.close();
		return cursor.getCount();
	}

}
