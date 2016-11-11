package com.campbellapps.christiancampbell.peoplemonv1.Components;

import java.util.Locale;

/**
 * Created by christiancampbell on 11/7/16.
 */

public class Utils {
    public static String formatDouble(Double number){
        String prefix = number < 0 ? "-$" : "$"; // if number is less than zero, equals -$, else $
        return prefix + String.format(Locale.US, "%.2f", Math.abs(number)); // rounds to two decimals.
        //$100.00 or -$9.87 will be returned

    }
}
