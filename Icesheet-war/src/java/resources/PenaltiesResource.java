/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dtos.PenaltyEJBDTO;
import ejbs.PenaltiesFacadeBean;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;

/**
 * REST Web Service
 *
 * @author David
 */
@Path("penalties")
public class PenaltiesResource {
    PenaltiesFacadeBean penaltiesFacadeBean = lookupPenaltiesFacadeBeanBean();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PenaltiesResource
     */
    public PenaltiesResource() {
    }
    
    @POST
    @Consumes("application/json")
    public Integer createPenaltyFromJson(PenaltyEJBDTO penalty) {
        return penaltiesFacadeBean.createPenalty(penalty);
    }
    
    @DELETE
    @Path("/{penaltyid}")
    public Integer deletePenaltyFromJson(@PathParam("penaltyid") String penaltyid) {
        return penaltiesFacadeBean.deletePenalty(Integer.parseInt(penaltyid));
    }

    private PenaltiesFacadeBean lookupPenaltiesFacadeBeanBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PenaltiesFacadeBean) c.lookup("java:global/Icesheet/Icesheet-ejb/PenaltiesFacadeBean!ejbs.PenaltiesFacadeBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
