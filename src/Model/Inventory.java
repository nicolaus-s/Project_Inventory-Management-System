/**
 *
 * @author Nicolaus Shaffer
 */

package Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**
     * @param newPart added to allParts.
     * FUTURE ENHANCEMENT Build in error handling instead of error handling outside of this function.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct added to allProducts. An enhancement to this function would be to build in error handling instead of error handling outside of this function.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * @param partId Id of the part to search for.
     * @return the part from allParts
     * ERROR HANDLING If no part ID matches, create and return a part with the ID of 0 to prevent an error.
     */
    public static Part lookupPart(int partId) {
        int i = -1;
        Part result = new InHouse(0,"Zero", 0,0, 0, 0, 0);
        for (Part part : Inventory.getAllParts()) {
            i++;
            if (part.getId() == partId) {
                result =  allParts.get(i);
                break;
            }
        }
        return result;
    }

    /**
     * @param productId Id of the part to search for.
     * @return the product from allProducts.
     * ERROR HANDLING If no product ID matches, create and return a product with the ID of 0 to prevent an error.
     */
    public static Product lookupProduct(int productId) {
        int i = -1;
        ObservableList<Part> emptyAssociatedParts = FXCollections.observableArrayList();
        Product result = new Product(0,"Zero",0,0,0,0,emptyAssociatedParts);
        for (Product prod : Inventory.getAllProducts()) {
            i++;
            if (prod.getId() == productId) {
                return allProducts.get(i);
            }
        }
        return result;
    }

    /**
     * @param partName Name of the part to search for.
     * @return filteredParts ObservableList. Populates filteredParts with Part objects that are partial matches to partName.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        filteredParts.clear();
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().contains(partName)) {
                filteredParts.add(part);
            }
        }
        return filteredParts;
    }

    /**
     * @param productName Name of the product to search for.
     * @return filteredProducts ObservableList. Populates filteredProducts with Product objects that are partial matches to productName.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        filteredProducts.clear();
        for (Product prod : Inventory.getAllProducts()) {
            if (prod.getName().contains(productName)) {
                filteredProducts.add(prod);
            }
        }
        return filteredProducts;
    }

    /**
     * @param selectedPart updated in allParts
     * @param index position in the ObservableList where the object exists
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * @param selectedProduct updated in allProducts
     * @param index position in the ObservableList where the object exists
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * @param selectedPart Selected Part object to delete.
     * @return boolean variable named success. Success is set to true after deleting selectedPart from allParts. Success is false if no matching Part is found.
     */
    public static boolean deletePart(Part selectedPart) {
        int id = selectedPart.getId();
        boolean success = false;
        for(Part p : Inventory.getAllParts()){
            if(p.getId() == id){
                success = Inventory.getAllParts().remove(p);
            }
        }
        return success;
    }

    /**
     * @param selectedProduct Selected Product object to delete.
     * @return boolean variable named success. Success is set to true after deleting selectedProduct from allProducts. Success is false if no matching Product is found.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        int id = selectedProduct.getId();
        boolean success = false;
        for(Product p : Inventory.getAllProducts()){
            if(p.getId() == id){
                success = Inventory.getAllProducts().remove(p);
            }
        }
        return success;
    }

    /**
     * @return all parts in allParts
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /**
     * @return all products in allProducts
     */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

    /**
     * @return filteredParts. Used in tables to provide search results.
     */
    public static ObservableList<Part> getFilteredParts() {
        return filteredParts;
    }

    /**
     * @return filteredProducts. Used in tables to provide search results.
     */
    public static ObservableList<Product> getFilteredProducts() {
        return filteredProducts;
    }
}