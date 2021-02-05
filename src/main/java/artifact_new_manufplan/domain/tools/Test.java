package artifact_new_manufplan.domain.tools;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Test {

    private static final String MIN_DEY_TO_SHIPMENT = "2020-03-15";
    private static final String MAX_DEY_TO_SHIPMENT = "2020-08-15";

    public static void main(String[] args) {
        String s = "2020-08-15";
        String[] date = s.split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, 0, 0);
        Timestamp timestamp = Timestamp.valueOf(localDateTime);
        System.out.println(localDateTime);
        System.out.println(timestamp.getDate());
    }
}
