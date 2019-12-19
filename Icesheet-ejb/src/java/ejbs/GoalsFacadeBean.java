/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AssistEJBDTO;
import dtos.GoalEJBDTO;
import dtos.PlayerEJBDTO;
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
import models.AssistsModel;
import models.GamesModel;
import models.GoalsModel;
import models.PlayersModel;
import models.TeamsModel;
import ejbs.Extensions;
import helpers.Conversions;

/**
 *
 * @author David
 */
@Stateless
@LocalBean
public class GoalsFacadeBean {

    @PersistenceContext
    private EntityManager em;
    private List<PlayersModel> playersToUpdate = new ArrayList<>(); 
    private PlayersModel homeGoalie = null;
    private PlayersModel visitorGoalie = null;
    private TeamsModel homeTeam = null;
    private TeamsModel visitorTeam = null;
    private GamesModel gamesModel = null;
    
    public GoalEJBDTO createGoal(GoalEJBDTO goal) {

        GoalsModel goalsModel = null;
        this.playersToUpdate.clear();
        
        try {
            goalsModel = new GoalsModel(null, goal.getGameid(), goal.getPlayer().getPlayerid(), goal.getPeriod(), goal.getTime());             
            gamesModel = em.find(GamesModel.class, goal.getGameid());
            PlayersModel goalPlayer = em.find(PlayersModel.class, goal.getPlayer().getPlayerid());
            goalPlayer.setGoals(goalPlayer.getGoals() + 1);
            this.playersToUpdate.add(goalPlayer);                        
            this.UpdateGame(goal, "create");            
            em.persist(goalsModel);
            goal.setGoalid(goalsModel.getGoalid());
            em.flush();   
            
        } catch (ConstraintViolationException v) { 
            Set<ConstraintViolation<?>> coll = v.getConstraintViolations(); 
            coll.stream().forEach((s) -> {
                System.out.println(s.getPropertyPath() + " " + s.getMessage());
            });
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }
        
        goal.setGoalid(goalsModel.getGoalid());
        
        return goal;
    }
    
    public Integer deleteGoal(Integer goalid) {
        
        GoalsModel goal = null;
        this.playersToUpdate.clear();
        int rowsDeleted = -1;
        
        try {
            goal = em.find(GoalsModel.class, goalid);  
            gamesModel = em.find(GamesModel.class, goal.getGameid());
            PlayersModel goalPlayer = em.find(PlayersModel.class, goal.getPlayerid());
            goalPlayer.setGoals(goalPlayer.getGoals() - 1);
            this.playersToUpdate.add(goalPlayer);               
            em.remove(goal);
            Query query = em.createNamedQuery("AssistsModel.findByGoalid");
            query.setParameter("goalid", goalid);
            List<AssistsModel> prevAssists = query.getResultList();
            
            if (prevAssists != null && prevAssists.size() > 0) {
                
                for (AssistsModel assist : prevAssists) {
                    this.DeleteAssist(assist);
                }
            }
            
            this.UpdateGame(Conversions.convertGoalsModelToEJBDTO(goal, goalPlayer), "delete");  
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }
        
        em.flush();        
        return rowsDeleted;
    }
    
    private Integer DeleteAssist(AssistsModel assist) {
        Integer rowsUpdated = -1;
        
        try {
            PlayersModel player = em.find(PlayersModel.class, assist.getPlayerid());
            player.setAssists(player.getAssists() - 1);
            this.playersToUpdate = Extensions.addPlayerToPlayersToUpdate(this.playersToUpdate, player, "assists");
            // Delete prev assist
            em.remove(assist);
            rowsUpdated = 1;
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }    
        
        return rowsUpdated;
    }
    
    private void UpdatePlayers() {
        
        if (this.playersToUpdate.size() < 1) {
            return;
        }
        
        for (PlayersModel player : this.playersToUpdate) {
            try {
                em.persist(player);
                em.flush();                
            }
            catch (Exception e) {        
                System.out.println(e.getMessage());
            }
        }
    }
    
    private void UpdateGame(GoalEJBDTO goal, String operation) {
        
        boolean isPlayerOnHomeTeam = Extensions.isPlayerOnHomeTeam(gamesModel, goal);
        homeTeam = em.find(TeamsModel.class, isPlayerOnHomeTeam ? goal.getPlayer().getTeamid() : gamesModel.getHometeamid());            
        visitorTeam = em.find(TeamsModel.class, !isPlayerOnHomeTeam ? goal.getPlayer().getTeamid() : gamesModel.getVisitorteamid());            
        Query query = em.createQuery("SELECT p FROM PlayersModel p WHERE p.position = 'G' AND (p.teamid = :hometeamid OR p.teamid = :visitorteamid)");
        query.setParameter("hometeamid", homeTeam.getTeamid());
        query.setParameter("visitorteamid", visitorTeam.getTeamid());
        List<PlayersModel> goalies = query.getResultList();
        homeGoalie = goalies.stream().filter(player -> player.getTeamid().equals(homeTeam.getTeamid())).findFirst().orElse(null);
        visitorGoalie = goalies.stream().filter(player -> player.getTeamid().equals(visitorTeam.getTeamid())).findFirst().orElse(null);
        String prevHomeTeamResult = Extensions.HomeTeamWinLossTie(gamesModel);
        gamesModel = operation.equals("create") ? Extensions.incrementScore(gamesModel, goal, isPlayerOnHomeTeam) : Extensions.decrementScore(gamesModel, goal, isPlayerOnHomeTeam);
        String newHomeTeamResult = Extensions.HomeTeamWinLossTie(gamesModel);

        if (isPlayerOnHomeTeam) {
            visitorGoalie.setSavepercentage(Extensions.calculateSavePercentage(gamesModel.getHometeamshots(), gamesModel.getHometeamgoals()));
        } else {
            homeGoalie.setSavepercentage(Extensions.calculateSavePercentage(gamesModel.getVisitorteamshots(), gamesModel.getVisitorteamgoals()));
        }

        switch(prevHomeTeamResult) {
            case "win":
                if (newHomeTeamResult.equals("tie")) {
                    homeTeam.setWins(homeTeam.getWins() - 1);
                    homeTeam.setTies(homeTeam.getTies() + 1);
                    homeGoalie.setWins(homeGoalie.getWins() - 1);
                    visitorTeam.setLosses(homeTeam.getLosses() - 1);
                    visitorTeam.setTies(visitorTeam.getTies() + 1);
                    visitorGoalie.setWins(visitorGoalie.getWins() - 1);
                }
                break;
            case "loss":
                if (newHomeTeamResult.equals("tie")) {                            
                    homeTeam.setLosses(homeTeam.getLosses() - 1);
                    homeTeam.setTies(homeTeam.getTies() + 1);
                    homeGoalie.setWins(homeGoalie.getWins() - 1);
                    visitorTeam.setWins(homeTeam.getWins() - 1);
                    visitorTeam.setTies(visitorTeam.getTies() + 1);
                    visitorGoalie.setWins(visitorGoalie.getWins() - 1);
                }
                break;
            case "tie":                        
                homeTeam.setTies(homeTeam.getTies() - 1);
                visitorTeam.setTies(visitorTeam.getTies() - 1);

                if (newHomeTeamResult.equals("win")) {
                    homeTeam.setWins(homeTeam.getWins() + 1);
                    homeGoalie.setWins(homeGoalie.getWins() + 1);
                } else {
                    visitorTeam.setWins(visitorTeam.getWins() + 1);
                    visitorGoalie.setWins(visitorGoalie.getWins() + 1);
                }
                break;
        }

        this.playersToUpdate.add(homeGoalie);
        this.playersToUpdate.add(visitorGoalie);
        this.UpdatePlayers();

        em.persist(gamesModel);
        em.persist(homeTeam);
        em.persist(visitorTeam);
    }
    
}
