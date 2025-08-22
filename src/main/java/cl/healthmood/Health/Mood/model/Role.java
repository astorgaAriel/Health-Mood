package cl.healthmood.Health.Mood.model;

public enum Role {
    ADMIN("Administrador"),
    CUSTOMER("Cliente");

    private final String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
