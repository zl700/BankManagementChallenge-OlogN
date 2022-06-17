package src.main.model.interfaces;

public interface INodable<T extends Comparable<T>,G> extends IIdentifiable<T>{
    public G toLeaf();
}
