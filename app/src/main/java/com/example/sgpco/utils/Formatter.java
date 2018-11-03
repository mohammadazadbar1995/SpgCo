package com.example.sgpco.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Formatter {

    private static Formatter sFormatter;
    private DecimalFormat df;
    private boolean hasFractionalPart;
    private DecimalFormat dfnd;

    public Formatter() {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Constants.language.getLocale());
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        df = new DecimalFormat("#,###.##", otherSymbols);
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###",otherSymbols);
        hasFractionalPart = false;
    }


    public static DecimalFormat getInstance() {
        if (sFormatter == null) {
            sFormatter = new Formatter();
        }
        return sFormatter.df;
    }

    public DecimalFormat df() {
        return df;
    }

    public DecimalFormat dfnf() {
        return dfnd;
    }

    public boolean isHasFractionalPart() {
        return hasFractionalPart;
    }

}
