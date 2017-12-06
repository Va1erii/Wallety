package com.valeriipopov.wallety.MainActivityPack;

import java.io.Serializable;

/**
 * Created by werk on 05/12/2017.
 */

public class Item implements Serializable {
    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_INCOME = "income";
    public static final String TYPE_UNKNOWN = "unknown";

    private String mName;
    private double mPrice;
    private String mType;

    public Item (String name, double price, String type) {
        mName = name;
        mPrice = price;
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public double getPrice() {
        return mPrice;
    }
}
