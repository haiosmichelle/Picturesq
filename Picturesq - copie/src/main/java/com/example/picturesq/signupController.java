package com.example.picturesq;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signupController {
 @FXML
    private Button btnSignUp;
    @FXML
    private TextField email2;
    @FXML
    private TextField full_name;
    @FXML
    private PasswordField pass2;
    @FXML
    private PasswordField conpass;
    @FXML
    private VBox vbox;
    private Parent fxml;
    @FXML
    private ImageView image;
    public HashMap<String ,String> pass=new HashMap<>();
    @FXML
    private Label lab;
    FileChooser fc = new FileChooser();
    String url;
    public void btnUploadOnAction(ActionEvent actionEvent) {
        fc.setTitle("Open File");
        File selectedFile = fc.showOpenDialog(null);
         url = selectedFile.toURI().toString();
        if(selectedFile != null) {

            image.setImage(new Image(url));
            image.maxWidth(119);
            image.maxHeight(169);
        }
    }
   public void btnSignUpOnAction(ActionEvent event) throws NoSuchAlgorithmException {
       if(full_name.getText()!=null || pass2.getText()!=null || conpass.getText()!=null || email2.getText()!=null)
       {registerUser();}
       else {
       lab.setText("Please enter name, email and password");
       }
   }
   public void registerUser() throws NoSuchAlgorithmException {
       if(pass2.getText().equals(conpass.getText())){
            if(validationEmail(email2.getText()))
            { Encryptor encryptor=new Encryptor();
                String nume=full_name.getText();
                String email=email2.getText();
                String password=pass2.getText();
                DataBaseConnection connectNow=new DataBaseConnection();
                Connection connectDB=connectNow.getConnection();
                pass.put(email,password);
                String verify=" INSERT INTO user(nume, email, pass, image) VALUES('"+ nume+"','"+ email+ "','"+encryptor.encryptString(password)+"','"+url+"');";

                try{
                    Statement statement=connectDB.createStatement();
                    statement.executeUpdate(verify);
                    lab.setText("Congratulations!");
            }catch(Exception e)
           {   e.printStackTrace();
               e.getCause();}}else{
               lab.setText("Invalid Email. Please try again...");
            }
       }else{
           lab.setText("Invalid Password. Please try again...");
       }
   }
   public boolean validationEmail(String input)
   {
       String emailRegex="^[A-Z0-9._%+-]+@gmail+\\.com$";
       Pattern emailPat= Pattern.compile(emailRegex,Pattern.CASE_INSENSITIVE);
       Matcher matcher=emailPat.matcher(input);
       return matcher.find();

   }


}
