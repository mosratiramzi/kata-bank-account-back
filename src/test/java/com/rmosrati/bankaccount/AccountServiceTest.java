package com.rmosrati.bankaccount;

import com.rmosrati.bankaccount.service.AccountService;
import com.rmosrati.bankaccount.model.TransactionDetail;
import com.rmosrati.bankaccount.model.TransactionType;
import com.rmosrati.bankaccount.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.rmosrati.bankaccount.service.AccountService.DELIMITER;
import static com.rmosrati.bankaccount.service.AccountService.TRANSACTION_HISTORY_HEADER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class AccountServiceTest {

    private AccountService accountService;

    @Before
    public void init(){
        accountService = new AccountService();
    }

    @Test
    public void should_deposit_with_a_positive_amount() {
        // Arrange
        double amount= 10;

        // Act
        double balance = accountService.deposit(amount);

        // Assert
        assertThat(balance).isEqualTo(10.00);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_exception_when_deposit_with_a_negative_amount() {
        // Arrange
        double amount = -100;

        // Act
        accountService.withdraw(amount);

        // Assert
        // expected an exception
    }

    @Test
    public void should_withdraw_with_positive_amount() {

        // Arrange
        double amount = 20;

        // Act
        double balance = accountService.withdraw(amount);

        // Assert
        assertThat(-20.00).isEqualTo(balance);
    }

    @Test
    public void should_throw_exception_when_withdraw_with_a_negative_amount() {

        // Act
        IllegalArgumentException argumentException = assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdraw(-10);
        });

        // Assert
        assertThat(argumentException.getMessage()).isEqualTo("The amount should be positive");

    }

    @Test
    public void should_save_transactions(){

        accountService.deposit(50);
        accountService.deposit(20);
        accountService.withdraw(30);

        // Act
        List<TransactionDetail> transactionDetails = accountService.getTransactionDetails();

        assertThat(transactionDetails.size()).isEqualTo(3);
        assertThat(transactionDetails.get(0).getTransactionType()).isEqualTo(TransactionType.DEPOSIT);
        assertThat(transactionDetails.get(2).getTransactionType()).isEqualTo(TransactionType.WITHDRAW);
        assertThat(transactionDetails.get(2).getBalance()).isEqualTo(40.0);

    }

    @Test
    public void should_display_customer_account_transaction_history() {

        accountService.deposit(50);
        accountService.withdraw(20);

        List<TransactionDetail> transactionDetails = accountService.getTransactionDetails();

        String expectedTransactionHistory = TRANSACTION_HISTORY_HEADER
                + "\n"
                + "DEPOSIT" + DELIMITER + DateUtils.convertDateToString(transactionDetails.get(0).getTransactionAt()) + DELIMITER + "50.0"+ DELIMITER + "50.0"
                + "\n"
                + "WITHDRAW" + DELIMITER + DateUtils.convertDateToString(transactionDetails.get(0).getTransactionAt()) + DELIMITER + "20.0"+ DELIMITER + "30.0"
                + "\n";

        //WHEN
        String transactionHistory = accountService.displayHistoryOfTransaction();

        //THEN
        assertEquals(expectedTransactionHistory, transactionHistory);

    }


}
