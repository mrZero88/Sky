package com.database;

import com.models.Bug;
import javafx.collections.ObservableList;

import java.sql.*;

public abstract class BaseRepository {

    protected Connection connect = null;
    protected Statement statement = null;
    protected PreparedStatement preparedStatement = null;
    protected ResultSet resultSet = null;
    protected final String CONN = "jdbc:mysql://localhost/flyzerosky?user=root&password=Waitangels999";

    public BaseRepository() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
    }

}
