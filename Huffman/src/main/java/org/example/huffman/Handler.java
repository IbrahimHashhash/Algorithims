package org.example.huffman;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Handler {
    private File file;
    private Label labels = new Label(" ");
    private String path = "C:\\Users\\ibrah\\OneDrive\\Desktop\\";
    private String table = "";
    private String headerTree = "";
    private long fileSize;
    private long compressedSize;
    private String decompressedFileName;
    private String compressedFileName;
    private String compressedRate;

    public Label getLabels() {
        return labels;
    }

    public void handleBrowse() {
        FileChooser fileChooser = new FileChooser();

        // Add filters for file types
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),           // Allow all file types
                new FileChooser.ExtensionFilter("Huffman Files (*.huf)", "*.huf") // Specifically allow .huf files
        );

        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            labels.setText(file.getName()); // Display the selected file's name
        }
    }


    public void handleCompress() {
        if (file != null) {
            HuffmanCompression huffmanCompression = new HuffmanCompression(file);
            try {
                String[] fileName = file.getName().split("\\.");
                System.out.println(fileName[0]);
                compressedFileName = "Huff" + fileName[0] + ".huf";
                huffmanCompression.compress(file.getAbsolutePath(),path + compressedFileName);
                headerTree = huffmanCompression.getSerializeTree();
                table = huffmanCompression.generateAndReturnCodeTable(huffmanCompression.getRoot());
                fileSize = huffmanCompression.getOriginalFileSize(file.getPath());
                compressedSize = huffmanCompression.getCompressedFileLength(path + compressedFileName);
                compressedRate = String.format("%.2f%%", huffmanCompression.calculateCompressionRate(fileSize, compressedSize));
            } catch (IOException ex) {
                showAlert("Error during compression: " + ex.getMessage());
            }
        } else {
            showAlert("Choose File first");
        }
    }

    public void handleDecompress() {
        if (file != null) {
            String[] format = file.getPath().split("\\.");
            decompressedFileName = "Decompressed" + file.getName() + "."+ format[1];
            HuffmanDecompression.decompress(path + compressedFileName,
                    path + decompressedFileName);
        } else {
            showAlert("Choose File first");
        }
    }

    public void handleStatistics() {
        if (file != null) {
            Stage statStage = new Stage();
            Label oName = new Label("File Name: " + file.getName());
            Label cName = new Label("Compressed Name: " + compressedFileName);
            Label oFile = new Label("Original File Size: " +  fileSize + " Bytes");
            Label cFile = new Label("Compressed File Size: " +  compressedSize + " Bytes");
            Label rate = new Label("compressed Rate: " + compressedRate);
            oName.getStyleClass().add("stat");
            cName.getStyleClass().add("stat");
            oFile.getStyleClass().add("stat");
            cFile.getStyleClass().add("stat");
            rate.getStyleClass().add("stat");
            VBox vBox = new VBox();
            vBox.setPadding(new Insets(10));
            vBox.setAlignment(Pos.CENTER);
            vBox.setSpacing(20);
            vBox.setStyle("-fx-background-color: linear-gradient(to bottom,deepSkyBlue , lightSkyBlue);");
            vBox.getChildren().addAll(oName,cName,oFile,cFile,rate);
            Scene scene = new Scene(vBox,500,270);
            statStage.setScene(scene);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            statStage.show();
        } else {
            showAlert("Choose File first");
        }
    }

    public void handleHuffman() {
        if (file != null) {
            showPopupWindow("Huffman Table", table);
        } else {
            showAlert("Choose File first");
        }
    }

    public void handleHeader() {
        if (file != null) {
            showPopupWindow("Header", headerTree);
        } else {
            showAlert("Choose File first");
        }
    }

    private void showPopupWindow(String title, String content) {
        // Create a new stage
        Stage popupStage = new Stage();
        popupStage.setTitle(title);

        // Create a TextArea to display the content
        TextArea textArea = new TextArea(content);
        textArea.setEditable(false); // Make it read-only
        textArea.setWrapText(true); // Enable text wrapping

        // Set the TextArea as the root node
        StackPane stackPane = new StackPane(textArea);

        // Create a scene with the stack pane
        Scene scene = new Scene(stackPane, 400, 300);

        // Ensure the stylesheet is loaded
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        // Set the scene and show the stage
        popupStage.setScene(scene);
        popupStage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
}
