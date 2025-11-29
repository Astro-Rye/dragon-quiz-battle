// child #1 of AbstractQuestion
public class StandardQuestion extends AbstractQuestion {

    private int difficulty; // 1 = easy, 2 = normal, 3 = hard

    //REQUIREMEENT: overloaded constructor #1
    public StandardQuestion(String text, String[] options, int correctIndex) {
        this(text, options, correctIndex, 1);
    }

    //REQUIREMENT: Overloaded constructor #2 (same name, extra parameter0
    public StandardQuestion(String text, String[] options, int correctIndex, int difficulty) {
        super(text, options, correctIndex);
        this.difficulty = difficulty;
    }

    // REQUIREMENT: overidden method
    @Override
    public int getScoreDelta(boolean correct) {
        int base = switch (difficulty) {
            case 3 -> 40;
            case 2 -> 25;
            default -> 15;
        };
        return correct ? base : -base / 3;
    }
}
