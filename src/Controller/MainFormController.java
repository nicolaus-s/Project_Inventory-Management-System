/**
 *
 * @author Nicolaus Shaffer
 */

package Controller;

import Model.Inventory;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ConcurrentModificationException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Parent;
import Model.Product;


public class MainFormController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Label sceneLabel;

    @FXML
    private AnchorPane partAnchorPane;

    @FXML
    private Label partTableLbl;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvLvlCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private Button buttonPartAdd;

    @FXML
    private Button buttonPartModify;

    @FXML
    private Button buttonPartDelete;

    @FXML
    private TextField partSearch;

    @FXML
    private AnchorPane prodAnchorPane;

    @FXML
    private Label prodTableLbl;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> prodIdCol;

    @FXML
    private TableColumn<Product, String> prodNameCol;

    @FXML
    private TableColumn<Product, Integer> prodInvCol;

    @FXML
    private TableColumn<Product, Double> prodPriceCol;

    @FXML
    private Button buttonProdDelete;

    @FXML
    private Button buttonProdModify;

    @FXML
    private Button buttonProdAdd;

    @FXML
    private TextField prodSearch;

    @FXML
    private Button buttonExit;

    /**
     * @param event When characters are typed, the table contents are filtered to matching part titles. If numbers are typed, all parts are displayed and the matching ID is highlighted in the table. If there are no matches an alert window if no results are found.
     * ERROR HANDLING If the search input is not a number, the Integer.parseInt() method fails and sets a boolean variable to true. If the boolean is true, the lookupPart(int) method id bypassed and the lookupPart(String) method is used.
     * ERROR HANDLING If no matches are found from the search, an alert box is displayed to notify that no results were found.
     */
    @FXML
    void onActionPartSearch(KeyEvent event) {
        String str = partSearch.getText();
        int id = 0;
        boolean errorOccurred = false;

        try{
            id = Integer.parseInt(str);
        }
        catch (NumberFormatException e){
            errorOccurred = true;
        }

        if(!errorOccurred){
            Part part = Inventory.lookupPart(id);
            if(part.getId() == id && part.getId() > 0){
                partTableView.getSelectionModel().select(Inventory.lookupPart(id));
            }
            else{
                partTableView.getSelectionModel().clearSelection();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part Search");
                alert.setContentText("No results found");
                alert.show();
            }
        }
        else {
            Inventory.lookupPart(str);
            partTableView.setItems(Inventory.getFilteredParts());
        }
        if(Inventory.getFilteredParts().isEmpty() && !str.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Search");
            alert.setContentText("No results found");
            alert.show();}
    }

    /**
     * @param event When characters are typed, the table contents are filtered to matching product titles. If numbers are typed, all products are displayed and the matching ID is highlighted in the table. If there are no matches an alert window if no results are found.
     * ERROR HANDLING If the search input is not a number, the Integer.parseInt() method fails and sets a boolean variable to true. If the boolean is true, the lookupProduct(int) method id bypassed and the lookupProduct(String) method is used.
     * ERROR HANDLING If no matches are found from the search, an alert box is displayed to notify that no results were found.
     */
    @FXML
    void onActionProductSearch(KeyEvent event) {
        String str = prodSearch.getText();
        int id = 0;
        boolean errorOccurred = false;

        try{
            id = Integer.parseInt(str);
        }
        catch (NumberFormatException e){
            errorOccurred = true;
        }

        if(!errorOccurred){
           Product prod = Inventory.lookupProduct(id);
            if(prod.getId() == id && prod.getId() > 0){
                productTableView.getSelectionModel().select(Inventory.lookupProduct(id));
            }
            else{
                productTableView.getSelectionModel().clearSelection();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Product Search");
                alert.setContentText("No results found");
                alert.show();
            }
        }
        else {
            Inventory.lookupProduct(str);
            productTableView.setItems(Inventory.getFilteredProducts());
        }

        if(Inventory.getFilteredProducts().isEmpty() && !str.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Search");
            alert.setContentText("No results found");
            alert.show();}

    }

    /**
     * @param event When the Add button (under Parts) is clicked, the 'Add Part' form is displayed.
     * @throws IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Add Part.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event When the Add button (under Products) is clicked, the 'Add Product' form is displayed.
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/View/Add Product.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * @param event When the Modify button (under Parts) is clicked, the 'Modify Part' form is displayed and populated with the part details.
     * @throws IOException
     * ERROR HANDLING If the Modify button is clicked when no part is selected, an alert box is displayed to notify the user that no part was selected.
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Modify Part.fxml"));
        loader.load();
        ModifyPartController MPC = loader.getController();
        Part p = partTableView.getSelectionModel().getSelectedItem();
        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modify Part");
            alert.setContentText("No part selected.");
            alert.show();
        } else {
            MPC.loadPart(p);
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * @param event When the Modify button (under Products) is clicked, the 'Modify Product' form is displayed and populated with the product details.
     * @throws IOException
     * ERROR HANDLING If the Modify button is clicked when no product is selected, an alert box is displayed to notify the user that no product was selected.
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/Modify Product.fxml"));
        loader.load();
        ModifyProductController ModProd = loader.getController();
        Product p = productTableView.getSelectionModel().getSelectedItem();
        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modify Product");
            alert.setContentText("No product selected.");
            alert.show();
        } else {
            ModProd.loadProduct(productTableView.getSelectionModel().getSelectedItem());
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * @param event When the Delete button (under Parts) is clicked, the selected part in the Parts table is deleted and an alert box is shown to confirm the deletion.
     * ERROR HANDLING If the Delete button is clicked when no part is selected, an alert box is displayed to notify the user that no part was selected.
     * ERROR HANDLING When the part is deleted, a ConcurrentModificationException is thrown. This error is consequential; when caught, no further action is taken.
     * FUTURE ENHANCEMENT Prevent the ConcurrentModificationException from occurring at all.
     * @throws IOException
     */

    @FXML
    void onActionDeletePart(ActionEvent event) {
        Part p = partTableView.getSelectionModel().getSelectedItem();
        if (p == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Part");
            alert.setContentText("No part selected.");
            alert.show();
        } else {
            try {
                Inventory.deletePart(p);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Part deleted");
                alert.setContentText("The selected part has been deleted.");
                alert.show();
            } catch (ConcurrentModificationException e) {
                //Do nothing
            }
        }
    }


    /**
     * @param event When the Delete button (under Products) is clicked, the selected part in the Products table is deleted and an alert box is shown to confirm the deletion. If the selected product has any associated parts, an alert box is displayed to notify the user that products with associated parts can not be deleted.
     * ERROR HANDLING If the Delete button is clicked when no product is selected, an alert box is displayed to notify the user that no product was selected.
     * @throws IOException
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event){
            Product p = productTableView.getSelectionModel().getSelectedItem();
            if (p == null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Product");
                alert.setContentText("No product selected.");
                alert.show();
            } else {
                if (p.getAllAssociatedParts().isEmpty()) {
                    Inventory.deleteProduct(p);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Product deleted");
                    alert.setContentText("The selected product has been deleted.");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Delete Product");
                    alert.setContentText("This product cannot be deleted while it has associated parts. Please remove associated parts prior to deleting this product.");
                    alert.show();
                }

            }
        }

    /**
     * @param event When the Exit button is clicked, the application is closed.
     */
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * @param url
     * @param rb Main Form initializes with two tables: Parts and Products. Each table is populated with getAllParts and getAllProducts respectively. Both tables are blank until Parts and Products are added to the allParts and allProducts ObservableLists through the Add Part and Add Products forms.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //All Parts
        partTableView.setItems(Model.Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvLvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        //All Products
        productTableView.setItems(Model.Inventory.getAllProducts());
        prodIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

}