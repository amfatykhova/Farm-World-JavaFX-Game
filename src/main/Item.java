package main;

public enum Item implements Marketable {

    MELON(20, 4),
    POTATO(5, 20),
    PUMPKIN(15, 8),
    WHEAT(10, 16),
    MELON_PESTICIDES(15, 0),
    POTATO_PESTICIDES(3, 0),
    PUMPKIN_PESTICIDES(10, 0),
    WHEAT_PESTICIDES(7, 0),
    PESTICIDE(10, 4),
    FERTILIZER(15, 4);

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
}

