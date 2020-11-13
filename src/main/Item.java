package main;


import javafx.scene.control.Button;


public enum Item implements Marketable {

    MELON(20, 4),
    POTATO(5, 20),
    PUMPKIN(15, 8),
    WHEAT(10, 16),

    PESTICIDE(10, 1),
    FERTILIZER(15, 0);


    private int basePrice;
    private int startingQuantity;

    Item(int price, int startQuantity) {
        try {
            this.basePrice = price;
            this.startingQuantity = startQuantity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDisplayName() {
        String str = this.name().toLowerCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public String toConcat() {
        if (this.name().contains("_")) {
            return this.name().substring(0, this.name().indexOf("_"));
        }
        return this.name();
    }

    @Override
    public int getPrice() {
        return this.basePrice;
    }

    @Override
    public int getQuantity() {
        return this.startingQuantity;
    }


    @Override
    public void setButtonSell(Button buttonSell) {
        this.buttonSell = buttonSell;
    }

    @Override
    public Button getButtonSell() {
        return buttonSell;
    }

    @Override
    public void setButtonBuy(Button buttonBuy) {
        this.buttonBuy = buttonBuy;
    }

    @Override
    public Button getButtonBuy() {
        return buttonBuy;
    }

    public void setPrice(int newPrice) {
        this.basePrice = newPrice;
    }

}

