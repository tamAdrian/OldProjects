package repository;

import model.Participant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ParticipantRepository implements CrudRepository<Integer, Participant>, IParticipantRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public ParticipantRepository(Properties properties) {
        logger.info("Initializing ParticipantRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preStm = connection.prepareStatement("SELECT  COUNT(*) AS [SIZE] FROM Participanti")) {
            try (ResultSet resultSet = preStm.executeQuery()) {
                if (resultSet.next()) {
                    logger.traceExit(resultSet.getInt("SIZE"));
                    return resultSet.getInt("SIZE");
                }
            } catch (SQLException e) {
                logger.error(e);
                System.out.println("ERROR DB " + e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

        @Override
    public void save(Participant entity) {

        logger.traceEntry("saving participant {} ", entity);

        Connection connection = dbUtils.getConnection();

        try(PreparedStatement preStm = connection.prepareStatement("INSERT INTO Participanti values (?,?,?)")){

            preStm.setInt(1, entity.getID());
            preStm.setString(2, entity.getNume());
            preStm.setInt(3, entity.getVarsta());
            int result = preStm.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit();

    }

    @Override
    public void delete(Integer integer) {

        logger.traceEntry("deleting participant with {}", integer);

        Connection connection = dbUtils.getConnection();

        try(PreparedStatement preStm = connection.prepareStatement("DELETE FROM Participanti WHERE id = ?")){
            preStm.setInt(1, integer);
            int result = preStm.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Participant entity) {

        logger.traceEntry("updating participant with {}", integer);

        Connection connection = dbUtils.getConnection();

        try(PreparedStatement preStm = connection.prepareStatement("UPDATE Participanti SET id = ?, nume = ?, varsta = ? WHERE id = ?")){
            preStm.setInt(1, entity.getID());
            preStm.setString(2, entity.getNume());
            preStm.setInt(3, entity.getVarsta());
            preStm.setInt(4, integer);

            int result = preStm.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit();
    }

    @Override
    public Participant findOne(Integer integer) {

        logger.traceEntry("finding participant with id {} ",integer);
        Connection connection = dbUtils.getConnection();

        try(PreparedStatement preStmt = connection.prepareStatement("SELECT * FROM Participanti WHERE id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {

                    Participant participant =  extractParticipant(result);

                    logger.traceExit(participant);
                    return participant;
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit("No participant found with id {}", integer);

        return null;
    }

    @Override
    public Iterable<Participant> findAll() {

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();

        List<Participant> participanti = new ArrayList<>();

        try(PreparedStatement preStm = connection.prepareStatement("SELECT * FROM Participanti")) {
            try (ResultSet result = preStm.executeQuery()) {
                while (result.next()) {

                    Participant participant =  extractParticipant(result);
                    participanti.add(participant);
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }

        logger.traceExit(participanti);
        return participanti;

    }

    private Participant extractParticipant(ResultSet result){

        try {
            int id = result.getInt("id");
            String nume = result.getString("nume");
            int varsta = result.getInt("varsta");

            return new Participant(id, nume, varsta);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Participant findByNameAndAge(String nume, Integer varsta) {
        logger.traceEntry();
        Connection connection = dbUtils.getConnection();


        try(PreparedStatement preStm = connection.prepareStatement("SELECT * FROM Participanti WHERE nume = ? AND varsta = ?")) {
            try (ResultSet result = preStm.executeQuery()) {
                if (result.next()) {
                    return extractParticipant(result);
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }

        logger.traceExit();
        return null;
    }
}
