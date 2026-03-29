package com.bank.fd.service;

import com.bank.fd.dto.AccountRequest;
import com.bank.fd.dto.CloseAccountResponse;
import com.bank.fd.entity.FdAccount;
import com.bank.fd.entity.FdProduct;
import com.bank.fd.model.AccountStatus;
import com.bank.fd.repository.FdAccountRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class FdAccountService {

    private final FdAccountRepository accountRepository;
    private final FdProductService productService;

    public FdAccountService(FdAccountRepository accountRepository, FdProductService productService) {
        this.accountRepository = accountRepository;
        this.productService = productService;
    }

    public FdAccount openAccount(AccountRequest request) {
        FdProduct product = productService.getProductById(request.getProductId());

        if (request.getPrincipal().compareTo(product.getMinAmount()) < 0 || 
            request.getPrincipal().compareTo(product.getMaxAmount()) > 0) {
            throw new IllegalArgumentException("Principal amount is outside the allowed range for this product.");
        }

        if (request.getTenorMonths() < product.getTenorMinMonths() || 
            request.getTenorMonths() > product.getTenorMaxMonths()) {
            throw new IllegalArgumentException("Tenor is outside the allowed range for this product.");
        }

        FdAccount account = new FdAccount();
        account.setCustomerId(request.getCustomerId());
        account.setProductId(product.getId());
        account.setPrincipal(request.getPrincipal());
        account.setTenorMonths(request.getTenorMonths());
        account.setStartDate(LocalDate.now());
        account.setMaturityDate(LocalDate.now().plusMonths(request.getTenorMonths()));
        account.setStatus(AccountStatus.ACTIVE);
        account.setAccruedInterest(BigDecimal.ZERO);

        return accountRepository.save(account);
    }

    public List<FdAccount> getAccounts(String customerId, AccountStatus status) {
        if (customerId != null && status != null) {
            return accountRepository.findByCustomerIdAndStatus(customerId, status);
        } else if (customerId != null) {
            return accountRepository.findByCustomerId(customerId);
        } else if (status != null) {
            return accountRepository.findByStatus(status);
        }
        return accountRepository.findAll();
    }
    
    public FdAccount getAccountById(Long id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
    }

    public FdAccount triggerAccrual(Long accountId) {
        FdAccount account = getAccountById(accountId);
        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new IllegalStateException("Cannot accrue interest on a closed account.");
        }

        FdProduct product = productService.getProductById(account.getProductId());
        
        long monthsElapsed = ChronoUnit.MONTHS.between(account.getStartDate(), LocalDate.now());
        if (monthsElapsed < 0) monthsElapsed = 0;
        if (monthsElapsed > account.getTenorMonths()) monthsElapsed = account.getTenorMonths();
        
        BigDecimal interest = calculateInterest(account.getPrincipal(), product.getAnnualRate(), product.getCompounding(), (int) monthsElapsed);
        account.setAccruedInterest(interest);
        return accountRepository.save(account);
    }

    public CloseAccountResponse closeAccount(Long accountId) {
        FdAccount account = getAccountById(accountId);
        if (account.getStatus() == AccountStatus.CLOSED) {
            throw new IllegalStateException("Account is already closed.");
        }

        FdProduct product = productService.getProductById(account.getProductId());
        LocalDate today = LocalDate.now();

        boolean isPremature = today.isBefore(account.getMaturityDate());
        long monthsElapsed = ChronoUnit.MONTHS.between(account.getStartDate(), today);
        if (monthsElapsed < 0) monthsElapsed = 0;
        if (monthsElapsed > account.getTenorMonths()) monthsElapsed = account.getTenorMonths();

        BigDecimal interestEarned = calculateInterest(account.getPrincipal(), product.getAnnualRate(), product.getCompounding(), (int) monthsElapsed);
        BigDecimal penalty = BigDecimal.ZERO;

        if (isPremature) {
            penalty = account.getPrincipal().multiply(new BigDecimal("0.01")).setScale(2, RoundingMode.HALF_UP);
        }

        account.setAccruedInterest(interestEarned);
        account.setStatus(AccountStatus.CLOSED);
        accountRepository.save(account);

        BigDecimal totalPayout = account.getPrincipal().add(interestEarned).subtract(penalty).setScale(2, RoundingMode.HALF_UP);

        CloseAccountResponse response = new CloseAccountResponse();
        response.setMessage(isPremature ? "Account closed prematurely with penalty." : "Account successfully matured and closed.");
        response.setPrincipal(account.getPrincipal());
        response.setInterestEarned(interestEarned.setScale(2, RoundingMode.HALF_UP));
        response.setPenaltyApplied(penalty);
        response.setTotalPayout(totalPayout);

        return response;
    }

    private BigDecimal calculateInterest(BigDecimal principal, BigDecimal annualRate, com.bank.fd.model.CompoundingType compounding, int months) {
        if (months == 0) return BigDecimal.ZERO;

        BigDecimal rateDecimal = annualRate.divide(new BigDecimal("100"), 6, RoundingMode.HALF_UP);
        int periodsPerYear = 1;
        switch (compounding) {
            case MONTHLY: periodsPerYear = 12; break;
            case QUARTERLY: periodsPerYear = 4; break;
            case YEARLY: periodsPerYear = 1; break;
        }

        BigDecimal ratePerPeriod = rateDecimal.divide(new BigDecimal(periodsPerYear), 6, RoundingMode.HALF_UP);
        double totalPeriods = (double) months / 12.0 * periodsPerYear;

        BigDecimal base = BigDecimal.ONE.add(ratePerPeriod);
        BigDecimal basePowered = new BigDecimal(Math.pow(base.doubleValue(), totalPeriods));
        
        BigDecimal amount = principal.multiply(basePowered);
        return amount.subtract(principal).setScale(2, RoundingMode.HALF_UP);
    }
}
