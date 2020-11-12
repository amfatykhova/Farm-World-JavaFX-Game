package sample;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private Map<Item, Integer> items;
    private int capacity;
    private int size;

    public Inventory(List<Item> startItems, FarmWorldConfigurations.Difficulty difficulty) {
        this.capacity = (int) (100 * difficulty.getMultiplier());
        this.items = new HashMap<Item, Integer>(this.capacity);
        for (Item s : startItems) {
            int quantity = (int) (s.getQuantity() * difficulty.getMultiplier());
            this.items.put(s, quantity);
            this.size += quantity;
        }
    }

    public void remove(Item item, int number) throws InsufficientItemsException {
        int existingQuantity = this.items.get(item);
        if (existingQuantity < number) {
            throw new InsufficientItemsException("Cannot remove " + number + " " + item.name()
                    + "'s since there are only " + existingQuantity
                    + " in the player's inventory");
        }
        System.out.println("New quantity of " + item.name() + " = " + (existingQuantity - number));
        this.items.put(item, existingQuantity - number); // Update the quantity of that item
        this.size -= number;
    }

    public void add(Item item, int number) throws InventoryCapacityException {
        if (this.size + number > this.capacity) {
            throw new InventoryCapacityException("The player's inventory (size = "
                    + this.size + ") does not have enough capacity (capacity = "
                    + this.capacity + ") to add " + number + " " + item.name() + "'s ");
        }
        System.out.println("New quantity of " + item.name() + " = "
                + (this.items.get(item) + number));
        this.items.put(item, this.items.get(item) + number);
        this.size += number;
    }


    public Map<Item, Integer> getItemMap() {
        return this.items;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public int getSize() {
        return size;
    }
}