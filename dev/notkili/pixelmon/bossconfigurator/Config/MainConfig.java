package dev.notkili.pixelmon.bossconfigurator.Config;

import com.google.gson.annotations.Expose;

public class MainConfig {
    @Expose
    private boolean enableRandomMovesForAll;
    @Expose
    private boolean enableRandomSetsForAll;
    @Expose
    private boolean enableRandomMovesPerBossType;
    @Expose
    private boolean enableRandomSetsPerBossType;
    @Expose
    private boolean enableRandomMovesPerPokemon;
    @Expose
    private boolean enableRandomSetsPerPokemon;

    public MainConfig(boolean enableRandomMovesForAll, boolean enableRandomSetsForAll, boolean enableRandomMovesPerBossType, boolean enableRandomSetsPerBossType, boolean enableRandomMovesPerPokemon, boolean enableRandomSetsPerPokemon) {
        this.enableRandomMovesForAll = enableRandomMovesForAll;
        this.enableRandomSetsForAll = enableRandomSetsForAll;
        this.enableRandomMovesPerBossType = enableRandomMovesPerBossType;
        this.enableRandomSetsPerBossType = enableRandomSetsPerBossType;
        this.enableRandomMovesPerPokemon = enableRandomMovesPerPokemon;
        this.enableRandomSetsPerPokemon = enableRandomSetsPerPokemon;
    }

    public static MainConfig getDefaultValuedConfig() {
        return new MainConfig(true, true, true, true, true, true);
    }

    public boolean areRandomMovesForAllEnabled() {
        return enableRandomMovesForAll;
    }

    public boolean areRandomSetsForAllEnabled() {
        return enableRandomSetsForAll;
    }

    public boolean areRandomMovesPerBossTypeEnabled() {
        return enableRandomMovesPerBossType;
    }

    public boolean areRandomSetsPerBossTypeEnabled() {
        return enableRandomSetsPerBossType;
    }

    public boolean areRandomMovesPerPokemonEnabled() {
        return enableRandomMovesPerPokemon;
    }

    public boolean areRandomSetsPerPokemonEnabled() {
        return enableRandomSetsPerPokemon;
    }
}
