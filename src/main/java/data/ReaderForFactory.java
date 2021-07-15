package data;

import model.description.DescriptionFactory;
import model.order.OrderFactory;
import time.DateConverter;

import java.sql.*;
import java.util.*;

public final class ReaderForFactory {
    private static String url = DataControl.getUrl();
    private static String user = DataControl.getUser();
    private static String password = DataControl.getPassword();

    private static String sqlTimeMarker;
    private static boolean needCheckData = false;


    public static List<OrderFactory> readDB(boolean isSelectOneDay, long time, String sortWay, String observe, String typeView) {
        List<DescriptionFactory> tempListDescription = new ArrayList<>();
        Map<Integer, OrderFactory> tempMapOrder = new TreeMap<>();
        List<OrderFactory> tempListOrders = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement st = conn.createStatement()) {
            Class.forName("org.postgresql.Driver");

            int bigNumber, position, amount, statusIndex, sizeA, sizeB, sizeC, typeIndex;
            String kod, idDoc, docNumber, client, manager, descrFirst, descrSecond, designer;
            Timestamp dateToFactory, dateToShipment;
            long statusTime;

            OrderFactory tempOrder;
            DescriptionFactory description;

            List<Integer> setCompare = Arrays.asList(0, 2, 7, 8, 11, 12, 13, 15, 20, 21, 22, 24);
            String sqlTypeView = getSqlTypeView(typeView);
            String sqlObserveView = getSqlObserveView(observe);
            String sqlSortWay = getSqlSortWay(sortWay);
            String sqlOther = "";

            if (needCheckData & isSelectOneDay) {
                long time2 = time + 24*3600*1000L;
                sqlOther = " AND " + sqlTimeMarker + " >= " + time + " AND " + sqlTimeMarker + " < " + time2 + " ";  //"time_8"
                sqlObserveView = "";
            }

            String sql0 = "SELECT * FROM order_view WHERE ";

            ResultSet rs = st.executeQuery(sql0 +sqlTypeView + sqlObserveView + sqlOther + sqlSortWay);

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
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return tempListOrders;
    }

    public static List<OrderFactory> readDBOnlyOne(String numberOrder) {
        List<DescriptionFactory> tempListDescription = new ArrayList<>();
        List<OrderFactory> tempListOrders = new ArrayList<>();
        OrderFactory tempOrder = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement st = conn.createStatement()) {
            Class.forName("org.postgresql.Driver");

            int bigNumber, position, amount, statusIndex, sizeA, sizeB, sizeC, typeIndex;
            String kod, idDoc, docNumber, client, manager, descrFirst, descrSecond, designer;
            Timestamp dateToFactory, dateToShipment;
            long statusTime;


            DescriptionFactory description;

            List<Integer> setCompare = Arrays.asList(0, 2, 7, 8, 11, 12, 13, 15, 20, 21, 22, 24);

            bigNumber = DateConverter.getYearShort(DateConverter.getNowDate()) * 100000 + Integer.valueOf(numberOrder);

            ResultSet rs = st.executeQuery("SELECT * FROM order_view WHERE big_number = " + bigNumber + ";");

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

                if (tempOrder == null) {
                    tempOrder = new OrderFactory(idDoc, bigNumber, docNumber, client, manager, dateToFactory, dateToShipment);
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

            tempOrder.setAllPosition(tempOrder.getDescriptions().size());
            tempListOrders.add(tempOrder);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return tempListOrders;
    }

    public static String getSqlSortWay(String sqlSortWay) {
        String result = "";
        if (sqlSortWay.equalsIgnoreCase("По номеру заказа")){
            result = "kod;";
        }
        else if (sqlSortWay.equalsIgnoreCase("По клиенту")){
            result = "client, kod;";
        }
        else if (sqlSortWay.equalsIgnoreCase("По дате поступления в КБ")){
            result = "time_2, kod;";
        }
        else if (sqlSortWay.equalsIgnoreCase("По дате запуска в цехе")){
            result = "time_7, kod;";
        }
        else if (sqlSortWay.equalsIgnoreCase("По дате 'Лазер'")){
            result = "time_8, kod;";
        }
        else if (sqlSortWay.equalsIgnoreCase("По дате 'Гибки'")){
            result = "time_11, kod;";
        }
        else if (sqlSortWay.equalsIgnoreCase("По дате 'Сварки'")){
            result = "time_12, kod;";
        }
        else if (sqlSortWay.equalsIgnoreCase("По дате 'Полировки'")){
            result = "time_13, kod;";
        }
        else if (sqlSortWay.equalsIgnoreCase("По дате 'Сборки'")){
            result = "time_15, kod;";
        }
        else if (sqlSortWay.equalsIgnoreCase("По дате 'ОТК'")){
            result = "time_20, kod;";
        }
        else if (sqlSortWay.equalsIgnoreCase("По дате Выпуска продукции")){
            result = "time_21, kod;";
        }
        else if (sqlSortWay.equalsIgnoreCase("По дате Отгрузки")){
            result = "time_22, kod;";
        }
        else if (sqlSortWay.equalsIgnoreCase("По дате Отгрузки из 1С")){
            result = "t_end, kod;";
        }
        return " ORDER BY " + result;
    }

    public static String getSqlTypeView(String typeView) {
        String result = "";
        if (typeView.equalsIgnoreCase("ЦЕХ, КБ, новый")) {
            result = "< 3 ";
        }
        else if (typeView.equalsIgnoreCase("ТЕХН")) {
            result = "= 3 ";
        }
        else if (typeView.equalsIgnoreCase("ЦЕХ")) {
            result = "= 2 ";
        }
        else if (typeView.equalsIgnoreCase("КБ")) {
            result = "= 1 ";
        }
        else if (typeView.equalsIgnoreCase("новый")) {
            result = "= 0 ";
        }
        else if (typeView.equalsIgnoreCase("Прочее")) {
            result = "= 5 ";
        }
        else {
            result = "< 6 "; //don't show 'ABC' (=4) and 'Other'(=5)
//            return " AND type_index < 4 "; //don't show 'ABC' (=4) and 'Other'(=5)
        }
        return " type_index " + result;
    }

    private static String getSqlObserveView (String observeView) {
        if (observeView.equalsIgnoreCase("Все открытые заказы")) {
            needCheckData = false;
            return " AND status_index <= 22 ";
        }
        else if (observeView.equalsIgnoreCase("Заказы в цехе")) {
            needCheckData = false;
            return " AND status_index <= 21 ";
        }
        else if (observeView.equalsIgnoreCase("Все заказы")) {
            needCheckData = false;
            return "";
        }
        else if (observeView.equalsIgnoreCase("в КБ")) {
            needCheckData = false;
            return " AND status_index >= 2 AND status_index < 7 ";
        }
        else if (observeView.equalsIgnoreCase("запуск в цехе")) {
            needCheckData = true;
            setSqlTimeMarker("time_7");
            return " AND status_index = 7 ";
        }
        else if (observeView.equalsIgnoreCase("на Лазере")) {
            needCheckData = true;
            setSqlTimeMarker("time_8");
            return " AND status_index = 8 ";
        }
        else if (observeView.equalsIgnoreCase("на Гибке")) {
            needCheckData = true;
            setSqlTimeMarker("time_11");
            return " AND status_index = 11 ";
        }
        else if (observeView.equalsIgnoreCase("на Сварке")) {
            needCheckData = true;
            setSqlTimeMarker("time_12");
            return " AND status_index = 12 ";
        }
        else if (observeView.equalsIgnoreCase("на Полировке")) {
            needCheckData = true;
            setSqlTimeMarker("time_13");
            return " AND status_index = 13 ";
        }
        else if (observeView.equalsIgnoreCase("на Сборке")) {
            needCheckData = true;
            setSqlTimeMarker("time_15");
            return " AND status_index = 15 ";
        }
        else if (observeView.equalsIgnoreCase("ожидающие ОТК")) {
            needCheckData = true;
            setSqlTimeMarker("time_20");
            return " AND status_index = 20 ";
        }
        else if (observeView.equalsIgnoreCase("на СКЛАДЕ")) {
            needCheckData = true;
            setSqlTimeMarker("time_21");
            return " AND status_index = 21 ";
        }
        else if (observeView.equalsIgnoreCase("Оплаченные")) {
            needCheckData = true;
            setSqlTimeMarker("time_22");
            return " AND status_index = 22 ";
        }
        else if (observeView.equalsIgnoreCase("Закрытые")) {
            needCheckData = false;
            return " AND status_index >= 23 ";
        }
        return "";
    }


/*    private static String setSqlTimeMarker (String dateFactor) {
        if (dateFactor.equalsIgnoreCase("дата 'в производство'")) {
            return "t_factory";
        }
        else if (dateFactor.equalsIgnoreCase("дата отгрузки (из 1С)")) {
            return "t_end";
        }
        else if (dateFactor.equalsIgnoreCase("дата отгрузки")) {
            return "t_invoice"; //need do type of data is timestamp
        }
        else *//*if (sortWay.equalsIgnoreCase("дата заказа"))*//* {
            return "t_create";
        }
    }*/
    public static String getSqlTimeMarker() {
        return sqlTimeMarker;
    }

    public static void setSqlTimeMarker(String sqlTimeMarker) {
        ReaderForFactory.sqlTimeMarker = sqlTimeMarker;
    }
}

