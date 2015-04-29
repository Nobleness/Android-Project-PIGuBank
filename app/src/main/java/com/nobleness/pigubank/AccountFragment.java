package com.nobleness.pigubank;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Nobleness on 25/02/2015.
 */
public class AccountFragment extends Fragment {

    private String title;
    private int page;

    public static AccountFragment newInstance() {
        AccountFragment fragmentAccount = new AccountFragment();
        Bundle args = new Bundle();
        return fragmentAccount;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        return view;
    }



}
