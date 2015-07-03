package de.hub.cses.ces.entity.game;

import de.hub.cses.ces.entity.BaseEntityWithIdentifier_;
import de.hub.cses.ces.entity.economy.Economy;
import de.hub.cses.ces.entity.game.GameStatus;
import de.hub.cses.ces.entity.game.GameTiming;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-07-03T13:05:23")
@StaticMetamodel(Game.class)
public class Game_ extends BaseEntityWithIdentifier_ {

    public static volatile SingularAttribute<Game, Date> stopAt;
    public static volatile SingularAttribute<Game, String> password;
    public static volatile SingularAttribute<Game, GameStatus> gameStatus;
    public static volatile SingularAttribute<Game, Date> today;
    public static volatile SingularAttribute<Game, Double> seedCapital;
    public static volatile SingularAttribute<Game, Economy> economy;
    public static volatile SingularAttribute<Game, GameTiming> gameTiming;
    public static volatile SingularAttribute<Game, Date> startAt;

}