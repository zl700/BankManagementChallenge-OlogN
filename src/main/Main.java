package src.main;

import src.main.model.Bank;
import src.main.model.util.InOut;

public class Main {
    private static final String accountsFile = "src/main/data/accounts.txt";
    private static final String transactionsFile = "src/main/data/transactions.txt";
    public static void main(String[] args) {

        Bank bank=InOut.createBank(accountsFile, transactionsFile);
        System.out.println(bank);   

    }
}
