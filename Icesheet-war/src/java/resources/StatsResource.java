/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dtos.PlayerEJBDTO;
import ejbs.PlayerFacadeBean;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * REST Web Service
 *
 * @author David
 */
@Path("stats")
public class StatsResource {
    PlayerFacadeBean playerFacadeBean = lookupPlayerFacadeBeanBean();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of StatsResource
     */
    public StatsResource() {
    }

    /**
     * Retrieves representation of an instance of resources.StandingsResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public List<PlayerEJBDTO> getStandingsJson() {
        return playerFacadeBean.readPlayers();
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
