import java.util.Arrays;

// REQUIREMENT: Parent class with 2+ children
public class AbstractQuestion implements Scorable {
    protected String text;
    protected String [] options;
    protected int correctIndex;

    //REQUIREMENT: Overloaded constructor example
    public AbstractQuestion(String text, String[] options, int correctIndex) {
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return Arrays.copyOf(options, options.length);
    }

    public int getCorrectIndex(){
        return correctIndex;
    }

    // REQUIREMENT: method that throws custom exception
    public boolean checkAnswer(int choseIndex) throws InvalidAnswerException {
        if(choseIndex < 0  || choseIndex >= options.length) {
            throw new InvalidAnswerException("Invalid option selected");
        }
        return choseIndex == correctIndex;
    }

    @Override
    public int getScoreDelta(boolean correct) {
        return 0;
    }
}
