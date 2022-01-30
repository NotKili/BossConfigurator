package dev.notkili.pixelmon.bossconfigurator.Config;

import com.google.gson.annotations.Expose;
import dev.notkili.pixelmon.bossconfigurator.Data.Set;

import java.util.ArrayList;
import java.util.HashMap;

public class Data {
    @Expose
    private ArrayList<String> RandomMovesForAllBosses;
    @Expose
    private ArrayList<Set> RandomMoveSetsForAllBosses;
    @Expose
    private HashMap<String, ArrayList<String>> RandomMovesPerBossType;
    @Expose
    private HashMap<String, ArrayList<Set>> RandomMoveSetsPerBossType;
    @Expose
    private HashMap<String, ArrayList<String>> RandomMovesPerPokemon;
    @Expose
    private HashMap<String, ArrayList<Set>> RandomMoveSetsPerPokemon;

    public Data(ArrayList<String> randomMovesForAllBosses, ArrayList<Set> randomMoveSetsForAllBosses, HashMap<String, ArrayList<String>> randomMovesPerBossType, HashMap<String, ArrayList<Set>> randomMoveSetsPerBossType, HashMap<String, ArrayList<String>> randomMovesPerPokemon, HashMap<String, ArrayList<Set>> randomMoveSetsPerPokemon) {
        RandomMovesForAllBosses = randomMovesForAllBosses;
        RandomMoveSetsForAllBosses = randomMoveSetsForAllBosses;
        RandomMovesPerBossType = randomMovesPerBossType;
        RandomMoveSetsPerBossType = randomMoveSetsPerBossType;
        RandomMovesPerPokemon = randomMovesPerPokemon;
        RandomMoveSetsPerPokemon = randomMoveSetsPerPokemon;
    }

    public ArrayList<String> getRandomMovesForAllBosses() {
        return RandomMovesForAllBosses;
    }

    public ArrayList<Set> getRandomMoveSetsForAllBosses() {
        return RandomMoveSetsForAllBosses;
    }

    public HashMap<String, ArrayList<String>> getRandomMovesPerBossType() {
        return RandomMovesPerBossType;
    }

    public HashMap<String, ArrayList<Set>> getRandomMoveSetsPerBossType() {
        return RandomMoveSetsPerBossType;
    }

    public HashMap<String, ArrayList<String>> getRandomMovesPerPokemon() {
        return RandomMovesPerPokemon;
    }

    public HashMap<String, ArrayList<Set>> getRandomMoveSetsPerPokemon() {
        return RandomMoveSetsPerPokemon;
    }

    public static class Builder {
        private ArrayList<String> RandomMovesForAllBosses = null;
        private ArrayList<Set> RandomMoveSetsForAllBosses = null;
        private HashMap<String, ArrayList<String>> RandomMovesPerBossType = null;
        private HashMap<String, ArrayList<Set>> RandomMoveSetsPerBossType = null;
        private HashMap<String, ArrayList<String>> RandomMovesPerPokemon = null;
        private HashMap<String, ArrayList<Set>> RandomMoveSetsPerPokemon = null;

        public Builder() {
        }

        public Builder addRandomMovesForAllBosses(ArrayList<String> arr) {
            this.RandomMovesForAllBosses = arr == null ? new ArrayList<>() : arr;
            return this;
        }

        public Builder addRandomMoveSetsForAllBosses(ArrayList<Set> arr) {
            this.RandomMoveSetsForAllBosses = arr == null ? new ArrayList<>() : arr;
            return this;
        }

        public Builder addRandomMovesPerBossType(HashMap<String, ArrayList<String>> map) {
            this.RandomMovesPerBossType = map == null ? new HashMap<>() : map;
            return this;
        }

        public Builder addRandomMoveSetsPerBossType(HashMap<String, ArrayList<Set>> map) {
            this.RandomMoveSetsPerBossType = map == null ? new HashMap<>() : map;
            return this;
        }

        public Builder addRandomMovesPerPokemon(HashMap<String, ArrayList<String>> map) {
            this.RandomMovesPerPokemon = map == null ? new HashMap<>() : map;
            return this;
        }

        public Builder addRandomMoveSetsPerPokemon(HashMap<String, ArrayList<Set>> map) {
            this.RandomMoveSetsPerPokemon = map == null ? new HashMap<>() : map;
            return this;
        }

        public Data build() {
            return new Data(RandomMovesForAllBosses, RandomMoveSetsForAllBosses, RandomMovesPerBossType, RandomMoveSetsPerBossType, RandomMovesPerPokemon, RandomMoveSetsPerPokemon);
        }
    }

    public static Data getDefaultData() {
        return new Data(getDefaultRandomMovesForAllBosses(),
                getDefaultRandomSetsForAllBosses(),
                getDefaultRandomMovesPerBossType(),
                getDefaultRandomMoveSetsPerBossType(),
                getDefaultRandomMovesPerPokemon(),
                getDefaultRandomMoveSetsPerPokemon());
    }

    private static ArrayList<String> getDefaultRandomMovesForAllBosses() {
        ArrayList<String> temp = new ArrayList<>();

        temp.add("Splash");
        temp.add("Toxic");
        temp.add("Ember");

        return temp;
    }

    private static ArrayList<Set> getDefaultRandomSetsForAllBosses() {
        ArrayList<Set> temp = new ArrayList<>();

        temp.add(new Set("Splash", "Toxic", "Hyper Beam"));
        temp.add(new Set("Telekinesis", "Body Slam", "Shock Wave"));
        temp.add(new Set("Splash", "Splash", "Splash", "Splash"));

        return temp;
    }

    private static HashMap<String, ArrayList<String>> getDefaultRandomMovesPerBossType() {
        HashMap<String, ArrayList<String>> temp = new HashMap<>();
        ArrayList<String> temp2 = new ArrayList<>();
        ArrayList<String> temp3 = new ArrayList<>();

        temp2.add("Splash");
        temp2.add("Mimic");
        temp2.add("Flash");

        temp3.add("Curse");
        temp3.add("Bide");
        temp3.add("Tri Attack");
        temp3.add("Trick");

        temp.put("Common", temp2);
        temp.put("Legendary", temp3);

        return temp;
    }

    private static HashMap<String, ArrayList<Set>> getDefaultRandomMoveSetsPerBossType() {
        HashMap<String, ArrayList<Set>> temp = new HashMap<>();
        ArrayList<Set> temp2 = new ArrayList<>();
        ArrayList<Set> temp3 = new ArrayList<>();

        temp2.add(new Set("Frustration", "Flash", "Embargo"));
        temp2.add(new Set("Mimic", "Dream Eater", "Double-Edge", "Return"));

        temp3.add(new Set("Toxic", "Gravity", "Confusion"));
        temp3.add(new Set("Flash", "Skull Bash", "Shock Wave"));

        temp.put("Common", temp2);
        temp.put("Legendary", temp3);

        return temp;
    }

    private static HashMap<String, ArrayList<String>> getDefaultRandomMovesPerPokemon() {
        HashMap<String, ArrayList<String>> temp = new HashMap<>();
        ArrayList<String> temp2 = new ArrayList<>();
        ArrayList<String> temp3 = new ArrayList<>();

        temp2.add("Curse");
        temp2.add("Barrier");

        temp3.add("Magic Coat");
        temp3.add("Captivate");
        temp3.add("Foul Play");

        temp.put("Sableye", temp2);
        temp.put("Latios", temp3);

        return temp;
    }

    private static HashMap<String, ArrayList<Set>> getDefaultRandomMoveSetsPerPokemon() {
        HashMap<String, ArrayList<Set>> temp = new HashMap<>();
        ArrayList<Set> temp2 = new ArrayList<>();
        ArrayList<Set> temp3 = new ArrayList<>();

        temp2.add(new Set("Mega Punch", "Fire Punch", "Speed Swap"));
        temp2.add(new Set("Rest", "Taunt", "Endure"));

        temp3.add(new Set("Sunny Day", "Psych Up"));
        temp3.add(new Set("Rest", "Dream Eater", "Shock Wave", "Shadow Ball"));

        temp.put("Pidgey", temp2);
        temp.put("Diancie", temp3);

        return temp;
    }
}
