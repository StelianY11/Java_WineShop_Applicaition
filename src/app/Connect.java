
package app;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class Connect {
    public Connection conn;
    public Statement stmt;
    public ResultSet rs;
    
    public Connect(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:chinook.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }
    
    public ArrayList<String> select(String[] saColumns, String sTable){
        ArrayList<String> data = new ArrayList();
        String sColumns = String.join(", ", saColumns);
        String sql = "SELECT " + sColumns + " from " + sTable;
        try{
            System.out.println(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
               String sItem="";
                for (int i = 0; i < saColumns.length; i++) {
                    sItem+=rs.getString(saColumns[i]) + "---";
                }
                sItem=sItem.substring(0,sItem.length()-3);
                data.add(sItem);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public ArrayList<String> selectWhere(){
        ArrayList<String> data = new ArrayList();
        String sql = 
        "SELECT FirstName, LastName FROM Employees where firstname like 'S%'";
        
        try{
            System.out.println(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
               data.add(rs.getString("FirstName") + " " 
                       + rs.getString("LastName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
     public ArrayList<Customer> loadCustomerNames(){
        ArrayList<Customer> data = new ArrayList();
        String sql = "select customerid, firstname, lastname from customers";
        
        
        try{
            System.out.println(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
               int nId = rs.getInt("customerid"); //4
               String sFn = rs.getString("firstname"); // nancy
               String sLn = rs.getString("lastname"); //edwards
               data.add(new Customer(nId,sFn,sLn));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
     
    public ArrayList<Customer> loadCustomerData(String filter) {
        ArrayList<Customer> data = new ArrayList();

        String sql = "SELECT Customerid, FirstName, LastName, City, Country, email FROM Customers";
        if (filter.trim().length() > 0) {
            sql += " WHERE " + filter;
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int nCustomerId = rs.getInt("customerid");
                String sFn = rs.getString("FirstName");
                String sLn = rs.getString("LastName");
                String sCity = rs.getString("City");
                String sCountry = rs.getString("Country");
                String sEmail = rs.getString("Email");
                data.add(new Customer(nCustomerId, sFn, sLn, sCity, sCountry, sEmail));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    public ArrayList<Products> loadProductData(String filter) {
        ArrayList<Products> data = new ArrayList();

        String sql = "SELECT Productid, Name, Price FROM Products";
        if (filter.trim().length() > 0) {
            sql += " WHERE " + filter;
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int nProductId = rs.getInt("Productid");
                String sName = rs.getString("Name");
                String sPrice = rs.getString("Price");
                data.add(new Products(nProductId, sName, sPrice));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    public ArrayList<Employee> loadEmployeeData(String filter) {
        ArrayList<Employee> data = new ArrayList();

        String sql = "SELECT Employeeid, FirstName, LastName, Title, City, Country FROM Employees";
        if (filter.trim().length() > 0) {
            sql += " WHERE " + filter;
        }
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int nEmployeeId = rs.getInt("employeeid");
                String sFn = rs.getString("FirstName");
                String sLn = rs.getString("LastName");
                String sTitle = rs.getString("Title");
                String sCity = rs.getString("City");
                String sCountry = rs.getString("Country");
                
                data.add(new Employee(nEmployeeId, sFn, sLn, sTitle,sCity, sCountry));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
     
     
    //login,update,delete,insert
     
    public void insert(String table, String[] columns, String[] values){
        String sql = "";
        //columns [0]username,[1]password
        String cols = String.join(", ", columns);
        //cols = username, password
        //values -> [0]aaa,[1]1234
        String vals = String.join("', '",values);
        //vals = aaa', '1234
        sql = String.format("insert into %s (%s) values ('%s')",table,cols,vals);
        //insert into users (username, password) values ('aaa', '1234')
        try{
            System.out.println(sql);
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void insertP(String table, String[] columns, String[] values){
        String sql = "";
        //columns [0]username,[1]password
        String cols = String.join(", ", columns);
        //cols = username, password
        //values -> [0]aaa,[1]1234
        String placeholders="";
        for (int i = 0; i < values.length; i++) {
            placeholders += "?";
            if(i<values.length-1)
                placeholders+=", ";
        }
        sql = String.format("insert into %s (%s) values (%s)",table,cols,placeholders);
        //insert into users (username, password) values ('aaa', '1234')
        try{
            System.out.println(sql);
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (int i = 0; i < values.length; i++) {
                stmt.setString(i+1, values[i]);
            }
            int res = stmt.executeUpdate();
            System.out.println("Rows affected: " + res);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public void delete(String table, String column, String val) {
        String sql = "DELETE FORM " + table + " WHERE " + column + " = ?";

        try {
            System.out.println(sql);
            PreparedStatement stmt;
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, val);
            stmt.executeUpdate(sql);
            int rows = stmt.executeUpdate();
            System.out.println("Rows affected: " + rows);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    

    public ArrayList<String> loginP(String user, String pass) {
        ArrayList<String> data = new ArrayList();
        String sql = "select userid, username, password, status from users where username = ? and password = ? ";
        try {
            System.out.println(sql);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user);
            stmt.setString(2, pass);
            //...where username = 'admin' and password = 'admin'"
            stmt.execute();
            rs = stmt.getResultSet();
            while (rs.next()) {
                data.add(rs.getInt("userid") + "---"
                        + rs.getString("username") + "---"
                        + rs.getString("password") + "---"
                        + rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public ArrayList<String> login(String user, String pass) {
        ArrayList<String> data = new ArrayList();
        String sql = "";
        sql = String.format("select userid, username, password, "
                + "status from users where username = '%s' and password = '%s' ", user, pass);
        try {
            System.out.println(sql);
            stmt = conn.createStatement();
            //...where username = 'admin' and password = 'admin'"
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                data.add(rs.getInt("userid") + "---"
                        + rs.getString("username") + "---"
                        + rs.getString("password") + "---"
                        + rs.getString("status"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    //update customers set firstname='aaa', lastname='bbb' where customerid=2
    public void update(String table, String[] columns, String[] values, String whereCol, String whereVal){
        if (columns.length!=values.length) {
            System.out.println("различен брой колони и стойности!");
            return;
        }
        String body="";
        for (int i = 0; i < columns.length; i++) {
            body+=columns[i] + " = '" + values[i] +"', ";
        }
        body=body.substring(0,body.length()-2);
        String sql = 
           String.format("update %s set %s where %s = %s",table,body,whereCol,whereVal);
        try{
            System.out.println(sql);
            stmt = conn.createStatement();
            //delete from customers where customerid = 77
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public void updateP(String table, String[] columns, String[] values, String whereCol, String whereVal){
       if (columns.length!=values.length) {
            System.out.println("различен брой колони и стойности!");
            return;
        }
        String body="";
        for (int i = 0; i < columns.length; i++) {
            body+=columns[i] + " = ?, ";
        }
        body=body.substring(0,body.length()-2);
        String sql = String.format("update %s set %s where %s = ?",table,body,whereCol);
        try{
            System.out.println(sql);
            PreparedStatement stmt = 
               conn.prepareStatement(sql);
            int i = 0;
            for (i = 0; i < values.length; i++) {
                stmt.setString(i+1, values[i]);
            }
            //update users set username = "qkuser", password = "qkaparola", status = "admin"
            stmt.setString(i+1,whereVal);
            //... where userid = 3
            int res = stmt.executeUpdate();
            System.out.println("Rows affected: " + res);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

     
    public void close(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Throwable ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
