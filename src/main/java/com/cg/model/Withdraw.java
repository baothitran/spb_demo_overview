package com.cg.model;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "withdraws")
public class Withdraw extends BaseEntity implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // referencedColumnName: tham chiếu tới cột id của bảng customer
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @Column(precision = 10, scale = 0, nullable = false)
    private BigDecimal transactionAmount;

    public Withdraw() {
    }

    public Withdraw(Long id, Customer customer, BigDecimal transactionAmount) {
        this.id = id;
        this.customer = customer;
        this.transactionAmount = transactionAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public boolean supports(Class<?> aClass) {

        return Customer.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Withdraw withdraw = (Withdraw) target;

        BigDecimal transactionAmount = withdraw.getTransactionAmount();
        BigDecimal maxTransactionAmount = BigDecimal.valueOf(0L);
        BigDecimal currentBalance = withdraw.getCustomer().getBalance();

        if (transactionAmount == null) {
            errors.rejectValue("transactionAmount", "transactionAmountWithdraw.null");
        } else {
            BigDecimal updateBalance = withdraw.getCustomer().getBalance().add(transactionAmount);

            if (transactionAmount.compareTo(currentBalance) > 0) {
                errors.rejectValue("transactionAmount", "transactionAmountWithdraw.insufficient");
            }
            if (transactionAmount.compareTo(maxTransactionAmount) < 0) {
                errors.rejectValue("transactionAmount", "transactionAmountWithdraw.min");
            }
        }
    }
}