package com.example.picturesq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class regController  {
    @FXML
    private ImageView Pofile;
    @FXML
    public Label Name;
    @FXML
    private Label mesaj;
    @FXML
    private PasswordField pass;
    @FXML
    public TextField email;
    Stage stage=new Stage();
    public void btnRegisterssetOnAction(ActionEvent actionEvent) throws NoSuchAlgorithmException {
        if (!email.getText().isBlank() && !pass.getText().isBlank()) {
            validatesignin();
            //mesaj.setText("You try to sign in");
        } else {
            mesaj.setText("Please enter email and password ");
        }
    }
    private void validatesignin() throws NoSuchAlgorithmException {
        Encryptor encryptor=new Encryptor();
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDB=connectNow.getConnection();
        String verify=" SELECT count(1) FROM user WHERE email='"+ email.getText() +"' AND pass= '"+ encryptor.encryptString(pass.getText())+ "';";
        try{
            Statement statement=connectDB.createStatement();
            ResultSet queryResult=statement.executeQuery(verify);
            while(queryResult.next()){
                if(queryResult.getInt(1)==1){
                    Controller con=new Controller();
                    User.email=email.getText();
                    con.start(email.getText());

                    email.setText("");
                    pass.setText("");
                    mesaj.setText("");
                }else{
                    mesaj.setText("Invalid Login. Please try again!");
                }

            }

        }catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
//    public void start() throws SQLException {
//        DataBaseConnection connectNow=new DataBaseConnection();
//        Connection connectDB=connectNow.getConnection();
//        String v="SELECT * FROM user WHERE email='"+email.getText()+"';";
//            Statement statement=connectDB.createStatement();
//            ResultSet queryResult=statement.executeQuery(v);
//        while ( queryResult.next() ) {
//            Name.setText(queryResult.getString("nume"));
//            String url = queryResult.getString("image");
//             Pofile.setImage(new Image(url));
//
//    }}
}

