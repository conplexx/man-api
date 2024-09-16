package web2.man.enums;

public enum ClientOrderAction {
    APPROVE("APPROVE"),
    REJECT("REJECT"),
    REDEEM_SERVICE("REDEEM_SERVICE"),
    PAY_SERVICE("PAY_SERVICE");

    private String value;

    ClientOrderAction(String value) {
        this.value = value;
    }
}
