import java.io.*;
import java.util.*;

public class GroceryDriver {
    /**
     * Used to handle user input and feed responses back to the console.
     */
    public static void main(String[] args) {
        String itemCode, itemName;
        int qty, averageSales;
        double price;
        Scanner stdin = new Scanner(System.in);
        String input;
        HashedGrocery grocery = new HashedGrocery();
        System.out.println("Creating new HashedGrocery object...");
        System.out.println("\nBusiness Day " + grocery.getBusinessDay() + ".\n\nMenu:\n\n(L) Load item catalog    \r\n"
                + "(A) Add items              \r\n" + "(B) Process Sales      \r\n" + "(C) Display all items\r\n"
                + "(N) Move to next business day  \r\n" + "(Q) Quit ");
        while (true) {
            System.out.print("\nEnter an option: ");
            input = stdin.next().toUpperCase();
            stdin.nextLine();
            switch (input) {
            case "L":
                System.out.print("\nEnter file to load: ");
                input = stdin.nextLine();
                try {
                    grocery.addItemCatalog(input);

                } catch (FileNotFoundException e) {
                    System.out.println("\nUnable to add: No entered file name does not exist.");
                }
                break;
            case "A":
                while (true) {
                    try {
                        System.out.print("\nEnter item code: ");
                        itemCode = stdin.next();
                        stdin.nextLine();
                        if (itemCode.equals(""))
                            throw new EmptyItemCodeException();
                        break;
                    } catch (EmptyItemCodeException e) {
                        System.out.println("The item code cannot be empty.");
                    }
                }
                System.out.print("Enter item name: ");
                itemName = stdin.nextLine();
                while (true) {
                    try {
                        System.out.print("Enter Quantity in store: ");
                        qty = stdin.nextInt();
                        if (qty < 0)
                            throw new InputMismatchException();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a non negative integer value.");
                        stdin.nextLine();
                    }
                }
                while (true) {
                    try {
                        System.out.print("Enter Average sales per day: ");
                        averageSales = stdin.nextInt();
                        if (averageSales < 0)
                            throw new InputMismatchException();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a non negative integer value.");
                        stdin.nextLine();
                    }
                }
                while (true) {
                    try {
                        System.out.print("Enter price: ");
                        price = stdin.nextDouble();
                        if (price < 0)
                            throw new InputMismatchException();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a non negative number.");
                        stdin.nextLine();
                    }
                }
                try {
                    grocery.addItem(new Item(itemCode, itemName, averageSales, qty, price, 0));
                    System.out.print("\n" + itemCode + ": " + itemName + " added to inventory.\n");
                } catch (NumberFormatException e) {
                } catch (KeyAlreadyExistsException e) {
                    System.out.print("\n" + itemCode + ": Cannot add item as item code already exists.");
                } catch (EmptyItemCodeException e) {
                }
                break;
            case "B":
                System.out.print("\nEnter a filename: ");
                input = stdin.nextLine();
                try {
                    grocery.processSales(input);
                } catch (FileNotFoundException e) {
                    System.out.println("Unable to add catalog: No file with specified name exists");
                }
                break;
            case "C":
                System.out.println("\n" + grocery.toString());
                break;
            case "N":
                grocery.nextBusinessDay();
                break;
            case "Q":
                stdin.close();
                System.out.println("\nProgram terminating normally...");
                System.exit(0);
            default:
                System.out.println("\nPlease enter a valid option.");
            }
        }
    }
}
