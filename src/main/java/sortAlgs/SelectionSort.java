package sortAlgs;
import java.awt.Color;
public class SelectionSort  extends sortAlg{

    public SelectionSort() {super();}
    public SelectionSort(int[] array) {
        super(array);
    }

    private void paintAndWait(int index,Color c) {
        paintOnePosition(index,c);
        synchronized (thread) {
            try {
                thread.wait(sortAlg.wait_time);
            }
            catch (InterruptedException e) {}
        }
    }

    @Override
    public void sorting() {

    }

    @Override
    public void run() {

        while (!thread.isInterrupted()) {
            for(int i=0; i < this.array.length - 1;i++) {
                // checking if thread is stoped or paused
                try {
                    synchronized (this) {
                        if (this.finished) {
                            thread.interrupt();break;
                        }
                        while (stoped) wait();
                    }
                    synchronized (thread) {
                        thread.wait(sortAlg.wait_time / 5 * 3);
                    }
                }
                catch (InterruptedException e) {}
                int min = this.array[i];
                int position = i; // position of minimum

                paintAndWait(i,sortAlg.min_color);

                for(int j = i + 1;j<this.array.length;j++) {
                    paintAndWait(j,sortAlg.compare_color);
                    paintOnePosition(j,sortAlg.rect_color);
                    if (this.array[j] < min) {
                        paintOnePosition(position,sortAlg.rect_color);
                        min = this.array[j];
                        position = j;
                        paintAndWait(position,sortAlg.min_color);
                    }
                }
                delTwoPosition(position,i);
                this.array[position] = this.array[i];
                this.array[i] = min;
                paintTwoPosition(i,position,sortAlg.compare_color);
                synchronized (thread) {
                    try {
                        thread.wait(sortAlg.wait_time / 2 * 3);
                    }
                    catch (InterruptedException e) {}
                }
                paintTwoPosition(i,position,sortAlg.rect_color);
            }
            thread.interrupt();
        }

    }
}
