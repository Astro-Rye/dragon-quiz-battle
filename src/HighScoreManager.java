import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HighScoreManager {
    // REQUIRERMENT: Read/Write data to file
    private static final String FILE_NAME = "highscores.txt";

    public static void saveScore(String playerName, int score) {
        try(PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            out.println(playerName + " , " + score );
        } catch (IOException e ) {
            System.out.println("Could not save high score: " + e.getMessage());
        }
    }
    public static List<String> loadScores() {
        List<String> scores = new ArrayList<>();
        File file = new File(FILE_NAME);
        if(!file.exists()) return scores;

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null) {
                scores.add(line);
            }
        } catch (IOException e) {
            System.err.println("Could not read high scores: " + e.getMessage());
        }
        return scores;
    }
}
