/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.model.dto;

import co.com.claro.model.dto.parent.PadreDTO;
import co.com.claro.model.entity.Politica;
import java.io.Serializable;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO para Politica
 * @author andres
 */
@XmlRootElement
public class PoliticaDTO extends PadreDTO implements Serializable{

    private String objetivo;
    private String descripcion;
    private String username;
    
    private Set<ConciliacionDTO> conciliaciones;

    public Set<ConciliacionDTO> getConciliaciones() {
        return conciliaciones;
    }

    public void setConciliaciones(Set<ConciliacionDTO> conciliaciones) {
        this.conciliaciones = conciliaciones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
        
    public Politica toEntity(){
        Politica p = new Politica();
        //Campos comunes
        p.setId(this.getId());
        p.setNombre(this.getNombre());
        p.setFechaCreacion(this.getFechaCreacion());
        p.setFechaActualizacion(this.getFechaActualizacion());
        
        //Campos de la entidad
        p.setDescripcion(this.descripcion);
        p.setObjetivo(this.objetivo);
       
        return p;
        
    }
     
}
