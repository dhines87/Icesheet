/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dtos.PlayerEJBDTO;
import ejbs.PlayerFacadeBean;
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
@Path("players")
public class PlayersResource {
    PlayerFacadeBean playerFacadeBean = lookupPlayerFacadeBeanBean();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PlayersResource
     */
    public PlayersResource() {
    }

    /**
     * Retrieves representation of an instance of resources.PlayersResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    @Path("/{teamid}")
    public List<PlayerEJBDTO> getPlayersByTeamIdJson(@PathParam("teamid") Integer teamid) {
        return playerFacadeBean.readPlayersByTeam(teamid);
    }
    
    @POST
    @Consumes("application/json")
    public String createPlayerFromJson(PlayerEJBDTO player) {
        return playerFacadeBean.createPlayer(player);
    }
    
    @PUT
    @Consumes("application/json")
    public Integer updatePlayersFromJson(PlayerEJBDTO player) {
        return playerFacadeBean.updatePlayer(player);
    }
    
    @DELETE
    public Integer deleteTeam(PlayerEJBDTO player) {
        return playerFacadeBean.deletePlayer(player);
    }

    private PlayerFacadeBean lookupPlayerFacadeBeanBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (PlayerFacadeBean) c.lookup("java:global/Icesheet/Icesheet-ejb/PlayerFacadeBean!ejbs.PlayerFacadeBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
