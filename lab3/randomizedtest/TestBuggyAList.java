package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    // YOUR TESTS HERE
    @Test
    public void testBuggy() {
        AListNoResizing<Integer> lst1 = new AListNoResizing<>();
        BuggyAList<Integer> lst2 = new BuggyAList<>();
        lst1.addLast(4); lst2.addLast(4);
        lst1.addLast(5); lst2.addLast(5);
        lst1.addLast(6); lst2.addLast(6);
        assertEquals(lst1.size(), lst2.size());
        assertEquals(lst1.removeLast(), lst2.removeLast());
        assertEquals(lst1.removeLast(), lst2.removeLast());
        assertEquals(lst1.removeLast(), lst2.removeLast());
}

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> L1 = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                L1.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size1 = L1.size();
                assertEquals(size, size1);
            } else if (operationNumber == 2){
                if (L.size() > 0){
                    L.getLast();
                    L1.getLast();
                    assertEquals(L.getLast(), L1.getLast());
                }
            } else if (operationNumber == 3){
                if (L.size()> 0){
                    L.removeLast();
                    L1.removeLast();
                }
            }
        }
    }
}
