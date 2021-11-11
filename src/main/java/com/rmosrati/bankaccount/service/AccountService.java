package com.rmosrati.bankaccount.service;

import com.rmosrati.bankaccount.model.TransactionDetail;
import com.rmosrati.bankaccount.model.TransactionType;
import com.rmosrati.bankaccount.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountService {

    public static final String DELIMITER = "|";
    public static final String TRANSACTION_HISTORY_HEADER =
            "Operation " + DELIMITER + "Date " + DELIMITER + "Amount " + DELIMITER + "Balance";

    public double balance = 0;
    private List<TransactionDetail> transactionDetails = new ArrayList<>();

    public double deposit(double amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("The amount should be positive");
        }

        balance +=amount;
        saveTransaction(amount, TransactionType.DEPOSIT);
        return balance;
    }

    public double withdraw(double amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("The amount should be positive");
        }

        balance -= amount;

        saveTransaction(amount, TransactionType.WITHDRAW);
        return balance;
    }

    public List<TransactionDetail> getTransactionDetails(){
        return transactionDetails;
    }

    public String displayHistoryOfTransaction() {
        StringBuilder accountStatement = new StringBuilder(TRANSACTION_HISTORY_HEADER + "\n");

        for (TransactionDetail transactionDetail: transactionDetails){
            accountStatement.append(transactionDetail.getTransactionType().toString()
                                    + DELIMITER
                                    + DateUtils.convertDateToString(transactionDetail.getTransactionAt())
                                    + DELIMITER
                                    + transactionDetail.getAmount()
                                    + DELIMITER
                                    + transactionDetail.getBalance())
                            .append("\n");
        }

        return accountStatement.toString();
    }

    private void saveTransaction(double amount, TransactionType transactionType) {
        TransactionDetail transactionDetail = TransactionDetail.builder().
                transactionAt(new Date())
                .transactionType(transactionType)
                .amount(amount)
                .balance(balance)
                .build();

        transactionDetails.add(transactionDetail);
    }


}
