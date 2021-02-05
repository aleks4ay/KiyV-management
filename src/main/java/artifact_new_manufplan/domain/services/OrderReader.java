package artifact_new_manufplan.domain.services;

import artifact_new_manufplan.domain.dao.OrderDao;
import artifact_new_manufplan.log.ClassNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import artifact_new_manufplan.domain.dao.UtilDao;
import artifact_new_manufplan.domain.model.Order;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderReader {

    private static final Logger log = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        log.info("Start writing 'O R D E R'.");

        List<Order> descriptions = new OrderReader().getAll();
        descriptions.forEach(System.out::println);

        long end = System.currentTimeMillis();
        log.info("End writing 'O R D E R'. Time = {} c. ", (double)(end-start)/1000);
    }


    public List<Order> getAll() {

        UtilDao utilDao = new UtilDao();
        Connection connPostgres = utilDao.getConnPostgres();

        OrderDao orderDao = new OrderDao(connPostgres);

        List<Order> orderList = orderDao.getAll();

        utilDao.closeConnection(connPostgres);
        log.info("Was read {} Orders.", orderList.size());

        return orderList;
    }

    public Map<String, Order> getAllAsMap() {
        List<Order> result = getAll();
        return result
                .stream()
                .collect(Collectors.toMap(Order::getIdOrder, Order::getOrder));
    }

}
