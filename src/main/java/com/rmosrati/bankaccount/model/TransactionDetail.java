package com.rmosrati.bankaccount.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class TransactionDetail {

    private Date transactionAt;
    private TransactionType transactionType;
    private double amount;
    private double balance;

}
