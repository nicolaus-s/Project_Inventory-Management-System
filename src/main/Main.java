/**
 *
 * @author Nicolaus Shaffer
 * Javadocs for this project are stored in ..\Shaffer Factory\JavaDocs
 */

package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * @param primaryStage The Main function opens the Main Form and titles the window "Inventory Management"
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../View/Main Form.fxml"));
        primaryStage.setTitle("Inventory Management");
        primaryStage.setScene(new Scene(root, 950, 600));
        primaryStage.show();
    }


    /**
     * @param args Main function definition.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
