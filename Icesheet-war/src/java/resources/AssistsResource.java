/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dtos.AssistEJBDTO;
import ejbs.AssistsFacadeBean;
import java.util.ArrayList;
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
@Path("assists")
public class AssistsResource {
    AssistsFacadeBean assistsFacadeBean = lookupAssistsFacadeBeanBean();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of AssistsResource
     */
    public AssistsResource() {
    }
    
    @POST
    @Consumes("application/json")
    public List<AssistEJBDTO> createAssistsFromJson(ArrayList<AssistEJBDTO> assists) {
        return assistsFacadeBean.createAssists(assists);
    }
    
    @DELETE   
    @Path("/{assistids}")
    public String deleteAssists(@PathParam("assistids") String assistIdsString) {
        String failedAssists = assistsFacadeBean.deleteAssists(assistIdsString);
        return failedAssists;
    }

    private AssistsFacadeBean lookupAssistsFacadeBeanBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AssistsFacadeBean) c.lookup("java:global/Icesheet/Icesheet-ejb/AssistsFacadeBean!ejbs.AssistsFacadeBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
