package sortAlgs;

public class MergeSort  extends sortAlg {

    public MergeSort() {super();}
    public MergeSort(int[] array) { super(array); }

    @Override
    public void sorting() {

    }
    public boolean test_sorting(int low,int high) {
        /**
         * For testing purpose
         */
        return true;
    }
    private void paintAndWait(int index,int mode) {
        paintOnePosition(index,sortAlg.compare_color);
        synchronized (thread) {
            try {
                thread.wait(sortAlg.wait_time);
            }
            catch (InterruptedException e) {}
        }
        if (mode == 1)paintOnePosition(index,sortAlg.rect_color);
        else delOnePositon(index);
    }


    private void merge(int l,int m,int r) {
        // sizes of subarrays to merge
        int size1 = m - l + 1;
        int size2 = r - m;
        // temp arrays
        int left[] = new int[size1];
        int right[] = new int[size2];
        // copying data to arrays
        for(int i=0;i < size1;i++)
            left[i] = this.array[l+i];
        for(int j=0;j < size2;j++)
            right[j] = this.array[m + 1 + j];

        // merge arrays
        int index1 = 0, index2 = 0;
        int index = l; // index of merged array
        while (index1 < size1 && index2 < size2) {
            // checking if thread is stoped or paused
            try {
                synchronized (this) {
                    if (this.finished) return;
                    while (stoped) wait();
                }
                synchronized (thread) {
                    thread.wait(sortAlg.wait_time / 5 * 3);
                }
            }
            catch (InterruptedException e) {}
            paintTwoPosition(l + index1,m + 1 + index2,sortAlg.pivot_color );
            synchronized (thread) {
                try {
                    thread.wait(sortAlg.wait_time);
                } catch (InterruptedException e) {}}
            paintTwoPosition(l+index1,m+1+index2,sortAlg.rect_color);
            delOnePositon(index);
            if (left[index1] < right[index2] ) {
                paintAndWait(index,0);
                this.array[index] = left[index1++];
                paintAndWait(index++,1);
            }
            else {
                paintAndWait(index,0);
                this.array[index] = right[index2++];
                paintAndWait(index++,1);
            }
        }
        // copy remaining elements
        while (index1 < size1) {
            paintAndWait(index,0);
            this.array[index] = left[index1++];
            paintAndWait(index++,1);
        }
        while (index2 < size2) {
            paintAndWait(index,0);
            this.array[index] = right[index2++];
            paintAndWait(index++,1);
        }
    }

    private void mSort(int l,int r) {
        if (l < r && !thread.isInterrupted()) {
            int m = (l+r) / 2; // middle point

            //  sort first and second half
            mSort(l,m);
            mSort(m+1,r);

            merge(l,m,r);
        }
    }


    @Override
    public void run() {
        mSort(0,array.length - 1);
    }

}
