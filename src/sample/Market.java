package sample;

import java.util.Map;
import java.util.Random;

public class Market {

    private Inventory inventory;
    private Map<Item, Double> prices;
    private FarmWorldConfigurations.Difficulty difficulty;

    public Market(String diff, Inventory playerInv) {
        this.difficulty = FarmWorldConfigurations.Difficulty.valueOf(diff);
        this.inventory = playerInv;
        for (Item item : Item.values()) {
            prices.put(item, item.getPrice() * difficulty.getMultiplier());
        }
    }

    public double getPrice(Item item) {
        return this.prices.get(item);
    }

    public int sellItem(Item item, int quantity) {
        this.inventory.remove(item, quantity);
        double variance = new Random().nextGaussian() * 5.0;
        return (int) (((double) quantity) * (item.getPrice() * this.difficulty.getMultiplier()
                + variance));
    }

    public void buyItem(Item item, int quantity) {

    }
}
