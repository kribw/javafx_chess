import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.*;

public class DecryptFile extends Application {
    private String print = "";
    private TextField txtConfirm = new TextField();
    private TextField txtFile = new TextField();
    private TextField txtSave = new TextField();

    @Override
    public void start(Stage primaryStage) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(1, 10, 10, 10));
        double width = 1000;
        double height = 100;

        // Top
        HBox topBox = new HBox();
        topBox.setPadding(new Insets(5, 5, 5, 0));
        topBox.setSpacing(10);

        txtFile.setPromptText("File to decrypt");
        txtFile.prefWidthProperty().bind(borderPane.widthProperty().multiply(0.45));

        txtSave.setPromptText("Save decrypted file to");
        txtSave.prefWidthProperty().bind(borderPane.widthProperty().multiply(0.45));

        Button btEncrypt = new Button("Decrypt");
        btEncrypt.setOnAction(e -> {
            File file = new File(txtFile.getText());
            File location = new File(txtSave.getText());
            encrypt(file, location);
        } );
        btEncrypt.setPrefWidth(100);

        topBox.getChildren().addAll(txtFile, txtSave, btEncrypt);
        topBox.setAlignment(Pos.CENTER);
        borderPane.setTop(topBox);


        // Center
        txtConfirm.setText(print);
        txtConfirm.setEditable(false);
        borderPane.setCenter(txtConfirm);

        // Scene
        Scene scene = new Scene(borderPane, width, height);
        topBox.requestFocus();
        primaryStage.setTitle("Decrypt file");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    private void encrypt(File file, File location) {
        if(file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

                byte[] arrayToDecrypt = bufferedInputStream.readAllBytes();
                byte[] decryptedArray = new byte[arrayToDecrypt.length];
                fileInputStream.close();
                bufferedInputStream.close();
                int i = 0;
                for (byte originalByte : arrayToDecrypt) {
                    decryptedArray[i] = (byte) (originalByte - 5) ;
                    i++;
                }

                FileOutputStream fileOutputStream = new FileOutputStream(location);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                bufferedOutputStream.write(decryptedArray);
                bufferedOutputStream.close();
                fileOutputStream.close();
                txtConfirm.setText("File " + file.getPath() + " has been decrypted and saved to " + location.getPath() + ".");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            txtConfirm.setText("File to encrypt does not exist.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}