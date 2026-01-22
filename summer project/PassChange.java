import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PassChange implements ActionListener {
    JFrame frame;

    JLabel prnLabel = new JLabel("PRN");
    JLabel passwordLabel = new JLabel("Password");
    JLabel changePassLabel = new JLabel("New Password");
    JLabel repassLabel = new JLabel("Re-Type New Password");
    JLabel Title = new JLabel("Change Your Password If You Don't Remember!");

    JPasswordField passwordField = new JPasswordField();
    JPasswordField changePassField = new JPasswordField();
    JPasswordField rePasswordField = new JPasswordField();

    JTextField prnTextField = new JTextField();

    JButton ChangeButton = new JButton("Change");

    PassChange() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        actionEvent();
    }

    public void createWindow() {
        frame = new JFrame();
        frame.setTitle("Change Password Form");
        frame.setBounds(50, 10, 900, 700);
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public void setLocationAndSize() {
        Title.setBounds(100, 10, 600, 40);
        Title.setFont(new Font("Cambria", Font.BOLD, 20));

        prnLabel.setBounds(100, 70, 200, 30);
        prnLabel.setFont(new Font("Cambria", Font.PLAIN, 16));

        prnTextField.setBounds(300, 70, 250, 30);
        prnTextField.setFont(new Font("Cambria", Font.PLAIN, 16));

        passwordLabel.setBounds(100, 120, 200, 30);
        passwordLabel.setFont(new Font("Cambria", Font.PLAIN, 16));

        passwordField.setBounds(300, 120, 250, 30);
        passwordField.setFont(new Font("Cambria", Font.PLAIN, 16));

        changePassLabel.setBounds(100, 170, 200, 30);
        changePassLabel.setFont(new Font("Cambria", Font.PLAIN, 16));

        changePassField.setBounds(300, 170, 250, 30);
        changePassField.setFont(new Font("Cambria", Font.PLAIN, 16));

        repassLabel.setBounds(100, 220, 200, 30);
        repassLabel.setFont(new Font("Cambria", Font.PLAIN, 16));

        rePasswordField.setBounds(300, 220, 250, 30);
        rePasswordField.setFont(new Font("Cambria", Font.PLAIN, 16));

        ChangeButton.setBounds(250, 300, 150, 40);
        ChangeButton.setFont(new Font("Cambria", Font.BOLD, 16));
    }

    public void addComponentsToFrame() {
        frame.add(Title);
        frame.add(prnLabel);
        frame.add(prnTextField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(changePassLabel);
        frame.add(changePassField);
        frame.add(repassLabel);
        frame.add(rePasswordField);
        frame.add(ChangeButton);
    }

    public void actionEvent() {
        ChangeButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ChangeButton) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/project", "root", "hritik7982");

                String prn = prnTextField.getText().trim();
                String pass = new String(passwordField.getPassword()).trim();
                String changePass = new String(changePassField.getPassword()).trim();
                String rePass = new String(rePasswordField.getPassword()).trim();

                if (prn.equals("")) {
                    JOptionPane.showMessageDialog(null, "PRN is missing");
                } else if (pass.equals("")) {
                    JOptionPane.showMessageDialog(null, "Password is missing");
                } else if (changePass.equals("")) {
                    JOptionPane.showMessageDialog(null, "New Password is missing");
                } else if (!changePass.equals(rePass)) {
                    JOptionPane.showMessageDialog(null, "Passwords do not match");
                } else {
                    Statement stmt = conn.createStatement();
                    String query = "SELECT * FROM User WHERE PRN='" + prn + "' AND Password='" + pass + "'";
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        PreparedStatement pst = conn.prepareStatement("UPDATE User SET Password=? WHERE PRN=?");
                        pst.setString(1, changePass);
                        pst.setString(2, prn);
                        pst.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Password updated successfully");
                        frame.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid PRN or Password");
                    }
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new PassChange();
    }
}
