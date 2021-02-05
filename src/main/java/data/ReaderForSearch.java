package data;

import model.description.DescriptionFactory;
import model.description.DescriptionOrder;
import model.order.Order;
import model.order.OrderFactory;
import name.TypeName;

import java.sql.*;
import java.util.*;

public final class ReaderForSearch {
    private static String url = DataControl.getURL();
    private static String user = DataControl.getUser();
    private static String password = DataControl.getPASSWORD();


    public static Order readDB(String sql) {
        Order tempOrder = null;
        List<DescriptionOrder> tempListDescription = new ArrayList<>();
//        Map<Integer, OrderFactory> tempMapOrder = new TreeMap<>();
//        List<OrderFactory> tempListOrders = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement st = conn.createStatement()) {
            Class.forName("org.postgresql.Driver");

            int bigNumber, position, amount, statusIndex, sizeA, sizeB, sizeC, typeIndex, durationTime;
            String idDoc, kod, docNumber, docNumberManufacture, docNumberInvoice, client, manager, descrFirst, descrSecond, designer;
            Timestamp dateCreate, dateToFactory, dateToShipment, dateManuf, dateInvoice;
            long statusTime;

            DescriptionOrder description;

            List<Integer> setCompare = Arrays.asList(0, 2, 3, 7, 8, 11, 12, 13, 15, 20, 21, 22, 24);

            String sql0 = "SELECT * FROM order_view WHERE big_number = " + sql + ";";
            ResultSet rs = st.executeQuery(sql0);

            while (rs.next()) {
                kod = rs.getString("kod");
                idDoc = rs.getString("iddoc");
                bigNumber = rs.getInt("big_number");
                docNumber = rs.getString("docno");
                docNumberManufacture = rs.getString("docno2");
                docNumberInvoice = rs.getString("docno3");
                dateCreate = rs.getTimestamp("t_create");
                dateToFactory = rs.getTimestamp("t_factory");
                dateToShipment = rs.getTimestamp("t_end");
//                dateManuf = rs.getTimestamp("t_manuf");
//                dateInvoice = rs.getTimestamp("t_invoice");
                position = rs.getInt("pos");
                amount = rs.getInt("amount");
                descrFirst = rs.getString("descr_first");
                descrSecond = rs.getString("descr_second");
                designer = rs.getString("designer");
                manager = rs.getString("manager");
                client = rs.getString("client");
                statusIndex = rs.getInt("status_index");
                durationTime = rs.getInt("duration");

                statusTime = rs.getLong("time_" + statusIndex);
                typeIndex = rs.getInt("type_index");
                sizeA = rs.getInt("size_a");
                sizeB = rs.getInt("size_b");
                sizeC = rs.getInt("size_c");

                description = new DescriptionOrder(kod, position, amount, descrFirst, descrSecond,
                        designer, statusIndex, statusTime, TypeName.NAME[typeIndex], sizeA, sizeB, sizeC);



                for (int i = 0; i < 25; i++) {
                    if (setCompare.contains(i)) {
                        if ((Long) rs.getLong("time_" + i) != null) {
                            description.getStatusTimeList()[i] = rs.getLong("time_" + i);
                        }
                    }
                }

                tempListDescription.add(description);

                if (tempOrder == null) {
                    tempOrder = new Order(idDoc, bigNumber, durationTime, docNumber, client, manager, dateCreate, dateToFactory, dateToShipment, docNumberManufacture, docNumberInvoice);
                    tempOrder.setDesigner(designer);
                }

                if (tempOrder.getMinStatusIndex() > description.getStatusIndex()) {
                    tempOrder.setMinStatusIndex(description.getStatusIndex());
                }

                tempOrder.getDescriptions().add(description);
                tempOrder.setAllPosition(tempOrder.getDescriptions().size());


                // set Designer to all Order
                if (description.getDesigner() != null) {
                    tempOrder.setDesigner(description.getDesigner());

                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return tempOrder;
    }
}
