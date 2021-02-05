package model.order;

import model.description.DescriptionParsing;
import time.DateConverter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderParsing {

    private List<DescriptionParsing> descriptions;
    private String idDoc;
    private int bigNumber;
    private String docNumber = "";
    private String client = "";
    private String manager = "";
    private int allPosition;
    private String descriptionFirstPosition;
//    private long timeToFactory;// = 0L;
    private long timeToParsing;// = 0L;
    private int minStatusIndex = 25;

    public OrderParsing(String idDoc, int bigNumber, String docNumber, String client, String manager, String descriptionFirstPosition, /*Timestamp dateToFactory,*/ long timeToParsing) {
        descriptions = new ArrayList<>();
        this.idDoc = idDoc;
        this.bigNumber = bigNumber;
        this.docNumber = docNumber;
        this.client = client;
        this.manager = manager;
        this.allPosition = 0;
        this.descriptionFirstPosition = descriptionFirstPosition;
//        this.timeToFactory = dateToFactory.getTime();
        this.timeToParsing = timeToParsing;
    }

    public String getStringNumber() {
        String result = String.valueOf(bigNumber).substring(2);
        if (result.substring(0,1).equals("0")) {
            return result.substring(1);
        }
        else {
            return result;
        }
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public int getBigNumber() {
        return bigNumber;
    }

    public void setBigNumber(int bigNumber) {
        this.bigNumber = bigNumber;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public int getAllPosition() {
        return allPosition;
    }

    public void setAllPosition(int allPosition) {
        this.allPosition = allPosition;
    }

    public String getDescriptionFirstPosition() {
        return descriptionFirstPosition;
    }

    public void setDescriptionFirstPosition(String descriptionFirstPosition) {
        this.descriptionFirstPosition = descriptionFirstPosition;
    }

    public long getTimeToParsing() {
        return timeToParsing;
    }

    public String getTimeToParsingString() {
        String time = DateConverter.timeToString(timeToParsing);
        String date = DateConverter.dateWithYearToString(timeToParsing);
        if (time.equalsIgnoreCase("10:00")) {
            return date;
        }
        else {
            return date + "   " + time;
        }
    }

//    public void setTimeToFactory(long timeToFactory) {
//        this.timeToFactory = timeToFactory;
//    }

    public List<DescriptionParsing> getDescriptions() {
        return descriptions;
    }

    public void addDescription(DescriptionParsing newDescription) {
        descriptions.add(newDescription);
        this.allPosition ++ ;
    }

    public int getMinStatusIndex() {
        return minStatusIndex;
    }

    public void setMinStatusIndex(int minStatusIndex) {
        this.minStatusIndex = minStatusIndex;
    }
}