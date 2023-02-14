package com.example.picturesq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Label Name;
    public ImageView Pofile;
    public ImageView imag_post;
    public Button addPhoto;
    public Button AddPost;
    public TextField TitlePost;
    public Text mesaj;
    @FXML
    private GridPane postGrid=new GridPane();
    private List<Post> posts;
    Stage stage=new Stage();
    FileChooser fc = new FileChooser();
    String url;
    //User user;
    int columns = 1;
    int rows = 1;
    public void addPhotoOnAction(ActionEvent actionEvent) {
        fc.setTitle("Open File");
        File selectedFile = fc.showOpenDialog(null);
        url = selectedFile.toURI().toString();
        if(selectedFile != null) {

            imag_post.setImage(new Image(url));
            imag_post.maxWidth(119);
            imag_post.maxHeight(169);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            posts = new ArrayList<>(data());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            for(int i=0; i<posts.size();i++)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("login/logout/post.fxml"));
                VBox postBox = fxmlLoader.load();
                PostController postController = fxmlLoader.getController();
                postController.SetDate(posts.get(i));
                if(columns == 3 ) {
                    columns = 1;
                    ++rows;
                }
                postGrid.add(postBox,columns++,rows);
                GridPane.setMargin(postBox, new Insets(15));
            }
        } catch (IOException e) {
           e.printStackTrace();
        }

    }
    public void start(String email) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("login/logout/feed.fxml"));
        Name = (Label) root.lookup("#Name");
        Pofile= (ImageView) root.lookup("#Pofile");
        Scene scene= new Scene(root);
        stage.initStyle(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.show();
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDB=connectNow.getConnection();
        String v="SELECT * FROM user WHERE email='"+email+"';";
        System.out.println(v);
        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult=statement.executeQuery(v);
            while ( queryResult.next() ) {
                System.out.println(queryResult.getString("nume"));
                Name.setText(queryResult.getString("nume"));
                String url = queryResult.getString("image");
                Pofile.setImage(new Image(url));
            } } catch (SQLException ex) {
            throw new RuntimeException(ex);}
        User.name=Name.getText();
        User.url=url;
    }

    public void AddBtnOnAction(ActionEvent actionEvent) throws IOException {
            Stage stage1=new Stage();
            Parent root=FXMLLoader.load(getClass().getResource("login/logout/Postare.fxml"));
            Scene scene= new Scene(root);
            // postGrid=(GridPane) root.lookup("#postGrid");
            postGrid = (GridPane) root.lookup("#postGrid");
            stage1.initStyle(StageStyle.DECORATED);
            stage1.setScene(scene);
            stage1.show();
        }
    public void AddPostOnAction(ActionEvent actionEvent) throws SQLException {
        if(TitlePost.getText()!=null)
        {
            DataBaseConnection connectNow=new DataBaseConnection();
            Connection connectDB=connectNow.getConnection();

            String verify=" INSERT INTO Post(titluPost, PostImag, UserName,Likes,data) VALUES('"+ TitlePost.getText()+"','"+ url+ "','"+User.name+"','"+0+"','"+java.time.LocalDate.now()+"');";

            try{
                Statement statement=connectDB.createStatement();
                statement.executeUpdate(verify);
                mesaj.setText("Congratulations!");
                start(User.email);
                try {
                    posts = new ArrayList<>(data());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    for(int i=0; i<posts.size();i++)
                    {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("login/logout/post.fxml"));
                        VBox postBox = fxmlLoader.load();
                        PostController postController = fxmlLoader.getController();
                        postController.SetDate(posts.get(i));
                        if(columns == 3 ) {
                            columns = 1;
                            ++rows;
                        }
                        postGrid.add(postBox,columns++,rows);
                        GridPane.setMargin(postBox, new Insets(15));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }catch(Exception e)
            {   e.printStackTrace();
                e.getCause();}
        }
        else{
            mesaj.setText("Error! Please enter the Title!!");
        }
    }
    @FXML
    private List<Post> data() throws SQLException {
        List<Post> ls = new ArrayList<>();
        DataBaseConnection connectNow=new DataBaseConnection();
        Connection connectDB=connectNow.getConnection();
        String img;
        String verify2=" SELECT * From Post";
        try{
            Statement statement=connectDB.createStatement();
            ResultSet queryResult=statement.executeQuery(verify2);
            System.out.println(verify2);
            while(queryResult.next()) {

                Post post1 = new Post();
                String imag=queryResult.getString("PostImag");
                String data=queryResult.getString("data");
                String like=queryResult.getString("Likes");
                String username=queryResult.getString("UserName");
                String verify1=" SELECT nume,image From user where nume='"+username+ "';";
                try{
                    Statement statement1=connectDB.createStatement();
                    ResultSet queryResult1=statement1.executeQuery(verify1);
                    System.out.println(verify1);
                    while(queryResult1.next()) {
                        User.url=queryResult1.getString("image");
                    }
                }catch (SQLException ex) {
                    throw new RuntimeException(ex);}
                post1.setPostImageSrc(imag);
                post1.setProfileImageSrc(User.url);
                post1.setUsername(username);
                post1.setDate(data);
                post1.setNbLikes(like);
                post1.setNbComments("13");
               // System.out.println("aaaaaa");
                System.out.println(post1);
                ls.add(post1);
            }
            return ls;
        }catch (SQLException ex) {
            throw new RuntimeException(ex);}
//        Post post = new Post();
//        post.setPostImageSrc("login/logout/Cyborg Woman, coco kim.png");
//        post.setProfileImageSrc("login/logout/Cyborg Woman, coco kim.png");
//        post.setUsername("toufailimyra");
//        post.setDate("4 Days Ago");
//        post.setNbLikes("12K");
//        post.setNbComments("13");
//        ls.add(post);

    }




}
