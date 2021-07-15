package data;

import data.DataControl;
import model.description.DescriptionOrder;
import model.description.DescriptionParsing;
import model.order.OrderParsing;
import name.TypeName;

import java.sql.*;
import java.util.*;

public final class ReaderForParsing {
    private static String url = DataControl.getUrl();
    private static String user = DataControl.getUser();
    private static String password = DataControl.getPassword();

    public static List<OrderParsing> readOrderForParsing() {
        List<OrderParsing> tempListOrders = new ArrayList<>();
        List<DescriptionParsing> listDescription = new ArrayList<>();
        Map<Integer, OrderParsing> mapOrder = new TreeMap<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
                    Statement st = conn.createStatement()) {
            Class.forName("org.postgresql.Driver");

            int bigNumber, statusIndex, position, amount, isParsing;
            String kod, idDoc, docNumber, client, manager, descr, type, descrSize;
            long dateToParsing;
//            Timestamp dateToFactory;

            OrderParsing tempOrder;

//            Set<Integer> bigNumbers = new TreeSet<>();
            int firstItem = Integer.MAX_VALUE;
            int newValue = 0;
//            ResultSet rsTemp = st.executeQuery("SELECT big_number FROM order_view WHERE type_index =0;");
            ResultSet rsTemp = st.executeQuery("SELECT big_number FROM order_view WHERE is_parsing = 0;");
            while (rsTemp.next()) {
                newValue = rsTemp.getInt("big_number");
                if (newValue < firstItem) {
                    firstItem = newValue;
                }
                if (firstItem < 1901000) {
                    firstItem = 1901000;
                }
//                System.out.println("new min bigNumber = " + firstItem);
            }

            String sql1 = "SELECT kod, iddoc, big_number, docno, client, manager, pos, descr_first, descr_second, size_a, size_b, size_c," +
                    " time_1, amount, type_index, status_index, is_parsing FROM order_view WHERE is_parsing = 0 AND big_number >= ";
//                    " t_factory, amount, type_index, status_index, is_parsing FROM order_view WHERE is_parsing = 0 AND big_number >= ";


            ResultSet rs = st.executeQuery(sql1 + firstItem + ";");

            while (rs.next()) {
                kod = rs.getString("kod");
                idDoc = rs.getString("iddoc");
                bigNumber = rs.getInt("big_number");
                docNumber = rs.getString("docno");
                client = rs.getString("client");
                manager = rs.getString("manager");
                position = rs.getInt("pos");
                descr = rs.getString("descr_first") + " " + rs.getString("descr_second");
                descrSize = rs.getInt("size_a") + "×" + rs.getInt("size_b") + "×" + rs.getInt("size_c");
//                dateToFactory = rs.getTimestamp("t_factory");
                dateToParsing = rs.getLong("time_1");
                amount = rs.getInt("amount");
                type = TypeName.NAME[rs.getInt("type_index")];
                statusIndex = rs.getInt("status_index");
                isParsing = rs.getInt("is_parsing");

                DescriptionParsing descriptionParsing = new DescriptionParsing(kod, position, descr, descrSize, amount,
                        type, statusIndex, isParsing);

                listDescription.add(descriptionParsing);

                if ((tempOrder = mapOrder.get(bigNumber)) == null) {
                    tempOrder = new OrderParsing(idDoc, bigNumber, docNumber, client, manager, " ", dateToParsing);
                    mapOrder.put(bigNumber, tempOrder);
                }
                if (descriptionParsing.getPosition() == 1) {
                    tempOrder.setDescriptionFirstPosition(descriptionParsing.getDescr());
                }

                if (tempOrder.getMinStatusIndex() > descriptionParsing.getStatusIndex()) {
                    tempOrder.setMinStatusIndex(descriptionParsing.getStatusIndex());
                }
                tempOrder.addDescription(descriptionParsing);
            }

            for (OrderParsing o : mapOrder.values()) {
                tempListOrders.add(o);
/*
                if (o.getMinStatusIndex() < 2 ) {
                    tempListOrders.add(o);
                }
*/
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return tempListOrders;
    }

}
