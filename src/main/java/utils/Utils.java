/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Nacher
 */
public class Utils {

    public static boolean esVacioONulo(String texto) {
        boolean esNulo = false;
        if (texto != null) {
            if (texto.isEmpty()) {
                esNulo = true;
            }
        }else{
            esNulo = true;
        }
        return esNulo;
    }

}
