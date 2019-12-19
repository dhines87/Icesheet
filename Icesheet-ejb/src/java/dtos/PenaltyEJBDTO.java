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
public class PenaltyEJBDTO {
    
    private Integer penaltyid;
    private Integer gameid;
    private Integer period;
    private String time;
    private String penalty;
    private Integer minutes;
    private PlayerEJBDTO player;

    public Integer getPenaltyid() {
        return penaltyid;
    }

    public void setPenaltyid(Integer penaltyid) {
        this.penaltyid = penaltyid;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public PlayerEJBDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEJBDTO player) {
        this.player = player;
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

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }
}
