package deque;

import java.util.Comparator;
import java.util.Iterator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> c1;

    public MaxArrayDeque(Comparator<T> c){
        super();
        c1 = c;
    }

    public T max(){
        if (isEmpty()){
            return null;
        }

        T maxItem = getFirst();
        for (T item : this){
            int cmp = c1.compare(item,maxItem);
            if (cmp>0){
                maxItem = item;
            }
        }
        return maxItem;
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T maxItem = getFirst();
        for (T item : this) {
            int cmp = c.compare(item, maxItem);
            if (cmp > 0) {
                maxItem = item;
            }
        }
        return maxItem;
    }
}