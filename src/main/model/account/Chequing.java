package src.main.model.account;

import src.main.model.interfaces.Taxable;

public class Chequing extends Account implements Taxable{
    private static final double overDraftFee=5.5;
    private static final double taxRate=0.15;
    private static final double threashhold=3000;
    private double income;
    public Chequing(String id, String name, double balance) {
        super(AccountType.Chequing,id, name, balance);
        this.income=0;
    } 
    
    public Chequing(Chequing source){
        super(source);
        this.income=source.income;
    }

    
    @Override
    public void deposit(double amount) {
        this.income+=amount;
        super.setBalance(super.getBalance()+amount);
    }

    @Override
    public void withdraw(double amount) {
        double difference=super.getBalance()-amount;
        this.income-=amount;
        if(difference>0){
            super.setBalance(super.getBalance()-amount);
        }else if(difference<0&&difference>=-200){
            super.setBalance(super.getBalance()-amount-overDraftFee);
        }else{
            throw new IllegalArgumentException(super.getId()+":you can't withdraw more than "+(amount+super.getBalance()));
        }
    }

    @Override
    public void tax() {
        if(this.income>threashhold){
            double balance=super.getBalance();
            super.setBalance(balance-(this.income-threashhold)*taxRate);
            this.income=0;
        }
    }

    public double getTax(){
        return this.income>threashhold?(this.income-threashhold)*taxRate:0;
    }
    @Override
    public Account copy() {
        return new Chequing(this);
    }

    public static double getThreashhold() {
        return threashhold;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.getType().ordinal());
        sb.append("[id:");
        sb.append(super.getId());
        sb.append(", name:");
        sb.append(super.getName());
        sb.append(", balance:");
        sb.append(super.getBalance());
        sb.append(",income:");
        sb.append(this.income);
        sb.append("]");

        return sb.toString();
    }
}
