package com.nobleness.pigubank;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Matthew on 17/03/2015.
 */
public class BudgetFragmentListView extends ListFragment {

    private ListView mListView;
    private BudgetFragmentArrayAdapter mBgtFragArrayAdapter;
    private ArrayList<Integer> aListBgtId;
    private ArrayList<String> aListBgtName;
    private ArrayList<String> aListBgtLimit;
    private ArrayList<String> aListBgtSpent;
    private ArrayList<String> aListBgtReset;


    private ArrayList<String> aListBgtFreq;
    private ArrayList<String> aListBgtFreqType;
    private ArrayList<String> aListBgtFreqWeekDay;
    private ArrayList<String> aListBgtFreqMonthDate;
    private ArrayList<String> aListBgtFreqMonthDateBeforeAfter;
    private ArrayList<String> aListBgtFreqMonthWeek;
    private ArrayList<String> aListBgtFreqMonthWeekDay;
    private ArrayList<String> aListBgtFreqYearMonthDate;
    private ArrayList<String> aListBgtFreqYearMonth;

    public BudgetFragmentListView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_budget_view, container, false);
        mListView = (ListView) rootView.findViewById(android.R.id.list);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BudgetDatabaseHelper bgtDb = new BudgetDatabaseHelper(getActivity());
        aListBgtId = new ArrayList<Integer>();
        aListBgtName = new ArrayList<String>();
        aListBgtLimit = new ArrayList<String>();
        aListBgtSpent = new ArrayList<String>();
        aListBgtReset = new ArrayList<String>();
        aListBgtFreqMonthDateBeforeAfter = new ArrayList<String>();
        List<Budget> bgtList = bgtDb.getAllBudgets();
        for (Budget bgt : bgtList) {
            int bgtId = bgt.getID();
            aListBgtId.add(bgtId);
            String strBgtName = bgt.getBudgetName();
            aListBgtName.add(strBgtName);
            String strBgtLimit = bgt.getBudgetLimit();
            aListBgtLimit.add(strBgtLimit);
            String strBgtSpent = bgt.getBudgetAmountSpent();
            aListBgtSpent.add(strBgtSpent);
            String strBgtFregMonthDateBeforeAfter = bgt.getBudgetFreqMonthDateBeforeAfter();

            // read budget info to calc freq
            String freqType = bgt.getBudgetFreqType();
            Calendar c = Calendar.getInstance();
            c.getTime();

            int iCurDateOfMonth = c.get(Calendar.DAY_OF_MONTH);
            int iMaxDaysOfYear = c.getActualMaximum(Calendar.DAY_OF_YEAR);
            int iCurDayOfYear = c.get(Calendar.DAY_OF_YEAR);
            int iCurMonth = c.get(Calendar.MONTH);
            int iDaysCurMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
            int iCurWeekday = c.get(Calendar.DAY_OF_WEEK);

            int iBgtFreq = Integer.parseInt(bgt.getBudgetFreq());

            switch (Integer.parseInt(freqType)) {
                case 0:
                    String sFreqNotSet = "Budget Frequency not set";
                    aListBgtReset.add(sFreqNotSet);
                    break;
                case 1:
                    String strBgtFreqWeekday = bgt.getBudgetFreqWeekDay();
                    int freqMultiplier = 0;
                    if (strBgtFreqWeekday == "0" || iBgtFreq == 0) {
                        String sFreqWeekNotSet = "Budget frequency by weekday not set";
                        aListBgtReset.add(sFreqWeekNotSet);
                    } else {

                        if (iBgtFreq > 1) {
                            freqMultiplier = (iBgtFreq - 1) * 7;
                        }

                        int iCurDayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                        int iDayOfWeek = Integer.parseInt(strBgtFreqWeekday);
                            Log.i(PIGuActivity.APP_LOG,"iCurDayOfWeek: " + iCurDayOfWeek +
                                "\niDayOfWeek: " + iDayOfWeek );
                        if ((iDayOfWeek - iCurDayOfWeek) < 0) {
                            String sResetInX = "Budget resets in " +
                                    ((iDayOfWeek - iCurDayOfWeek) + 7 + freqMultiplier)
                                    + " Days";
                            aListBgtReset.add(sResetInX);
                        } else if ((iDayOfWeek - iCurDayOfWeek) == 0 && freqMultiplier > 0) {
                            String sResetToday = "Budget resets today";
                            aListBgtReset.add(sResetToday);
                        } else if ((iDayOfWeek - iCurDayOfWeek) == 1 && freqMultiplier > 0) {
                            String sResetIn1 = "Budget resets in tomorrow";
                            aListBgtReset.add(sResetIn1);
                        } else {
                            String sResetInX = "Budget resets in " +
                                    ((iDayOfWeek - iCurDayOfWeek) + freqMultiplier)
                                    + " Days";
                            aListBgtReset.add(sResetInX);
                        }
                    }
                    break;
                case 2:
                    String strBgtFreqMonthDate = bgt.getBudgetFreqMonthDate();
                    String strBgtFreqMonthWeek = bgt.getBudgetFreqMonthWeek();

                    int iFreqDateOfMonth = Integer.parseInt(strBgtFreqMonthDate);

                    String sBgtResetDayFormat = "0";
                    String sBgtResetMonthFormat = "0";
                    String sBgtResetYearFormat = "0";

                    String sDayButtonBoolean = bgt.getBudgetFreqMonthDayButtonBole();
                    String sDateButtonBoolean = bgt.getBudgetFreqMonthDateButtonBole();
                    Boolean bDayButtonBoolean = false;
                    Boolean bDateButtonBoolean = false;
                    if (sDayButtonBoolean.contains("true")) {
                        bDayButtonBoolean = true;
                    }
                    if (sDateButtonBoolean.contains("true")) {
                        bDateButtonBoolean = true;
                    }

                    Calendar cFreqMonth = Calendar.getInstance();
                    cFreqMonth.getTime();
                    cFreqMonth.set(Calendar.DAY_OF_MONTH, iFreqDateOfMonth);
                    int freqWeekday = cFreqMonth.get(Calendar.DAY_OF_WEEK);

                    if (bDateButtonBoolean != true && iBgtFreq != 0) {
                        // calculate when budget resets by month date
                        // LOG
                        Log.i(PIGuActivity.APP_LOG, "Current date in calendar: " + c);
                        Log.i(PIGuActivity.APP_LOG,
                                "Printing Freq Month week spinner selection: " +
                                strBgtFreqMonthWeek);
                        Log.i(PIGuActivity.APP_LOG,
                                "Printing Freq Month date spinner selection: " +
                                strBgtFreqMonthDate);
                        Log.i(PIGuActivity.APP_LOG,
                                "Printing iCurDateOfMonth: " + iCurDateOfMonth);
                        Log.i(PIGuActivity.APP_LOG,
                                "Printing iFreqDateOfMonth: " + iFreqDateOfMonth);
                        Log.i(PIGuActivity.APP_LOG,
                                "Number of days in current month: " + iDaysCurMonth);
                        //Log.i(PIGuActivity.APP_LOG,
                          //      "Weekday of freq date" + freqWeekday);
                        Log.i(PIGuActivity.APP_LOG,
                                "printing current Day of week: " + iCurWeekday);
                        // LOG

                        int iBgtResets;
                        int iAddMonths = 0;
                        try {

                            if (iBgtFreq < 2 &&
                                    iCurDateOfMonth < iFreqDateOfMonth) {
                                iBgtResets = iFreqDateOfMonth - iCurDateOfMonth;
                                iAddMonths = 1;
                            } else {
                                iBgtResets = (iDaysCurMonth - iCurDateOfMonth)
                                        + iFreqDateOfMonth;
                                iAddMonths = 2;
                            }
                            if (iBgtFreq > 2) {
                                for (int i = 1; i != (iBgtFreq - 1); i++) {
                                    cFreqMonth.add(Calendar.MONTH, 1);
                                    int iAddToBgtRests =
                                            cFreqMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
                                    iBgtResets = iBgtResets + iAddToBgtRests;
                                }
                                cFreqMonth.add(Calendar.MONTH, 1);
                                iAddMonths = iBgtFreq;
                            } else {
                            // Do nothing
                            }
                            //log
                            Log.i(PIGuActivity.APP_LOG, "cFreqMonth: " + cFreqMonth);
                            switch (Integer.parseInt(strBgtFregMonthDateBeforeAfter)) {
                                case 0:
                                    //do nothing to iFreqDateOfMonth
                                    break;
                                case 1:
                                    //change to first weekday before date
                                    if (freqWeekday == 7) {
                                        iBgtResets = iBgtResets -1;
                                        cFreqMonth.add(Calendar.DAY_OF_MONTH, -1);
                                    } else if (freqWeekday == 1) {
                                        iBgtResets = iBgtResets -2;
                                        cFreqMonth.add(Calendar.DAY_OF_MONTH, -2);
                                    }

                                    break;
                                case 2:
                                    //change to first weekday after date
                                    if (freqWeekday == 7) {
                                        iBgtResets = iBgtResets + 2;
                                        cFreqMonth.add(Calendar.DAY_OF_MONTH, 2);
                                    } else if (freqWeekday == 1) {
                                        iBgtResets = iBgtResets + 1;
                                        cFreqMonth.add(Calendar.DAY_OF_MONTH, 1);
                                    }
                                    break;
                            }

                            Log.i(PIGuActivity.APP_LOG, "cFreqMonth date budget resets: /n" +
                                cFreqMonth
                            );

                            sBgtResetDayFormat =
                                    Integer.toString(
                                            cFreqMonth.get(
                                                    Calendar.DAY_OF_MONTH));
                            sBgtResetMonthFormat =
                                    Integer.toString(
                                            cFreqMonth.get(Calendar.MONTH) + iAddMonths);
                            sBgtResetYearFormat =
                                    Integer.toString(
                                            cFreqMonth.get(Calendar.YEAR));

                            String sBgtResets = Integer.toString(iBgtResets);
                                if (iBgtResets > 1) {
                                    String sResetInX = "Budget resets in " + sBgtResets + " Days" +
                                            " on: " +
                                            sBgtResetDayFormat +"/" + sBgtResetMonthFormat + "/"
                                            + sBgtResetYearFormat;
                                    aListBgtReset.add(sResetInX);
                                } else if (iBgtResets == 1) {
                                    String sResetIn1 = "Budget resets tomorrow" +
                                            " on: " +
                                            sBgtResetDayFormat +"/" + sBgtResetMonthFormat + "/"
                                            + sBgtResetYearFormat;
                                    aListBgtReset.add(sResetIn1);
                                } else if (iBgtResets == 0) {
                                    String sResetToday = "Budget reset today" +
                                            " on: " +
                                            sBgtResetDayFormat +"/" + sBgtResetMonthFormat + "/"
                                            + sBgtResetYearFormat;
                                    aListBgtReset.add(sResetToday);
                                }
                        } catch (Exception e) {
                            Log.e(PIGuActivity.APP_LOG, "Exception processing Month reset logic: "
                                    + e );
                        }

                    } else if (bDayButtonBoolean != true && iBgtFreq != 0) {
                        //
                        // calculate when budget resets by month week
                        //
                        //
                        int iBgtWeek = Integer.parseInt(bgt.getBudgetFreqMonthWeek());
                        int iBgtWeekday = Integer.parseInt(bgt.getBudgetFreqMonthWeekDay());
                        int iSum = 0;
                        int iSumSinceWDMatch = 0;
                        boolean bWDMatch = false;
                        boolean bMMaxMatch = false;

                        int iLoopFreq = 0;
                        int iLoopWeek = 0;
                        Calendar calDayOfMonth = Calendar.getInstance();
                        calDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
////////////////////////
                        while (true){
                            if (iLoopFreq > iBgtFreq){
                                Log.e(PIGuActivity.APP_LOG, "Error in loop, loop " +
                                                "frequency higher than budget frequency: \n"
                                                + "iLoopFreq: " + iLoopFreq + " \n"
                                                + "iBgtFreq: " + iBgtFreq
                                );
                                break;
                            }
                            int iWeekday = calDayOfMonth.get(Calendar.DAY_OF_WEEK);
                            int iMonth = calDayOfMonth.get(Calendar.MONTH);
                            int iDateOfMonth = calDayOfMonth.get(Calendar.DAY_OF_MONTH);
                            int iMaxDays = calDayOfMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
                            ///////////////////
                            Log.i(PIGuActivity.APP_LOG, "------------ \n");
                            Log.i(PIGuActivity.APP_LOG, "while iteration start");
                            Log.i(PIGuActivity.APP_LOG, "iDateOfMonth= " +
                                    iDateOfMonth);
                            Log.i (PIGuActivity.APP_LOG, "Actual Maximum days: " + iMaxDays);
                            Log.i(PIGuActivity.APP_LOG, "iSum= " +
                                    iSum);
                            Log.i(PIGuActivity.APP_LOG, "iSumSinceWDMatch= " +
                                    iSumSinceWDMatch);

                            Log.i(PIGuActivity.APP_LOG, "iLoopWeek= " +
                                    iLoopWeek);
                            Log.i(PIGuActivity.APP_LOG, "iBgtWeek= " +
                                    iBgtWeek);
                            Log.i(PIGuActivity.APP_LOG, "iLoopFreq= " +
                                    iLoopFreq);
                            Log.i(PIGuActivity.APP_LOG, "iBgtFreq= " +
                                    iBgtFreq);
                            ////////////////////
                            if (iWeekday == iBgtWeekday) {
                                iLoopWeek++;
                                iSumSinceWDMatch = 0;
                                bWDMatch = true;
                                if (iLoopWeek == iBgtWeek) {
                                    if (iCurDateOfMonth > iDateOfMonth &&
                                            iCurMonth == iMonth) {
                                        //do nothing
                                    } else {
                                        iLoopFreq++;
                                    }

                                    if (iLoopFreq == iBgtFreq){
                                            iSum++;
                                            Log.i(PIGuActivity.APP_LOG, "-------------- \n");
                                            Log.i(PIGuActivity.APP_LOG, "Break after " +
                                                    "iLoopFreq == iBgtFreq");
                                            Log.i(PIGuActivity.APP_LOG, "iSum= " +
                                                    iSum);
                                            Log.i(PIGuActivity.APP_LOG, "iSumSinceWDMatch= " +
                                                    iSumSinceWDMatch);

                                            Log.i(PIGuActivity.APP_LOG, "iLoopWeek= " +
                                                    iLoopWeek);
                                            Log.i(PIGuActivity.APP_LOG, "iBgtWeek= " +
                                                    iBgtWeek);
                                            Log.i(PIGuActivity.APP_LOG, "iLoopFreq= " +
                                                    iLoopFreq);
                                            Log.i(PIGuActivity.APP_LOG, "iBgtFreq= " +
                                                    iBgtFreq);
                                            Log.i(PIGuActivity.APP_LOG, "\n ----------------");

                                            sBgtResetDayFormat =
                                                    Integer.toString(
                                                            calDayOfMonth.get(
                                                                    Calendar.DAY_OF_MONTH));
                                            sBgtResetMonthFormat =
                                                    Integer.toString(
                                                            calDayOfMonth.get(Calendar.MONTH) + 1);
                                            sBgtResetYearFormat =
                                                    Integer.toString(
                                                            calDayOfMonth.get(Calendar.YEAR));

                                            break;
                                    }
                                }
                            }

                            if (iDateOfMonth == iMaxDays) {

                                if (iBgtWeek == 4) {
                                    if (iLoopWeek > 0 && iLoopWeek < 4) {
                                        iLoopFreq++;
                                    }
                                }
                                if (iBgtWeek == 5) {
                                    if (iLoopWeek > 0){
                                        iLoopFreq++;
                                    }
                                }
                                if (iLoopFreq == iBgtFreq) {
                                    iSum++;
                                    if (iSumSinceWDMatch < 7){
                                        iSum = iSum - iSumSinceWDMatch;
                                        calDayOfMonth.add(Calendar.DAY_OF_MONTH,
                                                -iSumSinceWDMatch);
                                    }

                                    Log.i(PIGuActivity.APP_LOG, "---------------- \n");
                                    Log.i(PIGuActivity.APP_LOG, "Break after " +
                                            "last week in month and iLoopFreq == iBgtFreq");
                                    Log.i(PIGuActivity.APP_LOG, "iSum= " +
                                            iSum);
                                    Log.i(PIGuActivity.APP_LOG, "iSumSinceWDMatch= " +
                                            iSumSinceWDMatch);
                                    Log.i(PIGuActivity.APP_LOG, "iLoopWeek= " +
                                            iLoopWeek);
                                    Log.i(PIGuActivity.APP_LOG, "iBgtWeek= " +
                                            iBgtWeek);
                                    Log.i(PIGuActivity.APP_LOG, "iLoopFreq= " +
                                            iLoopFreq);
                                    Log.i(PIGuActivity.APP_LOG, "iBgtFreq= " +
                                            iBgtFreq);
                                    Log.i(PIGuActivity.APP_LOG, "\n ----------------");
                                    sBgtResetDayFormat =
                                            Integer.toString(
                                                    calDayOfMonth.get(Calendar.DAY_OF_MONTH));
                                    sBgtResetMonthFormat =
                                            Integer.toString(
                                                    calDayOfMonth.get(Calendar.MONTH) + 1);
                                    sBgtResetYearFormat =
                                            Integer.toString(
                                                    calDayOfMonth.get(Calendar.YEAR));

                                    break;
                                }


                            Log.i(PIGuActivity.APP_LOG, "Before adding month: " +
                                    calDayOfMonth);

                            calDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
                            calDayOfMonth.add(Calendar.MONTH, 1);
                            calDayOfMonth.set(Calendar.DAY_OF_MONTH, 1);
                            iLoopWeek = 0;
                            iSumSinceWDMatch = 0;
                            bMMaxMatch = true;

                            Log.i(PIGuActivity.APP_LOG, "after adding month: " +
                                    calDayOfMonth);
                            }


                            // add to iteration count for count since last week
                            if (iSumSinceWDMatch > 0) {
                            iSumSinceWDMatch++;
                            }
                            if (bWDMatch == true) {
                                iSumSinceWDMatch++;
                                bWDMatch = false;
                            }
                            //iteration count of days in current month
                            if (bMMaxMatch == true) {
                                // iteration already set
                                bMMaxMatch = false;
                            } else {
                                calDayOfMonth.add(Calendar.DAY_OF_MONTH, 1);
                            }
                            // end of first loop add to iteration count
                            iSum++;


                            Log.i(PIGuActivity.APP_LOG, "while iteration end");

                            Log.i(PIGuActivity.APP_LOG, "iDateOfMonth= " +
                                    iDateOfMonth);
                            Log.i (PIGuActivity.APP_LOG, "Actual Maximum days: " + iMaxDays);
                            Log.i(PIGuActivity.APP_LOG, "iSum= " +
                                    iSum);
                            Log.i(PIGuActivity.APP_LOG, "iSumSinceWDMatch= " +
                                    iSumSinceWDMatch);

                            Log.i(PIGuActivity.APP_LOG, "iLoopWeek= " +
                                    iLoopWeek);
                            Log.i(PIGuActivity.APP_LOG, "iBgtWeek= " +
                                    iBgtWeek);
                            Log.i(PIGuActivity.APP_LOG, "iLoopFreq= " +
                                    iLoopFreq);
                            Log.i(PIGuActivity.APP_LOG, "iBgtFreq= " +
                                    iBgtFreq);
                            Log.i(PIGuActivity.APP_LOG, "\n ----------------");
                        }
////////////////////////
                        // calc when budget resets from iterSum

                        Log.i(PIGuActivity.APP_LOG,
                                "values before calculation: \n" +
                                "iSum: " + iSum + " \n" +
                                "iCurDateOfMonth: " + iCurDateOfMonth
                        );

                        int iTotalDays = iSum - iCurDateOfMonth;

                        String sBgtResets = Integer.toString(iTotalDays);
                        if (iTotalDays > 1) {
                            String sResetInX = "Budget resets in " + sBgtResets + " Days" +
                                    " on: " +
                                    sBgtResetDayFormat +"/" + sBgtResetMonthFormat + "/"
                                    + sBgtResetYearFormat;
                            aListBgtReset.add(sResetInX);
                        } else if (iTotalDays == 1) {
                            String sResetToday = "Budget resets tomorrow" +
                                    " on: " +
                                    sBgtResetDayFormat +"/" + sBgtResetMonthFormat + "/"
                                    + sBgtResetYearFormat;
                            aListBgtReset.add(sResetToday);
                        } else if (iTotalDays == 0) {
                            String sResetIn1 = "Budget reset today" +
                                    " on: " +
                                    sBgtResetDayFormat +"/" + sBgtResetMonthFormat + "/"
                                    + sBgtResetYearFormat;
                            aListBgtReset.add(sResetIn1);
                        } else {
                            // fault in calc
                            Log.i(PIGuActivity.APP_LOG, "Error in " +
                                    "BudgetFragmentListView: Budget rest " +
                                    "calculation is less than 0");
                        }
                    } else {
                        String sFreqMonthNotSet = "Budget frequency by month not set";
                        aListBgtReset.add(sFreqMonthNotSet);
                        }
                    break;
                case 3:
                    String strBgtFreqYearMonth = bgt.getBudgetFreqYearMonth();
                    if (strBgtFreqYearMonth != "0" || iBgtFreq != 0) {
////////////////////////////////////////////////////////////////////////////////////////////////////
                        // calculate when budget resets by year
                        //
                        //
                        int iSumYear = 0;

                        Calendar calDateOfYear = Calendar.getInstance();
                        int iBgtYearMonthDate = Integer.parseInt(bgt.getBudgetFreqYearMonthDate());
                        int iBgtYearMonth = Integer.parseInt(bgt.getBudgetFreqYearMonth()) - 1;
                        calDateOfYear.set(Calendar.MONTH, iBgtYearMonth);
                        calDateOfYear.set(Calendar.DAY_OF_MONTH, iBgtYearMonthDate);

                        if (iCurMonth > iBgtYearMonth ) {
                            calDateOfYear.add(Calendar.YEAR, iBgtFreq);
                        } else {
                            if (iBgtFreq == 1) {
                                 // do nothing
                            } else {
                                calDateOfYear.add(Calendar.YEAR, (iBgtFreq - 1));
                            }
                        }

                        iSumYear = iMaxDaysOfYear - iCurDayOfYear;
                        if (iBgtFreq > 1) {
                            for (int i = 0; i != iBgtFreq -1; i++) {
                                c.add(Calendar.YEAR, 1);
                                iSumYear = iSumYear +
                                        (c.getActualMaximum(Calendar.DAY_OF_YEAR));
                            }
                        }
                        int iBgtDaysOfYear  = calDateOfYear.get(Calendar.DAY_OF_YEAR);
                        iSumYear = iSumYear + iBgtDaysOfYear;

                        String sBgtResets = Integer.toString(iSumYear);
                        if (iSumYear < 0) {
                            Log.e(PIGuActivity.APP_LOG, "iSumYear less than zero: " + iSumYear);
                        } else if (iSumYear == 0) {
                            String sResetToday = "Budget reset today";
                            aListBgtReset.add(sResetToday);
                        } else if (iSumYear == 1) {
                            String sResetIn1 = "Budget resets tomorrow";
                            aListBgtReset.add(sResetIn1);
                        } else {
                            String sResetInX = "Budget resets in " + sBgtResets + " Days";
                            aListBgtReset.add(sResetInX);
                        }



                    } else {
                        String sFreqYearNotSet = "Budget frequency by year not set";
                        aListBgtReset.add(sFreqYearNotSet);
                    }
                    break;
            }

        }
        bgtDb.close();

        aListBgtId.toArray();
        aListBgtName.toArray();
        aListBgtLimit.toArray();
        aListBgtSpent.toArray();
        aListBgtReset.toArray();


        mBgtFragArrayAdapter = new BudgetFragmentArrayAdapter(getActivity(),
                aListBgtId,
                aListBgtName,
                aListBgtLimit,
                aListBgtSpent,
                aListBgtReset
            );


        mListView.setAdapter(mBgtFragArrayAdapter);

    }

}
