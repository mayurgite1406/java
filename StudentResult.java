
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class StudentResult extends JFrame {
    JTextField name, roll, java, maths, electronics;

    StudentResult() {
        setTitle("Student Examination Form");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(6, 2, 8, 8));

        name = new JTextField();
        roll = new JTextField();
        java = new JTextField();
        maths = new JTextField();
        electronics = new JTextField();

        add(new JLabel("Student Name:"));
        add(name);

        add(new JLabel("Roll Number:"));
        add(roll);

        add(new JLabel("Java Marks:"));
        add(java);

        add(new JLabel("Maths Marks:"));
        add(maths);

        add(new JLabel("Electronics Marks:"));
        add(electronics);

        JButton button = new JButton("Show Result");
        add(new JLabel(""));
        add(button);

        button.addActionListener(e -> showResult());
    }

    void showResult() {
        try {
            String n = name.getText();
            String r = roll.getText();

            int j = Integer.parseInt(java.getText());
            int m = Integer.parseInt(maths.getText());
            int el = Integer.parseInt(electronics.getText());

            if (n.trim().isEmpty() || r.trim().isEmpty()
                    || j < 0 || j > 100
                    || m < 0 || m > 100
                    || el < 0 || el > 100) {
                JOptionPane.showMessageDialog(this,
                        "Enter valid details and marks from 0 to 100.");
                return;
            }

            int total = j + m + el;
            double percentage = total / 3.0;

            String result =
                    (j >= 35 && m >= 35 && el >= 35)
                    ? "PASS" : "FAIL";

            JFrame frame = new JFrame("Student Result");
            frame.setSize(350, 300);
            frame.setLocationRelativeTo(this);

            JTextArea area = new JTextArea();
            area.setEditable(false);

            area.setText(
                    "STUDENT RESULT\n\n" +
                    "Name: " + n + "\n" +
                    "Roll Number: " + r + "\n\n" +
                    "Java: " + j + "/100\n" +
                    "Maths: " + m + "/100\n" +
                    "Electronics: " + el + "/100\n\n" +
                    "Total: " + total + "/300\n" +
                    String.format("Percentage: %.2f%%\n",
                            percentage) +
                    "Result: " + result
            );

            frame.add(new JScrollPane(area));
            frame.setDefaultCloseOperation(
                    JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Please enter valid numeric marks.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new StudentResult().setVisible(true));
    }
}
