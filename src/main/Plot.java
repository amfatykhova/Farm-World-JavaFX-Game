import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Plot {

    private Item plant;
    private Maturity maturity;
    private Button button;
    private int size;

    public Plot(Item plant, Maturity maturity, int plotSize) {
        this.plant = plant;
        this.maturity = maturity;
        this.size = plotSize;

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
        this.button = cropButton;
    }

    public void grow() {
        System.out.println("\nMaturity: " + this.maturity.name());
        System.out.println("Plant: " + this.plant.name() + "\n");
        String path = "file:images/" + this.plant.name().toLowerCase();

        switch (this.maturity) {
        case SEED:
            this.maturity = Maturity.SPROUT;
            break;
        case SPROUT:
            this.maturity = Maturity.IMMATURE;
            break;
        case IMMATURE:
            this.maturity = Maturity.MATURE;
            break;
        default:
            return;
        }
        ImageView plotView = new ImageView(new Image(path + this.maturity.getOrder() + ".PNG"));
        plotView.setFitHeight(this.size);
        plotView.setFitWidth(this.size);
        this.button.setGraphic(plotView);
    }

    public Button getButton() {
        return this.button;
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
