package model.description;

import javafx.scene.control.CheckBox;
import name.TypeName;

public class DescriptionFactory {

    private CheckBox[] checkBoxesStatus = new CheckBox[25];
    private long[] statusTimeList = new long[25];

    private String kod;
    private int bigNumber = 0;
    private int position = 0;
    private int amount = 0;
    private String descr = "";
    private String descrSize = "";
    private String designer = null;
    private int statusIndex = 0;
    private long statusTime = 0L;
    private String type = "";


    public DescriptionFactory(String kod, int bigNumber, int position, int amount, String descrFirst, String descrSecond,
                              String designer, int statusIndex, long statusTime, int typeIndex, int sizeA, int sizeB, int sizeC) {
        for (int i = 0; i < 25; i++) {
            statusTimeList[i] = 0;
        }
        this.kod = kod;
        this.bigNumber = bigNumber;
        this.position = position;
        this.amount = amount;
        this.descr = descrFirst + " " + descrSecond;
        this.descrSize = sizeA + " × " + sizeB + " × " + sizeC;
        this.designer = designer;
        this.statusIndex = statusIndex;
        this.statusTime = statusTime;
        this.type = TypeName.NAME[typeIndex];

        this.statusTimeList[statusIndex] = statusTime;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public int getBigNumber() {
        return bigNumber;
    }

    public void setBigNumber(int bigNumber) {
        this.bigNumber = bigNumber;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDescrSize() {
        return descrSize;
    }

    public void setDescrSize(String descrSize) {
        this.descrSize = descrSize;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {

        if (designer.equalsIgnoreCase("SERVER-KIY-V") || designer.equalsIgnoreCase("192.168.0.11")) {
            this.designer = "SERVER-KIY-V";
        }
        if (designer.equalsIgnoreCase("aser") || designer.equalsIgnoreCase("Sergienko")) {
            this.designer = "Сергиенко А.";
        }
        else if (designer.equalsIgnoreCase("KB1") || designer.equalsIgnoreCase("KB-pc")) {
            this.designer = "Мигашко А.";
        }
        else if (designer.equalsIgnoreCase("KEV") || designer.equalsIgnoreCase("KEV-pc")) {
            this.designer = "Дячок Д.";
        }
        else if (designer.equalsIgnoreCase("Mosienko")) {
            this.designer = "Мосиенко В.";
        }
        else {
            this.designer = designer;
        }
    }

    public int getStatusIndex() {
        return statusIndex;
    }

    public void setStatusIndex(int statusIndex) {
        this.statusIndex = statusIndex;
    }

    public long getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(long statusTime) {
        this.statusTime = statusTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long[] getStatusTimeList() {
        return statusTimeList;
    }

    public CheckBox[] getCheckBoxesStatus() {
        return checkBoxesStatus;
    }

}
