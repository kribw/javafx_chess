import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;


public class BindingChess extends Application {
    @Override
    public void start(Stage primaryStage) {
        double width = 200;
        double height = 200;
        GridPane pane = new GridPane();

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {
                Rectangle rectangle = new Rectangle();
                if(i % 2 == 0) {
                    if(j % 2 == 0) {
                        rectangle.setFill(Color.WHITE);
                    } else {
                        rectangle.setFill(Color.BLACK);
                    }
                } else {
                    rectangle.setFill(Color.WHITE);
                    if (j % 2 == 0) {
                        rectangle.setFill(Color.BLACK);
                    }
                }
                rectangle.heightProperty().bind(pane.heightProperty().divide(8));
                rectangle.widthProperty().bind(pane.widthProperty().divide(8));
                pane.add(rectangle, i, j);
            }
        }

        Scene scene = new Scene(pane, width, height);
        primaryStage.setTitle("Sjakkbrett");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}