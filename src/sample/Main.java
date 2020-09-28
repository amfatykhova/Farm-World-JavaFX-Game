package sample;

import javafx.application.Application;
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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;


public class Main extends Application {

    private Scene welcome;
    private Scene config;
    private Scene farmUI;

    // game canvas dimensions
    private static int width = 800;
    private static int height = 800;

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Farm World");

        // -------SCENE WELCOME-------
        Group welcomeGroup = new Group();
        welcome = new Scene(welcomeGroup);
        primaryStage.setScene(welcome);
        Canvas welcomeCanvas = new Canvas(width, height);
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
        start.setOnMouseClicked(e -> {
            primaryStage.setScene(config);
        });
        Canvas configCanvas = new Canvas(width, height);
        // CREATES A PLAYER OBJECT
        Player player = new Player();
        // CREATES A NEW FARM WORLD CONFIGURATIONS OBJECT
        FarmWorldConfigurations configurationsOfWorld = new FarmWorldConfigurations();
        // CREATES DROP DOWN MENU FOR DIFFICULTY
        ObservableList<String> difficultyOptions =
                FXCollections.observableArrayList(
                        "Easy",
                        "Medium",
                        "Hard"
                );
        final ComboBox diffBox = new ComboBox(difficultyOptions);
        // CREATES DROP DOWN MENU FOR SEED
        ObservableList<String> seedOptions =
                FXCollections.observableArrayList(
                        "Barrow Hills",
                        "Dessert Oasis",
                        "Rolling Plains"
                );
        final ComboBox seedBox = new ComboBox(seedOptions);
        // CREATES DROP DOWN MENU FOR SEASON
        ObservableList<String> seasonOptions =
                FXCollections.observableArrayList(
                        "Winter",
                        "Spring",
                        "Summer",
                        "Fall"
                );
        final ComboBox seasonBox = new ComboBox(seasonOptions);

        AtomicBoolean toUI = new AtomicBoolean(false);

        GridPane grid = configOptionsScreen(new ComboBox[] {diffBox, seedBox, seasonBox},
                configGroup, configCanvas, toUI, configurationsOfWorld, player);

        // -------SCENE FarmUI--------
        Button continueToUI = new Button("Click to Continue");
        grid.add(continueToUI, 0, 6);

        Group farmUIGroup = new Group();
        Canvas farmCanvas = new Canvas(width, height);
        farmUI = new Scene(farmUIGroup);
        Farm farm = new Farm(0);
        Text moneyDisplay = new Text("");
        Text dayDisplay = new Text("");

        Label fillEverything = new Label("Please fill in every field correctly to continue!");

        continueToUI.setOnMouseClicked(e -> {
            // GOES TO USER INTERFACE SCENE
            boolean user = toUI.getAndSet(true);
            if (user) {
                grid.add(new Label(player.getName() + " " + configurationsOfWorld.getDifficulty()
                        + " " + configurationsOfWorld.getSeed() + " "
                        + configurationsOfWorld.getSeason()), 0, 5);

                configureFarmScreen(farmUIGroup, configurationsOfWorld, moneyDisplay,
                        dayDisplay, farmCanvas, farm);

                primaryStage.setScene(farmUI);
            } else {
                grid.add(fillEverything, 7, 0);
            }
        });

        // SHOW STAGE
        primaryStage.show();
    }

    private static GridPane configOptionsScreen(ComboBox[] boxes, Group configGroup,
                                                Canvas configCanvas, AtomicBoolean toUI,
                                                FarmWorldConfigurations world, Player player) {
        // CREATES TEXTFIELD AND BUTTON FOR NAME ENTRY
        TextField nameEntry = new TextField();
        // CREATES BUTTON TO ENTER WORLD SPECIFICATIONS
        Button enter = new Button("Enter Game Configurations");
        Button nameEnter = new Button("Enter Name");
        Label nameLabel = new Label("Enter Name: ");

        // ORGANIZES ALL ATTRIBUTES IN A GRID PANE (COLUMN, ROW)
        GridPane grid = new GridPane();
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
                world.setDifficulty(boxes[0].getValue().toString());
                world.setSeed(boxes[1].getValue().toString());
                world.setSeason(boxes[2].getValue().toString());
                player.setName(nameEntry.getText());
                addDiff.setText("");
                addSeed.setText("");
                addSeason.setText("");
                addNameMust.setText("");
                boolean user = toUI.getAndSet(true);
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

    private static void configureFarmScreen(Group farmUIGroup, FarmWorldConfigurations config,
                                            Text moneyDisplay, Text dayDisplay, Canvas farmCanvas,
                                            Farm farm) {
        int startingMoney = 0;
        switch (config.getDifficulty()) {
        case "Medium":
            startingMoney = 750;
            break;
        case "Hard":
            startingMoney = 500;
            break;
        default:
            startingMoney = 1000;
        }
        farm.setMoney(startingMoney);
        moneyDisplay.setText("Money: $" + farm.getMoney());
        Font displayFont = Font.font("Verdana", FontWeight.MEDIUM, 24);
        moneyDisplay.setFont(displayFont);
        moneyDisplay.setTranslateY(moneyDisplay.getLayoutBounds().getHeight());
        dayDisplay.setText("Day " + farm.getDay());
        dayDisplay.setFont(displayFont);
        dayDisplay.setTranslateY(dayDisplay.getLayoutBounds().getHeight());
        dayDisplay.setTranslateX(width - dayDisplay.getLayoutBounds().getWidth());

        GraphicsContext gc2 = farmCanvas.getGraphicsContext2D();
        Image plotImg = new Image("file:images/SamplePlot.png");
        GridPane farmGrid = new GridPane();

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
    }


    public static void main(String[] args) {
        launch(args);
    }
}