import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FindWordInFile extends Application {
    public TextArea txtLog = new TextArea();
    public String console = "";
    public TextField txtName = new TextField();
    public TextField txtWord = new TextField();
    public List<File> allFiles = new ArrayList<>();
    public int scannedDirectories = 0;
    public int scannedFiles = 0;
    public int wordsFound = 0;



    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(1, 10, 10, 10));
        double width = 1000;
        double height = 500;

        // Top
        HBox topBox = new HBox();
        topBox.setPadding(new Insets(0, 0, 5, 0));
        topBox.setSpacing(10);

        txtName.setPromptText("Directory or filename");
        txtName.prefWidthProperty().bind(borderPane.widthProperty().divide(2));

        Button btSearch = new Button("Search");
        btSearch.setOnAction(e -> {
            try {
                listFiles(txtName.getText());
                searchFiles(allFiles, txtWord.getText());
            } catch (Exception ex) {
                // fuck off
            }});
        btSearch.setPrefWidth(100);

        txtWord.setPromptText("Word");
        txtWord.prefWidthProperty().bind(borderPane.widthProperty().divide(2).subtract(btSearch.widthProperty()));

        topBox.getChildren().addAll(txtName, txtWord, btSearch);
        topBox.setAlignment(Pos.CENTER);
        borderPane.setTop(topBox);

        // Center
        txtLog.setText(console);
        txtLog.setEditable(false);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(txtLog);
        borderPane.setCenter(stackPane);

        // Scene
        Scene scene = new Scene(borderPane, width, height);
        topBox.requestFocus();
        primaryStage.setTitle("Find word in files");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void listFiles(String path) {
        resetSearch();
        File dir = new File(path);
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                listFiles(file.getPath());
                scannedDirectories++;
            } else if (file.isFile() && file.getName().toLowerCase().endsWith(".txt")) {
                allFiles.add(file);
                scannedFiles++;
            }
        }
    }


    public void resetSearch() {
        allFiles.clear();
        console = "Search start.";
        console = console + ("\n-----------------------------");
        txtLog.setText(console);
        scannedDirectories = 0;
        scannedFiles = 0;
        wordsFound = 0;
    }

    public void searchFiles(List<File> files, String word) throws Exception {
        for (File file : files) {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                if (line.toLowerCase().contains(word.toLowerCase())) {
                    String[] wordAmount = line.split(" ");
                    int count = 0;
                    for(String amount : wordAmount) {
                        if(amount.toLowerCase().contains(word.toLowerCase())) {
                            count++;
                        }
                    }
                    wordsFound += count;
                    console = console + "\n" + file.getPath() + ":      " + line;
                    txtLog.setText(console);
                }
            }
        }
        console = console + ("\n-----------------------------");
        if(wordsFound > 0) {
            console = console + ("\nSearch end.");
            console = console + ("\nSearched " + scannedDirectories + " directories and " + scannedFiles + " files.");
            console = console + ("\nFound " + wordsFound + " occurences of '" + word.toLowerCase() + "'.");
        } else {
            console = console + ("\n'" + word.toLowerCase() + "' does not exist.");
        }
        console = console + "\n\n";
        txtLog.setText(console);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
