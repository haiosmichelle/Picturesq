package com.example.picturesq;

import java.sql.*;

public class DataBaseConnection {
    public Connection databaselink;
    public Connection getConnection(){
        String databaseUser="root";
        String databasePassword="20202021";
        String url="jdbc:mysql://localhost:3306/picturesq";
        try{
           Class.forName("com.mysql.jdbc.Driver");
           databaselink=DriverManager.getConnection(url,databaseUser,databasePassword);
        }catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
return databaselink;
    }

}
