package repository;
import javafx.util.Pair;
import model.Inscriere;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class InscriereRepository implements CrudRepository<Pair<Integer, Integer>, Inscriere>, IInscriereRepository {

    private JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public InscriereRepository(Properties properties) {
        logger.info("Initializing InscriereRepository with properties: {} ", properties);
        dbUtils = new JdbcUtils(properties);
    }

    @Override
    public int size() {

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preStm = connection.prepareStatement("SELECT  COUNT(*) AS [SIZE] FROM Inscrieri")) {
            try (ResultSet result = preStm.executeQuery()) {
                if (result.next()) {
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            } catch (SQLException ex) {
                logger.error(ex);
                System.out.println("ERROR DB  " + ex);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void save(Inscriere entity) {
        logger.traceEntry("saving 'inscrire' {} ", entity);

        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preStm = connection.prepareStatement("INSERT INTO Inscrieri VALUES (?, ?)")) {
            preStm.setInt(1, entity.getID().getKey());
            preStm.setInt(2, entity.getID().getValue());
            int result = preStm.executeUpdate();

        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB  " + ex);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Pair<Integer, Integer> pair) {
        logger.traceEntry("deleting 'inscriere' with {}", pair);

        Connection connection = dbUtils.getConnection();
        try (PreparedStatement preStm = connection.prepareStatement("DELETE FROM Inscrieri WHERE idParticipant = ? AND idProba = ?")) {
            preStm.setInt(1, pair.getKey());
            preStm.setInt(2, pair.getValue());
            int result = preStm.executeUpdate();

        } catch (SQLException ex) {
            logger.error(ex);
            System.out.println("Error DB  " + ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Pair<Integer, Integer> idPair, Inscriere entity) {
        logger.traceEntry("updating 'inscriere' with {}", entity);

        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preStm = connection.prepareStatement("UPDATE Inscrieri SET idParticipant = ?, idProba  = ? WHERE idParticipant = ?, idProba  = ?")) {
            preStm.setInt(1, entity.getID().getKey());
            preStm.setInt(2, entity.getID().getValue());
            preStm.setInt(3, idPair.getKey());
            preStm.setInt(3, idPair.getValue());
            int result = preStm.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB  " + e);
        }

        logger.traceExit();
    }

    @Override
    public Inscriere findOne(Pair<Integer, Integer> pair) {
        logger.traceEntry("finding 'inscriere' with id {} ", pair);

        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preStm = connection.prepareStatement("SELECT * FROM Inscrieri WHERE idParticipant = ? AND idProba = ?")) {
            preStm.setInt(1, pair.getKey());
            preStm.setInt(2, pair.getValue());
            try (ResultSet result = preStm.executeQuery()) {
                if (result.next()) {

                    Inscriere inscriere = extractInscriere(result);
                    logger.traceExit(inscriere);

                    return inscriere;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB  " + e);
        }
        logger.traceExit("No 'persoanaOficiu' found with id {}", pair);

        return null;
    }

    @Override
    public Iterable<Inscriere> findAll() {
        logger.traceEntry();

        Connection connection = dbUtils.getConnection();
        List<Inscriere> list = new ArrayList<>();

        try (PreparedStatement preStm = connection.prepareStatement("SELECT * FROM Inscrieri")) {
            try (ResultSet result = preStm.executeQuery()) {
                while (result.next()) {

                    Inscriere inscriere = extractInscriere(result);
                    list.add(inscriere);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit(list);
        return list;
    }

    private Inscriere extractInscriere(ResultSet result) {
        try {
            Integer idParticipant = result.getInt("idParticipant");
            Integer idProba = result.getInt("idProba");

            return new Inscriere(idParticipant, idProba);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Integer> findByProbaId(Integer idProba) {
        logger.traceEntry();

        Connection connection = dbUtils.getConnection();
        List<Integer> list = new ArrayList<>();

        try (PreparedStatement preStm = connection.prepareStatement("SELECT * FROM Inscrieri WHERE idProba = ?")) {
            preStm.setInt(1, idProba);
            try (ResultSet result = preStm.executeQuery()) {
                while (result.next()) {
                    Inscriere inscriere = extractInscriere(result);
                    list.add(inscriere.getID().getKey());
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit(list);
        return list;
    }

    @Override
    public List<Integer> findByParticipantId(Integer idParticipant) {
        logger.traceEntry();

        Connection connection = dbUtils.getConnection();
        List<Integer> list = new ArrayList<>();

        try (PreparedStatement preStm = connection.prepareStatement("SELECT * FROM Inscrieri WHERE idParticipant = ?")) {
            preStm.setInt(1, idParticipant);
            try (ResultSet result = preStm.executeQuery()) {
                while (result.next()) {
                    Inscriere inscriere = extractInscriere(result);
                    list.add(inscriere.getID().getValue());
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }

        logger.traceExit(list);
        return list;
    }

    @Override
    public Map<Integer, Integer> groupByProbaId() {

        Map<Integer, Integer> map = new HashMap<>();

        logger.traceEntry();
        Connection connection = dbUtils.getConnection();

        try (PreparedStatement preStm = connection.prepareStatement("SELECT idProba, count(*) as numar FROM Inscrieri GROUP BY idProba")) {
            try(ResultSet resultSet = preStm.executeQuery()){
                while (resultSet.next()){
                    map.put(resultSet.getInt("idProba") , resultSet.getInt("numar"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
}