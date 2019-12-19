package models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-12-19T13:40:28")
@StaticMetamodel(GamesModel.class)
public class GamesModel_ { 

    public static volatile SingularAttribute<GamesModel, Integer> gameid;
    public static volatile SingularAttribute<GamesModel, Date> date;
    public static volatile SingularAttribute<GamesModel, Integer> hometeamid;
    public static volatile SingularAttribute<GamesModel, Integer> visitorteamid;
    public static volatile SingularAttribute<GamesModel, Integer> hometeamgoals;
    public static volatile SingularAttribute<GamesModel, Integer> hometeamshots;
    public static volatile SingularAttribute<GamesModel, Integer> visitorteamshots;
    public static volatile SingularAttribute<GamesModel, Integer> visitorteamgoals;

}