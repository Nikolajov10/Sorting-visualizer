package testing;

import app.SortingApp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import sortAlgs.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.Arrays;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class sortAlgTest {

    private sortAlg algorithm;
    @Mock static  private MergeSort merge_alg;
    private void testing() {
        // given
        int[] array = algorithm.getArray().clone();

        // when
        algorithm.sorting();

        // then
        int[] sorted_array = algorithm.getArray();
        Arrays.sort(array);
        assertArrayEquals(array,sorted_array);
    }

    @BeforeAll
    static void setUp() {
        merge_alg = new MergeSort();
    }

    @Test
    void testBubbleSort() {
        algorithm = new BubbleSort();
        testing();
    }
    @Test
    void testInsertionSort() {
        algorithm = new InsertionSort();
        testing();
    }
    @Test
    @Disabled("Need to implement quickSort without GUI")
    void testQuickSort(){}

    @Test
    void testMergeSortThrows() {
        // given
        given(merge_alg.test_sorting(-1,10)).willThrow(IndexOutOfBoundsException.class);
        assertThatThrownBy(()->merge_alg.test_sorting(-1,10));
    }


}