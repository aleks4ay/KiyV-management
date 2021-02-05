package artifact_new_manufplan.domain.services;

import artifact_new_manufplan.domain.model.Invoice;
import artifact_new_manufplan.log.ClassNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import artifact_new_manufplan.domain.dao.InvoiceDao;
import artifact_new_manufplan.domain.dao.UtilDao;

import java.sql.Connection;
import java.util.*;
import java.util.stream.Collectors;

public class InvoiceReader {

    private static final Logger log = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public static void main(String[] args) {
//        long start = System.currentTimeMillis();
//        log.info("Start writing 'I N V O I C E'.");
//
//        List<Invoice> invoices = new InvoiceReader().getAll();
//        invoices.forEach(System.out::println);
//
//        long end = System.currentTimeMillis();
//        log.info("End writing 'I N V O I C E'. Time = {} c. ", (double)(end-start)/1000);

        Map<String, List<Invoice>> invoiceMap = new InvoiceReader().getAllAsMapOfList();
        int i=0;
        for (List<Invoice> invoiceList : invoiceMap.values()) {
            i++;
            for (Invoice inv : invoiceList) {
                System.out.println(inv);
            }
            System.out.println("*****************");
        }
        System.out.println(invoiceMap.size());
    }


    public List<Invoice> getAll() {

        UtilDao utilDao = new UtilDao();
        Connection connPostgres = utilDao.getConnPostgres();

        InvoiceDao invoiceDao = new InvoiceDao(connPostgres);

        List<Invoice> invoiceList = invoiceDao.getAll();

        utilDao.closeConnection(connPostgres);
        log.info("Was read {} Invoices.", invoiceList.size());

        return invoiceList;
    }

    public Map<String, Invoice> getAllAsMap() {
        List<Invoice> result = getAll();
        return result
                .stream()
                .collect(Collectors.toMap(Invoice::getId, Invoice::getInvoice));
    }

    public Map<String, List<Invoice>> getAllAsMapOfList() {
        Map<String, List<Invoice>> result = new HashMap<>();
        List<Invoice> invoices = getAll();
        for (Invoice invoice : invoices) {
            String idOrder = invoice.getIdOrder();
            if (result.get(idOrder) == null) {
                List<Invoice> newInvoiceList = new ArrayList<>();
                newInvoiceList.add(invoice);
                result.put(idOrder, newInvoiceList);
            } else {
                List<Invoice> oldInvoiceList = result.get(idOrder);
                oldInvoiceList.add(invoice);
            }
        }
        return result;
    }
}
