/*
 *  Nombre del clinete
 *  Sistema de Gestión
 *  Desarrollado por Sofis Solutions
 */
package mensajes;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 *
 * @author Usuario
 */
public class Etiquetas {
    
    private static final String ETIQUETAS = "mensajes.etiquetas";
    
    private static ResourceBundle bundle = ResourceBundle.getBundle(ETIQUETAS, new Locale("es"));
    private static ResourceBundle bundlePT = ResourceBundle.getBundle(ETIQUETAS, new Locale("pt"));

    
    public static String getValue(String key) {
        if (bundle.containsKey(key)){
            return bundle.getString(key);
        }else{
            return "?"+key+"?";
        }
    }
    
    public static String getValue(String key, Locale loc) {
        if(loc.getLanguage().equalsIgnoreCase("pt")){
            if (bundlePT.containsKey(key)){
                return bundlePT.getString(key);
            }else{
                return "?"+key+"?PT";
            }
        }else{
            if (bundle.containsKey(key)){
                return bundle.getString(key);
            }else{
                return "?"+key+"?";
            }
        }
        
    }
    
    public static boolean containsKey(String key){
        return bundle.containsKey(key);
    }
}
