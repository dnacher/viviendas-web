/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import Constantes.Constantes;
import Mensajes.Mensajes;
import entities.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import security.Security;
import service.UsuarioSessionBean;
import org.jboss.logging.Logger;
import utils.JSFUtils;
import java.util.Locale;
import javax.faces.context.FacesContext;
import service.UsuarioFacadeLocal;

/**
 *
 * @author Nacher
 */
@ManagedBean(name = "indexMB")
@ApplicationScoped
@Named
public class IndexMB implements Serializable {

    private String txtuser;
    private String txtPassword;
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    private List<String> test = new ArrayList<>();
    
    @Inject
    UsuarioSessionBean usb;
    
    @Inject
    UsuarioFacadeLocal ufl;
    
    public void init(){
        test.add("test1");
        test.add("test2");
        test.add("test3");
        test.add("test4");
        test.add("test5");
    }

    public void login() {
        validationLogin();
    }
    

    public int validationLogin() {
        if (txtuser == null || txtuser.isEmpty()) {
            return Constantes.ERROR_FALTA_USUARIO;
        } else if (txtPassword == null || txtPassword.isEmpty()) {
            return Constantes.ERROR_FALTA_PASS;
        } else {
            try {                
                Usuario usu=ufl.traerUsuarioXNombre(txtuser);
                if (usu != null) {
                    usu.setPassword(txtPassword);
                    if (usu.getNombre().equals(txtuser) && Security.verifyPassword(usu)) {
                        JSFUtils.agregarInfo(null, Mensajes.LOGIN_CORRECTO, "");                        
                        return 0;
                    } else {
                        JSFUtils.agregarInfo(null, Mensajes.LOGIN_ERROR, ""); 
                        return -1;
                    }
                } else {
                    JSFUtils.agregarInfo(null, Mensajes.LOGIN_ERROR, ""); 
                        return -1;
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Logger.Level.FATAL, ex, ex);
//                return new Notification(ConstantesEtiquetas.ERROR, ConstantesErrores.CONTACTE_ADMINISTRADOR, Notification.ERROR_ICON);
            }
        }
        return 0;
    }

    public String getTxtuser() {
        return txtuser;
    }

    public void setTxtuser(String txtuser) {
        this.txtuser = txtuser;
    }

    public String getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword = txtPassword;
    }

    public List<String> getTest() {
        return test;
    }

    public void setTest(List<String> test) {
        this.test = test;
    }
    
    

}
