//Bikalp Timalsina
//Conway's Game Of Life
//CIS 36A
import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame frame = new LifeFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}