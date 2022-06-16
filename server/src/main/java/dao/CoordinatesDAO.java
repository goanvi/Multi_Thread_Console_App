package dao;

import model.Coordinates;
import view.command.exceptions.ExecuteCommandException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;

public class CoordinatesDAO implements DAO<Coordinates> {
    private final Connection connection;
    private final String getById = "SELECT * FROM Coordinates WHERE id=?";
    private final String create = "INSERT INTO Coordinates(X,Y) VALUES (?,?) RETURNING id";
    private final String update = "UPDATE Coordinates SET X=?, Y=? WHERE id = ?";
    private final String getAll = "SELECT * FROM Coordinates";
    private final String deleteById = "DELETE FROM Coordinates WHERE id=?";

    public CoordinatesDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Coordinates getById(String id) throws ExecuteCommandException {
        try {
            PreparedStatement statement = connection.prepareStatement(getById);
            statement.setInt(1, Integer.parseInt(id));
            ResultSet coordinatesFromDb = statement.executeQuery();
            Coordinates coordinates = null;
            while (coordinatesFromDb.next()){
                int coordinatesId = coordinatesFromDb.getInt("id");//пока не уверен, что это нужно
                int x = coordinatesFromDb.getInt("X");
                int y = coordinatesFromDb.getInt("Y");
                coordinates = new Coordinates(coordinatesId,x,y);
                break;
            }
            return coordinates;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new ExecuteCommandException();
        }
    }

    @Override
    public Coordinates create(Coordinates coordinates) throws ExecuteCommandException {
        int id = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(create);
            statement.setInt(1, coordinates.getX());
            statement.setInt(2,coordinates.getY());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            id = resultSet.getInt(1);

        }catch (SQLException e){
            e.printStackTrace();
            throw new ExecuteCommandException();
        }
            return getById(Integer.toString(id));
    }

    @Override
    public void update(Coordinates coordinates, String id) throws ExecuteCommandException {
        try {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setInt(1,coordinates.getX());
            statement.setInt(2,coordinates.getY());
            statement.setInt(3,Integer.parseInt(id));
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            throw new ExecuteCommandException();
        }

    }

    @Override
    public TreeSet<Coordinates> getByOwner(int id) throws ExecuteCommandException {
        return null;
    }

    @Override
    public TreeSet<Coordinates> getAll(String query) throws ExecuteCommandException {
        return null;
    }

    @Override
    public void deleteById(String id) throws ExecuteCommandException {
        try {
            PreparedStatement statement = connection.prepareStatement(deleteById);
            statement.setInt(1,Integer.parseInt(id));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExecuteCommandException();
        }
    }

    @Override
    public void deleteByOwner(String id) throws ExecuteCommandException {

    }
}
