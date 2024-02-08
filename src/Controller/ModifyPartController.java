/**
 *
 * @author Nicolaus Shaffer
 */

package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyPartController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private AnchorPane modPart;

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
     * The same ID is maintained.
     * Price is parsed as a Double.
     * Stock is parsed as an Integer.
     * Min and Max are parsed as Integers. These numbers can be equal. If the Min value is greater than the Max value, an alert box is shown to notify that user that min can't be more than max and both fields are set to be blank.
     * If the 'In-House' radio button is selected, the In-House constructor is used.
     * If the 'Outsourced' radio button is select, the Outsourced constructor is used.
     * ERROR HANDLING If any of the input values can't be parsed to Double or Integer as needed, a NumberFormatException is caught and an alert box is displayed to notify the user to enter valid number values.
     * FUTURE ENHANCEMENT Specify which fields are invalid in the alert box and highlight the boxes to guide the user to the fields that must be corrected.
     * A For loop loops through the getAllParts list, while incrementing an index variable, to find the matching ID. Once the matching ID is found, the current index is found and used to set the object in the allParts ObservableList.
     * Once the relevant constructor is called and set at the index to the allParts ObservableList, the application returns to the Main Form.
     * @throws IOException
     */
    @FXML
    void onActionSavePart(ActionEvent event) throws IOException {
        try {
            int id = Integer.parseInt(partIdTxt.getText());
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

                int i = -1;
                for (Part part : Inventory.getAllParts()) {
                    i++;
                    if (part.getId() == id) {
                        break;
                    }
                }

                if(partInHouseRadio.isSelected()) {
                    int machineId = Integer.parseInt(partOriginTxt.getText());
                    InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.updatePart(i, newPart);
                }
                else {
                    String companyName = partOriginTxt.getText();
                    Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                    Inventory.updatePart(i, newPart);
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
     * @param p Used in subsequent methods to get the following properties of the Part: ID, Name, Stock, Price, Min, Max. The fields in the Modify Part form are set to the values to each of each of the relevant properties. If the object is an instance of In-House, the 'In-House' radio button is set. Otherwise, the 'Outsourced' radio button is set.
     */
    public void loadPart(Part p){
        partIdTxt.setText(String.valueOf(p.getId()));
        partNameTxt.setText(p.getName());
        partInvTxt.setText(String.valueOf(p.getStock()));
        partPriceTxt.setText(String.valueOf(p.getPrice()));
        partMaxTxt.setText(String.valueOf(p.getMax()));
        partMinTxt.setText(String.valueOf(p.getMin()));

        if(p instanceof InHouse){
            partInHouseRadio.setSelected(true);
            partOriginlbl.setText("Machine ID");
            partOriginTxt.setText(String.valueOf(((InHouse) p).getMachineID()));
        }
        else {
            partOutsourcedRadio.setSelected(true);
            partOriginlbl.setText("Company Name");
            partOriginTxt.setText(String.valueOf(((Outsourced) p).getCompanyName()));
        }

    }


    /**
     * @param url
     * @param rb Modify Part initializes with populated fields from the loadPart() method.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
    }
}