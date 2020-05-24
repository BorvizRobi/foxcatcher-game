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
    private Button giveUpButton;

    private IntegerProperty gameOver = new SimpleIntegerProperty();


    public void setPlayerNames(Vector<String> playerNames){this.playerNames=playerNames; }

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
                    playerStatsDao.updatePlayerStats(playerNames.get(1), 1, 0);
                    playerStatsDao.updatePlayerStats(playerNames.get(2), 0, 1);
                }
                else if(newValue.equals(2)) {
                    playerStatsDao.updatePlayerStats(playerNames.get(1), 0, 1);
                    playerStatsDao.updatePlayerStats(playerNames.get(2), 1, 0);
                }

        });

        turnPlayer.addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(1)) {
                turnLabel.setText(playerNames.get(1) + "'s turn:");
            }  else  if (newValue.equals(2)) {
                turnLabel.setText(playerNames.get(2) + "'s turn:");
            }
        });

        resetGame();

    }


    private void resetGame() {
        gameState = new FoxcatcherState(FoxcatcherState.INITIAL);
        steps.set(0);
        startTime = Instant.now();
        gameOver.setValue(0);
        displayPawns();
        createStopWatch();
        Platform.runLater(() -> messageLabel.setText(playerNames.get(1) + " Vs. "+ playerNames.get(2)));
        Platform.runLater(() -> turnLabel.setText(playerNames.get(1) + "'s turn:"));

    }
/*
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
    */
    private void displayPawns() {


        for (int i = 0; i < gameState.getDogPositions().size(); i++) {

            //ImageView view = (ImageView) gameGrid.getChildren().get(i * 3 + j);
            Button button = (Button) gameGrid.getChildren().get(gameState.getDogPositions().get(i).getX() * 8 + gameState.getDogPositions().get(i).getY());

            ImageView imageView = new ImageView(images.get(1));
            imageView.setFitWidth(44);
            imageView.setFitHeight(44);

            button.setGraphic(imageView);

            if (button.getGraphic() != null) {
                log.trace("Image({}, {}) = {}", gameState.getDogPositions().get(i).getX(), gameState.getDogPositions().get(i).getY(), imageView.getImage().getUrl());
            }


        }

        Button button = (Button) gameGrid.getChildren().get(gameState.getFoxPosition().getX() * 8 + gameState.getFoxPosition().getY());

        ImageView imageView = new ImageView(images.get(2));
        imageView.setFitWidth(44);
        imageView.setFitHeight(44);
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

                if (!gameState.isGameOwer() && gameState.getPawn(selectedPawnCoordinate).getValue()==turnPlayer.getValue() )displayPossibleMoves();

            } else {
                Coordinate coordinate = new Coordinate(row, col);
                if (!gameState.isGameOwer() && gameState.getPawn(selectedPawnCoordinate).getValue() == turnPlayer.getValue()  && gameState.canMovePawn(selectedPawnCoordinate, coordinate)) {

                    unDisplayPawns();
                    undisplayPossibleMoves();

                    gameState.movePawn(selectedPawnCoordinate, coordinate);
                    displayPawns();

                    selectedPawnCoordinate = null;
                    possiblemoveCoordinates = null;
                    //displayGameState();
                    steps.set(steps.get() + 1);

                    if (gameState.isGameOwer()) {
                        gameOver.setValue(gameState.whoIsTheWinner());
                        log.info("Player {} has won the game in {} steps", playerNames.get(gameState.whoIsTheWinner()), steps.get());
                        messageLabel.setText("Congratulations, " + playerNames.get(gameState.whoIsTheWinner()) + " you are the winner!");
                        turnLabel.setText(null);
                        giveUpButton.setText("Finish");
                    }
                    advanceTurnPlayer();

                } else if(!gameState.isGameOwer()){
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
*/
    public void loadHighscores(ActionEvent actionEvent)throws IOException{
        log.info("Loading high scores scene...");
        fxmlLoader.setLocation(getClass().getResource("/fxml/highscores.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void handleGiveUpButton(ActionEvent actionEvent)throws IOException{

        String buttonText = ((Button) actionEvent.getSource()).getText();
        log.debug("{} is pressed", buttonText);
        if (buttonText.equals("Give Up")) {
            log.info("The game has been given up");
            advanceTurnPlayer();
            gameOver.setValue(turnPlayer.getValue());
            loadHighscores(actionEvent);


        }
        else if(buttonText.equals("Finish")) {
            loadHighscores(actionEvent);
        }
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
