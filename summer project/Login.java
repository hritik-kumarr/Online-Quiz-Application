import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class Login implements ActionListener {
    JFrame frame;
    JFrame homeFrame; 

    JLabel PRNLabel = new JLabel("USERID");
    JLabel Title = new JLabel("LOGIN FORM");
    JTextField PRNTextField = new JTextField();
    JLabel passwordLabel = new JLabel("PASSWORD");
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Login");
    JLabel image = new JLabel();
    Icon p;

    public Login(JFrame homeFrame) {
        this.homeFrame = homeFrame;
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        actionEvent();
    }

    public void createWindow() {
        frame = new JFrame();
        frame.setTitle("Login Form");
        frame.setBounds(200, 100, 900, 700);
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public void setLocationAndSize() {
        Title.setBounds(300, 50, 400, 40);
        Title.setFont(new Font("Cambria", Font.CENTER_BASELINE, 25));
        Title.setForeground(Color.red);

        PRNLabel.setBounds(200, 200, 400, 40);
        PRNLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 25));
        PRNLabel.setForeground(Color.red);

        PRNTextField.setBounds(200, 250, 400, 40);
        PRNTextField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 20));

        passwordLabel.setBounds(200, 300, 400, 40);
        passwordLabel.setFont(new Font("Cambria", Font.CENTER_BASELINE, 20));
        passwordLabel.setForeground(Color.red);

        passwordField.setBounds(200, 350, 400, 40);
        passwordField.setFont(new Font("Cambria", Font.CENTER_BASELINE, 20));

        p = new ImageIcon("D:\\summer project\\homepagephoto.webp");
        image.setBounds(10, 130, 800, 419);
        image.setIcon(p);

        loginButton.setBounds(200, 450, 400, 40);
    }

    public void addComponentsToFrame() {
        frame.add(Title);
        frame.add(PRNLabel);
        frame.add(PRNTextField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(loginButton);
        frame.add(image);
    }

    public void actionEvent() {
        loginButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            try {
                Connection conni = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/project", "root", "hritik7982");

                String prn = PRNTextField.getText().trim();
                String pass = new String(passwordField.getPassword()).trim();

                String query = "SELECT * FROM User WHERE PRN = ? AND Password = ?";
                PreparedStatement pstmt = conni.prepareStatement(query);
                pstmt.setString(1, prn);
                pstmt.setString(2, pass);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Logged in successfully!");

                    PreparedStatement Pstatement = conni.prepareStatement(
                        "INSERT INTO login (PRN, Password) VALUES (?, ?)");
                    Pstatement.setString(1, prn);
                    Pstatement.setString(2, pass);
                    Pstatement.executeUpdate();

                    frame.setVisible(false); 
                    if (homeFrame != null) homeFrame.setVisible(false); 

                    Quiz1 q = new Quiz1();

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Credentials");
                }

                rs.close();
                pstmt.close();
                conni.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }
}
