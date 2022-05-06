import java.util.*;

public class Path implements Comparable<Path>{
    String[] path;
    Integer time;
    int numItems = 0;
    
    Path(int size){
        path = new String[size];
    };

    void add(DistNode d, Hashtable<String, HashNode> ht){
        path[numItems++] = ht.get(d.key).name;
    }


    @Override
    public int compareTo(Path p){
        return this.time.compareTo(p.time);
    }

}
