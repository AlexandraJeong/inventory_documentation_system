/**
 * An Exception thrown in the updateIem method of the HashedGrocery class when
 * changing an Item's qtyInStore results in it becoming negative.
 *
 */
public class NotEnoughStockException extends Exception {
    /**
     * Creates a NotEnoughStockException with no message.
     */
    public NotEnoughStockException() {
    }

    /**
     * Creates a NotEnoughStockException with a specified message.
     * 
     * @param message The String to print when this Exception is thrown.
     */
    public NotEnoughStockException(String message) {
        super(message);
    }
}
