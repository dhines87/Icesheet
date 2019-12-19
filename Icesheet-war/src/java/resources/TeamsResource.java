/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dtos.TeamEJBDTO;
import ejbs.TeamFacadeBean;
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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;

/**
 * REST Web Service
 *
 * @author David
 */
@Path("teams")
public class TeamsResource {
    TeamFacadeBean teamFacadeBean = lookupTeamFacadeBeanBean();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of TeamsResource
     */
    public TeamsResource() {
    }

    /**
     * Retrieves representation of an instance of resources.TeamsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public List<TeamEJBDTO> getTeamsJson() {
        return teamFacadeBean.readTeams();
    }
    
    @POST
    @Consumes("application/json")
    public String createTeamFromJson(TeamEJBDTO team) {
        return teamFacadeBean.createTeam(team);
    }
    
    @PUT
    @Consumes("application/json")
    public List<TeamEJBDTO> updateTeamFromJson(ArrayList<TeamEJBDTO> teams) {
        return teamFacadeBean.updateTeams(teams);
    }
    
    @DELETE
    public Integer deleteTeam(TeamEJBDTO team) {
        return teamFacadeBean.deleteTeam(team);
    }

    private TeamFacadeBean lookupTeamFacadeBeanBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TeamFacadeBean) c.lookup("java:global/Icesheet/Icesheet-ejb/TeamFacadeBean!ejbs.TeamFacadeBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
