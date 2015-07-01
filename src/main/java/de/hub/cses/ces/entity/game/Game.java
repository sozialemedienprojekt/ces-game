package de.hub.cses.ces.entity.game;

/*
 * #%L
 * CES-Game
 * %%
 * Copyright (C) 2015 Humboldt-Universität zu Berlin,
 * Department of Computer Science,
 * Research Group "Computer Science Education / Computer Science and Society"
 * Sebastian Gross <sebastian.gross@hu-berlin.de>
 * Sven Strickroth <sven.strickroth@hu-berlin.de>
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
import de.hub.cses.ces.entity.BaseEntityWithIdentifier;
import de.hub.cses.ces.entity.economy.Economy;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Sebastian Gross <sebastian.gross@hu-berlin.de>
 */
@Entity
@Table(name = "game_tbl")
@NamedQueries({
    @NamedQuery(name = "Game.findByGameStatus", query = "SELECT g FROM Game g WHERE g.gameStatus = :gameStatus"),
    @NamedQuery(name = "Game.findRunningByGameTiming", query = "SELECT g FROM Game g WHERE g.gameTiming = :gameTiming AND g.gameStatus = :gameStatus"),
    @NamedQuery(name = "Game.findByIdentifer", query = "SELECT g FROM Game g WHERE g.identifier = :identifier")})
//@ValidateDateRange(startAt = "startAt", stopAt = "stopAt")
public class Game extends BaseEntityWithIdentifier {

    @Column(name = "seed_capital")
    @Min(value = 1000000, message = "{number} {min_number}")
    @Max(value = 100000000, message = "{number} {max_number}")
    private double seedCapital = 1000000;
    @Column(name = "password", length = 255)
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "{password} {wrong_characters}")
    private String password;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "economy")
    private Economy economy;
    @Enumerated(EnumType.STRING)
    @Column(name = "game_status", nullable = false)
    private GameStatus gameStatus = GameStatus.INITIALIZED;
    @Enumerated(EnumType.STRING)
    @Column(name = "game_timing", nullable = false)
    private GameTiming gameTiming = GameTiming.TEN_SECONDS;
    @Column(name = "start_at", insertable = true, updatable = false, nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startAt;
    @Column(name = "stop_at", insertable = true, updatable = false, nullable = false)
    @Temporal(TemporalType.DATE)
    private Date stopAt;
    @Column(name = "today")
    @Temporal(TemporalType.DATE)
    private Date today;

    /**
     *
     */
    public Game() {
    }

    /**
     *
     * @param identifier
     * @param seedCapital
     * @param password
     * @param economy
     * @param gameStatus
     * @param startAt
     * @param stopAt
     * @param today
     */
    public Game(String identifier, double seedCapital, String password, Economy economy, GameStatus gameStatus, Date startAt, Date stopAt, Date today) {
        super(identifier);
        this.seedCapital = seedCapital;
        this.password = password;
        this.economy = economy;
        this.gameStatus = gameStatus;
        this.startAt = startAt;
        this.stopAt = stopAt;
        this.today = today;
    }

    /**
     *
     * @return
     */
    public double getSeedCapital() {
        return seedCapital;
    }

    /**
     *
     * @param seedCapital
     */
    public void setSeedCapital(double seedCapital) {
        this.seedCapital = seedCapital;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public Economy getEconomy() {
        return economy;
    }

    /**
     *
     * @param economy
     */
    public void setEconomy(Economy economy) {
        this.economy = economy;
    }

    /**
     *
     * @return
     */
    public GameTiming getGameTiming() {
        return gameTiming;
    }

    /**
     *
     * @param gameTiming
     */
    public void setGameTiming(GameTiming gameTiming) {
        this.gameTiming = gameTiming;
    }

    /**
     *
     * @return
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     *
     * @param gameStatus
     */
    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    /**
     *
     * @return
     */
    public Date getStartAt() {
        return startAt;
    }

    /**
     *
     * @param startAt
     */
    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    /**
     *
     * @return
     */
    public Date getStopAt() {
        return stopAt;
    }

    /**
     *
     * @param stopAt
     */
    public void setStopAt(Date stopAt) {
        this.stopAt = stopAt;
    }

    /**
     *
     * @return
     */
    public Date getToday() {
        return today;
    }

    /**
     *
     * @param today
     */
    public void setToday(Date today) {
        this.today = today;
    }

}
