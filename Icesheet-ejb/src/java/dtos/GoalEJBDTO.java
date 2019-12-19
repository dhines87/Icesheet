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
public class GoalEJBDTO {
    
    private Integer goalid;
    private Integer gameid;
    private PlayerEJBDTO player;
    private Integer period;
    private String time;
    private List<AssistEJBDTO> assists;
    
    public List<AssistEJBDTO> getAssists() {
        return assists;
    }

    public PlayerEJBDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEJBDTO player) {
        this.player = player;
    }

    public void setAssists(List<AssistEJBDTO> assists) {
        this.assists = assists;
    }

    public Integer getGoalid() {
        return goalid;
    }

    public void setGoalid(Integer goalid) {
        this.goalid = goalid;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
