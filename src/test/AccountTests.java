package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import src.main.model.account.Chequing;
import src.main.model.account.Loan;
import src.main.model.account.Savings;

public class AccountTests {
    private Chequing chequing;
    private Savings savings;
    private Loan loan;
    public static void main(String[] args) {
        
    }

    @Before
    public void setUp(){
        chequing = new Chequing("f84c43f4-a634-4c57-a644-7602f8840870", "Michael Scott", 1524.51);
        savings = new Savings("ce07d7b3-9038-43db-83ae-77fd9c0450c9", "Saul Goodman", 2241.60);
        loan = new Loan("4991bf71-ae8f-4df9-81c1-9c79cff280a5", "Phoebe Buffay", 2537.31);
    }
    @Test
    public void withdrawal(){
        chequing.withdraw(1440);
        assertEquals(84.51,chequing.round(chequing.getBalance()));
    }
    @Test
    public void overdraft(){
        chequing.withdraw(1534.43);
        assertEquals(-15.42,chequing.round(chequing.getBalance()));
    }
    @Test(expected=IllegalArgumentException.class)
    public void overdraftLimit(){
        chequing.withdraw(1726);
        assertEquals(1524.51,chequing.round(chequing.getBalance()));
    }
    @Test
    public void withdrawFee(){
        savings.withdraw(100);
        assertEquals(2136.60,savings.round(savings.getBalance()));
    }
    @Test
    public void withdrawFeeInterest(){
        loan.withdraw(2434.31);
        assertEquals(5020.31,loan.round(loan.getBalance()));
    }
    @Test(expected=IllegalArgumentException.class)
    public void withdrawalLimit(){
        loan.withdraw( 7463.69);
    }

    @Test
    public void deposit(){
        chequing.deposit(5000);
        assertEquals(6524.51,chequing.round(chequing.getBalance()));
    }
    @Test
    public void loanDeposit(){
        loan.deposit(1000);
        assertEquals(1537.31,loan.round(loan.getBalance()));
    }
    @Test
    public void incomeTax(){
        chequing.deposit(4000);
        chequing.tax();
        assertEquals(5374.51,chequing.round(chequing.getBalance()));
    }
}
