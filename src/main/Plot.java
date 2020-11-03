import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Plot {

    private Item plant;
    private Maturity maturity;
    private Button button;
    private int size;
    private final int minWater = 20;
    private final int maxWater = 90;
    private int waterLevel;

    public Plot(Item plant, Maturity maturity, int plotSize) {
        this.plant = plant;
        this.maturity = maturity;
        this.size = plotSize;
        this.waterLevel = 50;

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
        cropButton.setTooltip(new Tooltip("Water: " + this.getWaterLevel()));
        this.button = cropButton;
    }

    public void grow() {
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
        String path = "file:images/" + this.plant.name().toLowerCase();
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

    public void plantSeed(String str) {
        this.maturity = Maturity.SEED;
        this.plant = Item.valueOf(str);
    }

    public void waterPlot() {
        this.waterLevel += 10;
        this.button.getTooltip().setText("Water: " + this.waterLevel);
    }

    public void waterUp(int amount) {
        this.waterLevel += amount;
        if (this.waterLevel > 100) {
            this.waterLevel = 100;
        }
        this.button.getTooltip().setText("Water: " + this.waterLevel);
        this.waterLevelCheck();
    }

    public void waterDown(int amount) {
        waterLevel -= amount;
        if (waterLevel < 0) {
            waterLevel = 0;
        }
        this.button.getTooltip().setText("Water: " + this.waterLevel);
        this.waterLevelCheck();
    }

    public int getWaterLevel() {
        return this.waterLevel;
    }

    public void waterLevelCheck() {
        // If too much or not enough water, kill the plant
        if (waterLevel > maxWater || waterLevel < minWater) {
            this.kill();
        }
    }

    public void kill() {
        if (this.getMaturity().equals(Maturity.DEAD)) {
            return;
        }
        this.maturity = Maturity.DEAD;
        ImageView plotView = new ImageView(new Image("file:images/dead.PNG"));
        plotView.setFitHeight(this.size);
        plotView.setFitWidth(this.size);
        this.button.setGraphic(plotView);
    }

}
