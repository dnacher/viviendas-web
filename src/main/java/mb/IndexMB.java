/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mb;

import Constantes.Constantes;
import entities.Usuario;
import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import security.Security;
import service.UsuarioSessionBean;
import org.jboss.logging.Logger;

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
    
    @Inject
    UsuarioSessionBean usb;

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
                Usuario usu=usb.traerUsuarioXNombre(txtuser);
//                Viviendas.user = ub.traerUsuarioXNombre(nombre);
                if (usu != null) {
                    usu.setPassword(txtPassword);
                    if (usu.getNombre().equals(txtuser) && Security.verifyPassword(usu)) {
//                        ub = new UsuariosBean();
//                        Viviendas.listaPermisos = ub.TraePermisos(Viviendas.getTipoUsuario());
//                        ConfiguracionBean cb = new ConfiguracionBean();
//                        listaConfiguracion = cb.traerTodos();
                        System.out.println("correcto");
                        return 0;
                    } else {
                        System.out.println("Error");
                        return -1;
                    }
                } else {
                    System.out.println("Error");
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

}
