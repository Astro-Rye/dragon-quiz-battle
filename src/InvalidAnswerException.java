
// REQUIREMENT: Custom exception
public class InvalidAnswerException extends Throwable {
    public InvalidAnswerException(String message) {
        super(message);
    }
}
