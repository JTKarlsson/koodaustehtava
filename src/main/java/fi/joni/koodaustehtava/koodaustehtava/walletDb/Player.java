package fi.joni.koodaustehtava.koodaustehtava.walletDb;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "player")
public class Player {

    @Id
    @Column(name = "playerId")
    private String playerId;

    @Column(name = "playerName")
    private String playerName;

    @Column(name = "balance")
    private String balance;

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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
