package sample;

import javafx.scene.control.Button;

public enum Item implements Marketable {
    MELON(20.0, 4),
    POTATO(5.0, 20),
    PUMPKIN(15.0, 8),
    WHEAT(10.0, 16);

    private double basePrice;
    private int startingQuantity;
    private Button button;

    Item(double price, int startQuantity) {
        this.basePrice = price;
        this.startingQuantity = startQuantity;
        this.button = new Button("Sell");
    }

    @Override
    public double getPrice() {
        return this.basePrice;
    }

    @Override
    public int getQuantity() {
        return this.startingQuantity;
    }

    @Override
    public void setButton(Button button){
        this.button = button;
    }

    @Override
    public Button getButton() {
        return button;
    }
}
