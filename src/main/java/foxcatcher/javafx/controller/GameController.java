package foxcatcher.javafx.controller;

import foxcatcher.stats.PlayerStatsDao;
import foxcatcher.state.Coordinate;
import foxcatcher.state.FoxcatcherState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.*;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Vector;

@Slf4j
public class GameController {

    @Inject
    private FXMLLoader fxmlLoader;

    @Inject
    private PlayerStatsDao playerStatsDao;

    //@Inject
    //private GameResultDao gameResultDao;

    private String player1Name;
    private String player2Name;
    private FoxcatcherState gameState;
    private IntegerProperty steps = new SimpleIntegerProperty();
    private IntegerProperty turnPlayer = new SimpleIntegerProperty(1);
    private Instant startTime;
    private List<Image> images;

    private Coordinate selectedPawnCoordinate;
    private Vector<Coordinate> possiblemoveCoordinates;



    @FXML
    private Label messageLabel;

    @FXML
    private Label turnLabel;

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

    private IntegerProperty gameOver = new SimpleIntegerProperty();

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }
    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    private void advanceTurnPlayer(){
        if (turnPlayer.getValue()==1){
            turnPlayer.set(2);
        }
        else if(turnPlayer.getValue()==2){
            turnPlayer.set(1);
        }
    }

    @FXML
    public void initialize() {

        images = List.of(
                new Image(getClass().getResource("/images/Highlight.png").toExternalForm()),
                new Image(getClass().getResource("/images/Dog.png").toExternalForm()),
                new Image(getClass().getResource("/images/Fox.png").toExternalForm())
        );



       stepsLabel.textProperty().bind(steps.asString());
        gameOver.addListener((observable, oldValue, newValue) -> {

                log.info("Game is over");
                log.debug("Saving result to database...");
                stopWatchTimeline.stop();
                if (newValue.equals(1)) {
                    playerStatsDao.updatePlayerStats(player1Name, 0, 1);
                    playerStatsDao.updatePlayerStats(player2Name, 1, 0);
                }
                else if(newValue.equals(2)) {
                    playerStatsDao.updatePlayerStats(player1Name, 1, 0);
                    playerStatsDao.updatePlayerStats(player2Name, 0, 1);
                }

        });

        turnPlayer.addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(1)) {
                turnLabel.setText(player1Name + "'s turn:");
            }  else  if (newValue.equals(2)) {
                turnLabel.setText(player2Name + "'s turn:");
            }
        });

        resetGame();

    }


    private void resetGame() {
        gameState = new FoxcatcherState(FoxcatcherState.INITIAL);
        steps.set(0);
        startTime = Instant.now();
        gameOver.setValue(0);
        displayGameState();
        createStopWatch();
        Platform.runLater(() -> messageLabel.setText(player1Name + " Vs. "+ player2Name));
        Platform.runLater(() -> turnLabel.setText(player1Name + "'s turn:"));

    }

    private void displayGameState() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //ImageView view = (ImageView) gameGrid.getChildren().get(i * 3 + j);
                Button clickedButton = (Button) gameGrid.getChildren().get(i * 8 + j);

                if(gameState.getChessBoard()[i][j].getValue()!=0) {



                    ImageView imageView = new ImageView(images.get(gameState.getChessBoard()[i][j].getValue()));
                    imageView.setFitWidth(44);
                    imageView.setFitHeight(44);

                    clickedButton.setGraphic(imageView);

                    if (clickedButton.getGraphic() != null) {
                        log.trace("Image({}, {}) = {}", i, j, imageView.getImage().getUrl());
                    }
                }
                else {
                   clickedButton.setGraphic(null);
                }

                //view.setImage(cubeImages.get(gameState.getTray()[i][j].getValue()));
            }
        }

    }
    private void displayPossibleMoves() {
        if(!possiblemoveCoordinates.isEmpty()) {
            for(int i=0;i<possiblemoveCoordinates.size();i++) {
                Button button = (Button) gameGrid.getChildren().get(possiblemoveCoordinates.get(i).getX() * 8 + possiblemoveCoordinates.get(i).getY());
                ImageView imageView = new ImageView(images.get(0));
                imageView.setFitWidth(44);
                imageView.setFitHeight(44);
                button.setGraphic(imageView);
            }
    }
}

    private void undisplayPossibleMoves() {

        if (!possiblemoveCoordinates.isEmpty()) {
            for (int i = 0; i < possiblemoveCoordinates.size(); i++) {
                Button button = (Button) gameGrid.getChildren().get(possiblemoveCoordinates.get(i).getX() * 8 + possiblemoveCoordinates.get(i).getY());
                button.setGraphic(null);
            }
        }
    }

    public void handleClickOnTile(MouseEvent mouseEvent) {

        int row = GridPane.getRowIndex((Node) mouseEvent.getSource());
        int col = GridPane.getColumnIndex((Node) mouseEvent.getSource());
        log.debug("Cube ({}, {}) is pressed", row, col);


            if (selectedPawnCoordinate == null) {
                selectedPawnCoordinate = new Coordinate(row, col);
                possiblemoveCoordinates = gameState.calculatePossibleMoveCoordinates(selectedPawnCoordinate);

                if (gameState.getPawn(selectedPawnCoordinate).getValue()==turnPlayer.getValue() )displayPossibleMoves();

            } else {
                Coordinate coordinate = new Coordinate(row, col);
                if ( gameState.getPawn(selectedPawnCoordinate).getValue() == turnPlayer.getValue()  && gameState.canMovePawn(selectedPawnCoordinate, coordinate)) {
                    gameState.movePawn(selectedPawnCoordinate, coordinate);

                    selectedPawnCoordinate = null;
                    undisplayPossibleMoves();
                    possiblemoveCoordinates = null;
                    displayGameState();
                    advanceTurnPlayer();
                    steps.set(steps.get() + 1);
                } else {
                    selectedPawnCoordinate = new Coordinate(row, col);
                    undisplayPossibleMoves();
                    possiblemoveCoordinates = gameState.calculatePossibleMoveCoordinates(selectedPawnCoordinate);

                    if (gameState.getPawn(selectedPawnCoordinate).getValue() == turnPlayer.getValue())displayPossibleMoves();
                }
            }

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
        displayGameState();
    }
/*
    public void handleResetButton(ActionEvent actionEvent)  {
        log.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        log.info("Resetting game...");
        stopWatchTimeline.stop();
        resetGame();
    }

 */

    public void handleGiveUpButton(ActionEvent actionEvent) throws IOException {
        String buttonText = ((Button) actionEvent.getSource()).getText();
        log.debug("{} is pressed", buttonText);
        if (buttonText.equals("Give Up")) {
            log.info("The game has been given up");
        }
        gameOver.setValue(turnPlayer.getValue());
        log.info("Loading high scores scene...");
        fxmlLoader.setLocation(getClass().getResource("/fxml/highscores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    private void createStopWatch() {
        stopWatchTimeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {
            long millisElapsed = startTime.until(Instant.now(), ChronoUnit.MILLIS);
            stopWatchLabel.setText(DurationFormatUtils.formatDuration(millisElapsed, "HH:mm:ss"));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        stopWatchTimeline.setCycleCount(Animation.INDEFINITE);
        stopWatchTimeline.play();
    }


}
