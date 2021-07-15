package data;

import time.DateConverter;

import java.io.*;
import java.util.Properties;

public final class DataControl {

    public static int i = 0;

    private static String currentProfile = "";
    private static String url;
    private static String driverPostgres;
    private static String user;
    private static String password;

    private static String fileName;
    private static String fileName1C;
    private static String fileUserName;

    static {
        try (InputStream in = DataControl.class.getClassLoader().getResourceAsStream("persistence.properties")){
            Properties properties = new Properties();
            properties.load(in);

            fileName = properties.getProperty("fileName");
            fileName1C = properties.getProperty("fileName1C");
            fileUserName = properties.getProperty("fileUserName");
            driverPostgres = properties.getProperty("database.driverClassName");
            url = properties.getProperty("database.url");
            user = properties.getProperty("database.username");
            password = properties.getProperty("database.password");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // APPEND new time
    public static boolean writeTimeChange(String role){

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(new File(fileName), true), "UTF8"))) {
            long millis = DateConverter.getNowDate();
            String date = DateConverter.dateToString(millis) + "." + DateConverter.getYear(millis);
            String time = DateConverter.timeToString(millis);
            String s = date + "  " + time + " < " + role + " >";
            bw.write( s + "\r\n");
            bw.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public static String getUrl() {
        return url;
    }

    public static String getDriverPostgres() {
        return driverPostgres;
    }

    public static String getPassword() {
        return password;
    }

    public static String getFileName1C() {
        return fileName1C;
    }

    public static String getUser() {
        return user;
    }

    public static String getFileName() {
        return fileName;
    }

    public static String getFileUserName() {
        return fileUserName;
    }

    public static String getCurrentProfile() {
        return currentProfile;
    }

    public static void setCurrentProfile(String currentProfile) {
        DataControl.currentProfile = currentProfile;
    }

    public static boolean checkIntField(String amount) {
        if(amount.equals("")){
            System.out.println("Empty field!");
            return false;
        }
        try {
            Integer.parseInt(amount);
            return true;
        }catch (NumberFormatException e) {
            System.out.println("Exception: " + e);
            return false;
        }
    }

    public static String readTimeChange() {
        String line = "";
        String result = "";
        try (BufferedReader br = new BufferedReader(new InputStreamReader( new FileInputStream(new File(fileName)), "UTF8"))) {

            while ( (line = br.readLine()) != null ) {
                result = line;
            }
            return result;
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            return "";
        }
    }
}

