package fi.joni.koodaustehtava.koodaustehtava.dto;

import java.math.BigDecimal;

public class RequestDto {
    private String transactionId;
    private String playerId;
    private BigDecimal amount;

    public RequestDto(String transactionId, String playerId, BigDecimal amount) {
        this.transactionId = transactionId;
        this.playerId = playerId;
        this.amount = amount;
    }

    public String gettransactionId() {
        return transactionId;
    }

    public void setPayId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
