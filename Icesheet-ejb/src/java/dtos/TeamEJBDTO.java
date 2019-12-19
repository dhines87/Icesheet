/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.List;

/**
 *
 * @author David
 */
public class TeamEJBDTO {
    
    public TeamEJBDTO() {}
    
    private Integer teamid;
    private String name;
    private Integer wins;
    private Integer losses;
    private Integer ties;
    private List<PlayerEJBDTO> players;

    public List<PlayerEJBDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerEJBDTO> players) {
        this.players = players;
    }

    public Integer getTeamId() {
        return teamid;
    }

    public void setTeamId(Integer teamid) {
        this.teamid = teamid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public Integer getTies() {
        return ties;
    }

    public void setTies(Integer ties) {
        this.ties = ties;
    }
}
