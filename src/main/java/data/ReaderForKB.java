package data;

import javafx.scene.control.Label;
import model.description.DescriptionFactory;
import model.order.OrderFactory;

import java.sql.*;
import java.util.*;

public final class ReaderForKB {
    private static String url = DataControl.getURL();
    private static String user = DataControl.getUser();
    private static String password = DataControl.getPASSWORD();


    public static List<OrderFactory> readDB(String sql2, String sortWayKb, Label info_kb_2, Label info_kb_3) {
        List<DescriptionFactory> tempListDescription = new ArrayList<>();
        Map<Integer, OrderFactory> tempMapOrder = new TreeMap<>();
        List<OrderFactory> tempListOrders = new ArrayList<>();
        String sortWay;
        int summOrderBeginning = 0;
        int summPosition = 0;
        if (sortWayKb.equalsIgnoreCase("по № заказа")) {
            sortWay = " ORDER BY kod;";
        }
        else if (sortWayKb.equalsIgnoreCase("по дате поступления в КБ")) {
            sortWay = " ORDER BY time_2, kod;";
        }
        else {
            sortWay = "";
        }

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement st = conn.createStatement()) {
            Class.forName("org.postgresql.Driver");

            int bigNumber, position, amount, statusIndex, sizeA, sizeB, sizeC, typeIndex;
            String kod, idDoc, docNumber, client, manager, descrFirst, descrSecond, designer;
            Timestamp dateToFactory, dateToShipment;
            long statusTime;

            OrderFactory tempOrder;
            DescriptionFactory description;

            List<Integer> setCompare = Arrays.asList(0, 2, 3, 4, 5, 6, 7);

            String sql0 = "SELECT * FROM order_view WHERE type_index = 1 AND " + sql2 + sortWay;

            ResultSet rs = st.executeQuery(sql0);

            while (rs.next()) {
                kod = rs.getString("kod");
                idDoc = rs.getString("iddoc");
                bigNumber = rs.getInt("big_number");
                docNumber = rs.getString("docno");
                dateToFactory = rs.getTimestamp("t_factory");
                dateToShipment = rs.getTimestamp("t_end");
                position = rs.getInt("pos");
                amount = rs.getInt("amount");
                descrFirst = rs.getString("descr_first");
                descrSecond = rs.getString("descr_second");
                designer = rs.getString("designer");
                manager = rs.getString("manager");
                client = rs.getString("client");
                statusIndex = rs.getInt("status_index");

                statusTime = rs.getLong("time_" + statusIndex);
                typeIndex = rs.getInt("type_index");
                sizeA = rs.getInt("size_a");
                sizeB = rs.getInt("size_b");
                sizeC = rs.getInt("size_c");

                description = new DescriptionFactory(kod, bigNumber, position, amount, descrFirst, descrSecond,
                        designer, statusIndex, statusTime, typeIndex, sizeA, sizeB, sizeC);



                for (int i = 0; i < 25; i++) {
                    if (setCompare.contains(i)) {
                        if ((Long) rs.getLong("time_" + i) != null) {
                            description.getStatusTimeList()[i] = rs.getLong("time_" + i);
                        }
                    }
                }

                tempListDescription.add(description);

                if ((tempOrder = tempMapOrder.get(bigNumber)) == null) {
                    tempOrder = new OrderFactory(idDoc, bigNumber, docNumber, client, manager, dateToFactory, dateToShipment);
                    tempMapOrder.put(bigNumber, tempOrder);
                    tempListOrders.add(tempOrder);
                }

                if (tempOrder.getMinStatusIndex() > description.getStatusIndex()) {
                    tempOrder.setMinStatusIndex(description.getStatusIndex());
                }

                tempOrder.getDescriptions().add(description);

                // set Designer to all Order
                if (description.getDesigner() != null) {
                    tempOrder.setDesigner(description.getDesigner());

                }
            }

            for (OrderFactory o : tempListOrders) {
                o.setAllPosition(o.getDescriptions().size());
                summPosition += o.getDescriptions().size();
                if (o.getDesigner() != null) {
                    summOrderBeginning ++;
                }
            }
            info_kb_2.setText(String.valueOf(summOrderBeginning));
            info_kb_3.setText(String.valueOf(summPosition));

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return tempListOrders;
    }
}
