package src.main.model.account;

public class Loan extends Account{
    private static final double interestRate=0.02;
    public Loan(String id, String name, double balance) {
        super(AccountType.Loan,id, name, balance);
    }

    public Loan(Loan source){
        super(source);
    }

    @Override
    public void deposit(double amount) {
        super.setBalance(super.getBalance()-amount);
    }

    @Override
    public void withdraw(double amount) {
        double difference = super.getBalance()-amount;
        if(difference<0){
            throw new IllegalArgumentException(super.getId()+":you only have "+super.getBalance()+"and you can't borrow more than 10000");
        }else{
            super.setBalance(super.getBalance()+amount*(1+interestRate));
        }
        
    }

    @Override
    public Account copy() {
        return new Loan(this);
    }


}
