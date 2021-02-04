package com.database;

import java.sql.*;

public abstract class BaseRepository {

    protected Connection connect = null;
    protected Statement statement = null;
    protected PreparedStatement preparedStatement = null;
    protected ResultSet resultSet = null;
    protected String CONN;

    public BaseRepository() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        ConfigPropertiesReader cpr = new ConfigPropertiesReader();
        this.CONN = cpr.getConn();
    }
}
