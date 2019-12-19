/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.sun.istack.Nullable;
import dtos.AssistEJBDTO;
import dtos.GameEJBDTO;
import dtos.GoalEJBDTO;
import dtos.PenaltyEJBDTO;
import dtos.PlayerEJBDTO;
import dtos.TeamEJBDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import models.AssistsModel;
import models.GamesModel;
import models.GoalsModel;
import models.PenaltiesModel;
import models.PlayersModel;
import models.TeamsModel;

/**
 *
 * @author davidh
 */
public class Conversions {
    
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH);
    
    public static TeamEJBDTO convertTeamModelToEJBDTO(TeamsModel team) {
        TeamEJBDTO dto = new TeamEJBDTO();
        
        dto.setTeamid(team.getTeamid());
        dto.setName(team.getName());
        dto.setWins(team.getWins());
        dto.setLosses(team.getLosses());
        dto.setTies(team.getTies());
        
        return dto;
    }
    
    public static List<PlayerEJBDTO> convertPlayerModelListToEJBDTO(List<PlayersModel> players) {
        
        List<PlayerEJBDTO> dto = new ArrayList();
        
        for (PlayersModel player : players) {
            dto.add(convertPlayerModelToEJBDTO(player));
        }
        
        return dto;
    }
    
    public static PlayerEJBDTO convertPlayerModelToEJBDTO(PlayersModel player) {
        PlayerEJBDTO dto = new PlayerEJBDTO();
        
        dto.setPlayerid(player.getPlayerid());
        dto.setTeamid(player.getTeamid());
        dto.setFirstname(player.getFirstname());
        dto.setLastname(player.getLastname());
        dto.setNumber(player.getNumber());
        dto.setPosition(player.getPosition());
        dto.setShoots(player.getShoots());
        dto.setAssists(player.getAssists());
        dto.setGoals(player.getGoals());
        dto.setPims(player.getPims());
        dto.setSavePercentage(player.getSavepercentage());
        dto.setWins(player.getWins());
        
        return dto;
    }
    
    public static List<PlayersModel> convertPlayerEJBDTOListToModel(List<PlayerEJBDTO> players) {
        
        List<PlayersModel> model = new ArrayList<>();
        
        for (PlayerEJBDTO player : players) {
            model.add(convertPlayerEJBDTOToModel(player));
        }
        
        return model;
    }
    
    public static PlayersModel convertPlayerEJBDTOToModel(PlayerEJBDTO player) {
        PlayersModel model = new PlayersModel();
        
        model.setPlayerid(player.getPlayerid());
        model.setFirstname(player.getFirstname());
        
        return model;
    }
    
     public static PlayersModel convertEJBDTOToModel(PlayerEJBDTO player) {
        PlayersModel playersModel = new PlayersModel();
        
        playersModel.setPlayerid(player.getPlayerid());
        playersModel.setTeamid(player.getTeamid());
        playersModel.setFirstname(player.getFirstname());
        playersModel.setLastname(player.getLastname());
        playersModel.setNumber(player.getNumber());
        playersModel.setPosition(player.getPosition());
        playersModel.setShoots(player.getShoots());
        playersModel.setGoals(player.getGoals());
        playersModel.setAssists(player.getAssists());
        playersModel.setPims(player.getPims());
        playersModel.setWins(player.getWins());
        playersModel.setSavepercentage(player.getSavePercentage());
        
        return playersModel;
    }
    
    public static List<GoalEJBDTO> convertGoalModelListToEJBDTO(List<GoalsModel> goals, List<PlayersModel> players) {
        List<GoalEJBDTO> dto = new ArrayList();
        
        for (GoalsModel goal : goals) {
            dto.add(convertGoalsModelToEJBDTO(goal, filterPlayerByPlayerId(players, goal.getPlayerid())));
        }
        
        return dto;
    }
    
    public static GoalEJBDTO convertGoalsModelToEJBDTO(GoalsModel goal, PlayersModel player) {
        GoalEJBDTO dto = new GoalEJBDTO();
        
        dto.setPlayer(convertPlayerModelToEJBDTO(player));
        dto.setGoalid(goal.getGoalid());
        dto.setGameid(goal.getGameid());
        dto.setPeriod(goal.getPeriod());
        dto.setTime(goal.getTime());
        
        return dto;
    }
    
    public static GoalsModel convertGoalsEJBDTOToModel(GoalEJBDTO goal) {
        GoalsModel model = new GoalsModel();
        
        model.setPlayerid(goal.getPlayer().getPlayerid());
        model.setGoalid(goal.getGoalid());
        model.setGameid(goal.getGameid());
        model.setPeriod(goal.getPeriod());
        model.setTime(goal.getTime());
        
        return model;
    }
    
    public static List<AssistEJBDTO> convertAssistModelListToEJBDTO(List<AssistsModel> assists, List<PlayersModel> players) {
        List<AssistEJBDTO> dto = new ArrayList();
        
        for (AssistsModel assist : assists) {
            dto.add(convertAssistModelToEJBDTO(assist, filterPlayerByPlayerId(players, assist.getPlayerid())));
        }
        
        return dto;
    }
    
    public static AssistEJBDTO convertAssistModelToEJBDTO(AssistsModel assist, PlayersModel player) {
        AssistEJBDTO dto = new AssistEJBDTO();
        
        dto.setPlayer(convertPlayerModelToEJBDTO(player));
        dto.setGoalid(assist.getGoalid());
        dto.setAssistid(assist.getAssistid());
        dto.setGameid(assist.getGameid());
        
        return dto;
    }
    
    public static AssistsModel convertAssistEJBDTOToModel(AssistEJBDTO assist) {
        AssistsModel model = new AssistsModel();
        
        model.setGameid(assist.getGameid());
        model.setPlayerid(assist.getPlayer().getPlayerid());
        model.setAssistid(assist.getAssistid());
        model.setGoalid(assist.getGoalid());
        
        return model;
    }
    
    public static List<PenaltyEJBDTO> convertPenaltyModelListToEJBDTO(List<PenaltiesModel> penalties, List<PlayersModel> players) {
        List<PenaltyEJBDTO> dto = new ArrayList();
        
        for (PenaltiesModel penalty : penalties) {
            dto.add(convertPenaltyModelToEJBDTO(penalty, filterPlayerByPlayerId(players, penalty.getPlayerid())));
        }
        
        return dto;
    }
    
    public static PenaltyEJBDTO convertPenaltyModelToEJBDTO(PenaltiesModel penalty, PlayersModel player) {
        PenaltyEJBDTO dto = new PenaltyEJBDTO();
        
        dto.setPlayer(convertPlayerModelToEJBDTO(player));
        dto.setPenaltyid(penalty.getPenaltyid());
        dto.setGameid(penalty.getGameid());
        dto.setPeriod(penalty.getPeriod());
        dto.setTime(penalty.getTime());
        dto.setMinutes(penalty.getMinutes());
        dto.setPenalty(penalty.getPenalty());
        
        return dto;
    }
    
    public static PenaltiesModel convertPenaltlyEJBDTOToModel(PenaltyEJBDTO penalty) {
        PenaltiesModel model = new PenaltiesModel();
        
        model.setPlayerid(penalty.getPlayer().getPlayerid());
        model.setPenaltyid(penalty.getPenaltyid());
        model.setGameid(penalty.getGameid());
        model.setPeriod(penalty.getPeriod());
        model.setTime(penalty.getTime());
        model.setMinutes(penalty.getMinutes());
        model.setPenalty(penalty.getPenalty());
        
        return model;
    }
    public static GameEJBDTO convertGamesModelToEJBDTO(GamesModel game, TeamEJBDTO homeTeam, TeamEJBDTO visitorTeam) {
        GameEJBDTO dto = new GameEJBDTO();
        
        dto.setGameid(game.getGameid());
        dto.setGameDate(game.getDate().toString());
        dto.setHomeTeam(homeTeam);
        dto.setHomeTeamShots(game.getHometeamshots());
        dto.setVisitorTeam(visitorTeam);
        dto.setVisitorTeamShots(game.getVisitorteamshots());            
        dto.setHomeTeamScore(game.getHometeamgoals());
        dto.setVisitorTeamScore(game.getVisitorteamgoals());
        
        return dto;
    }
    
    public static GameEJBDTO convertGamesModelToEJBDTO(GamesModel game, TeamEJBDTO homeTeam, TeamEJBDTO visitorTeam, List<GoalsModel> goals, List<AssistsModel> assists, List<PenaltiesModel> penalties, List<PlayersModel> players) {
        GameEJBDTO dto = new GameEJBDTO();
        
        dto.setGameid(game.getGameid());
        dto.setGameDate(game.getDate().toString());
        dto.setHomeTeam(homeTeam);
        dto.setHomeTeamShots(game.getHometeamshots());
        dto.setVisitorTeam(visitorTeam);
        dto.setVisitorTeamShots(game.getVisitorteamshots());            
        dto.setHomeTeamScore(game.getHometeamgoals());
        dto.setVisitorTeamScore(game.getVisitorteamgoals());
        dto.setGoals(convertGoalModelListToEJBDTO(goals, players));
        dto.setAssists(convertAssistModelListToEJBDTO(assists, players));
        dto.setPenalties(convertPenaltyModelListToEJBDTO(penalties, players));
        
        return dto;
    }
    
    public static GamesModel convertGamesEJBDTOToModel(GameEJBDTO game) {
        GamesModel model = new GamesModel();
        Date gameDate = null;
        
        try {
            gameDate = formatter.parse(game.getGameDate());
        } catch (Exception e) {
            
        }  
        
        model.setGameid(game.getGameid());
        model.setDate(gameDate);
        model.setHometeamid(game.getHomeTeam().getTeamid());
        model.setVisitorteamid(game.getVisitorTeam().getTeamid());
        model.setHometeamshots(game.getHomeTeamShots());
        model.setVisitorteamshots(game.getVisitorTeamShots());
        model.setHometeamgoals(game.getHomeTeamScore());
        model.setVisitorteamgoals(game.getVisitorTeamScore());
        
        return model;
    }
    
    public static List<PlayerEJBDTO> convertListModelToEJBDTO(List<PlayersModel> players) {
        
        List<PlayerEJBDTO> dtos = new ArrayList<>();
        
        for(PlayersModel player : players) {            
            dtos.add(Conversions.convertModelToEJBDTO(player));
        }
    
        return dtos;
    }
    
    public static PlayerEJBDTO convertModelToEJBDTO(PlayersModel player) {
        PlayerEJBDTO dto = new PlayerEJBDTO();
        
        dto.setPlayerid(player.getPlayerid());
        dto.setTeamid(player.getTeamid());
        dto.setFirstname(player.getFirstname());
        dto.setLastname(player.getLastname());
        dto.setNumber(player.getNumber());
        dto.setPosition(player.getPosition());
        dto.setShoots(player.getShoots());
        dto.setAssists(player.getAssists());
        dto.setGoals(player.getGoals());
        dto.setPims(player.getPims());
        dto.setSavePercentage(player.getSavepercentage());
        dto.setWins(player.getWins());
        
        return dto;
    }
    
    public static GoalsModel setGoalsModel(GoalsModel model, GoalEJBDTO goal) {
        
        model.setPlayerid(goal.getPlayer().getPlayerid());
        model.setGameid(goal.getGameid());
        model.setPeriod(goal.getPeriod());
        model.setTime(goal.getTime());
        
        return model;
    }
    
    public static GamesModel setGamesModel(GamesModel model, GameEJBDTO game) {
        
        model.setHometeamid(game.getHomeTeam().getTeamid());
        model.setVisitorteamid(game.getVisitorTeam().getTeamid());
        model.setHometeamshots(game.getHomeTeamShots());
        model.setVisitorteamshots(game.getVisitorTeamShots());
        model.setHometeamgoals(game.getHomeTeamScore());
        model.setVisitorteamgoals(game.getVisitorTeamScore());
        
        return model;
    }
    
    public static AssistsModel setAssistsModel(AssistsModel model, AssistEJBDTO assist) {
        
        model.setPlayerid(assist.getPlayer().getPlayerid());
        model.setGoalid(assist.getGoalid());
        
        return model;
    }
    
    public static PlayersModel setPlayersModel(PlayersModel model, PlayerEJBDTO player) {
        
        model.setFirstname(player.getFirstname());
        model.setLastname(player.getLastname());
        model.setNumber(player.getNumber());
        model.setPosition(player.getPosition());
        model.setShoots(player.getShoots());
        model.setTeamid(player.getTeamid());
        model.setPims(player.getPims());
        model.setGoals(player.getGoals());
        model.setAssists(player.getAssists());
        model.setSavepercentage(player.getSavePercentage());
        model.setWins(player.getWins());
        
        return model;
    }
    
    public static PenaltiesModel setPenaltiesModel(PenaltiesModel model, PenaltyEJBDTO penalty) {
        
        model.setPenaltyid(penalty.getPenaltyid());
        model.setGameid(penalty.getGameid());
        model.setPlayerid(penalty.getPlayer().getPlayerid());
        model.setPenalty(penalty.getPenalty());
        model.setMinutes(penalty.getMinutes());
        model.setPeriod(penalty.getPeriod());
        model.setTime(penalty.getTime());
        
        return model;
    }
    
    private static PlayersModel filterPlayerByPlayerId(List<PlayersModel> players, Integer playerid) {
        
        return players
                .stream()
                .filter(player -> playerid.equals(player.getPlayerid()))
                .findAny()
                .orElse(null);
    }
}
