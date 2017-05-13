package app;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        mainStage = primaryStage;

        Parent root = App.getInstance().load("/auth.fxml");

        primaryStage.setTitle("BCI");
        primaryStage.setScene(new Scene(root, App.Dimension.WIDTH.getSize(), App.Dimension.HEIGHT.getSize()));
        primaryStage.show();
    }

    public static void setTitle(String title) {
        mainStage.setTitle(title);
    }

    public static void setScene(Parent view) {
        mainStage.setScene(new Scene(view, App.Dimension.WIDTH.getSize(), App.Dimension.HEIGHT.getSize()));
    }

    public static void show() {
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
