// Child #2 of AbstractQuestion
public class BonusQuestion {
    public BonusQuestion(String text, String [] options, int corrrectIndex) {
        super(text, options, correctIndex);
    }
   // REQUIREMENT; Overridden method (different
    @Override
    public int getScoreDelta(boolean correct) {
        return correct ? 60 :-10;
    }
}
