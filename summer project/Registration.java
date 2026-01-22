import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.*;

public class Registration implements ActionListener {

    JFrame frame;

    JLabel Title = new JLabel("Registration Form");
    JLabel prnLabel = new JLabel("PRN");
    JLabel nameLabel = new JLabel("Username");
    JLabel addressLabel = new JLabel("Address");
    JLabel emaiLabel = new JLabel("Email");
    JLabel contactLabel = new JLabel("Contact");
    JLabel classLabel = new JLabel("Class");
    JLabel passwordLabel = new JLabel("Password");
    JLabel branchLabel = new JLabel("Branch");

    JTextField prnTextField = new JTextField();
    JTextField nameTextField = new JTextField();
    JTextField contactTextField = new JTextField();
    JTextField emailTextField = new JTextField();

    JTextArea addressTextArea = new JTextArea();
    JScrollPane addressScroll; // new scroll pane

    JPasswordField passwordField = new JPasswordField();

    String[] branch = {"CSIT", "CSE", "ECE", "ELECTRICAL", "MECHANICAL", "CIVIL", "AUTOMOBILE", "MECHATRONICS"};
    String[] Class = {"1st year", "2nd year", "3rd year", "4th year"};

    JComboBox className = new JComboBox(Class);
    JComboBox branchName = new JComboBox(branch);

    JButton SubmitButton = new JButton("Submit");
    JButton ResetButton = new JButton("Reset");

    public Registration() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        actionEvent();
    }

    public void createWindow() {
        frame = new JFrame();
        frame.setTitle("Student Details Form");
        frame.setBounds(100, 100, 600, 700); // width=600, height=700 to fit compact layout
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
    }

    public void setLocationAndSize() {
        Font labelFont = new Font("Cambria", Font.PLAIN, 16);

        int xLabel = 50;
        int xField = 200;
        int widthLabel = 140;
        int widthField = 300;
        int height = 30;
        int yGap = 10;
        int y = 20;

        Title.setBounds(150, y, 400, 40);
        Title.setFont(new Font("Cambria", Font.BOLD, 24));
        y += 50;

        prnLabel.setBounds(xLabel, y, widthLabel, height);
        prnLabel.setFont(labelFont);
        prnTextField.setBounds(xField, y, widthField, height);
        prnTextField.setFont(labelFont);
        y += height + yGap;

        nameLabel.setBounds(xLabel, y, widthLabel, height);
        nameLabel.setFont(labelFont);
        nameTextField.setBounds(xField, y, widthField, height);
        nameTextField.setFont(labelFont);
        y += height + yGap;

        emaiLabel.setBounds(xLabel, y, widthLabel, height);
        emaiLabel.setFont(labelFont);
        emailTextField.setBounds(xField, y, widthField, height);
        emailTextField.setFont(labelFont);
        y += height + yGap;

        passwordLabel.setBounds(xLabel, y, widthLabel, height);
        passwordLabel.setFont(labelFont);
        passwordField.setBounds(xField, y, widthField, height);
        passwordField.setFont(labelFont);
        y += height + yGap;

        classLabel.setBounds(xLabel, y, widthLabel, height);
        classLabel.setFont(labelFont);
        className.setBounds(xField, y, widthField, height);
        className.setFont(labelFont);
        y += height + yGap;

        branchLabel.setBounds(xLabel, y, widthLabel, height);
        branchLabel.setFont(labelFont);
        branchName.setBounds(xField, y, widthField, height);
        branchName.setFont(labelFont);
        y += height + yGap;

        contactLabel.setBounds(xLabel, y, widthLabel, height);
        contactLabel.setFont(labelFont);
        contactTextField.setBounds(xField, y, widthField, height);
        contactTextField.setFont(labelFont);
        y += height + yGap;

        addressLabel.setBounds(xLabel, y, widthLabel, height);
        addressLabel.setFont(labelFont);

        addressScroll = new JScrollPane(addressTextArea);
        addressScroll.setBounds(xField, y, widthField, height * 2);
        addressTextArea.setFont(labelFont);
        y += height * 2 + yGap;

        SubmitButton.setBounds(xField, y, 100, height + 5);
        SubmitButton.setFont(labelFont);
        ResetButton.setBounds(xField + 120, y, 100, height + 5);
        ResetButton.setFont(labelFont);
    }

    public void addComponentsToFrame() {
        frame.add(Title);
        frame.add(prnLabel);
        frame.add(prnTextField);
        frame.add(nameLabel);
        frame.add(nameTextField);
        frame.add(emaiLabel);
        frame.add(emailTextField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(classLabel);
        frame.add(className);
        frame.add(branchLabel);
        frame.add(branchName);
        frame.add(contactLabel);
        frame.add(contactTextField);
        frame.add(addressLabel);
        frame.add(addressScroll); // add scroll pane instead of bare textarea
        frame.add(SubmitButton);
        frame.add(ResetButton);
    }

    public void actionEvent() {
        SubmitButton.addActionListener(this);
        ResetButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == SubmitButton) {
            try (Connection conni = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/project", "root", "hritik7982")) {
                PreparedStatement Pstatement = conni.prepareStatement(
                        "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

                Pstatement.setString(1, prnTextField.getText());
                Pstatement.setString(2, nameTextField.getText());
                Pstatement.setString(3, emailTextField.getText());
                Pstatement.setString(4, passwordField.getText());
                Pstatement.setString(5, className.getSelectedItem().toString());
                Pstatement.setString(6, contactTextField.getText());
                Pstatement.setString(7, branchName.getSelectedItem().toString());
                Pstatement.setString(8, addressTextArea.getText());

                String prn = prnTextField.getText();
                String pass = passwordField.getText();
                String email = emailTextField.getText();

                if (prn.equals("")) {
                    JOptionPane.showMessageDialog(null, "PRN is missing");
                } else if (pass.equals("")) {
                    JOptionPane.showMessageDialog(null, "Password is missing");
                } else if (email.equals("")) {
                    JOptionPane.showMessageDialog(null, "Email is missing");
                } else {
                    try (Statement stmt = conni.createStatement();
                         ResultSet rs = stmt.executeQuery("SELECT PRN, Password FROM user")) {
                        boolean userExists = false;
                        while (rs.next()) {
                            if (rs.getString("PRN").equals(prn) || rs.getString("Password").equals(pass)) {
                                userExists = true;
                                JOptionPane.showMessageDialog(null, "PRN or Password already exists");
                                break;
                            }
                        }
                        if (!userExists) {
                            Pstatement.executeUpdate();
                            JOptionPane.showMessageDialog(null, "User Registered Successfully");
                            frame.setVisible(false);
                            Login loginObj = new Login(frame);
                        }
                    }
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Database error: " + e1.getMessage());
            }
        }

        if (e.getSource() == ResetButton) {
            prnTextField.setText("");
            nameTextField.setText("");
            addressTextArea.setText("");
            contactTextField.setText("");
            className.setSelectedIndex(0);
            branchName.setSelectedIndex(0);
            passwordField.setText("");
            emailTextField.setText("");
        }
    }
}

class Reg {
    public static void main(String args[]) {
        new Registration();
    }
}
