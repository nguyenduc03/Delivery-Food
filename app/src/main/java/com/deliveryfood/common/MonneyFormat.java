package com.deliveryfood.common;

import java.text.NumberFormat;
import java.util.Locale;

public class MonneyFormat {
    public static String formatMonney(Long monney) {
        Locale localeVN = new Locale("vi", "VN");
        return NumberFormat.getCurrencyInstance(localeVN).format(monney);
    }
}
