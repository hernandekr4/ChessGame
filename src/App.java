// Sergio Sauceda, Taven Hathaway,  Kevin Hernandez
// Chess Game - CSCI-3331-001
// 11/8/2024
// Just launches the Game

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Instantiate Model, View, and Controller
        Model model = new Model();
        View view = new View();
        Controller controller = new Controller(model, view);

        // Set up the Scene and primary Stage
        Scene scene = new Scene(view.getBoardUI(), 600, 600);
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}