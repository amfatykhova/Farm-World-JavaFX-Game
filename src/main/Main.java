package main;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;


public class Main extends Application {

    private static Scene config;
    private static Scene farmUI;
    private static Scene marketUI;

    // game canvas dimensions
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int PLOT_ROWS = 3;
    private static final int PLOT_COLS = 4;
    private static final int PLOT_SIZE = 100;

    private static final Font DISPLAY_FONT = Font.font("Verdana", FontWeight.MEDIUM, 24);

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Farm World");

        // -------SCENE WELCOME-------
        Group welcomeGroup = new Group();
        Scene welcome = new Scene(welcomeGroup);
        primaryStage.setScene(welcome);
        Canvas welcomeCanvas = new Canvas(WIDTH, HEIGHT);
        welcomeGroup.getChildren().add(welcomeCanvas);
        GraphicsContext gcWelcome = welcomeCanvas.getGraphicsContext2D();

        // SET BACKGROUND IMAGE
        Image farmImg = new Image("file:images/TitleScreen.PNG");
        gcWelcome.drawImage(farmImg, 0, 0);

        // SETS UP START BUTTON
        Button start = new Button("START");
        start.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400),"
            + " linear-gradient(#ffef84, #f2ba44),"
            + " linear-gradient(#ffea6a, #efaa22),"
            + " linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),"
            + " linear-gradient(from 0% 0% to 15% 50%,"
            + " rgba(255,255,255,0.9),"
            + " rgba(255,255,255,0));"
            + "-fx-background-radius: 30;"
            + "-fx-background-insets: 0,1,2,3,0;"
            + "-fx-text-fill: #654b00;"
            + "-fx-font-weight: bold;"
            + "-fx-font-size: 14px;"
            + "-fx-padding: 10 20 10 20;");
        start.setTranslateY(welcomeCanvas.getHeight() * 0.75 + 15); // 615
        start.setTranslateX(welcomeCanvas.getWidth() / 2 - 30); // 370
        welcomeGroup.getChildren().add(start);


        // ------SCENE CONFIGURATION-------
        Group configGroup = new Group();
        config = new Scene(configGroup);
        // switch when button "start" clicked

        start.setOnMouseClicked(e -> primaryStage.setScene(config));
        Canvas configCanvas = new Canvas(WIDTH, HEIGHT);

        // SET'S BACKGROUND IMAGE FOR OPTIONS SCREEN
        GraphicsContext gcConfig = configCanvas.getGraphicsContext2D();
        Image configImg = new Image("file:images/Market.PNG");
        gcConfig.drawImage(configImg, 0, 0);


        // CREATES A NEW FARM WORLD CONFIGURATIONS OBJECT
        FarmWorldConfigurations configurationsOfWorld = new FarmWorldConfigurations();
        // CREATES DROP DOWN MENU FOR DIFFICULTY
        ObservableList<String> difficultyOptions =
            FXCollections.observableArrayList(
                "Easy",
                "Medium",
                "Hard"
            );
        final ComboBox<?> diffBox = new ComboBox<>(difficultyOptions);
        // CREATES DROP DOWN MENU FOR SEED
        ObservableList<String> seedOptions =
            FXCollections.observableArrayList(
                "Barrow Hills",
                "Dessert Oasis",
                "Rolling Plains"
            );
        final ComboBox<?> seedBox = new ComboBox<>(seedOptions);
        // CREATES DROP DOWN MENU FOR SEASON
        ObservableList<String> seasonOptions =
            FXCollections.observableArrayList(
                "Winter",
                "Spring",
                "Summer",
                "Fall"
            );

        final ComboBox<?> seasonBox = new ComboBox<>(seasonOptions);

        AtomicBoolean toUI = new AtomicBoolean(false);

        Player player = new Player();
        Market market = new Market();

        GridPane grid = configOptionsScreen(new ComboBox[]{diffBox, seedBox, seasonBox},
            configGroup, configCanvas, toUI, configurationsOfWorld, player);

        // -------SCENE FarmUI--------
        configFarmUI(primaryStage, configurationsOfWorld, toUI, player, grid, market);

        // SHOW STAGE
        primaryStage.show();
    }

    private static void configFarmUI(Stage primaryStage, FarmWorldConfigurations worldConfig,
                                     AtomicBoolean toUI, Player player, GridPane grid,
                                     Market market) {

        Button continueToUI = new Button("Click to Continue");
        continueToUI.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400),"
            + " linear-gradient(#ffef84, #f2ba44),"
            + " linear-gradient(#ffea6a, #efaa22),"
            + " linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),"
            + " linear-gradient(from 0% 0% to 15% 50%,"
            + " rgba(255,255,255,0.9),"
            + " rgba(255,255,255,0));"
            + "-fx-background-radius: 30;"
            + "-fx-background-insets: 0,1,2,3,0;"
            + "-fx-text-fill: #654b00;"
            + "-fx-font-weight: bold;"
            + "-fx-font-size: 14px;"
            + "-fx-padding: 10 20 10 20;");
        grid.add(continueToUI, 0, 6);

        Group farmUIGroup = new Group();
        Canvas farmCanvas = new Canvas(WIDTH, HEIGHT);
        farmUI = new Scene(farmUIGroup);

        Label fillEverything = new Label("Please fill in every field correctly to continue!");
        fillEverything.setTextFill(Color.WHITE); // NEW CONTENT

        continueToUI.setOnMouseClicked(e -> {
            // GOES TO USER INTERFACE SCENE
            boolean user = toUI.getAndSet(true);
            if (user) {
                grid.add(new Label(player.getName() + " " + worldConfig.getDifficulty()
                    + " " + worldConfig.getSeed() + " "
                    + worldConfig.getSeason()), 0, 5);

                // NEW CONTRNT EDITED METHOD PARAMETERS: ADDED "worldConfig"
                configureFarmScreen(primaryStage, farmUIGroup, farmCanvas,
                    player, market, openInventory(player.getInventory()), worldConfig);

                primaryStage.setScene(farmUI);
            } else {
                grid.add(fillEverything, 7, 0);
            }
        });
    }

    /*
    Figured out how to refresh hashmap items in an ObservableList from this StackOverflow link:
    https://stackoverflow.com/questions/18618653/binding-hashmap-with-tableview-javafx
     */
    private static TableView openInventory(Inventory inventory) {
        Map<Item, Integer> map = inventory.getItemMap();

        // use fully detailed type for Map.Entry<String, String>
        TableColumn<Map.Entry<Item, Integer>, String> column1 = new TableColumn<>("Item");
        column1.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue()
            .getKey().getDisplayName()));

        TableColumn<Map.Entry<Item, Integer>, String> column2 = new TableColumn<>("Quantity");
        column2.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue()
            .getValue().toString()));

        ObservableList<Map.Entry<Item, Integer>> items =
            FXCollections.observableArrayList(map.entrySet());
        final TableView<Map.Entry<Item, Integer>> table = new TableView<>(items);

        table.getColumns().setAll(column1, column2);
        table.setMaxHeight(150);
        table.setMaxWidth(200);
        return table;
    }

    private static GridPane configOptionsScreen(ComboBox[] boxes, Group configGroup,
                                                Canvas configCanvas, AtomicBoolean toUI,
                                                FarmWorldConfigurations world, Player player)
        throws RuntimeException {

        // CREATES TEXT FIELD AND BUTTON FOR NAME ENTRY
        TextField nameEntry = new TextField();
        // CREATES BUTTON TO ENTER WORLD SPECIFICATIONS
        GridPane grid = new GridPane();
        Button enter = new Button("Enter Game Configurations");
        enter.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400),"
            + " linear-gradient(#ffef84, #f2ba44),"
            + " linear-gradient(#ffea6a, #efaa22),"
            + " linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),"
            + " linear-gradient(from 0% 0% to 15% 50%,"
            + " rgba(255,255,255,0.9),"
            + " rgba(255,255,255,0));"
            + "-fx-background-radius: 30;"
            + "-fx-background-insets: 0,1,2,3,0;"
            + "-fx-text-fill: #654b00;"
            + "-fx-font-weight: bold;"
            + "-fx-font-size: 14px;"
            + "-fx-padding: 10 20 10 20;");
        Button nameEnter = new Button("Enter Name: ");
        nameEnter.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400),"
            + " linear-gradient(#ffef84, #f2ba44),"
            + " linear-gradient(#ffea6a, #efaa22),"
            + " linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),"
            + " linear-gradient(from 0% 0% to 15% 50%,"
            + " rgba(255,255,255,0.9),"
            + " rgba(255,255,255,0));"
            + "-fx-background-radius: 30;"
            + "-fx-background-insets: 0,1,2,3,0;"
            + "-fx-text-fill: #654b00;"
            + "-fx-font-weight: bold;"
            + "-fx-font-size: 14px;"
            + "-fx-padding: 10 20 10 20;");
        Label nameLabel = new Label("Enter Name: ");
        nameLabel.setTextFill(Color.WHITE); // NEW CONTENT


        // ORGANIZES ALL ATTRIBUTES IN A GRID PANE (COLUMN, ROW)
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(nameLabel, 0, 0);
        grid.add(nameEntry, 1, 0);
        grid.add(nameEnter, 2, 0);
        Label diff = new Label("Choose Difficulty");
        diff.setTextFill(Color.WHITE);
        grid.add(diff, 0, 1);
        grid.add(boxes[0], 1, 1);
        Label seed = new Label("Choose Seed: ");
        seed.setTextFill(Color.WHITE);
        grid.add(seed, 0, 2);
        grid.add(boxes[1], 1, 2);
        Label season = new Label("Choose Season: ");
        season.setTextFill(Color.WHITE);
        grid.add(season, 0, 3);
        grid.add(boxes[2], 1, 3);
        grid.add(enter, 0, 4);

        configGroup.getChildren().add(configCanvas);
        configGroup.getChildren().add(grid);

        AtomicBoolean itsAllGood = new AtomicBoolean(false);

        nameEnter.setOnMouseClicked(e -> {
            boolean invalid = true;
            char[] a = nameEntry.getText().toCharArray();
            for (char letter : a) {
                if (!(letter == ' ')) {
                    invalid = false;
                    break;
                }
            }
            if (invalid) {
                nameLabel.setText("Name invalid, please re-enter:");
                nameLabel.setTextFill(Color.WHITE);
                nameEntry.clear();
            } else {
                nameLabel.setText("Name Entered: " + nameEntry.getText());
                nameLabel.setTextFill(Color.WHITE);
                itsAllGood.set(true);
            }
        });

        Label addDiff = new Label("Please select a difficulty, don't leave it blank");
        addDiff.setTextFill(Color.WHITE); // NEW
        Label addSeed = new Label("Please select a seed, don't leave it blank");
        addSeed.setTextFill(Color.WHITE); // NEW
        Label addSeason = new Label("Please select a season, don't leave it blank");
        addSeason.setTextFill(Color.WHITE); // NEW
        Label addNameMust = new Label("Please don't forget to enter a valid name!");
        addNameMust.setTextFill(Color.WHITE); // NEW

        enter.setOnMouseClicked(e -> {
            boolean isDifficultyEmpty = boxes[0].getValue() == null;
            boolean isSeedEmpty = boxes[1].getValue() == null;
            boolean isSeasonEmpty = boxes[2].getValue() == null;

            boolean itsOk = itsAllGood.getAndSet(true);
            if (nameEntry.getText() == null) {
                itsOk = false;
                grid.add(addNameMust, 5, 0);
                nameLabel.setText("please don't leave the name null");
            }
            if (!isDifficultyEmpty) {
                grid.getChildren().remove(addDiff);
            }
            if (!isSeedEmpty) {
                grid.getChildren().remove(addSeed);
            }
            if (!isSeasonEmpty) {
                grid.getChildren().remove(addSeason);
            }
            if (nameEntry.getText() != null) {
                grid.getChildren().remove(addNameMust);
            }
            if (!isDifficultyEmpty && !isSeedEmpty && !isSeasonEmpty && itsOk) {
                world.setDifficulty(boxes[0].getValue().toString().toUpperCase());
                world.setSeed(boxes[1].getValue().toString());
                world.setSeason(boxes[2].getValue().toString());
                player.init(nameEntry.getText(), world.getStartingItems(), world.getDifficulty());
                addDiff.setText("");
                addSeed.setText("");
                addSeason.setText("");
                addNameMust.setText("");
                toUI.getAndSet(true);
            } else {
                if (isDifficultyEmpty) {
                    grid.add(addDiff, 2, 1);
                }
                if (isSeedEmpty) {
                    grid.add(addSeed, 2, 2);
                }
                if (isSeasonEmpty) {
                    grid.add(addSeason, 2, 3);
                }
            }
        });
        return grid;
    }

    private static void configureFarmScreen(Stage primaryStage, Group farmUIGroup,
                                            Canvas farmCanvas, Player player, Market market,
                                            TableView<Map.Entry<Item, Integer>> tableView,
                                            FarmWorldConfigurations worldConfig) {
        Text moneyDisplay = new Text("Money: $" + player.getBalance());
        Text dayDisplay = new Text("Day " + player.getDay());
        Button toMarketButton = new Button("Market");
        toMarketButton.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400),"
            + " linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),"
            + " linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),"
            + " linear-gradient(from 0% 0% to 15% 50%,"
            + " rgba(255,255,255,0.9), rgba(255,255,255,0));"
            + "-fx-background-radius: 30; -fx-background-insets: 0,1,2,3,0;"
            + "-fx-text-fill: #654b00; -fx-font-weight: bold; -fx-font-size: 14px;"
            + "-fx-padding: 10 20 10 20;");
        Button nextDayButton = new Button("Next Day");
        nextDayButton.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400),"
            + " linear-gradient(#ffef84, #f2ba44), linear-gradient(#ffea6a, #efaa22),"
            + " linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),"
            + " linear-gradient(from 0% 0% to 15% 50%,"
            + " rgba(255,255,255,0.9), rgba(255,255,255,0));"
            + "-fx-background-radius: 30; -fx-background-insets: 0,1,2,3,0;"
            + "-fx-text-fill: #654b00; -fx-font-weight: bold; -fx-font-size: 14px;"
            + "-fx-padding: 10 20 10 20;");
        GridPane farmGrid = new GridPane();

        //IMPORTS BACKGROUND IMAGE
        Image hills = new Image("file:images/BarrowHills.PNG");
        Image desert = new Image("file:images/DessertOasis.PNG");
        Image plains = new Image("file:images/RollingPlains.PNG");
        ImagePattern hillsPattern = new ImagePattern(hills);
        ImagePattern desertPattern = new ImagePattern(desert);
        ImagePattern plainsPattern = new ImagePattern(plains);

        if (worldConfig.getSeed().equals("Barrow Hills")) {
            farmUI.setFill(hillsPattern);
        } else if (worldConfig.getSeed().equals("Dessert Oasis")) {
            farmUI.setFill(desertPattern);
        } else if (worldConfig.getSeed().equals("Rolling Plains")) {
            farmUI.setFill(plainsPattern);
        }

        Plot[][] plots = new Plot[PLOT_ROWS][PLOT_COLS];

        moneyDisplay.setFont(DISPLAY_FONT);
        moneyDisplay.setTranslateY(moneyDisplay.getLayoutBounds().getHeight());
        dayDisplay.setFont(DISPLAY_FONT);
        dayDisplay.setTranslateY(dayDisplay.getLayoutBounds().getHeight());
        dayDisplay.setTranslateX(WIDTH - dayDisplay.getLayoutBounds().getWidth());
        toMarketButton.setTranslateY(moneyDisplay.getLayoutBounds().getHeight() * 2.0);
        toMarketButton.setFont(DISPLAY_FONT);
        Label inventoryLabel = new Label("Inventory:");
        inventoryLabel.setTranslateY(moneyDisplay.getLayoutBounds().getHeight() * 4.5);
        tableView.setTranslateY(moneyDisplay.getLayoutBounds().getHeight() * 5.0);
        nextDayButton.setFont(DISPLAY_FONT);
        nextDayButton.setTranslateY(HEIGHT - moneyDisplay.getLayoutBounds().getHeight() * 2.0);

        setupPlots(player, tableView, farmGrid, plots);

        toMarketButton.setOnMouseClicked(e -> {
            moneyDisplay.setTranslateY(moneyDisplay.getLayoutBounds().getHeight());
            setupMarket(primaryStage, player, market, moneyDisplay, tableView, farmUIGroup);
            System.out.println(player.getBalance());
        });

        nextDayButton.setOnMouseClicked(e -> {
            dayDisplay.setText("Day " + player.incrementDay());
            dayDisplay.setTranslateX(WIDTH - dayDisplay.getLayoutBounds().getWidth());
            ArrayList<Button> plotButtons = new ArrayList<>();

            // Calculate random events
            int event = player.getRandomEvent();
            System.out.println("Rand event is: " + event);
            Random rand = new Random();
            int amount = (rand.nextInt(6) + 1) * 10;
            double threshold = 1 - (0.6 * player.getDifficulty().getMultiplier());
            int numToKill = (int) (threshold * PLOT_COLS * PLOT_ROWS);
            int numKilled = 0;
            for (int i = 0; i < PLOT_COLS; i++) {
                for (int j = 0; j < PLOT_ROWS; j++) {
                    switch (event) {
                    case 1:
                        plots[j][i].waterUp(amount); // Rain
                        break;
                    case 2:
                        plots[j][i].waterDown(amount); // Drought
                        break;
                    case 3:
                        // Locusts
                        System.out.println("Locusts: supposed to kill " + numToKill);
                        if (plots[j][i].getPlant() == null) {
                            break;
                        }
                        if (plots[j][i].getPlant().name().contains("_")) { //Ignore if pesticides
                            System.out.println("Your pesticides protected against locusts!");
                            numToKill--;
                            break;
                        }
                        if (Math.random() <= 0.5 && numToKill > 0) {
                            plots[j][i].kill();
                            numToKill--;
                            numKilled++;
                        }
                        break;
                    default:
                        // Nothing
                    }
                    // No random event occurred, so decrease hydration and advance growth stage
                    plots[j][i].waterDown(10);
                    //Grow twice if fertilized, otherwise one grow
                    if (plots[j][i].getFertilizerLevel() > 0) {
                        plots[j][i].grow();
                        plots[j][i].grow();
                        plots[j][i].decrementFertilizerLevel();
                    } else {
                        plots[j][i].grow();
                    }
                    plotButtons.add(plots[j][i].getButton());
                }
            }
            if (event != 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                if (event == 1) {
                    alert.setContentText("It rained! Plot water levels increased by " + amount);
                } else if (event == 2) {
                    alert.setContentText("There was a drought! Plot water levels decreased by "
                        + amount);
                } else if (event == 3) {
                    alert.setContentText("Locusts ate your crops! " + numKilled
                        + " plots were killed");
                }
                alert.showAndWait();
            }

            farmGrid.getChildren().clear();
            farmGrid.getChildren().addAll(plotButtons);
        });

        farmGrid.setTranslateX((farmCanvas.getWidth() / 2.0)
            - ((PLOT_SIZE * farmGrid.getColumnCount()) / 2.0));
        farmGrid.setTranslateY((farmCanvas.getHeight() / 2.0)
            - ((PLOT_SIZE * farmGrid.getRowCount()) / 2.0));

        farmUIGroup.getChildren().add(moneyDisplay);
        farmUIGroup.getChildren().add(dayDisplay);
        farmUIGroup.getChildren().add(farmCanvas);
        farmUIGroup.getChildren().add(farmGrid);
        farmUIGroup.getChildren().add(toMarketButton);
        farmUIGroup.getChildren().add(inventoryLabel);
        farmUIGroup.getChildren().add(tableView);
        farmUIGroup.getChildren().add(nextDayButton);
    }

    private static void setupPlots(Player player, TableView<Map.Entry<Item, Integer>> tableView,
                                   GridPane farmGrid, Plot[][] plots) {

        for (int i = 0; i < PLOT_COLS; i++) {
            for (int j = 0; j < PLOT_ROWS; j++) {
                Random rand = new Random();
                int maturity = rand.nextInt(4) + 1; // 1..4
                int type = rand.nextInt(4); // 0..3
                Item plant = Item.values()[type];
                Plot newPlot = new Plot(plant, Maturity.values()[maturity], PLOT_SIZE);
                Button plotButton = newPlot.getButton();

                // Context Menu for determining seed type
                ContextMenu contextMenu = new ContextMenu();
                MenuItem menuItem1 = new MenuItem("MELON");
                MenuItem menuItem2 = new MenuItem("POTATO");
                MenuItem menuItem3 = new MenuItem("PUMPKIN");
                MenuItem menuItem4 = new MenuItem("WHEAT");
                contextMenu.getItems().addAll(menuItem1, menuItem2, menuItem3, menuItem4);

                // Context Menu for right-click actions
                ContextMenu contextMenu2 = new ContextMenu();
                MenuItem waterMenuItem = new MenuItem("Water Plot");
                MenuItem pesticideMenuItem = new MenuItem("Apply Pesticides");
                MenuItem fertilizerMenuItem = new MenuItem("Apply Fertilizer");
                contextMenu2.getItems().addAll(waterMenuItem,
                    pesticideMenuItem, fertilizerMenuItem);
                TextArea menuArea = new TextArea();
                menuArea.setContextMenu(contextMenu);
                TextArea menuArea2 = new TextArea();
                menuArea2.setContextMenu(contextMenu2);
                plotButton.setOnMouseClicked(e -> {

                    // Clear dead plants
                    if (newPlot.getMaturity().equals(Maturity.DEAD)
                        && e.getButton() == MouseButton.PRIMARY) {
                        System.out.println("Plant to clear: " + plant.name());
                        newPlot.harvest();
                        System.out.println(player.getInventory().getItemMap().toString());
                        tableView.getColumns().get(0).setVisible(false);
                        tableView.getColumns().get(0).setVisible(true);
                        ImageView emptyView = new ImageView(
                            new Image("file:images/empty.PNG"));
                        emptyView.setFitHeight(PLOT_SIZE);
                        emptyView.setFitWidth(PLOT_SIZE);
                        plotButton.setGraphic(emptyView);
                    }

                    // Harvesting mature plants
                    if (newPlot.getMaturity().equals(Maturity.MATURE)
                        && e.getButton() == MouseButton.PRIMARY) {
                        try {
                            int numHarvested = rand.nextInt(3
                                + newPlot.getFertilizerLevel() > 0 ? 2 : 0) + 2;
                            int remSpace = player.getInventory().getCapacity()
                                - player.getInventory().getSize();
                            if (remSpace == 0) {
                                System.out.println("Cannot harvest " + plant.name()
                                    + " because you've run out of inventory space");
                            } else {
                                player.getInventory().add(newPlot.getPlant(),
                                    Math.min(remSpace, numHarvested));
                                System.out.println("Plant to harvest: "
                                    + newPlot.getPlant().name());
                                newPlot.harvest();
                                System.out.println(player.getInventory().getItemMap().toString());
                                tableView.getColumns().get(0).setVisible(false);
                                tableView.getColumns().get(0).setVisible(true);
                                ImageView emptyView = new ImageView(
                                    new Image("file:images/empty.PNG"));
                                emptyView.setFitHeight(PLOT_SIZE);
                                emptyView.setFitWidth(PLOT_SIZE);
                                plotButton.setGraphic(emptyView);
                            }
                            System.out.println("New size: " + player.getInventory().getSize()
                                + "/" + player.getInventory().getCapacity());
                        } catch (InventoryCapacityException ex) {
                            System.out.println("Harvesting failed with error: " + ex.getMessage());
                        }
                    }

                    // Planting new seeds
                    if (newPlot.getMaturity().equals(Maturity.EMPTY)
                        && e.getButton() == MouseButton.SECONDARY) {
                        menuItem1.setOnAction(event1 -> plantAction(player, tableView, newPlot,
                            plotButton, menuItem1, Item.MELON));
                        menuItem2.setOnAction(event1 -> plantAction(player, tableView, newPlot,
                            plotButton, menuItem2, Item.POTATO));
                        menuItem3.setOnAction(event1 -> plantAction(player, tableView, newPlot,
                            plotButton, menuItem3, Item.PUMPKIN));
                        menuItem4.setOnAction(event1 -> plantAction(player, tableView, newPlot,
                            plotButton, menuItem4, Item.WHEAT));
                        plotButton.setOnContextMenuRequested(contextMenuEvent -> {
                            if (newPlot.getMaturity().equals(Maturity.EMPTY)) {
                                contextMenu.show(plotButton, contextMenuEvent.getScreenX(),
                                    contextMenuEvent.getScreenY());
                            }
                        });
                    }

                    // Water, fertilize, pesticides actions in menu
                    if (!newPlot.getMaturity().equals(Maturity.EMPTY)
                        && e.getButton() == MouseButton.SECONDARY) {
                        waterMenuItem.setOnAction(event1 -> {
                            // Water crop
                            newPlot.waterPlot();
                            System.out.println("New water level is " + newPlot.getWaterLevel());
                        });
                        pesticideMenuItem.setOnAction(event1 -> { // Apply pesticides
                            try {
                                player.getInventory().remove(Item.PESTICIDE, 1);
                                newPlot.setPlant(Item.valueOf(newPlot.getPlant().toConcat()
                                    + "_PESTICIDES"));
                                //Update table numbers
                                tableView.getColumns().get(0).setVisible(false);
                                tableView.getColumns().get(0).setVisible(true);
                                System.out.println("Pesticides applied to "
                                    + newPlot.getPlant().name());
                            } catch (InsufficientItemsException e2) {
                                System.out.println("Cannot apply pesticides. You do not have any.");
                            }
                        });
                        fertilizerMenuItem.setOnAction(event1 -> {
                            // Apply fertilizer
                            try {
                                player.getInventory().remove(Item.FERTILIZER, 1);
                                newPlot.addFertilizer(3);
                                //Update table numbers
                                tableView.getColumns().get(0).setVisible(false);
                                tableView.getColumns().get(0).setVisible(true);
                                System.out.print("Fertilizer applied to "
                                    + newPlot.getPlant().name());
                                System.out.println(". New level is "
                                    + newPlot.getFertilizerLevel());
                            } catch (InsufficientItemsException e2) {
                                System.out.println("Cannot apply fertilizer. You do not have any.");
                            }
                        });
                        plotButton.setOnContextMenuRequested(contextMenuEvent -> {
                            if (!newPlot.getMaturity().equals(Maturity.EMPTY)) {
                                contextMenu2.show(plotButton, contextMenuEvent.getScreenX(),
                                    contextMenuEvent.getScreenY());
                            }
                        });
                    }
                });
                plots[j][i] = newPlot;
                farmGrid.add(plotButton, i, j);
            }
        }
    }

    private static void plantAction(Player player, TableView<Map.Entry<Item, Integer>> tableView,
                                    Plot newPlot, Button plotButton, MenuItem menuItem, Item item) {
        try {
            System.out.println(menuItem.getText());
            // extracts seeds from an item
            player.getInventory().remove(item, 1);
            // updates inventory table
            tableView.getColumns().get(0).setVisible(false);
            tableView.getColumns().get(0).setVisible(true);
            // PLANT SEED MECHANIC CALLED
            newPlot.plantSeed(menuItem.getText());
            ImageView emptyView = new ImageView(new Image("file:images/Seed.PNG"));
            emptyView.setFitHeight(PLOT_SIZE);
            emptyView.setFitWidth(PLOT_SIZE);
            plotButton.setGraphic(emptyView);
        } catch (InsufficientItemsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void setupMarket(Stage primaryStage, Player player, Market market,
                                    Text moneyDisplay,
                                    TableView<Map.Entry<Item, Integer>> tableView,
                                    Group farmUIGroup) {


        moneyDisplay.setText("$" + player.getBalance());
        Text marketInventory = new Text("                                Inventory:");
        Map<Item, Integer> map1 = player.getInventory().getItemMap();
        TableColumn<Map.Entry<Item, Integer>, String> column1Inventory =
            new TableColumn<>("Item");
        column1Inventory.setCellValueFactory(p ->
            new SimpleObjectProperty<>(p.getValue().getKey().getDisplayName())
        );
        TableColumn<Map.Entry<Item, Integer>, String> column2Inventory =
            new TableColumn<>("Quantity");
        column2Inventory.setCellValueFactory(p ->
            new SimpleObjectProperty<>(p.getValue().getValue().toString())
        );
        ObservableList<Map.Entry<Item, Integer>> items1 =
            FXCollections.observableArrayList(map1.entrySet());
        final TableView<Map.Entry<Item, Integer>> inventoryTable = new TableView<>(items1);

        Button returnToUI = new Button("Return to Farm UI");
        returnToUI.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400),"
            + " linear-gradient(#ffef84, #f2ba44),"
            + " linear-gradient(#ffea6a, #efaa22),"
            + " linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),"
            + " linear-gradient(from 0% 0% to 15% 50%,"
            + " rgba(255,255,255,0.9),"
            + " rgba(255,255,255,0));"
            + "-fx-background-radius: 30;"
            + "-fx-background-insets: 0,1,2,3,0;"
            + "-fx-text-fill: #654b00;"
            + "-fx-font-weight: bold;"
            + "-fx-font-size: 14px;"
            + "-fx-padding: 10 20 10 20;");
        returnToUI.setOnMouseClicked(e -> {
            moneyDisplay.setText("Money: $" + player.getBalance());
            System.out.println(player.getBalance());
            tableView.getColumns().get(0).setVisible(false);
            tableView.getColumns().get(0).setVisible(true);
            primaryStage.setScene(farmUI);
            moneyDisplay.setFont(DISPLAY_FONT);
            moneyDisplay.setTranslateY(moneyDisplay.getLayoutBounds().getHeight() - 60);
            farmUIGroup.getChildren().add(moneyDisplay);
        });

        inventoryTable.getColumns().setAll(column1Inventory, column2Inventory);

        //DROP DOWN MENUS FOR INVENTORY AND MARKET
        ComboBox<Item> marketBox = new ComboBox<>();
        ComboBox<Item> inventoryBox = new ComboBox<>();
        refreshBox(inventoryBox, player.getInventory().getItemMap());
        refreshBox(marketBox, market.getItemMap());

        // POPULATE WITH AN OBSERVABLE LIST OF ITEMS IN THE MARKET USE BUY BUTTON
        Label marketStand = new Label("Items for Sale:");

        Map<Item, Integer> map2 = market.getItemMap();
        map2.replace(Item.PESTICIDE, (int) ((1 / player.getDifficulty().getMultiplier()) * 10));
        map2.replace(Item.FERTILIZER, (int) ((1 / player.getDifficulty().getMultiplier()) * 15));
        TableColumn<Map.Entry<Item, Integer>, String> column1Market = new TableColumn<>("Item");
        column1Market.setCellValueFactory(p ->
            new SimpleObjectProperty<>(p.getValue().getKey().getDisplayName())
        );
        TableColumn<Map.Entry<Item, Integer>, String> column2Market = new TableColumn<>("Price");
        column2Market.setCellValueFactory(p ->
            new SimpleObjectProperty<>(p.getValue().getValue().toString())
        );
        ObservableList<Map.Entry<Item, Integer>> items2 =
            FXCollections.observableArrayList(map2.entrySet());
        final TableView<Map.Entry<Item, Integer>> saleTable = new TableView<>(items2);

        saleTable.getColumns().setAll(column1Market, column2Market);

        // SELL BUTTON
        Button sellButton = new Button("Sell");
        sellButton.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400),"
            + " linear-gradient(#ffef84, #f2ba44),"
            + " linear-gradient(#ffea6a, #efaa22),"
            + " linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),"
            + " linear-gradient(from 0% 0% to 15% 50%,"
            + " rgba(255,255,255,0.9),"
            + " rgba(255,255,255,0));"
            + "-fx-background-radius: 30;"
            + "-fx-background-insets: 0,1,2,3,0;"
            + "-fx-text-fill: #654b00;"
            + "-fx-font-weight: bold;"
            + "-fx-font-size: 14px;"
            + "-fx-padding: 10 20 10 20;");
        sellButton.setOnMouseClicked(e -> {
            Item sellItem = inventoryBox.getValue();
            if (sellItem != null) {
                player.sellItem(sellItem, 1);
                refreshBox(inventoryBox, player.getInventory().getItemMap());
                refreshBox(marketBox, market.getItemMap());
                inventoryTable.getColumns().get(0).setVisible(false);
                inventoryTable.getColumns().get(0).setVisible(true);
                saleTable.getColumns().get(0).setVisible(false);
                saleTable.getColumns().get(0).setVisible(true);
                moneyDisplay.setText("$" + player.getBalance());
            }
        });

        // BUY BUTTON
        Button buyButton = new Button("Buy");
        buyButton.setStyle("-fx-background-color: linear-gradient(#ffd65b, #e68400),"
            + " linear-gradient(#ffef84, #f2ba44),"
            + " linear-gradient(#ffea6a, #efaa22),"
            + " linear-gradient(#ffe657 0%, #f8c202 50%, #eea10b 100%),"
            + " linear-gradient(from 0% 0% to 15% 50%,"
            + " rgba(255,255,255,0.9),"
            + " rgba(255,255,255,0));"
            + "-fx-background-radius: 30;"
            + "-fx-background-insets: 0,1,2,3,0;"
            + "-fx-text-fill: #654b00;"
            + "-fx-font-weight: bold;"
            + "-fx-font-size: 14px;"
            + "-fx-padding: 10 20 10 20;");
        buyButton.setOnMouseClicked(e -> {
            Item buyItem = marketBox.getValue();
            if (buyItem != null && !buyItem.name().contains("_")) {
                try {
                    player.buyItem(buyItem, 1);
                    refreshBox(inventoryBox, player.getInventory().getItemMap());
                    refreshBox(marketBox, market.getItemMap());
                    inventoryTable.getColumns().get(0).setVisible(false);
                    inventoryTable.getColumns().get(0).setVisible(true);
                    saleTable.getColumns().get(0).setVisible(false);
                    saleTable.getColumns().get(0).setVisible(true);
                    moneyDisplay.setText("$" + player.getBalance());
                } catch (InsufficientFundsException | InventoryCapacityException ex) {
                    System.out.println("Failed to buy item: " + ex.getMessage());
                }
            }
        });


        VBox content = new VBox(5);
        content.getChildren().addAll(returnToUI, moneyDisplay, marketInventory, inventoryTable,
            inventoryBox, sellButton, marketStand, saleTable, marketBox, buyButton);
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);

        Pane root = new Pane();
        content.getChildren().add(root);
        marketUI = new Scene(new BorderPane(scroller, null, null, null, null), HEIGHT, WIDTH);
        primaryStage.setScene(marketUI);
        primaryStage.show();
    }

    private static void refreshBox(ComboBox box, Map<Item, Integer> map) {
        map.forEach((key, value) -> {
            box.getItems().remove(key);
            box.getItems().add(key);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}