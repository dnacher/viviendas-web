/*
 * Nombre del clinete
 * Sistema de Gestión
 * Desarrollado por Sofis Solutions
 */
package service;

import java.lang.reflect.Field;

/**
 *
 * @author Usuario
 */
public class ReflectionUtils {
    
    public static Field obtenerCampoAnotado(Class o, Class claseAnotacion) {
        Field fieldSeleccionado = null;
        for (Field field : o.getDeclaredFields()) {
            field.setAccessible(true); // You might want to set modifier to public first.
            if (field.isAnnotationPresent(claseAnotacion)) {
                fieldSeleccionado = field;
                break;
            }


        }
        return fieldSeleccionado;
    }
    
    
    public static String obtenerNombreCampoAnotado(Class o, Class claseAnotacion) {
        Field f = obtenerCampoAnotado(o,claseAnotacion);
        if (f!=null) {
            return f.getName();
        }
        return null;
    }
    
}
