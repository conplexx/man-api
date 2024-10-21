package web2.man.enums;

public enum FinancialReportType {
    TIME_BASED("TIME_BASED"),
    EQUIPMENT_CATEGORY("EQUIPMENT_CATEGORY");
    private String value;

    FinancialReportType(String value) {
        this.value = value;
    }
}
