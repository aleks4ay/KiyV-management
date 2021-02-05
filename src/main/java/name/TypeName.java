package name;

public final class TypeName {
    public static final String[] NAME = {"новый", "КБ", "ЦЕХ", "Техн.", "АВС", "Прочее" };

    private String typeName;

    private TypeName(String statusName) {
        this.typeName = statusName;
    }

    public String getTypeName() {
        return typeName;
    }
}