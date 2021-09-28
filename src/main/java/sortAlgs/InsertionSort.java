package sortAlgs;

public class InsertionSort extends sortAlg {

    public InsertionSort() {super();}
    public InsertionSort(int[] array) {
        super(array);
    }

    @Override
    public void sorting() {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    @Override
    public void run() {
        try {
            while (!thread.interrupted()) {
                for (int i = 1; i <  array.length; i++) {

                    // checking if thread is stopped or paused
                    synchronized (this) {
                        if (this.finished) {
                            thread.interrupt();
                            break;
                        }
                    }
                    synchronized (this) {
                        while (stoped) wait();
                    }

                    int key = array[i];
                    int j = i - 1;
                    paintOnePosition(i,sortAlg.compare_color);
                    try {
                        synchronized (thread) {
                            thread.wait(sortAlg.wait_time);
                        }
                    } catch (InterruptedException e) {
                        break;
                    }
                    paint();
                    while ( j >= 0 && array[j] > key) {
                        // checking if thread is stopped or paused
                        synchronized (this) {
                            if (this.finished) {
                                thread.interrupt();
                                break;
                            }
                        }
                        synchronized (this) {
                            while (stoped) wait();
                        }
                        paintTwoPosition(j,j+1,sortAlg.compare_color);
                        synchronized (thread) {
                            try {
                                thread.wait(sortAlg.wait_time * 3);
                            }
                            catch (InterruptedException e) {break;}
                        }
                        delTwoPosition(j,j+1);
                        array[j+1] = array[j];
                        paintTwoPosition(j,j+1,sortAlg.compare_color);
                        try {
                            synchronized (thread) {
                                thread.wait(sortAlg.wait_time*3);
                            }
                        } catch (InterruptedException e) {
                            break;
                        }
                        paintTwoPosition(j,j+1,sortAlg.rect_color);
                        j--;
                    }
                    delOnePositon(j+1);
                    array[j+1] = key;
                    paintOnePosition(j+1,sortAlg.compare_color);
                    synchronized (thread) {
                        try {
                            thread.wait(sortAlg.wait_time*2);
                        }
                        catch (InterruptedException e) {break;}
                    }
                    paintOnePosition(j+1,sortAlg.rect_color);
                }
                thread.interrupt();
            }
        }
        catch (Exception e) { e.printStackTrace();}
    }

}
