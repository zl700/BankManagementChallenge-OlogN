package src.main.model.util;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import src.main.model.account.Account;
import src.main.model.transaction.Transaction;
import src.main.model.Bank;
public class InOut{
    private static final String accountsFile="src/main/data/accounts.txt";
    private static final String transactionsFile="src/main/data/transactions.txt";
    private InOut() {}

    public static Account createObject(String[] values) {
        Account account = null;
        try {
            account = (Account) Class.forName("src.main.model.account." + values[0])
                    .getConstructor(String.class, String.class, double.class)
                    .newInstance(values[1], values[2], Double.valueOf(values[3]));
        } catch (Exception e) {
            System.out.println(e);
        }
        return account;
    }
    public static Bank createBank(String accountsFile, String transactionsFile){
        Bank bank=new Bank();

        try( FileInputStream fis = new FileInputStream(accountsFile);
            Scanner scanner = new Scanner(fis, StandardCharsets.UTF_8);){
            while (scanner.hasNextLine()) {
                String[] parameters = scanner.nextLine().split(",");
                Account account = createObject(parameters);
                bank.addAccount(account);
            }
        }catch(Exception e){
            System.out.println(e);
        }

        List<Transaction> list = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream(transactionsFile);
            Scanner scanner = new Scanner(fis, StandardCharsets.UTF_8); ){
          
            while (scanner.hasNextLine()) {
                String[] parameters = scanner.nextLine().split(",");
                Transaction t = new Transaction(Long.valueOf(parameters[0]), parameters[1], parameters[2],
                        Double.valueOf(parameters[3]));
                list.add(t);
                
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        Collections.sort(list);
        for(Transaction t : list){
            //to visualize some specific account while debugging.
            String id=t.getIdentifier();
            // if (id.equals("a9e7617e-beef-4d93-a8b1-7a53b6b92c7a")) {
            //     System.out.println(bank.getAccount(id));
            //     System.out.println(t);
            //     bank.executeTransaction(t);
            //     System.out.println("after:" + bank.getAccount(id));
            //     System.out.println("----------------------------------------");
            // }
            try {
                bank.executeTransaction(t);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return bank;
    }
}