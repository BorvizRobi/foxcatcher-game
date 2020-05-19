package foxcatcher.javafx;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

@Slf4j
public class FoxcatcherApplication extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        log.info("Starting application...");
        //context.init();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/launch.fxml"));

        primaryStage.setTitle("Foxcatcher Game");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
