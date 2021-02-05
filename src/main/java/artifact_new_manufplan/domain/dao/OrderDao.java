package artifact_new_manufplan.domain.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import artifact_new_manufplan.domain.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static artifact_new_manufplan.log.ClassNameUtil.getCurrentClassName;

public class OrderDao {

    private Connection connPostgres;
    private static final Logger log = LoggerFactory.getLogger(getCurrentClassName());
    private static final String SQL_GET_ALL = "SELECT * FROM orders;";

    public OrderDao(Connection conn) {
        this.connPostgres = conn;
        log.debug("Get connection to PostgreSQL from {}.", UtilDao.class);
    }


    public List<Order> getAll() {
        List<Order> result = new ArrayList<>();
        try {
            Statement statement = connPostgres.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GET_ALL);
            log.debug("Select all 'Order'. SQL = {}.", SQL_GET_ALL);

            while (rs.next()) {
                String idOrder = rs.getString("iddoc");
                Timestamp dateCreate = rs.getTimestamp("t_create");
                Timestamp dateToFactory = rs.getTimestamp("t_factory");
                Timestamp dateToShipment = rs.getTimestamp("t_end");
                String docNumber = rs.getString("docno");
                String clientName = rs.getString("client_name");
                String managerName = rs.getString("manager_name");

                Order order = new Order(idOrder, dateCreate, dateToFactory, dateToShipment,
                        docNumber, clientName, managerName);

                result.add(order);
            }
            log.debug("Was read {} Order from Postgres.", result.size());
            return result;
        } catch (SQLException e) {
            log.warn("Exception during reading all 'Order'.", e);
        }
        log.debug("Order not found.");
        return null;
    }


}
