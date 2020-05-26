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

    private Vector<String> playerNames;
    private FoxcatcherState gameState;
    private IntegerProperty steps = new SimpleIntegerProperty();
    private Instant startTime;
    private List<Image> images;

    private Coordinate selectedPawnCoordinate;
    private Vector<Coordinate> possiblemoveCoordinates;



    @FXML
    private Label messageLabel;

    @FXML
    private Label activePlayerLabel;

    @FXML
    private GridPane gameGrid;

    @FXML
    private Label stepsLabel;

    @FXML
    private Label stopWatchLabel;

    private Timeline stopWatchTimeline;

    @FXML
    private Button giveUpButton;

    private IntegerProperty gameOver = new SimpleIntegerProperty();


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
                    playerStatsDao.updatePlayerStats(playerNames.get(1), true);
                    playerStatsDao.updatePlayerStats(playerNames.get(2), false);
                }
                else if(newValue.equals(2)) {
                    playerStatsDao.updatePlayerStats(playerNames.get(1), false);
                    playerStatsDao.updatePlayerStats(playerNames.get(2), true);
                }

        });

        resetGame();

    }


    private void resetGame() {
        gameState = new FoxcatcherState();
        steps.set(0);
        startTime = Instant.now();
        gameOver.setValue(0);
        displayPawns();
        createStopWatch();
        Platform.runLater(() -> messageLabel.setText(playerNames.get(1) + " Vs. "+ playerNames.get(2)));
        Platform.runLater(() -> activePlayerLabel.setText(playerNames.get(1) + "'s turn:"));

    }

    private void displayPawns() {

        for (int i = 0; i < gameState.getDogPositions().size(); i++) {


            Button button = (Button) gameGrid.getChildren().get(gameState.getDogPositions().get(i).getX() * 8 + gameState.getDogPositions().get(i).getY());

            ImageView imageView = new ImageView(images.get(1));
            imageView.setFitWidth(42);
            imageView.setFitHeight(42);

            button.setGraphic(imageView);

            if (button.getGraphic() != null) {
                log.trace("Image({}, {}) = {}", gameState.getDogPositions().get(i).getX(), gameState.getDogPositions().get(i).getY(), imageView.getImage().getUrl());
            }


        }

        Button button = (Button) gameGrid.getChildren().get(gameState.getFoxPosition().getX() * 8 + gameState.getFoxPosition().getY());

        ImageView imageView = new ImageView(images.get(2));
        imageView.setFitWidth(42);
        imageView.setFitHeight(42);
        button.setGraphic(imageView);

        if (button.getGraphic() != null) {
            log.trace("Image({}, {}) = {}", gameState.getFoxPosition().getX(), gameState.getFoxPosition().getY(), imageView.getImage().getUrl());
        }

    }

    private void unDisplayPawns() {

        for (int i = 0; i < gameState.getDogPositions().size(); i++) {

            Button button = (Button) gameGrid.getChildren().get(gameState.getDogPositions().get(i).getX() * 8 + gameState.getDogPositions().get(i).getY());
            button.setGraphic(null);
        }

        Button button = (Button) gameGrid.getChildren().get(gameState.getFoxPosition().getX() * 8 + gameState.getFoxPosition().getY());
        button.setGraphic(null);

    }

    private void displayPossibleMoves(){
        if(!possiblemoveCoordinates.isEmpty()){

            for(int i=0;i<possiblemoveCoordinates.size();i++){

                Button button=(Button)gameGrid.getChildren().get(possiblemoveCoordinates.get(i).getX()*8+possiblemoveCoordinates.get(i).getY());
                ImageView imageView=new ImageView(images.get(0));

                imageView.setFitWidth(42);
                imageView.setFitHeight(42);
                button.setGraphic(imageView);

                if (button.getGraphic() != null) {
                    log.trace("Image({}, {}) = {}", possiblemoveCoordinates.get(i).getX(), possiblemoveCoordinates.get(i).getY(), imageView.getImage().getUrl());
                }

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


            if (selectedPawnCoordinate == null ) {
                selectedPawnCoordinate = new Coordinate(row, col);
                possiblemoveCoordinates = gameState.calculatePossibleMoveCoordinates(selectedPawnCoordinate);

                if (!gameState.isGameOwer() && gameState.getPawn(selectedPawnCoordinate).getValue()==gameState.getActivePlayer()) {
                    displayPossibleMoves();
                }

            } else {

                Coordinate coordinate = new Coordinate(row, col);

                if (!gameState.isGameOwer() &&  gameState.canMovePawn(selectedPawnCoordinate, coordinate)) {

                    unDisplayPawns();
                    undisplayPossibleMoves();

                    gameState.movePawn(selectedPawnCoordinate, coordinate);
                    displayPawns();

                    selectedPawnCoordinate = null;
                    possiblemoveCoordinates = null;

                    steps.set(steps.get() + 1);

                    setActivePlayerLabel(gameState.getActivePlayer());

                    if (gameState.isGameOwer()) {
                        handleGameOver();
                    }


                } else if(!gameState.isGameOwer() ){
                    selectedPawnCoordinate = new Coordinate(row, col);
                    undisplayPossibleMoves();
                    possiblemoveCoordinates = gameState.calculatePossibleMoveCoordinates(selectedPawnCoordinate);

                    if (gameState.getPawn(selectedPawnCoordinate).getValue() == gameState.getActivePlayer()){
                        displayPossibleMoves();
                    }
                }
            }

    }

    private void handleGameOver(){

        gameOver.setValue(gameState.whoIsTheWinner());
        log.info("Player {} has won the game in {} steps", playerNames.get(gameState.whoIsTheWinner()), steps.get());
        messageLabel.setText("Congratulations, " + playerNames.get(gameState.whoIsTheWinner()) );
        activePlayerLabel.setText("you are the winner!");
        giveUpButton.setText("Finish");

    }


    public void handleGiveUpButton(ActionEvent actionEvent)throws IOException{

        String buttonText = ((Button) actionEvent.getSource()).getText();
        log.debug("{} is pressed", buttonText);
        if (buttonText.equals("Give Up")) {
            log.info("The game has been given up");
            gameOver.setValue(gameState.getNextActivePlayer());
            loadHighScores(actionEvent);


        }
        else if(buttonText.equals("Finish")) {
            loadHighScores(actionEvent);
        }
    }

    private void loadHighScores(ActionEvent actionEvent)throws IOException{
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

    public void setPlayerNames(Vector<String> playerNames){
        this.playerNames=playerNames;
    }

    private void setActivePlayerLabel(int activePlayer){

        if (activePlayer==1) {
            activePlayerLabel.setText(playerNames.get(1) + "'s turn:");
        }  else  if (activePlayer==2) {
            activePlayerLabel.setText(playerNames.get(2) + "'s turn:");
        }
    }


}
