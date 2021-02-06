package com.database;

import com.models.Bug;
import com.models.Country;
import com.models.Currency;
import com.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.DriverManager;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CountriesRepository extends BaseRepository {

    public CountriesRepository() throws Exception {
        super();
    }

    public Country insert(Country country) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into countries " +
                            "(name, " +
                            "flag, " +
                            "shortcut, " +
                            "continent_id, " +
                            "file_name, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, country.getName());
            preparedStatement.setBinaryStream(2, country.getFlag());
            preparedStatement.setString(3, country.getShortcut());
            preparedStatement.setInt(4, country.getContinentId());
            preparedStatement.setString(5, country.getFileName());
            preparedStatement.setTimestamp(6, country.getCreatedAt());
            preparedStatement.setTimestamp(7, country.getUpdatedAt());
            preparedStatement.setLong(8, country.getCreatedUserId());
            preparedStatement.setLong(9, country.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from countries order by id desc limit 1");
                resultSet.next();
                country.setId(resultSet.getInt("id"));
            }
            return country;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Country getById(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            Country country = new Country();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from countries where id=" + id);
            resultSet.next();
            country.setId(resultSet.getInt("id"));
            country.setName(resultSet.getString("name"));
            country.setFlag(resultSet.getBinaryStream("flag"));
            country.setShortcut(resultSet.getString("small_version"));
            country.setContinentId(resultSet.getByte("continent_id"));
            country.setFileName(resultSet.getString("file_name"));
            country.setCreatedAt(resultSet.getTimestamp("created_at"));
            country.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            country.setCreatedUserId(resultSet.getLong("created_user_id"));
            country.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            country.setActive(resultSet.getBoolean("active"));
            return country;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Country> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Country> countries = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from countries where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                Country country = new Country();
                country.setId(resultSet.getInt("id"));
                country.setName(resultSet.getString("name"));
                country.setFlag(resultSet.getBinaryStream("flag"));
                country.setShortcut(resultSet.getString("shortcut"));
                country.setContinentId(resultSet.getByte("continent_id"));
                country.setFileName(resultSet.getString("file_name"));
                country.setCreatedAt(resultSet.getTimestamp("created_at"));
                country.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                country.setCreatedUserId(resultSet.getLong("created_user_id"));
                country.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                country.setActive(resultSet.getBoolean("active"));
                countries.add(country);
            }
            return FXCollections.observableList(countries);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Country> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Country> countries = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from countries where active=true");
            while (resultSet.next()) {
                Country country = new Country();
                country.setId(resultSet.getInt("id"));
                country.setName(resultSet.getString("name"));
                country.setFlag(resultSet.getBinaryStream("flag"));
                country.setShortcut(resultSet.getString("shortcut"));
                country.setContinentId(resultSet.getByte("continent_id"));
                country.setFileName(resultSet.getString("file_name"));
                country.setCreatedAt(resultSet.getTimestamp("created_at"));
                country.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                country.setCreatedUserId(resultSet.getLong("created_user_id"));
                country.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                country.setActive(resultSet.getBoolean("active"));
                countries.add(country);
            }
            return FXCollections.observableList(countries);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Country update(Country country) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            country.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update countries set" +
                            " name = ?," +
                            " flag = ?," +
                            " shortcut = ?, " +
                            " continent_id = ?, " +
                            " file_name = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, country.getName());
            preparedStatement.setBinaryStream(2, country.getFlag());
            preparedStatement.setString(3, country.getShortcut());
            preparedStatement.setInt(4, country.getContinentId());
            preparedStatement.setString(5, country.getFileName());
            preparedStatement.setTimestamp(6, country.getUpdatedAt());
            preparedStatement.setLong(7, country.getUpdatedUserId());
            preparedStatement.setInt(8, country.getId());
            preparedStatement.executeUpdate();
            return country;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Country delete(Country country) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            country.setActive(false);
            preparedStatement = connect.prepareStatement("update countries set active = ? where id = ?");
            preparedStatement.setBoolean(1, country.getActive());
            preparedStatement.setLong(2, country.getId());
            preparedStatement.executeUpdate();
            return country;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Country> loadCreatedUsers(ObservableList<Country> countries) throws Exception {
        if (countries.isEmpty())
            return countries;

        try {
            connect = DriverManager.getConnection(CONN);
            for (Country country : countries) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + country.getCreatedUserId());
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setEmailVerifiedAt(resultSet.getTimestamp("email_verified_at"));
                    user.setPassword(resultSet.getString("password"));
                    user.setTypeId(resultSet.getByte("type_id"));
                    user.setIsSuperUser(resultSet.getBoolean("is_super_user"));
                    user.setCountryId(resultSet.getInt("country_id"));
                    user.setPicture(resultSet.getBinaryStream("picture"));
                    user.setBirthDate(resultSet.getDate("birth_date"));
                    user.setAdress(resultSet.getString("adress"));
                    user.setPostalCode(resultSet.getString("postal_code"));
                    user.setPhoneNumber(resultSet.getString("phone_number"));
                    user.setRememberToken(resultSet.getString("remember_token"));
                    user.setGenderId(resultSet.getInt("gender_id"));
                    user.setCreatedAt(resultSet.getTimestamp("created_at"));
                    user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    user.setCreatedUserId(resultSet.getLong("created_user_id"));
                    user.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                    user.setActive(resultSet.getBoolean("active"));
                    country.setCreatedUser(user);
                }
            }
            return countries;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Country> loadUpdatedUsers(ObservableList<Country> countries) throws Exception {
        if (countries.isEmpty())
            return countries;

        try {
            connect = DriverManager.getConnection(CONN);
            for (Country country : countries) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + country.getUpdatedUserId());
                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setEmailVerifiedAt(resultSet.getTimestamp("email_verified_at"));
                    user.setPassword(resultSet.getString("password"));
                    user.setTypeId(resultSet.getByte("type_id"));
                    user.setIsSuperUser(resultSet.getBoolean("is_super_user"));
                    user.setCountryId(resultSet.getInt("country_id"));
                    user.setPicture(resultSet.getBinaryStream("picture"));
                    user.setBirthDate(resultSet.getDate("birth_date"));
                    user.setAdress(resultSet.getString("adress"));
                    user.setPostalCode(resultSet.getString("postal_code"));
                    user.setPhoneNumber(resultSet.getString("phone_number"));
                    user.setRememberToken(resultSet.getString("remember_token"));
                    user.setGenderId(resultSet.getInt("gender_id"));
                    user.setCreatedAt(resultSet.getTimestamp("created_at"));
                    user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    user.setCreatedUserId(resultSet.getLong("created_user_id"));
                    user.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                    user.setActive(resultSet.getBoolean("active"));
                    country.setUpdatedUser(user);
                }
            }
            return countries;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }
}
