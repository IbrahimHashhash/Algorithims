package gameapp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
public class Manual {
    public static IntegerProperty inputCount = new SimpleIntegerProperty(0);

    public static void startManualInput(int numCoins, Root sp, IntegerProperty counter,int mode) {
        Button start = new Button("Start Game");
        start.setId("start-game");
        start.setDisable(true);

        inputCount.set(0);
        HBox hBox = CustomLayOuts.hBox();
        hBox.setSpacing(10);
        Button btn = new Button("Add");
        btn.setStyle("-fx-pref-width: 50px; -fx-pref-height: 50px;");
        TextField tf = new TextField();
        tf.setStyle("-fx-pref-width: 150px; -fx-pref-height: 50px;");
        tf.setAlignment(Pos.CENTER);

        sp.getTitle().setText("Enter coin values");
        hBox.getChildren().addAll(tf, btn);
        FlowPane labelBox = CustomLayOuts.fBox();
        sp.getVbox().getChildren().clear();
        int[] coins = new int[numCoins];
        Label label1 = new Label(String.valueOf(inputCount.get()));
        label1.setStyle("-fx-font-size: 40px; -fx-text-fill: Green;");
        labelBox.getChildren().addAll(label1);

        start.setOnAction(e -> {
            if(mode==0){
                PlayerSetup playerSetup = new PlayerSetup(labelBox); // HBox coinBox, Root root,String name1,String name2
            }else {
                ComputerMode computer = new ComputerMode(coins,labelBox);
            }
        });
        sp.getVbox().getChildren().addAll(sp.getTitle(), hBox, label1, labelBox, start, sp.getBack());

        updateStartButtonOpacity(start, inputCount, counter, tf, btn);
        start.setStyle("-fx-border-color: rgba(42, 79, 139,1);-fx-background-color: rgb(224, 242, 249);-fx-text-fill: rgba(42, 79, 139, 1);-fx-opacity: 0.4;");

        addInputCountListener(label1, counter, tf, btn, start);
        addButtonActionListener(coins, tf, btn, counter, labelBox);
        validateInput(tf);
    }
    private static void validateInput(TextField tf){
        tf.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*")) {
                return change;
            }
            return null;
        }));
    }
    private static void addInputCountListener(Label label1, IntegerProperty counter, TextField tf, Button btn, Button start) {
        inputCount.addListener((obs, oldVal, newVal) -> {
            label1.setText(String.valueOf(newVal));
            updateStartButtonOpacity(start, inputCount, counter, tf, btn);
        });
    }

    private static void updateStartButtonOpacity(Button start, IntegerProperty inputCount, IntegerProperty counter, TextField tf, Button btn) {
        if (inputCount.get() == counter.get()) {
            start.setDisable(false);
            tf.setStyle("-fx-pref-width: 150px; -fx-pref-height: 50px; -fx-opacity: 0.2;");
            btn.setStyle("-fx-pref-width: 150px; -fx-pref-height: 50px; -fx-opacity: 0.2;");
            start.setStyle("-fx-background-color: lightGreen;-fx-border-color:lightGreen;-fx-text-fill:white;");
            tf.setEditable(false);
            btn.setDisable(true);
        } else {
            start.setStyle("-fx-border-color: rgba(42, 79, 139,1);-fx-background-color: rgb(224, 242, 249);-fx-text-fill: rgba(42, 79, 139, 1);-fx-opacity: 0.4;");
            start.setDisable(true);
            btn.setStyle("-fx-pref-width: 150px; -fx-pref-height: 50px;");
            tf.setStyle("-fx-pref-width: 150px; -fx-pref-height: 50px;");
            tf.setEditable(true);
            btn.setDisable(false);
        }
    }

    private static void addButtonActionListener(int[] coins, TextField tf, Button btn, IntegerProperty counter, FlowPane labelBox) {
        btn.setOnAction(e -> {
            if (inputCount.get() < counter.get() && !tf.getText().isEmpty()) {
                coins[inputCount.get()] = Integer.parseInt(tf.getText());
                CoinLabel newLabel = new CoinLabel(tf.getText(), labelBox, coins, inputCount.get());
                labelBox.getChildren().add(newLabel);
                inputCount.set(inputCount.get() + 1);
                tf.clear();
            }
        });
    }
}
