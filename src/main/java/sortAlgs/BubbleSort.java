package sortAlgs;

public class BubbleSort extends sortAlg {

    public BubbleSort() {
        super();
    }

    public BubbleSort(int[] array) {
        super(array);
    }

    @Override
    public void run() {
        try {
            while (!thread.isInterrupted()) {
                int pos = this.array.length - 1;
                do {
                    int bound = pos;
                    pos = -1;
                    for (int i = 0; i <= bound - 1; i++) {

                        // checking if thread is stoped or paused
                        synchronized (this) {
                            if (this.finished) break;
                        }
                        synchronized (this) {
                            while (stoped) wait();
                        }
                        try {
                            synchronized (thread) {
                                thread.wait(sortAlg.wait_time / 5 * 4);
                            }
                        } catch (InterruptedException e) {
                            break;
                        }
                        if (this.array[i] > this.array[i + 1]) {
                            paintTwoPosition(i, i + 1, sortAlg.compare_color);
                            synchronized (thread) {
                                try {
                                    thread.wait(sortAlg.wait_time * 2);
                                } catch (InterruptedException e) {
                                    break;
                                }
                            }
                            delTwoPosition(i, i + 1);
                            this.swap(i, i + 1);
                            paintTwoPosition(i, i + 1, sortAlg.compare_color);
                            try {
                                synchronized (thread) {
                                    thread.wait(sortAlg.wait_time * 2);
                                }
                            } catch (InterruptedException e) {
                                break;
                            }
                            paintTwoPosition(i, i + 1, sortAlg.rect_color);
                            pos = i;
                        }
                    }
                }
                while (pos != -1);
                thread.interrupt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sorting() {
        /**
         * This is for test purpose
         * no functions related to GUI
         * and thread
         */
        int pos = this.array.length - 1;
        do {
            int bound = pos;
            pos = -1;
            for (int i = 0; i <= bound - 1; i++) {
                if (this.array[i] > this.array[i + 1]) {
                    this.swap(i, i + 1);
                    pos = i;
                }
            }
        }
        while (pos != -1);
    }
}