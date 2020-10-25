/*
    JUnit tests for Milestone 3 Code
    @author Noura El Bayrouti
    @version 1.0
 */

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;


public class M4JUnit {
    private static final int TO = 250;

    @Test (timeout = TO)
    public void incrementTimeMethod() {
        Player p1 = new Player();
        int day = p1.getDay();
        p1.incrementDay();
        assertEquals(day + 1, p1.getDay());
    }

    @Test (timeout = TO)
    public void plantPotato() {
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant],Maturity.EMPTY,100);
        plot.plantSeed(Item.POTATO, "POTATO");
        assertEquals(Item.POTATO, plot.getPlant());
        assertEquals(Maturity.SEED, plot.getMaturity());
    }

    @Test (timeout = TO)
    public void plantMelon() {
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant],Maturity.EMPTY,100);
        plot.plantSeed(Item.MELON, "MELON");
        assertEquals(Item.MELON, plot.getPlant());
        assertEquals(Maturity.SEED, plot.getMaturity());
    }

    @Test (timeout = TO)
    public void plantPumpkin() {
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant],Maturity.EMPTY,100);
        plot.plantSeed(Item.PUMPKIN, "PUMPKIN");
        assertEquals(Item.PUMPKIN, plot.getPlant());
        assertEquals(Maturity.SEED, plot.getMaturity());
    }

    @Test (timeout = TO)
    public void plantWheat() {
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant],Maturity.EMPTY,100);
        plot.plantSeed(Item.WHEAT, "WHEAT");
        assertEquals(Item.WHEAT, plot.getPlant());
        assertEquals(Maturity.SEED, plot.getMaturity());
    }

    @Test (timeout = TO)
    public void growthCycle() {
        Random rand = new Random();
        int plant = rand.nextInt(4);
        Plot plot = new Plot(Item.values()[plant],Maturity.EMPTY,100);
        plot.plantSeed(Item.POTATO, "POTATO");
        assertEquals(Item.POTATO, plot.getPlant());
        plot.grow();
        assertEquals(Maturity.SPROUT, plot.getMaturity());
        plot.grow();
        assertEquals(Maturity.IMMATURE, plot.getMaturity());
        plot.grow();
        assertEquals(Maturity.MATURE, plot.getMaturity());
    }

    @Test
    public void testJavaFX() {
        String[] args = new String[10];
        Main.main(args);
    }
}
