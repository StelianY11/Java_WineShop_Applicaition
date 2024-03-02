
package app;

public class Customer {
    private int nCustomerId;
    private String sFirstName;
    private String sLastName;
    private String sCity;
    private String sCountry;
    private String sEmail;


    public Customer(int nCustomerId, String sFirstName, String sLastName){
        this.nCustomerId = nCustomerId;
        this.sFirstName = sFirstName;
        this.sLastName = sLastName;
    }
    
    public Customer(int nCustomerId, String sFirstName, String sLastName, String sCity, String sCountry, String sEmail){
        this.nCustomerId = nCustomerId;
        this.sFirstName = sFirstName;
        this.sLastName = sLastName;
        this.sCity = sCity;
        this.sCountry = sCountry;
        this.sEmail = sEmail;
    }

    public Object[] toArray(){
        return new Object[]{
            nCustomerId,sFirstName,sLastName,sCity,sCountry,sEmail};
    }  
    
    @Override
    public String toString() {
        return sFirstName + " " + sLastName;
    }
}
