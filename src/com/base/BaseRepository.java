package com.base;

import com.utils.ConfigPropertiesReader;

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

    protected void closeConnections() throws Exception {
        if (this.preparedStatement != null)
            this.preparedStatement.close();
        if (this.statement != null)
            this.statement.close();
        if (this.resultSet != null)
            this.resultSet.close();
    }
}
