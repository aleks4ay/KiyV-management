package artifact_new_manufplan.domain.dao;

import artifact_new_manufplan.domain.model.Description;
import artifact_new_manufplan.domain.model.Tmc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static artifact_new_manufplan.log.ClassNameUtil.getCurrentClassName;

public class DescriptionDao {

    private Connection connPostgres;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String SQL_GET_ALL = "SELECT * FROM descriptions;";

    public DescriptionDao(Connection conn) {
        this.connPostgres = conn;
        log.debug("Get connection to PostgreSQL from {}.", UtilDao.class);
    }


    public List<Description> getAll() {
        List<Description> result = new ArrayList<>();
        try {
            Statement statement = connPostgres.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GET_ALL);
            log.debug("Select all 'Description'. SQL = {}.", SQL_GET_ALL);

            while (rs.next()) {
                String id = rs.getString("id");
                String idOrder = rs.getString("iddoc");
                int position = rs.getInt("position");
                int quantity = rs.getInt("quantity");
                String descrSecond = rs.getString("descr_second");
                int sizeA = rs.getInt("size_a");
                int sizeB = rs.getInt("size_b");
                int sizeC = rs.getInt("size_c");
                String idTmc = rs.getString("id_tmc");
                Tmc tmc = new Tmc(idTmc);

                Description description = new Description(id, idOrder, position, quantity, descrSecond,
                sizeA, sizeB, sizeC, tmc);

                result.add(description);
            }
            log.debug("Was read {} Description from Postgres.", result.size());
            return result;
        } catch (SQLException e) {
            log.warn("Exception during reading all 'Description'.", e);
        }
        log.debug("Description not found.");
        return null;
    }


}
