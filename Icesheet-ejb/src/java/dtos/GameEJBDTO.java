/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.Date;
import java.util.List;

/**
 *
 * @author David
 */
public class GameEJBDTO {
    
    private Integer gameid;
    private String gameDate;
    private TeamEJBDTO homeTeam;
    private TeamEJBDTO visitorTeam;
    private Integer homeTeamShots;
    private Integer visitorTeamShots;
    private Integer homeTeamScore;
    private Integer visitorTeamScore;
    private List<GoalEJBDTO> goals;
    private List<AssistEJBDTO> assists;
    private List<PenaltyEJBDTO> penalties;
    private GoalEJBDTO goalToUpdate;
    private List<AssistEJBDTO> updateAssists;
    private PenaltyEJBDTO penaltyToUpdate;

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public TeamEJBDTO getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(TeamEJBDTO homeTeam) {
        this.homeTeam = homeTeam;
    }

    public TeamEJBDTO getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(TeamEJBDTO visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public Integer getHomeTeamShots() {
        return homeTeamShots;
    }

    public void setHomeTeamShots(Integer homeTeamShots) {
        this.homeTeamShots = homeTeamShots;
    }

    public Integer getVisitorTeamShots() {
        return visitorTeamShots;
    }

    public void setVisitorTeamShots(Integer visitorTeamShots) {
        this.visitorTeamShots = visitorTeamShots;
    }

    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public Integer getVisitorTeamScore() {
        return visitorTeamScore;
    }

    public void setVisitorTeamScore(Integer visitorTeamScore) {
        this.visitorTeamScore = visitorTeamScore;
    }

    public List<GoalEJBDTO> getGoals() {
        return goals;
    }

    public void setGoals(List<GoalEJBDTO> goals) {
        this.goals = goals;
    }

    public List<AssistEJBDTO> getAssists() {
        return assists;
    }

    public void setAssists(List<AssistEJBDTO> assists) {
        this.assists = assists;
    }

    public List<PenaltyEJBDTO> getPenalties() {
        return penalties;
    }

    public void setPenalties(List<PenaltyEJBDTO> penalties) {
        this.penalties = penalties;
    }

    public GoalEJBDTO getGoalToUpdate() {
        return goalToUpdate;
    }

    public void setGoalToUpdate(GoalEJBDTO goalToUpdate) {
        this.goalToUpdate = goalToUpdate;
    }

    public List<AssistEJBDTO> getUpdateAssists() {
        return updateAssists;
    }

    public void setUpdateAssists(List<AssistEJBDTO> updateAssists) {
        this.updateAssists = updateAssists;
    }

    public PenaltyEJBDTO getPenaltyToUpdate() {
        return penaltyToUpdate;
    }

    public void setPenaltyToUpdate(PenaltyEJBDTO penaltyToUpdate) {
        this.penaltyToUpdate = penaltyToUpdate;
    }    
}
