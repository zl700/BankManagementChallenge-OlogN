package src.main.model.account;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

import src.main.model.util.interfaces.IIdentifiable;

public abstract class Account implements IIdentifiable<String>{
    private AccountType type;
    private String id;
    private String name;
    private double balance;
    private double initial;
    enum AccountType{
        Chequing,
        Loan,
        Savings;
    }
    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    // I think Object has a default clone method, so I changed the name
    public abstract Account copy();
    
    Account(AccountType type,String id, String name, double balance) {
        this.type=type;
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.initial=balance;
    }

    Account(String type, String id, String name, double balance) {
        this.type = AccountType.valueOf(type);
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.initial=balance;
    }
    Account(Account source){
        this.type=source.type;
        this.id = source.id;
        this.name = source.name;
        this.balance = source.balance;
        this.initial=source.initial;
    }

   

    public double round(double amount) {
        DecimalFormat formatter = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
        return Double.parseDouble(formatter.format(amount));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.type.ordinal());
        sb.append("[id:");
        sb.append(this.id);
        sb.append(", name:");
        sb.append(this.name);
        sb.append(", balance:");
        sb.append(this.balance);
        sb.append(",initial");
        sb.append(this.initial);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.balance,this.id,this.type.ordinal(),this.initial);
    }

    
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (type != other.type)
            return false;
        return true;
    }
    @Override
    public String getIdentifier() {
        return this.id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
    public AccountType getType() {
        return type;
    }
    public void setType(AccountType type) {
        this.type = type;
    }
    
    public double getInitial() {
        return initial;
    }
    public void setInitial(double initial) {
        this.initial = initial;
    }
    public static void main(String[] args) {
        Chequing chequing = new Chequing("f84c43f4-a634-4c57-a644-7602f8840870", "Michael Scott", 1524.51);
        Account chequingCopy = chequing.copy();

        Savings savings = new Savings("ce07d7b3-9038-43db-83ae-77fd9c0450c9", "Saul Goodman", 2241.60);
        Account savingsCopy = savings.copy();
    }


}
