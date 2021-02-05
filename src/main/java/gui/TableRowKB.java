package gui;

import data.DataControl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.description.DescriptionOrder;
import name.StatusName;
import model.description.DescriptionFactory;
import model.order.OrderFactory;
import time.DateConverter;

import java.security.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class TableRowKB {

    private int rowHeight = 150;
    private OrderFactory order = null;
    private HBox hBox = new HBox(0);;

    TableRowKB(OrderFactory order) {
        this.order = order;
        createHBox(order);
    }

    private void createHBox(OrderFactory order) {
        hBox.setSpacing(0);
        hBox.setPadding(new Insets(0, 0, 0, 0));
        hBox.setId("row_body");
        hBox.getChildren().clear();
        List<Long> timeCriterion = new ArrayList<>();
//        Long l1 = null, l2 = null;
        String t3 = DateConverter.dateWithYearToString (order.getDateToFactory().getTime());
//        String t13 = DateConverter.dateAfterThreeDay(order);
        long endTimeInMillis = DateConverter.dateAfterThreeDay(order);
        String t13 = DateConverter.dateWithYearToString(endTimeInMillis) + "\n" + DateConverter.timeToString(endTimeInMillis);


        Label col2_num = createLabel(order.getStringNumber(), 65.0, 16);
        HBox col2_client = createClientBox(order.getClient());
        Label col2_dateToFactory = createLabel(t3, 60.0, 12);
        Label col2_manager = createLabel(order.getManager() + "\n" + order.getDesigner(), 115.0, 14);
//        Label col2_designer = createLabel(order.getDesigner(), 80.0, 12);

        VBox vBox = getAllDescription(order.getDescriptions());

        Label col2_date13 = createLabel(t13, 60.0, 12);

        hBox.getChildren().addAll(col2_num, col2_client, col2_dateToFactory, col2_manager, /*col2_designer,*/ vBox, col2_date13);
        int amountDay = DateConverter.getPeriodForDesigner(endTimeInMillis);
        if (amountDay < -3) {
            hBox.setStyle("-fx-background-color: rgba(155, 0, 0, 0.8);");
        }
        else if (amountDay < 0) {
            hBox.setStyle("-fx-background-color: rgba(255, 0, 0, 0.9);");
        }
        else if (amountDay == 0) {
            hBox.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5);");
        }
        else if (amountDay == 1) {
            hBox.setStyle("-fx-background-color: rgba(255, 0, 0, 0.2);");
        }

/*        String ollDescriptionWords = (order.getDescriptions().get(0).getDescr());
        String twoLastWord = (order.getDescriptions().get(0).getDescr()).substring(ollDescriptionWords.length()-2);
        System.out.println(twoLastWord);
        if (twoLastWord.equals("НТ") | twoLastWord.equals("НП")) {
            hBox.setStyle("-fx-background-color: rgba(110, 222, 170, 0.5);");
        }*/
        if (order.getClient().equalsIgnoreCase("новая техника")) {
            hBox.setStyle("-fx-background-color: rgba(110, 222, 170, 0.5);");
        }


//*     mark Order which contains Description position 'Cupboard'
        for (DescriptionFactory descriptionFactory : order.getDescriptions()) {
            String compareDescription = descriptionFactory.getDescr();
            if ( compareDescription.contains("Шафа для хліба") | compareDescription.contains("шафа для хліба") ) {
                hBox.setStyle("-fx-background-color: rgba(79, 86, 191, 0.76);");
                break;
            }
        }
    }

    private VBox getAllDescription(List<DescriptionFactory> descriptions) {
        VBox vBox = new VBox(0);
        vBox.setSpacing(0);
        vBox.setPadding(new Insets(0,0,0,0));
        HBox line = new HBox(0);
        for (DescriptionFactory descriptionFactory : descriptions) {
            line = new HBox(0);
            line.setId("row_description");
            line.setPrefWidth(760);
            line.setSpacing(0);
            line.setPadding(new Insets(0,0,0,0));

            String textForLabelDate1 = DateConverter.dateWithYearToString(descriptionFactory.getStatusTimeList()[2]) + "\n" +
                    DateConverter.timeToString(descriptionFactory.getStatusTimeList()[2]);

            Label col2_pos = createLabelForHBox(String.valueOf(descriptionFactory.getPosition()), "first_descr_cell", 30.0, 12);
            col2_pos.setMinHeight(30);
            Label col2_type = createLabelForHBox(descriptionFactory.getType(), "first_descr_cell", 50.0, 12);
            Label col2_status = createLabelForHBox(StatusName.LONG_NAME[descriptionFactory.getStatusIndex()], "first_descr_cell", 90.0, 12);
            col2_status.setStyle("-fx-text-fill: blue");

            Text col2_descr = new Text(descriptionFactory.getDescr());
            col2_descr.setWrappingWidth(290);
            col2_descr.setId("first_descr_cell");
            col2_descr.setTranslateX(5);
            col2_descr.setFont(new Font(16));

            Label col2_descrSize = createLabelForHBox(descriptionFactory.getDescrSize(), "last_descr_cell", 100.0, 16);
            col2_descrSize.setWrapText(true);
            col2_descrSize.setTranslateX(5);

            Label col2_amount = createLabelForHBox(String.valueOf(descriptionFactory.getAmount()), "last_descr_cell", 30.0, 12);
            Label col2_date1 = createLabelForHBox(textForLabelDate1, "last_descr_cell", 60.0, 12);

            Node col2_date3 = createNodeForHBox(descriptionFactory, 3, 60.0, 12);
            Node col2_date4 = createNodeForHBox(descriptionFactory, 4, 60.0, 12);
            Node col2_date5 = createNodeForHBox(descriptionFactory, 5, 60.0, 12);
//            Node col2_date6 = createNodeForHBox(descriptionFactory, 6, 60.0, 12);
            Node col2_date7 = createNodeForHBox(descriptionFactory, 7, 60.0, 12);

            line.getChildren().addAll(col2_pos, /*col2_type, col2_status,*/ col2_descr, col2_descrSize, col2_amount, col2_date1,
                    col2_date3, col2_date4, col2_date5, /*col2_date6, */ col2_date7);
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
        Label label = new Label(s);
        label.setId(id);
        label.setPrefWidth(width);
//        label.setMaxHeight(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        label.setMaxHeight(rowHeight);
        label.setFont(new Font(fontSize));
        return label;
    }

    private Node createNodeForHBox(DescriptionFactory descr, int statusPosition, Double width, int fontSize) {
        if ( descr.getStatusTimeList()[statusPosition] == 0 ){
            CheckBox chBox = new CheckBox();
            descr.getCheckBoxesStatus()[statusPosition] = chBox;
            chBox.setId("last_descr_cell");
            chBox.setPrefWidth(width);
            chBox.setAlignment(Pos.CENTER);
            chBox.setPadding(new Insets(0, 0, 6, 3));
            chBox.setMinHeight(30);
            chBox.setMaxHeight(rowHeight);
            chBox.setFont(new Font(fontSize));
            return chBox;
        }
        else {
            Label label = new Label(DateConverter.dateWithYearToString(descr.getStatusTimeList()[statusPosition]) + "\n" +
                    DateConverter.timeToString(descr.getStatusTimeList()[statusPosition]));
            label.setId("last_descr_cell");
            label.setPrefWidth(width);
            label.setAlignment(Pos.CENTER);
//            label.setPadding(new Insets(0, 0, 6, 3));
//            label.setMinHeight(35);
            label.setMaxHeight(rowHeight);
            label.setFont(new Font(fontSize));

            return label;
        }


    }

    public HBox createClientBox (String clientName) {
        HBox hBox = new HBox(0);
        int wight = 100;
        hBox.setSpacing(0);
        hBox.setPadding(new Insets(0, 0, 0, 0));
        hBox.setPrefWidth(wight);
        hBox.setId("cell_body");
        hBox.getChildren().clear();

        Text col2_client = new Text(clientName);
        col2_client.setWrappingWidth(wight-3);
        col2_client.setTranslateX(3);
        col2_client.setFont(new Font(14));
        hBox.getChildren().add(col2_client);
        return hBox;
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
