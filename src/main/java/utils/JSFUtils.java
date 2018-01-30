/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Nacher
 */
public class JSFUtils {   
    
    public static void mostrarMensaje(String mensaje, FacesMessage.Severity sev) {
        FacesMessage msg = new FacesMessage(sev, mensaje, mensaje);
        FacesContext.getCurrentInstance().addMessage(null, msg);     
    }

    public static void mostrarMensajes(List<String> mensajes, FacesMessage.Severity sev) {
        for (String mensaje : mensajes) {
            FacesMessage msg = new FacesMessage(sev, mensaje, null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public static void agregarError(String clientId, String mensaje, String detail) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, detail));
    }

    public static void agregarInfo(String clientId, String mensaje, String detail) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, detail));
    }

    public static void agregarWarn(String clientId, String mensaje, String detail) {
        FacesContext.getCurrentInstance().addMessage(clientId, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, detail));
    }
    
    /**
     * Dado el summary controla si el comienzo empieza con 'ERROR', 'WARN' o
     * 'INFO' y despliega el mensaje según uno de estas severidades..
     *
     * @param clientId
     * @param summary
     * @param detail
     */

    public static void removerMensages() {
        Iterator<FacesMessage> msgIterator = FacesContext.getCurrentInstance().getMessages();
        while (msgIterator.hasNext()) {
            msgIterator.next();
            msgIterator.remove();
        }
    } 

    public static void redirect(String go) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(go);
        } catch (IOException ex) {
            Logger.getLogger(JSFUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }
}
