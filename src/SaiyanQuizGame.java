import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SaiyanQuizGame {
    // REQUIREMENT: LayoutManager with JFramee ( BorderLayout, plus inner layouts)

    // Model
    private final QuizQuestionBank questionBank = new QuizQuestionBank();
    private AbstractQuestion currentQuestion;
    private int score = 0;

    // Player
    private String playerName = "Player";

    // UI Components
    private final JLabel titleLabel = new JLabel("SAIYAN QUIZ BATTLE ⚡ ", SwingConstants.CENTER);
    private final JTextField nameField = new JTextField(12);
    private final JLabel powerLabel = new JLabel("Power: 0");
    private final JLabel questionNumberLabel = new JLabel("Question 1 / " + questionBank.getTotalQuestions());

    private final JLabel questionTextLabel = new JLabel("Question will appear here");
    private final JRadioButton[] optionButtons = new JRadioButton[4];
    private final ButtonGroup optionsGroup = new ButtonGroup();

    private final JButton submitButton = new JButton("Submit Answer");
    private final JButton nextButton = new JButton("Next Question");
    private final JButton highScoreButton = new JButton("View High Scores");

    private final JTextArea logArea = new JTextArea(6,40);
    private final JScrollPane logScroll = new JScrollPane(logArea);

    // REQUIREMENT: Swing element - image use
    private final JLabel imageLabel = new JLabel(); // will show correct/wrong image
    private final ImageIcon corectIcon;
    private final ImageIcon wrongIcon;

    public SaiyanQuizGame() {
        super("Saiyan Quiz Battle");
        // Load images ( put files in / resources or project root)
        // Requirement: Image usage
        corectIcon = new ImageIcon("resoources/correct.png"); // update paths for your project
        wrongIcon = new ImageIcon("resources/wrong.png");

        buildUI();
        wireEvents();

        loadFirstQuestiono();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
    private void buildUI() {
        // Root Layout
        JPanel root = new JPanel(new BorderLayout(8,8));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ==== NORTHL Title + player ====
        title.Label.setFont(new Font("SansSerif", Font.BOLD, 20));
        root.add(titleLabel, BorderLayout.NORTH);

        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        playerPanel.add(new JLabel ("Name: "));
        playerPanel.add(nameField);
        playerPanel.add(powerLabel);
        root.add(playerPanel, BorderLayout.SOUTH); // WE'LL move log later; for now this is simple

        // ===== CENTER: main content split into left question + right image ===
        JPanel center = new JPanel(new GridLayout(1,2,8,8));

        // LEFT side: question and option
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.setBorder(BorderFactory.createTitledBorder("Question"));

        questionPanel.add(questionNumberLabel);
        questionTextLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        questionPanel.add(Box.createVerticalStrut(5));
        questionPanel.add(questionTextLabel);
        questionPanel.add(Box.createVerticalStrut(10));

        for(int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JRadioButton("Option" + (i + 1));
            optionsGroup.add(optionButtons[i]);
            questionPanel.add(optionButtons[i]);
        }

        JPanel buttonsRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonsRow.add(submitButton);
        buttonsRow.add(nextButton);
        buttonsRow.add(highScoreButton);
        questionPanel.add(Box.createVerticalStrut(10));
        questionPanel.add(buttonsRow);

        center.add(questionPanel);

        // RIGHT side: feedback image + log
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout(5,5));
        rightPanel.setBorder(BorderFactory.createTitleBorder("Battle Feedback"));
         imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
         rightPanel.add(imageLabel, BorderLayout.CENTER);

         center.add(rightPanel);
         root.add(center, BorderLayout.CENTER);

         setContentPane(root);

         // Initial states
        nextButton.setEnabled(false);
    }

    /
}
