package src.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import src.main.model.Bank;
import src.main.model.account.Chequing;
import src.main.model.transaction.Transaction;

public class BankTests {
    private Bank bank;
    @Before
    public void setup() {
        bank = new Bank();
        bank.addAccount(new Chequing("f84c43f4-a634-4c57-a644-7602f8840870", "Michael Scott", 1524.51));
    }
    
    @Test
    public void successfulTransaction() {
        this.bank.executeTransaction(
                new Transaction(Transaction.Type.WITHDRAW, 1546905600, "f84c43f4-a634-4c57-a644-7602f8840870", 624.99));
        this.bank.depositTransaction(
                new Transaction(Transaction.Type.DEPOSIT, 1578700800, "f84c43f4-a634-4c57-a644-7602f8840870", 441.93));
        assertEquals(2, bank.getTransactions("f84c43f4-a634-4c57-a644-7602f8840870").length);
    }
    @Test(expected = IllegalArgumentException.class)
    public void failedTransaction() {
        this.bank.executeTransaction(new Transaction(Transaction.Type.WITHDRAW, 1546905600,
                "f84c43f4-a634-4c57-a644-7602f8840870", 10000000));
        assertEquals(0, bank.getTransactions("f84c43f4-a634-4c57-a644-7602f8840870").length);
    }

    @Test
    public void taxDeduction1() {
        this.bank.executeTransaction(
                new Transaction(Transaction.Type.DEPOSIT, 1578700800, "f84c43f4-a634-4c57-a644-7602f8840870", 4000));
        this.bank.deductTaxes();
        assertEquals(5374.51, bank.getAccount("f84c43f4-a634-4c57-a644-7602f8840870").getBalance(),0.000001);
    }
    @Test
    public void taxDeduction() {
        this.bank.executeTransaction(new Transaction(Transaction.Type.DEPOSIT, 1578700800, "f84c43f4-a634-4c57-a644-7602f8840870", 4000));
        this.bank.executeTransaction(new Transaction(Transaction.Type.WITHDRAW, 1578700800, "f84c43f4-a634-4c57-a644-7602f8840870", 500));

        this.bank.deductTaxes();
        assertEquals(4949.51, bank.getAccount("f84c43f4-a634-4c57-a644-7602f8840870").getBalance(),0.000001);    
    }

}
