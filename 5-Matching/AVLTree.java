import java.util.ArrayList;

public class AVLTree<T extends Comparable<T>, V extends Comparable<V>>{
    // Declare sentinel as raw type because generic cannot be static
    static final AVLNode NIL = new AVLNode<>(null, null, null, 0);

    public AVLNode<T, V> root;

    public AVLTree(){
        root = (AVLNode<T, V>) NIL;
    }

    // search
    public AVLNode<T, V> search(T obj){
        return searchItem(root, obj);
    }
    public AVLNode<T, V> searchItem(AVLNode<T, V> tNode, T obj){
        if(tNode == NIL){
            return null;
        }
        else if(obj.compareTo(tNode.item) == 0){
            return tNode;  
        } 
        else if(obj.compareTo(tNode.item) < 0){
            return searchItem(tNode.left, obj);
        }
        else{
             return searchItem(tNode.right, obj);
        }
    }

    // insert
    public void insert(T obj, V v){
        root = insertItem(root, obj, v);
    }
    public AVLNode<T, V> insertItem(AVLNode<T, V> tNode, T obj, V v){
        int type;
        if(tNode == NIL){
            tNode = new AVLNode<T, V>(obj, v);
        }
        else if(obj.compareTo(tNode.item) < 0){
            tNode.left = insertItem(tNode.left, obj, v);
            tNode.height = 1 + Math.max(tNode.right.height, tNode.left.height);
            type = needBalance(tNode);
            if(type != NO_NEED){
                tNode = balanceAVL(tNode, type);
            }
        }
        else if(obj.compareTo(tNode.item) > 0){
            tNode.right = insertItem(tNode.right, obj, v);
            tNode.height = 1 + Math.max(tNode.right.height, tNode.left.height);
            type = needBalance(tNode);
            if(type != NO_NEED){
                tNode = balanceAVL(tNode, type);
            }
        }
        else{
            int idx = 0;
            for(int i=0; i < tNode.list.size(); i++){
                idx = i;
                if(tNode.list.get(i).compareTo(v) >= 0){
                    break;
                }
            }
            if(idx == tNode.list.size()-1 &&tNode.list.get(idx).compareTo(v) < 0){
                tNode.list.add(v);
            }else{
                tNode.list.add(idx, v);
            }
        }
        return tNode;
    }

    // balancing
    private final int LL = 1, LR = 2, RR = 3, RL = 4, NO_NEED = 0, ILLEGAL = -1;

    private int needBalance(AVLNode<T, V> t) {
        int type = ILLEGAL;
        if(t.left.height + 2 <= t.right.height){ // type R
            if(t.right.left.height <= t.right.right.height){
                type = RR;
            }
            else{
                type = RL;
            }
        }
        else if(t.left.height >= t.right.height + 2){ // type L
            if(t.left.left.height < t.left.right.height){
                type = LR;
            }
            else{
                type = LL;
            }
        }
        else{
            type = NO_NEED;
        }
        return type;
    }

    private AVLNode<T, V> balanceAVL(AVLNode<T, V> tNode, int type){
        AVLNode<T, V> returnNode;
        switch(type){
            case LL:
                returnNode = rightRotate(tNode);
                break;
            case LR:
                tNode.left = leftRotate(tNode.left);
                returnNode = rightRotate(tNode);
                break;
            case RR:
                returnNode = leftRotate(tNode);
                break;
            case RL:
                tNode.right = rightRotate(tNode.right);
                returnNode = leftRotate(tNode);
                break;
            default:
                throw new RuntimeException("Balancing AVLTree Exception");
        }
        return returnNode;
    }
    
    private AVLNode<T, V> leftRotate(AVLNode<T, V> t){
        AVLNode<T, V> RChild = t.right;
        if(RChild == NIL){
            throw new RuntimeException("Rotating AVLTree Exception");
        }
        AVLNode<T, V> RLChild = RChild.left;
        RChild.left = t;
        t.right = RLChild;
        t.height = 1 + Math.max(t.left.height, t.right.height);
        RChild.height = 1 + Math.max(RChild.left.height, RChild.right.height);
        return RChild;
    }
    private AVLNode<T, V> rightRotate(AVLNode<T, V> t){
        AVLNode<T, V> LChild = t.left;
        if(LChild == NIL){
            throw new RuntimeException("Rotating AVLTree Exception");
        }
        AVLNode<T, V> LRChild = LChild.right;
        LChild.right = t;
        t.left = LRChild;
        t.height = 1 + Math.max(t.left.height, t.right.height);
        LChild.height = 1 + Math.max(LChild.left.height, LChild.right.height);
        return LChild;
    }

    public ArrayList<T> preorder(){
        return preTraversal(root);
    }

    public ArrayList<T> preTraversal(AVLNode<T, V> tNode){
        ArrayList<T> result = new ArrayList<>();
        if(tNode != NIL){
            result.add(tNode.item);
            result.addAll(preTraversal(tNode.left));
            result.addAll(preTraversal(tNode.right));
        }
        return result;
    }
}