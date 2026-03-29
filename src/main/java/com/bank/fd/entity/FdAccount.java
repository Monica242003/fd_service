package com.bank.fd.entity;

import com.bank.fd.model.AccountStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class FdAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String customerId;

    @NotNull
    private Long productId;

    @NotNull
    private BigDecimal principal;

    @NotNull
    private Integer tenorMonths;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate maturityDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AccountStatus status;

    @NotNull
    private BigDecimal accruedInterest;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public BigDecimal getPrincipal() { return principal; }
    public void setPrincipal(BigDecimal principal) { this.principal = principal; }
    public Integer getTenorMonths() { return tenorMonths; }
    public void setTenorMonths(Integer tenorMonths) { this.tenorMonths = tenorMonths; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getMaturityDate() { return maturityDate; }
    public void setMaturityDate(LocalDate maturityDate) { this.maturityDate = maturityDate; }
    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }
    public BigDecimal getAccruedInterest() { return accruedInterest; }
    public void setAccruedInterest(BigDecimal accruedInterest) { this.accruedInterest = accruedInterest; }
}
