package ifmo.oop.todo.dao.sqlite;



import ifmo.oop.todo.dao.TaskDao;
import ifmo.oop.todo.domain.Task;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

// TODO: Maven



public class SQLiteTaskDao implements TaskDao {

    private final static String SELECT_BY_ID = "SELECT * FROM Tasks WHERE id=?";
    private final static String SELECT_BY_NAME = "SELECT * FROM Tasks WHERE  name LIKE ?";
    private final static String SELECT_FROM_DATE_TO_DATE = "SELECT * FROM Tasks WHERE dueDate>? AND dueDate<?";
    private final static String SELECT_ALL = "SELECT * FROM Tasks";
    private final static String REMOVE_BY_ID = "DELETE FROM Tasks WHERE id=?";
    private final static String REMOVE_BY_NAME = "DELETE FROM Tasks WHERE name LIKE ?";
    private final static String REMOVE_FROM_DATE_TO_DATE = "DELETE FROM Tasks WHERE dueDate>? AND dueDate<?";
    private final static String INSERT = "INSERT INTO Tasks VALUES (?, ?, ?, ?, ?)";
    private final static String UPDATE = "UPDATE Tasks SET dueDate=?, editDate=? WHERE id=?";

    private Connection connection;


    public SQLiteTaskDao(String dbName) throws SQLException {

        connection = DriverManager.getConnection("jdbc:sqlite:" + dbName);
    }


    @Override
    public void insert(Task o) {

        try {

            PreparedStatement ps = connection.prepareStatement(INSERT);
            ps.setNull(1, 0);
            ps.setString(2, o.getName());
            ps.setString(3, sdf.format(o.getDueDate()));
            ps.setString(4, sdf.format(o.getEditDate()));
            ps.setString(5, sdf.format(o.getCreateDate()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Task get(long id) {

        try {

            PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID);
            ps.setLong(1, id);
            ResultSet result = ps.executeQuery();
            assert result != null;
            return Task.newBuiilder()
                    .setId(result.getLong(1))
                    .setName(result.getString(2))
                    .setDueDate(sdf.parse(result.getString(3)))
                    .setCreateDate(sdf.parse(result.getString(4)))
                    .setEditDate(sdf.parse(result.getString(5)))
                    .build();
        } catch (SQLException | ParseException e) {
        }
        return null;
    }


    @Override
    public List<Task> getAll() {

        List<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
            ResultSet result = ps.executeQuery();
            assert result != null;
            while (result.next()) {
                tasks.add(Task.newBuiilder()
                        .setId(result.getLong(1))
                        .setName(result.getString(2))
                        .setDueDate(sdf.parse(result.getString(3)))
                        .setCreateDate(sdf.parse(result.getString(4)))
                        .setEditDate(sdf.parse(result.getString(5)))
                        .build());
            }
        } catch (SQLException | ParseException e) {
        }
        return tasks;
    }


    @Override
    public void update(Task o) {

        try {
            PreparedStatement ps = connection.prepareStatement(UPDATE);
            ps.setString(1, sdf.format(o.getDueDate()));
            ps.setString(2, sdf.format(o.getEditDate()));
            ps.setLong(3, o.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }


    @Override
    public void remove(long id) {

        try {
            PreparedStatement ps = connection.prepareStatement(REMOVE_BY_ID);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }


    @Override
    public List<Task> get(String pattern) {

        List<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_BY_NAME);
            ps.setString(1, pattern + "%");
            ResultSet result = ps.executeQuery();
            assert result != null;
            while (result.next()) {
                tasks.add(Task.newBuiilder()
                        .setId(result.getLong(1))
                        .setName(result.getString(2))
                        .setDueDate(sdf.parse(result.getString(3)))
                        .setCreateDate(sdf.parse(result.getString(4)))
                        .setEditDate(sdf.parse(result.getString(5)))
                        .build());
            }
        } catch (SQLException | ParseException e) {
        }
        return tasks;
    }


    @Override
    public List<Task> get(String dueDateFrom, String dueDateTo) {

        List<Task> tasks = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_FROM_DATE_TO_DATE);
            ps.setString(1, dueDateFrom);
            ps.setString(2, dueDateTo);
            ResultSet result = ps.executeQuery();
            assert result != null;
            while (result.next()) {
                tasks.add(Task.newBuiilder()
                        .setId(result.getLong(1))
                        .setName(result.getString(2))
                        .setDueDate(sdf.parse(result.getString(3)))
                        .setCreateDate(sdf.parse(result.getString(4)))
                        .setEditDate(sdf.parse(result.getString(5)))
                        .build());
            }
        } catch (SQLException | ParseException e) {
        }
        return tasks;
    }


    @Override
    public void remove(String namePattern) {

        try {
            PreparedStatement ps = connection.prepareStatement(REMOVE_BY_NAME);
            ps.setString(1, namePattern + "%");
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }


    @Override
    public void remove(String dueDateFrom, String dueDateTo) {

        try {
            PreparedStatement ps = connection.prepareStatement(REMOVE_FROM_DATE_TO_DATE);
            ps.setString(1, dueDateFrom);
            ps.setString(2, dueDateTo);
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
}
