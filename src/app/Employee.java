
package app;


public class Employee {
    private int nEmployeeId;
    private String sFirstName;
    private String sLastName;
    private String sTitle;
    private String sCity;
    private String sCountry;

    public Employee(
            int id , String fn, String ln, String title, String city, String country
    ){
        this.nEmployeeId = id;
        this.sFirstName = fn;
        this.sLastName = ln;
        this.sTitle = title;
        this.sCity = city;
        this.sCountry = country;
    }
    
    public int getID(){
        return nEmployeeId;
    }
    
    public String getFirstName(){
        return sFirstName;
    }
    
    public String getLastName(){
        return sLastName;
    }
    
    public String getTitle(){
        return sTitle;
    }
    
    public String getCity(){
        return sCity;
    }
    
    public String getCountry(){
        return sCountry;
    }
    
    
    @Override
    public String toString() {
        return sFirstName + " " + sLastName;
    }
}
