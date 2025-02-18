package org.example.huffman;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private final Handler handler = new Handler(); // a class that handles the logic

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane(); // the main layout for the app
        root.setStyle("-fx-background-color: linear-gradient(to bottom,deepSkyBlue , lightSkyBlue);"); // set background style
        root.setTop(createTopPane()); // set the top section
        root.setCenter(createCenterPane()); // set the center section
        root.setBottom(createBottomPane()); // set the bottom section

        BorderPane.setMargin(root.getTop(), new Insets(20)); // add margin to the top section
        BorderPane.setMargin(root.getBottom(), new Insets(20)); // add margin to the bottom section

        Scene scene = new Scene(root, 580, 390); // create the app scene with size 580x390
        stage.setScene(scene); // set the scene to the stage
        stage.getScene().getStylesheets().add(getClass().getResource("/style.css").toExternalForm()); // add external css
        stage.show(); // display the stage
    }

    private FlowPane createTopPane() {
        FlowPane flowPane = new FlowPane(80, 10); // layout for top section with spacing
        flowPane.setAlignment(Pos.CENTER); // align elements to the center

        Button compress = new Button("Compress"); // button to compress
        Button decompress = new Button("Decompress"); // button to decompress
        compress.setOnAction(e -> handler.handleCompress()); // set action for compress button
        decompress.setOnAction(e -> handler.handleDecompress()); // set action for decompress button

        flowPane.getChildren().addAll(compress, decompress); // add buttons to the top section
        return flowPane;
    }

    private VBox createCenterPane() {
        VBox middle = new VBox(); // layout for center section
        middle.setAlignment(Pos.CENTER); // align elements to the center
        middle.setSpacing(10); // add spacing between elements

        Button browseButton = new Button("Browse"); // button to browse files
        browseButton.setOnAction(e -> handler.handleBrowse()); // set action for browse button
        middle.getChildren().addAll(browseButton, handler.getLabels()); // add browse button and labels to the center
        return middle;
    }

    private FlowPane createBottomPane() {
        FlowPane infoPane = new FlowPane(10, 10); // layout for bottom section with spacing
        infoPane.setAlignment(Pos.CENTER); // align elements to the center

        Button stat = new Button("Statistics"); // button to show statistics
        Button huffman = new Button("Huffman"); // button to show huffman info
        Button header = new Button("Header"); // button to show header info

        stat.setOnAction(e -> handler.handleStatistics()); // set action for statistics button
        huffman.setOnAction(e -> handler.handleHuffman()); // set action for huffman button
        header.setOnAction(e -> handler.handleHeader()); // set action for header button

        infoPane.getChildren().addAll(stat, huffman, header); // add buttons to the bottom section
        return infoPane;
    }

    public static void main(String[] args) {
        launch(args); // start the application
    }
}
