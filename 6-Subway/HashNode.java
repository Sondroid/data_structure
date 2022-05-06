import java.util.*;

public class HashNode {
    String name;
    String line;
    LinkedList<AdjacencyPair> list;
    
    HashNode(String n, String l){
        name = n;
        line = l;
        list = new LinkedList<AdjacencyPair>();
    }
}
