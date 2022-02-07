import javax.swing.*;
import java.awt.*;

public class SwingApp {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Cryptography");
        jFrame.setSize(new Dimension(540, 680));
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel rootPanel = new JPanel();
        JLabel jLabel = new JLabel();
        jLabel.setText("Enter your name");
        JTextField jTextField = new JTextField();
        jTextField.setColumns(20);
        JButton jButton = new JButton("Enter");
      //  jButton.addActionListener();


        rootPanel.add(jLabel);
        rootPanel.add(jTextField);
        rootPanel.add(jButton);

        jFrame.getContentPane().add(rootPanel);
        jFrame.setVisible(true);

    }
}
