package fr.lesprogbretons.seawar.model;

public enum VictoryType {
    DESTROY ("destroying enemy"),
    TAKE ("taking 3 lighthouse");

    private final String name;

    VictoryType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
