package dk.ek.adventureproject.Model.enums;

public enum ProductType {
    FOOD ("Mad"),
    DRINK ("Drikkevarer"),
    MERCHANDISE ("Merch");

    private final String prettyprint;

    ProductType(String prettyprint) {
        this.prettyprint = prettyprint;
    }

    @Override
    public String toString() {
        return prettyprint;
    }

}
