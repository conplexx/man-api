package web2.man.enums;

public enum EmployeeOrderFilterType {
    TODAY("TODAY"),
    DATE_PERIOD("DATE_PERIOD"),
    ALL("ALL");

    private String value;

    EmployeeOrderFilterType(String value) {
        this.value = value;
    }
}
