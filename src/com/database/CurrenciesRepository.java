package com.database;

import com.models.Country;
import com.models.Currency;
import com.models.Feature;
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

public class CurrenciesRepository extends BaseRepository {

    public CurrenciesRepository() throws Exception {
        super();
    }

    public Currency insert(Currency currency) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into currencies " +
                            "(title, " +
                            "symbol, " +
                            "iso, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, currency.getTitle());
            preparedStatement.setString(2, currency.getSymbol());
            preparedStatement.setString(3, currency.getIso());
            preparedStatement.setTimestamp(4, currency.getCreatedAt());
            preparedStatement.setTimestamp(5, currency.getUpdatedAt());
            preparedStatement.setLong(6, currency.getCreatedUserId());
            preparedStatement.setLong(7, currency.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from currencies order by id desc limit 1");
                resultSet.next();
                currency.setId(resultSet.getInt("id"));
            }
            return currency;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Currency getById(int id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            Currency currency = new Currency();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from currencies where id=" + id);
            resultSet.next();
            currency.setId(resultSet.getInt("id"));
            currency.setTitle(resultSet.getString("title"));
            currency.setSymbol(resultSet.getString("symbol"));
            currency.setIso(resultSet.getString("iso"));
            currency.setCreatedAt(resultSet.getTimestamp("created_at"));
            currency.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            currency.setCreatedUserId(resultSet.getLong("created_user_id"));
            currency.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            currency.setActive(resultSet.getBoolean("active"));
            return currency;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Currency> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Currency> currencies = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from currencies where active=true");
            while (resultSet.next()) {
                Currency currency = new Currency();
                currency.setId(resultSet.getInt("id"));
                currency.setTitle(resultSet.getString("title"));
                currency.setSymbol(resultSet.getString("symbol"));
                currency.setIso(resultSet.getString("iso"));
                currency.setCreatedAt(resultSet.getTimestamp("created_at"));
                currency.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                currency.setCreatedUserId(resultSet.getLong("created_user_id"));
                currency.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                currency.setActive(resultSet.getBoolean("active"));
                currencies.add(currency);
            }
            return FXCollections.observableList(currencies);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Currency update(Currency currency) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            currency.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update currencies set title = ?," +
                            " symbol = ?," +
                            " iso = ?, " +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, currency.getTitle());
            preparedStatement.setString(2, currency.getSymbol());
            preparedStatement.setString(3, currency.getIso());
            preparedStatement.setTimestamp(4, currency.getUpdatedAt());
            preparedStatement.setLong(5, currency.getUpdatedUserId());
            preparedStatement.setInt(6, currency.getId());
            preparedStatement.executeUpdate();
            return currency;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Currency delete(Currency currency) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            currency.setActive(false);
            preparedStatement = connect.prepareStatement("update currencies set active = ? where id = ?");
            preparedStatement.setBoolean(1, currency.getActive());
            preparedStatement.setLong(2, currency.getId());
            preparedStatement.executeUpdate();
            return currency;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<Currency> currencies) throws Exception {
        if (currencies.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Currency currency : currencies) {
            set.add("" + currency.getCreatedUserId());
            set.add("" + currency.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (Currency currency : currencies) {
            for (User user : users) {
                if (currency.getCreatedUserId() == user.getId())
                    currency.setCreatedUser(user);
                if (currency.getUpdatedUserId() == user.getId())
                    currency.setUpdatedUser(user);
            }
        }
    }
}
