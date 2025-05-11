package fi.joni.koodaustehtava.koodaustehtava.dto;

public class RequestDto {
    private String transactionId;
    private String playerId;
    private String amount;

    public RequestDto(String transactionId, String playerId, String amount) {
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
