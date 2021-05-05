/**
 * An Exception thrown if an empty String is passed as the itemCode in the arg Item constructor.
 *
 */
public class EmptyItemCodeException extends Exception {
    /**
     * Used to create a EmptyItemCodeException with no message.
     */
    public EmptyItemCodeException() {
    }
    /**
     * Creates a EmptyItemCodeException with a specified message.
     * 
     * @param message The String to print when this Exception is thrown.
     */
    public EmptyItemCodeException(String message) {
        super(message);
    }
}
