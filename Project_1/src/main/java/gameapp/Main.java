package gameapp;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    private static final Root ROOT = new Root();
    static MainMenu mainMenu = new MainMenu(ROOT);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(mainMenu.getScene());
        mainMenu.setupExitButton(primaryStage);
        mainMenu.getScene().getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        primaryStage.show();
        primaryStage.setMaximized(true);
    }

    public static Root getStartPane() {
        return ROOT;
    }
}
