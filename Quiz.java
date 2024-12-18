import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Quiz extends JFrame implements ActionListener {

    private JFrame loginFrame, instructionFrame, quizFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton[] options;
    private JButton loginButton, startTestButton, nextButton, bookmarkButton;
    private JLabel questionLabel;
    private JPanel bookmarkPanel; 
    private ButtonGroup optionsGroup;

    private int currentQuestion = 0;
    private int correctAnswers = 0;
    private int bookmarkIndex = 0;
    private int[] bookmarkedQuestions = new int[10];
    private JLabel[] bookmarkLabels = new JLabel[10]; 

    private static final String[][] QUESTIONS = {
            {"Which concept is NOT associated with Object-Oriented Programming?", "inheritance", "abstraction", "polymorphism", "sequential programming", "3"},
            {"What is the process of creating an object called in Java?", "instantiation", "inheritance", "polymorphism", "abstraction", "0"},
            {"Which keyword is used to inherit a class in Java?", "super class", "super", "inherit", "extends", "3"},
            {"What is the access level of a class member accessible from the same class?", "public", "default", "protected", "private", "3"},
            {"Which OOP principle allows an object to exhibit multiple forms?", "encapsulation", "polymorphism", "inheritance", "abstraction", "1"},
            {"How to read an entire file in one line using Java 8?", "Files.readAllLines()", "Files.read()", "Files.readFile()", "Files.lines()", "0"},
            {"What is the process of wrapping data and methods into a single unit?", "abstraction", "polymorphism", "inheritance", "encapsulation", "3"},
            {"Which method is automatically called when an object is created?", "constructor()", "final()", "main()", "toString()", "0"},
            {"Which keyword is used to prevent a class from being inherited?", "final", "abstract", "private", "static", "0"},
            {"Which method is used to override a superclass method?", "overload", "inherit", "extend", "override", "3"}
    };

    public Quiz(String title) {
        initializeLoginFrame(title);
    }

    private void initializeLoginFrame(String title) {
        loginFrame = new JFrame(title);

        JLabel loginLabel = new JLabel("LOG IN");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");

        loginFrame.setSize(600, 350);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(null);
        loginFrame.setLocation(250, 100);

        loginLabel.setFont(new Font("Times New Roman", Font.BOLD, 28));
        loginLabel.setBounds(250, 50, 300, 30);

        usernameLabel.setBounds(150, 100, 100, 20);
        passwordLabel.setBounds(150, 140, 100, 20);
        usernameField.setBounds(250, 100, 200, 20);
        passwordField.setBounds(250, 140, 200, 20);

        // Here we set the button colors & size of login button and login frame
        loginButton.setBounds(250, 180, 100, 30);
        loginButton.setBackground(Color.ORANGE);
        loginButton.addActionListener(this);

        loginFrame.add(loginLabel);
        loginFrame.add(usernameLabel);
        loginFrame.add(passwordLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.setVisible(true);
    }

    private void initializeInstructionFrame() {
        instructionFrame = new JFrame("Instructions");

        JTextArea instructions = new JTextArea("\n\tInstructions:\n\t> This is a free online test. DO NOT pay.\n\t> Total number of questions: 10\n\t> Time Allowed: 30 minutes\n\t> Each question carries 1 mark.\n\t> At least 50% marks to pass the test.\n\t> Best of luck!");
        instructions.setEditable(false);
        instructions.setOpaque(false);

        // Here we set the button colors & size of start test button
        startTestButton = new JButton("Start Test");
        startTestButton.setBounds(250, 240, 100, 30);
        startTestButton.setBackground(Color.ORANGE);
        startTestButton.addActionListener(this);

        instructionFrame.setSize(600, 350);
        instructionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instructionFrame.setLayout(null);
        instructionFrame.setLocation(250, 100);

        instructions.setBounds(50, 50, 500, 150);
        instructionFrame.add(instructions);
        instructionFrame.add(startTestButton);
    }

    private void initializeQuizFrame() {
        quizFrame = new JFrame("Quiz");
        questionLabel = new JLabel();
        options = new JRadioButton[4];
        optionsGroup = new ButtonGroup();
        nextButton = new JButton("Next");
        bookmarkButton = new JButton("Bookmark");
        bookmarkPanel = new JPanel();
        bookmarkPanel.setLayout(new BoxLayout(bookmarkPanel, BoxLayout.Y_AXIS));
        bookmarkPanel.setBounds(450, 30, 120, 250);
        bookmarkPanel.setBackground(Color.WHITE);

        for (int i = 0; i < options.length; i++) {
            options[i] = new JRadioButton();
            options[i].setBounds(50, 80 + (i * 30), 400, 20);
            optionsGroup.add(options[i]);
            quizFrame.add(options[i]);
        }

        // Here we set the button colors & size of next, bookmark and quiz frame
        nextButton.setBounds(100, 240, 100, 30);
        nextButton.setBackground(Color.ORANGE);
        nextButton.addActionListener(this);

        bookmarkButton.setBounds(270, 240, 100, 30);
        bookmarkButton.setBackground(Color.ORANGE);
        bookmarkButton.addActionListener(this);

        questionLabel.setBounds(30, 40, 400, 20);

        quizFrame.setSize(600, 350);
        quizFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        quizFrame.setLayout(null);
        quizFrame.setLocation(250, 100);
        quizFrame.add(questionLabel);
        quizFrame.add(nextButton);
        quizFrame.add(bookmarkButton);
        quizFrame.add(bookmarkPanel);
    }

    private void loadQuestion(int index) {
        String[] question = QUESTIONS[index];
        questionLabel.setText((index + 1) + ". " + question[0]);
        for (int i = 0; i < options.length; i++) {
            options[i].setText(question[i + 1]);
            options[i].setSelected(false);
        }
    }

    private boolean isAnswerCorrect() {
        String correctAnswerIndex = QUESTIONS[currentQuestion][5];
        return options[Integer.parseInt(correctAnswerIndex)].isSelected();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            // TODO: Enhance login validation (e.g., check against a database)
            if (username.equals("admin") && password.equals("password")) {
                loginFrame.setVisible(false);
                initializeInstructionFrame();
                instructionFrame.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == startTestButton) {
            instructionFrame.setVisible(false);
            initializeQuizFrame();
            loadQuestion(currentQuestion);
            quizFrame.setVisible(true);
        }

        if (e.getSource() == nextButton) {
            if (isAnswerCorrect()) correctAnswers++;
            currentQuestion++;
            if (currentQuestion < QUESTIONS.length) {
                loadQuestion(currentQuestion);
            } else {
                showResults();
            }
        }

        if (e.getSource() == bookmarkButton) {
            bookmarkedQuestions[bookmarkIndex] = currentQuestion;

            JLabel bookmarkLabel = new JLabel("Bookmark " + (bookmarkIndex + 1));
            bookmarkLabel.setOpaque(true);
            bookmarkLabel.setBackground(Color.ORANGE);
            bookmarkLabel.setForeground(Color.BLACK);
            bookmarkLabel.setHorizontalAlignment(SwingConstants.CENTER);
            bookmarkLabel.setPreferredSize(new Dimension(100, 30));
            int questionToGo = currentQuestion;
            bookmarkLabel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    currentQuestion = questionToGo;
                    loadQuestion(currentQuestion);
                }
            });
            bookmarkPanel.add(bookmarkLabel);
            bookmarkPanel.revalidate();
            bookmarkPanel.repaint();

            bookmarkIndex++;
            currentQuestion++;
            if (currentQuestion < QUESTIONS.length) {
                loadQuestion(currentQuestion);
            } else {
                bookmarkButton.setText("Revisit Bookmarked");
            }
        }
    }

    // Results are showing here
    private void showResults() {
        int percentage = (correctAnswers * 100) / QUESTIONS.length;
        String resultMessage = "Correct answers: " + correctAnswers + "\n" +
                "Wrong answers: " + (QUESTIONS.length - correctAnswers) + "\n" +
                "Percentage: " + percentage + "%\n" +
                (percentage >= 50 ? "Pass" : "Fail");
        JOptionPane.showMessageDialog(this, resultMessage);
        System.exit(0);
    }

    public static void main(String[] args) {
        new Quiz("Online Quiz App");
    }
}
