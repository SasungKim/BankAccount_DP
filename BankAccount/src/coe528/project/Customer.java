/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Sasung Kim
 */

public class Customer implements Cloneable{

//Overview: Mutable class that loads informations from given Customer file 
//and do bank operations with the informations includes Deposit, Withdraw and Online Purchase
    
    /*
    * Abstract Function
    *   AF(c) = a Customer d
    *       where c.role = d.role
    *       c.balance = d.balance
    *       c.username = d.username
    *       c.password = d.password
    *       c.level = d.level
    *       c.bankAccunt = d.bankAccount
    *
    *   Rep Invariant: 
    *       c.balance >= 0
    *       c.username != null
    *       c.passwrd != null
    *       c.level != null
    *       c.bankAccunt >= 0
    *       c.username is unique in the list (no duplicated username)
    */
    

    private final String role = "Customer";
    private Level level;
    private double balance;
    private String username, password;
    private static int bankAccount = 0;
    
    public Customer(Level lvl){
        balance = 100;
        this.level = lvl;
        bankAccount++;
    }
    
    public void loadFile(File file) throws IOException{
        /*
        * Effect: load the informations from the given file and set those as instance variables
        * Requires: file is not null (file exists) and first four lines in the file are not null
        */
        FileInputStream FI = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(FI));
        username = br.readLine();
        password = br.readLine();
        br.readLine();
        balance = Double.parseDouble(br.readLine());
    }
    
    static void updateFile(File file, String oldString, String newString)
    {
        /*
        *Effect: Find certain informations/texts in the file and replace it with new informations/texts
        *Requires: The file is not null (file exists), oldString, newString is not null, file contains oldString
        *Modifiers: file
        */
        
        String oldContent = "";
        BufferedReader reader = null;
        FileWriter writer = null;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) 
            {
                oldContent = oldContent + line + System.lineSeparator();    
                line = reader.readLine();
            }
            String newContent = oldContent.replaceAll(oldString, newString);
            writer = new FileWriter(file);
            writer.write(newContent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                reader.close();
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }
    
    public String getRole(){
        /*
        *Effect: return role as String
        *Requires: role is not null
        */
        return role;
    }
    
    public String getUsername(){
        /*
        *Effect: return username as String
        *Requires: username is not null
        */
        return username;
    }
    
    public String getPassword(){
        /*
        *Effect: return password as String
        *Requires: password is not null
        */
        return password;
    }
    
    public double getBalance(){
        /*
        *Effect: return balance as double
        *Requires: balance >= 0
        */
        return balance;
    }
    
    public double getFee(){
        /*
        *Effect: return online purchase fee as double
        *Requires: level.getFee() >= 0
        */
        return level.getFee();
    }
    
    public int getBankAccount(){
        /*
        *Effect: return bankAccount as int
        *Requires: bankAccount>=0
        */
        return bankAccount;
    }
    
    public void setUsername(String Id){
        /*
        *Effect: Set the username to Id
        *Requires: Id is not null
        *Modifiers: this
        */
        this.username = Id;
    }
    
    public void setPassword(String pw){
        /*
        *Effect: Set password to pw
        *Requires: pw is not null
        *Modifiers: this
        */
        this.password = pw;
    }
    
    public void Deposit(double dep){
        /*
        *Effect: Add balance with dep and change the level (if needed)
        *Requires: dep >= 0
        *Modifiers: this
        */
        balance = balance + dep;
        level.Change(this);
    }
    
    public void Withdraw(double wit){
        /*
        *Effect: Subtract balance with wit and change the level (if needed)
        *Requires: wit >= 0 && balance >= wit
        *Modifiers: this
        */
        if(balance >= wit){
        balance = balance - wit;
        level.Change(this);
        }
    }
    
    public void onlinePurchase(double price){
        /*
        *Effect: Subtract balance with (price + online purchase fee) and change the level (if needed)
        *Requires: price >= 0, price + level.getFee() <= balance
        *Modifiers: this
        */
        if(balance >= price+level.getFee()){
        balance = balance - price - level.getFee();
        level.Change(this);
        }
    }
    
    //State pattern
    public Level getLevel(){
        /*
        *Effect: return level as Level
        *Requires: level != null
        */
        return level;
    }
    
    public void setLevel(Level l){
        /*
        *Effect: Set level to l
        *Requires: l is not null
        *Modifiers: this
        */
        level = l;
    }
    
    public boolean repOk(){
        /*
        *Effect: Check the valid conditions
        */
        if (username == null || username.isEmpty()) return false;
        if(password == null || password.isEmpty()) return false;
        if(balance < 0) return false;
        Manager m = Manager.getManager();
        for (Customer c: m.getCustomer()){
            if (c.getUsername().equals(username))
                return false;
        }
        return true;
    }
    
    @Override
    public String toString(){
        /* 
        *   Effect: return all the informations in Customer class in String type
        *   Requires: bankAccunt >= 0 && username != null && password != null, balance >= 0, level.toStrng != null
        */
        return ("Bank Account #" + bankAccount + "ID: "+ username + ", password: " + password + ", balance: " + balance + ", level: " + level.toString());
    }
}
