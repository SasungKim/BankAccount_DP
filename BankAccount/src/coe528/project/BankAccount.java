/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe528.project;

import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Sasung Kim
 */
public class BankAccount extends Application {
    
    private Manager m = Manager.getManager();
    private Customer c;
    private double balance;
    private Label output = new Label();
    private File customerFile;

    // Customer file loading
        private File CustomerSetting(String username){
        File dir = new File("./Customers");
        File temp = null;
        File[] dir_contents = dir.listFiles();
                
        for(File fs:dir_contents){
            if(fs.getName().equals(username)){
                temp = fs;
                break;
            }
        }
        return temp;
    }
    
    public void AddCustomer() throws IOException {
        Stage s = new Stage();
        
        // Input Screen
        GridPane Add = new GridPane();
        Add.setPadding(new Insets(10,10,10,10));
        Add.setVgap(5);
        Add.setHgap(5);
        
        TextField UsernameAdd = new TextField();
        UsernameAdd.setPrefColumnCount(15);
        GridPane.setConstraints(UsernameAdd, 1, 0);
        
        TextField PasswordAdd = new TextField();
        GridPane.setConstraints(PasswordAdd, 1, 1);
        
        Label setID = new Label("Enter new ID:");
        GridPane.setConstraints(setID, 0, 0);
        
        Label setPW = new Label("Enter new password");
        GridPane.setConstraints(setPW, 0, 1);
        
        Button close = new Button("Close");
        GridPane.setConstraints(close, 3, 1);
        
        close.setOnAction(e->{
            s.close();
        });
        
        Button enter = new Button("Enter");
        GridPane.setConstraints(enter, 3, 0);
        
        enter.setOnAction(e-> {
            String username = UsernameAdd.getText();
            String password = PasswordAdd.getText();
            m.addCustomer(username, password);
            output.setText(m.getAddingText());
        });
        Add.getChildren().addAll(UsernameAdd, setID, PasswordAdd, setPW, enter, close);
        Add.add(output, 0, 3);
        Scene adding = new Scene(Add, 500, 150);
        s.setScene(adding);
        s.show();
    }

    public void deleteCustomer(){
        Stage s = new Stage();
        
        GridPane delete = new GridPane();
        delete.setPadding(new Insets(10,10,10,10));
        delete.setVgap(5);
        delete.setHgap(5);
        
        TextField UsernameDel = new TextField();
        UsernameDel.setPrefColumnCount(15);
        GridPane.setConstraints(UsernameDel, 1, 0);
        
        Button enter = new Button("Enter");
        GridPane.setConstraints(enter, 2, 0);
        
        Label deleting = new Label("Enter the name of customer for removing \nfrom the list:");
        GridPane.setConstraints(deleting, 0, 0);
        
        Button close = new Button("Close");
        GridPane.setConstraints(close, 2, 2);
        
        close.setOnAction(e->{
            s.close();
        });
        
        enter.setOnAction(e->{
        String username = UsernameDel.getText();
        m.deleteCustomer(username, CustomerSetting(username));
        output.setText(m.getDeleteMessage());
        });
        
        delete.getChildren().addAll(deleting, UsernameDel, enter, close);
        delete.add(output, 0, 2);
        Scene Delete = new Scene(delete, 600, 140);
        s.setScene(Delete);
        s.show();
    }
    
    public void Deposit() {
        Stage s = new Stage();
        
        GridPane input = new GridPane();
        input.setPadding(new Insets(10,10,10,10));
        input.setVgap(5);
        input.setHgap(5);
        
        TextField Balance = new TextField();
        Balance.setPrefColumnCount(15);
        GridPane.setConstraints(Balance, 1, 0);
        
        Button close = new Button("Close");
        GridPane.setConstraints(close, 3, 0);
        
        close.setOnAction(e->{
            s.close();
        });
        
        Button enter = new Button("Enter");
        GridPane.setConstraints(enter, 2, 0);
        
        Label setBal = new Label("Enter the amount of money to deposit:");
        GridPane.setConstraints(setBal, 0, 0);
            
        enter.setOnAction(dep->{
        output.setText(Double.toString(balance));
        balance = Double.parseDouble(Balance.getText());
            if(balance >= 0){
            try {
                c.loadFile(customerFile);
            } catch (IOException ex) {
            }
                String tempb = Double.toString(c.getBalance());
                String tempL = c.getLevel().toString();
                c.Deposit(balance);
                c.updateFile(customerFile, tempb, Double.toString(c.getBalance()));
                c.updateFile(customerFile, tempL, c.getLevel().toString());
                output.setText("$" + balance + " is deposited in the account.");
            }
            else
                output.setText("Please enter the valid money amount.");
            });
        input.getChildren().addAll(setBal, Balance, enter, close);
        input.add(output, 0, 2);
            Scene in = new Scene(input);
            s.setScene(in);
            s.show();
    }
    
    public void Withdraw(){
        Stage s = new Stage();
        
        GridPane input = new GridPane();
        input.setPadding(new Insets(10,10,10,10));
        input.setVgap(5);
        input.setHgap(5);
        
        TextField Balance = new TextField();
        Balance.setPrefColumnCount(15);
        GridPane.setConstraints(Balance, 1, 0);
        
        Button close = new Button("Close");
        GridPane.setConstraints(close, 3, 0);
        
        close.setOnAction(e->{
            s.close();
        });
        
        Button enter = new Button("Enter");
        GridPane.setConstraints(enter, 2, 0);
        
        Label setBal = new Label("Enter the amount of money to withdraw:");
        GridPane.setConstraints(setBal, 0, 0);
                        
        enter.setOnAction(ent->{
        output.setText(Double.toString(balance));
                balance = Double.parseDouble(Balance.getText());
            if(balance >= 0){
                if(c.getBalance() >= balance){
                    String tempb = Double.toString(c.getBalance());
                    String tempL = c.getLevel().toString();
                    c.Withdraw(balance);
                    c.updateFile(customerFile, tempb, Double.toString(c.getBalance()));
                    c.updateFile(customerFile, tempL, c.getLevel().toString());
                    output.setText("$" + balance + " is withdrawn from the account.");
                }
                else
                    output.setText("The account does not have enough money to withdraw");
            }
            else
                output.setText("Please enter the valid money amount.");
        });
        input.getChildren().addAll(setBal, Balance, enter, close);
        input.add(output, 0, 2);
        Scene in = new Scene(input);
        s.setScene(in);
        s.show();
    }
    
    public void OnlinePurchase(){
        Stage s = new Stage();
        
        GridPane input = new GridPane();
        input.setPadding(new Insets(10,10,10,10));
        input.setVgap(5);
        input.setHgap(5);
        
        TextField Balance = new TextField();
        Balance.setPrefColumnCount(15);
        GridPane.setConstraints(Balance, 1, 0);
        
        Button close = new Button("Close");
        GridPane.setConstraints(close, 3, 0);
        
        close.setOnAction(e->{
            s.close();
        });
        
        Button enter = new Button("Enter");
        GridPane.setConstraints(enter, 2, 0);
        
        Label setBal = new Label("Enter the amount of money to purchase:");
            GridPane.setConstraints(setBal, 0, 0);
            
            enter.setOnAction(ent->{
            balance = Double.parseDouble(Balance.getText());
            System.out.println(balance);
            if(balance >= 50){
                try {
                    c.loadFile(customerFile);
                } catch (IOException ex) {
                }
                String tempb = Double.toString(c.getBalance());
                String tempL = c.getLevel().toString();
                c.updateFile(customerFile, tempb, Double.toString(c.getBalance()));
                c.updateFile(customerFile, tempL, c.getLevel().toString());
                double payment = balance + c.getFee();
                if(c.getBalance() >= balance + c.getFee()){
                output.setText("$" + payment + " is purchased from the account.");
                }
                else
                    output.setText("Not enought money for purchase");
                c.onlinePurchase(balance);
            }
            else if (balance < 50 && balance >= 0)
                output.setText("Minimum money for \nOnline Purchase \nis $50");
            else
                output.setText("Please enter the valid money amount.");
        });
        input.getChildren().addAll(setBal, Balance, enter, close);
        input.add(output, 0, 2);
        Scene in = new Scene(input, 700, 110);
        s.setScene(in);
        s.show();
    }
    
    public void getBalance(){
        Stage s = new Stage();
        
        VBox Info = new VBox();
        Info.setSpacing(30);
        Info.setAlignment(Pos.CENTER);
        
        Button close = new Button("Close");
        GridPane.setConstraints(close, 0, 4);
        
        close.setOnAction(e->{
            s.close();
        });
        Label info0 = new Label("Role: " + c.getRole());
        Label info1 = new Label("Your ID: " + c.getUsername());
        Label info2 = new Label("Your balance: $" + c.getBalance());
        Label info3 = new Label("Your Level: " + c.getLevel().toString());
        Info.getChildren().addAll(info0, info1, info2, info3, close);
        Scene information = new Scene(Info, 220, 230);
        s.setScene(information); 
        s.show();
    }
    
    public void notInTheList(){
        Stage s = new Stage();
        
        VBox Info = new VBox();
        Info.setSpacing(30);
        Info.setAlignment(Pos.CENTER);
        
        Button close = new Button("Close");
        GridPane.setConstraints(close, 0, 4);
        
        close.setOnAction(e->{
            s.close();
        });
        
        Label no = new Label("ID not in the list");
        Info.getChildren().addAll(no, close);
        Scene information = new Scene(Info, 220, 200);
        s.setScene(information); 
        s.show();
    }
    
    public void error(Label error){
        Stage s = new Stage();
        
        VBox Info = new VBox();
        Info.setSpacing(30);
        Info.setAlignment(Pos.CENTER);
        
        Button close = new Button("Close");
        GridPane.setConstraints(close, 0, 4);
        
        close.setOnAction(e->{
            s.close();
        });
        
        Info.getChildren().addAll(error, close);
        Scene information = new Scene(Info, 220, 200);
        s.setScene(information); 
        s.show();
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        // Buttons
        // Log In Button
        Button login = new Button("Log in");
        GridPane.setConstraints(login, 2, 0);
        
        //Log Out Button
        Button LogOff = new Button("Log out");
        GridPane.setConstraints(LogOff, 10, 10);
        
        //Log Out Button for customer
        Button LogOut = new Button("Log out");
        GridPane.setConstraints(LogOff, 10, 10);
        
        //Exit Button
        Button exit = new Button("Exit");
        GridPane.setConstraints(exit, 2, 1);
        
        // Interfaces
        // main interface
        GridPane begin = new GridPane();
        begin.setPadding(new Insets(10, 10, 10, 10));
        begin.setVgap(5);
        begin.setHgap(5);
        begin.getChildren().add(login);
        begin.getChildren().add(exit);
        Label username = new Label("ID: ");
        GridPane.setConstraints(username, 0, 0);
        final TextField ID = new TextField();
        ID.setPromptText("Enter your ID.");
        ID.setPrefColumnCount(15);
        GridPane.setConstraints(ID, 1, 0);
        Label pw = new Label("Password: ");
        GridPane.setConstraints(pw, 0, 1);
        final TextField password = new TextField();
        password.setPromptText("Enter your password.");
        GridPane.setConstraints(password, 1, 1);
        begin.getChildren().addAll(username, ID, pw, password);
        Scene main = new Scene(begin);
        
        // Manager Interface
        GridPane manager = new GridPane();
        manager.setPadding(new Insets(10,10,10,10));
        manager.setVgap(5);
        manager.setHgap(5);
        Button addCustomer = new Button("Add Customer");
        GridPane.setConstraints(addCustomer, 0, 0);
        Button deleteCustomer = new Button("Remove Customer");
        GridPane.setConstraints(deleteCustomer, 0, 1);
        manager.getChildren().addAll(addCustomer, deleteCustomer, LogOff);
        Scene ManScreen = new Scene(manager);
        
        // Customer Interface        
        HBox customer = new HBox();
        customer.setSpacing(30);
        customer.setAlignment(Pos.CENTER);
        Button Deposit = new Button("Deposit");
        Deposit.setPrefWidth(300);
        Deposit.setPrefHeight(150);
        Button Withdraw = new Button("Withdraw");
        Withdraw.setPrefWidth(300);
        Withdraw.setPrefHeight(150);
        Button OnlinePurchase = new Button("Online Purchase");
        OnlinePurchase.setPrefWidth(300);
        OnlinePurchase.setPrefHeight(150);
        Button getBalance = new Button("Check Balance");
        getBalance.setPrefWidth(300);
        getBalance.setPrefHeight(150);
        customer.getChildren().addAll(Deposit, Withdraw, OnlinePurchase, getBalance);
        customer.getChildren().add(LogOut);
        Scene CusScreen = new Scene(customer);
        
        // Handlers
        // Log Off Button Handler
        LogOff.setOnAction(e -> {
            primaryStage.setScene(main);
        });
        
        // Log off button for customer
        LogOut.setOnAction(e -> {
            primaryStage.setScene(main);
        });
        
        // Exit Button Handler
        exit.setOnAction(e -> {
            System.exit(0);
        });
        
        // Log In Button Handler
        login.setOnAction(e ->{
            String id = ID.getText();
            String pass = password.getText();
            customerFile = CustomerSetting(id);
            if(id.equals("admin")){
                if(pass.equals("admin")){
                primaryStage.setScene(ManScreen);
                }
                else{
                    Label error = new Label ("Password does not match");
                    error(error);
                }
            }
            else if (customerFile == null){
                Label error = new Label("ID doesn't exist");
                error(error);
            }
            else if(id.equals(customerFile.getName())){
                c = new Customer(new Silver());
                try {
                    c.loadFile(customerFile);
                } catch (IOException ex) {
                    System.out.println("File doesn't exist");
                }
            String passcheck = c.getPassword();
            if (pass.equals(passcheck)) {
                LogOut.setPrefWidth(300);
                LogOut.setPrefHeight(150);
                primaryStage.setScene(CusScreen);
            }  
            else{
                Label error = new Label("Password doesn't match with ID");
                error(error);
            }
            }
        });
        
        // Add Customer Button Handler
        addCustomer.setOnAction(e->{
                try{
                AddCustomer();
                }
                catch(IOException ex){
                    System.out.println("error occured");
                }
        });
        
        // Deleting Customer Button Handler
        deleteCustomer.setOnAction(e->{
            deleteCustomer();
        });
        
        // Deposit Button Handler
        Deposit.setOnAction(e->{
            Deposit();
        });
        
        // Withdraw Button Handler
        Withdraw.setOnAction(e-> {
            Withdraw();
        });

        // Online Purchase Button Handler
        OnlinePurchase.setOnAction(e->{
            OnlinePurchase();
        });
        
        // Get Balance Button Handler
        getBalance.setOnAction(e->{
            getBalance();
            });
        primaryStage.setTitle("Bank Account");
        primaryStage.setScene(main);
        primaryStage.show();
        }
    

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        System.out.println();
        launch(args);
        }
    }