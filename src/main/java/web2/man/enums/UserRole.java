package web2.man.enums;

public enum UserRole {
    CLIENT("CLIENT"),
    EMPLOYEE("EMPLOYEE");

    private String value;

    UserRole(String value) {
        this.value = value;
    }
}
