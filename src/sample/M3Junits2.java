package sample;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class M3Junits2 {
    private static final int TO = 1000;
    FarmWorldConfigurations world;

    @Before
    public void setup() {
        FarmWorldConfigurations world = new FarmWorldConfigurations(FarmWorldConfigurations.Difficulty.MEDIUM.toString(),
                                                              "rolling plains", "summer");
    }

    @Test(timeout = TO)
    public void playerDay() {
        Player p1 = new Player();
        assertEquals(p1.getDay(), 1);
        p1.incrementDay();
        assertEquals(p1.getDay(), 2);
    }

    @Test(timeout = TO)
    public void playerInit() {
        Player p1 = new Player();
        Item i1 = Item.MELON;
        List<Item> items = world.getStartingSeeds();
        p1.init("Peter Quill", items, FarmWorldConfigurations.Difficulty.MEDIUM);
        assertEquals(p1.getName(), "Peter Quill");
        assertEquals(p1.getBalance(), 500);
    }

}
