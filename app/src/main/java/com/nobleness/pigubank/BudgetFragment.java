package com.nobleness.pigubank;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
public class BudgetFragment extends Fragment {

    //newInstance constructor for creating fragment with arguments
	public static BudgetFragment newInstance() {
        BudgetFragment fragmentBudget = new BudgetFragment();
        return fragmentBudget;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
       public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_budget, container, false);
        BudgetFragmentAdapter mBudgetPageAdapter =
                new BudgetFragmentAdapter(getChildFragmentManager());
        ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.fragBgtVP);
        mViewPager.setAdapter(mBudgetPageAdapter);
        return rootView;
    }

}
