/**
 *
 * @author Nicolaus Shaffer
 */

package Model;
public class InHouse extends Part {
    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @param machineId Sets the machineId of the InHouse object
     */
    public void setMachineID(int machineId) {

        this.machineId = machineId;
    }

    /**
     * @return the machineId of the InHouse object
     */
    public int getMachineID() {

        return machineId;
    }
}