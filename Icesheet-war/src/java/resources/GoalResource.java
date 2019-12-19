/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resources;

import dtos.GoalEJBDTO;
import ejbs.GoalsFacadeBean;
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
@Path("goal")
public class GoalResource {
    GoalsFacadeBean goalsFacadeBean = lookupGoalsFacadeBeanBean();

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GoalResource
     */
    public GoalResource() {
    }
    
    @POST
    @Consumes("application/json")
    public GoalEJBDTO createGoalFromJson(GoalEJBDTO goal) {
        return goalsFacadeBean.createGoal(goal);
    }
    
    @DELETE   
    @Path("/{goalid}")
    public Integer deleteGoalsFromJson(@PathParam("goalid") String goalid) {
       return goalsFacadeBean.deleteGoal(Integer.parseInt(goalid));
    }


    private GoalsFacadeBean lookupGoalsFacadeBeanBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (GoalsFacadeBean) c.lookup("java:global/Icesheet/Icesheet-ejb/GoalsFacadeBean!ejbs.GoalsFacadeBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
