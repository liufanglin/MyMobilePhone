package com.shopex.phone.phone.utils;


import com.shopex.phone.phone.library.view.ChartData;

import java.util.Comparator;

/**
 * Created by hz on 2016/3/14.
 */

    public class ChartComparator implements Comparator<ChartData> {
        public int compare(ChartData o1, ChartData o2) {
            return (o1.getMoney() < o2.getMoney() ? -1 : (o1.getMoney() == o2.getMoney() ? 0 : 1));
        }
}
