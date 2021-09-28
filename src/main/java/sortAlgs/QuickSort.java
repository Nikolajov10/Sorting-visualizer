package sortAlgs;

public class QuickSort extends sortAlg {

    public QuickSort() {super();}
    public QuickSort(int[] array) { super(array); }

    private void paintTwoAndWait(int pos1,int pos2) {
        paintTwoPosition(pos1,pos2,sortAlg.compare_color);
        try{
            synchronized (thread) {
                thread.wait(sortAlg.wait_time);
            }}catch(InterruptedException e) {}
    }

    @Override
    public void  sorting() {

    }

    private int partition(int low, int high) {
        int pivot = this.array[high];
        paintOnePosition(high,sortAlg.pivot_color);
        int i = low - 1; // index of smaller element

        for(int j=low;j <= high;j++) {
            // checking if thread is stoped or paused
            try {
                synchronized (this) {
                    if (this.finished) return - 1;
                    while (stoped) wait();
                }
                synchronized (thread) {
                    paintOnePosition(j,sortAlg.min_color);
                    thread.wait(sortAlg.wait_time / 5 * 4);
                    paintOnePosition(j,sortAlg.rect_color);
                }
            }
            catch (InterruptedException e) {}

            if (this.array[j] < pivot ) {
                i++;
                paintTwoAndWait(i,j);
                delTwoPosition(i,j);
                swap(i,j);
                paintTwoAndWait(i,j);
                paintTwoPosition(i,j,sortAlg.rect_color);

            }
        }
        paintTwoAndWait(++i,high);
        delTwoPosition(i,high);
        swap(i,high);
        paintTwoAndWait(i,high);
        paintTwoPosition(i,high,sortAlg.rect_color);
        paintOnePosition(high,sortAlg.rect_color);
        return i;
    }

    private void qSort(int low,int high) {
    if (low < high) {
        int p = partition(low, high);
        if (p == -1) return;
        qSort(low, p - 1);
        qSort(p + 1, high);
        }
    }

    @Override
    public void run() {
        qSort(0,this.array.length - 1);
    }
}
