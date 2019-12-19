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
@Table(name = "TEAMS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TeamsModel.findAll", query = "SELECT t FROM TeamsModel t"),
    @NamedQuery(name = "TeamsModel.findByTeamid", query = "SELECT t FROM TeamsModel t WHERE t.teamid = :teamid"),
    @NamedQuery(name = "TeamsModel.findByName", query = "SELECT t FROM TeamsModel t WHERE t.name = :name"),
    @NamedQuery(name = "TeamsModel.findByWins", query = "SELECT t FROM TeamsModel t WHERE t.wins = :wins"),
    @NamedQuery(name = "TeamsModel.findByLosses", query = "SELECT t FROM TeamsModel t WHERE t.losses = :losses"),
    @NamedQuery(name = "TeamsModel.findByTies", query = "SELECT t FROM TeamsModel t WHERE t.ties = :ties")})
public class TeamsModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TEAMID")
    private Integer teamid;
    @Size(max = 50)
    @Column(name = "NAME")
    private String name;
    @Column(name = "WINS")
    private Integer wins;
    @Column(name = "LOSSES")
    private Integer losses;
    @Column(name = "TIES")
    private Integer ties;

    public TeamsModel() {
    }

    public TeamsModel(Integer teamid) {
        this.teamid = teamid;
    }

    public TeamsModel(Integer teamid, String name, Integer wins, Integer losses, Integer ties) {
        this.teamid = teamid;
        this.name = name;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamid != null ? teamid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TeamsModel)) {
            return false;
        }
        TeamsModel other = (TeamsModel) object;
        if ((this.teamid == null && other.teamid != null) || (this.teamid != null && !this.teamid.equals(other.teamid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.TeamsModel[ teamid=" + teamid + " ]";
    }
    
}
