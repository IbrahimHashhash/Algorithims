package gameapp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Mode {
    private static IntegerProperty counter = new SimpleIntegerProperty(2);
    private static Label label = new Label(String.valueOf(counter.get()));
    private static final Root sp = Main.getStartPane();

    public static HBox turns(int mode) {
        counter.set(2);
        sp.getTitle().setText("Specify Number Of Coins");
        HBox hBox = CustomLayOuts.hBox();
        Button inc = new Button("+");
        Button dec = new Button("-");
        hBox.setSpacing(20);
        VBox.setMargin(hBox, new Insets(0, 0, 20, 0));
        inc.setStyle("-fx-pref-width: 50px; -fx-pref-height: 50px;");
        dec.setStyle("-fx-pref-width: 50px; -fx-pref-height: 50px;");

        counter.addListener((obs, oldVal, newVal) -> label.setText(String.valueOf(newVal)));

        inc.setOnAction(e -> {
            if (counter.get() < 100) {
                counter.set(counter.get() + 2);
            }
        });
        dec.setOnAction(e -> {
            if (counter.get() > 2) {
                counter.set(counter.get() - 2);
            }
        });

        Button submit = new Button("Confirm");
        submit.setOnAction(e -> submit(counter.get(),mode));

        hBox.getChildren().addAll(dec, label, inc);
        sp.getVbox().getChildren().clear();
        sp.getVbox().getChildren().addAll(sp.getTitle(), hBox, submit, sp.getBack());

        return hBox;
    }

    public static void submit(int num,int mode) {
        sp.getTitle().setText("Choose Mode");
        Button manualButton = new Button("Manual");
        Button randomButton = new Button("Random");
        Button backButton = new Button("Back");

        manualButton.setOnAction(e -> Manual.startManualInput(num, sp, counter,mode));
        randomButton.setOnAction(e -> Casual.generateRandomInput(num,mode));
        backButton.setOnAction(e -> sp.start());

        sp.getVbox().getChildren().clear();
        sp.getVbox().getChildren().add(sp.getTitle());
        sp.getVbox().getChildren().addAll(manualButton, randomButton, backButton);
    }

    public static void load() {
        Load.loadGame();
    }
}
