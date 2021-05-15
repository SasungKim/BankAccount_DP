/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528.project;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
/**
 *
 * @author Sasung Kim
 */
public class Manager{
    private final String role ="Manager", username = "admin", password = "admin";
    private static Manager m;
    private ArrayList<Customer>customer = new ArrayList<Customer>();
    private String addingText, delText, exist;
    File newCustomer;
    
    private Manager(){

    }
    
    public String getRole(){
        return role;
    }
    
    
    public String getUsername(){
        return username;
    }
    
    
    public String getPassword(){
        return password;
    }
    
    public ArrayList<Customer> getCustomer(){
        return customer;
    }
    
    //Singleton Pattern
    public static Manager getManager(){
        if (m == null)
            m = new Manager();
        return m;
    }
    
    public void addCustomer(String name, String pass){
        if(name.isEmpty() || pass.isEmpty()){
                    addingText = ("ID or password is missing");
                }
                else if(new File("./Customers", name).exists() == false){
                    Customer c = new Customer(new Silver());
                    c.setUsername(name);
                    c.setPassword(pass);
                    customer.add(c);
                    try{
                    CreateFile(name, pass);
                    put(name, pass, c.getLevel().toString(), 
                            Double.toString(c.getBalance()), newCustomer);
                    }
                    catch(IOException ex){
                        System.out.println("Cannot use that username or password");
                    }
                    addingText = ("Your account with ID: \n"+ name + "is set");
                }
                else{
                    addingText = ("This ID already exists.");
                }
    }
    
    public String getAddingText(){
        return addingText;
    }
    
    public Customer newCustomer(){
        return customer.get(customer.size()-1);
    }
    
    public void deleteCustomer(String name, File file){
        for (Customer c:customer)
            if(c.getUsername().equals(name)){
            customer.remove(c);
            break;
            }
            if (name == null)
                delText = ("Please enter ID");
            
            else if(new File("./Customers", name).exists()){
                if(file.delete())
                    delText = ("Customer is removed successfully");
            }
            else
                    delText = ("Customer does not exist");
    }
      
    public String getDeleteMessage(){
        return delText;
    }
    
    public void CreateFile(String name, String password) throws IOException{
        newCustomer = new File("./Customers/"+ name);
        
        if(newCustomer.createNewFile())
            exist = "User account is successfully added";
        else{
            exist = "Username already exists";
        }
    }
    
     public void put(String username, String password, String Level, String balance, File file)throws IOException{
        PrintWriter writer = null;
        try{
            writer = new PrintWriter(new java.io.FileWriter(file));
            writer.println(username);
            writer.println(password);
            writer.println(Level);
            writer.println(balance);
        }
        finally{
            if(writer != null){
                writer.close();
            }
        }
    } 
}
