package dk.ek.adventureproject.Model;

public enum Role {
    MANAGER ("Manager"),
    EMPLOYEE ("Employee");

    private final String prettyprint;

    Role(String prettyprint) {
        this.prettyprint = prettyprint;
    }

    //Changes "LAPRIMA" to "La Prima"
    @Override
    public String toString() {
        return prettyprint;
    }

    public static Role fromString(String value) {
        for (Role carEquipment : Role.values()) {
            if (carEquipment.prettyprint.equalsIgnoreCase(value)) {
                return carEquipment;
            }
        }
        return null;
    }
}
