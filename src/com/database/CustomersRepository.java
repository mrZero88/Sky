package com.database;

import com.models.Country;
import com.models.Customer;
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

public class CustomersRepository extends BaseRepository {

    public CustomersRepository() throws Exception {
        super();
    }

    public Customer insert(Customer customer) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into customers " +
                            "(name, " +
                            "adress, " +
                            "phone_number, " +
                            "country_id, " +
                            "postal_code, " +
                            "city, " +
                            "picture, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAdress());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setInt(4, customer.getCountryId());
            preparedStatement.setString(5, customer.getPostalCode());
            preparedStatement.setString(6, customer.getCity());
            preparedStatement.setBinaryStream(7, customer.getPicture());
            preparedStatement.setTimestamp(8, customer.getCreatedAt());
            preparedStatement.setTimestamp(9, customer.getUpdatedAt());
            preparedStatement.setLong(10, customer.getCreatedUserId());
            preparedStatement.setLong(11, customer.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from customers order by id desc limit 1");
                resultSet.next();
                customer.setId(resultSet.getInt("id"));
            }
            return customer;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Customer getById(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            Customer customer = new Customer();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from customers where id=" + id);
            resultSet.next();
            customer.setId(resultSet.getInt("id"));
            customer.setName(resultSet.getString("name"));
            customer.setAdress(resultSet.getString("adress"));
            customer.setPhoneNumber(resultSet.getString("phone_number"));
            customer.setCountryId(resultSet.getInt("country_id"));
            customer.setPostalCode(resultSet.getString("postal_code"));
            customer.setCity(resultSet.getString("city"));
            customer.setPicture(resultSet.getBinaryStream("picture"));
            customer.setCreatedAt(resultSet.getTimestamp("created_at"));
            customer.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            customer.setCreatedUserId(resultSet.getLong("created_user_id"));
            customer.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            customer.setActive(resultSet.getBoolean("active"));
            return customer;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Customer> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Customer> customers = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from customers where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setAdress(resultSet.getString("adress"));
                customer.setPhoneNumber(resultSet.getString("phone_number"));
                customer.setCountryId(resultSet.getInt("country_id"));
                customer.setPostalCode(resultSet.getString("postal_code"));
                customer.setCity(resultSet.getString("city"));
                customer.setPicture(resultSet.getBinaryStream("picture"));
                customer.setCreatedAt(resultSet.getTimestamp("created_at"));
                customer.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                customer.setCreatedUserId(resultSet.getLong("created_user_id"));
                customer.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                customer.setActive(resultSet.getBoolean("active"));
                customers.add(customer);
            }
            return FXCollections.observableList(customers);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Customer> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Customer> customers = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from customers where active=true");
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getInt("id"));
                customer.setName(resultSet.getString("name"));
                customer.setAdress(resultSet.getString("adress"));
                customer.setPhoneNumber(resultSet.getString("phone_number"));
                customer.setCountryId(resultSet.getInt("country_id"));
                customer.setPostalCode(resultSet.getString("postal_code"));
                customer.setCity(resultSet.getString("city"));
                customer.setPicture(resultSet.getBinaryStream("picture"));
                customer.setCreatedAt(resultSet.getTimestamp("created_at"));
                customer.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                customer.setCreatedUserId(resultSet.getLong("created_user_id"));
                customer.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                customer.setActive(resultSet.getBoolean("active"));
                customers.add(customer);
            }
            return FXCollections.observableList(customers);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Customer update(Customer customer) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            customer.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update customers set" +
                            " name = ?," +
                            " adress = ?," +
                            " phone_number = ?," +
                            " country_id = ?," +
                            " postal_code = ?," +
                            " city = ?," +
                            " picture = ?," +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAdress());
            preparedStatement.setString(3, customer.getPhoneNumber());
            preparedStatement.setInt(4, customer.getCountryId());
            preparedStatement.setString(5, customer.getPostalCode());
            preparedStatement.setString(6, customer.getCity());
            preparedStatement.setBinaryStream(7, customer.getPicture());
            preparedStatement.setTimestamp(8, customer.getUpdatedAt());
            preparedStatement.setLong(9, customer.getUpdatedUserId());
            preparedStatement.setLong(10, customer.getId());
            preparedStatement.executeUpdate();
            return customer;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Customer delete(Customer customer) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            customer.setActive(false);
            preparedStatement = connect.prepareStatement("update customers set active = ? where id = ?");
            preparedStatement.setBoolean(1, customer.getActive());
            preparedStatement.setLong(2, customer.getId());
            preparedStatement.executeUpdate();
            return customer;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<Customer> customers) throws Exception {
        if (customers.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Customer customer : customers) {
            set.add("" + customer.getCreatedUserId());
            set.add("" + customer.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (Customer customer : customers) {
            for (User user : users) {
                if (customer.getCreatedUserId() == user.getId())
                    customer.setCreatedUser(user);
                if (customer.getUpdatedUserId() == user.getId())
                    customer.setUpdatedUser(user);
            }
        }
    }
}
