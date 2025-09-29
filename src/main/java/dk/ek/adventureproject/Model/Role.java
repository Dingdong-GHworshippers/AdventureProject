package dk.ek.adventureproject.Model;

public enum Role {
    MANAGER ("Manager"),
    EMPLOYEE ("Employee");

    private final String prettyprint;

    Role(String prettyprint) {
        this.prettyprint = prettyprint;
    }

    @Override
    public String toString() {
        return prettyprint;
    }

    public static Role fromString(String value) {
        for (Role role : Role.values()) {
            if (role.prettyprint.equalsIgnoreCase(value)) {
                return role;
            }
        }
        return null;
    }
}
