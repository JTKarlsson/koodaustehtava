package fi.joni.koodaustehtava.koodaustehtava.walletDb;

import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "timestamp", nullable = false)
    private String timestamp;

    @Column(name = "transaction_type", nullable = false)
    private String transactionType;

    @Column(name = "amount", nullable = false)
    private String amount;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}

