/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.model.entity;

import co.com.claro.model.dto.ParametroDTO;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author andresbedoya
 */
@Entity
@Table(name = "TBL_GAI_PARAMETROS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Parametro.findAll", query = "SELECT p FROM Parametro p")
    , @NamedQuery(name = "Parametro.findByCodParametro", query = "SELECT p FROM Parametro p WHERE p.id = :codParametro")
    , @NamedQuery(name = "Parametro.findByParametro", query = "SELECT p FROM Parametro p WHERE p.tipo = :tipo AND p.parametro = :parametro")
    , @NamedQuery(name = "Parametro.findByValor", query = "SELECT p FROM Parametro p WHERE p.valor = :valor")
    , @NamedQuery(name = "Parametro.findByDescripcion", query = "SELECT p FROM Parametro p WHERE p.descripcion = :descripcion")
    , @NamedQuery(name = "Parametro.findByFechaCreacion", query = "SELECT p FROM Parametro p WHERE p.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "Parametro.findByFechaActualizacion", query = "SELECT p FROM Parametro p WHERE p.fechaActualizacion = :fechaActualizacion")
    , @NamedQuery(name = "Parametro.findByAnyColumn", query = "SELECT DISTINCT(p) FROM Parametro p WHERE lower(p.parametro) LIKE lower(:parametro) or lower(p.descripcion) LIKE lower(:descripcion) or lower(p.tipo) LIKE lower(:tipo) or lower(p.valor) = lower(:valor)")
    , @NamedQuery(name = "Parametro.findByColumn", query = "SELECT DISTINCT(p) FROM Parametro p WHERE lower(p.parametro) = lower(:parametro) or lower(p.descripcion) = lower(:descripcion)")
    , @NamedQuery(name = "Parametro.findByOdi", query = "SELECT DISTINCT(p) FROM Parametro p WHERE lower(p.parametro) LIKE lower(:parametro)")
    , @NamedQuery(name = "Parametro.findAVencer", query = "SELECT p FROM Parametro p WHERE p.tipo = 'CONCILIACION' and ( (p.fechaActualizacion is null and  p.fechaCreacion < :fecha) or ((p.fechaActualizacion is not null and  p.fechaActualizacion < :fecha)))")
    , @NamedQuery(name = "Parametro.findByCodPadre", query = "SELECT DISTINCT(p) FROM Parametro p WHERE lower(p.tipo) LIKE lower(:tipo)")
    , @NamedQuery(name = "Parametro.findParaIndicadores", query = "SELECT DISTINCT(p) FROM Parametro p WHERE lower(p.tipo) LIKE lower(:tipo) OR p.tipo = 'GENERAL'")
        
})

public class Parametro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COD_PARAMETRO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Size(max = 200)
    @Column(name = "PARAMETRO")
    private String parametro;
    
    @Size(max = 200)
    @Column(name = "VALOR")
    private String valor;
    
    @Size(max = 200)
    @Column(name = "TIPO")
    private String tipo;
    
    @Size(max = 2147483647)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Column(name = "FECHA_ACTUALIZACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    
    @Column(name = "COD_PADRE")
    private Integer codPadre;

    public Parametro() {
    }

    public Parametro(Integer codParametro) {
        this.id = codParametro;
    }

    public Parametro(Integer codParametro, Date fechaCreacion) {
        this.id = codParametro;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion != null ? fechaCreacion : Date.from(Instant.now());
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCodPadre() {
        return codPadre;
    }

    public void setCodPadre(Integer codPadre) {
        this.codPadre = codPadre;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametro)) {
            return false;
        }
        Parametro other = (Parametro) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Parametro{" + "id=" + id + ", parametro=" + parametro + ", valor=" + valor + '}';
    }


    
    public ParametroDTO toDTO(){
        ParametroDTO entidadDTO = new ParametroDTO();
        entidadDTO.setId(id);
        entidadDTO.setParametro(parametro);
        entidadDTO.setDescripcion(descripcion);
        entidadDTO.setValor(valor);
        entidadDTO.setFechaActualizacion(fechaActualizacion);
        entidadDTO.setFechaCreacion(fechaCreacion);
        entidadDTO.setCodPadre(codPadre);
        entidadDTO.setTipo(tipo);
        
        return entidadDTO;
    }
    
}
