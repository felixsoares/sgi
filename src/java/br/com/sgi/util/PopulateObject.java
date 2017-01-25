package br.com.sgi.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

public class PopulateObject {

    public static Object createObjectBusiness(Object obj, HttpServletRequest request) {
        ConvertUtils.register(new DateConverter(), Date.class);

        try {
            BeanUtils.populate(obj, request.getParameterMap());
        } catch (IllegalAccessException ex) {
            Logger.getLogger(PopulateObject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(PopulateObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return obj;
    }
}
