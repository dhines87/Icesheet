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
@Table(name = "PENALTIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PenaltiesModel.findAll", query = "SELECT p FROM PenaltiesModel p"),
    @NamedQuery(name = "PenaltiesModel.findByPenaltyid", query = "SELECT p FROM PenaltiesModel p WHERE p.penaltyid = :penaltyid"),
    @NamedQuery(name = "PenaltiesModel.findByGameid", query = "SELECT p FROM PenaltiesModel p WHERE p.gameid = :gameid"),
    @NamedQuery(name = "PenaltiesModel.findByPlayerid", query = "SELECT p FROM PenaltiesModel p WHERE p.playerid = :playerid"),
    @NamedQuery(name = "PenaltiesModel.findByPenalty", query = "SELECT p FROM PenaltiesModel p WHERE p.penalty = :penalty"),
    @NamedQuery(name = "PenaltiesModel.findByMinutes", query = "SELECT p FROM PenaltiesModel p WHERE p.minutes = :minutes"),
    @NamedQuery(name = "PenaltiesModel.findByPeriod", query = "SELECT p FROM PenaltiesModel p WHERE p.period = :period"),
    @NamedQuery(name = "PenaltiesModel.findByTime", query = "SELECT p FROM PenaltiesModel p WHERE p.time = :time")})
public class PenaltiesModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PENALTYID")
    private Integer penaltyid;
    @Column(name = "GAMEID")
    private Integer gameid;
    @Column(name = "PLAYERID")
    private Integer playerid;
    @Size(max = 50)
    @Column(name = "PENALTY")
    private String penalty;
    @Column(name = "MINUTES")
    private Integer minutes;
    @Column(name = "PERIOD")
    private Integer period;
    @Size(max = 50)
    @Column(name = "TIME")
    private String time;

    public PenaltiesModel() {
    }

    public PenaltiesModel(Integer penaltyid, Integer gameid, Integer playerid, String penalty, Integer minutes, Integer period, String time) {
        this.penaltyid = penaltyid;
        this.gameid = gameid;
        this.playerid = playerid;
        this.penalty = penalty;
        this.minutes = minutes;
        this.period = period;
        this.time = time;
    }

    public PenaltiesModel(Integer penaltyid) {
        this.penaltyid = penaltyid;
    }

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

    public Integer getPlayerid() {
        return playerid;
    }

    public void setPlayerid(Integer playerid) {
        this.playerid = playerid;
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
        hash += (penaltyid != null ? penaltyid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PenaltiesModel)) {
            return false;
        }
        PenaltiesModel other = (PenaltiesModel) object;
        if ((this.penaltyid == null && other.penaltyid != null) || (this.penaltyid != null && !this.penaltyid.equals(other.penaltyid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.PenaltiesModel[ penaltyid=" + penaltyid + " ]";
    }
    
}
