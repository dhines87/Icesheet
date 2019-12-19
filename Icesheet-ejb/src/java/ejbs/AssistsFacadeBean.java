/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AssistEJBDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import models.AssistsModel;
import models.PlayersModel;

/**
 *
 * @author David
 */
@Stateless
@LocalBean
public class AssistsFacadeBean {

    @PersistenceContext
    private EntityManager em;
    
    public List<AssistEJBDTO> createAssists(ArrayList<AssistEJBDTO> assists) {

        AssistsModel assistsModel = null;
        
        try {
            for (AssistEJBDTO assist : assists) {
                assistsModel = new AssistsModel(null, assist.getGameid(), assist.getGoalid(), assist.getPlayer().getPlayerid()); 
                PlayersModel assistPlayer = em.find(PlayersModel.class, assist.getPlayer().getPlayerid());
                assistPlayer.setAssists(assistPlayer.getAssists() + 1);                
                em.persist(assistPlayer);
                em.persist(assistsModel);
                em.flush();
                assist.setAssistid(assistsModel.getAssistid());
            }             
        } catch (ConstraintViolationException v) { 
            Set<ConstraintViolation<?>> coll = v.getConstraintViolations(); 
            coll.stream().forEach((s) -> {
                System.out.println(s.getPropertyPath() + " " + s.getMessage());
            });
            return null;
        } catch (Exception e) {        
            System.out.println(e.getMessage());
            return null;
        }
        
        return assists;
    }
    
    public List<AssistEJBDTO> updateAssists(ArrayList<AssistEJBDTO> assists) {

        AssistsModel assistsModel = null;
        List<AssistEJBDTO> failedAssists = new ArrayList<>();
        
        for (AssistEJBDTO assist : assists) {
            try {
                assistsModel = em.find(AssistsModel.class, assist.getAssistid());
                
                assistsModel.setAssistid(assist.getAssistid());
                assistsModel.setGameid(assist.getGameid());
                assistsModel.setGoalid(assist.getGoalid());
                assistsModel.setPlayerid(assist.getPlayer().getPlayerid());
                
                em.persist(assistsModel);
                em.flush();
            } catch (ConstraintViolationException v) { 
                Set<ConstraintViolation<?>> coll = v.getConstraintViolations(); 
                coll.stream().forEach((s) -> {
                   System.out.println(s.getPropertyPath() + " " + s.getMessage());
                });
                
                failedAssists.add(assist);
            } catch (Exception e) {        
                System.out.println(e.getMessage());
                
                failedAssists.add(assist);
            }                   
        }
                    
        return failedAssists;
    }
    
    public String deleteAssists(String assistIdsString) {
        
        AssistsModel assist = null;
        int[] assistIds = Arrays.asList(assistIdsString.split(","))
                      .stream()
                      .map(String::trim)
                      .mapToInt(Integer::parseInt).toArray();
        List<Integer> failedAssistIds = new ArrayList<>();
        
        for (int assistid : assistIds) {
            try {            
                assist = em.find(AssistsModel.class, assistid);
                PlayersModel assistPlayer = em.find(PlayersModel.class, assist.getPlayerid());
                assistPlayer.setAssists(assistPlayer.getAssists() - 1);
                em.persist(assistPlayer);
                em.remove(assist);
                em.flush();
            } catch (Exception e) {        
                System.out.println(e.getMessage());
                failedAssistIds.add(assistid);
            }     
        }        
        
        return failedAssistIds.size() > 0 ? failedAssistIds
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) : null;
    }
}