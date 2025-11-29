// Child #1 of AbstractQuestion
public class BonusQuestion extends AbstractQuestion {
    public BonusQuestion(String text, String[] options, int correctIndex) {
        super(text, options, correctIndex);
    }

    // REQUIREMENT: Overriden method ( different scoring )
    @Override
    public int getScoreDelta(boolean correct) {
        return correct ? 60 : -10;
    }
}
