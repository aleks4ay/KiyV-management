package fill_status;

import artifact_new_manufplan.domain.dao.UtilDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import time.DateConverter;

import java.net.URL;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class StatusSceneController implements Initializable {

// ---------------   S E A R C H ------------------------------------------
    @FXML private Label search_client;
    @FXML private Label search_manager;
    @FXML private Label search_designer;
    @FXML private Label search_num;
    @FXML private Label search_d1;
    @FXML private Label search_d2;

    @FXML private TextField search_year;
    @FXML private TextField search_tablo_num;
    @FXML private TextField search_tablo_id;
    @FXML private TextField search_tablo_pos;

    @FXML private TextField fill_descr_new;
    @FXML private TextField fill_type_new;
    @FXML private TextField fill_status_new;
    @FXML private TextField fill_date_0_new;
    @FXML private TextField fill_date_1_new;
    @FXML private TextField fill_date_2_new;
    @FXML private TextField fill_date_3_new;
    @FXML private TextField fill_date_4_new;
    @FXML private TextField fill_date_5_new;
    @FXML private TextField fill_date_7_new;
    @FXML private TextField fill_date_21_new;
    @FXML private TextField fill_date_22_new;
    @FXML private TextField fill_date_24_new;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initDefaultStartValue();
    }


    private void initDefaultStartValue(){
        search_year.setText(String.valueOf(DateConverter.getYear(DateConverter.getNowDate())));
        search_tablo_pos.setText("1");
    }


    public void searchByIdByEnter(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if (keyCode.getName().equalsIgnoreCase("Enter")) {
            showFoundedStatusById();
        }
    }

    public void searchByNumByEnter(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        if (keyCode.getName().equalsIgnoreCase("Enter")) {
            showFoundedStatusByNum();
        }
    }


    @FXML
    private void showFoundedStatusByNum () {
        String numString = search_tablo_num.getText();
        String year = search_year.getText();
        Connection conn = new UtilDao().getConnPostgres();
        StatusDaoJdbc dao = new StatusDaoJdbc(conn);
        String id = dao.getIdByDocNumber(numString, Integer.parseInt(year));
        search_tablo_id.setText(id);
        showFoundedStatusById();
    }

    @FXML
    private void showFoundedStatusById () {
        String id = search_tablo_id.getText();
        String positionString = search_tablo_pos.getText();

        Connection conn = new UtilDao().getConnPostgres();
        StatusDaoJdbc dao = new StatusDaoJdbc(conn);
        StatusDTO statusDTO = dao.getOne(id + "-" + positionString);

        if (statusDTO != null) {
            OrderDTO order = dao.getOrder(id);
            search_num.setText("Заказ №  " + order.getDocNumber());
            search_client.setText(order.getClient());
            search_manager.setText(order.getManager());
            search_d1.setText(order.getDateCreate().toString());
            search_d2.setText(order.getDateToFactory().toString());

            fill_descr_new.setText(statusDTO.getDescription());
            fill_type_new.setText(String.valueOf(statusDTO.getTypeIndex()));
            fill_status_new.setText(String.valueOf(statusDTO.getStatusIndex()));
            fill_date_0_new.setText(getDateTimeString(statusDTO.getTime_0()));
            fill_date_1_new.setText(getDateTimeString(statusDTO.getTime_1()));
            fill_date_2_new.setText(getDateTimeString(statusDTO.getTime_2()));
            fill_date_3_new.setText(getDateTimeString(statusDTO.getTime_3()));
            fill_date_4_new.setText(getDateTimeString(statusDTO.getTime_4()));
            fill_date_5_new.setText(getDateTimeString(statusDTO.getTime_5()));
            fill_date_7_new.setText(getDateTimeString(statusDTO.getTime_7()));
            fill_date_21_new.setText(getDateTimeString(statusDTO.getTime_21()));
            fill_date_22_new.setText(getDateTimeString(statusDTO.getTime_22()));
            fill_date_24_new.setText(getDateTimeString(statusDTO.getTime_24()));
        }
        else {
            fill_descr_new.setText("");
            fill_type_new.setText("");
            fill_status_new.setText("");
            fill_date_0_new.setText("");
            fill_date_1_new.setText("");
            fill_date_2_new.setText("");
            fill_date_3_new.setText("");
            fill_date_4_new.setText("");
            fill_date_5_new.setText("");
            fill_date_7_new.setText("");
            fill_date_21_new.setText("");
            fill_date_22_new.setText("");
            fill_date_24_new.setText("");
        }
    }


    @FXML
    public void changeStatus(ActionEvent actionEvent) {
        String id = search_tablo_id.getText();
        String positionString = search_tablo_pos.getText();

        Connection conn = new UtilDao().getConnPostgres();
        StatusDaoJdbc dao = new StatusDaoJdbc(conn);
        String idPosition = id + "-" + positionString;
        StatusDTO statusDTO = dao.getOne(idPosition);

        if (statusDTO != null) {
            long t1 = getLongFromDateString(fill_date_1_new.getText());
            long t2 = getLongFromDateString(fill_date_2_new.getText());
            long t3 = getLongFromDateString(fill_date_3_new.getText());
            long t4 = getLongFromDateString(fill_date_4_new.getText());
            long t5 = getLongFromDateString(fill_date_5_new.getText());
            long t7 = getLongFromDateString(fill_date_7_new.getText());
            long t21 = getLongFromDateString(fill_date_21_new.getText());
            long t22 = getLongFromDateString(fill_date_22_new.getText());
            long t24 = getLongFromDateString(fill_date_24_new.getText());
            int statusIndex = Integer.parseInt(fill_status_new.getText());
            int typeIndex = Integer.parseInt(fill_type_new.getText());

            if (t1 != 0 && t1 != statusDTO.getTime_1()) {
                statusDTO.setTime_1(t1);
                dao.updateT1(idPosition, t1);
            }
            if (t2 != 0 && t2 != statusDTO.getTime_2()) {
                statusDTO.setTime_2(t2);
                dao.updateT2(idPosition, t2);
            }
            if (t3 != 0 && t3 != statusDTO.getTime_3()) {
                statusDTO.setTime_3(t3);
                dao.updateT3(idPosition, t3);
            }
            if (t4 != 0 && t4 != statusDTO.getTime_4()) {
                statusDTO.setTime_4(t4);
                dao.updateT4(idPosition, t4);
            }
            if (t5 != 0 && t5 != statusDTO.getTime_5()) {
                statusDTO.setTime_5(t5);
                dao.updateT5(idPosition, t5);
            }
            if (t7 != 0 && t7 != statusDTO.getTime_7()) {
                statusDTO.setTime_7(t7);
                dao.updateT7(idPosition, t7);
            }
            if (t21 != 0 && t21 != statusDTO.getTime_21()) {
                statusDTO.setTime_21(t21);
                dao.updateT21(idPosition, t21);
            }
            if (t22 != 0 && t22 != statusDTO.getTime_22()) {
                statusDTO.setTime_22(t22);
                dao.updateT22(idPosition, t22);
            }
            if (t24 != 0 && t24 != statusDTO.getTime_24()) {
                statusDTO.setTime_24(t24);
                dao.updateT24(idPosition, t24);
            }
            if (statusDTO.getStatusIndex() != statusIndex) {
                statusDTO.setStatusIndex(statusIndex);
                dao.updateStatus(idPosition, statusIndex);
            }
            if (statusDTO.getTypeIndex() != typeIndex) {
                statusDTO.setTypeIndex(typeIndex);
                dao.updateType(idPosition, typeIndex);
            }

        }
    }

    private String getDateTimeString(long date) {
        if (date == 0L)
            return "";
        Timestamp t1 = new Timestamp(date);
        return t1.toString();
    }

    private long getLongFromDateString(String dateString) {
        if (dateString.equals(""))
            return 0L;
        String[] date1 = dateString.split("\\."); //new Timestamp(2020-07-01 00:00:00.245)
        int millis = (int) (Double.parseDouble("." + date1[1]) * 1000_000_000);
        String[] date2 = date1[0].split(" "); //new Timestamp(2020-07-01 00:00:00)
        String[] date = date2[0].split("-");
        String[] time = date2[1].split(":");

        int year = Integer.parseInt(date[0]);
        if (year == 1970)
            return 0L;
        LocalDateTime t1 = LocalDateTime.of(
                Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]),
                Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]), millis
        );
        return Timestamp.valueOf(t1).getTime();
    }
}
