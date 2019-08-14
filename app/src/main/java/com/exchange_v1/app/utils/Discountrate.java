package com.exchange_v1.app.utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huanghh on 2016/12/2.
 */
public class Discountrate {

    public static final Class thisC = Discountrate.class;
    public static final String TAG = thisC.getSimpleName();

    private int cashperiods;//充值月数
    private String good_desc;//充值描述
    private double discountrate;//充值折扣
    private String cashstartday;

    public Discountrate(JSONObject o) throws JSONException {
        cashperiods = o.getInt("cashperiods");
        good_desc = o.getString("good_desc");
        discountrate = o.getDouble("discountrate");
        cashstartday = o.getString("cashstartday");
    }

    public int getCashperiods() {
        return cashperiods;
    }

    public String getCashstartday() {
        return cashstartday;
    }

    public double getDiscountrate() {
        return discountrate;
    }

    public String getGood_desc() {
        return good_desc;
    }

    @Override
    public boolean equals(Object o) {
        return cashperiods == ((Discountrate)o).getCashperiods();
    }
}
