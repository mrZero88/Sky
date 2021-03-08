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

public class ProjectsRepository extends BaseRepository {

    public ProjectsRepository() throws Exception {
        super();
    }

    public Project insert(Project project) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            preparedStatement = connect.prepareStatement(
                    "insert into projects " +
                            "(title, " +
                            "description, " +
                            "start, " +
                            "end, " +
                            "total_cost, " +
                            "currency_id, " +
                            "customer_id, " +
                            "state_id, " +
                            "picture, " +
                            "country_id, " +
                            "created_at, " +
                            "updated_at, " +
                            "created_user_id, " +
                            "updated_user_id) " +
                            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, project.getTitle());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setDate(3, project.getStart());
            preparedStatement.setDate(4, project.getEnd());
            preparedStatement.setDouble(5, project.getTotalCost());
            preparedStatement.setInt(6, project.getCurrencyId());
            preparedStatement.setLong(7, project.getCustomerId());
            preparedStatement.setInt(8, project.getStateId());
            preparedStatement.setBinaryStream(9, project.getPicture());
            preparedStatement.setInt(10, project.getCountryId());
            preparedStatement.setTimestamp(11, project.getCreatedAt());
            preparedStatement.setTimestamp(12, project.getUpdatedAt());
            preparedStatement.setLong(13, project.getCreatedUserId());
            preparedStatement.setLong(14, project.getUpdatedUserId());
            if (preparedStatement.executeUpdate() == 1) {
                statement = connect.createStatement();
                resultSet = statement.executeQuery("select id from projects order by id desc limit 1");
                resultSet.next();
                project.setId(resultSet.getInt("id"));
            }
            return project;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Project getById(long id) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            Project project = new Project();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from projects where id=" + id);
            resultSet.next();
            project.setId(resultSet.getInt("id"));
            project.setTitle(resultSet.getString("title"));
            project.setDescription(resultSet.getString("description"));
            project.setStart(resultSet.getDate("start"));
            project.setEnd(resultSet.getDate("end"));
            project.setTotalCost(resultSet.getDouble("total_cost"));
            project.setCurrencyId(resultSet.getInt("currency_id"));
            project.setCustomerId(resultSet.getLong("customer_id"));
            project.setStateId(resultSet.getInt("state_id"));
            project.setPicture(resultSet.getBinaryStream("picture"));
            project.setCountryId(resultSet.getInt("country_id"));
            project.setCreatedAt(resultSet.getTimestamp("created_at"));
            project.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            project.setCreatedUserId(resultSet.getLong("created_user_id"));
            project.setUpdatedUserId(resultSet.getLong("updated_user_id"));
            project.setActive(resultSet.getBoolean("active"));
            return project;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Project> getByIds(Set<String> ids) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Project> projects = new ArrayList<>();
            statement = connect.createStatement();
            String idsWithComma = String.join(",", ids);
            resultSet = statement.executeQuery("select * from projects where id in (" + idsWithComma + ")");
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("description"));
                project.setStart(resultSet.getDate("start"));
                project.setEnd(resultSet.getDate("end"));
                project.setTotalCost(resultSet.getDouble("total_cost"));
                project.setCurrencyId(resultSet.getInt("currency_id"));
                project.setCustomerId(resultSet.getLong("customer_id"));
                project.setStateId(resultSet.getInt("state_id"));
                project.setPicture(resultSet.getBinaryStream("picture"));
                project.setCountryId(resultSet.getInt("country_id"));
                project.setCreatedAt(resultSet.getTimestamp("created_at"));
                project.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                project.setCreatedUserId(resultSet.getLong("created_user_id"));
                project.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                project.setActive(resultSet.getBoolean("active"));
                projects.add(project);
            }
            return FXCollections.observableList(projects);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public ObservableList<Project> getAll() throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            List<Project> projects = new ArrayList<>();
            statement = connect.createStatement();
            resultSet = statement.executeQuery("select * from projects where active=true");
            while (resultSet.next()) {
                Project project = new Project();
                project.setId(resultSet.getInt("id"));
                project.setTitle(resultSet.getString("title"));
                project.setDescription(resultSet.getString("description"));
                project.setStart(resultSet.getDate("start"));
                project.setEnd(resultSet.getDate("end"));
                project.setTotalCost(resultSet.getDouble("total_cost"));
                project.setCurrencyId(resultSet.getInt("currency_id"));
                project.setCustomerId(resultSet.getLong("customer_id"));
                project.setStateId(resultSet.getInt("state_id"));
                project.setPicture(resultSet.getBinaryStream("picture"));
                project.setCountryId(resultSet.getInt("country_id"));
                project.setCreatedAt(resultSet.getTimestamp("created_at"));
                project.setUpdatedAt(resultSet.getTimestamp("updated_at"));
                project.setCreatedUserId(resultSet.getLong("created_user_id"));
                project.setUpdatedUserId(resultSet.getLong("updated_user_id"));
                project.setActive(resultSet.getBoolean("active"));
                projects.add(project);
            }
            return FXCollections.observableList(projects);
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Project update(Project project) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            project.setUpdatedAt(Timestamp.from(Instant.now()));
            preparedStatement = connect.prepareStatement(
                    "update projects set" +
                            " title = ?," +
                            " description = ?," +
                            " start = ?," +
                            " end = ?," +
                            " total_cost = ?," +
                            " currency_id = ?," +
                            " customer_id = ?," +
                            " state_id = ?," +
                            " picture = ?," +
                            " country_id = ?," +
                            " updated_at = ?, " +
                            " updated_user_id = ? " +
                            "where id = ?");
            preparedStatement.setString(1, project.getTitle());
            preparedStatement.setString(2, project.getDescription());
            preparedStatement.setDate(3, project.getStart());
            preparedStatement.setDate(4, project.getEnd());
            preparedStatement.setDouble(5, project.getTotalCost());
            preparedStatement.setInt(6, project.getCurrencyId());
            preparedStatement.setLong(7, project.getCustomerId());
            preparedStatement.setInt(8, project.getStateId());
            preparedStatement.setBinaryStream(9, project.getPicture());
            preparedStatement.setInt(10, project.getCountryId());
            preparedStatement.setTimestamp(11, project.getUpdatedAt());
            preparedStatement.setLong(12, project.getUpdatedUserId());
            preparedStatement.setLong(13, project.getId());
            preparedStatement.executeUpdate();
            return project;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public Project delete(Project project) throws Exception {
        try {
            connect = DriverManager.getConnection(CONN);
            project.setActive(false);
            preparedStatement = connect.prepareStatement("update projects set active = ? where id = ?");
            preparedStatement.setBoolean(1, project.getActive());
            preparedStatement.setLong(2, project.getId());
            preparedStatement.executeUpdate();
            return project;
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnections();
        }
    }

    public void loadUsers(ObservableList<Project> projects) throws Exception {
        if (projects.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Project project : projects) {
            set.add("" + project.getCreatedUserId());
            set.add("" + project.getUpdatedUserId());
        }

        UsersRepository ur = new UsersRepository();
        ObservableList<User> users = ur.getByIds(set);

        for (Project project : projects) {
            for (User user : users) {
                if (project.getCreatedUserId() == user.getId())
                    project.setCreatedUser(user);
                if (project.getUpdatedUserId() == user.getId())
                    project.setUpdatedUser(user);
            }
        }
    }

    public void loadCountries(ObservableList<Project> projects) throws Exception {
        if (projects.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Project project : projects) {
            set.add("" + project.getCountryId());
        }

        CountriesRepository cr = new CountriesRepository();
        ObservableList<Country> countries = cr.getByIds(set);

        for (Project project : projects) {
            for (Country country : countries) {
                if (project.getCountryId() == country.getId())
                    project.setCountry(country);
            }
        }
    }

    public void loadCurrencies(ObservableList<Project> projects) throws Exception {
        if (projects.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Project project : projects) {
            set.add("" + project.getCurrencyId());
        }

        CurrenciesRepository cr = new CurrenciesRepository();
        ObservableList<Currency> currencies = cr.getByIds(set);

        for (Project project : projects) {
            for (Currency currency : currencies) {
                if (project.getCurrencyId() == currency.getId())
                    project.setCurrency(currency);
            }
        }
    }

    public void loadCustomers(ObservableList<Project> projects) throws Exception {
        if (projects.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Project project : projects) {
            set.add("" + project.getCustomerId());
        }

        CustomersRepository cr = new CustomersRepository();
        ObservableList<Customer> customers = cr.getByIds(set);

        for (Project project : projects) {
            for (Customer customer : customers) {
                if (project.getCustomerId() == customer.getId())
                    project.setCustomer(customer);
            }
        }
    }

    public void loadStates(ObservableList<Project> projects) throws Exception {
        if (projects.isEmpty())
            return;

        Set<String> set = new HashSet<>();
        for (Project project : projects) {
            set.add("" + project.getStateId());
        }

        ProjectStatesRepository psr = new ProjectStatesRepository();
        ObservableList<ProjectState> states = psr.getByIds(set);

        for (Project project : projects) {
            for (ProjectState state : states) {
                if (project.getStateId() == state.getId())
                    project.setState(state);
            }
        }
    }
}
