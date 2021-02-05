package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import model.description.DescriptionOrder;
import name.StatusName;
import time.DateConverter;


public class TableRowSearch {

    private DescriptionOrder description = null;
    private HBox hBox = new HBox(0);

    TableRowSearch(DescriptionOrder description) {
        this.description = description;
        createHBox(description);
    }

    private void createHBox(DescriptionOrder descr) {
        hBox.setSpacing(0);
        hBox.setPadding(new Insets(0, 0, 0, 0));
        hBox.setId("row_body");
        hBox.setMaxWidth(1212.0);

        Label pos = createLabel(String.valueOf(descr.getPosition()), 30.0, 14);
        Label type = createLabel(descr.getType(), 50.0, 14);
        Label descrLabel = createLabel(descr.getDescrFirst() + " " + descr.getDescrSecond(), 200.0, 14);
        descrLabel.setWrapText(true);
        descrLabel.setPadding(new Insets(0, 2, 0, 2));
        Label descrSize = createLabel(descr.getSizeA() + "×" + descr.getSizeB() + "×" + descr.getSizeC(), 110.0, 14);
        Label amount = createLabel(String.valueOf(descr.getAmount()),  30.0, 12);
        Label status = createLabel(StatusName.LONG_NAME[descr.getStatusIndex()], 90.0, 14);
        status.setStyle("-fx-text-fill: blue");

        String text2 = "", text3 = "", text7 = "", text8 = "", text11 = "", text12 = "", text13 = "", text15 = "";
        String text20 = "", text21 = "", text22 = "", text24 = "";


        if ( descr.getStatusTimeList()[2] != 0) {
            text2 = DateConverter.dateWithYearToString(descr.getStatusTimeList()[2])
                    + "\n" + DateConverter.timeToString(descr.getStatusTimeList()[2]);
        }
        if ( descr.getStatusTimeList()[3] != 0) {
            text3 = DateConverter.dateWithYearToString(descr.getStatusTimeList()[3])
                    + "\n" + DateConverter.timeToString(descr.getStatusTimeList()[3]);
        }
        if ( descr.getStatusTimeList()[7] != 0) {
            text7 = DateConverter.dateWithYearToString(descr.getStatusTimeList()[7])
                    + "\n" + DateConverter.timeToString(descr.getStatusTimeList()[7]);
        }
        if ( descr.getStatusTimeList()[8] != 0) {
            text8 = DateConverter.dateWithYearToString(descr.getStatusTimeList()[8])
                    + "\n" + DateConverter.timeToString(descr.getStatusTimeList()[8]);
        }
        if ( descr.getStatusTimeList()[11] != 0) {
            text11 = DateConverter.dateWithYearToString(descr.getStatusTimeList()[11])
                    + "\n" + DateConverter.timeToString(descr.getStatusTimeList()[11]);
        }
        if ( descr.getStatusTimeList()[12] != 0) {
            text12 = DateConverter.dateWithYearToString(descr.getStatusTimeList()[12])
                    + "\n" + DateConverter.timeToString(descr.getStatusTimeList()[12]);
        }
/*
        if ( descr.getStatusTimeList()[13] != 0) {
            text13 = DateConverter.dateWithYearToString(descr.getStatusTimeList()[13])
                    + "\n" + DateConverter.timeToString(descr.getStatusTimeList()[13]);
        }
        if ( descr.getStatusTimeList()[15] != 0) {
            text15 = DateConverter.dateWithYearToString(descr.getStatusTimeList()[15])
                    + "\n" + DateConverter.timeToString(descr.getStatusTimeList()[15]);
        }
*/
        if ( descr.getStatusTimeList()[20] != 0) {
            text20 = DateConverter.dateWithYearToString(descr.getStatusTimeList()[20])
                    + "\n" + DateConverter.timeToString(descr.getStatusTimeList()[20]);
        }
        if ( descr.getStatusTimeList()[21] != 0) {
            text21 = DateConverter.dateWithYearToString(descr.getStatusTimeList()[21]);
        }
        if ( descr.getStatusTimeList()[22] != 0) {
            text22 = DateConverter.dateWithYearToString(descr.getStatusTimeList()[22]);
        }
        if ( descr.getStatusTimeList()[24] != 0) {
            text24 = DateConverter.dateWithYearToString(descr.getStatusTimeList()[24])
                    + "\n" + DateConverter.timeToString(descr.getStatusTimeList()[24]);
        }


        Label date2 = createLabel(text2, 65.0, 12);
        date2.setStyle("-fx-text-fill: green");
        Label date3 = createLabel(text3, 65.0, 12);
        date3.setStyle("-fx-text-fill: green");
        Label date7 = createLabel(text7, 60.0, 12);
        Label date8 = createLabel(text8, 65.0, 12);
        Label date11 = createLabel(text11, 60.0, 12);
        Label date12 = createLabel(text12, 60.0, 12);
//        Label date13 = createLabel(text13, 70.0, 12);
//        Label date15 = createLabel(text15, 60.0, 12);
        Label date20 = createLabel(text20, 65.0, 12);
        Label date21 = createLabel(text21, 75.0, 12);
        Label date22 = createLabel(text22, 70.0, 12);
        Label date24 = createLabel(text24, 75.0, 12);
        CheckBox isKB = new CheckBox();
        isKB.setAlignment(Pos.CENTER);
        isKB.setPadding(new Insets(0, 0, 6, 3));
        isKB.setMinHeight(35);
        isKB.setMaxHeight(200);


        hBox.getChildren().clear();
        hBox.getChildren().addAll(pos, type, descrLabel, descrSize, amount, status, date2, date3, date7, date8, date11,
                date12, /*date13, date15, */date20, date21, date22, date24, isKB);

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

   /* private Label createLabelForHBox(String s, String id, Double width, int fontSize) {
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
        if ( descr.getStatusTimeList()[statusPosition] == 0 ){
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
            Label label = new Label(DateConverter.dateWithYearToString(descr.getStatusTimeList()[statusPosition]) + "\n" +
                    DateConverter.timeToString(descr.getStatusTimeList()[statusPosition]));
            label.setId("last_descr_cell");
            label.setPrefWidth(width);
            label.setAlignment(Pos.CENTER);
//            label.setPadding(new Insets(0, 0, 6, 3));
//            label.setMinHeight(35);
            label.setMaxHeight(200);
            label.setFont(new Font(fontSize));

            return label;
        }


    }*/



    public DescriptionOrder getDescription() {
        return description;
    }

    public void setDescription(DescriptionOrder description) {
        this.description = description;
        createHBox(description);
    }

    public HBox gethBox() {
        return hBox;
    }

    public void sethBox(HBox hBox) {
        this.hBox = hBox;
    }
}
