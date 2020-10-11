package sample;

import javafx.scene.control.Button;

public enum Item implements Marketable {
    MELON(20.0, 4),
    POTATO(5.0, 20),
    PUMPKIN(15.0, 8),
    WHEAT(10.0, 16);

    private double basePrice;
    private int startingQuantity;
    private Button buttonSell;
    private Button buttonBuy;

    Item(double price, int startQuantity) {
        this.basePrice = price;
        this.startingQuantity = startQuantity;
        this.buttonSell = new Button("Sell");
        this.buttonBuy = new Button("Buy");
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
    public void setButtonSell(Button buttonSell){
        this.buttonSell = buttonSell;
    }

    @Override
    public Button getButtonSell() {
        return buttonSell;
    }

    @Override
    public void setButtonBuy(Button buttonBuy){
        this.buttonBuy = buttonBuy;
    }

    @Override
    public Button getButtonBuy(){
        return buttonBuy;
    }
}

