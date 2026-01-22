import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Quiz1 implements ActionListener {

    // keep this if other classes rely on it
    static int count = 0;

    JFrame frame;
    JLabel title, questionLabel;
    JRadioButton option1, option2, option3, option4;
    ButtonGroup bg;
    JButton nextButton;

    // QUESTIONS: add more here to expand the quiz
    String[] questions = {
        "1) Which is the container that doesn't contain title bar and Menu bar but can have other components like button, textfield etc?",
        "2) Which layout manager places components left-to-right and wraps when out of horizontal space?",
        "3) Which method is used to make a JFrame visible?",
        "4) Which interface must a class implement to handle button click events?",
        "5) Which Swing component is best for multi-line editable text?"
    };

    String[][] options = {
        {"a. Window", "b. Frame", "c. Panel", "d. Container"},
        {"a. BorderLayout", "b. GridLayout", "c. BoxLayout", "d. FlowLayout"},
        {"a. setVisible(true)", "b. show()", "c. display()", "d. setVisible()"},
        {"a. MouseListener", "b. ActionListener", "c. FocusListener", "d. KeyListener"},
        {"a. JTextField", "b. JLabel", "c. JTextArea", "d. JButton"}
    };

    // answer indices (0..3) corresponding to options[][] rows
    int[] answers = {2, 3, 0, 1, 2};

    int currentQuestion = 0;
    int score = 0;

    public Quiz1() {
        createWindow();
        setLocationAndSize();
        addComponentsToFrame();
        addActionEvent();
        updateQuestion(); // show first question
    }

    public void createWindow() {
        frame = new JFrame();
        frame.setTitle("Quiz");
        frame.setSize(1150, 700);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.white);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(true);
    }

    public void setLocationAndSize() {
        title = new JLabel("Quiz");
        questionLabel = new JLabel();
        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();
        bg = new ButtonGroup();
        nextButton = new JButton("Next");

        title.setBounds(350, 10, 608, 48);
        title.setFont(new Font("Cambria", Font.PLAIN, 25));

        questionLabel.setBounds(50, 60, 1100, 48);
        questionLabel.setFont(new Font("Cambria", Font.PLAIN, 18));

        option1.setBounds(50, 130, 600, 40);
        option1.setBackground(Color.white);
        option1.setFont(new Font("Cambria", Font.PLAIN, 18));

        option2.setBounds(50, 180, 600, 40);
        option2.setBackground(Color.white);
        option2.setFont(new Font("Cambria", Font.PLAIN, 18));

        option3.setBounds(50, 230, 600, 40);
        option3.setBackground(Color.white);
        option3.setFont(new Font("Cambria", Font.PLAIN, 18));

        option4.setBounds(50, 280, 600, 40);
        option4.setBackground(Color.white);
        option4.setFont(new Font("Cambria", Font.PLAIN, 18));

        nextButton.setBounds(50, 350, 288, 40);
        nextButton.setFont(new Font("Cambria", Font.PLAIN, 18));

        // group radio buttons to make choices exclusive
        bg.add(option1);
        bg.add(option2);
        bg.add(option3);
        bg.add(option4);
    }

    public void addComponentsToFrame() {
        frame.add(title);
        frame.add(questionLabel);
        frame.add(option1);
        frame.add(option2);
        frame.add(option3);
        frame.add(option4);
        frame.add(nextButton);
    }

    public void addActionEvent() {
        nextButton.addActionListener(this);
    }

    private void updateQuestion() {
        // set text for current question and options
        questionLabel.setText(questions[currentQuestion]);
        option1.setText(options[currentQuestion][0]);
        option2.setText(options[currentQuestion][1]);
        option3.setText(options[currentQuestion][2]);
        option4.setText(options[currentQuestion][3]);

        // clear previous selection
        bg.clearSelection();

        // change button text on last question
        if (currentQuestion == questions.length - 1) {
            nextButton.setText("Submit");
        } else {
            nextButton.setText("Next");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            // determine selected answer
            int selected = -1;
            if (option1.isSelected()) selected = 0;
            else if (option2.isSelected()) selected = 1;
            else if (option3.isSelected()) selected = 2;
            else if (option4.isSelected()) selected = 3;

            if (selected == -1) {
                // no selection
                JOptionPane.showMessageDialog(frame, "Please select an answer before proceeding.");
                return;
            }

            // check correctness
            if (selected == answers[currentQuestion]) {
                score++;
                JOptionPane.showMessageDialog(frame, "Correct Answer!");
            } else {
                JOptionPane.showMessageDialog(frame, "Wrong Answer!");
            }

            // move to next question or finish
            currentQuestion++;
            if (currentQuestion < questions.length) {
                updateQuestion();
            } else {
                // Quiz finished
                JOptionPane.showMessageDialog(frame,
                        "Quiz finished!\nYour score: " + score + " / " + questions.length);

                // keep compatibility with your original code's 'count'
                count = score;

                // If you have a FeedBack class as in your original code, call it:
                try {
                    FeedBack fd = new FeedBack(); // keep same behavior
                } catch (Exception ex) {
                    // If FeedBack class not present or constructor different, ignore safely
                    System.out.println("FeedBack class not found or failed to construct: " + ex.getMessage());
                }

                frame.dispose();
            }
        }
    }

    public static void main(String[] args) {
        // run on EDT
        SwingUtilities.invokeLater(() -> new Quiz1());
    }
}
