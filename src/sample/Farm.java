package sample;

public class Farm {
    private int money;
    private int day;

    public Farm(int startingMoney) {
        this.money = startingMoney;
        this.day = 1; // Start on day 1
    }

    public void incrementDay() {
        day++;
    }

    public void setMoney(int amount) {
        this.money = amount;
    }

    public int getMoney() {
        return this.money;
    }

    public int getDay() {
        return this.day;
    }
}