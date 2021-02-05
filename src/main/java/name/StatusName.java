package name;

public final class StatusName {
    public static final String[] LONG_NAME = {"?", "Распределение\nцехом", "Поступил\n  в КБ", "Разработка", "Согласование\nс менеджером",
            "Разработка\nпосле\nсогласования", "Разработка\nокончена", "Запуск\nв цехе", "Лазер", "Гильотина",
            "Зачистка\nпосле\nлазера", "Гибка", "Сварка", "Полировка", "Покраска",
            "Сборка", "Ожидание\nкомплектующих", "Возобновление\nсборки", "Сборка\nразличных\nсистем", "Окончательная\nсборка",
            "ОТК", "Склад", "Отгрузка", "Отменен", "ВЫПОЛНЕН", "---","Технологич.\nоборудов." };

    private String statusName;

    private StatusName(String statusName) {
        this.statusName = statusName;
    }
    public String getStatusName() {
        return statusName;
    }
}