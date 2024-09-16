package web2.man.enums;

public enum EmployeeOrderFilter {
    TODAY("TODAY"),
    DATE_PERIOD("DATE_PERIOD"),
    ALL("ALL");

    private String value;

    EmployeeOrderFilter(String value) {
        this.value = value;
    }
}
