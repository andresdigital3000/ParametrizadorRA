package co.com.claro.service.rest.excepciones;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author andres
 */
public class DatosNoEncontrados extends RuntimeException{
    public DatosNoEncontrados(String mensaje) {
        super(mensaje);
    }
    
}
