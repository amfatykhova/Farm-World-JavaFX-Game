package sample;

public class Farm {
    private int money;
    private int day;

    public Farm(int startingMoney) {
        if (startingMoney < 0) {
            throw new IllegalArgumentException("Starting money can't be a negative integer.");
        }
        this.money = startingMoney;
        this.day = 1; // Start on day 1
    }

    public void incrementDay() {
        day++;
    }

    public void setMoney(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Starting money can't be a negative integer.");
        }
        this.money = amount;
    }

    public int getMoney() {
        return this.money;
    }

    public int getDay() {
        return this.day;
    }
}