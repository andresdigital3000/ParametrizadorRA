/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.service.rest;

import co.com.claro.ejb.dao.LogAuditoriaDAO;
import co.com.claro.ejb.dao.ParametroDAO;
import co.com.claro.ejb.dao.utils.UtilListas;
import co.com.claro.model.dto.ParametroDTO;
import co.com.claro.model.entity.LogAuditoria;
import co.com.claro.model.entity.Parametro;
import co.com.claro.service.rest.response.WrapperResponseEntity;
import co.com.claro.service.rest.tokenFilter.JWTTokenNeeded;

import java.time.Instant;
import static java.util.Comparator.comparing;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Transient;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author andresbedoya
 */
@Stateless
@Path("parametros")
public class ParametrosREST {

    @Transient
    private static final Logger logger = Logger.getLogger(ParametrosREST.class.getSimpleName());
<<<<<<< HEAD
    
    private String modulo = "PARAMETROS";
    
=======

    private String usuario = "admin";
    private String modulo = "parametros";

>>>>>>> origin/master
    @EJB
    protected LogAuditoriaDAO logAuditoriaDAO;
    @EJB
    protected ParametroDAO managerDAO;

    //@EJB
    //protected EscenarioDAO padreDAO;
    /**
     * Obtiene las Parametros Paginadas
     *
     * @param offset Desde cual item se retorna
     * @param limit Limite de items a retornar
     * @param orderby Indica por cual campo descriptivo va a guardar (id,
     * nombre, fechaCreacion)
     * @return Toda la lista de escenarios que corresponden con el criterio
     */
    @GET
    @JWTTokenNeeded
    @Produces({MediaType.APPLICATION_JSON})
    public List<ParametroDTO> find(
            @QueryParam("offset") int offset,
            @QueryParam("limit") int limit,
            @QueryParam("orderby") String orderby) {
        logger.log(Level.INFO, "offset:{0}limit:{1}orderby:{2} ", new Object[]{offset, limit, orderby});
        List<Parametro> lst = managerDAO.findRange(new int[]{offset, limit});
        List<ParametroDTO> lstDTO = lst.stream().map(item -> item.toDTO()).distinct().sorted(comparing(ParametroDTO::getId).reversed()).collect(toList());
        //lstDTO = UtilListas.ordenarListaParametros(lstDTO, orderby);
        List<ParametroDTO> lstFinal = (List<ParametroDTO>) (List<?>) lstDTO;
        return lstFinal;
    }

    @GET
    @Path("/findAVencer")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ParametroDTO> findAVencer() {
        List<Parametro> lst = managerDAO.findAVencer();
        return lst.stream().map(item->item.toDTO()).collect(toList());
    }

    /**
     * Obtiene un indicador por id
     *
     * @param id Identificador de conciliacion
     * @return Una Escenario que coincide con el criterio
     */
    @GET
    @Path("{id}")
    @JWTTokenNeeded
    @Produces({MediaType.APPLICATION_JSON})
    public ParametroDTO getById(@PathParam("id") int id) {
        logger.log(Level.INFO, "id:{0}", id);
        Parametro entidad = managerDAO.find(id);
        return entidad.toDTO();

    }

    /**
     * Busca los indicadores por cualquier columna
     *
     * @param texto Texto a buscar en cualquier texto
     * @return Lista de Escenarios que cumplen con el criterio
     */
    @GET
    @Path("/findByAny")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ParametroDTO> findByAnyColumn(@QueryParam("texto") String texto) {
        logger.log(Level.INFO, "texto:{0}", texto);
        List<Parametro> lst = managerDAO.findByAnyColumn(texto);
        List<ParametroDTO> lstDTO = lst.stream().map(item -> item.toDTO()).sorted(comparing(ParametroDTO::getId)).collect(toList());
        List<ParametroDTO> lstFinal = (List<ParametroDTO>) (List<?>) lstDTO;
        return lstFinal;
    }

    /**
     * Busca los Parametros por algun padre. Recuerden que la relacion es logica
     * no fisica
     *
     * @param tipo
     * @param codPadre
     * @return Lista de Escenarios que cumplen con el criterio
     */
    @GET
    @Path("/padre")
    @JWTTokenNeeded
    @Produces({MediaType.APPLICATION_JSON})
    public List<ParametroDTO> findByPadre(@QueryParam("tipo") String tipo, @QueryParam("codpadre") Integer codPadre) {
        logger.log(Level.INFO, "tipo:{0}codPadre:{1}", new Object[]{tipo, codPadre});
        List<Parametro> lst = managerDAO.findByCodPadre(tipo, codPadre);
        List<ParametroDTO> lstDTO = lst.stream().map(item -> item.toDTO()).sorted(comparing(ParametroDTO::getId)).collect(toList());
        List<ParametroDTO> lstFinal = (List<ParametroDTO>) (List<?>) lstDTO;
        return lstFinal;
    }

    /**
     * Crea una nueva entidad
     *
     * @param entidad Entidad que se va a agregar
     * @return el la entidad recien creada
     */
    @POST
    @JWTTokenNeeded
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response add(ParametroDTO entidad) {
        logger.log(Level.INFO, "entidad:{0}", entidad);
        Parametro entidadJPA = entidad.toEntity();
        managerDAO.create(entidadJPA);
        LogAuditoria logAud = new LogAuditoria(this.modulo, Constantes.Acciones.AGREGAR.name(), Date.from(Instant.now()), entidad.getUsername(), entidadJPA.toString());
        logAuditoriaDAO.create(logAud);
        return Response.status(Response.Status.CREATED).entity(entidadJPA.toDTO()).build();
    }

    /**
     * Actualiza la entidad por su Id
     *
     * @param entidad conciliacion con la cual se va a trabajar
     * @return el resultado de la operacion
     */
    @PUT
    @JWTTokenNeeded
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(ParametroDTO entidad) {
        logger.log(Level.INFO, "entidad:{0}", entidad);
        Parametro entidadJPA = managerDAO.find(entidad.getId());
        if (entidadJPA != null) {
            entidadJPA.setFechaActualizacion(Date.from(Instant.now()));
            entidadJPA.setParametro(entidad.getParametro() != null ? entidad.getParametro() : entidadJPA.getParametro());
            entidadJPA.setValor(entidad.getValor() != null ? entidad.getValor() : entidadJPA.getValor());
            entidadJPA.setDescripcion(entidad.getDescripcion() != null ? entidad.getDescripcion() : entidadJPA.getDescripcion());
            entidadJPA.setTipo(entidad.getTipo() != null ? entidad.getTipo() : entidadJPA.getTipo());
            entidadJPA.setCodPadre(entidad.getCodPadre() != null ? entidad.getCodPadre() : entidadJPA.getCodPadre());
            managerDAO.edit(entidadJPA);
            LogAuditoria logAud = new LogAuditoria(this.modulo, Constantes.Acciones.EDITAR.name(), Date.from(Instant.now()), entidad.getUsername(), entidadJPA.toString());
            logAuditoriaDAO.create(logAud);
            return Response.status(Response.Status.OK).entity(entidadJPA.toDTO()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();

    }

    /**
     * Borra una entidad por su Id
     *
     * @param id Identificador de la identidad
     * @return el resultado de la operacion
     */
    @DELETE
    @Path("{id}/{username}")
    @JWTTokenNeeded
    @Produces({MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("id") Integer id, @PathParam("username") String username) {
        Parametro entidadJPA = managerDAO.find(id);
        ParametroDTO dto = entidadJPA.toDTO();
        managerDAO.remove(entidadJPA);
        LogAuditoria logAud = new LogAuditoria(this.modulo, Constantes.Acciones.BORRAR.name(), Date.from(Instant.now()), username, dto.toString());
        logAuditoriaDAO.create(logAud);
        WrapperResponseEntity mensaje = new WrapperResponseEntity(Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase(), "Registro borrado exitosamente");
        return Response.status(Response.Status.OK).entity(mensaje).build();
    }

    @GET
    @Path("/count")
    @JWTTokenNeeded
    @Produces({MediaType.APPLICATION_JSON})
    public int count() {
        return managerDAO.count();
    }
}
