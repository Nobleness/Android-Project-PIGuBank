package com.nobleness.pigubank;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ListView;

import java.util.List;


/**
 * Created by Nobleness on 10/03/2015.
 */
public class BudgetFragmentAdapter extends FragmentStatePagerAdapter {
    private final int PAGE_COUNT = 1;

    public BudgetFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;  // return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        BudgetFragmentListView mBgtFragListView = new BudgetFragmentListView();
        return mBgtFragListView;
    }
}

