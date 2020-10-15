package sample;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class Main extends Application {

    private static Scene config;
    private static Scene farmUI;
    private static Scene marketUI;
    private static boolean marketRun;

    // game canvas dimensions
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Farm World");

        // -------SCENE WELCOME-------
        Group welcomeGroup = new Group();
        Scene welcome = new Scene(welcomeGroup);
        primaryStage.setScene(welcome);
        Canvas welcomeCanvas = new Canvas(WIDTH, HEIGHT);
        welcomeGroup.getChildren().add(welcomeCanvas);
        GraphicsContext gc = welcomeCanvas.getGraphicsContext2D();

        // SETS CANVAS COLOR
        Color c = Color.rgb(139, 218, 232);
        gc.setFill(c);
        gc.fillRect(0, 0, welcomeCanvas.getWidth(), welcomeCanvas.getHeight());

        // IMPORTS LOGO
        Image farmImg = new Image("file:images/FarmWorld2.png");
        gc.drawImage(farmImg, 110, 100);

        // SETS UP START BUTTON
        Button start = new Button("START");
        start.setTranslateY(welcomeCanvas.getHeight() * 0.75 + 15); // 615
        start.setTranslateX(welcomeCanvas.getWidth() / 2 - 30); // 370
        welcomeGroup.getChildren().add(start);

        // SETS UP TITLE TEXT FOR WELCOME SCREEN
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.DARKCYAN);
        gc.setLineWidth(3);
        Font theFont = Font.font("Verdana", FontWeight.BOLD, 48);
        gc.setFont(theFont);
        gc.fillText("Welcome to Farm World!", 70, 70);
        gc.strokeText("Welcome to Farm World!", 70, 70);

        // ------SCENE CONFIGURATION-------
        Group configGroup = new Group();
        config = new Scene(configGroup);
        // switch when button "start" clicked
        start.setOnMouseClicked(e -> primaryStage.setScene(config));
        Canvas configCanvas = new Canvas(WIDTH, HEIGHT);
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

        GridPane grid = configOptionsScreen(new ComboBox[] {diffBox, seedBox, seasonBox},
                configGroup, configCanvas, toUI, configurationsOfWorld, player);

        // -------SCENE FarmUI--------
        configFarmUI(primaryStage, configurationsOfWorld, toUI, player, grid, market);

        // SHOW STAGE
        primaryStage.show();
    }

    private static void configFarmUI(Stage primaryStage, FarmWorldConfigurations worldConfig,
                                     AtomicBoolean toUI, Player player, GridPane grid, Market market) {
        // HARVEST MECHANIC:
        boolean harvested = false;

        Button continueToUI = new Button("Click to Continue");
        grid.add(continueToUI, 0, 6);

        Group farmUIGroup = new Group();
        Canvas farmCanvas = new Canvas(WIDTH, HEIGHT);
        farmUI = new Scene(farmUIGroup);
        Farm farm = new Farm(0);

        Label fillEverything = new Label("Please fill in every field correctly to continue!");

        continueToUI.setOnMouseClicked(e -> {
            // GOES TO USER INTERFACE SCENE
            boolean user = toUI.getAndSet(true);
            if (user) {
                grid.add(new Label(player.getName() + " " + worldConfig.getDifficulty()
                        + " " + worldConfig.getSeed() + " "
                        + worldConfig.getSeason()), 0, 5);

                configureFarmScreen(primaryStage, farmUIGroup, worldConfig, farmCanvas, farm, player, market, openInventory(player.getInventory(), false));

                primaryStage.setScene(farmUI);
            } else {
                grid.add(fillEverything, 7, 0);
            }
        });

        boolean refreshList = false;

        if (harvested) {
            refreshList = true;
        }
    }

    /*
    Figured out how to refresh hashmap items in an ObservableList from this StackOverflow link:
    https://stackoverflow.com/questions/18618653/binding-hashmap-with-tableview-javafx
     */
    private static TableView openInventory(Inventory inventory, Boolean refreshList) {
        Map<Item, Integer> map = inventory.getItemMap();

        // use fully detailed type for Map.Entry<String, String>
        TableColumn<Map.Entry<Item, Integer>, String> column1 = new TableColumn<>("Item");
        column1.setCellValueFactory(p -> {
            return new SimpleObjectProperty<>(p.getValue().getKey().toString());
        });

        TableColumn<Map.Entry<Item, Integer>, String> column2 = new TableColumn<>("Quantity");
        column2.setCellValueFactory(p -> {
            return new SimpleObjectProperty<>(p.getValue().getValue().toString());
        });

        ObservableList<Map.Entry<Item, Integer>> items = FXCollections.observableArrayList(map.entrySet());
        final TableView<Map.Entry<Item, Integer>> table = new TableView<>(items);

        table.getColumns().setAll(column1, column2);
        table.setMaxHeight(150);
        table.setMaxWidth(150);
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
        Button nameEnter = new Button("Enter Name");
        Label nameLabel = new Label("Enter Name: ");

        // ORGANIZES ALL ATTRIBUTES IN A GRID PANE (COLUMN, ROW)
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(nameLabel, 0, 0);
        grid.add(nameEntry, 1, 0);
        grid.add(nameEnter, 2, 0);
        grid.add(new Label("Choose Difficulty: "), 0, 1);
        grid.add(boxes[0], 1, 1);
        grid.add(new Label("Choose Seed: "), 0, 2);
        grid.add(boxes[1], 1, 2);
        grid.add(new Label("Choose Season :"), 0, 3);
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
                }
                if (!invalid) {
                    break;
                }
            }
            if (invalid) {
                nameLabel.setText("Name invalid, please re-enter:");
                nameEntry.clear();
            } else {
                nameLabel.setText("Name Entered: " + nameEntry.getText());
                itsAllGood.set(true);
            }
        });

        Label addDiff = new Label("Please select a difficulty, don't leave it blank");
        Label addSeed = new Label("Please select a seed, don't leave it blank");
        Label addSeason = new Label("Please select a season, don't leave it blank");
        Label addNameMust = new Label("Please don't forget to enter a valid name!");

        enter.setOnMouseClicked(e -> {
            boolean isDifficultyEmpty = boxes[0].getValue() == null;
            boolean isSeedEmpty = boxes[1].getValue() == null;
            boolean isSeasonEmpty = boxes[2].getValue() == null;

            boolean itsOk = itsAllGood.getAndSet(true);
            if (!isDifficultyEmpty && !isSeedEmpty && !isSeasonEmpty && itsOk) {
                world.setDifficulty(boxes[0].getValue().toString().toUpperCase());
                world.setSeed(boxes[1].getValue().toString());
                world.setSeason(boxes[2].getValue().toString());
                player.init(nameEntry.getText(), world.getStartingSeeds(), world.getDifficulty());
                addDiff.setText("");
                addSeed.setText("");
                addSeason.setText("");
                addNameMust.setText("");
                toUI.getAndSet(true);
            } else {
                if (isDifficultyEmpty) {
                    grid.add(addDiff, 2, 1);
                } else if (isSeedEmpty) {
                    grid.add(addSeed, 2, 2);
                } else if (isSeasonEmpty) {
                    grid.add(addSeason, 2, 3);
                } else {
                    grid.add(addNameMust, 5, 0);
                }
            }
        });
        return grid;
    }

    private static void configureFarmScreen(Stage primaryStage, Group farmUIGroup, FarmWorldConfigurations config,
                                            Canvas farmCanvas, Farm farm, Player player, Market market, TableView tableView) {
        Text moneyDisplay = new Text("");
        Text dayDisplay = new Text("");
        Button toMarketButton = new Button("Market");
        Font displayFont = Font.font("Verdana", FontWeight.MEDIUM, 24);
        Image plotImg = new Image("file:images/empty.png");
        GridPane farmGrid = new GridPane();

        farm.setMoney(config.getStartingMoney());
        moneyDisplay.setText("Money: $" + player.getBalance());
        moneyDisplay.setFont(displayFont);
        moneyDisplay.setTranslateY(moneyDisplay.getLayoutBounds().getHeight());
        dayDisplay.setText("Day " + farm.getDay());
        dayDisplay.setFont(displayFont);
        dayDisplay.setTranslateY(dayDisplay.getLayoutBounds().getHeight());
        dayDisplay.setTranslateX(WIDTH - dayDisplay.getLayoutBounds().getWidth());
        toMarketButton.setTranslateY(moneyDisplay.getLayoutBounds().getHeight() * 2.0);
        toMarketButton.setFont(displayFont);
        Label inventoryLabel = new Label("Inventory:");
        inventoryLabel.setTranslateY(moneyDisplay.getLayoutBounds().getHeight() * 4.5);
        tableView.setTranslateY(moneyDisplay.getLayoutBounds().getHeight() * 5.0);

        toMarketButton.setOnMouseClicked(e -> {
            setupMarket(primaryStage, player, market, moneyDisplay);
        });


        int plotSize = 100;
        int plotCols = 4;
        int plotRows = 3;

        for (int i = 0; i < plotCols; i++) {
            for (int j = 0; j < plotRows; j++) {
                ImageView plotView = new ImageView(plotImg);
                plotView.setFitHeight(plotSize);
                plotView.setFitWidth(plotSize);
                farmGrid.add(plotView, i, j);
            }
        }

        farmGrid.setTranslateX((farmCanvas.getWidth() / 2)
                - (plotSize * farmGrid.getColumnCount() / 2));
        farmGrid.setTranslateY((farmCanvas.getHeight() / 2)
                - (plotSize * farmGrid.getRowCount() / 2));

        farmUIGroup.getChildren().add(moneyDisplay);
        farmUIGroup.getChildren().add(dayDisplay);
        farmUIGroup.getChildren().add(farmGrid);
        farmUIGroup.getChildren().add(farmCanvas);
        farmUIGroup.getChildren().add(toMarketButton);
        farmUIGroup.getChildren().add(inventoryLabel);
        farmUIGroup.getChildren().add(tableView);
    }

    private static void setupMarket(Stage primaryStage, Player player, Market market, Text moneyDisplay) {

        Label marketInventory = new Label("Inventory:");
        Map<Item, Integer> map1 = player.getInventory().getItemMap();
        TableColumn<Map.Entry<Item, Integer>, String> column1Inventory = new TableColumn<>("Item");
        column1Inventory.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey().toString()));
        TableColumn<Map.Entry<Item, Integer>, String> column2Inventory = new TableColumn<>("Quantity");
        column2Inventory.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().toString()));
        ObservableList<Map.Entry<Item, Integer>> items1 = FXCollections.observableArrayList(map1.entrySet());
        final TableView<Map.Entry<Item, Integer>> table1 = new TableView<>(items1);

        Button returnToUI = new Button("Return to Farm UI");
        returnToUI.setOnMouseClicked(e -> {
            primaryStage.setScene(farmUI);

        });

        table1.getColumns().setAll(column1Inventory, column2Inventory);

        //DROP DOWN MENUS FOR INVENTORY AND MARKET
        ComboBox<Item> marketBox  = new ComboBox<>();
        ComboBox<Item> inventoryBox  = new ComboBox<>();
        refreshBox(inventoryBox, marketBox, player.getInventory().getItemMap(), market.getItemMap());
        refreshBox(inventoryBox, marketBox, player.getInventory().getItemMap(), market.getItemMap());



        // POPULATE WITH AN OBSERVABLE LIST OF ITEMS IN THE MARKET
        // USE BUY BUTTON
        Label marketStand = new Label("Items for Sale:");
        Map<Item, Integer> map2 = market.getItemMap();
        TableColumn<Map.Entry<Item, Integer>, String> column1Market = new TableColumn<>("Item");
        column1Market.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getKey().toString()));
        TableColumn<Map.Entry<Item, Integer>, String> column2Market = new TableColumn<>("Price");
        column2Market.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue().toString()));
        ObservableList<Map.Entry<Item, Integer>> items2 = FXCollections.observableArrayList(map2.entrySet());
        final TableView<Map.Entry<Item, Integer>> table2 = new TableView<>(items2);

        table2.getColumns().setAll(column1Market, column2Market);

        // SELL BUTTON
        Button sellButton = new Button("Sell");
        sellButton.setOnMouseClicked(e -> {
            Item sellItem = inventoryBox.getValue();
            if (sellItem != null) {
                player.sellItem(sellItem, 1);
                System.out.println(player.getInventory().getItemMap().toString() + " | Money: $" + player.getBalance() + " | Inventory size: " + player.getInventory().getSize());
                refreshBox(inventoryBox, marketBox, player.getInventory().getItemMap(), market.getItemMap());
                table1.getColumns().get(0).setVisible(false);
                table1.getColumns().get(0).setVisible(true);
                table2.getColumns().get(0).setVisible(false);
                table2.getColumns().get(0).setVisible(true);
            }
        });


        //BUY BUTTON
        Button buyButton = new Button("Buy");
        buyButton.setOnMouseClicked(e -> {
            Item buyItem = marketBox.getValue();
            if (buyItem != null) {
                try {
                    player.buyItem(buyItem, 1);
                    System.out.println(player.getInventory().getItemMap().toString() + " Money: $" + player.getBalance() + " | Inventory size: " + player.getInventory().getSize());
                } catch (InsufficientFundsException | InventoryCapacityException ex) {
                    System.out.println("Failed to buy item: " + ex.getMessage());
                }
                refreshBox(inventoryBox, marketBox, player.getInventory().getItemMap(), market.getItemMap());
                table1.getColumns().get(0).setVisible(false);
                table1.getColumns().get(0).setVisible(true);
                table2.getColumns().get(0).setVisible(false);
                table2.getColumns().get(0).setVisible(true);
            }
        });


        VBox content = new VBox(5);
        content.getChildren().addAll(returnToUI, marketInventory, table1, inventoryBox, sellButton, marketStand, table2, marketBox, buyButton);
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);

        Pane root = new Pane();
        content.getChildren().add(root);
        marketUI = new Scene(new BorderPane(scroller, null, null, null, null), HEIGHT, WIDTH);
        primaryStage.setScene(marketUI);
        primaryStage.show();

        moneyDisplay.setText(null);
        moneyDisplay.setText("Money: $" + player.getBalance());

    }

    public static void refreshBox(ComboBox box1, ComboBox box2, Map<Item, Integer> map1, Map<Item, Integer> map2) {
        for(Map.Entry<Item, Integer> e : map1.entrySet()) {
            box1.getItems().remove(e.getKey());
            box1.getItems().add(e.getKey());
        }

        for(Map.Entry<Item, Integer> e : map2.entrySet()) {
            box2.getItems().remove(e.getKey());
            box2.getItems().add(e.getKey());
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
