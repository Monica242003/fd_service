package com.bank.fd.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AccountRequest {
    @NotBlank
    private String customerId;
    @NotNull
    private Long productId;
    @NotNull
    private BigDecimal principal;
    @NotNull
    private Integer tenorMonths;

    // Getters and setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public BigDecimal getPrincipal() { return principal; }
    public void setPrincipal(BigDecimal principal) { this.principal = principal; }
    public Integer getTenorMonths() { return tenorMonths; }
    public void setTenorMonths(Integer tenorMonths) { this.tenorMonths = tenorMonths; }
}
