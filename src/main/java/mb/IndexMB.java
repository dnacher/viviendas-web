package mb;

import constantes.Constantes;
import mensajes.Mensajes;
import entities.Usuario;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import security.Security;
import service.UsuarioSessionBean;
import org.jboss.logging.Logger;
import utils.JSFUtils;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import service.UsuarioFacadeLocal;

/**
 *
 * @author Nacher
 */
@ManagedBean(name = "indexMB")
@SessionScoped
@Named
public class IndexMB implements Serializable {

    private String txtuser;
    private String txtPassword;
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
    private Usuario usu=null;
    private FacesContext faces = FacesContext.getCurrentInstance();

    @Inject
    UsuarioSessionBean usb;

    @Inject
    UsuarioFacadeLocal ufl;

    public void init() {
    }

    public String login() {
        return validationLogin();
    }

    public String validationLogin() {
        String redireccion;
        if (txtuser == null || txtuser.isEmpty()) {
            return Constantes.ERROR_FALTA_USUARIO;
        } else if (txtPassword == null || txtPassword.isEmpty()) {
            return Constantes.ERROR_FALTA_PASS;
        } else {
            try {
                usu = ufl.traerUsuarioXNombre(txtuser);
                if (usu != null) {
                    usu.setPassword(txtPassword);
                    if (usu.getNombre().equals(txtuser) && Security.verifyPassword(usu)) {
                        redireccion = "MAIN";
                        JSFUtils.agregarInfo(null, Mensajes.LOGIN_CORRECTO + " " + redireccion, "");
                        //faces.getExternalContext().getSessionMap().put("usuario", usu);
                        return redireccion;
                    } else {
                        JSFUtils.agregarError(null, Mensajes.LOGIN_ERROR, "");
                        return null;
                    }
                } else {
                    JSFUtils.agregarError(null, Mensajes.LOGIN_ERROR, "");
                    return null;
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Logger.Level.FATAL, ex, ex);
//                return new Notification(ConstantesEtiquetas.ERROR, ConstantesErrores.CONTACTE_ADMINISTRADOR, Notification.ERROR_ICON);
            }
        }
        return null;
    }

    public boolean verificarSession() {
//        usu = (Usuario) faces.getExternalContext().getSessionMap().get("usuario");
        return usu != null;
    }
    
    public void sinPermisos(){
        JSFUtils.agregarError(null, Mensajes.SIN_PERMISOS, "");
    }

    public void cerrarSession() {
        usu = null;
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

    public Usuario getUsu() {
        return usu;
    }

    public void setUsu(Usuario usu) {
        this.usu = usu;
    }

}
