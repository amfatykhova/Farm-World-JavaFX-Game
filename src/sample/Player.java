package sample;

import java.util.List;
import java.util.Random;

/*
player class
 */
public class Player {
    private String name;
    private Inventory inventory;
    private FarmWorldConfigurations.Difficulty difficulty;
    private int balance;

    public Player() {

    }

    public void init(String name, List<Item> items, FarmWorldConfigurations.Difficulty diff) {
        this.inventory = new Inventory(items, diff);
        this.difficulty = diff;
        this.name = name;
        this.balance = (int) (this.difficulty.getMultiplier() * 1000);
    }

    public String getName() {
        return name;
    }

    public void sellItem(Item item, int quantity) {
        try {
            this.inventory.remove(item, quantity);
            double variance = new Random().nextGaussian() * 5.0;
            this.balance += (int) (((double) quantity) * (item.getPrice() * this.difficulty.getMultiplier()
                    + variance));
        } catch (IllegalArgumentException e) {
            System.out.println("Cannot sell item. You don't have any " + item.name());
        }
    }

    public void buyItem(Item item, int quantity) throws InsufficientFundsException, InventoryCapacityException {
        if (item.getPrice() * quantity > this.balance) {
            throw new InsufficientFundsException("Player does not have enough money to buy " + quantity + " " + item.name());
        }
        this.inventory.add(item, quantity);
        this.balance -= (int) (((double) quantity) * item.getPrice());
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public int getBalance() {
        return this.balance;
    }

}