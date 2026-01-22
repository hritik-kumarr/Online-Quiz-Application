
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import java.sql.*;

public class HomePagee implements ActionListener {
    static String prn = null;
    JFrame frame;
    JLabel welcome = new JLabel("Welcome to Quiz World Application. It Accelerates Your Studies!");
    
    JMenuBar menuBar = new JMenuBar();
    JMenu menu1 = new JMenu("File");
    JMenu menu2 = new JMenu("Help");
    JMenu menu3 = new JMenu("Feedback");

    JMenuItem menuItem1 = new JMenuItem("New User Registration");
    JMenuItem menuItem2 = new JMenuItem("Login");
    JMenuItem menuItem3 = new JMenuItem("Take Quiz");
    JMenuItem menuItem4 = new JMenuItem("LogOut");
    JMenuItem menuItem5 = new JMenuItem("Change Password");
    JMenuItem menuItem6 = new JMenuItem("Give Feedback");

    JLabel image = new JLabel();

    HomePagee() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        actionEvent();
    }

    public void createWindow() {
        frame = new JFrame();
        frame.setTitle("Online Quiz Application");
        frame.setBounds(200, 100, 900, 700); 
        frame.getContentPane().setBackground(Color.white);
        frame.getContentPane().setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setJMenuBar(menuBar);
    }

    public void setLocationAndSize() {
        Font menuFont = new Font("Cambria", Font.PLAIN, 16); 

        menu1.setFont(menuFont);
        menu2.setFont(menuFont);
        menu3.setFont(menuFont);

        menuItem1.setFont(menuFont);
        menuItem2.setFont(menuFont);
        menuItem3.setFont(menuFont);
        menuItem4.setFont(menuFont);
        menuItem5.setFont(menuFont);
        menuItem6.setFont(menuFont);

        menu1.add(menuItem1);
        menu1.add(menuItem2);
        menu1.add(menuItem3);
        menu1.add(menuItem4);

        menu2.add(menuItem5);
        menu3.add(menuItem6);

        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);

        welcome.setBounds(50, 40, 800, 40); 
        welcome.setBackground(Color.white);
        welcome.setFont(new Font("Cambria", Font.BOLD, 20));
        welcome.setForeground(Color.RED);

        
        String imagePath = "D:\\summer project\\B2.jpg"; 
        File imageFile = new File(imagePath);

        if (!imageFile.exists()) {
            System.err.println("Image file not found at: " + imageFile.getAbsolutePath());
        } else {
            ImageIcon originalIcon = new ImageIcon(imageFile.getAbsolutePath());
            Icon quiz = new ImageIcon(
                originalIcon.getImage()
                    .getScaledInstance(1000, 600, java.awt.Image.SCALE_SMOOTH)
            );
            image.setBounds(0, 0, 900, 650);
            image.setIcon(quiz);
        }
    }

    public void addComponentsToFrame() {
        frame.add(welcome);
        frame.add(image);
    }

    public void actionEvent() {
        menuItem1.addActionListener(this);
        menuItem2.addActionListener(this);
        menuItem3.addActionListener(this);
        menuItem4.addActionListener(this);
        menuItem5.addActionListener(this);
        menuItem6.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuItem1) {
            new Registration();
        }
        if (e.getSource() == menuItem2) {
            new Login(frame);
        }
        if (e.getSource() == menuItem3) {
            prn = JOptionPane.showInputDialog("Enter Your PRN");
            if (prn == null || prn.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "PRN cannot be empty.");
                return;
            }
            try (Connection conni = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3307/project", "root", "hritik7982");
                 Statement stmt = conni.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT PRN FROM Login")) {

                boolean found = false;
                while (rs.next()) {
                    String userId = rs.getString("PRN");
                    if (userId.equals(prn)) {
                        JOptionPane.showMessageDialog(null, "All The Best For Quiz!");
                        new Quiz1();
                        frame.setVisible(false);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    JOptionPane.showMessageDialog(null, "User is not Logged In");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (e.getSource() == menuItem4) {
            if (prn != null) {
                try (Connection conni = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3307/project", "root", "hritik7982")) {
                    PreparedStatement pat = conni.prepareStatement("DELETE FROM Login WHERE PRN=?");
                    pat.setString(1, prn);
                    int rows = pat.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(null, "Logout Successful!");
                        frame.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "No active session found.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No user is logged in.");
            }
        }
        if (e.getSource() == menuItem5) {
            new PassChange();
        }
        if (e.getSource() == menuItem6) {
            new FeedBack();
        }
    }

    public static void main(String[] args) {
        new HomePagee();
    }
}

