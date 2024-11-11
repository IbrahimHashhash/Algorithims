package gameapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import main.Player;

public class TwoPlayer extends VBox {
    private static final String PLAYER_INACTIVE_STYLE = "-fx-background-color: lightGreen; -fx-opacity: 0.8; " +
            "-fx-border-color: lightGreen; -fx-border-radius: 10px; " +
            "-fx-background-radius: 10px; -fx-pref-width: 200px; " +
            "-fx-pref-height: 200px; -fx-padding: 10px 50px;";
    private static final String PLAYER_ACTIVE_STYLE = "-fx-background-color: rgb(224, 242, 249); -fx-opacity: 0.8; " +
            "-fx-border-color: rgba(42, 79, 139,1); -fx-border-radius: 10px; " +
            "-fx-background-radius: 10px; -fx-pref-width: 200px; " +
            "-fx-pref-height: 200px; -fx-padding: 10px 50px;";
    private static final String COIN_SELECTED_STYLE = "-fx-background-color: rgb(197, 158, 1); -fx-border-color: yellow; -fx-text-fill: white;";

    Root root = Main.getStartPane();
    Player p1;
    Player p2;
    int turn;
    int picked;
    private static int start, end;
    private final Coin[] coins;
    private final FlowPane coinBox;
    PlayerPane playerPane1 = new PlayerPane("player1");
    PlayerPane playerPane2 = new PlayerPane("player2");
    FlowPane scoreContainer;
    int counter = 0;
    GameInfo gameInfo;
    MenuPane menuPane;

    public TwoPlayer(FlowPane coinBox, String name1, String name2, int picked) {
        this.picked = picked;
        this.gameInfo = new GameInfo(coinBox, root, name1, name2, picked);
        menuPane = new MenuPane(gameInfo);
        scoreContainer = new FlowPane();
        scoreContainer.setAlignment(Pos.CENTER);
        scoreContainer.setHgap(20); // Spacing between elements
        FlowPane.setMargin(playerPane1, new Insets(20, 20, 20, 20));
        FlowPane.setMargin(playerPane2, new Insets(20, 20, 20, 20));
        scoreContainer.getChildren().addAll(playerPane1, menuPane, playerPane2);
        root.setTop(scoreContainer);
        this.setAlignment(Pos.CENTER);
        this.root = root;

        turn = picked; // Set initial turn based on 'picked': 0 for Player 1, 1 for Player 2
        p1 = new Player(name1);
        p2 = new Player(name2);
        this.coins = initializeArray(coinBox);
        end = coins.length - 1;
        start = 0;
        this.coinBox = initialize();

        // Apply initial active style to the starting player
        if (turn % 2 == 0) {
            // Player 1 starts
            scoreContainer.getChildren().get(2).setStyle(PLAYER_ACTIVE_STYLE);
            scoreContainer.getChildren().get(0).setStyle(PLAYER_INACTIVE_STYLE);
        } else {
            // Player 2 starts
            scoreContainer.getChildren().get(0).setStyle(PLAYER_ACTIVE_STYLE);
            scoreContainer.getChildren().get(2).setStyle(PLAYER_INACTIVE_STYLE);
        }

        play();
    }

    // Initialize the coins and coinBox
    public Coin[] initializeArray(FlowPane flowPane) {
        Coin[] coins = new Coin[flowPane.getChildren().size()];
        for (int i = 0; i < flowPane.getChildren().size(); i++) {
            coins[i] = new Coin(((CoinLabel) flowPane.getChildren().get(i)).getText());
        }
        return coins;
    }

    public FlowPane initialize() {
        FlowPane coinBox = new FlowPane();
        coinBox.setAlignment(Pos.CENTER);
        coinBox.setHgap(10); // Set spacing between coins for a more flexible layout
        coinBox.getChildren().addAll(coins);
        ((Coin) coinBox.getChildren().get(start)).getCoinLabel().setDisable(false);
        ((Coin) coinBox.getChildren().get(end)).getCoinLabel().setDisable(false);

        int valueStart = ((Coin) coinBox.getChildren().get(start)).getValue();
        int valueEnd = ((Coin) coinBox.getChildren().get(end)).getValue();
        ((Coin) coinBox.getChildren().get(start)).getCoinLabel().setText(String.valueOf(valueStart));
        ((Coin) coinBox.getChildren().get(end)).getCoinLabel().setText(String.valueOf(valueEnd));
        this.getChildren().add(coinBox);
        root.setCenter(this);
        return coinBox;
    }

    // Start the game play
    public void play() {
        setStartClickHandler();
        setEndClickHandler();
    }

    private void handleClick(int index) {
        int coinValue = ((Coin) coinBox.getChildren().get(index)).getValue();
        ((Coin) coinBox.getChildren().get(index)).getCoinLabel().setDisable(true);
        ((Coin) coinBox.getChildren().get(index)).getCoinLabel().setStyle(COIN_SELECTED_STYLE);

        if (turn % 2 == 0) {
            p1.sum(coinValue);
            playerPane1.setScore(p1.getSum());
            scoreContainer.getChildren().get(2).setStyle(PLAYER_INACTIVE_STYLE);
            scoreContainer.getChildren().get(0).setStyle(PLAYER_ACTIVE_STYLE);
        } else {
            p2.sum(coinValue);
            playerPane2.setScore(p2.getSum());
            scoreContainer.getChildren().get(0).setStyle(PLAYER_INACTIVE_STYLE);
            scoreContainer.getChildren().get(2).setStyle(PLAYER_ACTIVE_STYLE);
        }
    }

    private void setStartClickHandler() {
        if (start <= end) {
            ((Coin) coinBox.getChildren().get(start)).getCoinLabel().setOnMouseClicked(mouseEvent -> {
                handleClick(start);
                start++;
                turn++;
                moveFirst();
                counter++;
                if (counter == coinBox.getChildren().size()) {
                    showGameOver();
                }
            });
        }
    }

    private void setEndClickHandler() {
        if (end >= start) {
            ((Coin) coinBox.getChildren().get(end)).getCoinLabel().setOnMouseClicked(mouseEvent -> {
                handleClick(end);
                end--;
                turn++;
                counter++;
                moveLast();
                if (counter == coinBox.getChildren().size()) {
                    showGameOver();
                }
            });
        }
    }

    public void moveFirst() {
        if (start <= end) {
            int valueStart = ((Coin) coinBox.getChildren().get(start)).getValue();
            ((Coin) coinBox.getChildren().get(start)).getCoinLabel().setText(String.valueOf(valueStart));
            ((Coin) coinBox.getChildren().get(start)).getCoinLabel().setOpacity(1);
            ((Coin) coinBox.getChildren().get(start)).getCoinLabel().setDisable(false);
            setStartClickHandler();
        }
    }

    public void moveLast() {
        if (end >= start) {
            int valueEnd = ((Coin) coinBox.getChildren().get(end)).getValue();
            ((Coin) coinBox.getChildren().get(end)).getCoinLabel().setText(String.valueOf(valueEnd));
            ((Coin) coinBox.getChildren().get(end)).getCoinLabel().setOpacity(1);
            ((Coin) coinBox.getChildren().get(end)).getCoinLabel().setDisable(false);
            setEndClickHandler();
        }
    }

    private void showGameOver() {
        Button showScoreboardButton = new Button("Score");
        if (p1.getSum() > p2.getSum()) {
            showScoreboardButton.setOnAction(e -> new GameOver(gameInfo, "player1", p1.getSum()).show());
        } else if (p1.getSum() < p2.getSum()) {
            showScoreboardButton.setOnAction(e -> new GameOver(gameInfo, "player2", p2.getSum()).show());
        } else {
            showScoreboardButton.setOnAction(e -> new GameOver(gameInfo, "Draw", p1.getSum()).show());
        }
        BorderPane.setMargin(showScoreboardButton, new Insets(20));
        BorderPane.setAlignment(showScoreboardButton, Pos.BOTTOM_CENTER);
        root.setBottom(showScoreboardButton);
    }
}
