package web2.man.enums;

public enum ClientOrderState {
    OPEN("OPEN"),
    BUDGETED("BUDGETED"),
    REJECTED("REJECTED"),
    APPROVED("APPROVED"),
    REDIRECTED("REDIRECTED"),
    FIXED("FIXED"),
    PAYED("PAYED"),
    ENDED("ENDED");


    private String value;

    ClientOrderState(String value) {
        this.value = value;
    }
}




