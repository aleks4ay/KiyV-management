package data;

import data.DataControl;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import model.description.DescriptionOrder;
import model.order.Order;
import name.TypeName;
import time.DateConverter;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public final class ReaderForManager {
    private static String url = DataControl.getUrl();
    private static String user = DataControl.getUser();
    private static String password = DataControl.getPassword();

    public static List<Order> readOrderForManager(long timeStart, long timeEnd,
                  String managerFilter, String designerFilter, String sortWay, String sqlType, String observeOrder, String dateFactor) {
        List<Order> tempListOrders = new ArrayList<>();


        try (Connection conn = DriverManager.getConnection(url, user, password);
        Statement st = conn.createStatement()) {
            Class.forName("org.postgresql.Driver");

            int bigNumber, durationTime, position, amount, statusIndex, sizeA, sizeB, sizeC;
            String kod, idDoc, docNumber, docNumber2, docNumber3, client, manager, descrFirst, descrSecond, designer, type;
            Timestamp dateCreate, dateToFactory, dateToShipment;
            long statusTime;

            Order tempOrder = null;


            String sql_manager = " AND manager = '" + managerFilter + "'";
            String sql_design = " AND designer = '" + designerFilter + "'";
            String timeMarker = setSqlTimeMarker(dateFactor);
//            String timeFactor = setSqlTimeMarker(dateFactor);
            String sql_type = setSqlType(sqlType);
            String sql_observ = setObserveOrder(observeOrder);
            String sql_sortyng = setSqlSorting(sortWay);
            String sql1 = "SELECT * FROM order_view WHERE " + timeMarker + " >= ? AND " + timeMarker + " <= ?";

            if (managerFilter.equals("Выбрать всех")) {
                sql_manager = "";
            }
            if (designerFilter.equals("Выбрать всех")) {
                sql_design = "";
            }

            PreparedStatement ps = conn.prepareStatement(sql1 + sql_manager + sql_design + sql_type + sql_observ + sql_sortyng);

            ps.setTimestamp(1, new Timestamp(timeStart));
            ps.setTimestamp(2, new Timestamp(timeEnd));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                kod = rs.getString("kod");
                idDoc = rs.getString("iddoc");
                bigNumber = rs.getInt("big_number");
                durationTime = rs.getInt("duration");
                docNumber = rs.getString("docno");
                docNumber2 = rs.getString("docno2");
                docNumber3 = rs.getString("docno3");
                dateCreate = rs.getTimestamp("t_create");
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
                type = TypeName.NAME[rs.getInt("type_index")];
                sizeA = rs.getInt("size_a");
                sizeB = rs.getInt("size_b");
                sizeC = rs.getInt("size_c");

                DescriptionOrder descriptionOrder = new DescriptionOrder(kod, position, amount, descrFirst, descrSecond,
                        designer, statusIndex, statusTime, type, sizeA, sizeB, sizeC);

                for (int i = 21; i <= 22; i++) {
                    if ((Long) rs.getLong("time_" + i) != null) {
                        descriptionOrder.getStatusTimeList()[i] = rs.getLong("time_" + i);
                    }
                }

//                listDescription.add(descriptionOrder);

                if (tempListOrders.isEmpty()) {
                    tempOrder = new Order(idDoc, bigNumber, durationTime, docNumber, client, manager,
                            dateCreate, dateToFactory, dateToShipment, docNumber2, docNumber3);
                    tempListOrders.add(tempOrder);
                }
                else if (tempListOrders.get(tempListOrders.size() - 1).getBigNumber() != bigNumber) {
                    tempOrder = new Order(idDoc, bigNumber, durationTime, docNumber, client, manager,
                            dateCreate, dateToFactory, dateToShipment, docNumber2, docNumber3);
                    tempListOrders.add(tempOrder);
                }

                if (tempOrder.getMinStatusIndex() > descriptionOrder.getStatus().getStatusIndex()) {
                    tempOrder.setMinStatusIndex(descriptionOrder.getStatus().getStatusIndex());
                }

                // set Designer to all Order
                if (descriptionOrder.getDesigner() != null) {
                    tempOrder.setDesigner(descriptionOrder.getDesigner());
                }

                tempOrder.getDescriptions().add(descriptionOrder);
            }
//            tempListOrders.addAll(mapOrder.values());
            for (Order o : tempListOrders) {
                o.setAllPosition(o.getDescriptions().size());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return tempListOrders;
    }

    private static String setSqlType (String type) {
        if (type.equalsIgnoreCase("ЦЕХ, КБ, новый")) {
            return " AND type_index < 3 ";
        }
        else if (type.equalsIgnoreCase("ТЕХН")) {
            return " AND type_index = 3 ";
        }
        else if (type.equalsIgnoreCase("ЦЕХ")) {
            return " AND type_index = 2 ";
        }
        else if (type.equalsIgnoreCase("КБ")) {
            return " AND type_index = 1 ";
        }
        else if (type.equalsIgnoreCase("новый")) {
            return " AND type_index = 0 ";
        }
        else if (type.equalsIgnoreCase("Прочее")) {
            return " AND type_index = 5 ";
        }
        else {
            return " AND type_index < 6 "; //don't show 'ABC' (=4) and 'Other'(=5)
//            return " AND type_index < 4 "; //don't show 'ABC' (=4) and 'Other'(=5)
        }
    }

    private static String setSqlTimeMarker (String dateFactor/*, String timeMarker*/) {
        if (dateFactor.equalsIgnoreCase("дата 'в производство'")) {
            return "t_factory";
        }
        else if (dateFactor.equalsIgnoreCase("дата отгрузки (из 1С)")) {
            return "t_end";
        }
        else if (dateFactor.equalsIgnoreCase("дата отгрузки")) {
            return "t_invoice"; //need do type of data is timestamp
        }
        else /*if (sortWay.equalsIgnoreCase("дата заказа"))*/ {
            return "t_create";
        }
    }

    private static String setSqlSorting(String sortWay) {
//        String sql_sortyng = " ORDER BY ";
        if (sortWay.equalsIgnoreCase("По номеру заказа")) {
            return " ORDER BY big_number, kod;";
        }
        else if (sortWay.equalsIgnoreCase("По дате заказа")) {
            return " ORDER BY t_create, kod;";
        }
        else if (sortWay.equalsIgnoreCase("По дате 'в производство'")) {
            return " ORDER BY t_factory, kod;";
        }
        else if (sortWay.equalsIgnoreCase("По дате отгрузки (из 1С)")) {
            return " ORDER BY t_end, kod;";
        }
        else if (sortWay.equalsIgnoreCase("По дате отгрузки фактической")) {
            return " ORDER BY t_invoice, kod;";
        }
        else if (sortWay.equalsIgnoreCase("По клиенту")) {
            return " ORDER BY client, kod;";
        }
        else if (sortWay.equalsIgnoreCase("По менеджеру")) {
            return " ORDER BY manager, kod;";
        }
        else if (sortWay.equalsIgnoreCase("По конструктору")) {
            return " ORDER BY designer, kod;";
        }
        else if (sortWay.equalsIgnoreCase("По типу")) {
            return " ORDER BY type_index, kod;";
        }
        else return " ORDER BY kod;";
    }

    private static String setObserveOrder (String observeOrder) {
        if (observeOrder.equalsIgnoreCase("Все заказы (кроме закрытых)")) {
            return " AND status_index < 22 ";
        }
        else if (observeOrder.equalsIgnoreCase("Заказы в процессе производства")) {
            return " AND status_index < 21 ";
        }
        else if (observeOrder.equalsIgnoreCase("Заказы на СКЛАДЕ")) {
            return " AND status_index = 21 ";
        }
        else if (observeOrder.equalsIgnoreCase("Оплаченные")) {
            return " AND status_index = 22 ";
        }
        else if (observeOrder.equalsIgnoreCase("Закрытые")) {
            return " AND status_index >= 23 ";
        }


            return "";
    }

    public static void setDatePeriod(String dateWay, DatePicker start, DatePicker end) {
        LocalDate date2 = DateConverter.getNowLocalDate();
        LocalDate date1 = null;

        if (dateWay.equalsIgnoreCase("За текущий год")) {
            int dayOfYears = date2.getDayOfYear();
            date1 = date2.minusDays(dayOfYears - 1);
        } else if (dateWay.equalsIgnoreCase("За последние 30 дней")) {
            date1 = date2.minusDays(29);
        } else if (dateWay.equalsIgnoreCase("За текущий месяц")) {
            int dayOfMonth = date2.getDayOfMonth();
            date1 = date2.minusDays(dayOfMonth - 1);
        } else if (dateWay.equalsIgnoreCase("За неделю")) {
            date1 = date2.minusDays(6);
        } else if (dateWay.equalsIgnoreCase("За день")) {
            date1 = date2;
        }
        start.setValue(date1);
        end.setValue(date2);
    }

    public static int readCountOrders(long timeStart, long timeEnd, String flag, String sortWay) {
        String timeMarker = setSqlTimeMarker(sortWay);
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement st = conn.createStatement()) {
            Class.forName("org.postgresql.Driver");

            String sql = "";
            if (flag.equalsIgnoreCase("closed")) {
                sql = "SELECT COUNT(big_number) FROM order_view WHERE " + timeMarker + " >= ? AND " + timeMarker + " <= ? AND pos = 1 AND status_index > 21;" ;
            }
            else {
                sql = "SELECT COUNT(big_number) FROM order_view WHERE " + timeMarker + " >= ? AND " + timeMarker + " <= ? AND pos = 1;" ;
            }
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setTimestamp(1, new Timestamp(timeStart));
            ps.setTimestamp(2, new Timestamp(timeEnd));

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
        return 0;
    }
}

