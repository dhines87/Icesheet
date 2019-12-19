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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David
 */
@Entity
@Table(name = "ASSISTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AssistsModel.findAll", query = "SELECT a FROM AssistsModel a"),
    @NamedQuery(name = "AssistsModel.findByAssistid", query = "SELECT a FROM AssistsModel a WHERE a.assistid = :assistid"),
    @NamedQuery(name = "AssistsModel.findByGameid", query = "SELECT a FROM AssistsModel a WHERE a.gameid = :gameid"),
    @NamedQuery(name = "AssistsModel.findByGoalid", query = "SELECT a FROM AssistsModel a WHERE a.goalid = :goalid"),
    @NamedQuery(name = "AssistsModel.findByPlayerid", query = "SELECT a FROM AssistsModel a WHERE a.playerid = :playerid")})
public class AssistsModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ASSISTID")
    private Integer assistid;
    @Column(name = "GAMEID")
    private Integer gameid;
    @Column(name = "GOALID")
    private Integer goalid;
    @Column(name = "PLAYERID")
    private Integer playerid;

    public AssistsModel() {
    }

    public AssistsModel(Integer assistid, Integer gameid, Integer goalid, Integer playerid) {
        this.assistid = assistid;
        this.gameid = gameid;
        this.goalid = goalid;
        this.playerid = playerid;
    }

    public AssistsModel(Integer assistid) {
        this.assistid = assistid;
    }

    public Integer getAssistid() {
        return assistid;
    }

    public void setAssistid(Integer assistid) {
        this.assistid = assistid;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public Integer getGoalid() {
        return goalid;
    }

    public void setGoalid(Integer goalid) {
        this.goalid = goalid;
    }

    public Integer getPlayerid() {
        return playerid;
    }

    public void setPlayerid(Integer playerid) {
        this.playerid = playerid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (assistid != null ? assistid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssistsModel)) {
            return false;
        }
        AssistsModel other = (AssistsModel) object;
        if ((this.assistid == null && other.assistid != null) || (this.assistid != null && !this.assistid.equals(other.assistid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.AssistsModel[ assistid=" + assistid + " ]";
    }
    
}
