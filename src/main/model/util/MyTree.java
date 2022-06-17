package src.main.model.util;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

import src.main.model.util.interfaces.INodable;

public class MyTree<T extends Comparable<T>,G extends INodable<T,G>>{

    private TreeMap<T,HashSet<G>> myTree;
    
    public MyTree() {
        this.myTree = new TreeMap<>();
    }

    public void add(G data){
        HashSet<G> node=myTree.get(data.getIdentifier());
        if(node==null){
            HashSet<G> newNode=new HashSet<>();
            newNode.add(data.toLeaf());
            myTree.put(data.getIdentifier(),newNode);
        }else{
            node.add(data.toLeaf());
        }
    }

    public boolean remove(G data){
        HashSet<G> node = myTree.get(data.getIdentifier());
        if (node == null) {
            return false;
        } else {
            return node.remove(data);
        }
    }

    public boolean contains(G data){
        HashSet<G> node=myTree.get(data);
        if(node==null){
            return false;
        }else{
            return node.contains(data.toLeaf());
        }
    }
    public HashSet<G> get(T identifier){
        return myTree.get(identifier);
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        myTree.forEach((x,y)->{
            sb.append("--------------------------------");
            sb.append("id:");
            sb.append(x);
            sb.append("--------------------------------");
            sb.append("\n");
            for(G g:y){
                sb.append("  ");
                sb.append(g.toString());
                sb.append("\n");
            }
        });
        return sb.toString();
    }

    public Map<T, HashSet<G>> getMyTree() {
        return myTree;
    }

    
}