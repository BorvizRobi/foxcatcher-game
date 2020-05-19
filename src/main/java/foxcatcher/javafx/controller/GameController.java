package foxcatcher.javafx.controller;

import foxcatcher.state.FoxcatcherState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;


import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
public class GameController {

    @Inject
    private FXMLLoader fxmlLoader;

    //@Inject
    //private GameResultDao gameResultDao;

    private String player1Name;
    private String player2Name;
    private FoxcatcherState gameState;
    private IntegerProperty steps = new SimpleIntegerProperty();
    private Instant startTime;

    private  ImageView foxImage;
    private  ImageView dog1Image;
    private  ImageView dog2Image;
    private  ImageView dog3Image;
    private  ImageView dog4Image;


    @FXML
    private Label messageLabel;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Label stepsLabel;

    @FXML
    private Label stopWatchLabel;

    private Timeline stopWatchTimeline;

    @FXML
    private Button resetButton;

    @FXML
    private Button giveUpButton;

    private BooleanProperty gameOver = new SimpleBooleanProperty();

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }
    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    @FXML
    public void initialize() {

        foxImage = new ImageView(new Image(getClass().getResourceAsStream("/images/Fox.png")));
        foxImage.setFitHeight(44);
        foxImage.setFitWidth(44);

        dog1Image = new ImageView(new Image(getClass().getResourceAsStream("/images/Dog.png")));
        dog1Image.setFitHeight(44);
        dog1Image.setFitWidth(44);
        dog2Image = new ImageView(new Image(getClass().getResourceAsStream("/images/Dog.png")));
        dog2Image.setFitHeight(44);
        dog2Image.setFitWidth(44);
        dog3Image = new ImageView(new Image(getClass().getResourceAsStream("/images/Dog.png")));
        dog3Image.setFitHeight(44);
        dog3Image.setFitWidth(44);
        dog4Image = new ImageView(new Image(getClass().getResourceAsStream("/images/Dog.png")));
        dog4Image.setFitHeight(44);
        dog4Image.setFitWidth(44);

        foxImage = new ImageView(new Image(getClass().getResourceAsStream("/images/Fox.png")));
        foxImage.setFitHeight(44);
        foxImage.setFitWidth(44);


       stepsLabel.textProperty().bind(steps.asString());
        gameOver.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                log.info("Game is over");
                log.debug("Saving result to database...");
                //gameResultDao.persist(createGameResult());
                stopWatchTimeline.stop();
            }
        });
        resetGame();
       // ImageView view = (ImageView) gameGrid.getChildren().get(0);
        //view.setImage(foxImage);
        Button clicked0Button = (Button) gameGrid.getChildren().get(2);
        clicked0Button.setGraphic(foxImage);

        Button clickedButton = (Button) gameGrid.getChildren().get(57);
            clickedButton.setGraphic(dog1Image);

        Button clicked1Button = (Button) gameGrid.getChildren().get(59);
        clicked1Button.setGraphic(dog2Image);

        Button clicked2Button = (Button) gameGrid.getChildren().get(61);
        clicked2Button.setGraphic(dog3Image);

        Button clicked3Button = (Button) gameGrid.getChildren().get(63);
        clicked3Button.setGraphic(dog4Image);
    }


    private void resetGame() {
        gameState = new FoxcatcherState(FoxcatcherState.INITIAL);
        steps.set(0);
        startTime = Instant.now();
        gameOver.setValue(false);
        //displayGameState();
        createStopWatch();
        Platform.runLater(() -> messageLabel.setText(player1Name + " Vs. "+ player2Name));
    }

    /*private void displayGameState() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ImageView view = (ImageView) gameGrid.getChildren().get(i * 3 + j);
                if (view.getImage() != null) {
                    log.trace("Image({}, {}) = {}", i, j, view.getImage().getUrl());
                }
                view.setImage(cubeImages.get(gameState.getTray()[i][j].getValue()));
            }
        }
    }
    */

    public void handleClickOnTile(MouseEvent mouseEvent) {
       // int row = GridPane.getRowIndex((Node) mouseEvent.getSource());
        //int col = GridPane.getColumnIndex((Node) mouseEvent.getSource());
        //log.debug("Cube ({}, {}) is pressed", row, col);

        Button clickedButton = (Button) mouseEvent.getTarget();
        if(clickedButton.getGraphic()==dog1Image) {
            clickedButton.setGraphic(null);
        }
        if(clickedButton.getGraphic()==dog2Image) {
            clickedButton.setGraphic(null);
        }
        if(clickedButton.getGraphic()==dog3Image) {
            clickedButton.setGraphic(null);
        }
        if(clickedButton.getGraphic()==dog4Image) {
            clickedButton.setGraphic(null);
        }
        //else clickedButton.setGraphic(null);

        /*
        if (! gameState.isSolved() && gameState.canRollToEmptySpace(row, col)) {
            steps.set(steps.get() + 1);
            gameState.rollToEmptySpace(row, col);
            if (gameState.isSolved()) {
                gameOver.setValue(true);
                log.info("Player {} has solved the game in {} steps", playerName, steps.get());
                messageLabel.setText("Congratulations, " + playerName + "!");
                resetButton.setDisable(true);
                giveUpButton.setText("Finish");
            }
        }
        displayGameState();*/
    }
/*
    public void handleResetButton(ActionEvent actionEvent)  {
        log.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        log.info("Resetting game...");
        stopWatchTimeline.stop();
        resetGame();
    }

 */
/*
    public void handleGiveUpButton(ActionEvent actionEvent) throws IOException {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        log.debug("{} is pressed", buttonText);
        if (buttonText.equals("Give Up")) {
            log.info("The game has been given up");
        }
        gameOver.setValue(true);
        log.info("Loading high scores scene...");
        fxmlLoader.setLocation(getClass().getResource("/fxml/highscores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
*//*
    private GameResult createGameResult() {
        GameResult result = GameResult.builder()
                .player(playerName)
                .solved(gameState.isSolved())
                .duration(Duration.between(startTime, Instant.now()))
                .steps(steps.get())
                .build();
        return result;
    }*/

    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }


}
