package src.main.model.interfaces;

public interface IIdentifiable<T extends Comparable<T>>{
    public T getIdentifier();
}
