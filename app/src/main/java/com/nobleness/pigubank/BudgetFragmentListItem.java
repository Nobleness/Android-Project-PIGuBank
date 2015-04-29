package com.nobleness.pigubank;

import java.util.ArrayList;

/**
 * Created by Matthew on 16/03/2015.
 */
public class BudgetFragmentListItem {
    public final Integer aListBgtId;
    public final String aListBgtName;
    public final String aListBgtLimit;
    public final String aListBgtSpent;
    public final String aListBgtReset;

    public BudgetFragmentListItem(
            int aListBgtId,
            String aListBgtName,
            String aListBgtLimit,
            String aListBgtSpent,
            String aListBgtReset
            ) {
        this.aListBgtId = aListBgtId;
        this.aListBgtName = aListBgtName;
        this.aListBgtLimit = aListBgtLimit;
        this.aListBgtSpent = aListBgtSpent;
        this.aListBgtReset = aListBgtReset;
    }

}

