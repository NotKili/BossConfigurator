package dev.notkili.pixelmon.bossconfigurator.Data;

public enum ActionType {
    ALL("ForAll"),
    TYPE("PerBossType"),
    POKEMON("PerPokemon");

    private String name;

    ActionType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
