package foxcatcher.javafx.controller;

import foxcatcher.state.Coordinate;
import foxcatcher.state.FoxcatcherState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DurationFormatUtils;


import javax.inject.Inject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Vector;

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

    private List<Image> pawnImages;
    private  Image foxImage;
    private  Image dogImage;

    private Coordinate selectedPawnCoordinate;
    private Vector<Coordinate> possiblemoveCoordinates;



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

        pawnImages = List.of(
                new Image(getClass().getResource("/images/Dogimage.png").toExternalForm()),
                new Image(getClass().getResource("/images/Dogimage.png").toExternalForm()),
                new Image(getClass().getResource("/images/Foximage.png").toExternalForm())
        );

        foxImage = new Image(getClass().getResourceAsStream("/images/Foximage.png"));

       // foxImage.setFitWidth(44);

        dogImage = new Image(getClass().getResourceAsStream("/images/Dogimage.png"));
        //dog1Image.setFitHeight(44);
        //dog1Image.setFitWidth(44);





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
        displayGameState();
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

    private void displayGameState() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                //ImageView view = (ImageView) gameGrid.getChildren().get(i * 3 + j);
                Button clickedButton = (Button) gameGrid.getChildren().get(i * 8 + j);
                if(gameState.chessBoard.getTile(i,j).getPawn().getValue()!=0) {



                    ImageView imageView = new ImageView(pawnImages.get(gameState.chessBoard.getTile(i, j).getPawn().getValue()));
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


    public void handleClickOnTile(MouseEvent mouseEvent) {

        int row = GridPane.getRowIndex((Node) mouseEvent.getSource());
        int col = GridPane.getColumnIndex((Node) mouseEvent.getSource());
        log.debug("Cube ({}, {}) is pressed", row, col);

        if(selectedPawnCoordinate==null) {
            selectedPawnCoordinate=new Coordinate(row,col);
            possiblemoveCoordinates=gameState.calculatePossibleMoveCoordinates(selectedPawnCoordinate);
        }
        else{
            Coordinate coordinate = new Coordinate(row,col);
            if(possiblemoveCoordinates.contains(coordinate)) {
                gameState.movePawn(selectedPawnCoordinate, coordinate);
                displayGameState();
                selectedPawnCoordinate = null;
                possiblemoveCoordinates = null;
            }
            else{
                selectedPawnCoordinate=new Coordinate(row,col);
                possiblemoveCoordinates=gameState.calculatePossibleMoveCoordinates(selectedPawnCoordinate);
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
