import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private Map<Item, Integer> items;
    private int capacity;

    public Inventory(List<Item> startItems, FarmWorldConfigurations.Difficulty difficulty) {
        this.capacity = (int) (100 * difficulty.getMultiplier());
        this.items = new HashMap<Item, Integer>(this.capacity);
        for (Item s : startItems) {
            this.items.put(s, (int) (s.getQuantity() * difficulty.getMultiplier()));
        }
    }

    public void remove(Item item, int number) throws IllegalArgumentException {
        int existingQuantity = this.items.get(item);
        if (existingQuantity < number) {
            throw new IllegalArgumentException("Cannot remove " + number + " " + item.name()
                    + "'s since there are only " + existingQuantity
                    + " in the player's inventory");
        }
        this.items.put(item, existingQuantity - number); // Update the quantity of that item
    }

    public void add(Item item, int number) throws InventoryCapacityException {
        if (this.items.size() + number > this.capacity) {
            throw new InventoryCapacityException("The player's inventory (size = "
                    + this.items.size() + ") does not have enough capacity to add "
                    + number + " " + item.name() + "'s ");
        }
        this.items.put(item, this.items.get(item) + number);
    }

    // CREATE A TO ARRAY LIST METHOD
    public ArrayList<Item> toArrayList() {
    }

    public Map<Item, Integer> getItemMap() {
        return this.items;
    }

}