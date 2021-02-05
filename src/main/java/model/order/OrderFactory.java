package model.order;

import model.description.DescriptionFactory;
import model.description.DescriptionOrder;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderFactory implements Comparable<OrderFactory> {
    private List<DescriptionFactory> descriptions = new ArrayList<>();

    private String idDoc;
    private int bigNumber;
    private String docNumber = "";
    private String client = "";
    private String manager = "";
    private String designer = null;
//    private Timestamp dateCreate;// = 0L;
    private Timestamp dateToFactory;// = 0L;
    private Timestamp dateToShipment;// = 0L;
    private int minStatusIndex = 25;
    private int allPosition = 0;


    public OrderFactory(String idDoc, int bigNumber, String docNumber, String client, String manager,
                        Timestamp dateToFactory, Timestamp dateToShipment) {

        this.idDoc = idDoc;
        this.bigNumber = bigNumber;
        this.docNumber = docNumber;
        this.client = client;
        this.manager = manager;
//        this.dateCreate = dateCreate;
        this.dateToFactory = dateToFactory;
        if (dateToShipment != null) {
            this.dateToShipment = dateToShipment;
        }
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

    public List<DescriptionFactory> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<DescriptionFactory> descriptions) {
        this.descriptions = descriptions;
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
        if (designer != null) {
            return designer;
        }
        return "";
    }

    public void setDesigner(String designer) {
        this.designer = designer;
    }

    @Override
    public int compareTo(OrderFactory o) {
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
        OrderFactory other = (OrderFactory) obj;
        return this.idDoc.equals(other.idDoc);
    }
}