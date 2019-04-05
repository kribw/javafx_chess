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
import javafx.stage.Stage;

public class TowerOfHanoi extends Application {
    private TextArea txtLog = new TextArea();
    private String console = "";
    private TextField txtWord = new TextField();
    private int moves = 0;

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(1, 10, 10, 10));
        double width = 550;
        double height = 600;

        // Top
        HBox topBox = new HBox();
        topBox.setPadding(new Insets(0, 0, 5, 0));
        topBox.setSpacing(10);

        Button btSearch = new Button("Find moves");
        btSearch.setOnAction(e -> {
            clear();
            print("Number of disks: " + txtWord.getText());
            print("Moves are:\n");
            findMoves(Integer.parseInt(txtWord.getText()),"A", "B", "C");
            print("\nNumber of calls to the method is: " + moves);
        });
        btSearch.setPrefWidth(100);

        txtWord.setPromptText("Number of disks");
        txtWord.prefWidthProperty().bind(borderPane.widthProperty().multiply(0.8));

        topBox.getChildren().addAll(txtWord, btSearch);
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
        primaryStage.setTitle("Tower of Hanoi");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void findMoves(int disks, String start_location, String end_location, String temp_location) {
        if (disks == 1) {
            moves++;
            print("Move number: (" + moves + ") - Moved disk 1 from " +  start_location + " to " + end_location);
            return;
        }
        findMoves(disks - 1, start_location, temp_location, end_location);
        moves++;
        print("Move number: (" + moves + ") - Moved disk " + disks + " from " +  start_location + " to " + end_location);
        findMoves(disks - 1, temp_location, end_location, start_location);
    }

    private void clear() {
        console = "";
    }

    private void print(String text){
        console = console + text + "\n";
        txtLog.setText(console);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
