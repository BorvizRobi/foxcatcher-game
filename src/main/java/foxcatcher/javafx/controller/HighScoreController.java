package foxcatcher.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import foxcatcher.stats.PlayerStats;
import foxcatcher.stats.PlayerStatsDao;

import javax.inject.Inject;
import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

@Slf4j
public class HighScoreController {

    @Inject
    private FXMLLoader fxmlLoader;

    @Inject
    private PlayerStatsDao playerStatsDao;

    @FXML
    private TableView<PlayerStats> highScoreTable;

    @FXML
    private TableColumn<PlayerStats, String> player;

    @FXML
    private TableColumn<PlayerStats, Integer> wins;

    @FXML
    private TableColumn<PlayerStats, Duration> losses;

    @FXML
    private TableColumn<PlayerStats, ZonedDateTime> created;

    @FXML
    private void initialize() {
        log.debug("Loading high scores...");
        List<PlayerStats> highScoreList = playerStatsDao.findBest(22);

        player.setCellValueFactory(new PropertyValueFactory<>("player"));
        wins.setCellValueFactory(new PropertyValueFactory<>("wins"));
        losses.setCellValueFactory(new PropertyValueFactory<>("losses"));
        created.setCellValueFactory(new PropertyValueFactory<>("created"));


        created.setCellFactory(column -> {
            TableCell<PlayerStats, ZonedDateTime> cell = new TableCell<PlayerStats, ZonedDateTime>() {
                private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
                @Override
                protected void updateItem(ZonedDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty) {
                        setText(null);
                    } else {
                        setText(item.format(formatter));
                    }
                }
            };
            return cell;
        });

        ObservableList<PlayerStats> observableResult = FXCollections.observableArrayList();
        observableResult.addAll(highScoreList);

        highScoreTable.setItems(observableResult);
    }

    public void handleRestartButton(ActionEvent actionEvent) throws IOException {
        log.debug("{} is pressed", ((Button) actionEvent.getSource()).getText());
        log.info("Loading launch scene...");
        fxmlLoader.setLocation(getClass().getResource("/fxml/launch.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
