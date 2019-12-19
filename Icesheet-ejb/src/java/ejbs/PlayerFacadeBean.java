/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.PlayerEJBDTO;
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
import models.PlayersModel;

/**
 *
 * @author David
 */
@Stateless
@LocalBean
public class PlayerFacadeBean {

    @PersistenceContext
    private EntityManager em;
    
    public String createPlayer(PlayerEJBDTO player) {

        PlayersModel playersModel = null;
        
        try {
            playersModel = new PlayersModel(null, 
                    player.getTeamid(), 
                    player.getFirstname(), 
                    player.getLastname(), 
                    player.getNumber(), 
                    player.getPosition(), 
                    player.getShoots(),
                    0,
                    0,
                    0,
                    0,
                    0.0); 
            em.persist(playersModel);
            em.flush();  
        } catch (ConstraintViolationException v) { 
            Set<ConstraintViolation<?>> coll = v.getConstraintViolations(); 
            coll.stream().forEach((s) -> {
                System.out.println(s.getPropertyPath() + " " + s.getMessage());
            });
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }
        
        return (playersModel != null) ? playersModel.getFirstname() + " " + playersModel.getLastname() : "ERROR";
    }
    
    public List<PlayerEJBDTO> readPlayers() {
        List<PlayersModel> players;
        List<PlayerEJBDTO> playerDtos = new ArrayList();
        
        try {
            Query query = em.createNamedQuery("PlayersModel.findAll");
            players = query.getResultList();            
            playerDtos = Conversions.convertListModelToEJBDTO(players);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return playerDtos;
    }
    
    public List<PlayerEJBDTO> readPlayersByTeam(Integer teamid) {
        List<PlayersModel> players;
        List<PlayerEJBDTO> playerDtos = new ArrayList();
        
        try {
            Query query = em.createNamedQuery("PlayersModel.findByTeamid");
            query.setParameter("teamid", teamid);
            players = query.getResultList();
            playerDtos = Conversions.convertListModelToEJBDTO(players);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return playerDtos;
    }   
    
    public Integer updatePlayer(PlayerEJBDTO player) {
        
        Integer rowsUpdated = -1;
        
        try { 
            PlayersModel playersModel = em.find(PlayersModel.class, player);   
            playersModel = Conversions.setPlayersModel(playersModel, player);
            em.persist(playersModel);
            em.flush();
            rowsUpdated = 1;
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }
        
        return rowsUpdated;
    }
    
    public Integer deletePlayer(PlayerEJBDTO player) {
        
        PlayersModel model = null;
        int rowsDeleted = -1;
        
        try {
            model = em.find(PlayersModel.class, player.getPlayerid());
            em.remove(model);
            rowsDeleted = 1;
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }
        
        return rowsDeleted;
    }
}