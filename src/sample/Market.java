package sample;

import java.util.Map;
import java.util.Random;

public class Market {
    enum Seed implements Marketable {
        WHEAT(10.0),
        MELON(20.0),
        PUMPKIN(15.0),
        POTATO(5.0);

        private double basePrice;

        Seed(double price) {
            this.basePrice = price;
        }

        @Override
        public double getPrice() {
            return this.basePrice;
        }
    }
    private Inventory inventory; // Probably want to implement with a Set
    private Map<Seed, Double> prices;
    private FarmWorldConfigurations.Difficulty difficulty;

    public Market(String diff, Inventory playerInv) {
        this.difficulty = FarmWorldConfigurations.Difficulty.valueOf(diff);
        for (Seed seed : Seed.values()) {
            prices.put(seed, seed.getPrice() * difficulty.getMultiplier());
        }
    }

    public double getPrice(Seed seed) {
        return this.prices.get(seed);
    }

    public int sellItem(Marketable item, int quantity) {
        this.inventory.remove(item, quantity);
        double variance = new Random().nextGaussian() * 5.0;
        return (int) (((double) quantity) * (item.getPrice() * this.difficulty.getMultiplier()
                + variance));
    }
}
