/**
 * An Exception thrown in HashedGrocery's addItemCatalog and addItem methods
 * when the user attempts to add an item to HashedGrocery with the same itemCode
 * as a preexisting Item.
 *
 */
public class KeyAlreadyExistsException extends Exception {
    /**
     * Creates a KeyAlreadyExistsException with no message.
     */
    public KeyAlreadyExistsException() {
    }

    /**
     * Creates a KeyAlreadyExistsException with a specified message.
     * 
     * @param message The String to print when this Exception is thrown.
     */
    public KeyAlreadyExistsException(String message) {
        super(message);
    }
}
