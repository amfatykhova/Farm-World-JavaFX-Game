package sample;

public enum Item implements Marketable {
    MELON(20.0, 4),
    POTATO(5.0, 20),
    PUMPKIN(15.0, 8),
    WHEAT(10.0, 16);

    private double basePrice;
    private int startingQuantity;

    Item(double price, int startQuantity) {
        this.basePrice = price;
        this.startingQuantity = startQuantity;
    }

    @Override
    public double getPrice() {
        return this.basePrice;
    }

    @Override
    public int getQuantity() {
        return this.startingQuantity;
    }
}
