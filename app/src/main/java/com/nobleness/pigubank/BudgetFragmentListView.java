package com.nobleness.pigubank;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
            //aListBgtFreqMonthDateBeforeAfter.add(strBgtFregMonthDateBeforeAfter);

            // read budget info to calc freq
            String freqType = bgt.getBudgetFreqType();
            Calendar c = Calendar.getInstance();
            c.getTime();

            int iBgtFreq = Integer.parseInt(bgt.getBudgetFreq());

            switch (Integer.parseInt(freqType)) {
                case 0:
                    String sFreqNotSet = "Budget Frequency not set";
                    aListBgtReset.add(sFreqNotSet);
                    break;
                case 1:
                    String strBgtFreqWeekday = bgt.getBudgetFreqWeekDay();
                    int freqMultiplier = 0;
                    if (strBgtFreqWeekday == "0") {
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
                    int iCurDateOfMonth = c.get(Calendar.DAY_OF_MONTH);
                    int iDaysCurMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                    int iCurWeekday = c.get(Calendar.DAY_OF_WEEK);
                    int iFreqDateOfMonth = Integer.parseInt(strBgtFreqMonthDate);

                    Calendar cFreqMonth = Calendar.getInstance();
                    cFreqMonth.getTime();
                    cFreqMonth.set(Calendar.DAY_OF_MONTH, iFreqDateOfMonth);
                    int freqWeekday = cFreqMonth.get(Calendar.DAY_OF_WEEK);

                    if (strBgtFreqMonthDate != "0") {
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
                        try {

                            if (iBgtFreq < 2 &&
                                    iCurDateOfMonth < iFreqDateOfMonth) {
                                iBgtResets = iFreqDateOfMonth - iCurDateOfMonth;

                            } else {
                                iBgtResets = (iDaysCurMonth - iCurDateOfMonth)
                                        + iFreqDateOfMonth;
                            }
                            if (iBgtFreq > 2) {
                                for (int i = 1; i != (iBgtFreq - 1); i++) {
                                    cFreqMonth.add(Calendar.MONTH, 1);
                                    int iAddToBgtRests =
                                            cFreqMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
                                    iBgtResets = iBgtResets + iAddToBgtRests;
                                }
                                cFreqMonth.add(Calendar.MONTH, 1);
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
                                    } else if (freqWeekday == 1) {
                                        iBgtResets = iBgtResets -2;
                                    }

                                    break;
                                case 2:
                                    //change to first weekday after date
                                    if (freqWeekday == 7) {
                                        iBgtResets = iBgtResets + 2;
                                    } else if (freqWeekday == 1) {
                                        iBgtResets = iBgtResets + 1;
                                    }
                                    break;
                            }

                            String sBgtResets = Integer.toString(iBgtResets);
                                if (iBgtResets > 0) {
                                    String sResetInX = "Budget resets in " + sBgtResets + " Days";
                                    aListBgtReset.add(sResetInX);
                                } else if (iBgtResets == 1) {
                                    String sResetToday = "Budget resets tomorrow";
                                    aListBgtReset.add(sResetToday);
                                } else if (iBgtResets == 0) {
                                    String sResetIn1 = "Budget reset today";
                                    aListBgtReset.add(sResetIn1);
                                }
                        } catch (Exception e) {
                            Log.e(PIGuActivity.APP_LOG, "Exception processing Month reset logic: "
                                    + e );
                        }

                    } else if (strBgtFreqMonthWeek != "0") {
                        //
                        // calculate when budget resets by month week
                        //
                        //


                    } else {
                        String sFreqMonthNotSet = "Budget frequency by month not set";
                        aListBgtReset.add(sFreqMonthNotSet);
                    }
                    break;
                case 3:
                    String strBgtFreqYearMonth = bgt.getBudgetFreqYearMonth();
                    if (strBgtFreqYearMonth != "0") {
                        //
                        // calculate when budget resets by year
                        //
                        //
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
