import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Rectangle;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        final double WIDTH = 200;
        final double HEIGHT = 200;
        final double RECTANGLE_SIZE = WIDTH/8;
        double xPos = 0;
        double yPos = 0;
        Pane pane = new Pane();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle rectangle = new Rectangle(xPos, yPos, RECTANGLE_SIZE, RECTANGLE_SIZE);
                if(i % 2 == 0) { // checks row for color
                    if(j % 2 == 0) { // checks column for color
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
                pane.getChildren().add(rectangle);
                xPos += RECTANGLE_SIZE;
            }
            xPos = 0;
            yPos += RECTANGLE_SIZE;
        }

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        primaryStage.setTitle("Sjakkbrett");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}