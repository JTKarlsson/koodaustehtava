package fi.joni.koodaustehtava.koodaustehtava.model;

import java.math.BigDecimal;

public class CurrentBalance {
    private BigDecimal currentBalance;

    public CurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }
}
