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
public class AssistEJBDTO {
    
    private Integer gameid;
    private Integer assistid;
    private Integer goalid;
    private PlayerEJBDTO player;

    public Integer getGameid() {
        return gameid;
    }

    public PlayerEJBDTO getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEJBDTO player) {
        this.player = player;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public Integer getAssistid() {
        return assistid;
    }

    public void setAssistid(Integer assistid) {
        this.assistid = assistid;
    }

    public Integer getGoalid() {
        return goalid;
    }

    public void setGoalid(Integer goalid) {
        this.goalid = goalid;
    }
}
