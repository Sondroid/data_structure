public class intPair implements Comparable<intPair>{
    public Integer int1;
    public Integer int2;

    intPair(int i, int j){
        int1 = i;
        int2 = j;
    }

    @Override
    public int compareTo(intPair o) {
        if(int1.compareTo(o.int1) != 0){
            return int1.compareTo(o.int1);
        }
        else{
            return int2.compareTo(o.int2);
        }
    }
}