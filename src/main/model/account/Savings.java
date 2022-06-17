package src.main.model.account;

public class Savings extends Account{
    private static final double withdrawFee=5;
    public Savings(String id, String name, double balance) {
        super(AccountType.Savings,id, name, balance);
    }

    public Savings(Savings source){
        super(source);
    }

    @Override
    public void deposit(double amount) {
        super.setBalance(super.getBalance()+amount);
    }

    @Override
    public void withdraw(double amount) {
        super.setBalance(super.getBalance()-amount-withdrawFee);
    }

    @Override
    public Account copy() {
        return new Savings(this);
    }

}
