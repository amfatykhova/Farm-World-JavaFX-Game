package main;

import java.util.List;
import java.util.Random;

/*
player class
 */
public class Player {

    private String name;
    private Inventory inventory;
    // worker inventory added
    private FarmWorldConfigurations.Difficulty difficulty;
    private int balance;
    private int day;

    public Player() {
        this.day = 1; // Start on day
    }

    public void init(String name, List<Item> items, FarmWorldConfigurations.Difficulty diff) {
        this.inventory = new Inventory(items, diff);
        // worker inventory initialized:
        this.difficulty = diff;
        this.name = name;
        this.balance = (int) (this.difficulty.getMultiplier() * 1000);
        Item.PESTICIDE.setPrice((int) (1 / diff.getMultiplier()) * 10);
    }

    public String getName() {
        return name;
    }

    public void sellItem(Item item, int quantity) {
        try {
            this.inventory.remove(item, quantity);
            double variance = new Random().nextGaussian() * 5.0;
            this.balance += (int) (((double) quantity) * (item.getPrice()
                    * this.difficulty.getMultiplier() + variance));
            System.out.println("Selling " + item.name());
        } catch (InsufficientItemsException e) {
            System.out.println("Cannot sell item. You don't have any " + item.name());
        }
    }

    public void buyItem(Item item, int quantity) throws InsufficientFundsException,
            InventoryCapacityException {
        if (item.getPrice() * quantity > this.balance) {
            throw new InsufficientFundsException("Player does not have enough money to buy "
                    + quantity + " " + item.name());
        }
        this.inventory.add(item, quantity);
        this.balance -= (int) (((double) quantity) * item.getPrice());
    }

    public int incrementDay() {
        return ++this.day;
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getBalance() {
        return this.balance;
    }

    public int getDay() {
        return this.day;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FarmWorldConfigurations.Difficulty getDifficulty() {
        return difficulty;
    }

    public int getRandomEvent() {
        int pRain = (int) (20 * this.difficulty.getMultiplier());
        int pDrought = 0;
        int pLocusts = 0;
        switch (this.difficulty) {
        case MEDIUM:
            pDrought = 10;
            pLocusts = 5;
            break;
        case HARD:
            pDrought = 15;
            pLocusts = 8;
            break;
        default:
            pDrought = 5;
            pLocusts = 2;
        }
        Random rand = new Random();
        int prob = rand.nextInt(100) + 1; // 1..100
        System.out.println("Prob: " + prob);
        if (prob <= pRain) {
            return 1; // Rain
        }
        if (prob <= pRain + pDrought) {
            return 2; // Drought
        }
        if (prob <= pRain + pDrought + pLocusts) {
            return 3; // Locusts
        }
        return 0; // No event
    }
}
