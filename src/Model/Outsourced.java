/**
 *
 * @author Nicolaus Shaffer
 */

package Model;
public class Outsourced  extends Part{
    private String companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

   /**
   * @param companyName set the companyName of the Outsourced object.
   */
   public void setCompanyName(String companyName){
       this.companyName = companyName;
   }

   /**
   * @return the companyName of the Outsourced object.
   */
   public String getCompanyName(){
       return this.companyName;
   }
}
