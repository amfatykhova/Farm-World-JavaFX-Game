package main;


import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Plot {

    private Item plant;
    private Maturity maturity;
    private Button button;
    private int size;
    private boolean hasPesticides = false;
    private final int minWater = 20;
    private final int maxWater = 90;
    private int waterLevel;

    private final int minFertilizerLevel = 0;
    private final int maxFertilizerLevel = 10;

    private int fertilizerLevel;

    public Plot(Item plant, Maturity maturity, int plotSize) {
        this.plant = plant;
        this.maturity = maturity;
        this.size = plotSize;
        this.waterLevel = 50;
        this.fertilizerLevel = 0;

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
        cropButton.setTooltip(new Tooltip("Water: " + this.getWaterLevel()
                + "\nFertilizer: " + this.getFertilizerLevel()));
        this.button = cropButton;
    }

    public void grow() {
        switch (this.maturity) {
        case SEED:
            if (this.getFertilizerLevel() > 0) {
                this.maturity = Maturity.IMMATURE;
            } else {
                this.maturity = Maturity.SPROUT;
            }
            break;
        case SPROUT:
            if (this.getFertilizerLevel() > 0) {
                this.maturity = Maturity.MATURE;
            } else {
                this.maturity = Maturity.IMMATURE;
            }
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

    public void setPlant(Item plant) {
        this.plant = plant;
    }

    public Maturity getMaturity() {
        return maturity;
    }

    public void harvest() {
        this.maturity = Maturity.EMPTY;
        this.plant = null;
        removePesticides();
    }

    public void plantSeed(String str) {
        this.maturity = Maturity.SEED;
        this.plant = Item.valueOf(str);
    }

    // Multiple other methods need to use this functionality
    private void setTooltip() {
        this.button.getTooltip().setText("Water: " + this.getWaterLevel()
                + "\nFertilizer: " + this.getFertilizerLevel());
    }

    public void waterPlot() {
        this.waterLevel += 10;
        setTooltip();
    }

    public void waterUp(int amount) {
        this.waterLevel += amount;
        if (this.waterLevel > 100) {
            this.waterLevel = 100;
        }
        setTooltip();
        this.waterLevelCheck();
    }

    public void waterDown(int amount) {
        waterLevel -= amount;
        if (waterLevel < 0) {
            waterLevel = 0;
        }
        setTooltip();
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

    public void applyPesticides() {
        hasPesticides = true;
    }
    public void removePesticides() {
        hasPesticides = false;
    }

    public boolean getPesticides() {
        return hasPesticides;
    }

    public int getFertilizerLevel() {
        return fertilizerLevel;
    }

    public void addFertilizer(int addLevel) {
        fertilizerLevel += addLevel;
        if (fertilizerLevel > 10) {
            fertilizerLevel = 10;
        }
        setTooltip();
    }

    public void decrementFertilizerLevel() {
        fertilizerLevel--;
        if (fertilizerLevel < 0) {
            fertilizerLevel = 0;
        }
        setTooltip();
    }

}
