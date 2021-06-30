package fill_status;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Test {
    public static void main(String[] args) {
        getLongFromDateString("2020-07-01 00:00:00.45");
        getLongFromDateString("2020-07-01 00:00:00.4");
    }

    private static long getLongFromDateString(String dateString) {
        String[] date1 = dateString.split("\\."); //new Timestamp(2020-07-01 00:00:00.45)
        String[] date2 = date1[0].split(" "); //new Timestamp(2020-07-01 00:00:00)
        String[] date = date2[0].split("-");
        String[] time = date2[1].split(":");

        int year = Integer.parseInt(date[0]);
        if (year == 1970) {
            return 0L;
        }
        int millis = (int) (Double.parseDouble("." + date1[1]) * 1000_000_000);
        System.out.println(millis);
        LocalDateTime t1 = LocalDateTime.of(
                Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2]),
                Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]), millis
        );
        System.out.println("dateString = " + dateString);
        System.out.println("LocalDateTime = " + t1);
        System.out.println("Timestamp.valueOf(t1).getTime() = " + Timestamp.valueOf(t1).getTime());
        return Timestamp.valueOf(t1).getTime();
    }
}
