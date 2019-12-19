/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.GameEJBDTO;
import dtos.GoalEJBDTO;
import dtos.TeamEJBDTO;
import java.util.List;
import models.GamesModel;
import models.PlayersModel;
import models.TeamsModel;

/**
 *
 * @author David
 */
public class Extensions {
    
    public static boolean isPlayerOnHomeTeam(GamesModel gamesModel, GoalEJBDTO goal) {
        return gamesModel.getHometeamid().equals(goal.getPlayer().getTeamid());
    }
    
    public static boolean isPlayerOnHomeTeam(GameEJBDTO gameEJBDTO, GoalEJBDTO goal) {
        return gameEJBDTO.getHomeTeam().getTeamId().equals(goal.getPlayer().getTeamid());
    }
    
    public static boolean isPlayerOnVisitorTeam(GamesModel gamesModel, GoalEJBDTO goal) {
        return gamesModel.getVisitorteamid().equals(goal.getPlayer().getTeamid());
    }
    
    public static boolean isPlayerOnVisitorTeam(GameEJBDTO gameEJBDTO, GoalEJBDTO goal) {
        return gameEJBDTO.getVisitorTeam().getTeamId().equals(goal.getPlayer().getTeamid());
    }
    
    public static String HomeTeamWinLossTie(GameEJBDTO game) {
        return game.getHomeTeamScore() > game.getVisitorTeamScore() ? 
                "win" : game.getHomeTeamScore() < game.getVisitorTeamScore() ? 
                    "loss" : "tie";
    }
    
    public static String HomeTeamWinLossTie(GamesModel game) {
        return game.getHometeamgoals() > game.getVisitorteamgoals()? 
                "win" : game.getHometeamgoals() < game.getVisitorteamgoals() ? 
                    "loss" : "tie";
    }
    
    public static Double calculateSavePercentage(Integer shots, Integer goals) {
        return (double) Math.round(((shots - goals) / shots) * 1000d) / 1000d;
    }
    
    public static GameEJBDTO incrementScore(GameEJBDTO game, GoalEJBDTO goal) {
        if (Extensions.isPlayerOnHomeTeam(game, goal)) {
            game.setHomeTeamScore(game.getHomeTeamScore() + 1); 
            game.setHomeTeamShots(game.getHomeTeamShots() + 1); 
        }
        else {
            game.setVisitorTeamScore(game.getVisitorTeamScore() + 1);
            game.setVisitorTeamShots(game.getVisitorTeamShots() + 1);
        }   
        
        return game;
    }
    
    public static GamesModel incrementScore(GamesModel game, GoalEJBDTO goal, boolean isPlayerOnHomeTeam) {
        if (isPlayerOnHomeTeam) {
            game.setHometeamgoals(game.getHometeamgoals() + 1); 
            game.setHometeamshots(game.getHometeamshots() + 1);
        }
        else {
            game.setVisitorteamgoals(game.getVisitorteamgoals() + 1);
            game.setVisitorteamshots(game.getVisitorteamshots() + 1);
        }   
        
        return game;
    }
    
    public static GameEJBDTO decrementScore(GameEJBDTO game, GoalEJBDTO goal) {
        if (Extensions.isPlayerOnHomeTeam(game, goal)) {
            game.setHomeTeamScore(game.getHomeTeamScore() - 1);  
            game.setHomeTeamShots(game.getHomeTeamShots() - 1); 
        }
        else {
            game.setVisitorTeamScore(game.getVisitorTeamScore() - 1);
            game.setVisitorTeamShots(game.getVisitorTeamShots() - 1);
        }   
        
        return game;
    }
    
    public static GamesModel decrementScore(GamesModel game, GoalEJBDTO goal, boolean isPlayerOnHomeTeam) {
        if (isPlayerOnHomeTeam) {
            game.setHometeamgoals(game.getHometeamgoals() - 1);  
            game.setHometeamshots(game.getHometeamshots() - 1);
        }
        else {
            game.setVisitorteamgoals(game.getVisitorteamgoals() - 1);
            game.setVisitorteamshots(game.getVisitorteamshots() - 1);
        }   
        
        return game;
    }
    
    public static TeamsModel incrementWin(TeamsModel team) {
        team.setWins(team.getWins() + 1);
        
        return team;
    }
    
    public static TeamsModel decrementWin(TeamsModel team) {
        team.setWins(team.getWins() - 1);
        
        return team;
    }
    
    public static TeamsModel incrementTies(TeamsModel team) {
        team.setTies(team.getTies() + 1);
        
        return team;
    }
    
    public static TeamsModel decrementTies(TeamsModel team) {
        team.setTies(team.getTies() - 1);
        
        return team;
    }
    
    public static TeamsModel incrementLosses(TeamsModel team) {
        team.setLosses(team.getLosses()+ 1);
        
        return team;
    }
    
    public static TeamsModel decrementLosses(TeamsModel team) {
        team.setLosses(team.getLosses() - 1);
        
        return team;
    }
    
    public static List<PlayersModel> addPlayerToPlayersToUpdate(List<PlayersModel> playersToUpdate, PlayersModel player, String propertyToUpdate) {
        
        if (playersToUpdate.size() < 1) {
            playersToUpdate.add(player);
        } else {
            for (PlayersModel p : playersToUpdate) {
                
                if (p.getPlayerid().equals(player.getPlayerid())) {
                    switch (propertyToUpdate) {
                        case "goals":
                            p.setGoals(player.getGoals());
                            break;
                        case "assists":
                           p.setAssists(player.getAssists());
                            break;
                        case "pims":
                            p.setPims(player.getPims());
                            break;
                    }
                    
                    break;
                }
            }
        
            playersToUpdate.add(player);
        }
        
        return playersToUpdate;
    }   
}
