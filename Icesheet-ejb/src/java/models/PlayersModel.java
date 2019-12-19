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
@Table(name = "PLAYERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlayersModel.findAll", query = "SELECT p FROM PlayersModel p"),
    @NamedQuery(name = "PlayersModel.findByPlayerid", query = "SELECT p FROM PlayersModel p WHERE p.playerid = :playerid"),
    @NamedQuery(name = "PlayersModel.findByTeamid", query = "SELECT p FROM PlayersModel p WHERE p.teamid = :teamid"),
    @NamedQuery(name = "PlayersModel.findByFirstname", query = "SELECT p FROM PlayersModel p WHERE p.firstname = :firstname"),
    @NamedQuery(name = "PlayersModel.findByLastname", query = "SELECT p FROM PlayersModel p WHERE p.lastname = :lastname"),
    @NamedQuery(name = "PlayersModel.findByNumber", query = "SELECT p FROM PlayersModel p WHERE p.number = :number"),
    @NamedQuery(name = "PlayersModel.findByPosition", query = "SELECT p FROM PlayersModel p WHERE p.position = :position"),
    @NamedQuery(name = "PlayersModel.findByShoots", query = "SELECT p FROM PlayersModel p WHERE p.shoots = :shoots"),
    @NamedQuery(name = "PlayersModel.findByGoals", query = "SELECT p FROM PlayersModel p WHERE p.goals = :goals"),
    @NamedQuery(name = "PlayersModel.findByAssists", query = "SELECT p FROM PlayersModel p WHERE p.assists = :assists"),
    @NamedQuery(name = "PlayersModel.findByPims", query = "SELECT p FROM PlayersModel p WHERE p.pims = :pims"),
    @NamedQuery(name = "PlayersModel.findByWins", query = "SELECT p FROM PlayersModel p WHERE p.wins = :wins"),
    @NamedQuery(name = "PlayersModel.findBySavepercentage", query = "SELECT p FROM PlayersModel p WHERE p.savepercentage = :savepercentage")})
public class PlayersModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PLAYERID")
    private Integer playerid;
    @Column(name = "TEAMID")
    private Integer teamid;
    @Size(max = 50)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Size(max = 50)
    @Column(name = "LASTNAME")
    private String lastname;
    @Column(name = "NUMBER")
    private Integer number;
    @Size(max = 50)
    @Column(name = "POSITION")
    private String position;
    @Column(name = "SHOOTS")
    private Character shoots;
    @Column(name = "GOALS")
    private Integer goals;
    @Column(name = "ASSISTS")
    private Integer assists;
    @Column(name = "PIMS")
    private Integer pims;
    @Column(name = "WINS")
    private Integer wins;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SAVEPERCENTAGE")
    private Double savepercentage;

    public PlayersModel() {
    }

    public PlayersModel(Integer playerid, Integer teamid, String firstname, String lastname, Integer number, String position, Character shoots, Integer goals, Integer assists, Integer pims, Integer wins, Double savepercentage) {
        this.playerid = playerid;
        this.teamid = teamid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.number = number;
        this.position = position;
        this.shoots = shoots;
        this.goals = goals;
        this.assists = assists;
        this.pims = pims;
        this.wins = wins;
        this.savepercentage = savepercentage;
    }

    public PlayersModel(Integer playerid) {
        this.playerid = playerid;
    }

    public Integer getPlayerid() {
        return playerid;
    }

    public void setPlayerid(Integer playerid) {
        this.playerid = playerid;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
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

    public Double getSavepercentage() {
        return savepercentage;
    }

    public void setSavepercentage(Double savepercentage) {
        this.savepercentage = savepercentage;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playerid != null ? playerid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlayersModel)) {
            return false;
        }
        PlayersModel other = (PlayersModel) object;
        if ((this.playerid == null && other.playerid != null) || (this.playerid != null && !this.playerid.equals(other.playerid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.PlayersModel[ playerid=" + playerid + " ]";
    }
    
}
