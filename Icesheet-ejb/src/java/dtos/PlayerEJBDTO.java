/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author David
 */
public class PlayerEJBDTO {
 
    public PlayerEJBDTO() {}
    
    private Integer playerid;
    private String firstname;
    private String lastname;
    private Integer number;
    private String position;
    private Character shoots;
    private Integer teamid;
    private Integer goals;
    private Integer assists;
    private Integer pims;
    private Integer wins;
    private Double savepercentage;

    public Integer getPlayerid() {
        return playerid;
    }

    public void setPlayerid(Integer playerid) {
        this.playerid = playerid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Character getShoots() {
        return shoots;
    }

    public void setShoots(Character shoots) {
        this.shoots = shoots;
    }     
    
    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public Integer getGoals() {
        return goals;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists) {
        this.assists = assists;
    }

    public Integer getPims() {
        return pims;
    }

    public void setPims(Integer pims) {
        this.pims = pims;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Double getSavePercentage() {
        return savepercentage;
    }

    public void setSavePercentage(Double savepercentage) {
        this.savepercentage = savepercentage;
    }
}
