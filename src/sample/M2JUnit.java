package sample;
/*
    JUnit tests for Milestone 2 Code

    @author Dhruv Patel
    @version 1.0
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class M2JUnit {
    private static final int TO = 250;
    private Farm realFarm;
    private FarmWorldConfigurations realWorld;
    private Player realPlayer;

    @Before
    public void setUp() {
        realFarm = new Farm(100);
        realWorld = new FarmWorldConfigurations("Easy", "Rolling Plains", "Spring");
        realPlayer = new Player("Bob");
    }

    //Test Farm
    @Test(timeout = TO)
    public void testIncrementDay() {
        assertEquals(1, realFarm.getDay());
        realFarm.incrementDay();
        assertEquals(2, realFarm.getDay());
    }

    @Test(timeout = TO)
    public void testMoney() {
        assertEquals(100, realFarm.getMoney());
        realFarm.setMoney(250);
        assertEquals(250, realFarm.getMoney());
    }

    //Test Farm World Configurations
    @Test(timeout = TO)
    public void testWorldConstructor() {
        assertEquals("Easy", realWorld.getDifficulty());
        assertEquals("Rolling Plains", realWorld.getSeed());
        assertEquals("Spring", realWorld.getSeason());
    }

    @Test(timeout = TO)
    public void testWorldDifficulty() {
        realWorld.setDifficulty("Hard");
        assertEquals("Hard", realWorld.getDifficulty());
    }

    @Test(timeout = TO)
    public void testWorldSeed() {
        realWorld.setSeed("Barrow Hills");
        assertEquals("Barrow Hills", realWorld.getSeed());
    }

    @Test(timeout = TO)
    public void testWorldSeason() {
        realWorld.setSeason("Winter");
        assertEquals("Winter", realWorld.getSeason());
    }

    //Test Player class
    @Test(timeout = TO)
    public void testConstructor() {
        //Making sure default constructor runs
        Player fakePlayer = new Player();
    }

    @Test(timeout = TO)
    public void testName() {
        assertEquals("Bob", realPlayer.getName());
        realPlayer.setName("Old McDonald");
        assertEquals("Old McDonald", realPlayer.getName());
    }

    //JavaFX
    @Test
    public void testJavaFX() {
        String[] args = new String[10];
        Main.main(args);
    }

}