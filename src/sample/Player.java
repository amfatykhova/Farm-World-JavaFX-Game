package sample;

/*
player class
 */
public class Player {
    private String name;
    // Inventory here

    public Player() {
    }
    public Player(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
