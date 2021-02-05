package artifact_new_manufplan.domain.model;


import java.sql.Timestamp;

public class Order { //orders
    private String idOrder; //orders.iddoc
    private Timestamp dateCreate; //orders.t_create
    private Timestamp dateToFactory; //orders.t_factory
    private Timestamp dateToShipment; //orders.t_end
    private String docNumber; //orders.docno
    private String ClientName; //orders.client_name
    private String ManagerName; //orders.manager_name

    public Order(String idOrder, Timestamp dateCreate, Timestamp dateToFactory, Timestamp dateToShipment,
                 String docNumber, String clientName, String managerName) {
        this.idOrder = idOrder;
        this.dateCreate = dateCreate;
        this.dateToFactory = dateToFactory;
        this.dateToShipment = dateToShipment;
        this.docNumber = docNumber;
        ClientName = clientName;
        ManagerName = managerName;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
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

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getManagerName() {
        return ManagerName;
    }

    public void setManagerName(String managerName) {
        ManagerName = managerName;
    }

    public Order getOrder() {
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrder='" + idOrder + '\'' +
                ", dateCreate=" + dateCreate +
                ", dateToFactory=" + dateToFactory +
                ", dateToShipment=" + dateToShipment +
                ", docNumber='" + docNumber + '\'' +
                ", ClientName='" + ClientName + '\'' +
                ", ManagerName='" + ManagerName + '\'' +
                '}';
    }
}
