/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AssistEJBDTO;
import dtos.GameEJBDTO;
import dtos.GoalEJBDTO;
import dtos.PenaltyEJBDTO;
import dtos.PlayerEJBDTO;
import dtos.TeamEJBDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
import models.PenaltiesModel;
import models.PlayersModel;
import models.TeamsModel;
import helpers.Conversions;
import java.util.Objects;

/**
 *
 * @author David
 */
@Stateless
@LocalBean
public class GamesFacadeBean {

    @PersistenceContext
    private EntityManager em;
    private List<PlayersModel> playersToUpdate = new ArrayList<>();
    private GameEJBDTO game = null;
    private TeamsModel homeTeam = null;
    private TeamsModel visitorTeam = null;
    private PlayersModel homeGoalie = null;
    private PlayersModel visitorGoalie = null;
    
    public Integer createGame(GameEJBDTO game) {

        GamesModel gamesModel = null;
        
        try {
            gamesModel = new GamesModel(
                    null, 
                    game.getHomeTeam().getTeamId(), 
                    game.getVisitorTeam().getTeamId(), 
                    new Date(game.getGameDate()), 
                    game.getHomeTeamScore(), 
                    game.getVisitorTeamScore(), 
                    game.getHomeTeamShots(), 
                    game.getVisitorTeamShots()); 
            em.persist(gamesModel);
            em.flush();  
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
        
        return gamesModel.getGameid();
    }
    
    public List<GameEJBDTO> readGames() {
        List<TeamsModel> teams;
        List<GamesModel> games;
        List<GameEJBDTO> gameDtos = new ArrayList();
        
        try {            
            Query query = em.createNamedQuery("TeamsModel.findAll");
            teams = query.getResultList();
            
            query = em.createNamedQuery("GamesModel.findAll");
            games = query.getResultList();
            
            for (GamesModel game : games) {       
                TeamsModel homeTeamModel = filterTeamById(teams, game.getHometeamid());
                TeamsModel visitorTeamModel = filterTeamById(teams, game.getVisitorteamid());
                
                TeamEJBDTO homeTeam = new TeamEJBDTO();
                TeamEJBDTO visitorTeam = new TeamEJBDTO();
                
                homeTeam.setTeamid(homeTeamModel.getTeamid());
                homeTeam.setName(homeTeamModel.getName());
                
                visitorTeam.setTeamid(visitorTeamModel.getTeamid());
                visitorTeam.setName(visitorTeamModel.getName());
                
                GameEJBDTO dto = Conversions.convertGamesModelToEJBDTO(game, homeTeam, visitorTeam);
                
                gameDtos.add(dto);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return gameDtos;
    }       
    
    public GameEJBDTO readGameById(Integer gameid) {
                
        GameEJBDTO gameDto = new GameEJBDTO();
        
        if (gameid == -1) {
            gameDto.setGameDate(new Date().toString());
            gameDto.setHomeTeamScore(0);
            gameDto.setHomeTeamShots(0);
            gameDto.setVisitorTeamScore(0);
            gameDto.setVisitorTeamShots(0);
            gameDto.setHomeTeam(null);
            gameDto.setVisitorTeam(null);
            
            return gameDto;
        }
        
        GamesModel game = new GamesModel();
        List<GoalsModel> goals;
        List<AssistsModel> assists;
        List<PenaltiesModel> penalties;
        TeamEJBDTO homeTeam = new TeamEJBDTO();
        TeamEJBDTO visitorTeam = new TeamEJBDTO();
        
        
        try {
            Query query = em.createNamedQuery("GamesModel.findByGameid");
            query.setParameter("gameid", gameid);
            game = (GamesModel) query.getSingleResult();
            
            query = em.createQuery("SELECT t FROM TeamsModel t WHERE t.teamid = :visitorTeamId OR t.teamid = :homeTeamId");
            query.setParameter("homeTeamId", game.getHometeamid());
            query.setParameter("visitorTeamId", game.getVisitorteamid());
            List<TeamsModel> teams = query.getResultList();
            
            query = em.createNamedQuery("PlayersModel.findByTeamid");
            query.setParameter("teamid", game.getHometeamid());
            homeTeam = Conversions.convertTeamModelToEJBDTO(filterTeamById(teams, game.getHometeamid())); 
            List<PlayersModel> homePlayers = query.getResultList();
            homeTeam.setPlayers(Conversions.convertPlayerModelListToEJBDTO(homePlayers));            
            
            query = em.createNamedQuery("PlayersModel.findByTeamid");
            query.setParameter("teamid", game.getVisitorteamid());
            visitorTeam = Conversions.convertTeamModelToEJBDTO(filterTeamById(teams, game.getVisitorteamid()));
            List<PlayersModel> visitorPlayers = query.getResultList();
            visitorTeam.setPlayers(Conversions.convertPlayerModelListToEJBDTO(visitorPlayers));
            
            List<PlayersModel> players = homePlayers;
            players.addAll(visitorPlayers);
            
            query = em.createNamedQuery("GoalsModel.findByGameid");
            query.setParameter("gameid", gameid);
            goals = query.getResultList();
            
            query = em.createNamedQuery("AssistsModel.findByGameid");
            query.setParameter("gameid", gameid);
            assists = query.getResultList();
            
            query = em.createNamedQuery("PenaltiesModel.findByGameid");
            query.setParameter("gameid", gameid);
            penalties = query.getResultList();
            
            gameDto = Conversions.convertGamesModelToEJBDTO(game, homeTeam, visitorTeam, goals, assists, penalties, players);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return gameDto;
    }
    
    public GameEJBDTO UpdateGame(GameEJBDTO game) {
        this.game = game;
        this.playersToUpdate.clear();
        GoalEJBDTO goalToUpdate = this.game.getGoalToUpdate();        
        this.setTeamsAndGoalies(game);
        boolean updateGoalOrAssist = goalToUpdate != null || game.getUpdateAssists().size() > 0;
        
        try {
            if (goalToUpdate != null) {
                // Get previous goal to see if player changed.
                GoalsModel prevGoal = em.find(GoalsModel.class, goalToUpdate.getGoalid());
                
                if (prevGoal.getPlayerid() != goalToUpdate.getPlayer().getPlayerid()) {
                    PlayersModel prevGoalPlayer = em.find(PlayersModel.class, prevGoal.getPlayerid());
                    PlayersModel newGoalPlayer = em.find(PlayersModel.class, goalToUpdate.getPlayer().getPlayerid());            
                    // Decrement previous goal player goals property.
                    prevGoalPlayer.setGoals(prevGoalPlayer.getGoals() - 1);
                    // Increment new goal player goals property.
                    newGoalPlayer.setGoals(newGoalPlayer.getGoals() + 1);
                    // Add both player models to playersToUpdate.
                    this.playersToUpdate = Extensions.addPlayerToPlayersToUpdate(this.playersToUpdate, prevGoalPlayer, "goals");
                    this.playersToUpdate = Extensions.addPlayerToPlayersToUpdate(this.playersToUpdate, newGoalPlayer, "goals");
                    
                    // change team score
                    if (prevGoalPlayer.getTeamid() != newGoalPlayer.getTeamid()) {
                        String prevHomeTeamResult = Extensions.HomeTeamWinLossTie(game);
                        
                        if (Extensions.isPlayerOnHomeTeam(this.game, goalToUpdate)) {
                            this.game.setVisitorTeamScore(game.getVisitorTeamScore() - 1);
                            this.game.setVisitorTeamShots(game.getVisitorTeamShots() - 1);
                            this.game.setHomeTeamScore(game.getHomeTeamScore() + 1); 
                            this.game.setHomeTeamShots(game.getHomeTeamShots() + 1); 
                        }
                        else {
                            this.game.setVisitorTeamScore(game.getVisitorTeamScore() + 1);
                            this.game.setVisitorTeamShots(game.getVisitorTeamShots() + 1);
                            this.game.setHomeTeamScore(game.getHomeTeamScore() - 1); 
                            this.game.setHomeTeamShots(game.getHomeTeamShots() - 1);
                        }         

                        String newHomeTeamResult = Extensions.HomeTeamWinLossTie(game);
                        homeGoalie.setSavepercentage(Extensions.calculateSavePercentage(this.game.getVisitorTeamShots(), this.game.getVisitorTeamScore()));
                        visitorGoalie.setSavepercentage(Extensions.calculateSavePercentage(this.game.getHomeTeamShots(), this.game.getHomeTeamScore()));

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

                        em.persist(homeTeam);
                        em.persist(visitorTeam);
                        em.flush();
                        this.playersToUpdate.add(homeGoalie);
                        this.playersToUpdate.add(visitorGoalie);
                        
                    }
                }            
                
                // Update the goal.
                this.UpdateGoal(goalToUpdate);
                List<GoalEJBDTO> goals = this.game.getGoals();

                goals.stream()
                    .map(goal -> goal.getGoalid() == goalToUpdate.getGoalid() ? goalToUpdate : goal)
                    .collect(Collectors.toList());

                this.game.setGoals(goals);     
            }

            if (game.getUpdateAssists() != null && game.getUpdateAssists().size() > 0) {
                Query query = em.createNamedQuery("AssistsModel.findByGoalid");
                query.setParameter("goalid", goalToUpdate.getGoalid());
                List<AssistsModel> prevAssists = query.getResultList();
                
                if (prevAssists.size() < 1) {
                    
                    for (AssistEJBDTO assist : game.getUpdateAssists()) {
                        this.CreateAssist(assist);
                    }
                }
                else {
                    List<AssistEJBDTO> updateAssists = game.getUpdateAssists();
                    
                    for (AssistEJBDTO assist : updateAssists) {
                        
                        if (assist.getAssistid() == null) {
                            this.CreateAssist(assist);
                            continue;
                        }
                        
                        for (AssistsModel prevAssist : prevAssists) {
                            
                            if (Objects.equals(prevAssist.getAssistid(), assist.getAssistid())) {
                                
                                if (!Objects.equals(prevAssist.getPlayerid(), assist.getPlayer().getPlayerid())) {
                                    em.persist(Conversions.setAssistsModel(prevAssist, assist));                         
                                    this.IncrementAssistPlayer(prevAssist);
                                    this.DecrementAssistPlayer(Conversions.convertAssistEJBDTOToModel(assist));
                                }                                
                                
                                prevAssists.remove(prevAssist);
                                break;
                            }
                        }
                    }
                }
                
                if (prevAssists.size() > 0) {

                    for (AssistsModel assist : prevAssists) {
                        this.DeleteAssist(assist);
                    }
                }
            } 
            
            

            if (updateGoalOrAssist) {
                return this.UpdatePlayers();
            }

            PenaltyEJBDTO penaltyToUpdate = this.game.getPenaltyToUpdate();

            if (penaltyToUpdate != null) {
                // Get previous penalty.
                PenaltiesModel prevPenalty = em.find(PenaltiesModel.class, penaltyToUpdate.getPenaltyid());
                // Get previous penalty player model and new penalty player model.
                PlayersModel prevPenaltyPlayer = em.find(PlayersModel.class, prevPenalty.getPlayerid());            
                PlayersModel newPenaltyPlayer = em.find(PlayersModel.class, penaltyToUpdate.getPlayer().getPlayerid());             
                // Subtract previous penalty player pims property by prevPenalty.minutes.
                prevPenaltyPlayer.setPims(prevPenaltyPlayer.getPims() - prevPenalty.getMinutes());
                // Add new penalty player pims property by penaltyToUpdate.minutes.
                newPenaltyPlayer.setPims(newPenaltyPlayer.getPims() + penaltyToUpdate.getMinutes());
                // Add both player models to playersToUpdate.
                this.playersToUpdate = Extensions.addPlayerToPlayersToUpdate(this.playersToUpdate, prevPenaltyPlayer, "pims");            
                this.playersToUpdate = Extensions.addPlayerToPlayersToUpdate(this.playersToUpdate, newPenaltyPlayer, "pims");
                // Update penalty
                this.UpdatePenalty(penaltyToUpdate);

                return this.UpdatePlayers();
            }

            homeGoalie.setSavepercentage(Extensions.calculateSavePercentage(this.game.getVisitorTeamShots(), this.game.getVisitorTeamScore()));
            visitorGoalie.setSavepercentage(Extensions.calculateSavePercentage(this.game.getHomeTeamShots(), this.game.getHomeTeamScore()));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return this.UpdatePlayers();
    }    
    
    private void IncrementAssistPlayer(AssistsModel assist) {
        PlayersModel player = em.find(PlayersModel.class, assist.getPlayerid());
        player.setAssists(player.getAssists() + 1);
        this.playersToUpdate = Extensions.addPlayerToPlayersToUpdate(this.playersToUpdate, player, "assists");
    }
    
    private void DecrementAssistPlayer(AssistsModel assist) {
        PlayersModel player = em.find(PlayersModel.class, assist.getPlayerid());
        player.setAssists(player.getAssists() - 1);
        this.playersToUpdate = Extensions.addPlayerToPlayersToUpdate(this.playersToUpdate, player, "assists");
    }
    
    private GameEJBDTO UpdatePlayers() {       

        if (this.playersToUpdate.size() < 1) {
            return this.game;
        }
        
        for (PlayersModel player : this.playersToUpdate) {
            // Update player.
            try {
                em.persist(player);
                em.flush();
                
                this.game
                        .getHomeTeam()
                        .getPlayers()
                        .stream()
                        .map(p -> p.getPlayerid().equals(player.getPlayerid()) ? player : p);
                
                this.game
                        .getVisitorTeam()
                        .getPlayers()
                        .stream()
                        .map(p -> p.getPlayerid().equals(player.getPlayerid()) ? player : p);
                
            }
            catch (Exception e) {        
                System.out.println(e.getMessage());
            }
        }    
        

        GamesModel gamesModel = em.find(GamesModel.class, this.game.getGameid());
        gamesModel = Conversions.setGamesModel(gamesModel, this.game);
        em.persist(gamesModel);
        em.flush();

        return this.game;
    }
    
    private Integer UpdateGoal(GoalEJBDTO goal) {
        Integer rowsUpdated = -1;
        
        // Convert to Model.
        try {
            GoalsModel model = em.find(GoalsModel.class, goal.getGoalid());
            model = Conversions.setGoalsModel(model, goal);
            // Update goal.
            em.persist(model);
            em.flush();
            rowsUpdated = 1;            
            this.game
                    .getGoals()
                    .stream()
                    .map(g -> Objects.equals(g.getGoalid(), goal.getGoalid()) ? goal : g);
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }
        
        return rowsUpdated;
    }
    
    private Integer UpdatePenalty(PenaltyEJBDTO penalty) {
        Integer rowsUpdated = -1;
        
        // Convert to Model.
        try {
            PenaltiesModel model = em.find(PenaltiesModel.class, penalty.getPenaltyid());
            model = Conversions.setPenaltiesModel(model, penalty);
            // Update goal.
            em.persist(model);
            em.flush();
            this.game
                    .getPenalties()
                    .stream()
                    .map(p -> Objects.equals(p.getPenaltyid(), penalty.getPenaltyid()) ? penalty : p);
            rowsUpdated = 1;
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }
        
        return rowsUpdated;
    }
    
    private Integer CreateAssist(AssistEJBDTO assist) {
        Integer rowsUpdated = -1;
        AssistsModel model = new AssistsModel(null, assist.getGameid(), assist.getGoalid(), assist.getPlayer().getPlayerid());        
        
        try {
            this.IncrementAssistPlayer(model);
            em.persist(model);
            rowsUpdated = 1;
            assist.setAssistid(model.getAssistid());
            this.game.getAssists().add(assist);
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }        
        
        return rowsUpdated;
    }
    
    private Integer DeleteAssist(AssistsModel assist) {
        Integer rowsUpdated = -1;
        
        try {
            this.DecrementAssistPlayer(assist);
            // Delete prev assist
            em.remove(assist);
            this.game.getAssists().removeIf(a -> a.getAssistid().equals(assist.getAssistid()));
            rowsUpdated = 1;
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        }    
        
        return rowsUpdated;
    }
    
    private void DeleteTeamAndGoalieRecord(GameEJBDTO game) {
        
        this.setTeamsAndGoalies(game);
        
        try {       
            String prevHomeTeamResult = Extensions.HomeTeamWinLossTie(game);
            
            switch(prevHomeTeamResult) {
                case "win":
                    homeTeam.setWins(homeTeam.getWins() - 1);
                    visitorTeam.setLosses(visitorTeam.getLosses() - 1);
                    homeGoalie.setWins(homeGoalie.getWins() - 1);   
                    break;
                case "loss":
                    homeTeam.setLosses(homeTeam.getWins() - 1);
                    visitorTeam.setWins(visitorTeam.getLosses() - 1);                   
                    visitorGoalie.setWins(visitorGoalie.getWins() - 1);
                    break;
                case "tie":
                    homeTeam.setTies(homeTeam.getTies()- 1);
                    visitorTeam.setTies(visitorTeam.getTies() - 1);
                    break;
            }
            
            em.persist(homeTeam);
            em.persist(visitorTeam);
            em.flush();
        } catch (Exception e) {        
            System.out.println(e.getMessage());
        } 
    }
    
    
    
    private TeamsModel filterTeamById(List<TeamsModel> teams, Integer teamid) {
        return teams
                .stream()
                .filter(team -> teamid.equals(team.getTeamid()))
                .findAny()
                .orElse(null);
    }
    
    private List<PlayersModel> filterPlayersByTeamId(List<PlayersModel> players, Integer teamid) {
        
        return players
                .stream()
                .filter(player -> teamid.equals(player.getTeamid()))
                .collect(Collectors.toList());
    }
    
    private void setTeamsAndGoalies(GameEJBDTO game) {
        try {
            int homeGoalieId = this.game
                    .getHomeTeam()
                    .getPlayers()
                    .stream()
                    .filter(player -> player.getPosition().equals("G"))
                    .reduce((a, b) -> {
                        throw new IllegalStateException("Multiple elements: " + a + ", " + b);
                    })
                    .get()
                    .getPlayerid();
            
            int visitorGoalieId = this.game
                .getVisitorTeam()
                .getPlayers()
                .stream()
                .filter(player -> player.getPosition().equals("G"))
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple elements: " + a + ", " + b);
                })
                .get()
                .getPlayerid();    
            
            homeGoalie = em.find(PlayersModel.class, homeGoalieId);
            visitorGoalie = em.find(PlayersModel.class, visitorGoalieId);            
            homeTeam =  em.find(TeamsModel.class, this.game.getHomeTeam().getTeamId());
            visitorTeam = em.find(TeamsModel.class, this.game.getVisitorTeam().getTeamId());
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        
    }
}
