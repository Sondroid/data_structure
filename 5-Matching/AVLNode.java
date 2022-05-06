import java.util.LinkedList;


public class AVLNode<T extends Comparable<T>, V extends Comparable<V>> {
    public T item;
    public LinkedList<V> list;
    public int height;
    public AVLNode<T, V> left;
    public AVLNode<T, V> right;

    // Constructed when new substring entered to tree
    AVLNode(T obj, V value){
        item = obj; 
        left = right = (AVLNode<T, V>) AVLTree.NIL;
        height = 1;
        list = new LinkedList<V>();
        this.list.add(value);
    }
    // Only to declare sentinel NIL
    AVLNode(T obj, AVLNode<T, V> leftChild, AVLNode<T, V> rightChild, int h){
        item = obj; 
        list = new LinkedList<V>();
        left = leftChild;
        right = rightChild;
        height = h;
    }

    public boolean contain(V v){
        for(int i=0; i < this.list.size(); i++){
            if(this.list.get(i).compareTo(v) == 0){
                return true;
            }
            if(this.list.get(i).compareTo(v) > 0){
                break;
            }
        }
        return false;
    }

}