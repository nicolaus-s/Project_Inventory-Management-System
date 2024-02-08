/**
 *
 * @author Nicolaus Shaffer
 */

package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.naming.InvalidNameException;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    Stage stage;
    Parent scene;
    ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();
    ObservableList<Part> removedAssociatedParts = FXCollections.observableArrayList();

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonCancel;

    @FXML
    private TableView<Part> prodAddPartTableView;

    @FXML
    private TableColumn<Part, Integer> prodAddPartIdCol;

    @FXML
    private TableColumn<Part, String> prodAddPartNameCol;

    @FXML
    private TableColumn<Part, Integer> prodAddPartInvCol;

    @FXML
    private TableColumn<Part, Double> prodAddPartPriceCol;

    @FXML
    private TableView<Part> prodRemovePartTableView;

    @FXML
    private TableColumn<Part, Integer> prodRemovePartIdCol;

    @FXML
    private TableColumn<Part, String> prodRemovePartNameCol;

    @FXML
    private TableColumn<Part, Integer> prodRemovePartInvCol;

    @FXML
    private TableColumn<Part, Double> prodRemovePartPriceCol;

    @FXML
    private TextField prodSearchTxt;

    @FXML
    private Button buttonAddPart;

    @FXML
    private Button buttonRemovePart;

    @FXML
    private Label prodIdLbl;

    @FXML
    private TextField prodIdTxt;

    @FXML
    private Label prodNameLbl;

    @FXML
    private TextField prodNameTxt;

    @FXML
    private Label prodInvLbl;

    @FXML
    private TextField prodInvTxt;

    @FXML
    private Label prodPriceLbl;

    @FXML
    private TextField prodPriceTxt;

    @FXML
    private Label prodMaxLbl;

    @FXML
    private TextField prodMaxTxt;

    @FXML
    private Label prodMinLbl;

    @FXML
    private TextField prodMinTxt;

    /**
     * @param event When characters are typed, the table contents are filtered to matching part titles. If numbers are typed, all parts are displayed and the matching ID is highlighted in the table. If there are no matches an alert window if no results are found.
     * ERROR HANDLING If the search input is not a number, the Integer.parseInt() method fails and sets a boolean variable to true. If the boolean is true, the lookupPart(int) method id bypassed and the lookupPart(String) method is used.
     * ERROR HANDLING If no matches are found from the search, an alert box is displayed to notify that no results were found.
     */
    @FXML
    void onActionPartSearch(KeyEvent event) {
        String str = prodSearchTxt.getText();
        int id = -1;
        boolean errorOccurred = false;

        try{
            id = Integer.parseInt(str);
        }
        catch (NumberFormatException e){
            errorOccurred = true;
        }

        if(!errorOccurred){
            Part part = Inventory.lookupPart(id);
            if(part.getId() == id){
                prodAddPartTableView.getSelectionModel().select(Inventory.lookupPart(id));
            }
            else{
                prodAddPartTableView.getSelectionModel().clearSelection();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part Search");
                alert.setContentText("No results found");
                alert.show();
            }
        }
        else {
            Inventory.lookupPart(str);
            prodAddPartTableView.setItems(Inventory.getFilteredParts());
        }
        if(Inventory.getFilteredParts().isEmpty() && !str.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Search");
            alert.setContentText("No results found");
            alert.show();}
    }

    /**
     * @param event When the Add button is clicked, the highlighted part in the All Parts table (upper table) is added to tempAssociatedParts.
     */
    @FXML
    void onActionAddPart(ActionEvent event) {
        Part p = prodAddPartTableView.getSelectionModel().getSelectedItem();
        if(p == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add Associated Part");
            alert.setContentText("No part selected.");
            alert.show();
        }
        else{
            tempAssociatedParts.add(p);
        }
    }

    /**
     * @param event When the Remove button is clicked, the highlighted part in the Associated Parts table (lower table) is added to tempAssociatedParts. The tempAssociatedParts list is later used in the Product constructor when the product is saved.
     */
    @FXML
    void onActionRemovePart(ActionEvent event) {
        Part p = prodRemovePartTableView.getSelectionModel().getSelectedItem();
        if(p == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Remove Associated Part");
            alert.setContentText("No part selected.");
            alert.show();
        }
        else{
            tempAssociatedParts.remove(p);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Associated Part Removed");
            alert.setContentText("The selected associated part has been removed.");
            alert.show();
        }
    }

    /**
     * @param event When the Save button is clicked the following occurs:
     * The same ID is maintained.
     * Price is parsed as a Double.
     * Stock is parsed as an Integer.
     * Min and Max are parsed as Integers. These numbers can be equal. If the Min value is greater than the Max value, an alert box is shown to notify that user that min can't be more than max and both fields are set to be blank.
     * ERROR HANDLING If any of the input values can't be parsed to Double or Integer as needed, a NumberFormatException is caught and an alert box is displayed to notify the user to enter valid number values.
     * FUTURE ENHANCEMENT Specify which fields are invalid in the alert box and highlight the boxes to guide the user to the fields that must be corrected.
     * A For loop loops through the getAllProducts ObservableList, while incrementing an index variable, to find the matching ID. Once the matching ID is found, the current index is found and used to set the object in the allProducts ObservableList.
     * Once the Product constructor is called and set at the index to the AllProducts ObservableList, the application returns to the Main Form.
     * @throws IOException
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(prodIdTxt.getText());
            String name = prodNameTxt.getText();
            double price = Double.parseDouble(prodPriceTxt.getText());
            int stock = Integer.parseInt(prodInvTxt.getText());
            int min = Integer.parseInt(prodMinTxt.getText());
            int max = Integer.parseInt(prodMaxTxt.getText());
            ObservableList<Part> associatedParts = tempAssociatedParts;
            if(min > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Maximum cannot be less than minimum.");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK){
                    prodMinTxt.setText("");
                    prodMaxTxt.setText("");
                }
            }
            else {

                int i = -1;
                for (Product prod : Inventory.getAllProducts()) {
                    i++;
                    if (prod.getId() == id) {
                        break;
                    }
                }

                Product modProd = new Product(id, name, price, stock, min, max, associatedParts);
                Inventory.updateProduct(i, modProd);
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
     * @param event When the Cancel button is clicked, the application returns to the Main Form without saving any user input.
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main Form.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param p Used in subsequent methods to get the following properties of the Product: ID, Name, Stock, Price, Min, Max. The fields in the Modify Part form are set to the values to each of each of the relevant properties. If the object is an instance of In-House, the 'In-House' radio button is set. Otherwise, the 'Outsourced' radio button is set. The tempAssociatedParts ObservableList is populated to equal the objects returned from getAllAssociatedParts for the Product. Once tempAssociatedParts matches getAllAssociatedParts, the Associated Parts table is set to display each object in tempAssociatedParts.
     */
    public void loadProduct(Product p) {
        prodIdTxt.setText(String.valueOf(p.getId()));
        prodNameTxt.setText(p.getName());
        prodInvTxt.setText(String.valueOf(p.getStock()));
        prodPriceTxt.setText(String.valueOf(p.getPrice()));
        prodMaxTxt.setText(String.valueOf(p.getMax()));
        prodMinTxt.setText(String.valueOf(p.getMin()));
        for (Part part : p.getAllAssociatedParts()) {
            tempAssociatedParts.add(part);
        }
        prodRemovePartTableView.setItems(tempAssociatedParts);
    }

    /**
     * @param url
     * @param rb Modify Product initializes with populated fields and two tables from the loadProduct() method. The first table displays all parts using the getAllParts() method. The Associated Parts table displays objects in the tempAssociatedParts ObservableList.
     */
    @Override
    public void initialize (URL url, ResourceBundle rb){
        //Create temporary ObservableList
        //All parts
        prodAddPartTableView.setItems(Model.Inventory.getAllParts());
        prodAddPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodAddPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodAddPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodAddPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        //Associated Parts
        prodRemovePartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodRemovePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodRemovePartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodRemovePartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}
