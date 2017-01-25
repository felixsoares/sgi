package br.com.sgi.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.beanutils.Converter;

public class DateConverter implements Converter {
    
    public Object convert(Class pClasse, Object data) {
        try {
            return new SimpleDateFormat( "dd/MM/yyyy" ).parse( (String)data );
        } catch (ParseException ex) {
            ex.printStackTrace();
            return new Date();
        }
    }

}
