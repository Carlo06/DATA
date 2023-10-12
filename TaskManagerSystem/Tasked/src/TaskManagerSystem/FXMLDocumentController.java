/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package TaskManagerSystem;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author carlo
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Hyperlink alreadyhaveAccount;

    @FXML
    private Hyperlink createAccount;

    @FXML
    private javafx.scene.control.Button log_loginBtn;

    @FXML
    private PasswordField log_password;

    @FXML
    private javafx.scene.control.TextField log_username;

    @FXML
    private AnchorPane login_form;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField reg_password;

    @FXML
    private javafx.scene.control.Button reg_signupBtn;

    @FXML
    private javafx.scene.control.TextField reg_username;

    @FXML
    private AnchorPane signup_form;
    
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    private Alert alert;
    
    public void loginAccount(){
        
        String selectData = "SELECT username, password FROM user WHERE username = '"
                + log_username.getText() + "' and password = '" + log_password.getText() + "'";
        
        connect = database.connectDB();
        
        try{
            
            if(log_username.getText().isEmpty()|| log_password.getText().isEmpty() ){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            }else{
                prepare = connect.prepareStatement(selectData);
                result = prepare.executeQuery();
                
                if(result.next()){
                    
                    data.username = log_username.getText();
                    
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully login");
                    alert.showAndWait();
                    
                    log_loginBtn.getScene().getWindow().hide();
                    
                    Parent root = FXMLLoader.load(getClass().getResource("taskController.fxml"));
                    
                    Stage stage = new Stage();
                    stage.setTitle("Task Manager System");
                    
                    Scene scene = new Scene(root); 
                    stage.setScene(scene);
                    stage.show();
                    
                }else{
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect Username/Password");
                    alert.showAndWait();
                }
            }
            
            
            
        }catch(Exception e){e.printStackTrace();}
        
    }
    
    public void registerAccount(){
        String insertData = "INSERT INTO user(username, password, date) VALUES(?,?,?)";
        
        connect = database.connectDB();
        
        try{
            
            if(reg_username.getText().isEmpty() ||reg_password.getText().isEmpty() ){
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            }else{
                String checkUsername = "SELECT username FROM user WHERE username = '" 
                        + reg_username.getText() + "'";
                
                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();
                
                if(result.next()){
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(reg_username.getText() + "was already taken");
                    alert.showAndWait();
                }else{
                    
                    if(reg_password.getText().length() < 8){
                        alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Invalid Password, atleast 8 characters needed");
                        alert.showAndWait();
                    }else{
                        prepare = connect.prepareStatement(insertData);
                        prepare.setString(1, reg_username.getText());
                        prepare.setString(2,reg_password.getText());

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        prepare.setString(3, String.valueOf(sqlDate));
                        
                        prepare.executeUpdate();
                        
                        alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully create a new account");
                        alert.showAndWait();
                        
                        reg_username.setText("");
                        reg_password.setText("");
                        
                        signup_form.setVisible(false);
                        login_form.setVisible(true);
                        
                    }
                    
                }
            }
           
            
        }catch(Exception e){e.printStackTrace();}
    }
    
    
    public void switchForm(ActionEvent event){
        
        if(event.getSource() == createAccount){
            signup_form.setVisible(true);
            login_form.setVisible(false);
        }else if(event.getSource() == alreadyhaveAccount){
            signup_form.setVisible(false);
            login_form.setVisible(true);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
