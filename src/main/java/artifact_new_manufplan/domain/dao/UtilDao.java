package artifact_new_manufplan.domain.dao;

import artifact_new_manufplan.log.ClassNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class UtilDao {
    private static final Logger log = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    private static String driverPostgres = null;
    private static String urlPostgres = null;
    private static String user = null;
    private static String password = null;

    static {
        //load DB properties
        try (InputStream in = UtilDao.class.getClassLoader().getResourceAsStream("persistence.properties")){
            Properties properties = new Properties();
            properties.load(in);

            driverPostgres = properties.getProperty("database.driverClassName");
            urlPostgres = properties.getProperty("database.url");
            user = properties.getProperty("database.username");
            password = properties.getProperty("database.password");
            log.debug("Loaded properties as Stream: dbf.driverClassName = {}, dbf.url = {}, database.driverClassName = {}, " +
                            "database.url = {}, database.username = {})",
                     driverPostgres, urlPostgres, user);

        } catch (IOException e) {
            log.warn("Exception during Loaded properties from file {}.", new File("/persistence.properties").getPath(), e);
        }
    }

    public Connection getConnPostgres() {
        try {
            Class.forName(driverPostgres);
            Connection connPostgres = DriverManager.getConnection(urlPostgres, user, password);
            connPostgres.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connPostgres.setAutoCommit(false);
            log.debug("Created connection for 'postgres'. Url= {}, user= {}.", urlPostgres, user);
            return connPostgres;
        } catch (SQLException | ClassNotFoundException e) {
            log.warn("Exception during create connection for 'postgres' url= {}, user= {}.", urlPostgres, user, e);
        }
        return null;
    }

    public void closeConnection(Connection conn) {
        try {
            log.debug("Closing connection {}.", conn);
            conn.close();
        } catch (SQLException e) {
            log.warn("Exception during Closing connection {}.", conn, e);
        }
    }
}
