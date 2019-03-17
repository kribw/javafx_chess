import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.util.Random;

public class CircleAndSquare extends Application {
    private Random generator = new Random();
    private Rectangle rectangle;
    private Circle circle;
    private Object currentObject = null;
    private BorderPane borderPane = new BorderPane();
    private Pane centerPane = new Pane();
    private HBox topBox = new HBox();
    private HBox bottomBox = new HBox();
    private VBox leftBox = new VBox();
    private VBox rightBox = new VBox();

    @Override
    public void start(Stage primaryStage) {
        // Top
        TextField displayField = new TextField();
        displayField.setPrefWidth(250);
        displayField.setEditable(false);
        displayField.setText(printObjects());
        topBox.getChildren().add(displayField);
        topBox.setAlignment(Pos.CENTER);
        borderPane.setTop(topBox);

        // Left
        Button redButton = new Button("Red");
        redButton.setOnAction(e -> {
            if(currentObject == null) {
                // do nothing
            } else if (currentObject == rectangle) {
                rectangle.setFill(Color.RED);
            } else if (currentObject == circle){
                circle.setFill(Color.RED);
            }
        });

        Button blueButton = new Button("Blue");
        blueButton.setOnAction(e -> {
            if(currentObject == null) {
                // do nothing
            } else if (currentObject == rectangle) {
                rectangle.setFill(Color.BLUE);
            } else if (currentObject == circle) {
                circle.setFill(Color.BLUE);
            }
        });
        leftBox.getChildren().addAll(redButton, blueButton);
        leftBox.setAlignment(Pos.CENTER);
        borderPane.setLeft(leftBox);

        // Center
        borderPane.setCenter(centerPane);
        centerPane.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        // Right
        Button rotateButton = new Button("Rotate");
        rotateButton.setOnAction(e -> {
            if (currentObject == null) {
                // do nothing
            } else if (currentObject == rectangle) {
                rectangle.getTransforms().add(new Rotate(45, rectangle.getX() + rectangle.getWidth()/2, rectangle.getY() + rectangle.getHeight()/2));
            }
        });
        rightBox.getChildren().add(rotateButton);
        rightBox.setAlignment(Pos.CENTER);
        borderPane.setRight(rightBox);

        // Bottom
        Button squareButton = new Button("Square");
        squareButton.setOnAction(e -> {
            createRectangle();
            displayField.setText(printObjects());
        });
        Button circleButton = new Button("Circle");
        circleButton.setOnAction(e -> {
            createCircle();
            displayField.setText(printObjects());
        });
        bottomBox.getChildren().addAll(squareButton, circleButton);
        bottomBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(bottomBox);

        // Scene
        Scene scene = new Scene(borderPane, 450, 400);
        primaryStage.setTitle("Draw square and circle");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void removeCircle() {
        if (centerPane.getChildren().contains(circle)) {
            centerPane.getChildren().remove(circle);
        }
    }
    public void removeRectangle() {
        if (centerPane.getChildren().contains(rectangle)) {
            centerPane.getChildren().remove(rectangle);
        }
    }

    public void createRectangle() {
        removeRectangle();
        rectangle = new Rectangle(50, 50);
        currentObject = rectangle;
        centerPane.getChildren().add(rectangle);
        rectangle.setX(randomRectangleX());
        rectangle.setY(randomRectangleY());
        rectangle.setOnMouseClicked(e -> currentObject = rectangle);
    }

    public void createCircle() {
        removeCircle();
        circle = new Circle(25);
        currentObject = circle;
        centerPane.getChildren().add(circle);
        circle.setCenterX(randomCircleX());
        circle.setCenterY(randomCircleY());
        circle.setOnMouseClicked(e -> currentObject = circle);
    }

    public double randomRectangleX() {
        double random = generator.nextDouble() * centerPane.getWidth();
        if(random > (centerPane.getWidth() - rectangle.getWidth())) {
            random = centerPane.getWidth() - rectangle.getWidth();
        }
        return random;
    }

    public double randomRectangleY() {
        double random = generator.nextDouble() * centerPane.getHeight();
        if(random > (centerPane.getHeight() - rectangle.getHeight())) {
            random = centerPane.getHeight() - rectangle.getHeight();
        }
        return random;
    }

    public double randomCircleX() {
        double random = generator.nextDouble() * centerPane.getWidth();
        if(random > centerPane.getWidth() - circle.getRadius()) {
            random = centerPane.getWidth() - circle.getRadius();
        } else if (random < circle.getRadius()) {
            random = circle.getRadius();
        }
        return random;
    }


    public double randomCircleY() {
        double random = generator.nextDouble() * centerPane.getHeight();
        if(random > centerPane.getHeight() - circle.getRadius()) {
            random = centerPane.getHeight() - circle.getRadius();
        } else if (random < circle.getRadius()) {
            random = circle.getRadius();
        }
        return random;
    }

    public String printRectangle() {
        if(rectangle != null) {
            return String.format("Square(%.1f, %.1f).", rectangle.getX(), rectangle.getY());
        } else {
            return ("No square found.");
        }
    }

    public String printCircle() {
        if(circle != null) {
            return String.format("Circle(%.1f, %.1f).", circle.getCenterX(), circle.getCenterY());
        } else {
            return ("No circle found.");
        }
    }

    public String printObjects() {
        if (rectangle == null && circle == null) {
            return ("No objects are currently being displayed.");
        } else {
            return String.format("%s %s", printRectangle(), printCircle());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}