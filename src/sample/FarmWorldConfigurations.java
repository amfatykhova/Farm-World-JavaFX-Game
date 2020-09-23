package sample;

/*
difficulty, starting seed, starting season
 */
public class FarmWorldConfigurations {
    public String difficulty;
    // easy, medium, hard
    public String seed;
    // barrow hills, dessert oasis, rolling plains
    public String season;
    // winter, spring, summer, fall

    // empty constructor
    public FarmWorldConfigurations() {
    }

    public FarmWorldConfigurations(String difficulty, String seed, String season) {
        this.difficulty = difficulty;
        this.seed = seed;
        this.season = season;
    }


    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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
}
