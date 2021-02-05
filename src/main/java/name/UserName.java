package name;

import data.DataControl;
import time.DateConverter;

import javax.sound.midi.Sequence;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Date;
import java.util.*;
import java.util.regex.Pattern;

public final class UserName {

    public static final String[] ROLE = {"Менеджер", "Конструктор", "ОТК", "Производство", "Директор"/*, "Разработчик"*/};
    public static final String[] MANAGER = {"solomko", "holohorenko", "varenya"};
    public static final String[] DESIGNER = {"mosienko", "aser", "dyachok", "KB1"}; //Mosienko, Sergienko, Dyachok, Migashko
    public static final String[] OTK = {"radgabov", "kuzmin"};
    public static final String[] WORKER = {"zgurjak-pc", "stefanishin"};
    public static final String[] BOSS = {"vostrov", "vostrova"};
//    public static final String[] DEVELOPER = {"sergienko"};
//    public static final String[] USER = {"user", "пользователь", "гость"};

    private String userName;
    private String userNameTwo = "";
    private int role = -1;

    public static void main(String[] args) {
        writeTimeChange("test: ");
    }

    public UserName(/*String userName*/) {
        try {
            this.userName = InetAddress.getLocalHost().getHostName();
            if (System.getenv().containsKey("USERNAME")) {
                this.userNameTwo = System.getenv().get("USERNAME");
            }
            for (String manager : MANAGER) {
                if (manager.equalsIgnoreCase(userName))
                    role = 0;
            }
            for (String designer : DESIGNER) {
                if (designer.equalsIgnoreCase(userName))
                    role = 1;
            }
            for (String otk : OTK) {
                if (otk.equalsIgnoreCase(userName))
                    role = 2;
            }
            for (String worker : WORKER) {
                if (worker.equalsIgnoreCase(userName))
                    role = 3;
            }
            for (String boss : BOSS) {
                if (boss.equalsIgnoreCase(userName))
                    role = 4;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    public String getUserNameTwo() {
        return userNameTwo;
    }

    public String getRoleName() {
        if (role == -1) {
            return "Пользователь";
        }
        else {
            return ROLE[role];
        }
    }

    public int getRoleIndex() {
        return role;
    }


    public String getComputerName()
    {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME"))
            return env.get("COMPUTERNAME");
        else if (env.containsKey("HOSTNAME"))
            return env.get("HOSTNAME");
        else
            return "Unknown Computer";
    }

    // APPEND new time
    public static boolean writeTimeChange(String s){
        Set<String> listComputerName = new TreeSet<>();

        UserName userName = new UserName();
        String fileNameUser = DataControl.getFileUserName();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(fileNameUser)), "UTF8"))) {
            String tempString = null;
            while ( (tempString = br.readLine()) != null) {
                String[] strings1 = tempString.split(": ",2);
                String[] strings2 = strings1[1].split("!-!");
//                System.out.println("user: " + strings2[0] + " userTwo: "  + strings2[1] + " computer: " +  strings2[2]);
                listComputerName.add(strings2[0] + strings2[1] + strings2[2]);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            return false;
        }

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(new File(fileNameUser), true), "UTF8"))) {
            String s1 = String.valueOf(userName.getUserName());
            String s2 = String.valueOf(userName.getUserNameTwo());
            String s3 = String.valueOf(userName.getComputerName());
            String s4 = DateConverter.dateWithYearToString(DateConverter.getNowDate());
            String s5 = DateConverter.timeToString(DateConverter.getNowDate());
            if (listComputerName.add(s1+s2+s3)) {
                bw.write(s + ": " + s1 + "!-!" + s2 + "!-!" + s3 + "!-!  " + s4 + ", " + s5 + "\r\n");
                bw.flush();
            }
            else {
//                System.out.println("user: " + s1 + " userTwo: "  + s2 + " computer: " +  s3 + " already exist");
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            return false;
        }
        return true;

    }
}