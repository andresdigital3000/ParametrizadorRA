/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ejb.dao;

import co.com.claro.ejb.dao.parent.AbstractJpaDAO;
import co.com.claro.model.entity.QueryAprobacion;
import co.com.claro.service.rest.excepciones.DataNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Definimos una subinterface por cada entidad que queremos persistir. para asi poder
 * agregar metodos propios de esta entidad y heredar los generales.
 * @author Andres Bedoya
 */

@Stateless
public class QueryAprobacionDAO extends AbstractJpaDAO<QueryAprobacion>{
    private static final Logger logger = Logger.getLogger(QueryAprobacionDAO.class.getSimpleName());
    @PersistenceContext(unitName = "co.com.claro_ParametrizadorClaro_war_1.0PU")
    private EntityManager em;

    public QueryAprobacionDAO() {
        super(QueryAprobacion.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Retornar todos los registros
     * @param range
     * @return Retorna todos los registros
     */
    public List<QueryAprobacion> findByAllTree(int[] range){
        TypedQuery<QueryAprobacion> query = em.createNamedQuery("QueryAprobacion.findAll", QueryAprobacion.class);
        query.setMaxResults(range[1]);// - range[0] + 1);
        query.setFirstResult(range[0]);           
        List<QueryAprobacion> lst = query.getResultList();
        if (lst == null || lst.isEmpty()) {
            throw new DataNotFoundException("No se encontraron datos");
        }        
        return query.getResultList();
    }  

    /**
     * Buscar la misma cadena en todos los campos descriptivos
     * @param busqueda Campo por el cual va a buscar en todos los campos descriptivos
     * @return Lista de QueryAprobacions que cumplan con el criterio
     */
    public List<QueryAprobacion> findByAnyColumn(String busqueda){
        logger.log(Level.INFO, "busqueda:{0}", new Object[]{busqueda});     
        TypedQuery<QueryAprobacion> query = em.createNamedQuery("QueryAprobacion.findByAnyColumn", QueryAprobacion.class);
        query.setParameter("estadoAprobacion", "%" + busqueda + "%");
        query.setParameter("nombreConciliacion", "%" + busqueda + "%");
        List<QueryAprobacion> results = query.getResultList();
        if (results == null || results.isEmpty()) {
            throw new DataNotFoundException("No se encontraron datos de Busqueda");
        }
        return results;
    }
        
        
}

