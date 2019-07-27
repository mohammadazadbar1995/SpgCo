package com.spg.sgpco.utils;

import android.content.res.Resources;

import com.spg.sgpco.R;


public class Validation {


    //    public static String getDescription(String description, String title, boolean isRequired, Resources res) {
//        if (description == null || description.trim().isEmpty()) {
//            return isRequired ? res.getString(R.string.enter_title, title) : null;
//        }
//        if (!description.matches("^((([^\u003c\u003e%!@#$\u0026]){1,1000})|)$")) {
//            return res.getString(R.string.invalid_value, title);
//        }
//        return null;
//    }
//
//    public static String getNameError(String name, String title, boolean isRequired, Resources res) {
//        if (name == null || name.trim().isEmpty()) {
//            return isRequired ? res.getString(R.string.enter_title, title) : null;
//        }
//        if (!name.matches("^([آ-ی]|[A-Z]|[a-z]| ){1,50}$")) {
//            return res.getString(R.string.invalid_value, title);
//        }
//        return null;
//    }
//
//
    public static String getMobileError(String mobile, boolean isRequired, Resources res) {
        if (mobile == null || mobile.trim().isEmpty()) {
            return isRequired ? res.getString(R.string.enter_mobile) : null;
        }
        if (mobile.length() != 11) {
            return res.getString(R.string.invalid_mobile);
        }
        if (!mobile.matches("^([\u06F0]|[\u0660]|[0])+([\u06F9]|[\u0669]|[9])+(([\u06F0-\u06F9]|[\u0660-\u0669]|[0-9])){9}$")) {
            return res.getString(R.string.invalid_mobile);
        }
        return null;
    }

    public static String getPasswordError(String password, Boolean isRequired, Resources res) {
        if (password == null || password.trim().isEmpty()) {
            return isRequired ? res.getString(R.string.enter_password) : null;
        }
        if (password.length() < 6) {
            return res.getString(R.string.invalid_password);
        }
        return null;
    }

    public static String getNameError(String name, String title, boolean isRequired, Resources res) {
        if (name == null || name.trim().isEmpty()) {
            return isRequired ? res.getString(R.string.enter_title, title) : null;
        }
        if (!name.matches("^([آ-ی]|[A-Z]|[a-z]| ){1,50}$")) {
            return res.getString(R.string.invalid_value, title);
        }
        return null;
    }

    public static String getCodeVerify(String code, Boolean isRequired, Resources res) {
        if (code == null || code.trim().isEmpty()) {
            return isRequired ? res.getString(R.string.enter_code) : null;
        }

        if (code.length() < 5) {
            return res.getString(R.string.invalid_code);
        }
        return null;
    }

    public static String getNumberError(String text,String title,  Boolean isRequired, Resources res) {
        if (text == null || text.trim().isEmpty()) {
            return isRequired ? res.getString(R.string.enter_title, title) : null;
        }

        return null;
    }


//
//    public static String getPhoneErrore(String phone, String title, boolean isRequired, Resources res) {
//        if (phone == null || phone.trim().isEmpty()) {
//            return isRequired ? res.getString(R.string.enter_title, title) : null;
//        }
//        if (phone.length() < 3 || phone.length() > 15) {
//            return res.getString(R.string.invalid_value, title);
//        }
//        return null;
//    }
//
//    public static String getEmailError(String email, String title, boolean isRequired, Resources res) {
//        if (email == null || email.trim().isEmpty()) {
//            return isRequired ? res.getString(R.string.enter_title, title) : null;
//        }
//        if (!email.matches("^[a-z0-9+-]+(.[a-z0-9+-]+)@[a-z0-9-]+(.[a-z0-9-]+).([a-z]{2,4})$")) {
//            return res.getString(R.string.invalid_value, title);
//        }
//
//        return null;
//    }
//
//    public static String getAddressError(String address, String title, boolean isRequired, Resources res) {
//
//        if (address == null || address.trim().isEmpty()) {
//            return isRequired ? res.getString(R.string.enter_title, title) : null;
//        }
//        if (!address.matches("^([آ-ی]|[A-Z]|[a-z]|([\u06F0-\u06F9]|[\u0660-\u0669]|[0-9])|[-]|[_]|[,]|[،]|[.]|[/]|[(]|[)]|[ ]){1,200}$")) {
//            return res.getString(R.string.invalid_value, title);
//        }
//
//        return null;
//    }
//
//    public static String getArea(String area, String title, String unit, boolean isRequired, Resources res) {
//        if (area == null || area.trim().isEmpty()) {
//            return isRequired ? res.getString(R.string.enter_title, title) : null;
//        }
//
//        return Validation.numberValidation(res, area, false, 2, 99999999, unit, title, res.getString(R.string.invalid_value, title));
//
//    }
//
//    public static String getAge(String age, String title, String unit, boolean isRequired, Resources res) {
//
//        if (age == null || age.trim().isEmpty()) {
//            return isRequired ? res.getString(R.string.enter_title, title) : null;
//        }
//
//        return Validation.numberValidation(res, age, true, 0, 99, unit, title, res.getString(R.string.invalid_value, title));
//
//    }
//
//    public static String getCountRoom(String room, String title, boolean isRequired, Resources res) {
//
//        if (room == null || room.trim().isEmpty()) {
//            return isRequired ? res.getString(R.string.enter_title, title) : null;
//        }
//        return Validation.numberValidation(res, room, true, 0, 99, null, title, res.getString(R.string.invalid_value, title));
//    }
//
//
//    public static String getFloor(String floor, String title, boolean isRequired, Resources res) {
//
//        if (floor == null || floor.trim().isEmpty()) {
//            return isRequired ? res.getString(R.string.enter_title, title) : null;
//        }
//        return Validation.numberValidation(res, floor, true, 0, 99, null, title, res.getString(R.string.invalid_value, title));
//    }
//
//
//    public static String getPriceValidation(String price, String title, String unit, boolean isRequired, boolean zeroBase, Resources res) {
//
//        if (price == null || price.trim().isEmpty()) {
//            return isRequired ? res.getString(R.string.enter_title, title) : null;
//        }
//
//        return Validation.numberValidation(res, price, zeroBase, 1000, 999999999999L, unit, title, res.getString(R.string.invalid_value, title));
//
//    }
//
//
//    private static String numberValidation(Resources res, String text, boolean zeroBased, int minValue, long maxValue, String unit, String title, String error) {
//        try {
//
//            long number = Long.parseLong(text);
//            if (zeroBased && number == 0) {
//                return null;
//            }
//            if (number < minValue) {
//                String txt = title + " " + Constants.convertNumberToDecimal(minValue) + " " + unit;
//                return unit == null ? error : res.getString(R.string.the_minimum_price, txt);
//            }
//            if (number > maxValue) {
//                String txt = Constants.convertNumberToDecimal(maxValue) + " " + unit;
//                return unit == null ? error : res.getString(R.string.the_maximum_price, txt);
//            }
//
//        } catch (Exception ex) {
//            return error;
//        }
//        return null;
//
//    }

}
