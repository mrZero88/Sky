package com.database;

import com.models.Gender;
import com.models.ProjectState;
import com.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.DriverManager;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GendersRepository extends BaseRepository {

    public GendersRepository() throws Exception {
        super();
    }

    public Gender insert(Gender gender) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into genders " +
                            "(name, " +
                            "shortcut, " +
                            "gender, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, gender.getName());
            preparedStatement.setString(2, gender.getShortcut());
            preparedStatement.setBinaryStream(3, gender.getGender());
            preparedStatement.setTimestamp(4, gender.getCreatedAt());
            preparedStatement.setTimestamp(5, gender.getUpdatedAt());
            preparedStatement.setLong(6, gender.getCreatedUserId());
            preparedStatement.setLong(7, gender.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from genders order by id desc limit 1");
                resultSet.next();
                gender.setId(resultSet.getInt("id"));
            }
            return gender;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Gender getById(int id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            Gender gender = new Gender();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from genders where id=" + id);
            resultSet.next();
            gender.setId(resultSet.getInt("id"));
            gender.setName(resultSet.getString("name"));
            gender.setShortcut(resultSet.getString("shortcut"));
            gender.setGender(resultSet.getBinaryStream("gender"));
            gender.setCreatedAt(resultSet.getTimestamp("created_at"));
            gender.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            gender.setCreatedUserId(resultSet.getLong("created_user_id"));
            gender.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            gender.setActive(resultSet.getBoolean("active"));
            return gender;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Gender> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Gender> genders = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from genders where active=true");
            while (resultSet.next()) {
                Gender gender = new Gender();
                gender.setId(resultSet.getInt("id"));
                gender.setName(resultSet.getString("name"));
                gender.setShortcut(resultSet.getString("shortcut"));
                gender.setGender(resultSet.getBinaryStream("gender"));
                gender.setCreatedAt(resultSet.getTimestamp("created_at"));
                gender.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                gender.setCreatedUserId(resultSet.getLong("created_user_id"));
                gender.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                gender.setActive(resultSet.getBoolean("active"));
                genders.add(gender);
            }
            return FXCollections.observableList(genders);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Gender update(Gender gender) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            gender.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update genders set name = ?," +
                            " shortcut = ?, " +
                            " gender = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, gender.getName());
            preparedStatement.setString(2, gender.getShortcut());
            preparedStatement.setBinaryStream(3, gender.getGender());
            preparedStatement.setTimestamp(4, gender.getUpdatedAt());
            preparedStatement.setLong(5, gender.getUpdatedUserId());
            preparedStatement.setInt(6, gender.getId());
            preparedStatement.executeUpdate();
            return gender;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Gender delete(Gender gender) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            gender.setActive(false);
            preparedStatement = connect.prepareStatement("update genders set active = ? where id = ?");
            preparedStatement.setBoolean(1, gender.getActive());
            preparedStatement.setLong(2, gender.getId());
            preparedStatement.executeUpdate();
            return gender;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Gender> loadCreatedUsers(ObservableList<Gender> genders) throws Exception {
        if (genders.isEmpty())
            return genders;

        try {
            connect = DriverManager.getConnection(CONN);
            for (Gender gender : genders) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + gender.getCreatedUserId());
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
                    user.setAbbreviation(resultSet.getString("abbreviation"));
                    user.setCreatedAt(resultSet.getTimestamp("created_at"));
                    user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    user.setCreatedUserId(resultSet.getLong("created_user_id"));
                    user.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                    user.setActive(resultSet.getBoolean("active"));
                    gender.setCreatedUser(user);
                }
            }
            return genders;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Gender> loadUpdatedUsers(ObservableList<Gender> genders) throws Exception {
        if (genders.isEmpty())
            return genders;

        try {
            connect = DriverManager.getConnection(CONN);
            for (Gender gender : genders) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select * from users where active=true and id=" + gender.getUpdatedUserId());
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
                    user.setAbbreviation(resultSet.getString("abbreviation"));
                    user.setCreatedAt(resultSet.getTimestamp("created_at"));
                    user.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                    user.setCreatedUserId(resultSet.getLong("created_user_id"));
                    user.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                    user.setActive(resultSet.getBoolean("active"));
                    gender.setUpdatedUser(user);
                }
            }
            return genders;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }
}
