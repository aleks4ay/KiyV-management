package gui;

import data.*;
import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import model.description.DescriptionFactory;
import model.description.DescriptionOrder;
import model.description.DescriptionParsing;
import model.order.Order;
import model.order.OrderFactory;
import model.order.OrderParsing;
import time.DateConverter;
import artifact_new_manufplan.controller.ReportController;

import java.io.File;
import java.net.URL;

import java.time.LocalDate;
import java.util.*;

public class SceneController implements Initializable {

    private LongProperty isNewOrderTime = new SimpleLongProperty(new File(DataControl.getFileName()).lastModified());

//    private boolean thisAppChangeData = false;
    private static File file1 = new File(DataControl.getFileName());
    public String profile = DataControl.getCurrentProfile();

    public static String sortWayKb = "по № заказа";
    public static String sortWay = "По номеру заказа";
    public static String dateFactor = "дата 'в производство'"; //"дата заказа";
    public static String observeOrder = "Заказы в процессе производства";
    public static String typeView = "ВСЕ"; //"ВСЕ (ЦЕХ, КБ, НОВЫЙ)";
    public static String managerName = "Выбрать всех";
    public static String designerName = "Выбрать всех";
    public static String dateWay = "За последние 30 дней";

    public static String sortWayFactory = "По номеру заказа";
//    public static String observeOrderFactory = "Все открытые заказы";
    public static String observeOrderFactory = "Заказы в цехе";
    public static String typeViewFactory = "ЦЕХ, КБ, новый"; //"ВСЕ (ЦЕХ, КБ, НОВЫЙ)";

    public long timeStart = 0L;
    public long timeEnd = 0L;
    public long timeFactory = 0L;
    public int selektedRow = 0;
    public boolean needDoEventDataPicker = true;
    public boolean isSearchFactory = false;

    public static Order foundedOrder = null;

    private List<String> listTechn = new ArrayList<>();

    private ObservableList<Order> listOrder = FXCollections.observableArrayList();
    private ObservableList<OrderFactory> listOrderForFactory = FXCollections.observableArrayList();
    private ObservableList<OrderFactory> listOrderForKB = FXCollections.observableArrayList();

    private ObservableList<HBox> listHBox = FXCollections.observableArrayList();
    private ObservableList<HBox> listHBoxForFactory = FXCollections.observableArrayList();
    private ObservableList<HBox> listHBoxForKB = FXCollections.observableArrayList();
    private ObservableList<HBox> listHBoxForSearch = FXCollections.observableArrayList();

    private ObservableList<TableRow> listTableRow = FXCollections.observableArrayList();
    private ObservableList<TableRowFactory> listTableRowForFactory = FXCollections.observableArrayList();
    private ObservableList<TableRowKB> listTableRowForKB = FXCollections.observableArrayList();
//    private ObservableList<TableRowSearch> listTableRowForSearch = FXCollections.observableArrayList();

    private ObservableList<OrderParsing> listOrderParsing = FXCollections.observableArrayList();
    private ObservableList<DescriptionParsing> listDescriptionParsing = FXCollections.observableArrayList();

    @FXML private DatePicker day_start;
    @FXML private DatePicker day_end;
    @FXML private DatePicker day_current;
    @FXML private DatePicker day_factory;
    @FXML private DatePicker day_shipment;
    @FXML private TabPane tab_pane;

    @FXML private Tab tab_manager;
    @FXML private Tab tab_kb;
    @FXML private Tab tab_parsing;
    @FXML private Tab tab_factory;

    @FXML private CheckBox mosienko;
    @FXML private CheckBox check_date_factory;
    @FXML private RadioButton radio_kb_1;
    @FXML private RadioButton radio_kb_2;

    @FXML private ListView<HBox> listview_one;
    @FXML private ListView<HBox> listview_two;
    @FXML private ListView<HBox> listview_kb;

    @FXML private Label info1;
    @FXML private Label info2;
    @FXML private Label info3;
    @FXML private Label info4;
    @FXML private Label info5;
    @FXML private Label info6;
    @FXML private Label info7;
    @FXML private Label info8;
    @FXML private Label info9;
    @FXML private Label info10;
    @FXML private Label info11;
//    @FXML private Label info_new_data; //messege

    @FXML private Label info_factory_1;
    @FXML private Label info_factory_2;
    @FXML private Label info_factory_3;
    @FXML private Label info_factory_4;

    @FXML private Label info_kb_1;
    @FXML private Label info_kb_2;
    @FXML private Label info_kb_3;
    @FXML private TextField search_tablo_factory;

//    @FXML private MenuButton menu1;
//    @FXML private MenuButton menu2;
//    @FXML private MenuButton menu3;
//    @FXML private MenuButton menu4;
//    @FXML private MenuButton menu5;
//    @FXML private MenuButton menu6;

//---------------------- B U T T O N
    @FXML private Button parsing_save;
    @FXML private Button kb_save;
    @FXML private Button faktory_save;
    @FXML private Button faktory_change;
//    @FXML private Button kb_change;
    @FXML private Button faktory_cansel;
//    @FXML private Button btn_new_data; //message
//    @FXML private Button shipment_save;

//----------------T A B    P A R S I N G  ---------------------------------
    @FXML private Label info_parsing;
//--------------- Table 1 ---------------
    @FXML private TableView<OrderParsing> tableParsingView1;
    @FXML private TableColumn<OrderParsing, String> parsing_num;
    @FXML private TableColumn<OrderParsing, String> parsing_client;
    @FXML private TableColumn<OrderParsing, String> parsing_manager;
    @FXML private TableColumn<OrderParsing, String> parsing_data_f;
    @FXML private TableColumn<OrderParsing, String> parsing_count_position;
    @FXML private TableColumn<OrderParsing, String> parsing_description;

//--------------- Table 2 ---------------
    @FXML private TableView<DescriptionParsing> tableParsingView2;
    @FXML private TableColumn<DescriptionParsing, String> parsing_pos;
//    @FXML private TableColumn<DescriptionParsing, String> parsing_data_f2;
    @FXML private TableColumn<DescriptionParsing, Text> parsing_description2;
    @FXML private TableColumn<DescriptionParsing, String> parsing_description_size;
    @FXML private TableColumn<DescriptionParsing, String> parsing_amount;
    @FXML private TableColumn<DescriptionParsing, String> parsing_type;
    @FXML private TableColumn<DescriptionParsing, RadioButton> parsing_factory;
    @FXML private TableColumn<DescriptionParsing, RadioButton> parsing_kb;
    @FXML private TableColumn<DescriptionParsing, RadioButton> parsing_tehn;
    @FXML private TableColumn<DescriptionParsing, RadioButton> parsing_other;

// ---------------T A B     S E A R C H ------------------------------------------
    @FXML private TextField search_tablo;

    @FXML private Label search_client;
    @FXML private Label search_manager;
    @FXML private Label search_designer;
    @FXML private Label search_num;
    @FXML private Label search_d1;
    @FXML private Label search_d2;
    @FXML private Label search_period;
    @FXML private Label search_d4;
    @FXML private Label search_d5;
    @FXML private ListView<HBox> listview_search;
    @FXML private Text timeUpdate;
    @FXML private TextField search_year;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Thread main = Thread.currentThread();
        new Thread(() -> {
            while (main.isAlive()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isNewOrderTime.get() != file1.lastModified()) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater( () -> isNewOrderTime.setValue(file1.lastModified()));
                }
            }
        }).start();

//        choiceProfile();

        initDefaultStartValue();
        initParsingTabOne();
        initParsingTabTwo();

        updateAllView();

        isNewOrderTime.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                try {
                    System.out.println("Sleep 0.1 c");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                updateAllView();
            }
        });


        tab_pane.setOnMouseClicked(event -> {
// !--------------- cancel all selection rows ----------------------- !
            int index = listview_one.getSelectionModel().getSelectedIndex();
            listview_one.getSelectionModel().clearSelection(index);
            listview_one.getSelectionModel().clearSelection();

            int index2 = listview_two.getSelectionModel().getSelectedIndex();
            listview_two.getSelectionModel().clearSelection(index2);
            listview_two.getSelectionModel().clearSelection();
        });

        tableParsingView1.getSelectionModel().selectedIndexProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                listDescriptionParsing.clear();
                if (tableParsingView1.getSelectionModel().getSelectedItem() != null  ) {
                    OrderParsing selectedOrder = tableParsingView1.getSelectionModel().getSelectedItem();
                    listDescriptionParsing.addAll(selectedOrder.getDescriptions());

                }
            }
        });
    }


    private void initDefaultStartValue(){
        search_year.setText(String.valueOf(DateConverter.getYear(DateConverter.getNowDate())));
        LocalDate date2 = DateConverter.getNowLocalDate();
        day_end.setValue(date2);
        day_factory.setValue(date2);
        LocalDate date1 = date2.minusDays(29);
        day_start.setValue(date1);
        listview_one.setOrientation(Orientation.VERTICAL);
        listview_one.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        choiceProfile();

        info1.setText(sortWay);
        info2.setText(observeOrder);
        info3.setText(typeView);
        info4.setText(managerName);
        info5.setText(designerName);
        info6.setText(dateWay);
        info11.setText(dateFactor);

    }


    private void initTabOrders(){
        info8.setText("0");
        info9.setText("0");
        timeStart = java.sql.Date.valueOf(day_start.getValue()).getTime();
        timeEnd = java.sql.Date.valueOf(day_end.getValue()).getTime();

        listOrder.clear();
        listHBox.clear();
        listTableRow.clear();
        listOrder.addAll(ReaderForManager.readOrderForManager(
                timeStart, timeEnd,  managerName, designerName, sortWay, typeView, observeOrder, dateFactor));

        int index = 0;
        for (Order order : listOrder) {
            TableRow tableRow = new TableRow(order, index++);
            listTableRow.add(tableRow);
            listHBox.add(tableRow.gethBox());
        }

        listview_one.setItems(listHBox);

        info7.setText(String.valueOf(DateConverter.getPeriod(day_start.getValue(), day_end.getValue())));
        info8.setText(String.valueOf(listOrder.size()));
        info9.setText(String.valueOf(ReaderForManager.readCountOrders(timeStart, timeEnd, "all", sortWay)));
        info10.setText(String.valueOf(ReaderForManager.readCountOrders(timeStart, timeEnd, "closed", sortWay)));
    }

    private void initTabFactory(boolean isSearch) {
        listview_two.setOrientation(Orientation.VERTICAL);
        listview_two.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listTableRowForFactory.clear();
        listOrderForFactory.clear();
        listHBoxForFactory.clear();

        timeFactory = java.sql.Date.valueOf(day_factory.getValue()).getTime();

        if (isSearch) {
            listOrderForFactory.addAll(ReaderForFactory.readDBOnlyOne(search_tablo_factory.getText()));
        }
        else {
            listOrderForFactory.addAll(ReaderForFactory.readDB(check_date_factory.isSelected(), timeFactory,
                    sortWayFactory, observeOrderFactory, typeViewFactory));
        }

        for (OrderFactory order : listOrderForFactory) {
            TableRowFactory tableRowFactory = new TableRowFactory(order);
            listTableRowForFactory.add(tableRowFactory);
            listHBoxForFactory.add(tableRowFactory.gethBox());
        }
        listview_two.setItems(listHBoxForFactory);
        info_factory_4.setText(String.valueOf(listOrderForFactory.size()));
    }

    private void initTabKB() {
        listview_kb.setOrientation(Orientation.VERTICAL);
        listview_kb.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listTableRowForKB.clear();
        listOrderForKB.clear();
        listHBoxForKB.clear();
        listOrderForKB.addAll(ReaderForKB.readDB("status_index <= 6", sortWayKb, info_kb_2, info_kb_3));

//        int index = 0;
        for (OrderFactory order : listOrderForKB) {
            TableRowKB tableRowKB = new TableRowKB(order);
            listTableRowForKB.add(tableRowKB);
            listHBoxForKB.add(tableRowKB.gethBox());
        }
        listview_kb.setItems(listHBoxForKB);
        info_kb_1.setText(String.valueOf(listOrderForKB.size()));
    }

    private void initParsingTabOne() {
        listOrderParsing.clear();
        listOrderParsing.addAll(ReaderForParsing.readOrderForParsing());

        parsing_num.setCellValueFactory(new PropertyValueFactory<OrderParsing, String>("docNumber"));
        parsing_client.setCellValueFactory(new PropertyValueFactory<OrderParsing, String>("client"));
        parsing_manager.setCellValueFactory(new PropertyValueFactory<OrderParsing, String>("manager"));
        parsing_data_f.setCellValueFactory(new PropertyValueFactory<OrderParsing, String>("timeToParsingString"));
        parsing_count_position.setCellValueFactory(new PropertyValueFactory<OrderParsing, String>("allPosition"));
        parsing_description.setCellValueFactory(new PropertyValueFactory<OrderParsing, String>("descriptionFirstPosition"));

        tableParsingView1.setItems(listOrderParsing);
        tableParsingView1.getSelectionModel().select(selektedRow);
        info_parsing.setText(String.valueOf(listOrderParsing.size()));
    }

    private void initParsingTabTwo() {
        listDescriptionParsing.clear();
        if (tableParsingView1.getSelectionModel().getSelectedItem() != null) {
            OrderParsing selectedOrder = tableParsingView1.getSelectionModel().getSelectedItem();
            listDescriptionParsing.addAll(selectedOrder.getDescriptions());
        }

        parsing_pos.setCellValueFactory(new PropertyValueFactory<DescriptionParsing, String>("position"));
        parsing_description2.setCellValueFactory(new PropertyValueFactory<DescriptionParsing, Text>("descriptionText"));
        parsing_description_size.setCellValueFactory(new PropertyValueFactory<DescriptionParsing, String>("size"));
        parsing_amount.setCellValueFactory(new PropertyValueFactory<DescriptionParsing, String>("amount"));
        parsing_type.setCellValueFactory(new PropertyValueFactory<DescriptionParsing, String>("type"));
        parsing_factory.setCellValueFactory(new PropertyValueFactory<DescriptionParsing, RadioButton>("buttonFactory"));
        parsing_kb.setCellValueFactory(new PropertyValueFactory<DescriptionParsing, RadioButton>("buttonKB"));
        parsing_tehn.setCellValueFactory(new PropertyValueFactory<DescriptionParsing, RadioButton>("buttonTeh"));
        parsing_other.setCellValueFactory(new PropertyValueFactory<DescriptionParsing, RadioButton>("buttonOther"));

        tableParsingView2.setItems(listDescriptionParsing);
    }

    public void menu_1(ActionEvent actionEvent) {
        sortWay = ((MenuItem) actionEvent.getSource()).getText();
        info1.setText(sortWay);
        initTabOrders();
    }

    public void menu_2(ActionEvent actionEvent) {
        observeOrder = ((MenuItem) actionEvent.getSource()).getText();
        info2.setText(observeOrder);
        initTabOrders();
    }

    public void menu_3(ActionEvent actionEvent) {
        typeView = ((MenuItem) actionEvent.getSource()).getText();
        info3.setText(typeView);
        initTabOrders();
    }

    public void menu_4(ActionEvent actionEvent) {
        managerName = ((MenuItem) actionEvent.getSource()).getText();
        info4.setText(managerName);
        initTabOrders();
    }

    public void menu_5(ActionEvent actionEvent) {
        designerName = ((MenuItem) actionEvent.getSource()).getText();
        info5.setText(designerName);
        initTabOrders();
    }

    public void menu_6(ActionEvent actionEvent) {
        needDoEventDataPicker = false;
        dateWay = ((MenuItem) actionEvent.getSource()).getText();
        info6.setText(dateWay);
        ReaderForManager.setDatePeriod(dateWay, day_start, day_end);

        initTabOrders();
        needDoEventDataPicker = true;
    }

    public void menu_7(ActionEvent actionEvent) {
        dateFactor = ((MenuItem) actionEvent.getSource()).getText();
        info11.setText(dateFactor);
        initTabOrders();
    }

    public void changeDate(ActionEvent actionEvent) {
        if (needDoEventDataPicker) {
            dateWay = "Выбрать дату по календарю";
            info6.setText(dateWay);
            initTabOrders();
        }
    }

    public void menu_factory_1(ActionEvent actionEvent) {
        sortWayFactory = ((MenuItem) actionEvent.getSource()).getText();
        info_factory_1.setText(sortWayFactory);
        isSearchFactory = false;
        initTabFactory(isSearchFactory);
    }

    public void menu_factory_2(ActionEvent actionEvent) {
        observeOrderFactory = ((MenuItem) actionEvent.getSource()).getText();
        info_factory_2.setText(observeOrderFactory);
        isSearchFactory = false;
        initTabFactory(isSearchFactory);
    }

    public void menu_factory_3(ActionEvent actionEvent) {
        typeViewFactory = ((MenuItem) actionEvent.getSource()).getText();
        info_factory_3.setText(typeViewFactory);
        isSearchFactory = false;
        initTabFactory(isSearchFactory);
    }

    public void changeDateFactory(ActionEvent actionEvent) {
        isSearchFactory = false;
        initTabFactory(isSearchFactory);
    }

    private void choiceProfile() {
        if (profile.equalsIgnoreCase("manager") | profile.equalsIgnoreCase("0")) {
            tab_pane.getTabs().removeAll(tab_kb, tab_parsing, tab_factory);
        }
        if (profile.equalsIgnoreCase("designer") | profile.equalsIgnoreCase("1")) {
            tab_pane.getTabs().removeAll(tab_manager, tab_parsing, tab_factory);
        }
        if (profile.equalsIgnoreCase("factory") | profile.equalsIgnoreCase("2")) {
            tab_pane.getTabs().removeAll(tab_manager, tab_kb);
        }
        if (profile.equalsIgnoreCase("director") | profile.equalsIgnoreCase("3")) {

            parsing_save.setDisable(true);
            kb_save.setDisable(true);
            faktory_save.setDisable(true);
            faktory_change.setDisable(true);
//            tab_pane.getTabs().removeAll(tab_kb, tab_manager, tab_parsing, tab_ceh);
        }
        if (profile.equalsIgnoreCase("shipment") | profile.equalsIgnoreCase("4")) {
            tab_pane.getTabs().removeAll(tab_kb, tab_parsing, tab_factory);
//            sortWay = "По дате отгрузки фактической";
            dateFactor = "дата отгрузки";
            observeOrder = "Оплаченные";
            dateWay = "За день";
            LocalDate date2 = DateConverter.getNowLocalDate();
            day_end.setValue(date2);
            LocalDate date1 = date2;
            day_start.setValue(date1);
        }
    }

    public void applyStatus(ActionEvent actionEvent) throws InterruptedException {
//        thisAppChangeData = true;
        Setter.setStatus (listTableRowForFactory, listview_two);
    }

    public void applyParsing(ActionEvent actionEvent) throws InterruptedException {
//        thisAppChangeData = true;
        Setter.setType (listDescriptionParsing);
        selektedRow = tableParsingView1.getSelectionModel().getSelectedIndex();
    }

    public void applyWorkingKB(ActionEvent actionEvent) throws InterruptedException {
//        thisAppChangeData = true;
        if (mosienko.isSelected()) {
            Setter.setStatusKB(listTableRowForKB, listview_kb, true);
        }
        else {
            Setter.setStatusKB(listTableRowForKB, listview_kb, false);
        }
    }




    public void selectTheSame(ActionEvent actionEvent) {
        for (TableRowFactory tr : listTableRowForFactory) {
            OrderFactory or = tr.getOrder();
            for (DescriptionFactory descr : or.getDescriptions()) {
                CheckBox[] ch = descr.getCheckBoxesStatus();
                for (int statusIndex = 0; statusIndex < 25; statusIndex ++) {
                    if (ch[statusIndex] == null) {
                        continue;
                    }
                    else if (ch[statusIndex].isSelected() &  ! ch[statusIndex].isDisable()) {
                        for (DescriptionFactory d : or.getDescriptions()) {
                            if ( d.getCheckBoxesStatus()[statusIndex] != null) {
//                                System.out.println(statusIndex);
                                d.getCheckBoxesStatus()[statusIndex].setSelected(true);
                            }
                        }
                    }
                }
            }
        }
    }

    public void selectTheSameKB(ActionEvent actionEvent) {
        for (TableRowKB tr : listTableRowForKB) {
            OrderFactory or = tr.getOrder();
            for (DescriptionFactory descr : or.getDescriptions()) {
                CheckBox[] ch = descr.getCheckBoxesStatus();
                for (int statusIndex = 3; statusIndex < 8; statusIndex ++) {
                    if (ch[statusIndex] == null) {
                        continue;
                    }
                    else if (ch[statusIndex].isSelected() &  ! ch[statusIndex].isDisable()) {
                        for (DescriptionFactory d : or.getDescriptions()) {
                            if ( d.getCheckBoxesStatus()[statusIndex] != null) {
//                                System.out.println(statusIndex);
                                d.getCheckBoxesStatus()[statusIndex].setSelected(true);
                            }
                        }
                    }
                }
            }
        }
    }

    public void clearKB(ActionEvent actionEvent) {
        for (TableRowKB tr : listTableRowForKB) {
            OrderFactory or = tr.getOrder();
            for (DescriptionFactory descr : or.getDescriptions()) {
                CheckBox[] ch = descr.getCheckBoxesStatus();
                for (int statusIndex = 3; statusIndex < 8; statusIndex ++) {
                    if (ch[statusIndex] != null) {
                        ch[statusIndex].setSelected(false);
                    }
                }
            }
        }
    }

    private void updateAllView(){
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        initTabOrders();
        initTabFactory(isSearchFactory);
        initTabKB();

        initParsingTabOne();
        timeUpdate.setText(DataControl.readTimeChange());
//        timeUpdate.setText(DateConverter.dateWithYearToString(DateConverter.getNowDate()) + "  " + DateConverter.timeToString(DateConverter.getNowDate()));
    }

    public void searchOrderByEnter(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if (keyCode.getName().equalsIgnoreCase("Enter")) {
            showFoundedOrder();
        }
    }

    public void searchOrderByEnterFactory(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if (keyCode.getName().equalsIgnoreCase("Enter")) {
            showFoundedOrderFactory();
        }
    }

    @FXML
    private void showFoundedOrder () {
        String numString = search_tablo.getText();
//        Order order = null;
        if (DataControl.checkIntField(numString)) {
            int bigNumber = Integer.valueOf(search_year.getText(2,4)) * 100000 + Integer.valueOf(numString);
            foundedOrder = ReaderForSearch.readDB(String.valueOf(bigNumber));
            if (foundedOrder != null) {
                search_client.setText(foundedOrder.getClient());
                search_manager.setText(foundedOrder.getManager());
                search_designer.setText(foundedOrder.getDesigner());
                search_num.setText("Заказ №  " + foundedOrder.getDocNumber());
                search_d1.setText(DateConverter.dateWithYearToString(foundedOrder.getDateCreate().getTime()));
                search_d2.setText(DateConverter.dateWithYearToString(foundedOrder.getDateToFactory().getTime()));
                search_period.setText(String.valueOf(foundedOrder.getDurationTime()));
                search_d4.setText(DateConverter.dateWithYearToString(foundedOrder.getDateToShipment().getTime()));


                listview_search.setOrientation(Orientation.VERTICAL);
                listview_search.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

                listHBoxForSearch.clear();
                for (DescriptionOrder descr : foundedOrder.getDescriptions()) {
                    listHBoxForSearch.add(new TableRowSearch(descr).gethBox());
                }
                listview_search.setItems(listHBoxForSearch);


            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFO");
                alert.setHeaderText("Заказа с таким номером нет в базе...");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText("Введите число (максимум 5 цифр)");
            alert.showAndWait();
            search_tablo.setText("");
        }
    }

    private void showFoundedOrderFactory () {
        String numString = search_tablo_factory.getText();
        Order order = null;
        if (DataControl.checkIntField(numString)) {
            int bigNumber = DateConverter.getYearShort(DateConverter.getNowDate()) * 100000 + Integer.valueOf(numString);
            order = ReaderForSearch.readDB(String.valueOf(bigNumber));
            if (order != null) {
                isSearchFactory = true;
                initTabFactory(isSearchFactory);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFO");
                alert.setHeaderText("Заказа с таким номером нет в базе...");
                alert.showAndWait();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFO");
            alert.setHeaderText("Введите число (максимум 5 цифр)");
            alert.showAndWait();
            search_tablo_factory.setText("");
        }
    }

    public void canselOneOrder(ActionEvent actionEvent) {
        isSearchFactory = false;
        search_tablo_factory.setText("");
        initTabFactory(isSearchFactory);
    }


    public void sortingKb(ActionEvent actionEvent) {
        sortWayKb = ((RadioButton) actionEvent.getSource()).getText();
        initTabKB();
    }

    public void shipmentDaySave(ActionEvent actionEvent) {
        if (day_shipment.getValue() != null) {
            LocalDate dateShipment = day_shipment.getValue();
            System.out.println(dateShipment.toString());
        }
    }

    public void setAside(ActionEvent actionEvent) {
        for (TableRowKB tr : listTableRowForKB) {
            OrderFactory or = tr.getOrder();
            for (DescriptionFactory descr : or.getDescriptions()) {
                CheckBox[] ch = descr.getCheckBoxesStatus();
                for (int statusIndex = 3; statusIndex < 8; statusIndex ++) {
                    if (ch[statusIndex] == null) {
                        continue;
                    }
                    else if (ch[statusIndex].isSelected() &  ! ch[statusIndex].isDisable()) {
                        WriterPostgreSql.writeAside(descr.getKod(), 5);
                        updateAllView();
                        return;
                    }
                }
            }
        }
    }

    public void renew(ActionEvent actionEvent) {
        System.out.println("renew");
    }

    public void setTypeKB(ActionEvent actionEvent) {
        List<DescriptionOrder> descriptionList = new ArrayList<>();
        for (int i = 0; i < listHBoxForSearch.size(); i++) {
            HBox row = listHBoxForSearch.get(i);
            for (Node object: row.getChildren()) {
                if (object.getClass().equals(CheckBox.class)) {
                    if (((CheckBox)object).isSelected()) {
                        WriterPostgreSql.rewriteTypeKB(foundedOrder.getDescriptions().get(i).getKod());
                        break;
                    }
                }
            }
        }
        updateAllView();
    }

    public void printReport(ActionEvent actionEvent) {
        String dateStart = "" +  day_start.getValue().getDayOfMonth() + "-" + day_start.getValue().getMonthValue() + "-" + day_start.getValue().getYear();
        String dateEnd = "" +  day_end.getValue().getDayOfMonth() + "-" + day_end.getValue().getMonthValue() + "-" + day_end.getValue().getYear();
        new ReportController("print/report2.html").createAndPrintReport(dateStart, dateEnd);
    }
}
