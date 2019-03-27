package repository;

import model.Distanta;
import model.Participant;
import model.Proba;
import model.Stil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProbaRepository implements CrudRepository<Integer, Proba> {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public ProbaRepository(Properties properties) {
        logger.info("Initializing ProbaRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();

        try(PreparedStatement preStmt = connection.prepareStatement("SELECT COUNT(*) AS [SIZE] FROM Probe")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ ex);
        }
        return 0;
    }

    @Override
    public void save(Proba entity) {

        logger.traceEntry("saving 'proba' {} ", entity);

        Connection connection = dbUtils.getConnection();

        try(PreparedStatement preStm = connection.prepareStatement("INSERT INTO Probe values (?,?,?)")){

            preStm.setInt(1, entity.getID());
            preStm.setString(2, entity.getDistanta().toString());
            preStm.setString(3, entity.getStil().toString());
            int result = preStm.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit();

    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting 'proba' with {}", integer);

        Connection connection = dbUtils.getConnection();

        try(PreparedStatement preStm = connection.prepareStatement("DELETE FROM Probe WHERE id = ?")){
            preStm.setInt(1, integer);
            int result = preStm.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Proba entity) {

        logger.traceEntry("updating 'proba' with {}", integer);

        Connection connection = dbUtils.getConnection();

        try(PreparedStatement preStm = connection.prepareStatement("UPDATE Probe SET id = ?, distanta = ?, stil = ? WHERE id = ?")){

            preStm.setInt(1, entity.getID());
            preStm.setString(2, entity.getDistanta().toString());
            preStm.setString(3, entity.getStil().toString());
            preStm.setInt(4, integer);

            int result = preStm.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit();
    }

    @Override
    public Proba findOne(Integer integer) {
        logger.traceEntry("finding 'proba' with id {} ",integer);
        Connection connection = dbUtils.getConnection();

        try(PreparedStatement preStmt = connection.prepareStatement("SELECT * FROM Probe WHERE id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {

                    Proba proba =  extractProba(result);

                    logger.traceExit(proba);
                    return proba;
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
    public Iterable<Proba> findAll() {

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();

        List<Proba> probe = new ArrayList<>();

        try(PreparedStatement preStm = connection.prepareStatement("SELECT * FROM Probe")) {
            try (ResultSet result = preStm.executeQuery()) {
                while (result.next()) {

                    Proba proba =  extractProba(result);
                    probe.add(proba);
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB "+e);
        }

        logger.traceExit(probe);
        return probe;
    }

    private Proba extractProba(ResultSet result){

        try {
            int id = result.getInt("id");
            String dist = result.getString("distanta");
            String stil = result.getString("stil");

            return new Proba(id, Distanta.valueOf(dist), Stil.valueOf(stil));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
