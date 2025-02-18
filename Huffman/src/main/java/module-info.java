module org.example.huffman {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.huffman to javafx.fxml;
    exports org.example.huffman;
}