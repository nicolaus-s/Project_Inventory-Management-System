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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {

    Stage stage;
    Parent scene;
    ObservableList<Part> tempAssociatedParts = FXCollections.observableArrayList();

    @FXML
    private Button buttonSave;

    @FXML
    private Button buttonCancel;

    @FXML
    private TableView<Part> prodAllPartTableView;

    @FXML
    private TableColumn<Part, Integer> prodAddPartIdCol;

    @FXML
    private TableColumn<Part, String> prodAddPartNameCol;

    @FXML
    private TableColumn<Part, Integer> prodAddPartInvCol;

    @FXML
    private TableColumn<Part, Double> prodAddPartPriceCol;

    @FXML
    private TableView<Part> prodAssociatedPartTableView;

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
                prodAllPartTableView.getSelectionModel().select(Inventory.lookupPart(id));
            }
            else{
                prodAllPartTableView.getSelectionModel().clearSelection();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part Search");
                alert.setContentText("No results found");
                alert.show();
            }
        }
        else {
            Inventory.lookupPart(str);
            prodAllPartTableView.setItems(Inventory.getFilteredParts());
        }

        if(Inventory.getFilteredParts().isEmpty() && !str.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Search");
            alert.setContentText("No results found");
            alert.show();}
    }

    /**
     * @param event When the Add button is clicked, the highlighted part in the All Parts table (upper table) is added to a temporary list called tempAssociatedParts.
     */
    @FXML
    void onActionAddPart(ActionEvent event) {
        Part p = prodAllPartTableView.getSelectionModel().getSelectedItem();
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
     * @param event When the Remove button is clicked, the highlighted part in the Associated Parts table (lower table) is added to a temporary list called tempAssociatedParts. The tempAssociatedParts list is later used in the Product constructor when the product is saved.
     */
    @FXML
    void onActionRemovePart(ActionEvent event) {
        Part p = prodAssociatedPartTableView.getSelectionModel().getSelectedItem();
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
     * ID is generated to be 1 higher than the current highest ID number
     * Price is parsed as a Double.
     * Stock is parsed as an Integer.
     * Min and Max are parsed as Integers. These numbers can be equal. If the Min value is greater than the Max value, an alert box is shown to notify that user that min can't be more than max and both fields are set to be blank.
     * The tempAssociatedParts list is used to pass Part objects into the new Product object in the Product constructor.
     * ERROR HANDLING If any of the input values can't be parsed to Double or Integer as needed, a NumberFormatException is caught and an alert box is displayed to notify the user to enter valid number values.
     * FUTURE ENHANCEMENT Specify which fields are invalid in the alert box and highlight the boxes to guide the user to the fields that must be corrected.
     * Once the Product constructor is called and added to allProducts ObservableList, the application returns to the Main Form.
     * @throws IOException
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException {
        //Generate ID
        int maxId = 0;
        for(Product p : Inventory.getAllProducts()){
            int id = p.getId();
            if (id < maxId) {
                continue;
            }
            maxId = id;
        }
        maxId++;

        try {
            //Set Item Properties
            int id = maxId;
            String name = prodNameTxt.getText();
            double price = Double.parseDouble(prodPriceTxt.getText());
            int stock = Integer.parseInt(prodInvTxt.getText());
            int min = Integer.parseInt(prodMinTxt.getText());
            int max = Integer.parseInt(prodMaxTxt.getText());

            if(min > max) {
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

                //Use class constructor
                Product newProduct = new Product(id, name, price, stock, min, max, tempAssociatedParts);
                Inventory.addProduct(newProduct);
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
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Main Form.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param url
     * @param rb Add Product form initializes with all blank fields and two tables. The first table displays all parts using the getAllParts() method. The Associated Parts table displays objects in the tempAssociatedParts list, which is empty when first opening the Add Product form.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //All parts
        prodAllPartTableView.setItems(Model.Inventory.getAllParts());
        prodAddPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodAddPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodAddPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodAddPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        //Associated Parts
        prodAssociatedPartTableView.setItems(tempAssociatedParts);
        prodRemovePartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodRemovePartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodRemovePartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodRemovePartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}
