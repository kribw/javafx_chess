import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pendulum extends Application {
    @Override
    public void start(Stage primaryStage) {
        PendulumPane pane = new PendulumPane();

        Scene scene = new Scene(pane, 300, 200);
        primaryStage.setTitle("Pendulum"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(100), e -> pane.next()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play(); // Start animation

        pane.setOnMouseClicked(e -> {
            if(animation.getStatus() == Animation.Status.PAUSED) {
                animation.play();
            } else {
                animation.pause();
            }
        });

        pane.requestFocus();
        pane.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.UP) {
                animation.setRate(animation.getRate() + 1);
            } else if(e.getCode() == KeyCode.DOWN) {
                animation.setRate(animation.getRate() - 1);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class PendulumPane extends Pane {
    boolean decrease = true;
    double angle = 120;
    double pendulumRadius = 100;
    Line line;
    Circle c1;
    Circle c2;

    public PendulumPane() {
        line = new Line();
        c1 = new Circle();
        c2 = new Circle();

        line.startYProperty().bind(c1.centerYProperty());
        line.startXProperty().bind(c1.centerXProperty());
        line.endYProperty().bind(c2.centerYProperty());
        line.endXProperty().bind(c2.centerXProperty());

        c1.setRadius(5);
        c1.setCenterY(10);
        c1.centerXProperty().bind(this.widthProperty().divide(2));

        c2.setRadius(10);
        c2.setCenterY(c2GetY());
        c2.setCenterX(c2GetX());

        getChildren().addAll(line, c1, c2);
    }

    public void next() {
        if(angle == 120) {
            decrease = true;
        } else if(angle == 60) {
            decrease = false;
        }

        if(decrease) {
            --angle;
        } else {
            ++angle;
        }

        c2.setCenterX(c2GetX());
        c2.setCenterY(c2GetY());
    }

    public double c2GetX() {
        return c1.getCenterX() + pendulumRadius * Math.cos(Math.toRadians(angle));
    }

    public double c2GetY() {
        return c1.getCenterY() + pendulumRadius * Math.sin(Math.toRadians(angle));
    }
}