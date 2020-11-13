/*
    JUnit tests for Milestone 3 Code
    @author Noura El Bayrouti
    @version 1.0
 */

import javafx.embed.swing.JFXPanel;
import main.*;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;


public class M4JUnit {
    private static final int TO = 10000;

    @Test(timeout = TO)
    public void incrementTimeMethod() {
        Player p1 = new Player();
        int day = p1.getDay();
        p1.incrementDay();
        assertEquals(day + 1, p1.getDay());
    }

    @Test(timeout = TO)
    public void plantPotato() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant], Maturity.EMPTY, 100);
        plot.plantSeed("POTATO");
        assertEquals(Item.POTATO, plot.getPlant());
        assertEquals(Maturity.SEED, plot.getMaturity());
    }

    @Test(timeout = TO)
    public void plantMelon() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int type = rand.nextInt(4); // 0..
        Plot plot = new Plot(Item.MELON, Maturity.EMPTY, 100);
        plot.plantSeed("MELON");
        assertEquals(Item.MELON, plot.getPlant());
        assertEquals(Maturity.SEED, plot.getMaturity());
    }

    @Test(timeout = TO)
    public void plantPumpkin() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant], Maturity.EMPTY, 100);
        plot.plantSeed("PUMPKIN");
        assertEquals(Item.PUMPKIN, plot.getPlant());
        assertEquals(Maturity.SEED, plot.getMaturity());
    }

    @Test(timeout = TO)
    public void plantWheat() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant], Maturity.EMPTY, 100);
        plot.plantSeed("WHEAT");
        assertEquals(Item.WHEAT, plot.getPlant());
        assertEquals(Maturity.SEED, plot.getMaturity());
    }

    @Test(timeout = TO)
    public void growthCycle() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant], Maturity.EMPTY, 100);
        plot.plantSeed("POTATO");
        assertEquals(Item.POTATO, plot.getPlant());
        plot.grow();
        assertEquals(Maturity.SPROUT, plot.getMaturity());
        plot.grow();
        assertEquals(Maturity.IMMATURE, plot.getMaturity());
        plot.grow();
        assertEquals(Maturity.MATURE, plot.getMaturity());
    }

    @Test(timeout = TO)
    public void watering() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant], Maturity.SEED, 100);
        int initialWater = plot.getWaterLevel();
        plot.waterPlot();
        assertEquals(initialWater + 25, plot.getWaterLevel());
    }

    @Test(timeout = TO)
    public void waterDown() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant], Maturity.SEED, 100);
        int initialWater = plot.getWaterLevel();
        plot.waterDown();
        assertEquals(initialWater - 10, plot.getWaterLevel());
    }


    @Test(timeout = TO)
    public void tooMuchWater() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant], Maturity.SEED, 100);
        int initialWater = plot.getWaterLevel();
        plot.waterPlot();
        plot.waterPlot();
        plot.waterPlot();
        assertEquals(initialWater + 75, plot.getWaterLevel());
        plot.waterLevelCheck();
        assertEquals(Maturity.DEAD, plot.getMaturity());
    }

    @Test(timeout = TO)
    public void notEnoughWater() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant], Maturity.SEED, 100);
        int initialWater = plot.getWaterLevel();
        plot.waterDown();
        plot.waterDown();
        plot.waterDown();
        plot.waterDown();
        assertEquals(initialWater - 40, plot.getWaterLevel());
        plot.waterLevelCheck();
        assertEquals(Maturity.DEAD, plot.getMaturity());
    }

    @Test(timeout = TO)
    public void sufficientWaterLowerBound() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant], Maturity.SEED, 100);
        int initialWater = plot.getWaterLevel();
        plot.waterDown();
        plot.waterDown();
        plot.waterDown();
        plot.waterLevelCheck();
        assertEquals(Maturity.SEED, plot.getMaturity());
    }

    @Test(timeout = TO)
    public void sufficientWaterUpperBound() {
        JFXPanel fxPanel = new JFXPanel();
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant], Maturity.SEED, 100);
        int initialWater = plot.getWaterLevel();
        plot.waterPlot();
        plot.waterPlot();
        plot.waterLevelCheck();
        assertEquals(Maturity.SEED, plot.getMaturity());
    }
}
