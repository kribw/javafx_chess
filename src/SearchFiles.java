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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class SearchFiles extends Application {
    TextArea txtLog = new TextArea();
    String console = "";
    int i = 0;
    TextField txtName = new TextField();
    TextField txtWord = new TextField();



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
        //btSearch.setOnAction(e -> writeTest());
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

    public void searchDir() {
        File dir = new File(txtName.getText());
        String word = txtWord.getText();

        List<File> fileList = new ArrayList<>();



        txtLog.setText(console);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
