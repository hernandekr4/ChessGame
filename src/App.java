import Model.Model;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        initializeGame(primaryStage); // Call initializeGame method
    }

    public void initializeGame(Stage primaryStage) {
        // Instantiate Model, View, and Controller
        Model model = new Model();
        View view = new View(model);
        Controller controller = new Controller(model, view);

        // Set up the Scene and primary Stage
        Scene scene = new Scene(view.getRoot(), 600, 700);
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Handle restart action from the view
        view.setRestartAction(() -> initializeGame(primaryStage));
        
        // Handle quit action from the view
        view.setQuitAction(() -> primaryStage.close());
    }

    public static void main(String[] args) {
        launch(args);
    }
}