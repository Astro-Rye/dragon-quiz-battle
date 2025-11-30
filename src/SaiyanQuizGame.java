import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
//
// REQUIREMENT: LayoutManager with JFramee ( BorderLayout, plus inner layouts)
public class SaiyanQuizGame extends JFrame{


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
        corectIcon = new ImageIcon("resources/correct.png"); // update paths for your project
        wrongIcon = new ImageIcon("resources/wrong.png");

        buildUI();
        wireEvents();

        loadFirstQuestion();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }
    private void buildUI() {
        // Root Layout
        JPanel root = new JPanel(new BorderLayout(8,8));
        root.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ==== NORTHL Title + player ====
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        root.add(titleLabel, BorderLayout.NORTH);

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
        rightPanel.setBorder(
                BorderFactory.createTitledBorder("Battle Feedback"));
         imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
         rightPanel.add(imageLabel, BorderLayout.CENTER);
            logScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            rightPanel.add(logScroll, BorderLayout.SOUTH);
         center.add(rightPanel);
         root.add(center, BorderLayout.CENTER);
         // == SOUTH: player info ===
        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        playerPanel.add(new JLabel ("Name: "));
        playerPanel.add(nameField);
        playerPanel.add(powerLabel);
        root.add(playerPanel, BorderLayout.SOUTH);

         // Initial states
        nextButton.setEnabled(false);
    }

    private void wireEvents() {
        submitButton.addActionListener(this::onSubmit);
        nextButton.addActionListener(this::onNext);
        highScoreButton.addActionListener(this::onViewHighScores);

    }

private void loadFirstQuestion() {
        currentQuestion = questionBank.getCurrentQuestion();
        showQuestion();
}
private void showQuestion() {
        if(currentQuestion == null){
            endGame();
            return;
        }

int index = questionBank.getCurrentIndex();
questionNumberLabel.setText("Question " + (index + 1) + " / " + questionBank.getTotalQuestions());
questionTextLabel.setText(currentQuestion.getText());

String [] opts = currentQuestion.getOptions();
for(int i = 0; i < optionButtons.length; i++) {
    optionButtons[i].setText(opts[i]);
}

optionsGroup.clearSelection();
imageLabel.setIcon(null);
log("New question loaded.");
nextButton.setEnabled(false);
submitButton.setEnabled(true);
}

private int getSelectedOptionIndex() {
        for(int i = 0; i < optionButtons.length; i++) {
            if(optionButtons[i].isSelected()) return i;
        }
        return -1;
}

private void onSubmit(ActionEvent e ) {
        if(nameField.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Please enter your name first.");
            return;
        }
        playerName = nameField.getText().trim();

        int chosen = getSelectedOptionIndex();
        try {
            boolean correct = currentQuestion.checkAnswer(chosen); // may throw InvalidAnswerException

            int delta = currentQuestion.getScoreDelta(correct);
            score+= delta;
            powerLabel.setText("Power: " + score);
             if(correct) {
                 imageLabel.setIcon(corectIcon);
                 log(playerName + " answered correctly! Power +" + delta);
             } else {
                 imageLabel.setIcon(wrongIcon);
                 log(playerName + "missed the question. Power " + delta);
             }

             submitButton.setEnabled(false);
             nextButton.setEnabled(true);
        } catch (InvalidAnswerException ex) {
            // REQUIREMENT: custom exception is caught and handled
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Invalid Answer", JOptionPane.WARNING_MESSAGE);

        }
}

private void onNext(ActionEvent e) {
        currentQuestion = questionBank.nextQuestion();
        if(currentQuestion == null) {
            endGame();
        } else {
            showQuestion();
        }
}

private void onViewHighScores(ActionEvent e) {
        List<String> scores = HighScoreManager.loadScores();
        if(scores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No high scores yet.");
            return;
        }
        StringBuilder sb = new StringBuilder("Highscores:\n");
        for(String s : scores) {
            sb.append(". ").append(s).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
}

private void endGame() {
        log("Game over! Final power level " + score);
        HighScoreManager.saveScore(playerName, score);

        int playAgain = JOptionPane.showConfirmDialog(
                this,
                "Game over, " + playerName + "!\nFinal power level: " + score +
                        "\nPlay again?",
                "Play again?",
                JOptionPane.YES_NO_OPTION);

        if(playAgain == JOptionPane.YES_NO_OPTION) {
            // reset game state
            score = 0;
            powerLabel.setText("Power: 0");
        }
        JOptionPane.showMessageDialog(this,
                "Game over, " + playerName + "!\nYour Final power level: " + score +
                                    "\nScore saved to highscores.txt");

        submitButton.setEnabled(false);
        nextButton.setEnabled(false);
}

private void log(String text) {
        logArea.append(text + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SaiyanQuizGame().setVisible(true));
    }
}
