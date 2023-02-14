package com.example.picturesq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class PostController {

    @FXML
    private Button btnLikes;

    @FXML
    private Label comment;

    @FXML
    private Label data;

    @FXML
    private Label likes;

    @FXML
    private ImageView postImage;

    @FXML
    private ImageView profileImage;

    @FXML
    private Label username;
    public void SetDate(Post post)
    {
        System.out.println(post.getPostImageSrc());
        Image image = new Image(post.getPostImageSrc());
        postImage.setImage(image);//new Image(post.getPostImageSrc()));

        image = new Image(post.getProfileImageSrc());
        profileImage.setImage(image);//new Image(post.getProfileImageSrc()));
        username.setText(post.getUsername());
        data.setText(post.getDate());
        likes.setText(post.getNbLikes());
        comment.setText(post.getNbComments());
    }

    public void BtnLikesOnAction(ActionEvent actionEvent) {
        int number = Integer.parseInt(likes.getText());
        number++;
        likes.setText(String.valueOf(number));
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDB=connectNow.getConnection();

        String verify="UPDATE Post SET Likes='"+likes.getText()+"' where UserName='"+username.getText()+"'";
        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(verify);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void BtnCommentsOnAction(ActionEvent actionEvent) {

    }
}
