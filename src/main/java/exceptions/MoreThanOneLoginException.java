package exceptions;

/**
 * @author Максим Зеленский
 * @since 11.03.2020
 */
public class MoreThanOneLoginException extends Exception {

    public MoreThanOneLoginException() {
        super();
    }

    public MoreThanOneLoginException(String message) {
        super(message);
    }
}
