package web2.man.enums;

public enum EmployeeOrderAction {
    PRODUCE_BUDGET("PRODUCE_BUDGET"),
    DO_MAINTANANCE("DO_MAINTANANCE"),
    END_ORDER("END_ORDER");

    private String value;

    EmployeeOrderAction(String value) {
        this.value = value;
    }
}
