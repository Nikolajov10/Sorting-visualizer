package sortAlgs;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

abstract public class sortAlg implements Runnable {
    protected int array[];
    public static int width = 30;
    public static int offset = 5;
    public static int max_height = 400;
    public static int height_mod = 4;
    public static Color rect_color = Color.CYAN;
    public static Color compare_color = Color.RED;
    public static Color min_color = Color.YELLOW;
    public static Color pivot_color = Color.GREEN;
    public static long wait_time = 500; // time to wait in ms
    protected Thread thread=null;
    protected boolean finished = false;
    protected boolean stoped = false;
    protected Graphics g;

    public sortAlg() {
        /**
        * Generating random array
        *  when there is no argument
        * in constructor.
        * */
         int n = 10;
         this.array = new int[n];
         int min = 5;
         int max = 80;
         for(int i=0; i < n;i++)
             this.array[i] = (int) (Math.random() * (max - min + 1) + min);
    }
    public sortAlg(int[] array) {
        this.array = array;
    }

    public int[] getArray() {return this.array;}

    public void setGraphics(Graphics g) {this.g=g;}

    private void drawRect(Graphics g,int pos,int x,int y,Color c) {
        // drawing rect
        g.setColor(c);
        g.fillRect(x,y,sortAlg.width,this.array[pos]*sortAlg.height_mod);
        // drawing string
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(array[pos]),x+(int)sortAlg.width/4,sortAlg.max_height - 2);
    }

    public void paint() {
        int x = 0;
        int y=0;
        for (int i=0;i<this.array.length;i++) {
            x+=sortAlg.offset;
            y = sortAlg.max_height - this.array[i]*sortAlg.height_mod;
            drawRect(g,i,x,y,sortAlg.rect_color);
            x += sortAlg.width;
        }
    }

    public int getArraySize() { return this.array.length; }

    public void sort() {
        finished = false;
        stoped = false;
        thread = new Thread(this);
        thread.start();
    }

    protected void swap(int i,int j) {
        /**
         * swaping array elements on
         * positons i & j
         */
        int temp = this.array[i];
        this.array[i] = this.array[j];
        this.array[j] = temp;
    }

    protected void paintTwoPosition(int pos1,int pos2,Color c) {
        /**
         * paint rect in red to showcase
         * which two numbers are
         * being compared
        * */
        int x1 =sortAlg.offset*(pos1+1) + sortAlg.width*(pos1);
        int y1 = sortAlg.max_height - this.array[pos1]*sortAlg.height_mod;
        int x2 =sortAlg.offset*(pos2+1) + sortAlg.width*(pos2);
        int y2 = sortAlg.max_height - this.array[pos2]*sortAlg.height_mod;
        // painting rects
        drawRect(g,pos1,x1,y1,c);
        drawRect(g,pos2,x2,y2,c);
    }

    protected void paintOnePosition(int pos,Color c) {
        int x1 =sortAlg.offset*(pos+1) + sortAlg.width*(pos);
        int y1 = sortAlg.max_height - this.array[pos]*sortAlg.height_mod;
        // painting rects
        drawRect(g,pos,x1,y1,c);
    }
    protected void  delOnePositon(int pos) {
        int x1 =sortAlg.offset*(pos+1) + sortAlg.width*(pos);
        int y1 = sortAlg.max_height - this.array[pos]*sortAlg.height_mod;
        // painting rects
        drawRect(g,pos,x1,y1,Color.WHITE);
    }

    protected void delTwoPosition(int pos1,int pos2) {
        /**
         * paint rects in white
         * for numbers that were compared
         * */
        int x1 =sortAlg.offset*(pos1+1) + sortAlg.width*(pos1);
        int y1 = sortAlg.max_height - this.array[pos1]*sortAlg.height_mod;
        int x2 =sortAlg.offset*(pos2+1) + sortAlg.width*(pos2);
        int y2 = sortAlg.max_height - this.array[pos2]*sortAlg.height_mod;
        // painting rects
        drawRect(g,pos1,x1,y1,Color.WHITE);
        drawRect(g,pos2,x2,y2,Color.WHITE);

    }
    public synchronized void stop_work() {stoped=true;}
    public synchronized void continue_work() {
        stoped=false;
        notify();
    }
    public synchronized void end_work() {
        finished=true;
        if (thread!=null) thread.interrupt();
    }
    public void waitFinish() {
        try {
            thread.join();
        } catch (InterruptedException e) {}
    }
    abstract public void sorting();
}
