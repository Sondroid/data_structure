import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Hashtable<K extends Comparable<K>, V extends Comparable<V>>{

    public ArrayList<AVLTree<K, V>> table;

    public Hashtable() {
        table = new ArrayList<>(100);
        for(int i=0; i<100; i++){
            table.add(new AVLTree<K, V>());
        }
    }

    public void insert(K k, V v){
        int slot = hash(k);
        table.get(slot).insert(k, v);
    }

    public AVLNode<K, V> search(K k){
        int slot = hash(k);
        return table.get(slot).search(k);
    }

    public static int hash(Object obj){
        String str = obj.toString();
        byte[] bytes = str.getBytes(StandardCharsets.US_ASCII);
        int sum = 0;
        for(int i=0; i < str.length(); i++){
            sum += bytes[i];
        }
        return sum % 100;
    }
}
