package sortAlgs;

import java.awt.*;

public class Visualizer extends Canvas {
    private sortAlg alg;
    public Visualizer(sortAlg Alg) {
        super();
        alg = Alg;
    }

    @Override
    public void paint(Graphics g) {
        alg.paint();
    }
    public void setAlg(sortAlg Alg) {
        Graphics g = getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,getWidth(),getHeight());
        alg=Alg;
    }
}
