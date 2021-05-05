import java.util.Enumeration;
import java.util.Hashtable;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;

public class HashedGrocery {
    private JSONParser parser = new JSONParser();
    private Hashtable<String, Item> hashTable = new Hashtable<String, Item>(50);
    private int businessDay = 1;

    /**
     * Used to create a HashedGrocery Object with no parameters.
     */
    public HashedGrocery() {
    }

    /**
     * Used to access the businessDay of the HashedGrocery outside of this class.
     * 
     * @return The businessDay.
     */
    public int getBusinessDay() {
        return businessDay;
    }

    /**
     * Used to add an item to the HashedGrocery.
     * 
     * @param item The item to add.
     * @throws KeyAlreadyExistsException Thrown if an Item already exists in the
     *                                   HashedGrocery with the same itemCode as the
     *                                   item currently being added.
     */
    public void addItem(Item item) throws KeyAlreadyExistsException {
        if (hashTable.containsKey(item.getItemCode()))
            throw new KeyAlreadyExistsException();
        hashTable.put(item.getItemCode(), item);
    }

    /**
     * Used to change an Item contained in the HashedGrocery's qtyInStore.
     * 
     * @param item        The Item whose qtyInStore should be changed.
     * @param adjustByQty The number to change the qtyInStore by.
     * @throws NotEnoughStockException Thrown if changing the specified Item's
     *                                 qtyInStore causes it to become negative.
     */
    public void updateItem(Item item, int adjustByQty) throws NotEnoughStockException {
        if (hashTable.get(item.getItemCode()).getQtyInStore() < -1 * adjustByQty)
            throw new NotEnoughStockException();
        hashTable.get(item.getItemCode())
                .setQtyInStore(hashTable.get(item.getItemCode()).getQtyInStore() + adjustByQty);
    }

    /**
     * Used to read in an array of Items contained in an external JSON file and add
     * them to the HashedGrocery.
     * 
     * @param filename The name of the JSON text file to derive the Items from.
     * @throws FileNotFoundException Thrown if a file cannot be found with the
     *                               specified filename.
     */
    public void addItemCatalog(String filename) throws FileNotFoundException {
        JSONObject obj;
        String itemCode, name;
        Item newItem;
        double price;
        int avgSales, qtyInStore, amtOnOrder;
        File fileToRead = new File(filename);
        if (!fileToRead.exists())
            throw new FileNotFoundException();
        try {
            FileInputStream fis = new FileInputStream(fileToRead);
            InputStreamReader isr = new InputStreamReader(fis);
            JSONArray objs = (JSONArray) parser.parse(isr);
            System.out.println();
            for (int i = 0; i < objs.size(); i++) {
                obj = (JSONObject) objs.get(i);
                itemCode = (String) obj.get("itemCode");
                if (hashTable.containsKey(itemCode)) {
                    System.out.println(itemCode + ": Cannot add item as item code already exists.\n");
                    continue;
                }
                name = (String) obj.get("itemName");
                try {
                    avgSales = Integer.parseInt((String) obj.get("avgSales"));
                    if (avgSales < 0)
                        throw new NumberFormatException();
                    qtyInStore = Integer.parseInt((String) obj.get("qtyInStore"));
                    if (qtyInStore < 0)
                        throw new NumberFormatException();
                    price = Double.parseDouble((String) obj.get("price"));
                    if (price < 0)
                        throw new NumberFormatException();
                    amtOnOrder = Integer.parseInt((String) obj.get("amtOnOrder"));
                    if (amtOnOrder < 0)
                        throw new NumberFormatException();
                    newItem = new Item(itemCode, name, avgSales, qtyInStore, price, amtOnOrder);
                    hashTable.put(newItem.getItemCode(), newItem);
                    System.out.println(itemCode + ": " + name + " is added to inventory.");
                } catch (EmptyItemCodeException e) {
                    System.out.println(name + " cannot be added to the inventory due to an empty item code.");
                } catch (NumberFormatException e) {
                    System.out.println(itemCode + ": Not added to inventory due to negative or invalid values.");
                }
            }
        } catch (IOException e) {
        } catch (ParseException e) {
        }
    }

    /**
     * Used to change the qtyInStore of the Items in the HashedGrocery based on the
     * information contained in an external JSON text file. Orders more of specific
     * Items if their qtyInStock falls below a threshold determined by its
     * averageSalesPerDay.
     * 
     * @param filename The name of the JSON text file.
     * @throws FileNotFoundException Thrown if a file with the specified filename
     *                               cannot be found.
     */
    public void processSales(String filename) throws FileNotFoundException {
        Item temp;
        JSONObject obj;
        String itemCode;
        int qtySold;
        File fileToRead = new File(filename);
        if (!fileToRead.exists())
            throw new FileNotFoundException();
        try {
            FileInputStream fis = new FileInputStream(fileToRead);
            InputStreamReader isr = new InputStreamReader(fis);
            JSONArray objs = (JSONArray) parser.parse(isr);
            for (int i = 0; i < objs.size(); i++) {
                obj = (JSONObject) objs.get(i);
                itemCode = (String) obj.get("itemCode");
                qtySold = Integer.parseInt((String) obj.get("qtySold"));
                if (!hashTable.containsKey(itemCode))
                    System.out.print("\n" + itemCode + ": Cannot buy as it is not an item in the grocery store.");
                else {
                    try {
                        temp = hashTable.get(itemCode);
                        updateItem(temp, -1 * (qtySold));
                        System.out.print("\n" + itemCode + ": " + qtySold + " units of " + temp.getName() + " are ");
                        if (qtySold >= 0)
                            System.out.print("sold. ");
                        else
                            System.out.print("returned. ");
                        if (temp.getQtyInStore() < 3 * temp.getAverageSalesPerDay() && temp.getArrivalDay() == 0) {
                            temp.setArrivalDay(businessDay + 3);
                            temp.setOnOrder(2 * temp.getAverageSalesPerDay());
                            System.out.print("Order has been places for " + temp.getOnOrder() + " more units.");
                        }
                    } catch (NotEnoughStockException e) {
                        System.out.print("\n" + itemCode + ": Not enough stock for sale. Not updated.");
                    }
                }
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("io exception");
        } catch (ParseException e) {
            System.out.println("parse exception");
        }
    }

    /**
     * Increments the HashedGrocery's businessDay by 1 and adds and deliveries to
     * their Item's qtyInStore.
     */
    public void nextBusinessDay() {
        Item temp;
        String itemsArrived = "";
        businessDay++;
        System.out.println("\nAdvancing business day...\nBusiness Day " + businessDay + ".");
        for (Enumeration<Item> items = hashTable.elements(); items.hasMoreElements();) {
            temp = items.nextElement();
            if (temp.getArrivalDay() == businessDay) {
                try {
                    updateItem(temp, temp.getOnOrder());
                } catch (NotEnoughStockException e) {
                }
                itemsArrived += "\n" + temp.getItemCode() + ": " + temp.getOnOrder() + " units of " + temp.getName();
                temp.setOnOrder(0);
                temp.setArrivalDay(0);
            }
        }
        if (itemsArrived.equals(""))
            System.out.println("\nNo orders have arrived.");
        else
            System.out.println("\nOrder have arrived for:\n" + itemsArrived);
    }

    /**
     * Used to create a String representation of the data fields of all Items
     * contained in the HashedGrocery.
     * 
     * @return A String containing the information of the HashedGrocery's Items.
     */
    public String toString() {
        String chart = "Item code   Name                Qty   AvgSales   Price    OnOrder    ArrOnBusDay\r\n"
                + "--------------------------------------------------------------------------------";
        for (Enumeration<Item> items = hashTable.elements(); items.hasMoreElements();)
            chart += "\n" + items.nextElement().toString();
        return chart;

    }
}
