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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;


public class Main extends Application {

    private Scene welcome;
    private Scene config;

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
        Image farm = new Image("file:images/FarmWorld2.png");
        gc.drawImage(farm, 110, 100);
        // SETS UP START BUTTON
        Button start = new Button("START");
        start.setTranslateY(615);
        start.setTranslateX(370);
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
        grid.add(diffBox, 1, 1);
        grid.add(new Label("Choose Seed: "), 0, 2);
        grid.add(seedBox, 1, 2);
        grid.add(new Label("Choose Season :"), 0, 3);
        grid.add(seasonBox, 1, 3);
        grid.add(enter, 0, 4);

        configGroup.getChildren().add(configCanvas);
        configGroup.getChildren().add(grid);

        AtomicBoolean itsAllGood = new AtomicBoolean(false);
        AtomicBoolean toUI = new AtomicBoolean(false);

        nameEnter.setOnMouseClicked(e -> {
            boolean invalid = true;
            char[] a = nameEntry.getText().toCharArray();
            for (char letter: a) {
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
        // WHEN THE ENTER BUTTON IS CLICKED,
        // THE NAME ENTERED IS SET TO THE NAME OF THE PLAYER OBJECT
        // DIFFICULTY, SEED, AND SEASON ARE SET TO THEIR CORRESPONDING
        // FIELDS IN THE CONFIGURATIONS OF WORLD OBJECT

        Label addDiff = new Label("Please select a difficulty, don't leave it blank");
        Label addSeed = new Label("Please select a seed, don't leave it blank");
        Label addSeason = new Label("Please select a season, don't leave it blank");
        Label addNameMust = new Label("Please don't forget to enter a valid name!");

        enter.setOnMouseClicked(e -> {
            boolean isDifficultyEmpty = diffBox.getValue() == null;
            boolean isSeedEmpty = seedBox.getValue() == null;
            boolean isSeasonEmpty = seasonBox.getValue() == null;

            boolean itsOk = itsAllGood.getAndSet(true);
            if (!isDifficultyEmpty && !isSeedEmpty && !isSeasonEmpty && itsOk) {
                configurationsOfWorld.setDifficulty(diffBox.getValue().toString());
                configurationsOfWorld.setSeed(seedBox.getValue().toString());
                configurationsOfWorld.setSeason(seasonBox.getValue().toString());
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

            // TESTING OUTPUT
            grid.add(new Label(player.getName() + " " + configurationsOfWorld.getDifficulty()
                    + " " + configurationsOfWorld.getSeed() + " "
                    + configurationsOfWorld.getSeason()), 0, 5);
        });

        Button continueToUI = new Button("Click to Continue");
        grid.add(continueToUI, 0, 6);

        Group userInterface = new Group();
        Scene userInterfaceScene = new Scene(userInterface);

        Label fillEverything = new Label("Please fill in every field correctly to continue!");

        continueToUI.setOnMouseClicked(e -> {
            // GOES TO USER INTERFACE SCENE
            boolean user = toUI.getAndSet(true);
            if (user) {
                primaryStage.setScene(userInterfaceScene);
                fillEverything.setText("");
            } else {
                grid.add(fillEverything, 7, 0);
            }
        });

        // SETS UP USER INTERFACE CANVAS
        Canvas userInterfaceCanvas = new Canvas(width, height);
        userInterface.getChildren().add(userInterfaceCanvas);

        // SHOW STAGE
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
