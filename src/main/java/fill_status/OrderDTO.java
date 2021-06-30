package fill_status;

import java.sql.Timestamp;

public class OrderDTO {
    private String idDoc;
    private String client;
    private String manager;
    private String docNumber;
    private Timestamp dateCreate;
    private Timestamp dateToFactory;

    public OrderDTO(String idDoc, String client, String manager, String docNumber, Timestamp dateCreate, Timestamp dateToFactory) {
        this.idDoc = idDoc;
        this.client = client;
        this.manager = manager;
        this.docNumber = docNumber;
        this.dateCreate = dateCreate;
        this.dateToFactory = dateToFactory;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
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

    @Override
    public String toString() {
        return "OrderDTO{" +
                "idDoc='" + idDoc + '\'' +
                ", client='" + client + '\'' +
                ", manager='" + manager + '\'' +
                ", docNumber='" + docNumber + '\'' +
                ", dateCreate=" + dateCreate +
                ", dateToFactory=" + dateToFactory +
                '}';
    }
}
