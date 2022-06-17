package src.main.model.transaction;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Objects;

import src.main.model.interfaces.INodable;

//Since Transaction is finally stored in a hashSet.
//It must have a good implementation of hashCode() and equals()
//Objects.hash(enum) is not safe, use Objects.hash(enum.ordinal()) instead
//I want to force it override them, but it seems documentation is also good. 
public class Transaction implements Comparable<Transaction>,INodable<String,Transaction>{
    private Type type;
    private ZonedDateTime timeStamp;
    private String id;
    private double amount;
    public Transaction(long timeStamp,String type,String id,double amount) {
        this.timeStamp = Instant.ofEpochMilli(timeStamp).atZone(ZoneId.systemDefault());
        this.type =Type.valueOf(type);
        this.id = id;
        this.amount = amount;
    }  
    
    public Transaction(Type type,String timeString, String id, double amount) {
        this.timeStamp = this.stringToTime(timeString);
        this.type = type;
        this.id = id;
        this.amount = amount;

    }
    public Transaction(Type type,long timeStamp, String id, double amount) {
        this.timeStamp = Instant.ofEpochMilli(timeStamp).atZone(ZoneId.systemDefault());
        this.type = type;
        this.id = id;
        this.amount = amount;

    }

    public Transaction(String type,String id,double amount) {
        this.timeStamp =ZonedDateTime.now();
        this.type = Type.valueOf(type);
        this.id = id;
        this.amount = amount;
    }
    
    public Transaction(long timeStamp, Type type, String id, double amount) {
        this.timeStamp = Instant.ofEpochMilli(timeStamp).atZone(ZoneId.systemDefault());
        this.type = type;
        this.id = id;
        this.amount = amount;
    }

    public Transaction(Type type, String id, double amount) {
        this.timeStamp = ZonedDateTime.now();
        this.type = type;
        this.id = id;
        this.amount = amount;
    }

    public Transaction(Transaction source){
        this.type = source.type;
        this.timeStamp = source.timeStamp;
        this.id = source.id;
        this.amount = source.amount;
    }
    public ZonedDateTime stringToTime(String input){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss");
        return LocalDateTime.parse(input, formatter).atZone(ZoneId.systemDefault());
    }
    public String returnDate() {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd@HH:mm:ss");
        return this.timeStamp.format(formater);
    }

    public enum Type {
        WITHDRAW,DEPOSIT
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type.ordinal(),this.id,this.amount,this.timeStamp.toEpochSecond());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Transaction other = (Transaction) obj;
        if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (timeStamp == null) {
            if (other.timeStamp != null)
                return false;
        } else if (!timeStamp.equals(other.timeStamp))
            return false;
        if (type != other.type)
            return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("[type:");
        sb.append(this.type);
        sb.append(", date:");
        sb.append(this.returnDate());
        sb.append(", id:");
        sb.append(this.id);
        sb.append(",amount:");
        sb.append(this.amount);
        sb.append("]");
        return sb.toString();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
    
    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(ZonedDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;

        
    }


    public static void main(String[] args) {
        Transaction t1=new Transaction(Transaction.Type.DEPOSIT,"1970-01-19@14:25:55", "f84c43f4-a634-4c57-a644-7602f8840870",635.95);
        Transaction t2=new Transaction(Transaction.Type.DEPOSIT,"1970-01-19@14:25:55", "f84c43f4-a634-4c57-a644-7602f8840870",635.95);
        HashSet<Transaction> set=new HashSet<>();
        set.add(t1);
        set.add(t2);
        System.out.println(set.contains(t1));
        System.out.println(set.contains(t2));
    }


    @Override
    public String getIdentifier() {
        return this.id;
    }

    @Override
    public Transaction toLeaf() {
        return new Transaction(this);
    }

    @Override
    public int compareTo(Transaction o) {
        return this.timeStamp.compareTo(o.timeStamp);
    }


    
}
