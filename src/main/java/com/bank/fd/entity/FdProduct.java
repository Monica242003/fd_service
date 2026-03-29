package com.bank.fd.entity;

import com.bank.fd.model.CompoundingType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class FdProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private BigDecimal minAmount;

    @NotNull
    private BigDecimal maxAmount;

    @NotNull
    private Integer tenorMinMonths;

    @NotNull
    private Integer tenorMaxMonths;

    @NotNull
    private BigDecimal annualRate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CompoundingType compounding;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getMinAmount() { return minAmount; }
    public void setMinAmount(BigDecimal minAmount) { this.minAmount = minAmount; }
    public BigDecimal getMaxAmount() { return maxAmount; }
    public void setMaxAmount(BigDecimal maxAmount) { this.maxAmount = maxAmount; }
    public Integer getTenorMinMonths() { return tenorMinMonths; }
    public void setTenorMinMonths(Integer tenorMinMonths) { this.tenorMinMonths = tenorMinMonths; }
    public Integer getTenorMaxMonths() { return tenorMaxMonths; }
    public void setTenorMaxMonths(Integer tenorMaxMonths) { this.tenorMaxMonths = tenorMaxMonths; }
    public BigDecimal getAnnualRate() { return annualRate; }
    public void setAnnualRate(BigDecimal annualRate) { this.annualRate = annualRate; }
    public CompoundingType getCompounding() { return compounding; }
    public void setCompounding(CompoundingType compounding) { this.compounding = compounding; }
}
