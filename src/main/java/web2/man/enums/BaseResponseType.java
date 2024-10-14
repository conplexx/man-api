package web2.man.enums;

public enum BaseResponseType {
    EMPTY("EMPTY"),
    ERROR("ERROR"),
    DATA("DATA");

    private String value;

    BaseResponseType(String value) {
        this.value = value;
    }
}
