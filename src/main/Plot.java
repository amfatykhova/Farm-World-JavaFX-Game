import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Plot {

    private Item plant;
    private Maturity maturity;

    public Plot(Item plant, Maturity maturity) {
        this.plant = plant;
        this.maturity = maturity;
    }

    public Button asButton(int plotSize) {
        String plantName = this.plant.name().toLowerCase();
        String num = this.maturity.getOrder();
        System.out.println("Setting plot with " + plantName + " to " + "file:images/" + plantName
                + num + ".PNG");
        ImageView plotView = null;
        if (this.maturity.equals(Maturity.EMPTY)) {
            plotView = new ImageView(new Image("file:images/empty.PNG"));
        } else if (this.maturity.equals(Maturity.SEED)) {
            plotView = new ImageView(new Image("file:images/seed.PNG"));
        } else {
            plotView = new ImageView(new Image("file:images/" + plantName + num + ".PNG"));
        }
        plotView.setFitHeight(plotSize);
        plotView.setFitWidth(plotSize);
        Button cropButton = new Button();
        cropButton.setGraphic(plotView);
        return cropButton;
    }

    public Item getPlant() {
        return plant;
    }

    public Maturity getMaturity() {
        return maturity;
    }

    public void harvest() {
        this.maturity = Maturity.EMPTY;
        this.plant = null;
    }

    public void plantSeed(Item item, String string) {
        this.maturity = Maturity.SEED;
        if (string.equals("POTATO")) {
            this.plant = Item.POTATO;
            System.out.println("new seed type planted: POTATO");
        } else if (string.equals("MELON")) {
            this.plant = Item.MELON;
            System.out.println("new seed type planted: MELON");
        } else if (string.equals("WHEAT")) {
            this.plant = Item.WHEAT;
            System.out.println("new seed type planted: WHEAT");
        } else { // PUMPKIN
            this.plant = Item.PUMPKIN;
            System.out.println("new seed type planted: PUMPKIN");

        }
    }
}
