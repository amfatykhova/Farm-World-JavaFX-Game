import java.util.HashMap;
import java.util.Map;

public class Market {

    private Map<Item, Integer> prices;

    public Market() {
        this.prices = new HashMap<>();
        for (Item item : Item.values()) {
            prices.put(item, item.getPrice());
        }
    }

    public double getPrice(Item item) {
        return this.prices.get(item);
    }

    public Map<Item, Integer> getItemMap() {
        return this.prices;
    }
}
