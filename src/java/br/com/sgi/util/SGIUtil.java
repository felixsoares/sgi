package br.com.sgi.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SGIUtil {
    public static Date formataData(String data) throws Exception {
        if (data == null || data.equals("")) {
            return null;
        }
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            date = (java.util.Date) formatter.parse(data);
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }
    
    public static Double getPrecisao(Double valor){
        BigDecimal bd = new BigDecimal(valor).setScale(2, RoundingMode.HALF_EVEN);
        
        return bd.doubleValue();
    }
    
    public static String getPagoRecebidoNome(Boolean pagoRecebido){
        if(pagoRecebido){
            return "Sim";
        }else{
            return "Não";
        }
    }
    
    public static Date getPrimeiraHoraDia(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    
    public static Date getUltimaHoraDia(Date data) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
    
    public static String getNomeMes(Date data){
        Locale locale = Locale.getDefault();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);

        return setPrimeiraLetraUpperCase(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale));
    }
    
    public static String getNomeMesComAno(Date data){
        Locale locale = Locale.getDefault();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        int year = calendar.get(Calendar.YEAR);
        
        return setPrimeiraLetraUpperCase(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)) + " de " + year;
    }

    public static String setPrimeiraLetraUpperCase(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }
}
