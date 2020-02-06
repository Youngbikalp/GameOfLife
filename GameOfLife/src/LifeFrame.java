import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LifeFrame extends JFrame {
    private int wWindow = 1000;
    private int hWindow = 800;

    public Dimension getPreferredSize() {
        return new Dimension(wWindow, hWindow);
    }


    private int buttonsPanelWidth = 150;
    private int gridPanelWidth = 600;

    private int minN = 10;
    private int maxN = 50;

    JPanel buttonsPanel = new JPanel();
    JPanel gridPanel = new GamePanel();

    private JButton changeButton = new JButton("Change Grid Size");
    private JButton startButton = new JButton("Start game");
    private JButton stopButton = new JButton("Stop game");
    private JButton quitButton = new JButton("Quit");

    private JLabel stepLabel = new JLabel("Step: 0");

    private LifeGame game;
    private int nGrid = 20;

    LifeFrame() {

        this.setLocation(200, 100);
        this.setTitle("Conway's Game Of Life");
        setLayout(null);

        add(buttonsPanel);
        buttonsPanel.setBounds(0, 0, buttonsPanelWidth, hWindow);
        buttonsPanel.setLayout(null);

        add(gridPanel);

        gridPanel.setBounds(buttonsPanelWidth, 0, gridPanelWidth, gridPanelWidth);
        gridPanel.setLayout(null);

        changeButton.setBounds(10, 10, 120, 30);
        startButton.setBounds(10, 60, 120, 30);
        stopButton.setBounds(10, 110, 120, 30);
        quitButton.setBounds(10, 150, 120, 30);
        stepLabel.setBounds(10, 210, 120, 30);

        buttonsPanel.add(changeButton);
        buttonsPanel.add(startButton);
        buttonsPanel.add(stopButton);
        buttonsPanel.add(quitButton);
        buttonsPanel.add(stepLabel);

        stopButton.setEnabled(false);

        quitButton.addActionListener((e) -> System.exit(0));
        stopButton.addActionListener((e) -> doStop());

        changeButton.addActionListener(new ChangeNAction());
        startButton.addActionListener(new StartLifeAction());
        gridPanel.addMouseListener(new MouseReader());

        game = new LifeGame(nGrid);
        pack();
    }

    private class GamePanel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.LIGHT_GRAY);

            int cellSize = gridPanelWidth / nGrid;
            for (int i = 0; i < nGrid; i++) {
                for (int j = 0; j < nGrid; j++) {
                    g.setColor(game.getGridCell(i, j) > 0 ? Color.BLUE : Color.WHITE);
                    g.fillRect(j * cellSize + 1, i * cellSize + 1, cellSize - 1, cellSize - 1);

                }
            }
        }
    }

    private class ChangeNAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = JOptionPane.showInputDialog("Enter new N (" + minN + "-" + maxN + "): ");
            try {
                nGrid = Integer.parseInt(s);
                if (nGrid < minN) nGrid = minN;
                if (nGrid > maxN) nGrid = maxN;
            } catch (Exception ex) {
                nGrid = minN;
            }

            game = new LifeGame(nGrid);

            stepLabel.setText("Step: " + game.getStepNum());
            gridPanel.repaint();
        }
    }

    private Thread go;
    private boolean toStop;

    private class StartLifeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            stopButton.setEnabled(true);
            quitButton.setEnabled(false);
            startButton.setEnabled(false);
            changeButton.setEnabled(false);

            Runnable r = () -> {
                try {
                    while (!toStop) {

                        game.make1Step();
                        stepLabel.setText("Step: " + game.getStepNum());
                        gridPanel.repaint();

                        Thread.sleep(100);
                        if (!game.gridChanged()) {
                            doStop();
                        }
                    }
                } catch (InterruptedException ex) {
                    doStop();
                }
            };

            go = new Thread(r);
            toStop = false;
            go.start();
        }
    }

    private void doStop() {
        toStop = true;
        stopButton.setEnabled(false);
        quitButton.setEnabled(true);
        startButton.setEnabled(true);
        changeButton.setEnabled(true);
    }

    private class MouseReader implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (stopButton.isEnabled()) return;

            int cellSize = gridPanelWidth / nGrid;
            int x = e.getX() / cellSize;
            int y = e.getY() / cellSize;
            if (x >= 0 && x < nGrid && y >= 0 && y < nGrid) {
                game.setGridCell(y, x, 1 - game.getGridCell(y, x));
                gridPanel.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}