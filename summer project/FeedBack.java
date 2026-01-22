import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;

public class FeedBack implements ActionListener {

    JFrame frame;
    JLabel PRNLabel = new JLabel("USER ID");
    JLabel Title = new JLabel("Your Feedback Improves Us, Kindly Give Your 5 Minutes To Give Your Feedback.");
    JTextField PRNTextField = new JTextField();
    JLabel feedbackLabel = new JLabel("Feedback");
    JTextArea fd = new JTextArea();
    JButton submitButton = new JButton("Submit");
    JLabel image = new JLabel();
    Icon imgIcon;

    FeedBack() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        actionEvent();
    }

    public void createWindow() {
        frame = new JFrame();
        frame.setTitle("Feedback");
        frame.setBounds(300, 100, 1000, 700);
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public void setLocationAndSize() {
        Title.setBounds(100, 30, 800, 40);
        Title.setFont(new Font("Cambria", Font.BOLD, 22));
        Title.setForeground(Color.RED);

        PRNLabel.setBounds(100, 100, 200, 40);
        PRNLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        PRNLabel.setForeground(Color.BLACK);

        PRNTextField.setBounds(300, 100, 300, 40);
        PRNTextField.setFont(new Font("Cambria", Font.PLAIN, 18));

        feedbackLabel.setBounds(100, 180, 200, 40);
        feedbackLabel.setFont(new Font("Cambria", Font.BOLD, 20));
        feedbackLabel.setForeground(Color.BLACK);

        fd.setBounds(300, 180, 500, 200);
        fd.setFont(new Font("Cambria", Font.PLAIN, 18));
        fd.setBackground(Color.WHITE);

        imgIcon = new ImageIcon("C:\\Users\\DELL\\Downloads\\feedbacks.jpeg");
        image.setBounds(850, 30, 120, 120);
        image.setIcon(imgIcon);

        submitButton.setBounds(400, 420, 150, 40);
        submitButton.setFont(new Font("Cambria", Font.BOLD, 18));
    }

    public void addComponentsToFrame() {
        frame.add(Title);
        frame.add(PRNLabel);
        frame.add(PRNTextField);
        frame.add(feedbackLabel);
        frame.add(fd);
        frame.add(submitButton);
        frame.add(image);
    }

    public void actionEvent() {
        submitButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String prn = PRNTextField.getText().trim();
            String feedbackText = fd.getText().trim();

            if (prn.equals("")) {
                JOptionPane.showMessageDialog(null, "User ID is missing");
            } else if (feedbackText.equals("")) {
                JOptionPane.showMessageDialog(null, "Feedback cannot be empty");
            } else {
                try {
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/project", "root", "hritik7982");

                    
                    String query = "SELECT * FROM User WHERE PRN = ?";
                    PreparedStatement checkStmt = conn.prepareStatement(query);
                    checkStmt.setString(1, prn);
                    ResultSet rs = checkStmt.executeQuery();

                    if (rs.next()) {
                        
                        PreparedStatement pst = conn.prepareStatement("INSERT INTO feedback (PRN, Feedback) VALUES (?, ?)");
                        System.out.println("My PRN"+ prn);
                        System.out.println("My Feedback" + feedbackText);
                        pst.setString(1, prn);
                        pst.setString(2, feedbackText);
                        pst.executeUpdate();

                        JOptionPane.showMessageDialog(null, "Thank you for your valuable feedback!");

                        
                        
                        frame.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid User ID");
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new FeedBack();
    }
}
