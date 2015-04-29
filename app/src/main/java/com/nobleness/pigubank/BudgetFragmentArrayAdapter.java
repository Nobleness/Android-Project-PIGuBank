package com.nobleness.pigubank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew on 18/03/2015.
 */
public class BudgetFragmentArrayAdapter extends ArrayAdapter<String> {

    // declare variables
    private final Context context;
    private final ArrayList<Integer> aListBgtId;
    private final ArrayList<String> aListBgtName;
    private final ArrayList<String> aListBgtLimit;
    private final ArrayList<String> aListBgtSpent;
    private final ArrayList<String> aListBgtReset;
    //LayoutInflater inflater;

    public BudgetFragmentArrayAdapter(
            Context context,
            ArrayList<Integer> aListBgtId,
            ArrayList<String> aListBgtName,
            ArrayList<String> aListBgtLimit,
            ArrayList<String> aListBgtSpent,
            ArrayList<String> aListBgtReset) {
        super(context, R.layout.fragment_budget_view_item, aListBgtName);
        this.context = context;
        this.aListBgtId = aListBgtId;
        this.aListBgtName = aListBgtName;
        this.aListBgtLimit = aListBgtLimit;
        this.aListBgtSpent = aListBgtSpent;
        this.aListBgtReset = aListBgtReset;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) convertView = inflater.inflate(
                R.layout.fragment_budget_view_item, parent, false);

        try {

            //TextView tvId = (TextView) convertView.findViewById(R.id.fragBgtViewTVbgtId);
           // tvId.setText(aListBgtId.get(position).toString());
            TextView tvName = (TextView) convertView.findViewById(R.id.fragBgtViewTVname);
            tvName.setText(aListBgtName.get(position));
            TextView tvLimit = (TextView) convertView.findViewById(R.id.fragBgtViewTVbgtLimit);
            tvLimit.setText(aListBgtLimit.get(position));
            TextView tvSpent = (TextView) convertView.findViewById(R.id.fragBgtViewTVbgtSpent);
            tvSpent.setText(aListBgtSpent.get(position));
            TextView tvReset = (TextView) convertView.findViewById(R.id.fragBgtViewTVbgtResets);
            tvReset.setText(aListBgtReset.get(position));
        } catch (Exception e) {
            Log.e(PIGuActivity.APP_LOG, "Unable to load array list items \n "
                    + aListBgtId
                    + "\n" + aListBgtName
                    + "\n" + aListBgtLimit
                    + "\n" + aListBgtSpent
                    + "\n" + aListBgtReset
            );
        }


        return convertView;
    }



}
