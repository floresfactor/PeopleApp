package com.uach.peopleapp.utilidades;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Utilidades sobre datos temporales
 */
public class UTiempo {
    public static long obtenerTiempoEnMS() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String obtenerTiempo() {
        Date date = GregorianCalendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
