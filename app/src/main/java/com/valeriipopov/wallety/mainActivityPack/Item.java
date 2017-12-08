package com.valeriipopov.wallety.mainActivityPack;

import java.io.Serializable;

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
