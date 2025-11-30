import java.util.LinkedList;

public class QuizQuestionBank {

    // REQUIREMENT: Uses LinkedList as data structure

        private final LinkedList<AbstractQuestion> questions = new LinkedList<>();
        private int index = 0;

        public QuizQuestionBank() {
            seedQuestions();
        }

        private void seedQuestions() {
            // NOTE: Make these dragon ball ish but generic enough
            questions.add(new StandardQuestion(
                    "Who is known as the Prince of all Saiyans?",
                    new String[]{"Goku","Vegeta", "Piccolo", "Krillin" },
                    1,2));

            questions.add(new StandardQuestion(
                    "Which form has golden hair and blue eyes?",
                    new String[]{"Base form", "Super Saiyan","Great Ape", "Fusion"},
                    1,1));

            questions.add(new BonusQuestion(
                    "Which attack is shouted with 'Kamehameha!' ? ",
                    new String[] {"Spirit Bomb", "Destructo Disk", "Kamehameha", "Galick Gun"},
                    2));

            // Add more questions if wanted
            questions.add(new BonusQuestion(
                    "Which Saiyan loves fighting more than anything' ? ",
                    new String[] {"Goku", "Gohank", "Yamcha", "Tien"},
                    2));
            questions.add(new StandardQuestion(
                    "Which character is known for training in 100x gravity' ? ",
                    new String[] {"Vegeta", "Trunks", "Krillin", "Bulma"},
                    3));
            questions.add(new BonusQuestion(
                    "Who defeated majin buu during the cell games' ? ",
                    new String[] {"Goku", "Gohan", "Vegeto", "Gotanks"},
                    4));
        }
        public AbstractQuestion getCurrentQuestion() {
            if(questions.isEmpty() || index >= questions.size()) return null;
            return questions.get(index);
        }

        public AbstractQuestion nextQuestion() {
            index++;
            if(index >= questions.size()) return null;
            return questions.get(index);
        }

        public int getTotalQuestions() {
            return questions.size();
        }

        public int getCurrentIndex() {
            return index;
        }

        public void reset() {
            index = 0;
        }
}
