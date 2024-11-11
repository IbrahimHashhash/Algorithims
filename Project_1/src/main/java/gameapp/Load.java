package gameapp;

import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Load {

    public static void loadGame() {
        handleLoad();
    }
    public static void handleLoad(){
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            readFromFile(filePath);
        } else {
            System.out.println("no file selected"); // if file not found then you print an error statement
        }
    }


    public static void readFromFile(String filePath) {
        FlowPane coinLabels = CustomLayOuts.fBox();
        int[] arr = new int[8];
        try {
            Scanner sc = new Scanner(new File(filePath)); // takes the file path from the picked file
            String line = sc.nextLine(); // read the first line
            String[] info = line.split(","); // split the line by commas
            for (int i = 0; i < info.length && i < arr.length; i++) {
                arr[i] = Integer.parseInt(info[i]); // fill array with coin values
                coinLabels.getChildren().add(new CoinLabel(info[i])); // add each value as a CoinLabel
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex + " ");
        }
        ready(coinLabels, arr);
    }
    public static void ready(FlowPane coinLabel,int[] arr){
        Button bt1 = new Button("2 Players");
        Button bt2 = new Button("Vs Bot");
        VBox vBox = CustomLayOuts.vBox();
        vBox.getChildren().addAll(bt1,bt2,Main.getStartPane().getBack());

        bt1.setOnAction(e-> {
                PlayerSetup playerSetup = new PlayerSetup(coinLabel);
        });
        bt2.setOnAction(e->{
            ComputerMode computerMode = new ComputerMode(arr,coinLabel);
        });
        Main.getStartPane().setCenter(vBox);
    }
}
