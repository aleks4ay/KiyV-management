package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.description.DescriptionFactory;
import name.StatusName;
import model.order.OrderFactory;
import time.DateConverter;


import java.util.List;


public class TableRowFactory {

    private OrderFactory order = null;
    private HBox hBox = new HBox(0);;

    TableRowFactory(OrderFactory order) {
        this.order = order;
        createHBox(order);
    }

    private void createHBox(OrderFactory order) {
        hBox.setSpacing(0);
        hBox.setPadding(new Insets(0, 0, 0, 0));
        hBox.setId("row_body");

        Label col2_num = createLabel(order.getStringNumber(), 65.0, 16);
        Label col2_client = createLabel(order.getClient(), 100.0, 12);
        String designerName = order.getDesigner();
        Label col2_manager = null;
        if (designerName != null) {
            col2_manager = createLabel((order.getManager() + "/\n" + order.getDesigner()), 110.0, 12);
        }
        else {
            col2_manager = createLabel(order.getManager(), 110.0, 12);
        }
//        Label col2_designer = createLabel(order.getDesigner(), 100.0, 12);

        String t13 = DateConverter.dateWithYearToString (order.getDateToShipment().getTime());
        Label col2_date13 = createLabel(t13, 75.0, 12);

        VBox vBox = getAllDescription(order.getDescriptions());
        hBox.getChildren().clear();
        hBox.getChildren().addAll(col2_num, col2_client, col2_manager, /*col2_designer,*/ vBox, col2_date13);
    }

    private VBox getAllDescription(List<DescriptionFactory> descriptions) {
        VBox vBox = new VBox(0);
        vBox.setSpacing(0);
        vBox.setPadding(new Insets(0,0,0,0));
        HBox line = new HBox(0);
        for (DescriptionFactory descriptionFactory : descriptions) {
            line = new HBox(0);
            line.setId("row_description");
            line.setPrefWidth(1200);
            line.setSpacing(0);
            line.setPadding(new Insets(0,0,0,0));

            Text col2_descr = new Text(descriptionFactory.getDescr() + "\nГабаритные размеры: " + descriptionFactory.getDescrSize());
            col2_descr.setWrappingWidth(290);
            col2_descr.setTranslateX(5);

            Label col2_pos = createLabelForHBox(String.valueOf(descriptionFactory.getPosition()), "first_descr_cell", 30.0, 12);
            col2_pos.setMinHeight(25);

            String textForLabelDate1 = DateConverter.dateWithYearToString(descriptionFactory.getStatusTimeList()[0]) ;
//                    + "\n" + DateConverter.timeToString(descriptionFactory.getStatusTimeList()[0]);
            String textForLabelDate2 = DateConverter.dateWithYearToString(descriptionFactory.getStatusTimeList()[2])
                    + "\n" + DateConverter.timeToString(descriptionFactory.getStatusTimeList()[2]);
            String textForLabelDate3 = DateConverter.dateWithYearToString(descriptionFactory.getStatusTimeList()[7])
                    + "\n" + DateConverter.timeToString(descriptionFactory.getStatusTimeList()[7]);

            Label col2_type = createLabelForHBox(descriptionFactory.getType(), "first_descr_cell", 50.0, 12);
            Label col2_amount = createLabelForHBox(String.valueOf(descriptionFactory.getAmount()), "last_descr_cell", 30.0, 12);
            Label col2_status = createLabelForHBox(StatusName.LONG_NAME[descriptionFactory.getStatusIndex()], "last_descr_cell", 90.0, 14);
            col2_status.setStyle("-fx-text-fill: blue");
            Label col2_date1 = createLabelForHBox(textForLabelDate1, "last_descr_cell", 60.0, 12);
            Label col2_date2 = createLabelForHBox(textForLabelDate2, "last_descr_cell", 65.0, 12);
            col2_date2.setStyle("-fx-text-fill: green");
            Label col2_date3 = createLabelForHBox(textForLabelDate3, "last_descr_cell", 60.0, 12);
            col2_date3.setStyle("-fx-text-fill: green");
            Node col2_date4 = createNodeForHBox(descriptionFactory, 8, 65.0, 12);
            Node col2_date5 = createNodeForHBox(descriptionFactory, 11, 60.0, 12);
            Node col2_date6 = createNodeForHBox(descriptionFactory, 12, 60.0, 12);
            Node col2_date7 = createNodeForHBox(descriptionFactory, 13, 70.0, 12);
            Node col2_date8 = createNodeForHBox(descriptionFactory, 15, 60.0, 12);
            Node col2_date9 = createNodeForHBox(descriptionFactory, 20, 60.0, 12);
            Node col2_date10 = createNodeForHBox(descriptionFactory, 21, 75.0, 12);
            Node col2_date11 = createNodeForHBox(descriptionFactory, 22, 65.0, 12);
//            Node col2_date12 = createNodeForHBox(descriptionFactory, 24, 64.0, 12);

            line.getChildren().addAll(col2_pos, col2_type, col2_descr, col2_amount, col2_status, col2_date1, col2_date2, col2_date3, col2_date4, col2_date5, col2_date6, col2_date7,
                    col2_date8, col2_date9, col2_date10, col2_date11 /*, col2_date12*/);
            vBox.getChildren().add(line);
        }
        line.setId("end_row_description");
        return vBox;
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

    private Label createLabelForHBox(String s, String id, Double width, int fontSize) {
        if (s.substring(0, 1).equalsIgnoreCase("-")) {
            s = "-";
        }
        Label label = new Label(s);
        label.setId(id);
        label.setPrefWidth(width);
//        label.setMaxHeight(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.setMaxHeight(200);
        label.setFont(new Font(fontSize));
        return label;
    }

    private Node createNodeForHBox(DescriptionFactory descr, int statusPosition, Double width, int fontSize) {
        if ( descr.getStatusTimeList()[statusPosition] == 0 & (statusPosition < 21) ){
            CheckBox chBox = new CheckBox();
            descr.getCheckBoxesStatus()[statusPosition] = chBox;
            chBox.setId("last_descr_cell");
            chBox.setPrefWidth(width);
            chBox.setAlignment(Pos.CENTER);
            chBox.setPadding(new Insets(0, 0, 6, 3));
            chBox.setMinHeight(35);
            chBox.setMaxHeight(200);
            chBox.setFont(new Font(fontSize));
            return chBox;
        }
        else {
            Label label = new Label();
            if (statusPosition>= 21) {
                label.setText(DateConverter.dateWithYearToString(descr.getStatusTimeList()[statusPosition]));
                if (descr.getStatusTimeList()[statusPosition] == 0L) {
                    label.setText("");
                }
            }
            else {
                 label.setText(DateConverter.dateWithYearToString(descr.getStatusTimeList()[statusPosition]) + "\n" +
                        DateConverter.timeToString(descr.getStatusTimeList()[statusPosition]));
            }
            label.setId("last_descr_cell");
            label.setPrefWidth(width);
            label.setAlignment(Pos.CENTER);
//            label.setPadding(new Insets(0, 0, 6, 3));
//            label.setMinHeight(35);
            label.setMaxHeight(200);
            label.setFont(new Font(fontSize));

            return label;
        }


    }



    public OrderFactory getOrder() {
        return order;
    }

    public void setOrder(OrderFactory order) {
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
