/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.PenaltyEJBDTO;
import helpers.Conversions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import models.PenaltiesModel;
import models.PlayersModel;

/**
 *
 * @author David
 */
@Stateless
@LocalBean
public class PenaltiesFacadeBean {

    @PersistenceContext
    private EntityManager em;
    
    public Integer createPenalty(PenaltyEJBDTO penalty) {

        PenaltiesModel penaltiesModel = null;
        Boolean error = true;
        
        try {        
            penaltiesModel = new PenaltiesModel(
                    null, 
                    penalty.getGameid(), 
                    penalty.getPlayer().getPlayerid(), 
                    penalty.getPenalty(), 
                    penalty.getMinutes(), 
                    penalty.getPeriod(), 
                    penalty.getTime()); 
            
            PlayersModel player = em.find(PlayersModel.class, penalty.getPlayer().getPlayerid());
            player.setPims(player.getPims() + penalty.getMinutes());
            em.persist(penaltiesModel);
            em.persist(player);
            em.flush();  
            error = false;
        } catch (ConstraintViolationException v) { 
            Set<ConstraintViolation<?>> coll = v.getConstraintViolations(); 
            coll.stream().forEach((s) -> {
                System.out.println(s.getPropertyPath() + " " + s.getMessage());
            });
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }
        
        return penaltiesModel.getPenaltyid();
    }
    
    public Integer deletePenalty(Integer penaltyid) {
        
        PenaltiesModel penalty = null;
        int rowsDeleted = -1;
        
        try {
            penalty = em.find(PenaltiesModel.class, penaltyid);
            PlayersModel player = em.find(PlayersModel.class, penalty.getPlayerid());
            player.setPims(player.getPims() + penalty.getMinutes());
            em.remove(penalty);
            em.persist(player);
            em.flush();
            rowsDeleted = 1;
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }
        
        return rowsDeleted;
    }
}
