package data;

import time.DateConverter;

import java.sql.*;

public final class WriterPostgreSql {
    private static String url = DataControl.getUrl();
    private static String user = DataControl.getUser();
    private static String password = DataControl.getPassword();

    public static boolean writeStatus(String kod, int statusIndex, long statusTime) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("org.postgresql.Driver");

            PreparedStatement psStatusOne = conn.prepareStatement(
                    "UPDATE statuses SET time_" + statusIndex + " = ? WHERE id = ?;");
            psStatusOne.setLong(1, statusTime);
            psStatusOne.setString(2, kod);
            psStatusOne.execute();

            PreparedStatement psStatusTwo = conn.prepareStatement(
                    "UPDATE statuses SET status_index = ? WHERE status_index < ? AND id = ?;");
            psStatusTwo.setInt(1, statusIndex);
            psStatusTwo.setInt(2, statusIndex);
            psStatusTwo.setString(3, kod);
            psStatusTwo.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true; //DataControl.writeTimeChange();
    }

    public static boolean writeType(String kod, int statusIndex, long statusTime, int type) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("org.postgresql.Driver");

            PreparedStatement psStatusOne = conn.prepareStatement(
//                    "UPDATE statuses SET type_index = ?, is_parsing = 1, time_" + statusIndex + " = ? WHERE kod = ? AND type_index = 0;");
                    "UPDATE statuses SET type_index = ?, is_parsing = 1, time_" + statusIndex + " = ? WHERE id = ? AND is_parsing = 0;");
            psStatusOne.setInt(1, type);
            psStatusOne.setLong(2, statusTime);
            psStatusOne.setString(3, kod);
            psStatusOne.execute();

            PreparedStatement psStatusTwo = conn.prepareStatement(
                    "UPDATE statuses SET status_index = ? WHERE status_index < ? AND id = ?;");
            psStatusTwo.setInt(1, statusIndex);
            psStatusTwo.setInt(2, statusIndex);
            psStatusTwo.setString(3, kod);
            psStatusTwo.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true; //DataControl.writeTimeChange();
    }

    public static boolean writeDesigner(String kod, int statusIndex, long statusTime, String designerName) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("org.postgresql.Driver");

            PreparedStatement preparedStatementDesigner = conn.prepareStatement(
                    "UPDATE statuses SET time_" + statusIndex + " = ?, designer_name = ? WHERE id = ?;");
            preparedStatementDesigner.setLong(1, statusTime);
            preparedStatementDesigner.setString(2, designerName);
            preparedStatementDesigner.setString(3, kod);
            preparedStatementDesigner.execute();

            PreparedStatement psStatusTwo = conn.prepareStatement(
                    "UPDATE statuses SET status_index = ? WHERE status_index < ? AND id = ?;");
            psStatusTwo.setInt(1, statusIndex);
            psStatusTwo.setInt(2, statusIndex);
            psStatusTwo.setString(3, kod);
            psStatusTwo.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true; //DataControl.writeTimeChange();
    }

    public static boolean writeAside(String kod, int type) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("org.postgresql.Driver");

            PreparedStatement psStatusOne = conn.prepareStatement(
                    "UPDATE statuses SET type_index = ? WHERE id = ?;");
            psStatusOne.setInt(1, type);
            psStatusOne.setString(2, kod);
            psStatusOne.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true; //DataControl.writeTimeChange();
    }

    public static boolean rewriteTypeKB(String kod) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            Class.forName("org.postgresql.Driver");

            PreparedStatement psStatusOne = conn.prepareStatement(
                    "UPDATE statuses SET type_index = 1, status_index = 2, time_2 = ?, time_7 = NULL WHERE id = ?;");
            psStatusOne.setLong(1, DateConverter.getNowDate());
            psStatusOne.setString(2, kod);
            psStatusOne.execute();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true; //DataControl.writeTimeChange();
    }

}
