package sample;

import javafx.scene.control.Button;

public interface Marketable {
    public double getPrice();
    public int getQuantity();
    public void setButtonSell(Button buttonSell);
    public Button getButtonSell();
    public void setButtonBuy(Button buttonBuy);
    public Button getButtonBuy();
}
