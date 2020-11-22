/*
    JUnit tests for Milestone 6 Code
    @author Noura El Bayrouti
    @version 1.0
 */

package test;

import main.FarmWorldConfigurations;
import main.InsufficientFundsException;
import main.InventoryCapacityException;
import main.Item;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class M6JUnit {
    private static final int TO = 10000;

    @Test (timeout = TO)
    public void tractorLimitUp() {
        main.Player p1 = new main.Player();
        main.FarmWorldConfigurations config = new FarmWorldConfigurations();
        config.setDifficulty("EASY");
        List<Item> items = new ArrayList<>();
        p1.init("Name", items, config.getDifficulty());
        int initial = p1.getPlotHarvestLimit();
        try {
            p1.buyItem(Item.TRACTOR, 1);
        } catch (InsufficientFundsException | InventoryCapacityException e) {
            System.out.println("Couldn't buy");
        }
        assertEquals(initial + 2, p1.getPlotHarvestLimit());
    }

    @Test (timeout = TO)
    public void tractorLimitDown() {
        main.Player p1 = new main.Player();
        main.FarmWorldConfigurations config = new FarmWorldConfigurations();
        config.setDifficulty("EASY");
        List<Item> items = new ArrayList<>();
        p1.init("Name", items, config.getDifficulty());
        try {
            p1.buyItem(Item.TRACTOR, 1);
        } catch (InsufficientFundsException | InventoryCapacityException e) {
            System.out.println("Couldn't buy");
        }
        int initial = p1.getPlotHarvestLimit();
        p1.sellItem(Item.TRACTOR, 1);
        assertEquals(initial - 2, p1.getPlotHarvestLimit());
    }

    @Test (timeout = TO)
    public void irrigationLimitUp() {
        main.Player p1 = new main.Player();
        main.FarmWorldConfigurations config = new FarmWorldConfigurations();
        config.setDifficulty("EASY");
        List<Item> items = new ArrayList<>();
        p1.init("Name", items, config.getDifficulty());
        int initial = p1.getPlotWateringLimit();
        try {
            p1.buyItem(Item.IRRIGATION, 1);
        } catch (InsufficientFundsException | InventoryCapacityException e) {
            System.out.println("Couldn't buy");
        }
        assertEquals(initial + 2, p1.getPlotWateringLimit());
    }

    @Test (timeout = TO)
    public void irrigationLimitDown() {
        main.Player p1 = new main.Player();
        main.FarmWorldConfigurations config = new FarmWorldConfigurations();
        config.setDifficulty("EASY");
        List<Item> items = new ArrayList<>();
        p1.init("Name", items, config.getDifficulty());
        try {
            p1.buyItem(Item.IRRIGATION, 1);
        } catch (InsufficientFundsException | InventoryCapacityException e) {
            System.out.println("Couldn't buy");
        }
        int initial = p1.getPlotWateringLimit();
        p1.sellItem(Item.IRRIGATION, 1);
        assertEquals(initial - 2, p1.getPlotHarvestLimit());
    }

    @Test (timeout = TO)
    public void limitReset() {
        main.Player p1 = new main.Player();
        p1.incrementPlotsHarvested();
        p1.incrementPlotsWatered();
        int initialHarvest = p1.getPlotsHarvestedToday();
        int initialWater = p1.getPlotsWateredToday();
        assertNotEquals(0, initialHarvest);
        assertNotEquals(0, initialWater);
        p1.incrementDay();
        assertEquals(0, p1.getPlotsHarvestedToday());
        assertEquals(0, p1.getPlotsWateredToday());
    }


}