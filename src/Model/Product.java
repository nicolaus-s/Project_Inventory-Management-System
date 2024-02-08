/**
 *
 * @author Nicolaus Shaffer
 */

package Model;
import javafx.collections.ObservableList;
public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max, ObservableList<Part> associatedParts){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = associatedParts;
    }

    /**
     * @return the id of the Product object.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set for the Product object.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name of the Product object.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set for the Product object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price of the Product object.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set for the Product object.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock of the Product object.
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set for the Product object.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min of the Product object.
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set for the Product object.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max of the Product object.
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set for the Product object.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part the part to set for the Product object.
     */
    public void addAssociatedPart(Part part){
        this.associatedParts.add(part);
    }

    /**
     * @param selectedAssociatedPart Selected Part object to remove from the associatedParts ObservableList.
     * @return boolean variable named success. Success is set to true when the associatedPart is deleted. Success is false if no matching associatedPart is found.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        int id = selectedAssociatedPart.getId();
        boolean success = false;
        for(Part p : this.getAllAssociatedParts()){
            if(p.getId() == id){
                success = this.getAllAssociatedParts().remove(p);
            }
        }
        return success;
    }

    /**
     * @return the associatedParts for the Product object.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return this.associatedParts;
    }
}
