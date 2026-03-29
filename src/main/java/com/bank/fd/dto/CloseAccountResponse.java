package com.bank.fd.dto;

import java.math.BigDecimal;

public class CloseAccountResponse {
    private String message;
    private BigDecimal principal;
    private BigDecimal interestEarned;
    private BigDecimal penaltyApplied;
    private BigDecimal totalPayout;

    // Getters and setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public BigDecimal getPrincipal() { return principal; }
    public void setPrincipal(BigDecimal principal) { this.principal = principal; }
    public BigDecimal getInterestEarned() { return interestEarned; }
    public void setInterestEarned(BigDecimal interestEarned) { this.interestEarned = interestEarned; }
    public BigDecimal getPenaltyApplied() { return penaltyApplied; }
    public void setPenaltyApplied(BigDecimal penaltyApplied) { this.penaltyApplied = penaltyApplied; }
    public BigDecimal getTotalPayout() { return totalPayout; }
    public void setTotalPayout(BigDecimal totalPayout) { this.totalPayout = totalPayout; }
}
