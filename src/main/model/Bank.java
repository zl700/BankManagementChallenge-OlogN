package src.main.model;

import java.util.HashMap;
import java.util.HashSet;

import src.main.model.account.Account;
import src.main.model.account.Chequing;
import src.main.model.account.Savings;
import src.main.model.interfaces.Taxable;
import src.main.model.transaction.Transaction;
import src.main.model.util.MyTree;
public class Bank {
    HashMap<String,Account> accounts;
    MyTree<String,Transaction> transactions;
 
    public Bank(){
        this.accounts=new HashMap<>();
        this.transactions=new MyTree<>();
    }

    public void addAccount(Account account) {
        this.accounts.put(account.getIdentifier(), account.copy());
    }
    public Account getAccount(String id){
        return accounts.get(id).copy();
    }
    private void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
    
    // O(logN) performance because of MyTree
    private void withdrawTransaction(Transaction transaction) {
        this.addTransaction(transaction);
        Account account=this.accounts.get(transaction.getIdentifier());
        account.withdraw(transaction.getAmount());
    }
    
    // O(logN) performance because of MyTree
    public void depositTransaction(Transaction transaction){
        this.addTransaction(transaction);
        Account account = this.accounts.get(transaction.getIdentifier());
        account.deposit(transaction.getAmount());
    }
    
    public Transaction[] getTransactions(String id){
        return this.transactions.get(id).toArray(new Transaction[0]);
    }

    //O(logN) performance because of MyTree
    public void updateTransaction(Transaction oldTransaction,Transaction newTransaction){
        HashSet<Transaction> node=this.transactions.get(oldTransaction.getIdentifier());
        if(node==null){
            throw new IllegalArgumentException("we don't have this transaction:"+oldTransaction.getIdentifier());
        }
        if(!node.contains(oldTransaction)){
            throw new IllegalArgumentException("we don't have this transaction:" + oldTransaction.getIdentifier());
        }
        Account account=this.accounts.get(oldTransaction.getIdentifier());
        // do the opposite operation on account since the transaction will be deleted
        switch (oldTransaction.getType()) {
            case WITHDRAW:
                account.deposit(oldTransaction.getAmount());
                break;
            case DEPOSIT:
                account.withdraw(oldTransaction.getAmount());
                break;
        }
        node.remove(oldTransaction);
        this.executeTransaction(newTransaction);
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        this.transactions.getMyTree().forEach((x, y) -> {
            sb.append("----------------------------------------------------------------------------------------------------\n");
            Account account = this.accounts.get(x);
            sb.append("["+account.getType()+"]\n");
            sb.append(account.getName()+":");
            sb.append("initial:"+account.getInitial());
            sb.append("\n");
            sb.append("balance:");
            sb.append(account.getBalance()+"\n");
            if (account instanceof Chequing){
                Chequing q=(Chequing)account;
                double tax=q.getTax();
                sb.append("tax:");
                sb.append(tax);
                sb.append("\n");
                sb.append("after tax:");
                sb.append(q.getBalance()-tax);
                sb.append("\n");
            }
            sb.append("----------------------------------------------------------------------------------------------------\n");
            for (Transaction t: y) {
                sb.append("  ");
                sb.append(t.toString());
                sb.append("\n");
            }
            
        });
        return sb.toString();
    }
    /**
     * Name: executeTransaction
     * 
     * @param transaction
     * 
     *                    Inside the function:
     *                    1. calls withdrawTransaction if transaction type is
     *                    WITHDRAW
     *                    2. calls depositTransaction if transaction type is DEPOSIT
     * 
     */
    public void executeTransaction(Transaction transaction) {
        switch (transaction.getType()) {
            case WITHDRAW:
                this.withdrawTransaction(transaction);
                break;
            case DEPOSIT:
                this.depositTransaction(transaction);
        }
    }

    public void deductTaxes() {
       for(Account account:accounts.values()){
        if(account instanceof Taxable){
            Taxable t = (Taxable)account;
            t.tax();
        }
       }
    }


    public static void main(String[] args) {
        Bank bank=new Bank();
        Account[] accounts = new Account[] {
            new Chequing("f84c43f4-a634-4c57-a644-7602f8840870", "Michael Scott", 1524.51),
            new Savings("ce07d7b3-9038-43db-83ae-77fd9c0450c9", "Saul Goodman", 2241.60)
        };
        
        for (Account account : accounts) {
            bank.addAccount(account);
        }
        
        Transaction[] transactions = new Transaction[] {
                new Transaction(Transaction.Type.WITHDRAW, 1546905600, "f84c43f4-a634-4c57-a644-7602f8840870", 624.99),
                new Transaction(Transaction.Type.DEPOSIT, 1578700800, "f84c43f4-a634-4c57-a644-7602f8840870", 441.93),
                new Transaction(Transaction.Type.WITHDRAW, 1547078400, "f84c43f4-a634-4c57-a644-7602f8840870", 546.72),
                new Transaction(Transaction.Type.WITHDRAW, 1546732800, "f84c43f4-a634-4c57-a644-7602f8840870", 546.72),
                new Transaction(Transaction.Type.DEPOSIT, 1578355200, "f84c43f4-a634-4c57-a644-7602f8840870", 635.95),
                new Transaction(Transaction.Type.WITHDRAW, 1547078400, "ce07d7b3-9038-43db-83ae-77fd9c0450c9", 875.64),
                new Transaction(Transaction.Type.WITHDRAW, 1578614400, "ce07d7b3-9038-43db-83ae-77fd9c0450c9", 912.45),
                new Transaction(Transaction.Type.WITHDRAW, 1577836800, "ce07d7b3-9038-43db-83ae-77fd9c0450c9", 695.09),
                new Transaction(Transaction.Type.WITHDRAW, 1609459200, "ce07d7b3-9038-43db-83ae-77fd9c0450c9", 917.21),
                new Transaction(Transaction.Type.WITHDRAW, 1578096000, "ce07d7b3-9038-43db-83ae-77fd9c0450c9", 127.94),
                new Transaction(Transaction.Type.WITHDRAW, 1546819200, "ce07d7b3-9038-43db-83ae-77fd9c0450c9", 612.52)
            };
            
            for (Transaction transaction : transactions) {
                bank.executeTransaction(transaction);
            }
            System.out.println(bank);
            Transaction oldTransaction=new Transaction(Transaction.Type.DEPOSIT, 1578355200, "f84c43f4-a634-4c57-a644-7602f8840870", 635.95);
            Transaction newTransaction=new Transaction(Transaction.Type.DEPOSIT, 1578355200, "f84c43f4-a634-4c57-a644-7602f8840870", 800);
            bank.updateTransaction(oldTransaction, newTransaction);
            System.out.println(bank);
        
    }
}
