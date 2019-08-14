package com.exchange_v1.app.utils;

import java.util.Collection;

/**
 * Created by huanghh on 2017/4/13.
 */

public class CollectionsUtils {
    public static boolean isEmpty(Collection c){
        boolean isEmpty = false;
        if (c==null) {
            return true;
        }
        return c.isEmpty();
    };
}
