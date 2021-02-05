package gui;

import data.DataControl;
import data.WriterPostgreSql;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import model.description.DescriptionFactory;
import model.description.DescriptionParsing;
import model.order.OrderFactory;
import time.DateConverter;

import java.util.List;

public final class Setter {

    public static boolean setStatus (List<TableRowFactory> list, ListView<HBox> listview_two) throws InterruptedException { //listFactory
        long statusTime = DateConverter.getNowDate();
//        String role="";
        int countRecord = 0;
        for (TableRowFactory tr : list) {
            OrderFactory or = tr.getOrder();
            for (DescriptionFactory descr : or.getDescriptions()) {
                CheckBox[] ch = descr.getCheckBoxesStatus();
                for (int statusIndex = 0; statusIndex < 25; statusIndex ++) {

                    if (ch[statusIndex] == null) {
                        continue;
                    }
                    else if (ch[statusIndex].isSelected() &  ! ch[statusIndex].isDisable() ) {
                        if (descr.getStatusIndex()<7 & statusIndex>7) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("INFO");
                            alert.setHeaderText("Заказ еще не распределен\nили находится в разработке.");
                            alert.showAndWait();
                            listview_two.getSelectionModel().select(tr.gethBox());
                            return false;
                        }
                        else if ( (descr.getStatusIndex() > statusIndex) & (statusIndex != 20 )) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("INFO");
                            alert.setHeaderText("Заказ находится на более\nпоздней стадии изготовления.");
                            alert.showAndWait();
                            listview_two.getSelectionModel().select(tr.gethBox());
                            return false;
                        }
                        else if ( (descr.getStatusIndex() < statusIndex) | (statusIndex == 20)) {
                            descr.setStatusIndex(statusIndex);
                            descr.setStatusTime(statusTime);
                            WriterPostgreSql.writeStatus(descr.getKod(), descr.getStatusIndex(), descr.getStatusTime());
                            countRecord ++;
                        }
                    }
                }
            }
        }
        if (countRecord > 0) {
            DataControl.writeTimeChange("ceh");
        }
        return true;
    }

    public static boolean setStatusKB (List<TableRowKB> list, ListView<HBox> listview_kb, boolean mosienko) throws InterruptedException {
        long statusTime = DateConverter.getNowDate();
        int countRecord = 0;
        for (TableRowKB tr : list) {
            OrderFactory or = tr.getOrder();
            for (DescriptionFactory descr : or.getDescriptions()) {
                CheckBox[] ch = descr.getCheckBoxesStatus();
                for (int statusIndex = 0; statusIndex < 25; statusIndex ++) {

                    if (ch[statusIndex] == null) {
                        continue;
                    }
                    else if (ch[statusIndex].isSelected() &  ! ch[statusIndex].isDisable()) {
                        if (descr.getStatusIndex() > statusIndex) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("INFO");
                            alert.setHeaderText("Заказ находится на более\nпоздней стадии разработки.");
                            alert.showAndWait();
                            listview_kb.getSelectionModel().select(tr.gethBox());
                            return false;
                        }
                        else if (descr.getStatusIndex() < statusIndex) {
                            descr.setStatusIndex(statusIndex);
                            descr.setStatusTime(statusTime);
                            if (statusIndex == 3) {
                                if (mosienko) {
                                    descr.setDesigner("Мосиенко В.");
                                }
                                else {
                                    if (System.getenv().containsKey("USERNAME")) {
                                        descr.setDesigner(System.getenv().get("USERNAME"));
                                    }
                                    else {
                                        descr.setDesigner(System.getProperty("user.name"));
                                    }
                                }
                                WriterPostgreSql.writeDesigner(descr.getKod(), descr.getStatusIndex(), descr.getStatusTime(), descr.getDesigner());
                                countRecord ++;
                            }
                            else {
                                WriterPostgreSql.writeStatus(descr.getKod(), descr.getStatusIndex(), descr.getStatusTime());
                                countRecord ++;
                            }
                        }
                    }
                }
            }
        }
        if (countRecord > 0) {
            DataControl.writeTimeChange("KB ");
        }
        return true;
    }

    public static boolean setType (List<DescriptionParsing> listParsing) {
        long statusTime = DateConverter.getNowDate();
        int countRecord = 0;
        for (DescriptionParsing descr : listParsing) {
            String kod = descr.getId();
            if (descr.getButtonKB().isSelected()) {
                WriterPostgreSql.writeType(kod, 2, statusTime, 1);
                countRecord ++;
            }
            else if (descr.getButtonFactory().isSelected()) {
                WriterPostgreSql.writeType(kod, 7, statusTime, 2);
                countRecord ++;
            }
            else if (descr.getButtonTeh().isSelected()) {
                WriterPostgreSql.writeType(kod, 7, statusTime, 3);
                countRecord ++;
            }
            else if (descr.getButtonOther().isSelected()) {
                WriterPostgreSql.writeType(kod, 24, statusTime, 5);
                countRecord ++;
            }

        }
        if (countRecord > 0) {
            DataControl.writeTimeChange("par");
        }
        return true;
    }
}
