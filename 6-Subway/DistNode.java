public class DistNode implements Comparable<DistNode>{
    String key;
    Integer dist;
    DistNode prev;
    
    DistNode(String k, int d){
        key = k;
        dist = d;
    }

    DistNode getPrev(){
        return prev;
    }

    @Override
    public int compareTo(DistNode d){
        return this.dist.compareTo(d.dist);
    }
}