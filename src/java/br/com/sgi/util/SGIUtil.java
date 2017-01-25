package br.com.sgi.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
}
