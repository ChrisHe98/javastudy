package deque;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Comparator;

public class MaxArrayDequeTest {
    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void maxTest() {
        Comparator<String> cmp = new Comparator<>() {
            @Override
            public int compare(String strA, String strB) {
                return strA.compareTo(strB);
            }
        };
        MaxArrayDeque ad1 = new MaxArrayDeque(cmp);
        ad1.addFirst("apple");
        ad1.addFirst("banana");
        ad1.addLast("orange");
        assertEquals("orange",ad1.max());

    }

    @Test
    public void max2Test() {
        Comparator<Integer> cmp1 = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2- o1;
            }
        };
        Comparator<Integer> cmp2 = new Comparator<>() {
            @Override
            public int compare(Integer o1, Integer o2) {
               return o1-o2;
            }
        };

        MaxArrayDeque ad2 = new MaxArrayDeque(cmp1);
        ad2.addFirst(1);
        ad2.addLast(2);
        ad2.addFirst(100);
        // should be empty
        assertEquals(100, ad2.max(cmp2));
    }

}
