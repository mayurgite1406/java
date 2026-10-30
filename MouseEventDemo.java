import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MouseEventDemo extends JFrame {

    MouseEventDemo() {
        setTitle("Mouse Event Demo");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel(
            "Move mouse here to show or hide frame",
            SwingConstants.CENTER
        );

        add(label);

        label.addMouseListener(new MouseListener() {

            public void mouseEntered(MouseEvent e) {
                System.out.println("Mouse Entered");
            }

            public void mouseExited(MouseEvent e) {
                System.out.println("Mouse Exited");
            }

            public void mouseClicked(MouseEvent e) {}
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new MouseEventDemo();
    }
}