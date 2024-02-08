/**
* Supplied class Model.Part.java
 */

/**
 *
 * @author Nicolaus Shaffer
 */
package Model;
public abstract class Part {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Part(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id of the Part object.
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set for the Part object.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name of the Part object.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set for the Part object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price of the Part object.
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set for the Part object.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock of the Part object.
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set for the Part object.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min of the Part object.
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set for the Part object.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max of the Part object.
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set for the Part object.
     */
    public void setMax(int max) {
        this.max = max;
    }

}