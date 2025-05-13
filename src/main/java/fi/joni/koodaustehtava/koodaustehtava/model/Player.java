package fi.joni.koodaustehtava.koodaustehtava.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name = "player")
public class Player {

    @Id
    @Column(name = "player_id", nullable = false, length = 255)
    private String playerId;

    @Column(name = "player_name", nullable = false, length = 255)
    private String playerName;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
