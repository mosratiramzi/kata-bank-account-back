package com.rmosrati.bankaccount.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

     public static String convertDateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return simpleDateFormat.format(date);
    }
}
