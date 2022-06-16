package dao;

import model.Person;
import view.command.exceptions.ExecuteCommandException;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.TreeSet;

public class PersonDAO implements DAO<Person> {
    private final Connection connection;
    private final String getById = "SELECT * FROM Persons WHERE id=?";
    private final String create = "INSERT INTO Persons(name,birthday,weight,passportID) VALUES (?,?,?,?) RETURNING id";
    private final String update = "UPDATE Persons SET name=?, birthday=?, weight=?, passportID=? WHERE id = ?";
    private final String getAll = "SELECT * FROM Persons";
    private final String deleteByOwner = "DELETE FROM Persons WHERE owner=?";
    private final String deleteById = "DELETE FROM Persons WHERE id=?";

    public PersonDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Person getById(String id) throws ExecuteCommandException {
        try {
            PreparedStatement statement = connection.prepareStatement(getById);
            statement.setInt(1, Integer.parseInt(id));
            ResultSet personsFromDb = statement.executeQuery();
            Person person = null;
            LocalDateTime birthday = null;
            while (personsFromDb.next()){
                int nId = personsFromDb.getInt("id");
                String name = personsFromDb.getString("name");
                if (personsFromDb.getObject("birthday")!=null) birthday = personsFromDb.getTimestamp("birthday").toLocalDateTime();
                float weight = personsFromDb.getFloat("weight");
                String passportID = personsFromDb.getString("passportID");
                person = new Person(nId,name,birthday,weight,passportID);
                break;
            }
            return person;
        }catch (SQLException e) {
            e.printStackTrace();
            throw new ExecuteCommandException();// поменять
        }
    }

    @Override
    public Person create(Person person) throws ExecuteCommandException {
        String id = null;
        try {
            PreparedStatement statement = connection.prepareStatement(create);
            statement.setString(1, person.getName());
            if (person.getBirthday()==null) statement.setNull(2,Types.TIMESTAMP);
            else statement.setTimestamp(2,Timestamp.valueOf(person.getBirthday()));
            statement.setFloat(3, person.getWeight());
            statement.setString(4,person.getPassportID());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            id = resultSet.getString(1);
        }catch (SQLException e){
            e.printStackTrace();
            throw new ExecuteCommandException();
        }
            return getById(id);

    }

    @Override
    public void update(Person person, String id) throws ExecuteCommandException {
        try {
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setString(1, person.getName());
            statement.setTimestamp(2,Timestamp.valueOf(person.getBirthday()));
            statement.setFloat(3, person.getWeight());
            statement.setString(4,person.getPassportID());
            statement.setInt(5,Integer.parseInt(id));
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            throw new ExecuteCommandException();
        }
    }

    @Override
    public TreeSet<Person> getAll(String query) throws ExecuteCommandException {
        TreeSet<Person> people = new TreeSet<>();
        LocalDateTime birthday = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(getAll);
            while (result.next()){
                int nId = result.getInt("id");
                String name = result.getString("name");
                if (result.getObject("birthday")!=null)  birthday = result.getTimestamp("birthday").toLocalDateTime();
                float weight = result.getFloat("weight");
                String passportID = result.getString("passportID");
                people.add(new Person(nId,name,birthday,weight,passportID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ExecuteCommandException();
        }
        return people;
    }

    @Override
    public TreeSet<Person> getByOwner(int id) throws ExecuteCommandException {
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
