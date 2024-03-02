
package app;


public class Products {
    private int nProductId;
    private String sName;
    private String sPrice;
    
    public Products(int id , String n, String p){
        this.nProductId = id;
        this.sName = n;
        this.sPrice = p;
    }
    
    public Object[] toArray(){
        return new Object[]{
            nProductId,sName,sPrice};
    }  
}
