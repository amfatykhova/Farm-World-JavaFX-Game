/*
    JUnit tests for Milestone 5 Code
    @author Noura El Bayrouti
    @version 1.0
 */

package test;

import javafx.embed.swing.JFXPanel;
import main.FarmWorldConfigurations;
import main.Item;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class M5JUnit {
    private static final int TO = 10000;

    @Test(timeout = TO)
    public void applyPesticide() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        main.Plot plot = new main.Plot(main.Item.values()[plant], main.Maturity.EMPTY, 100);
        plot.plantSeed("POTATO");

        plot.applyPesticides();
        assertEquals(true, plot.getPesticides());
    }

    @Test(timeout = TO)
    public void removePesticide() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        main.Plot plot = new main.Plot(main.Item.values()[plant], main.Maturity.EMPTY, 100);
        plot.plantSeed("POTATO");

        plot.applyPesticides();
        plot.removePesticides();
        assertEquals(false, plot.getPesticides());
    }

    @Test(timeout = TO)
    public void pesticidePriceEasy() {
        main.Player p1 = new main.Player();
        main.FarmWorldConfigurations config = new FarmWorldConfigurations();
        config.setDifficulty("EASY");
        List<Item> items = new ArrayList<>();
        p1.init("Name", items, config.getDifficulty());
        assertEquals(10, Item.PESTICIDE.getPrice());
    }

    @Test(timeout = TO)
    public void pesticidePriceMedium() {
        main.Player p1 = new main.Player();
        main.FarmWorldConfigurations config = new FarmWorldConfigurations();
        config.setDifficulty("MEDIUM");
        List<Item> items = new ArrayList<>();
        p1.init("Name", items, config.getDifficulty());
        assertEquals((int) (1 / 0.75) * 10, Item.PESTICIDE.getPrice());
    }

    @Test(timeout = TO)
    public void pesticidePriceHard() {
        main.Player p1 = new main.Player();
        main.FarmWorldConfigurations config = new FarmWorldConfigurations();
        config.setDifficulty("HARD");
        List<Item> items = new ArrayList<>();
        p1.init("Name", items, config.getDifficulty());
        assertEquals(20, Item.PESTICIDE.getPrice());
    }

    @Test(timeout = TO)
    public void addFertilizer() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        main.Plot plot = new main.Plot(main.Item.values()[plant], main.Maturity.EMPTY, 100);
        plot.plantSeed("POTATO");
        int initialLevel = plot.getFertilizerLevel();
        plot.addFertilizer(3);
        assertEquals(initialLevel + 3, plot.getFertilizerLevel());
    }

    @Test(timeout = TO)
    public void addFertilizerMax() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        main.Plot plot = new main.Plot(main.Item.values()[plant], main.Maturity.EMPTY, 100);
        plot.plantSeed("POTATO");
        int initialLevel = plot.getFertilizerLevel();
        plot.addFertilizer(12);
        assertEquals(10, plot.getFertilizerLevel());
    }

    @Test(timeout = TO)
    public void decreaseFertilizer() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        main.Plot plot = new main.Plot(main.Item.values()[plant], main.Maturity.EMPTY, 100);
        plot.plantSeed("POTATO");
        plot.addFertilizer(3);
        int initialLevel = plot.getFertilizerLevel();
        plot.decrementFertilizerLevel();
        assertEquals(initialLevel - 1, plot.getFertilizerLevel());
    }

    @Test(timeout = TO)
    public void decreaseFertilizerMin() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        main.Plot plot = new main.Plot(main.Item.values()[plant], main.Maturity.EMPTY, 100);
        plot.plantSeed("POTATO");
        int initialLevel = plot.getFertilizerLevel();
        plot.decrementFertilizerLevel();
        assertEquals(0, plot.getFertilizerLevel());
    }

    @Test(timeout = TO)
    public void fertilizerPriceEasy() {
        main.Player p1 = new main.Player();
        main.FarmWorldConfigurations config = new FarmWorldConfigurations();
        config.setDifficulty("EASY");
        List<Item> items = new ArrayList<>();
        p1.init("Name", items, config.getDifficulty());
        assertEquals(15, Item.FERTILIZER.getPrice());
    }

    @Test(timeout = TO)
    public void fertilizerPriceMedium() {
        main.Player p1 = new main.Player();
        main.FarmWorldConfigurations config = new FarmWorldConfigurations();
        config.setDifficulty("MEDIUM");
        List<Item> items = new ArrayList<>();
        p1.init("Name", items, config.getDifficulty());
        assertEquals((int) (1 / 0.75) * 15, Item.FERTILIZER.getPrice());
    }

    @Test(timeout = TO)
    public void fertilizerPriceHard() {
        main.Player p1 = new main.Player();
        main.FarmWorldConfigurations config = new FarmWorldConfigurations();
        config.setDifficulty("HARD");
        List<Item> items = new ArrayList<>();
        p1.init("Name", items, config.getDifficulty());
        assertEquals(30, Item.FERTILIZER.getPrice());
    }
}
