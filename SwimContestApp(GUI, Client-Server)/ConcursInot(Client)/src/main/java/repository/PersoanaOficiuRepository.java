package repository;

import model.PersoanaOficiu;
import model.Proba;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PersoanaOficiuRepository implements CrudRepository<String, PersoanaOficiu>, IPersoanaOficiuRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public PersoanaOficiuRepository(Properties properties) {
        logger.info("Initializing PersoanaOficiuRepository with properties: {} ", properties);
        this.dbUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {
        logger.traceEntry();

        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preStm = connection.prepareStatement("SELECT COUNT(*) AS [SIZE] FROM PersoaneOficiu")){
            try(ResultSet result = preStm.executeQuery()){
                if(result.next()){
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        return 0;
    }

    @Override
    public void save(PersoanaOficiu entity) {
        logger.traceEntry("saving 'persoanaOficiu' {} ",entity);

        Connection connection = dbUtils.getConnection();

        try(PreparedStatement preStm = connection.prepareStatement("INSERT INTO PersoaneOficiu VALUES (?, ?)")){
            preStm.setString(1, entity.getID());
            preStm.setString(2, entity.getPassword());
            int result = preStm.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(String s) {
        logger.traceEntry("deleting task with {}", s);

        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preStm = connection.prepareStatement("DELETE FROM PersoaneOficiu WHERE id = ?")){
            preStm.setString(1, s);
            int result = preStm.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(String s, PersoanaOficiu entity) {

        logger.traceEntry("updating 'persoanaOficiu' with {}", s);

        Connection connection = dbUtils.getConnection();

        try(PreparedStatement preStm = connection.prepareStatement("UPDATE PersoaneOficiu SET id = ?, password = ? WHERE id = ?")){
            preStm.setString(1, entity.getID());
            preStm.setString(2, entity.getPassword());
            preStm.setString(3, s);
            int result = preStm.executeUpdate();
        }catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit();


    }

    @Override
    public PersoanaOficiu findOne(String s) {
        logger.traceEntry("finding 'persoanaOficiu' with id {} ", s);

        Connection connection = dbUtils.getConnection();

        try(PreparedStatement preStm = connection.prepareStatement("SELECT * FROM PersoaneOficiu WHERE id = ?")){
            preStm.setString(1, s);
            try(ResultSet result = preStm.executeQuery()){
                if(result.next()){

                    PersoanaOficiu persoanaOficiu = extractPersoanaOficiu(result);
                    logger.traceExit(persoanaOficiu);

                    return persoanaOficiu;
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit("No 'persoanaOficiu' found with id {}", s);

        return null;

    }

    @Override
    public Iterable<PersoanaOficiu> findAll() {
        logger.traceEntry();

        Connection connection = dbUtils.getConnection();
        List<PersoanaOficiu> persoanaOficiuList = new ArrayList<>();

        try(PreparedStatement preStm = connection.prepareStatement("SELECT * FROM PersoaneOficiu")) {
            try (ResultSet result = preStm.executeQuery()) {
                while (result.next()) {

                    PersoanaOficiu persoanaOficiu =  extractPersoanaOficiu(result);
                    persoanaOficiuList.add(persoanaOficiu);
                }
            }
        }catch (SQLException e){
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit(persoanaOficiuList);
        return persoanaOficiuList;
    }

    private PersoanaOficiu extractPersoanaOficiu(ResultSet result){
        try {
            String id = result.getString("id");
            String password = result.getString("password");

            return new PersoanaOficiu(id, password);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean findByUsernameAndPassword(String username, String password) {

        logger.traceEntry("finding 'persoana oficiu' with username {} and password {}", username, password);
        PersoanaOficiu persoanaOficiu = findOne(username);
        if( persoanaOficiu != null && persoanaOficiu.getPassword().equals(password)){
            logger.traceExit(persoanaOficiu);
            return true;
        }
        logger.traceExit("No 'persoana oficiu' with username {} and password {}", username);
        return false;

    }
}
