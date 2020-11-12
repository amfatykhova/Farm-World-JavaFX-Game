package sample;

import javafx.scene.control.Button;

public enum Worker implements Workable{
    ENTRY(30, 1),
    MEDIUM(40, 3),
    ADVANCED(60, 5);

    private int basePrice;
    private int harvestAmount;

    Worker(int price, int harvestAmount) {
        try {
            this.basePrice = price;
            this.harvestAmount = harvestAmount;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getPrice() {
        return this.basePrice;
    }

    @Override
    public int getAmount() {
        return this.harvestAmount;
    }

}
