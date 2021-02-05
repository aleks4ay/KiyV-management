package artifact_new_manufplan.domain.dao;

import artifact_new_manufplan.domain.model.Invoice;
import artifact_new_manufplan.log.ClassNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDao {

    private Connection connPostgres;
    private static final Logger log = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());
    private static final String SQL_GET_ALL = "SELECT d.id, d.id_invoice, i.docno, i.id_order, d.id_tmc, " +
        "i.time_invoice, d.quantity, d.payment FROM invoice i, invoice_descr d WHERE i.iddoc = d.id_invoice;";

    public InvoiceDao(Connection conn) {
        this.connPostgres = conn;
        log.debug("Get connection to PostgreSQL from {}.", UtilDao.class);
    }


    public List<Invoice> getAll() {
        List<Invoice> result = new ArrayList<>();
        try {
            Statement statement = connPostgres.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GET_ALL);
            log.debug("Select all 'Invoice'. SQL = {}.", SQL_GET_ALL);

            while (rs.next()) {
                String id = rs.getString("id");
                String idInvoice = rs.getString("id_invoice");
                String docNomberInvoice = rs.getString("docno");
                String idOrder = rs.getString("id_order");
                String idTmc = rs.getString("id_tmc");
                Timestamp timeInvoice = rs.getTimestamp("time_invoice");
                int quantity = rs.getInt("quantity");
                double payment = rs.getDouble("payment");

                Invoice invoice = new Invoice(id, idInvoice, idOrder, idTmc, docNomberInvoice, timeInvoice, quantity, payment);
                result.add(invoice);
            }
            log.debug("Was read {} Invoice from Postgres.", result.size());
            return result;
        } catch (SQLException e) {
            log.warn("Exception during reading all 'Invoice'.", e);
        }
        log.debug("Invoice not found.");
        return null;
    }
}
