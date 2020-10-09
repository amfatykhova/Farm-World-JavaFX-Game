package sample;

import java.util.List;

/*
player class
 */
public class Player {
    private String name;
    private Inventory inventory;

    public Player() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void init(String name, List<Item> items, FarmWorldConfigurations.Difficulty diff) {
        this.inventory = new Inventory(items, diff);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
