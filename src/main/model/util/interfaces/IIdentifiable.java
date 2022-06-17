package src.main.model.util.interfaces;

public interface IIdentifiable<T extends Comparable<T>>{
    public T getIdentifier();
}
