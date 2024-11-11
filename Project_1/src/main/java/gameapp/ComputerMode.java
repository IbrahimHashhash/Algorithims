package gameapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import main.Computer;
import main.Dynamic;

public class ComputerMode {
    private static final String COIN_SELECTED_STYLE1 = "-fx-background-color: red; -fx-border-color: pink; -fx-text-fill: pink;";
    private static final String COIN_SELECTED_STYLE2 = "-fx-background-color: Green; -fx-border-color: lightGreen; -fx-text-fill: lightGreen;";
    private static final String COIN_SELECTED_STYLE11 = "-fx-background-color: red; -fx-border-color: pink; -fx-text-fill: pink;-fx-font-size:14px;-fx-pref-height:40px;-fx-pref-width:40px;";
    private static final String COIN_SELECTED_STYLE22 = "-fx-background-color: Green; -fx-border-color: lightGreen; -fx-text-fill: lightGreen;-fx-font-size:14px;-fx-pref-height:40px;-fx-pref-width:40px;";

    private Dynamic dynamic;
    private Computer comp1;
    private Computer comp2;
    private int start;
    private int moves = 0; // Track the total moves made by both players
    private int end;
    private int turn1;
    private int turn2;
    private boolean isComp1Turn = true; // Track which player's turn it is
    private int[] arr;
    private PlayerPane p1 = new PlayerPane("player1");
    private PlayerPane p2 = new PlayerPane("player2");
    private FlowPane coinLabel;
    private Button move = new Button("Next Move");
    private Button dpTable = new Button("DP Table");

    public ComputerMode(int[] arr, FlowPane coinLabel) {
        this.arr = arr;
        this.coinLabel = coinLabel;
        dynamic = new Dynamic(arr);
        end = coinLabel.getChildren().size() - 1;
        Main.getStartPane().setCenter(coinLabel);

        initializeGame();
        initializeButtons();
        initializeLayout();
    }

    private void initializeGame() {
        // Get the optimized turn order from the dynamic programming logic
        int[] turns = dynamic.getTurns();
        int halfLength = turns.length / 2;

        // Allocate turns1 and turns2 arrays for comp1 and comp2
        int[] turns1 = new int[halfLength];
        int[] turns2 = new int[halfLength];

        // Copy the first half of the optimized turns array for comp1's turns
        System.arraycopy(turns, 0, turns1, 0, halfLength);

        // Copy the second half in reverse order for comp2's turns
        for (int i = 0; i < halfLength; i++) {
            turns2[i] = turns[turns.length - 1 - i];
        }

        // Initialize the Computer objects with their respective turn arrays
        this.comp1 = new Computer("Player1", turns1);
        this.comp2 = new Computer("Player2", turns2);
    }

    private void initializeButtons() {
        move.setOnAction(e -> handleMove());
        dpTable.setOnAction(e -> new DPTable(dynamic.getDpTable()));
    }

    private void handleMove() {
        if (moves < arr.length) { // Continue as long as there are coins to pick
            int endCoin = Integer.parseInt(((CoinLabel) coinLabel.getChildren().get(end)).getText());
            int startCoin = Integer.parseInt(((CoinLabel) coinLabel.getChildren().get(start)).getText());

            // Determine which player’s turn it is
            if (isComp1Turn && turn1 < comp1.getTurns().length) {
                handlePlayerTurn(comp1, startCoin, endCoin, COIN_SELECTED_STYLE1, true);
                isComp1Turn = false; // Toggle turn to comp2
            } else if (!isComp1Turn && turn2 < comp2.getTurns().length) {
                handlePlayerTurn(comp2, startCoin, endCoin, COIN_SELECTED_STYLE2, false);
                isComp1Turn = true; // Toggle turn to comp1
            }

            moves++; // Increment the total moves
        }
    }

    private void handlePlayerTurn(Computer player, int startCoin, int endCoin, String style, boolean isComp1) {
        int[] playerTurns = player.getTurns();
        int currentTurnValue = isComp1 ? playerTurns[turn1] : playerTurns[turn2];

        // Directly select the coin at either start or end based on the currentTurnValue
        if (currentTurnValue == endCoin) {
            coinLabel.getChildren().get(end).setStyle(style);
            end--;  // Move the end pointer inwards
        } else if (currentTurnValue == startCoin) {
            coinLabel.getChildren().get(start).setStyle(style);
            start++;  // Move the start pointer inwards
        }

        // Increment the correct player's turn counter
        if (isComp1) {
            turn1++;
        } else {
            turn2++;
        }
    }

    private void handleCoinSelection(int currentTurnValue, int startCoin, int endCoin, String style, boolean isComp1) {
        if (currentTurnValue == endCoin) {
            coinLabel.getChildren().get(end).setStyle(style);
            end--;
        } else if (currentTurnValue == startCoin) {
            coinLabel.getChildren().get(start).setStyle(style);
            start++;
        }
    }


    private void initializeLayout() {
        p1.setStyle("-fx-background-color: orange;-fx-border-color: orange;");
        p1.setDisable(true);
        p2.setDisable(true);

        FlowPane bottom = CustomLayOuts.fBox();
        bottom.getChildren().addAll(move, dpTable);
        BorderPane.setMargin(bottom, new Insets(20));
        Main.getStartPane().setBottom(bottom);

        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setVgap(10);
        flowPane.setHgap(30);

        dpTable.setOnAction(e -> new DPTable(dynamic.getDpTable()));
        p1.setScore(comp1.result());
        p2.setScore(comp2.result());

        FlowPane fp1 = createTurnFlowPane(comp1, COIN_SELECTED_STYLE11);
        FlowPane fp2 = createTurnFlowPane(comp2, COIN_SELECTED_STYLE22);

        MenuPane menuPane = new MenuPane(this);
        flowPane.getChildren().addAll(fp1, p1, menuPane, p2, fp2);
        Main.getStartPane().setTop(flowPane);
        Main.getStartPane().setCenter(coinLabel);
    }

    private FlowPane createTurnFlowPane(Computer player, String style) {
        FlowPane fp = CustomLayOuts.fBox();
        for (int turn : player.getTurns()) {
            CoinLabel coinLabel2 = new CoinLabel(turn + "");
            coinLabel2.setStyle(style);
            fp.getChildren().add(coinLabel2);
        }
        return fp;
    }
    public int[] getArray() {
        return arr;
    }

}
