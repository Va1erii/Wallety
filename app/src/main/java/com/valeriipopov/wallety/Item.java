package com.valeriipopov.wallety;

/**
 * Created by werk on 05/12/2017.
 */

public class Item {
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
