package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.description.DescriptionOrder;
import model.order.Order;
import name.StatusName;
import time.DateConverter;

import java.util.List;


public class TableRow {

    private boolean needSetMinHeight;
    private Order order = null;
    private HBox hBox = new HBox(0);;
    private int index = 0;
    private String designer = "";

    TableRow(Order order, int index) {
        this.index = index;
        this.order = order;
        createHBox(order);
    }


    private void createHBox(Order order) {
        hBox.setSpacing(0);
        hBox.setPadding(new Insets(0, 0, 0, 0));
        hBox.setId("row_body");

        String t1 = DateConverter.dateWithYearToString (order.getDateCreate().getTime());
        String t2 = DateConverter.dateWithYearToString (order.getDateToFactory().getTime());
        String t6 = DateConverter.dateWithYearToString (order.getDescriptions().get(0).getStatusTimeList()[21]);

        String t7 = DateConverter.dateWithYearToString (order.getDateToShipment().getTime());
        String t8 = DateConverter.dateWithYearToString (order.getDescriptions().get(0).getStatusTimeList()[22]);

        if (order.getDocNumberManufacture() != null) {
            t6 += "\n" + order.getDocNumberManufacture();
        }
        if (order.getDocNumberInvoice() != null) {
            t8 += "\n" + order.getDocNumberInvoice();
        }

        Label l1 = createLabel(order.getStringNumber(), 65.0, 16);
        Label l2 = createLabel(t1, 65.0, 14);
        Label l3 = createLabel(order.getClient(), 100.0, 12);
        l3.setWrapText(true);
        Label l4 = createLabel(t2, 65.0, 14);

        Label l6 = createLabel(t6, 80.0, 14);
        Label l7 = createLabel(t7, 80.0, 14);
        l7.setStyle("-fx-text-fill: grey");
        Label l8 = createLabel(t8, 80.0, 14);
        Label l9 = createLabel(order.getDesigner(), 100.0, 14);
        Label l10 = createLabel(order.getManager(), 109.0, 14);

        VBox vBox5 = getAllDescription(order.getDescriptions());
//        l3.setPrefHeight(vBox5.getPrefHeight());
        hBox.getChildren().clear();
        hBox.getChildren().addAll(l1, l2, l3, l4, vBox5, l6, l7, l8, l9, l10);
        if ((order.getClient().length() > 17) & needSetMinHeight ) {
            l3.setMaxHeight(41);
        }

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private Label createLabel (String s, Double width, int fontSize) {
        Label label = new Label(s);
        label.setId("cell_body");
        label.setPrefWidth(width);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.setFont(new Font(fontSize));
        return label;
    }

    private Label createLabelforHBox (String s, String id, Double width, int fontSize) {
        Label label = new Label(s);
        label.setId(id);
        label.setPrefWidth(width);
//        label.setMaxHeight(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.setMaxHeight(200);
        label.setFont(new Font(fontSize));
        return label;
    }

    private VBox getAllDescription(List<DescriptionOrder> descriptions) {
        VBox vBox = new VBox(0);
        vBox.setSpacing(0);
        vBox.setPadding(new Insets(0,0,0,0));
        HBox line = new HBox(0);
        needSetMinHeight = false;
        for (DescriptionOrder descriptionOrder : descriptions) {
            line = new HBox(0);
            line.setId("row_description");
            line.setPrefWidth(500);
            line.setSpacing(0);
            line.setPadding(new Insets(0,0,0,0));

            Text descr = new Text(descriptionOrder.getDescrFirst() + " " + descriptionOrder.getDescrSecond());
            double widthDescr = descr.getBoundsInLocal().getWidth();
//            System.out.println(descriptionOrder.getKod() +  ":  width = " + widthDescr);
            descr.setWrappingWidth(250);
            descr.setTranslateX(5);

            Label pos = createLabelforHBox (String.valueOf(descriptionOrder.getPosition()), "position", 35.0, 12);
            pos.setMinHeight(40);

            Label am = createLabelforHBox (String.valueOf(descriptionOrder.getAmount()), "amount", 35.0, 12);
            Label type = createLabelforHBox (descriptionOrder.getType(), "type", 70.0, 12);
            type.setStyle("-fx-text-fill: green");
            Label status = createLabelforHBox (StatusName.LONG_NAME[descriptionOrder.getStatusIndex()], "status", 99.0, 14);
            status.setStyle("-fx-text-fill: blue");

            line.getChildren().addAll(pos, descr, am, type, status);
            vBox.getChildren().add(line);
            if ( (widthDescr < descr.getWrappingWidth()*2 - 10) & (descriptions.size() == 1)) {
                needSetMinHeight = true;
//                System.out.println(descriptionOrder.getKod() +  ":  width = " + widthDescr);
            }
        }
        line.setId("end_row_description");

        return vBox;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        createHBox(order);
    }

    public HBox gethBox() {
        return hBox;
    }

    public void sethBox(HBox hBox) {
        this.hBox = hBox;
    }


}
