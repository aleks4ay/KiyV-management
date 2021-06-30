package fill_status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class StatusDaoJdbc {

    private Connection connPostgres;
    private static final Logger log = LoggerFactory.getLogger(StatusDaoJdbc.class);

    private static final String SQL_GET_NUMBER = "SELECT docno FROM orders WHERE iddoc = ?;";
    private static final String SQL_GET_ORDER = "SELECT * FROM orders WHERE iddoc = ?;";

    private static final String SQL_GET_ID = "SELECT iddoc FROM orders WHERE docno LIKE ? AND t_create BETWEEN ? AND ?;";

    private static final String SQL_GET_ONE = "SELECT * FROM statuses WHERE id = ?;";

    private static final String SQL_UPDATE_T1 = "UPDATE statuses SET time_1=? WHERE id =?;";
    private static final String SQL_UPDATE_T2 = "UPDATE statuses SET time_2=? WHERE id =?;";
    private static final String SQL_UPDATE_T3 = "UPDATE statuses SET time_3=? WHERE id =?;";
    private static final String SQL_UPDATE_T4 = "UPDATE statuses SET time_4=? WHERE id =?;";
    private static final String SQL_UPDATE_T5 = "UPDATE statuses SET time_5=? WHERE id =?;";
    private static final String SQL_UPDATE_T7 = "UPDATE statuses SET time_7=? WHERE id =?;";
    private static final String SQL_UPDATE_T21 = "UPDATE statuses SET time_21=? WHERE id =?;";
    private static final String SQL_UPDATE_T22 = "UPDATE statuses SET time_22=? WHERE id =?;";
    private static final String SQL_UPDATE_T24 = "UPDATE statuses SET time_24=? WHERE id =?;";

    private static final String SQL_UPDATE_STATUS = "UPDATE statuses SET status_index=? WHERE id =?;";
    private static final String SQL_UPDATE_TYPE = "UPDATE statuses SET type_index=? WHERE id =?;";

    public StatusDaoJdbc(Connection conn) {
        this.connPostgres = conn;
        try {
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getDocNumber(String idDoc) {
        try {
            PreparedStatement statement = connPostgres.prepareStatement(SQL_GET_NUMBER);
            statement.setString(1, idDoc);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getString("docno");
        } catch (SQLException e) {
            log.warn("Exception during reading 'Document number' with idDoc = {}.", idDoc, e);
        }
        log.debug("Order with idDoc = {} not found.", idDoc);
        return null;
    }

    public OrderDTO getOrder(String id) {
        try {
            PreparedStatement statement = connPostgres.prepareStatement(SQL_GET_ORDER);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new OrderDTO(id, rs.getString("client_name"), rs.getString("manager_name"),
                        rs.getString("docno"), rs.getTimestamp("t_create"), rs.getTimestamp("t_factory"));
            }
        } catch (SQLException e) {
            log.warn("Exception during reading 'Document number' with idDoc = {}.", id, e);
        }
        log.debug("Order with idDoc = {} not found.", id);
        return null;
    }

    public String getIdByDocNumber(String docNumber, int year) {
        try {
            PreparedStatement ps = connPostgres.prepareStatement(SQL_GET_ID);
            ps.setString(1, "%" + docNumber);
            Timestamp start = Timestamp.valueOf(year + "-01-01 0:0:0");
            Timestamp end = Timestamp.valueOf(year+1 + "-01-01 0:0:0");
            ps.setTimestamp(2, start);
            ps.setTimestamp(3, end);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString("iddoc");
        } catch (SQLException e) {
            log.warn("Exception during reading 'Document number' with idDoc = {}.", docNumber, e);
        }
        log.debug("Order with idDoc = {} not found.", docNumber);
        return null;
    }

    public StatusDTO getOne(String id) {
        try {
            PreparedStatement ps = connPostgres.prepareStatement(SQL_GET_ONE);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new StatusDTO(rs.getString("id"), rs.getString("iddoc"), rs.getLong("time_0"),
                        rs.getLong("time_1"), rs.getLong("time_2"), rs.getLong("time_3"), rs.getLong("time_4"),
                        rs.getLong("time_5"), rs.getLong("time_7"), rs.getLong("time_21"),
                        rs.getLong("time_22"), rs.getLong("time_24"), rs.getInt("type_index"), rs.getInt("status_index"),
                        rs.getInt("is_technologichka"), rs.getInt("is_parsing"), rs.getString("designer_name"),
                        rs.getString("descr_first"));
            }
        } catch (SQLException e) {
            log.warn("Exception during reading all 'Order'.", e);
        }
        log.debug("Orders not found.");
        return null;
    }

    private boolean updateInt(String sql, String id, int newData) {
        try {
            PreparedStatement ps = connPostgres.prepareStatement(sql);
            ps.setInt(1, newData);
            ps.setString(2, id);
            ps.execute();
            log.debug("Status with id '{}' updated.", id);
            return true;
        } catch (SQLException e) {
            log.warn("Exception during update Status with id '{}'.", id, e);
        }
        return false;
    }

    private boolean updateLong(String sql, String id, long newData) {
        try {
            PreparedStatement ps = connPostgres.prepareStatement(sql);
            ps.setLong(1, newData);
            ps.setString(2, id);
            ps.executeUpdate();
            log.debug("Status with id '{}' updated.", id);
            return true;
        } catch (SQLException e) {
            log.warn("Exception during update Status with id '{}'.", id, e);
        }
        return false;
    }

    public boolean updateT1(String id, long newData) {
        return updateLong(SQL_UPDATE_T1, id ,newData);
    }

    public boolean updateT2(String id, long newData) {
        return updateLong(SQL_UPDATE_T2, id ,newData);
    }

    public boolean updateT3(String id, long newData) {
        return updateLong(SQL_UPDATE_T3, id ,newData);
    }

    public boolean updateT4(String id, long newData) {
        return updateLong(SQL_UPDATE_T4, id ,newData);
    }

    public boolean updateT5(String id, long newData) {
        return updateLong(SQL_UPDATE_T5, id ,newData);
    }

    public boolean updateT7(String id, long newData) {
        return updateLong(SQL_UPDATE_T7, id ,newData);
    }

    public boolean updateT21(String id, long newData) {
        return updateLong(SQL_UPDATE_T21, id ,newData);
    }

    public boolean updateT22(String id, long newData) {
        return updateLong(SQL_UPDATE_T22, id ,newData);
    }

    public boolean updateT24(String id, long newData) {
        return updateLong(SQL_UPDATE_T24, id ,newData);
    }

    public boolean updateStatus(String id, long newData) {
        return updateLong(SQL_UPDATE_STATUS, id ,newData);
    }

    public boolean updateType(String id, long newData) {
        return updateLong(SQL_UPDATE_TYPE, id ,newData);
    }
}
