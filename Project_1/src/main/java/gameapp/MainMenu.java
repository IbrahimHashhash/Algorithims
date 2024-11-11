package gameapp;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenu  {
    private final Root root;
    private final Button[] buttons;
    private final Scene scene;
    private final VBox vBox =CustomLayOuts.vBox();

    public MainMenu(Root root) {
        this.root = root;
        this.buttons = createButtons();
        this.scene = new Scene(root, 600, 400);
        setupBackground();
        setupButtons();
    }

    private void setupBackground() {
        Image backgroundImage = new Image(getClass().getResource("/img/bg.gif").toExternalForm());
        ImageView imageView = new ImageView(backgroundImage);
        imageView.setOpacity(0.55);
        imageView.fitWidthProperty().bind(scene.widthProperty());
        imageView.fitHeightProperty().bind(scene.heightProperty());
        root.getChildren().add(0, imageView);
    }

    private Button[] createButtons() {
        Button[] buttons = new Button[4];
        buttons[0] = new Button("Start");
        buttons[1] = new Button("Load");
        buttons[2] = new Button("Help");
        buttons[3] = new Button("Exit");
        return buttons;
    }

    private void setupButtons() {
        root.getVbox().getChildren().addAll(buttons);

        root.getBack().setOnAction(e -> {
            root.getVbox().getChildren().clear();
            root.getVbox().getChildren().addAll(buttons);
        });

        buttons[0].setOnAction(e -> root.start());
        buttons[1].setOnAction(e -> Mode.load());
        buttons[2].setOnAction(e -> root.help());
    }
    public Button[] getButtons(){
        return buttons;
    }

    public void setupExitButton(Stage primaryStage) {
        buttons[3].setOnAction(e -> primaryStage.close());
    }
    public Scene getScene() {
        return scene;
    }
}
