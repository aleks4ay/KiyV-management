package model.description;

public class DescriptionOrder implements Comparable<DescriptionOrder> {

    private long[] statusTimeList = new long[25];
    private Status status = null;

    private String kod;
    private int position = 0;
    private int amount = 0;
    private String descrFirst = "";
    private String descrSecond = "";
    private String designer = null;
    private int statusIndex = 0;
    private long statusTime = 0L;
    private String type = "";
    private int sizeA = 0;
    private int sizeB = 0;
    private int sizeC = 0;

    public DescriptionOrder(String kod, int position, int amount, String descrFirst, String descrSecond,
                            String designer, int statusIndex, long statusTime, String type, int sizeA, int sizeB, int sizeC) {
        for (int i = 0; i < 25; i++) {
            statusTimeList[i] = 0;
        }
        this.kod = kod;
        this.position = position;
        this.amount = amount;
        this.descrFirst = descrFirst;
        this.descrSecond = descrSecond;
        this.designer = designer;
        this.statusIndex = statusIndex;
        this.statusTime = statusTime;
        this.type = type;
        this.sizeA = sizeA;
        this.sizeB = sizeB;
        this.sizeC = sizeC;
        this.status = new Status(statusIndex, statusTime);
        this.statusTimeList[statusIndex] = statusTime;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
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

    public String getDescrFirst() {
        return descrFirst;
    }

    public void setDescrFirst(String descrFirst) {
        this.descrFirst = descrFirst;
    }

    public String getDescrSecond() {
        return descrSecond;
    }

    public void setDescrSecond(String descrSecond) {
        this.descrSecond = descrSecond;
    }

    public String getDesigner() {
        return designer;
    }

    public void setDesigner(String designer) {
        this.designer = designer;
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

    public int getSizeA() {
        return sizeA;
    }

    public void setSizeA(int sizeA) {
        this.sizeA = sizeA;
    }

    public int getSizeB() {
        return sizeB;
    }

    public void setSizeB(int sizeB) {
        this.sizeB = sizeB;
    }

    public int getSizeC() {
        return sizeC;
    }

    public void setSizeC(int sizeC) {
        this.sizeC = sizeC;
    }

    public void setStatus(int statusIndex) {
        this.status = new Status(statusIndex);
        this.statusTimeList[statusIndex] = this.status.getStatusTime();
    }

    public void setStatus(int statusIndex, long time) {
        this.status = new Status(statusIndex, time);
        this.statusTimeList[statusIndex] = time;
    }

    public long[] getStatusTimeList() {
        return statusTimeList;
    }

    public Status getStatus() {
        return status;
    }


    @Override
    public int compareTo(DescriptionOrder o) {
        if (this.kod.equals(o.kod)) {
            return 0;
        }
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DescriptionOrder other = (DescriptionOrder) obj;
        if (kod != other.kod)
            return false;
        return true;
    }

}
