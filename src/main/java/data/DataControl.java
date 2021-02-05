package data;

import time.DateConverter;

import java.io.*;

public final class DataControl {

    public static int i = 0;

//    private static String[] profile = {"manager", "designer", "ceh", "director", "developer"};
    private static String currentProfile = "";
//    private static final String URL = "jdbc:postgresql://localhost:5432/db_101_test?useSSL=false";
    private static final String URL = "jdbc:postgresql://192.168.0.11:5432/kiyv3?useSSL=false";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root8254";

    private static String fileName = "\\\\SERVER-KIY-V\\ptc\\KiyVtime.txt";
    private static String fileName1C = "\\\\SERVER-KIY-V\\ptc\\KiyVtime1C.txt";
    private static String fileUserName = "\\\\SERVER-KIY-V\\ptc\\KiyVuser.txt";

//    private static final String serverPath = "F:\\1C Base\\Copy250106";
//    private static final String dbfPath = "F:\\KiyV management\\DB_copy";



    public static String getURL() {
        return URL;
    }

    public static String getUser() {
        return USER;
    }

    public static String getPASSWORD() {
        return PASSWORD;
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

    /*// APPEND new time
    public static boolean writeTimeChange(){

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(new File(fileName), true), "UTF8"))) {
            String s = String.valueOf(DateConverter.getNowDate());
            bw.write( s + "\r\n");
            bw.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            return false;
        }
        return true;

    }*/

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

