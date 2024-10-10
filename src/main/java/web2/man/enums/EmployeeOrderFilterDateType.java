package web2.man.enums;

public enum EmployeeOrderFilterDateType {
    TODAY("TODAY"),
    DATE_PERIOD("DATE_PERIOD"),
    ALL("ALL");

    private String value;

    EmployeeOrderFilterDateType(String value) {
        this.value = value;
    }
}
