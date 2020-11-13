package main;

import java.util.Arrays;
import java.util.List;

/*
difficulty, starting seed, starting season
 */
public class FarmWorldConfigurations {


    enum Difficulty {
        EASY(1.0),
        MEDIUM(0.75),
        HARD(0.5);

        private double multiplier;

        Difficulty(double multiplier) {
            this.multiplier = multiplier;
        }

        public double getMultiplier() {
            return multiplier;
        }
    }

    private Difficulty difficulty;
    // easy, medium, hard
    private String seed;
    // barrow hills, dessert oasis, rolling plains
    private String season;
    // winter, spring, summer, fall

    private List<Item> startingSeeds;

    public FarmWorldConfigurations() {

    }

    public FarmWorldConfigurations(String difficulty, String seed, String season) {
        this.difficulty = Difficulty.valueOf(difficulty);
        this.seed = seed;
        this.season = season;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String diff) {
        this.difficulty = Difficulty.valueOf(diff);
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getStartingMoney() {
        return (int) (this.difficulty.getMultiplier() * 1000);
    }

    public List<Item> getStartingItems() {
        return Arrays.asList(Item.MELON, Item.POTATO, Item.PUMPKIN, Item.WHEAT,
            Item.MELON_PESTICIDES, Item.PUMPKIN_PESTICIDES, Item.POTATO_PESTICIDES,
            Item.WHEAT_PESTICIDES, Item.PESTICIDE, Item.FERTILIZER);
    }
}