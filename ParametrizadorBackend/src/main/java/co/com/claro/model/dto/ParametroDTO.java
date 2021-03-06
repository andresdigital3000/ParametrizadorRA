/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.model.dto;

import co.com.claro.model.entity.Parametro;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DTO para Parametros
 *
 * @author andres
 */
@XmlRootElement
public class ParametroDTO implements Serializable {

    private Integer id;
    private Integer codPadre;
    private String codPadreDesc;
    private String parametro;
    private String valor;
    private String descripcion;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private String tipo;
    private String username;
    
    public String getCodPadreDesc() {
		return codPadreDesc;
	}

	public void setCodPadreDesc(String codPadreDesc) {
		this.codPadreDesc = codPadreDesc;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Integer getCodPadre() {
        return codPadre;
    }

    public void setCodPadre(Integer codPadre) {
        this.codPadre = codPadre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Parametro toEntity() {
        Parametro p = new Parametro();

        p.setId(this.getId());
        p.setParametro(parametro);
        p.setValor(valor);
        p.setFechaCreacion(this.getFechaCreacion());
        p.setFechaActualizacion(this.getFechaActualizacion());
        p.setDescripcion(this.descripcion);
        p.setCodPadre(codPadre);
        p.setTipo(tipo);
        return p;

    }

    @Override
    public String toString() {
        return "ParametroDTO{" + "id=" + id + ", parametro=" + parametro + ", valor=" + valor + ", tipo=" + tipo + '}';
    }
    
    
}
