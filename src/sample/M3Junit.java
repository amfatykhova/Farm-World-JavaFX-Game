package sample;

/*
    JUnit tests for Milestone 3 Code

    @author Dhruv Patel
    @version 1.0
 */

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import javafx.scene.control.Button;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class M3Junit {
    private static final int TO = 250;

    @Test (timeout = TO)
    public void player_name() {
        Player p1 = new Player();
        p1.setName("BobBarker");
        assertEquals(p1.getName(), "BobBarker");
    }

    @Test (timeout = TO)
    public void item_price() {
        Item item1 = Item.MELON;
        Item item2 = Item.POTATO;
        assertEquals(item1.getPrice(), 20.0, 0.0);
        assertEquals(item2.getPrice(), 5.0, 0.0);
    }

    @Test (timeout = TO)
    public void item_quantity() {
        Item item1 = Item.PUMPKIN;
        Item item2 = Item.WHEAT;
        assertEquals(item1.getQuantity(), 8);
        assertEquals(item2.getQuantity(), 16);
    }

    @Test (timeout = TO)
    public void item_button() {
        Item item1 = Item.WHEAT;
        item1.setButtonBuy(new Button("Buy Wheat"));
        item1.setButtonSell(new Button("Sell Wheat"));
        assertEquals(item1.getButtonBuy().getText(), "Buy Wheat");
        assertEquals(item1.getButtonSell().getText(), "Sell Wheat");
    }

    @Test (timeout = TO)
    public void inventory_constructor() {
        List<Item> items = new ArrayList<>();
        items.add(Item.MELON);
        items.add(Item.PUMPKIN);
        items.add(Item.PUMPKIN);
        Inventory inv = new Inventory(items, FarmWorldConfigurations.Difficulty.MEDIUM);

        Map<Item, Integer> itemsMap = new HashMap<>();
        for (Item s : items) {
            itemsMap.put(s, (int) (s.getQuantity() * FarmWorldConfigurations.Difficulty.MEDIUM.getMultiplier()));
        }

        assertEquals(itemsMap, inv.getItemMap());
    }

    @Test (timeout = TO, expected = InventoryCapacityException.class)
    public void inventory_get() throws InventoryCapacityException {
        List<Item> items = new ArrayList<>();
        items.add(Item.MELON);
        items.add(Item.PUMPKIN);
        items.add(Item.PUMPKIN);
        Inventory inv = new Inventory(items, FarmWorldConfigurations.Difficulty.MEDIUM);

        inv.add(Item.MELON, 2);
        assertEquals(inv.getItemMap().get(Item.MELON), (Integer) 6);
        inv.add(Item.MELON, 100000);
    }

    @Test (timeout = TO, expected = IllegalArgumentException.class)
    public void inventory_remove() {
        List<Item> items = new ArrayList<>();
        items.add(Item.MELON);
        items.add(Item.PUMPKIN);
        items.add(Item.PUMPKIN);
        Inventory inv = new Inventory(items, FarmWorldConfigurations.Difficulty.MEDIUM);

        inv.remove(Item.MELON, 2);
        assertEquals(inv.getItemMap().get(Item.MELON), (Integer) 2);
        inv.remove(Item.MELON, 100000);
    }

    @Test (timeout = TO)
    public void player_init() {
        Player p1 = new Player();
        List<Item> items = new ArrayList<>();
        items.add(Item.MELON);
        items.add(Item.PUMPKIN);
        items.add(Item.MELON);
        p1.init("Peter Quill", items, FarmWorldConfigurations.Difficulty.HARD);
        assertEquals(p1.getName(), "Peter Quill");
    }

    @Test (timeout = TO)
    public void farmWorld_start_seeds() {
        FarmWorldConfigurations world = new FarmWorldConfigurations(FarmWorldConfigurations.Difficulty.EASY.toString(),
                                                                    "rolling plains", "summer");
        List<Item> list = new ArrayList<>();
        list.add(Item.MELON);
        list.add(Item.POTATO);
        list.add(Item.PUMPKIN);
        list.add(Item.WHEAT);

        assertEquals(world.getStartingSeeds(), list);
    }

    @Test
    public void testJavaFX() {
        String[] args = new String[10];
        Main.main(args);
    }
}