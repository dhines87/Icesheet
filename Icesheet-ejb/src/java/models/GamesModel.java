/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "GAMES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GamesModel.findAll", query = "SELECT g FROM GamesModel g"),
    @NamedQuery(name = "GamesModel.findByGameid", query = "SELECT g FROM GamesModel g WHERE g.gameid = :gameid"),
    @NamedQuery(name = "GamesModel.findByHometeamid", query = "SELECT g FROM GamesModel g WHERE g.hometeamid = :hometeamid"),
    @NamedQuery(name = "GamesModel.findByVisitorteamid", query = "SELECT g FROM GamesModel g WHERE g.visitorteamid = :visitorteamid"),
    @NamedQuery(name = "GamesModel.findByDate", query = "SELECT g FROM GamesModel g WHERE g.date = :date"),
    @NamedQuery(name = "GamesModel.findByHometeamshots", query = "SELECT g FROM GamesModel g WHERE g.hometeamshots = :hometeamshots"),
    @NamedQuery(name = "GamesModel.findByVisitorteamshots", query = "SELECT g FROM GamesModel g WHERE g.visitorteamshots = :visitorteamshots")})
public class GamesModel implements Serializable {
    @Column(name = "HOMETEAMGOALS")
    private Integer hometeamgoals;
    @Column(name = "VISITORTEAMGOALS")
    private Integer visitorteamgoals;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "GAMEID")
    private Integer gameid;
    @Column(name = "HOMETEAMID")
    private Integer hometeamid;
    @Column(name = "VISITORTEAMID")
    private Integer visitorteamid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "HOMETEAMSHOTS")
    private Integer hometeamshots;
    @Column(name = "VISITORTEAMSHOTS")
    private Integer visitorteamshots;

    public GamesModel() {
    }

    public GamesModel(Integer gameid, Integer hometeamid, Integer visitorteamid, Date date, Integer hometeamgoals, Integer visitorteamgoals, Integer hometeamshots, Integer visitorteamshots) {
        this.hometeamgoals = hometeamgoals;
        this.visitorteamgoals = visitorteamgoals;
        this.gameid = gameid;
        this.hometeamid = hometeamid;
        this.visitorteamid = visitorteamid;
        this.date = date;
        this.hometeamshots = hometeamshots;
        this.visitorteamshots = visitorteamshots;
    }
    

    public GamesModel(Integer gameid) {
        this.gameid = gameid;
    }

    public GamesModel(Integer gameid, Date date) {
        this.gameid = gameid;
        this.date = date;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public Integer getHometeamid() {
        return hometeamid;
    }

    public void setHometeamid(Integer hometeamid) {
        this.hometeamid = hometeamid;
    }

    public Integer getVisitorteamid() {
        return visitorteamid;
    }

    public void setVisitorteamid(Integer visitorteamid) {
        this.visitorteamid = visitorteamid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getHometeamshots() {
        return hometeamshots;
    }

    public void setHometeamshots(Integer hometeamshots) {
        this.hometeamshots = hometeamshots;
    }

    public Integer getVisitorteamshots() {
        return visitorteamshots;
    }

    public void setVisitorteamshots(Integer visitorteamshots) {
        this.visitorteamshots = visitorteamshots;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameid != null ? gameid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GamesModel)) {
            return false;
        }
        GamesModel other = (GamesModel) object;
        if ((this.gameid == null && other.gameid != null) || (this.gameid != null && !this.gameid.equals(other.gameid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.GamesModel[ gameid=" + gameid + " ]";
    }

    public Integer getHometeamgoals() {
        return hometeamgoals;
    }

    public void setHometeamgoals(Integer hometeamgoals) {
        this.hometeamgoals = hometeamgoals;
    }

    public Integer getVisitorteamgoals() {
        return visitorteamgoals;
    }

    public void setVisitorteamgoals(Integer visitorteamgoals) {
        this.visitorteamgoals = visitorteamgoals;
    }
    
}
