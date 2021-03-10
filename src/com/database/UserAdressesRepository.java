package com.database;

import com.base.BaseRepository;
import com.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserAdressesRepository extends BaseRepository {

    public UserAdressesRepository() throws Exception {
        super();
    }

    public UserAdress insert(UserAdress userAdress) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into user_adresses " +
                            "(adress, " +
                            "postal_code, " +
                            "city, " +
                            "country_id, " +
                            "is_default_adress, " +
                            "user_id, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, userAdress.getAdress());
            preparedStatement.setString(2, userAdress.getPostalCode());
            preparedStatement.setString(3, userAdress.getCity());
            preparedStatement.setInt(4, userAdress.getCountryId());
            preparedStatement.setBoolean(5, userAdress.getIsDefaultAdress());
            preparedStatement.setLong(6, userAdress.getUserId());
            preparedStatement.setTimestamp(7, userAdress.getCreatedAt());
            preparedStatement.setTimestamp(8, userAdress.getUpdatedAt());
            preparedStatement.setLong(9, userAdress.getCreatedUserId());
            preparedStatement.setLong(10, userAdress.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from user_adresses order by id desc limit 1");
                resultSet.next();
                userAdress.setId(resultSet.getInt("id"));
            }
            return userAdress;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public UserAdress getById(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            UserAdress userAdress = new UserAdress();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from user_adresses where id=" + id);
            resultSet.next();
            userAdress.setId(resultSet.getInt("id"));
            userAdress.setAdress(resultSet.getString("adress"));
            userAdress.setPostalCode(resultSet.getString("postal_code"));
            userAdress.setCity(resultSet.getString("city"));
            userAdress.setCountryId(resultSet.getInt("country_id"));
            userAdress.setIsDefaultAdress(resultSet.getBoolean("is_default_adress"));
            userAdress.setUserId(resultSet.getLong("user_id"));
            userAdress.setCreatedAt(resultSet.getTimestamp("created_at"));
            userAdress.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            userAdress.setCreatedUserId(resultSet.getLong("created_user_id"));
            userAdress.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            userAdress.setActive(resultSet.getBoolean("active"));
            return userAdress;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<UserAdress> getByUserId(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<UserAdress> userAdresses = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from user_adresses where active=true and user_id=" + id);
            while (resultSet.next()) {
                UserAdress userAdress = new UserAdress();
                userAdress.setId(resultSet.getInt("id"));
                userAdress.setAdress(resultSet.getString("adress"));
                userAdress.setPostalCode(resultSet.getString("postal_code"));
                userAdress.setCity(resultSet.getString("city"));
                userAdress.setCountryId(resultSet.getInt("country_id"));
                userAdress.setIsDefaultAdress(resultSet.getBoolean("is_default_adress"));
                userAdress.setUserId(resultSet.getLong("user_id"));
                userAdress.setCreatedAt(resultSet.getTimestamp("created_at"));
                userAdress.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                userAdress.setCreatedUserId(resultSet.getLong("created_user_id"));
                userAdress.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                userAdress.setActive(resultSet.getBoolean("active"));
                userAdresses.add(userAdress);
            }
            return FXCollections.observableList(userAdresses);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<UserAdress> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<UserAdress> userAdresses = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from user_adresses where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                UserAdress userAdress = new UserAdress();
                userAdress.setId(resultSet.getInt("id"));
                userAdress.setAdress(resultSet.getString("adress"));
                userAdress.setPostalCode(resultSet.getString("postal_code"));
                userAdress.setCity(resultSet.getString("city"));
                userAdress.setCountryId(resultSet.getInt("country_id"));
                userAdress.setIsDefaultAdress(resultSet.getBoolean("is_default_adress"));
                userAdress.setUserId(resultSet.getLong("user_id"));
                userAdress.setCreatedAt(resultSet.getTimestamp("created_at"));
                userAdress.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                userAdress.setCreatedUserId(resultSet.getLong("created_user_id"));
                userAdress.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                userAdress.setActive(resultSet.getBoolean("active"));
                userAdresses.add(userAdress);
            }
            return FXCollections.observableList(userAdresses);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<UserAdress> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<UserAdress> userAdresses = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from user_adresses where active=true");
            while (resultSet.next()) {
                UserAdress userAdress = new UserAdress();
                userAdress.setId(resultSet.getInt("id"));
                userAdress.setAdress(resultSet.getString("adress"));
                userAdress.setPostalCode(resultSet.getString("postal_code"));
                userAdress.setCity(resultSet.getString("city"));
                userAdress.setCountryId(resultSet.getInt("country_id"));
                userAdress.setIsDefaultAdress(resultSet.getBoolean("is_default_adress"));
                userAdress.setUserId(resultSet.getLong("user_id"));
                userAdress.setCreatedAt(resultSet.getTimestamp("created_at"));
                userAdress.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                userAdress.setCreatedUserId(resultSet.getLong("created_user_id"));
                userAdress.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                userAdress.setActive(resultSet.getBoolean("active"));
                userAdresses.add(userAdress);
            }
            return FXCollections.observableList(userAdresses);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public UserAdress update(UserAdress userAdress) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            userAdress.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update user_adresses set" +
                            " adress = ?," +
                            " postal_code = ?," +
                            " city = ?," +
                            " country_id = ?," +
                            " is_default_adress = ?," +
                            " user_id = ?," +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, userAdress.getAdress());
            preparedStatement.setString(2, userAdress.getPostalCode());
            preparedStatement.setString(3, userAdress.getCity());
            preparedStatement.setInt(4, userAdress.getCountryId());
            preparedStatement.setBoolean(5, userAdress.getIsDefaultAdress());
            preparedStatement.setLong(6, userAdress.getUserId());
            preparedStatement.setTimestamp(7, userAdress.getUpdatedAt());
            preparedStatement.setLong(8, userAdress.getUpdatedUserId());
            preparedStatement.setLong(9, userAdress.getId());
            preparedStatement.executeUpdate();
            return userAdress;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public UserAdress delete(UserAdress userAdress) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            userAdress.setActive(false);
            preparedStatement = connect.prepareStatement("update user_adresses set active = ? where id = ?");
            preparedStatement.setBoolean(1, userAdress.getActive());
            preparedStatement.setLong(2, userAdress.getId());
            preparedStatement.executeUpdate();
            return userAdress;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<UserAdress> userAdresses) throws Exception {
        if (userAdresses.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (UserAdress userAdress : userAdresses) {
            set.add("" + userAdress.getCreatedUserId());
            set.add("" + userAdress.getUpdatedUserId());
            set.add("" + userAdress.getUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (UserAdress userAdress : userAdresses) {
            for (User user : users) {
                if (userAdress.getCreatedUserId() == user.getId())
                    userAdress.setCreatedUser(user);
                if (userAdress.getUpdatedUserId() == user.getId())
                    userAdress.setUpdatedUser(user);
                if (userAdress.getUserId() == user.getId())
                    userAdress.setUser(user);
            }
        }
    }

    public void loadCountries(ObservableList<UserAdress> userAdresses) throws Exception {
        if (userAdresses.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (UserAdress userAdress : userAdresses) {
            set.add("" + userAdress.getCountryId());
        }

        CountriesRepository cr = new CountriesRepository();
        ObservableList<Country> countries = cr.getByIds(set);

        for (UserAdress userAdress : userAdresses) {
            for (Country country : countries) {
                if (userAdress.getCountryId() == country.getId())
                    userAdress.setCountry(country);
            }
        }
    }
}
