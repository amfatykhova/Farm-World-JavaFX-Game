package main;

public enum Maturity {
    EMPTY(""),
    SEED(""),
    SPROUT("1"),
    IMMATURE("2"),
    MATURE("3"),
    DEAD("4");

    private String order;

    Maturity(String order) {
        this.order = order;
    }

    public String getOrder() {
        return this.order;
    }
}
