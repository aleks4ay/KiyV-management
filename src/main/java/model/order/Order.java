package model.order;

import model.description.DescriptionOrder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order implements Comparable<Order> {
    private List<DescriptionOrder> descriptions = new ArrayList<>();

    private String idDoc;
    private int bigNumber;
//    private String idDoc = "";
//    private String idClient = "";
//    private String idManager = "";
    private int durationTime;
    private String docNumber = "";
    private String docNumberManufacture = "";
    private String docNumberInvoice = "";
    private String client = "";
    private String manager = "";
    private String designer = "";
    private Timestamp dateCreate;// = 0L;
    private Timestamp dateToFactory;// = 0L;
    private Timestamp dateToShipment;// = 0L;
    private int minStatusIndex = 25;


//    private int unloaded = 0;
//    private int isClosed = 0;
    private int allPosition = 0;


    public Order(String idDoc, int bigNumber, int durationTime, String docNumber, String client, String manager, /*String designer,*/
                 Timestamp dateCreate, Timestamp dateToFactory, Timestamp dateToShipment, String docNumberManufacture, String docNumberInvoice) {

        this.idDoc = idDoc;
        this.bigNumber = bigNumber;
//        this.idDoc = idDoc;
//        this.idClient = idClient;
//        this.idManager = idManager;
        this.durationTime = durationTime;
        this.docNumber = docNumber;
        this.docNumberManufacture = docNumberManufacture;
        this.docNumberInvoice = docNumberInvoice;
        this.dateCreate = dateCreate;
        this.dateToFactory = dateToFactory;
        if (dateToShipment != null) {
            this.dateToShipment = dateToShipment;
        }
//        this.unloaded = unloaded;
//        this.isClosed = isClosed;
//        this.allPosition = ;
        this.client = client;
        this.manager = manager;
//        this.designer = designer;
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

    public List<DescriptionOrder> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<DescriptionOrder> descriptions) {
        this.descriptions = descriptions;
    }

    public int getBigNumber() {
        return bigNumber;
    }

    public void setBigNumber(int bigNumber) {
        this.bigNumber = bigNumber;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Timestamp getDateToFactory() {
        return dateToFactory;
    }

    public void setDateToFactory(Timestamp dateToFactory) {
        this.dateToFactory = dateToFactory;
    }

    public Timestamp getDateToShipment() {
        return dateToShipment;
    }

    public void setDateToShipment(Timestamp dateToShipment) {
        this.dateToShipment = dateToShipment;
    }

    public String getClient() {
        return client;
    }

    public String getManager() {
        return manager;
    }

    public int getMinStatusIndex() {
        return minStatusIndex;
    }

    public void setMinStatusIndex(int minStatusIndex) {
        this.minStatusIndex = minStatusIndex;
    }

    public int getAllPosition() {
        return allPosition;
    }

    public void setAllPosition(int allPosition) {
        this.allPosition = allPosition;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    public String getDocNumberManufacture() {
        return docNumberManufacture;
    }

    public String getDocNumberInvoice() {
        return docNumberInvoice;
    }

    @Override
    public int compareTo(Order o) {
        return this.bigNumber - o.bigNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        return idDoc.equals(other.idDoc);
    }
}