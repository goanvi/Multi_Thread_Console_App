package dao;

import controller.StudyGroupComparator;
import model.*;
import model.Exceptions.IncorrectNameEnumException;
import view.command.exceptions.ExecuteCommandException;

import java.sql.*;
import java.time.LocalDate;
import java.util.TreeSet;

public class StudyGroupDAO implements DAO<StudyGroup> {
    private final Connection connection;
    private final CoordinatesDAO coordinatesDAO;
    private final PersonDAO personDAO;
    private boolean autoCommit = true;
    private final String getAll = "SELECT * FROM StudyGroups";
    private final String getById = "SELECT * FROM StudyGroups WHERE id=?";
    private final String getByOwner = "SELECT * FROM StudyGroups WHERE ownerId=?";
    private final String create = "INSERT INTO StudyGroups(ownerId,name,coordinates,creationDate,studentsCount,averageMark,formOfEducation, semester, person) VALUES (?,?,?,?,?,?,CAST(? AS formofeducation),CAST(? AS semester),?) RETURNING id";
    private final String update = "UPDATE StudyGroups SET name=?, studentsCount=?, averageMark=?, formOfEducation=CAST(? AS formofeducation), semester=CAST(? AS semester), person=? WHERE id = ?";
    private final String deleteByOwner = "DELETE FROM StudyGroups WHERE ownerId=?";
    private final String deleteById = "DELETE FROM StudyGroups WHERE id=?";


    public StudyGroupDAO(Connection connection, CoordinatesDAO coordinatesDAO, PersonDAO personDAO) {
        this.connection = connection;
        this.coordinatesDAO = coordinatesDAO;
        this.personDAO = personDAO;
    }

    private void setAutoCommit() {
        this.autoCommit = !this.autoCommit;
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public StudyGroup getById(String id) throws ExecuteCommandException {
        if (autoCommit) setAutoCommit();
        StudyGroup studyGroup = null;
        Person person = null;
        try {
            PreparedStatement statement = connection.prepareStatement(getById);
            statement.setInt(1, Integer.parseInt(id));
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int groupId = result.getInt("id");
                int ownerId = result.getInt("ownerId");
                String name = result.getString("name");
                Coordinates coordinates = coordinatesDAO.getById(Integer.toString(result.getInt("coordinates")));
                LocalDate date = result.getDate("creationDate").toLocalDate();
                long studentsCount = result.getLong("studentsCount");
                double averageMark = result.getFloat("averageMark");
                FormOfEducation formOfEducation = FormOfEducation.convert(result.getString("formOfEducation").trim());
                Semester semester = Semester.equals(result.getString("semester").trim());
                if (result.getObject("person") != null)
                    person = personDAO.getById(Integer.toString(result.getInt("person")));
                studyGroup = new StudyGroup(groupId, ownerId, name, coordinates, date, studentsCount, averageMark, formOfEducation, semester, person);
            }
            if (!autoCommit) {
                connection.commit();
                setAutoCommit();
            }
        } catch (IncorrectNameEnumException exep) {
            exep.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (!autoCommit) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new ExecuteCommandException();
        }
        return studyGroup;
    }

    @Override
    public TreeSet<StudyGroup> getByOwner(int id) throws ExecuteCommandException {
        if (autoCommit) setAutoCommit();
        StudyGroupComparator comparator = new StudyGroupComparator();
        TreeSet<StudyGroup> groups = new TreeSet<>(comparator);
        StudyGroup studyGroup = null;
        Person person = null;
        try {
            PreparedStatement statement = connection.prepareStatement(getByOwner);
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                int groupId = result.getInt("id");
                int ownerId = result.getInt("ownerId");
                String name = result.getString("name");
                Coordinates coordinates = coordinatesDAO.getById(Integer.toString(result.getInt("coordinates")));
                LocalDate date = result.getDate("creationDate").toLocalDate();
                long studentsCount = result.getLong("studentsCount");
                double averageMark = result.getFloat("averageMark");
                FormOfEducation formOfEducation = FormOfEducation.convert(result.getString("formOfEducation").trim());
                Semester semester = Semester.equals(result.getString("semester").trim());
                if (result.getObject("person") != null)
                    person = personDAO.getById(Integer.toString(result.getInt("person")));
                studyGroup = new StudyGroup(groupId, ownerId, name, coordinates, date, studentsCount, averageMark, formOfEducation, semester, person);
                groups.add(studyGroup);
            }
            if (!autoCommit) {
                connection.commit();
                setAutoCommit();
            }
        } catch (SQLException | IncorrectNameEnumException e) {
            e.printStackTrace();
            try {
                if (!autoCommit) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new ExecuteCommandException();
        }
        return groups;
    }

    @Override
    public StudyGroup create(StudyGroup studyGroup) throws ExecuteCommandException {
        if (autoCommit) setAutoCommit();
        String id = null;
        try {
            PreparedStatement statement = connection.prepareStatement(create);
            statement.setInt(1, studyGroup.getOwnerId());
            statement.setString(2, studyGroup.getName());
            statement.setInt(3, coordinatesDAO.create(studyGroup.getCoordinates()).getId());
            statement.setDate(4, Date.valueOf(studyGroup.getCreationDate()));
            statement.setLong(5, studyGroup.getStudentsCount());
            statement.setDouble(6, studyGroup.getAverageMark());
            statement.setString(7, studyGroup.getFormOfEducation().getName());
            statement.setString(8, studyGroup.getSemesterEnum().getName());
            if (studyGroup.getGroupAdmin() == null) statement.setNull(9, Types.INTEGER);
            else statement.setInt(9, personDAO.create(studyGroup.getGroupAdmin()).getId());
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            id = resultSet.getString(1);
            if (!autoCommit) {
                connection.commit();
                setAutoCommit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (!autoCommit) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new ExecuteCommandException();
        }
        return getById(id);
    }

    @Override
    public void update(StudyGroup object, String id) throws ExecuteCommandException {
        if (autoCommit) setAutoCommit();
        try {
            StudyGroup chGroup = getById(id);
            PreparedStatement statement = connection.prepareStatement(update);
            statement.setInt(7, Integer.parseInt(id));
            statement.setString(1, (object.getName() == null ? chGroup.getName() : object.getName()));
            if (!(object.getCoordinates() == null)) {
                coordinatesDAO.update(object.getCoordinates(), Integer.toString(chGroup.getCoordinates().getId()));
            }
            statement.setLong(2, (object.getStudentsCount() == 0 ? chGroup.getStudentsCount() : object.getStudentsCount()));
            statement.setDouble(3, (object.getAverageMark() == 0 ? chGroup.getAverageMark() : object.getAverageMark()));
            statement.setString(4, (object.getFormOfEducation() == null ? chGroup.getFormOfEducation().getName() : object.getFormOfEducation().getName()));
            statement.setString(5, (object.getSemesterEnum() == null ? chGroup.getSemesterEnum().getName() : object.getSemesterEnum().getName()));
            if (object.getGroupAdmin() != null) {
                personDAO.update(object.getGroupAdmin(), Integer.toString(chGroup.getGroupAdmin().getId()));
                statement.setInt(6, chGroup.getGroupAdmin().getId());
                statement.executeUpdate();
            } else {
                statement.setNull(6, Types.INTEGER);
                statement.executeUpdate();
                personDAO.deleteById(Integer.toString(chGroup.getGroupAdmin().getId()));
            }

            if (!autoCommit) {
                connection.commit();
                setAutoCommit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (!autoCommit) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new ExecuteCommandException();
        }
    }

    @Override
    public void deleteById(String id) throws ExecuteCommandException {
        if (autoCommit) setAutoCommit();
        StudyGroup studyGroup = null;
            studyGroup = getById(id);
        try {
            PreparedStatement statement = connection.prepareStatement(deleteById);
            statement.setInt(1, Integer.parseInt(id));
            statement.execute();
            if (!autoCommit) {
                connection.commit();
                setAutoCommit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (!autoCommit) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new ExecuteCommandException();
        }
        coordinatesDAO.deleteById(Integer.toString(studyGroup.getCoordinates().getId()));
        if (studyGroup.getGroupAdmin() != null)
            personDAO.deleteById(Integer.toString(studyGroup.getGroupAdmin().getId()));
    }

    @Override
    public void deleteByOwner(String id) throws ExecuteCommandException {
        if (autoCommit) setAutoCommit();
        TreeSet<StudyGroup> deleted = null;
            deleted = getByOwner(Integer.parseInt(id));
        try {
            PreparedStatement statement = connection.prepareStatement(deleteByOwner);
            statement.setInt(1, Integer.parseInt(id));
            statement.execute();
            if (!autoCommit) {
                connection.commit();
                setAutoCommit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (!autoCommit) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new ExecuteCommandException();
        }
        deleted.forEach((group) -> {
            try {
                coordinatesDAO.deleteById(Integer.toString(group.getCoordinates().getId()));
                if (group.getGroupAdmin() != null)
                    personDAO.deleteById(Integer.toString(group.getGroupAdmin().getId()));
            }catch (ExecuteCommandException e){e.printStackTrace();}
        });
    }

    @Override
    public TreeSet<StudyGroup> getAll(String query) throws ExecuteCommandException {
        if (autoCommit) setAutoCommit();
        StudyGroup studyGroup = null;
        Person person = null;
        StudyGroupComparator comparator = new StudyGroupComparator();
        TreeSet<StudyGroup> groups = new TreeSet<>(comparator);
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(getAll);
            while (result.next()) {
                int groupId = result.getInt("id");
                int ownerId = result.getInt("ownerId");
                String name = result.getString("name");
                Coordinates coordinates = coordinatesDAO.getById(Integer.toString(result.getInt("coordinates")));
                LocalDate date = result.getDate("creationDate").toLocalDate();
                long studentsCount = result.getLong("studentsCount");
                double averageMark = result.getFloat("averageMark");
                FormOfEducation formOfEducation = FormOfEducation.convert(result.getString("formOfEducation").trim());
                Semester semester = Semester.equals(result.getString("semester").trim());
                if (result.getObject("person") != null)
                    person = personDAO.getById(Integer.toString(result.getInt("person")));
                studyGroup = new StudyGroup(groupId, ownerId, name, coordinates, date, studentsCount, averageMark, formOfEducation, semester, person);
                groups.add(studyGroup);
            }
            if (!autoCommit) {
                connection.commit();
                setAutoCommit();
            }
        } catch (SQLException | IncorrectNameEnumException e) {
            e.printStackTrace();
            try {
                if (!autoCommit) connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new ExecuteCommandException();
        }
        return groups;
    }

    public boolean checkStudyGroupByOwner(String studyGroupId, String ownerId) {
        StudyGroup studyGroup = null;
        try {
            studyGroup = getById(studyGroupId);
        } catch (ExecuteCommandException e) {
            e.printStackTrace();
        }
        return ownerId.equals(String.valueOf(studyGroup.getOwnerId()));
    }
}
