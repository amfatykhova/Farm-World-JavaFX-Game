package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Market {

    private Inventory inventory;
    private Map<Item, Double> prices;
    private FarmWorldConfigurations.Difficulty difficulty;

    public Market() {

    }

    public void init(FarmWorldConfigurations.Difficulty diff, Inventory playerInv) {
        this.difficulty = diff;
        this.inventory = playerInv;
        this.prices = new HashMap<>();
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

    public int buyItem(Item item, int quantity) throws InventoryCapacityException {
        this.inventory.add(item, quantity);
        return (int) (((double) quantity) * (item.getPrice() * this.difficulty.getMultiplier()));
    }

    public Map<Item, Double> getItemMap() {
        return this.prices;
    }


}
