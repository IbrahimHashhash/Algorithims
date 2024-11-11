package gameapp;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import main.Computer;

import java.util.Random;

public class Casual {

    public static void generateRandomInput(int numCoins, int mode) {
        FlowPane flowPane = CustomLayOuts.fBox();
        int[] arr = new int[numCoins];
        Main.getStartPane().getVbox().getChildren().clear();
        Button start = new Button("Start");
        Main.getStartPane().getTitle().setText(null);
        Main.getStartPane().getVbox().getChildren().addAll(Main.getStartPane().getTitle(), flowPane, start, Main.getStartPane().getBack());
        populateCoins(numCoins, flowPane, start, Main.getStartPane(),arr);
        start.setOnAction(e -> {
            if (mode == 0) {
                PlayerSetup playerSetup = new PlayerSetup(flowPane);
            } else if (mode == 1) {
                ComputerMode computer = new ComputerMode(arr,flowPane);
            }
        });
    }

    private static void populateCoins(int numCoins, FlowPane flowPane, Button start, Root sp,int[] arr) {
        Label label = new Label(numCoins + " Coins");
        label.setStyle("-fx-font-size: 28px;-fx-text-fill: rgb(197, 158, 1);");
        Random random = new Random();

        for (int i = 0; i < numCoins; i++) {
            int coinVal = random.nextInt(50) * 2 + 1;
            flowPane.getChildren().add(new CoinLabel(String.valueOf(coinVal)));
            arr[i] = coinVal;
        }
        sp.getVbox().getChildren().add(2, label);
        sp.getTitle().setText("You are ready to begin");
        start.setStyle("-fx-border-color: lightGreen;-fx-background-color: lightGreen;-fx-text-fill: white;");
    }
}
