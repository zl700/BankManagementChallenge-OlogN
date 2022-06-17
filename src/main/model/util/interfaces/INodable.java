package src.main.model.util.interfaces;

public interface INodable<T extends Comparable<T>,G> extends IIdentifiable<T>{
    public G toLeaf();
}
