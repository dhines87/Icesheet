/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "GOALS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GoalsModel.findAll", query = "SELECT g FROM GoalsModel g"),
    @NamedQuery(name = "GoalsModel.findByGoalid", query = "SELECT g FROM GoalsModel g WHERE g.goalid = :goalid"),
    @NamedQuery(name = "GoalsModel.findByGameid", query = "SELECT g FROM GoalsModel g WHERE g.gameid = :gameid"),
    @NamedQuery(name = "GoalsModel.findByPlayerid", query = "SELECT g FROM GoalsModel g WHERE g.playerid = :playerid"),
    @NamedQuery(name = "GoalsModel.findByPeriod", query = "SELECT g FROM GoalsModel g WHERE g.period = :period"),
    @NamedQuery(name = "GoalsModel.findByTime", query = "SELECT g FROM GoalsModel g WHERE g.time = :time")})
public class GoalsModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GOALID")
    private Integer goalid;
    @Column(name = "GAMEID")
    private Integer gameid;
    @Column(name = "PLAYERID")
    private Integer playerid;
    @Column(name = "PERIOD")
    private Integer period;
    @Size(max = 50)
    @Column(name = "TIME")
    private String time;

    public GoalsModel() {
    }

    public GoalsModel(Integer goalid, Integer gameid, Integer playerid, Integer period, String time) {
        this.goalid = goalid;
        this.gameid = gameid;
        this.playerid = playerid;
        this.period = period;
        this.time = time;
    }

    public GoalsModel(Integer goalid) {
        this.goalid = goalid;
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

    public Integer getPlayerid() {
        return playerid;
    }

    public void setPlayerid(Integer playerid) {
        this.playerid = playerid;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (goalid != null ? goalid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GoalsModel)) {
            return false;
        }
        GoalsModel other = (GoalsModel) object;
        if ((this.goalid == null && other.goalid != null) || (this.goalid != null && !this.goalid.equals(other.goalid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.GoalsModel[ goalid=" + goalid + " ]";
    }
    
}
