package fill_status;

public class StatusDTO {
    private String id;
    private String idDoc;
    private long time_0;
    private long time_1;
    private long time_2;
    private long time_3;
    private long time_4;
    private long time_5;

    private long time_7;
    private long time_21;
    private long time_22;
    private long time_24;
    private int typeIndex;
    private int statusIndex;
    private int isTechno;
    private int isParsing;
    private String DesignerName;
    private String Description;

    public StatusDTO(String id, String idDoc, long time_0, long time_1, long time_2, long time_3, long time_4,
                     long time_5, long time_7, long time_21, long time_22, long time_24, int typeIndex,
                     int statusIndex, int isTechno, int isParsing, String designerName, String description) {
        this.id = id;
        this.idDoc = idDoc;
        this.time_0 = time_0;
        this.time_1 = time_1;
        this.time_2 = time_2;
        this.time_3 = time_3;
        this.time_4 = time_4;
        this.time_5 = time_5;
        this.time_7 = time_7;
        this.time_21 = time_21;
        this.time_22 = time_22;
        this.time_24 = time_24;
        this.typeIndex = typeIndex;
        this.statusIndex = statusIndex;
        this.isTechno = isTechno;
        this.isParsing = isParsing;
        DesignerName = designerName;
        Description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(String idDoc) {
        this.idDoc = idDoc;
    }

    public long getTime_0() {
        return time_0;
    }

    public void setTime_0(long time_0) {
        this.time_0 = time_0;
    }

    public long getTime_1() {
        return time_1;
    }

    public void setTime_1(long time_1) {
        this.time_1 = time_1;
    }

    public long getTime_2() {
        return time_2;
    }

    public void setTime_2(long time_2) {
        this.time_2 = time_2;
    }

    public long getTime_3() {
        return time_3;
    }

    public void setTime_3(long time_3) {
        this.time_3 = time_3;
    }

    public long getTime_4() {
        return time_4;
    }

    public void setTime_4(long time_4) {
        this.time_4 = time_4;
    }

    public long getTime_5() {
        return time_5;
    }

    public void setTime_5(long time_5) {
        this.time_5 = time_5;
    }

    public long getTime_7() {
        return time_7;
    }

    public void setTime_7(long time_7) {
        this.time_7 = time_7;
    }

    public long getTime_21() {
        return time_21;
    }

    public void setTime_21(long time_21) {
        this.time_21 = time_21;
    }

    public long getTime_22() {
        return time_22;
    }

    public void setTime_22(long time_22) {
        this.time_22 = time_22;
    }

    public long getTime_24() {
        return time_24;
    }

    public void setTime_24(long time_24) {
        this.time_24 = time_24;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
    }

    public int getStatusIndex() {
        return statusIndex;
    }

    public void setStatusIndex(int statusIndex) {
        this.statusIndex = statusIndex;
    }

    public int getIsTechno() {
        return isTechno;
    }

    public void setIsTechno(int isTechno) {
        this.isTechno = isTechno;
    }

    public int getIsParsing() {
        return isParsing;
    }

    public void setIsParsing(int isParsing) {
        this.isParsing = isParsing;
    }

    public String getDesignerName() {
        return DesignerName;
    }

    public void setDesignerName(String designerName) {
        DesignerName = designerName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }


    @Override
    public String toString() {
        return "StatusDTO{" +
                "id='" + id + '\'' +
                ", idDoc='" + idDoc + '\'' +
                ", time_1=" + time_1 +
                ", time_2=" + time_2 +
                ", time_3=" + time_3 +
                ", time_4=" + time_4 +
                ", time_5=" + time_5 +
                ", time_7=" + time_7 +
                ", time_21=" + time_21 +
                ", time_22=" + time_22 +
                ", time_24=" + time_24 +
                ", typeIndex=" + typeIndex +
                ", statusIndex=" + statusIndex +
                ", isTechno=" + isTechno +
                ", isParsing=" + isParsing +
                ", DesignerName='" + DesignerName + '\'' +
                ", Description='" + Description + '\'' +
//                ", DocNumber='" + DocNumber + '\'' +
                '}';
    }
}

