package co.com.claro.service.rest;

import co.com.claro.ejb.dao.ConciliacionDAO;
import co.com.claro.ejb.dao.EscenarioDAO;
import co.com.claro.ejb.dao.utils.UtilListas;
import co.com.claro.model.dto.EscenarioDTO;
import co.com.claro.model.dto.parent.PadreDTO;
import co.com.claro.model.entity.Conciliacion;
import co.com.claro.model.entity.Escenario;
import co.com.claro.service.rest.excepciones.DataNotFoundException;
import co.com.claro.service.rest.excepciones.MensajeError;
import java.time.Instant;
import static java.util.Comparator.comparing;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ejb.EJB;
import javax.persistence.Transient;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Clase que maneja el API Rest de Escenarios
 * @author Andres Bedoya
 */
@Path("escenarios")
public class EscenarioREST {
    @Transient
    private static final Logger logger = Logger.getLogger(EscenarioREST.class.getSimpleName());

    @EJB
    protected EscenarioDAO managerDAO;
    
    @EJB
    protected ConciliacionDAO padreDAO;

    /**
     * Obtiene las Escenarios Paginadas
     * @param offset Desde cual item se retorna
     * @param limit Limite de items a retornar
     * @param orderby Indica por cual campo descriptivo va a guardar (id, nombre, fechaCreacion)
     * @return Toda la lista de escenarios que corresponden con el criterio
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<EscenarioDTO> find(
            @QueryParam("offset") int offset,
            @QueryParam("limit") int limit,
            @QueryParam("orderby") String orderby) {
        logger.log(Level.INFO, "offset:{0}limit:{1}orderby:{2}", new Object[]{offset, limit, orderby});     
        List<Escenario> lst = managerDAO.findRange(new int[]{offset, limit});
        List<PadreDTO> lstDTO = lst.stream().map(item -> item.toDTO()).distinct().sorted(comparing(EscenarioDTO::getId)).collect(toList());

        lstDTO = UtilListas.ordenarLista(lstDTO, orderby);
        List<EscenarioDTO> lstFinal = (List<EscenarioDTO>)(List<?>) lstDTO;
        return lstFinal;
    }

    /**
     * Obtiene una Escenario por id
     * @param id Identificador de conciliacion
     * @return Una Escenario que coincide con el criterio
     */
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public EscenarioDTO getById(@PathParam("id") int id){
        logger.log(Level.INFO, "id:{0}", id);
        Escenario entidad = managerDAO.find(id);
        return entidad.toDTO();

    }

     /**
     * Busca las escenarios por cualquier columna
     * @param texto Texto a buscar en cualquier texto
     * @return Lista de Escenarios que cumplen con el criterio
     */
    @GET
    @Path("/findByAny")
    @Produces({MediaType.APPLICATION_JSON})
    public List<EscenarioDTO> findByAnyColumn(@QueryParam("texto") String texto){
        logger.log(Level.INFO, "texto:{0}", texto);      
        List<Escenario> lst = managerDAO.findByAnyColumn(texto);
        List<PadreDTO> lstDTO = lst.stream().map(item -> item.toDTO()).sorted(comparing(EscenarioDTO::getId)).collect(toList());
        List<EscenarioDTO> lstFinal = (List<EscenarioDTO>)(List<?>) lstDTO;
        return lstFinal;        
    }
   
     /**
     * Crea una nueva politica
     * @param entidad Entidad que se va a agregar
     * @return el la entidad recien creada
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response add(EscenarioDTO entidad) {
        logger.log(Level.INFO, "entidad:{0}", entidad);
        Conciliacion entidadPadreJPA;
        Escenario entidadHijaJPA = entidad.toEntity();
        entidadHijaJPA.setConciliacion(null);        

        if ( entidad.getIdConciliacion() != null) {
            entidadPadreJPA = padreDAO.find(entidad.getIdConciliacion());
            if (entidadPadreJPA == null) {
                throw new DataNotFoundException("Datos no encontrados " + entidad.getIdConciliacion());
            } else {
                managerDAO.create(entidadHijaJPA);
                entidadHijaJPA.setConciliacion(entidadPadreJPA);
                managerDAO.edit(entidadHijaJPA);
                entidadPadreJPA.addEscenario(entidadHijaJPA);
                padreDAO.edit(entidadPadreJPA);
            }
        } else {
            managerDAO.create(entidadHijaJPA);
        }
        return Response.status(Response.Status.CREATED).entity(entidadHijaJPA.toDTO()).build();
    }   
    
    /**
     * Actualiza la entidad por su Id
     * @param entidad conciliacion con la cual se va a trabajar
     * @return el resultado de la operacion
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response update(EscenarioDTO entidad) {
        logger.log(Level.INFO, "entidad:{0}", entidad);  
        Conciliacion entidadPadreJPA = null;
        if (entidad.getIdConciliacion() != null) {
            entidadPadreJPA = padreDAO.find(entidad.getIdConciliacion());
            if (entidadPadreJPA == null) {
                throw new DataNotFoundException(Response.Status.NOT_FOUND.getReasonPhrase() + entidad.getIdConciliacion());
            }
        }
        //Hallar La entidad actual para actualizarla
        Escenario entidadHijaJPA = managerDAO.find(entidad.getId());
        if (entidadHijaJPA != null) {
            entidadHijaJPA.setFechaActualizacion(Date.from(Instant.now()));
            entidadHijaJPA.setNombre(entidad.getNombre() != null ? entidad.getNombre() : entidadHijaJPA.getNombre());
            entidadHijaJPA.setImpacto(entidad.getImpacto() != null ? entidad.getImpacto() : entidadHijaJPA.getImpacto());
            entidadHijaJPA.setUsuario(entidad.getUsuario()!= null ? entidad.getUsuario() : entidadHijaJPA.getUsuario());
            entidadHijaJPA.setUsuarioAsignado(entidad.getUsuarioAsignado() != null ? entidad.getUsuarioAsignado() : entidadHijaJPA.getUsuarioAsignado());
            entidadHijaJPA.setConciliacion(entidad.getIdConciliacion() != null ?  (entidadPadreJPA != null ? entidadPadreJPA : null): entidadHijaJPA.getConciliacion());
            managerDAO.edit(entidadHijaJPA);
            if ((entidadPadreJPA != null)){
                entidadPadreJPA.addEscenario(entidadHijaJPA);
                padreDAO.edit(entidadPadreJPA);
            }
            return Response.status(Response.Status.OK).entity(entidadHijaJPA.toDTO()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    /**
     * Obtiene la entidad entidadPadreJPA que hay que asignar
     * @param entidadActualDTO
     * @return la entidad entidadPadreJPA que hay que asignar
     */
    private Conciliacion getConciliacionToAssign(EscenarioDTO entidadActualDTO){
        EscenarioDTO entidadInBDDTO = getById(entidadActualDTO.getId());
        Conciliacion conciliacion = new Conciliacion();
        if (entidadInBDDTO.getIdConciliacion() == null) {
            conciliacion.setId(entidadActualDTO.getIdConciliacion());
        } else {
            conciliacion.setId(entidadInBDDTO.getIdConciliacion());
        }
        return conciliacion;
    }  
    
    private Boolean isConciliacionAsignada(EscenarioDTO entidadActualDTO){
        EscenarioDTO entidadInBDDTO = getById(entidadActualDTO.getId());
        return entidadInBDDTO.getIdConciliacion() != null;
    }  
    
     /**
     * Borra una conciliacion por su Id
     * @param id Identificador de la identidad
     * @return el resultado de la operacion
     */
    @DELETE
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response remove(@PathParam("id") Integer id) {
        Escenario hijo = managerDAO.find(id);
        Conciliacion entidadPadreJPA = null;
        if (hijo.getConciliacion() != null) {
            entidadPadreJPA = padreDAO.find(hijo.getConciliacion().getId());
            entidadPadreJPA.removeEscenario(hijo);
            //entidadPadreJPA.getConciliaciones().remove(hijo);
        }
        managerDAO.remove(hijo);
        if (entidadPadreJPA != null) {
            padreDAO.edit(entidadPadreJPA);
        }
        MensajeError mensaje = new MensajeError(Response.Status.OK.getStatusCode(), Response.Status.OK.getReasonPhrase(), "Registro borrado exitosamente");
        return Response.status(Response.Status.OK).entity(mensaje).build();
    }
    
    @GET
    @Path("/count")
    @Produces({MediaType.APPLICATION_JSON})
    public int count(){
        return managerDAO.count();
    }
}