/**
 *
 * @author Nicolaus Shaffer
 */

package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddPartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private AnchorPane addPart;

    @FXML
    private Label partIDlbl;

    @FXML
    private Label partNamelbl;

    @FXML
    private Label partInvlbl;

    @FXML
    private TextField partIdTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private Label partPricelbl;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private Label partMaxlbl;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private Label partMinlbl;

    @FXML
    private TextField partMinTxt;

    @FXML
    private Label partOriginlbl;

    @FXML
    private TextField partOriginTxt;

    @FXML
    private Label sceneLabel;

    @FXML
    private RadioButton partInHouseRadio;

    @FXML
    private ToggleGroup partTypeToggleGroup;

    @FXML
    private RadioButton partOutsourcedRadio;

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonCancel;

    /**
     * @param event When the Cancel button is clicked, the application returns to the Main Form without saving any user input.
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main Form.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event When the Save button is clicked the following occurs:
     * ID is generated to be 1 higher than the current highest ID number
     * Price is parsed as a Double.
     * Stock is parsed as an Integer.
     * Min and Max are parsed as Integers. These numbers can be equal. If the Min value is greater than the Max value, an alert box is shown to notify that user that min can't be more than max and both fields are set to be blank.
     * If the 'In-House' radio button is selected, the In-House constructor is used.
     * If the 'Outsourced' radio button is select, the Outsourced constructor is used.
     * ERROR HANDLING If any of the input values can't be parsed to Double or Integer as needed, a NumberFormatException is caught and an alert box is displayed to notify the user to enter valid number values.
     * FUTURE ENHANCEMENT Specify which fields are invalid in the alert box and highlight the boxes to guide the user to the fields that must be corrected.
     * Once the relevant constructor is called and added to the allParts ObservableList, the application returns to the Main Form.
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException{
        //Generate ID
        int maxId = 0;
        for(Part p : Inventory.getAllParts()){
            int id = p.getId();
            if (id < maxId) {
                continue;
            }
            maxId = id;
        }
        maxId++;
        //Set Item Properties
        try {
            int id = maxId;
            String name = partNameTxt.getText();
            double price = Double.parseDouble(partPriceTxt.getText());
            int stock = Integer.parseInt(partInvTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            if(min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Maximum cannot be less than minimum.");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK){
                    partMinTxt.setText("");
                    partMaxTxt.setText("");
                }
            }
            else {
                if(partInHouseRadio.isSelected()) {
                    int machineId = Integer.parseInt(partOriginTxt.getText());
                    InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.addPart(newPart);}

                else {
                    String companyName = partOriginTxt.getText();
                    Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.addPart(newPart);
                }
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/View/Main Form.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid number values.");
            alert.show();
        }

    }

    /**
     * @param event When the 'Outsourced' radio button is selected, the 'Company Name' label is shown.
     */
    @FXML
    void showcName(ActionEvent event) {
        partOriginlbl.setText("Company Name");
    }

    /**
     * @param event When the 'In-House' radio button is selected, the 'Machine ID' label is shown. This is the default value when opening the Add Part page.
     */
    @FXML
    void showmID(ActionEvent event) {
        partOriginlbl.setText("Machine ID");
    }

    /**
     * @param url
     * @param rb Form is initialized with all blank fields.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
    }
}
