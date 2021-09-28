package app;

import sortAlgs.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SortingApp extends JFrame {
    private sortAlg alg = new BubbleSort();
    private JButton start_btn = new JButton("Start");
    private JButton pause_btn = new JButton("Pause");
    private JButton end_btn = new JButton("End");
    private Visualizer visualizer;
    private JLabel alg_name = new JLabel("Algorithm:  Bubble sort");
    String algs[] = {"Bubble sort","Insertion sort","Merge sort","Quicksort",
            "Selection sort"};

    public SortingApp() {
        super("Sorting App");
        int n = alg.getArraySize();
        int width = (sortAlg.width + sortAlg.offset) * n + 20;
        int height = sortAlg.max_height + 105;
        this.setSize(width,height);

        addMenuBar();
        addButtonActions();
        populateWindow();
    }
    private void addButtonActions() {
        start_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (start_btn.getText() == "Start") {
                    alg.sort();
                    start_btn.setEnabled(false);
                    start_btn.setText("Continue");
                    pause_btn.setEnabled(true);
                }
                else {
                    alg.continue_work();
                    start_btn.setEnabled(false);
                    pause_btn.setEnabled(true);
                }
            }
        });
        pause_btn.setEnabled(false);
        pause_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alg.stop_work();
                pause_btn.setEnabled(false);
                start_btn.setEnabled(true);
            }
        });
        end_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alg.end_work();
                start_btn.setText("Start");
                start_btn.setEnabled(true);
                pause_btn.setEnabled(false);
            }
        });
    }

    private void populateWindow() {
        JPanel main_panel = new JPanel(new BorderLayout());
        visualizer = new Visualizer(this.alg);
        visualizer.setBackground(Color.WHITE);
        main_panel.add(BorderLayout.CENTER,visualizer);
        main_panel.add(BorderLayout.NORTH,alg_name);
        JPanel button_panel = new JPanel(new GridLayout(1,0));
        button_panel.add(start_btn);button_panel.add(pause_btn);button_panel.add(end_btn);
        main_panel.add(BorderLayout.SOUTH,button_panel);
        add(main_panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        alg.setGraphics(visualizer.getGraphics());
    }
    private void addMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Chose algorithm");
        JMenuItem alg1;
        for(String s:algs) {
            alg1 = new JMenuItem(s);
            alg1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String sort = e.getActionCommand();
                    alg.end_work();
                    if ( sort == algs[0]) alg = new BubbleSort();
                    else if (sort == algs[1]) alg = new InsertionSort();
                    else if (sort == algs[2]) alg = new MergeSort();
                    else if (sort == algs[3]) alg = new QuickSort();
                    else if (sort == algs[4]) alg = new SelectionSort();
                    visualizer.setAlg(alg);
                    alg.setGraphics(visualizer.getGraphics());
                    alg_name.setText("Algorithm:  " + sort);
                    start_btn.setEnabled(true);
                    start_btn.setText("Start");
                    pause_btn.setEnabled(false);
                }
            });
            menu.add(alg1);
        }
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }
}
