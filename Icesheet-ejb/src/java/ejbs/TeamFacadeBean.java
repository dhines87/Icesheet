/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.TeamEJBDTO;
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
import models.TeamsModel;

/**
 *
 * @author David
 */
@Stateless
@LocalBean
public class TeamFacadeBean {

    @PersistenceContext
    private EntityManager em;
    
    public String createTeam(TeamEJBDTO team) {

        TeamsModel model = null;
        
        try {
            model = new TeamsModel(null, team.getName(), 0, 0, 0);            
            em.persist(model);
            em.flush();  
        } catch (ConstraintViolationException v) { 
            Set<ConstraintViolation<?>> coll = v.getConstraintViolations(); 
            coll.stream().forEach((s) -> {
                System.out.println(s.getPropertyPath() + " " + s.getMessage());
            });
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }
        
        return (model.getName() != null || !model.getName().trim().isEmpty()) ? model.getName() : "ERROR";
    }
    
    public List<TeamEJBDTO> readTeams() {
        List<TeamsModel> teams;
        List<TeamEJBDTO> teamDtos = new ArrayList();
        
        try {
            Query query = em.createNamedQuery("TeamsModel.findAll");
            teams = query.getResultList();
            
            teams.stream().map((team) -> {
                TeamEJBDTO dto = new TeamEJBDTO();
                
                dto.setTeamId(team.getTeamid());
                dto.setName(team.getName());
                dto.setWins(team.getWins());
                dto.setLosses(team.getLosses());
                dto.setTies(team.getTies());
                
                return dto;
            }).forEach(teamDtos::add);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return teamDtos;
    }       
    
    public List<TeamEJBDTO> updateTeams(List<TeamEJBDTO> teams) {
        
        TeamsModel model = null;
        List<TeamEJBDTO> failedTeams = new ArrayList<>();
        
        for (TeamEJBDTO team : teams) {
            try {
                model = em.find(TeamsModel.class, team.getTeamId());            
                model.setTeamid(team.getTeamId());
                model.setName(team.getName());    
                model.setWins(team.getWins());
                model.setLosses(team.getLosses());
                model.setTies(team.getTies());
                em.flush();     
            } catch (Exception e) {        
                System.out.println(e.getMessage());
                failedTeams.add(team);
            }
        }       
        
        return failedTeams;
    }
    
    public Integer deleteTeam(TeamEJBDTO team) {
        
        TeamsModel model = null;
        int rowsDeleted = -1;
        
        try {
            model = em.find(TeamsModel.class, team.getTeamId());
            em.remove(model);
            rowsDeleted = 1;
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }
        
        return rowsDeleted;
    }
}
