package web2.man.enums;

public enum OrderState {
    OPEN("OPEN"),
    BUDGETED("BUDGETED"),
    REJECTED("REJECTED"),
    APPROVED("APPROVED"),
    REDIRECTED("REDIRECTED"),
    FIXED("FIXED"),
    PAYED("PAYED"),
    FINALIZED("FINALIZED");


    private String value;

    OrderState(String value) {
        this.value = value;
    }
}




