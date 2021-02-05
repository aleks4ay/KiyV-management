package artifact_new_manufplan.domain.model;

import java.sql.Timestamp;

public class Invoice {
    private String id; //invoice_descr.id
    private String idInvoice; //invoice_descr.id_invoice = invoice.iddoc
    private String idOrder; //invoice.id_order
    private String idTmc; //invoice_descr.id_tmc
    private String docNomberInvoice; //invoice.docno
    private Timestamp timeInvoice; //invoice.time_invoice
    private int quantity = 0; //invoice_descr.quantity
    private double payment; //invoice_descr.payment

    public Invoice(String id) {
        this.id = id;
    }

    public Invoice(String id, String idInvoice, String idOrder, String idTmc, String docNomberInvoice,
                   Timestamp timeInvoice, int quantity, double payment) {
        this.id = id;
        this.idInvoice = idInvoice;
        this.idOrder = idOrder;
        this.idTmc = idTmc;
        this.docNomberInvoice = docNomberInvoice;
        this.timeInvoice = timeInvoice;
        this.quantity = quantity;
        this.payment = payment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdInvoice() {
        return idInvoice;
    }

    public void setIdInvoice(String idInvoice) {
        this.idInvoice = idInvoice;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getDocNomberInvoice() {
        return docNomberInvoice;
    }

    public void setDocNomberInvoice(String docNomberInvoice) {
        this.docNomberInvoice = docNomberInvoice;
    }

    public Timestamp getTimeInvoice() {
        return timeInvoice;
    }

    public void setTimeInvoice(Timestamp timeInvoice) {
        this.timeInvoice = timeInvoice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIdTmc() {
        return idTmc;
    }

    public void setIdTmc(String idTmc) {
        this.idTmc = idTmc;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public Invoice getInvoice() {
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;

        Invoice invoice = (Invoice) o;

        if (quantity != invoice.quantity) return false;
        if (id != null ? !id.equals(invoice.id) : invoice.id != null) return false;
        if (idInvoice != null ? !idInvoice.equals(invoice.idInvoice) : invoice.idInvoice != null) return false;
        if (idOrder != null ? !idOrder.equals(invoice.idOrder) : invoice.idOrder != null) return false;
        if (idTmc != null ? !idTmc.equals(invoice.idTmc) : invoice.idTmc != null) return false;
        if (docNomberInvoice != null ? !docNomberInvoice.equals(invoice.docNomberInvoice) : invoice.docNomberInvoice != null)
            return false;
        return timeInvoice != null ? timeInvoice.equals(invoice.timeInvoice) : invoice.timeInvoice == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idInvoice != null ? idInvoice.hashCode() : 0);
        result = 31 * result + (idOrder != null ? idOrder.hashCode() : 0);
        result = 31 * result + (idTmc != null ? idTmc.hashCode() : 0);
        result = 31 * result + (docNomberInvoice != null ? docNomberInvoice.hashCode() : 0);
        result = 31 * result + (timeInvoice != null ? timeInvoice.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id='" + id + '\'' +
                ", idInvoice='" + idInvoice + '\'' +
                ", idOrder='" + idOrder + '\'' +
                ", idTmc='" + idTmc + '\'' +
                ", docNomberInvoice='" + docNomberInvoice + '\'' +
                ", timeInvoice=" + timeInvoice +
                ", quantity=" + quantity +
                ", payment=" + payment +
                '}';
    }
}
