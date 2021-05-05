
public class Item {
    private String itemCode, name;
    private int qtyInStore, averageSalesPerDay, onOrder = 0, arrivalDay = 0;
    private double price;

    /**
     * Used to create an Item Object without any parameters.
     */
    public Item() {
    }

    /**
     * Used to create an Item Object with specified parameters.
     * 
     * @param itemCode           The item code to set the Item's itemCode to.
     * @param name               The name to set the Item's name to.
     * @param averageSalesPerDay The number to set the Item's averageSalesPerDay to.
     * @param qtyInStore         The number to set the Item's qtyInStore to.
     * @param price              The price to set the Item's price to.
     * @param onOrder            The number to set the Item's onOrder to.
     * @throws EmptyItemCodeException Thrown when the user attempts to create an
     *                                Item with an empty itemCode.
     */
    public Item(String itemCode, String name, int averageSalesPerDay, int qtyInStore, double price, int onOrder)
            throws EmptyItemCodeException {
        if (itemCode.equals(""))
            throw new EmptyItemCodeException();
        this.itemCode = itemCode;
        this.name = name;
        this.averageSalesPerDay = averageSalesPerDay;
        this.qtyInStore = qtyInStore;
        this.price = price;
        this.onOrder = onOrder;
    }

    /**
     * Used to access the Item's itemCode outside of this class.
     * 
     * @return The itemCode.
     */
    public String getItemCode() {
        return itemCode;
    }

    /**
     * Used to access the Item's name outside of this class.
     * 
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Used to access the Item's averageSalesPerDay outside of this class.
     * 
     * @return The averageSalesPerDay.
     */
    public int getAverageSalesPerDay() {
        return averageSalesPerDay;
    }

    /**
     * Used to access the number of units of the Item that are currently being
     * shipped outside of this class.
     * 
     * @return The onOrder.
     */
    public int getOnOrder() {
        return onOrder;
    }

    /**
     * Used to set the number of units of the Item that are currently being shipped
     * outside of this class.
     * 
     * @param onOrder The number to set onOrder to.
     */
    public void setOnOrder(int onOrder) {
        this.onOrder = onOrder;
    }

    /**
     * Used to access the day the shipment containing this Item will be delivered
     * outside of this class.
     * 
     * @return The arrivalDay.
     */
    public int getArrivalDay() {
        return arrivalDay;
    }

    /**
     * Used to set the day the shipment containing this Item will be delivered
     * outside of this class.
     * 
     * @param arrivalDay The number to set ArrivalDay to.
     */
    public void setArrivalDay(int arrivalDay) {
        this.arrivalDay = arrivalDay;
    }

    /**
     * Used to access the quantity of this Item in the store outside of this class.
     * 
     * @return The qtyInStore.
     */
    public int getQtyInStore() {
        return qtyInStore;
    }

    /**
     * Used to set the quantity of this Item in the store outside of this class.
     * 
     * @param qty The number to set the qtyInStore to.
     */
    public void setQtyInStore(int qty) {
        qtyInStore = qty;
    }

    /**
     * Used to create a String representation of the data fields contained in the
     * Item Object.
     * 
     * @return A String containing the Item's information.
     */
    public String toString() {
        if(name.length()>=20)
            return String.format("%-12s%-20s%3s%11s%8s%11s%15s", itemCode, name.substring(0,16)+"...", qtyInStore, averageSalesPerDay, price,
                    onOrder, arrivalDay);
        return String.format("%-12s%-20s%3s%11s%8s%11s%15s", itemCode, name, qtyInStore, averageSalesPerDay, price,
                onOrder, arrivalDay);
    }

}
