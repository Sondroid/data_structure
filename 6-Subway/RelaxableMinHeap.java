import java.util.*;

public class RelaxableMinHeap {
    private DistNode[] A;
    private int numItems;

    // build heap from adjacency hashtable
    RelaxableMinHeap(Hashtable<String, HashNode> ht){
        A = new DistNode[ht.size()];
        numItems = 0;

        ht.forEach((k, v) -> {
            try {
                insert(new DistNode(k, 2147483647));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public DistNode takeMinAndRelax(Hashtable<String, HashNode> ht) throws Exception{
        DistNode tmp = deleteMin();
        A[numItems] = tmp;

        ht.get(tmp.key).list.forEach(pair -> {
            if(get(pair.key) != null){
                if(get(pair.key).dist > tmp.dist + pair.time){
                    get(pair.key).dist = tmp.dist + pair.time;
                    get(pair.key).prev = tmp;
                }
            }
        });

        buildHeap();

        return tmp;
    }

    public DistNode get(String k){
        for(int i=0; i < numItems; i++){
            if(A[i].key.equals(k)){
                return A[i];
            }
        }
        return null;
    }
    
    public int getIndex(String k){
        for(int i=0; i<numItems; i++){
            if(A[i].key.equals(k)){
                return i;
            }
        }
        return -1;
    }

    public DistNode deleteMin() throws Exception{
        if(!isEmpty()){
            DistNode min = A[0];
            A[0] = A[numItems-1];
            numItems--;
            percolateDown(0);
            return min;
        }else throw new Exception("Heap deletion Error");
    }

    public void insert(DistNode newItem) throws Exception{
        if(numItems < A.length){
            A[numItems] = newItem;
            percolateUp(numItems);
            numItems++;
        } else throw new Exception("Overflow during Heap insertion");
    }

    public boolean isEmpty(){
        return numItems == 0;
    }
    
    public void buildHeap(){
        if(numItems >= 2){
            for(int i = (numItems-2)/2; i >= 0; i--){
                percolateDown(i);
            }
        }
    }

    public void percolateUp(int i){
        int parent = (i-1)/2;
        if(parent >= 0 && A[i].compareTo(A[parent]) < 0){
            DistNode tmp = A[i];
            A[i] = A[parent];
            A[parent] = tmp;
            percolateUp(parent);
        }
    }

    public void percolateDown(int i){
        int child = 2*i + 1;
        int rightChild = 2*i + 2;
        if(child <= numItems - 1){
            if(rightChild <= numItems-1 && A[child].compareTo(A[rightChild]) > 0){
                child = rightChild;
            }
            if(A[i].compareTo(A[child]) > 0){
                DistNode tmp = A[i];
                A[i] = A[child];
                A[child] = tmp;
                percolateDown(child);
            }
        }

    }
}
