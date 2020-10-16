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
    private static final int TO = 500;

    @Test (timeout = TO)
    public void playerDay() {
        Player p1 = new Player();
        assertEquals(p1.getDay(), 1);
        p1.incrementDay();
        assertEquals(p1.getDay(), 2);
    }

    @Test (timeout = TO)
    public void itemPrice() {
        Item item1 = Item.MELON;
        Item item2 = Item.POTATO;
        assertEquals(item1.getPrice(), 20.0, 0.0);
        assertEquals(item2.getPrice(), 5.0, 0.0);
    }

    @Test (timeout = TO)
    public void itemQuantity() {
        Item item1 = Item.PUMPKIN;
        Item item2 = Item.WHEAT;
        assertEquals(item1.getQuantity(), 8);
        assertEquals(item2.getQuantity(), 16);
    }

    @Test (timeout = TO)
    public void itemButton() {
        Item item1 = Item.WHEAT;
        item1.setButtonBuy(new Button("Buy Wheat"));
        item1.setButtonSell(new Button("Sell Wheat"));
        assertEquals(item1.getButtonBuy().getText(), "Buy Wheat");
        assertEquals(item1.getButtonSell().getText(), "Sell Wheat");
    }

    @Test (timeout = TO)
    public void inventoryConstructor() {
        List<Item> items = new ArrayList<>();
        items.add(Item.MELON);
        items.add(Item.PUMPKIN);
        items.add(Item.PUMPKIN);
        Inventory inv = new Inventory(items, FarmWorldConfigurations.Difficulty.MEDIUM);

        double mult = FarmWorldConfigurations.Difficulty.MEDIUM.getMultiplier();
        Map<Item, Integer> itemsMap = new HashMap<>();
        for (Item s : items) {
            itemsMap.put(s, (int) (s.getQuantity() * mult));
        }

        assertEquals(itemsMap, inv.getItemMap());
    }

    @Test (timeout = TO, expected = InventoryCapacityException.class)
    public void inventoryGet() throws InventoryCapacityException {
        List<Item> items = new ArrayList<>();
        Item i1 = Item.MELON;
        items.add(Item.MELON);
        items.add(Item.PUMPKIN);
        items.add(Item.PUMPKIN);
        Inventory inv = new Inventory(items, FarmWorldConfigurations.Difficulty.MEDIUM);

        inv.add(Item.MELON, 2);
        assertEquals(inv.getItemMap().get(Item.MELON), (Integer) 6);
        inv.add(Item.MELON, 100000);
    }

    @Test (timeout = TO, expected = IllegalArgumentException.class)
    public void inventoryRemove() {
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
    public void playerInit() {
        Player p1 = new Player();
        List<Item> items = new ArrayList<>();
        //items.add(Item.MELON);
        //items.add(Item.PUMPKIN);
        //items.add(Item.MELON);
        p1.init("Peter Quill", items, FarmWorldConfigurations.Difficulty.HARD);
        assertEquals(p1.getName(), "Peter Quill");
        assertEquals(p1.getBalance(), 500);
    }

    @Test (timeout = TO)
    public void farmWorldStartSeeds() {
        FarmWorldConfigurations world = new FarmWorldConfigurations(
                FarmWorldConfigurations.Difficulty.EASY.toString(),
                "rolling plains", "summer");
        List<Item> list = new ArrayList<>();
        list.add(Item.MELON);
        list.add(Item.POTATO);
        list.add(Item.PUMPKIN);
        list.add(Item.WHEAT);

        assertEquals(list, world.getStartingSeeds());
    }

    @Test
    public void testJavaFX() {
        String[] args = new String[10];
        Main.main(args);
    }
}