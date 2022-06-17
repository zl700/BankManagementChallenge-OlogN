package src.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import src.main.model.transaction.Transaction;


public class TransactionTests {

    Transaction transaction;

    @Before
    public void setup() {
        transaction = new Transaction(Transaction.Type.WITHDRAW , 1546905600, "6b8dd258-aba3-4b19-b238-45d15edd4b48", 624.99);
    }

    @Test
    public void correctDateTest() {
        assertEquals("1970-01-19@05:41:45", transaction.returnDate());
    }

}
