package gameapp;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.geometry.Insets;
public class DPTable {
    GridPane gp = new GridPane();

    public DPTable(int[][] dp) {
        Stage stage = new Stage();

        // Create ScrollPane and set GridPane as its content
        ScrollPane scrollPane = new ScrollPane(gp);
        scrollPane.setFitToWidth(true);   // Fit GridPane width to ScrollPane
        scrollPane.setFitToHeight(true);  // Fit GridPane height to ScrollPane

        Scene scene = new Scene(scrollPane, 600, 600);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());

        buildTP(dp);  // Build the table

        stage.setScene(scene);
        stage.show();
    }

    public void buildTP(int[][] dp) {
        gp.setAlignment(Pos.CENTER);
        gp.setVgap(10);
        gp.setHgap(10);

        // Add padding to create space between GridPane and ScrollPane borders
        gp.setPadding(new Insets(20));  // Adjust this value to control the space
        gp.setStyle("-fx-background-color: rgb(224, 242, 249);;");
        // Populate GridPane with data
        for(int i = 0; i < dp.length; i++) {
            for(int j = 0; j < dp[i].length; j++) {
                CoinLabel coinLabel = new CoinLabel(dp[i][j] + " ");
                coinLabel.setStyle("-fx-font-size: 14px;-fx-background-color:darkorange;-fx-border-color:yellow;-fx-text-fill:white;");
                coinLabel.setDisable(true);
                gp.add(coinLabel, j, i);
            }
        }
    }
}
