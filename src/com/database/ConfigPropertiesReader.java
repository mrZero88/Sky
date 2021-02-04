package com.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropertiesReader {

    private String conn;

    public ConfigPropertiesReader() {
        String propFileName = "./src/com/config.properties";
        try (InputStream input = new FileInputStream(propFileName)) {
            Properties prop = new Properties();
            prop.load(input);
            String ip = prop.getProperty("db_ip");
            String name = prop.getProperty("db_name");
            String user = prop.getProperty("db_user");
            String pass = prop.getProperty("db_password");
            conn = "jdbc:mysql://" + ip + "/" + name + "?user=" + user + "&password=" + pass;
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public String getConn() {
        return conn;
    }
}
