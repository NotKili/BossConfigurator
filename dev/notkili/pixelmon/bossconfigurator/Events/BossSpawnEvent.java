package dev.notkili.pixelmon.bossconfigurator.Events;

import com.pixelmonmod.pixelmon.api.events.spawning.SpawnEvent;
import com.pixelmonmod.pixelmon.battles.attacks.Attack;
import com.pixelmonmod.pixelmon.battles.attacks.AttackBase;
import com.pixelmonmod.pixelmon.entities.pixelmon.EntityPixelmon;
import com.pixelmonmod.pixelmon.entities.pixelmon.stats.Moveset;
import dev.notkili.pixelmon.bossconfigurator.Config.Data;
import dev.notkili.pixelmon.bossconfigurator.Config.MainConfig;
import dev.notkili.pixelmon.bossconfigurator.Data.ActionType;
import dev.notkili.pixelmon.bossconfigurator.Data.Set;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BossSpawnEvent {
    private final Data data;
    private final MainConfig config;

    public BossSpawnEvent(Data data, MainConfig config) {
        this.data = data;
        this.config = config;
        LogManager.getLogger().info("Enabled BossSpawnEventListener");
    }

    @SubscribeEvent
    public void onBossSpawn(SpawnEvent event) {
        Entity ent;

        if ((ent = event.action.getOrCreateEntity()) instanceof EntityPixelmon) {
            EntityPixelmon pokemon = (EntityPixelmon) ent;

            // Check if spawn has no owner -> is wild
            if (!pokemon.hasOwner() || pokemon.getTrainer() == null) {
                if (pokemon.getBossMode().isBossPokemon()) {
                    String pokemonName = pokemon.getName();
                    String bossModeName = pokemon.getBossMode().name();
                    Random random = new Random();

                    /*
                    Every check returns the event if a successful replacement of moves took place, meaning the highest precedent case that applies and has legal moves will be used
                    No check matching = Boss will stay unchanged

                    Check if Sets per poke are enabled / contain the specific pokemon
                     */
                    if (config.areRandomSetsPerPokemonEnabled()) {
                        HashMap<String, ArrayList<Set>> map = data.getRandomMoveSetsPerPokemon();

                        if (map.containsKey(pokemonName)) {
                            if (addSetToMoveset(pokemon, random, map.get(pokemonName), ActionType.POKEMON)) {
                                return;
                            }
                        }
                    }

                    /*
                    Check if Random Moves per poke are enabled
                     */
                    if (config.areRandomMovesPerPokemonEnabled()) {
                        HashMap<String, ArrayList<String>> map = data.getRandomMovesPerPokemon();

                        if (map.containsKey(pokemonName)) {
                            if (addUpToFourRandomMovesFromListToMoveset(pokemon, map.get(pokemonName), ActionType.POKEMON)) {
                                return;
                            }
                        }
                    }

                    /*
                    Check if Random Movesets per BossType are enabled
                     */
                    if (config.areRandomSetsPerBossTypeEnabled()) {
                        HashMap<String, ArrayList<Set>> map = data.getRandomMoveSetsPerBossType();

                        if (map.containsKey(bossModeName)) {
                            if (addSetToMoveset(pokemon, random, map.get(bossModeName), ActionType.TYPE)) {
                                return;
                            }
                        }
                    }

                    /*
                    Check if Random Moves per BossType are enabled
                     */
                    if (config.areRandomMovesPerBossTypeEnabled()) {
                        HashMap<String, ArrayList<String>> map = data.getRandomMovesPerBossType();

                        if (map.containsKey(bossModeName)) {
                            if (addUpToFourRandomMovesFromListToMoveset(pokemon, map.get(bossModeName), ActionType.TYPE)) {
                                return;
                            }
                        }
                    }

                    /*
                    Check if Random MoveSets are enabled in general
                     */
                    if (config.areRandomSetsForAllEnabled()) {
                        if (addSetToMoveset(pokemon, random, data.getRandomMoveSetsForAllBosses(), ActionType.ALL)) {
                            return;
                        }
                    }

                    /*
                    Check if Random Moves are enabled in general
                     */
                    if (config.areRandomMovesForAllEnabled()) {
                        addUpToFourRandomMovesFromListToMoveset(pokemon, data.getRandomMovesForAllBosses(), ActionType.ALL);
                    }
                }
            }
        }
    }

    // Function returns true if the provided list's size is > 0 and if there was at least one move found that was legal
    private boolean addSetToMoveset(EntityPixelmon pokemon, Random random, ArrayList<Set> sets, ActionType type) {
        Logger logger = LogManager.getLogger();

        // Check if the provided List's size is bigger 0, returns false if not
        if (sets.size() > 0) {
            Set set = sets.get(random.nextInt(sets.size()));
            Moveset moveset = pokemon.getPokemonData().getMoveset();
            String moveName;
            int wrongNames = 0;
            moveset.clear();

            if ((moveName = set.getMove1()) != null) {
                if (isMoveValid(moveName)) {
                    moveset.add(new Attack(moveName));
                } else {
                    logger.warn("No attack with name '" + moveName + "' found, make sure it is spelled correctly (Error in: RandomMoveSets" + type + ".json)");
                    wrongNames++;
                }
            }

            if ((moveName = set.getMove2()) != null) {
                if (isMoveValid(moveName)) {
                    moveset.add(new Attack(moveName));
                } else {
                    logger.warn("No attack with name '" + moveName + "' found, make sure it is spelled correctly (Error in: RandomMoveSets" + type + ".json)");
                    wrongNames++;
                }
            }

            if ((moveName = set.getMove3()) != null) {
                if (isMoveValid(moveName)) {
                    moveset.add(new Attack(moveName));
                } else {
                    logger.warn("No attack with name '" + moveName + "' found, make sure it is spelled correctly (Error in: RandomMoveSets" + type + ".json)");
                    wrongNames++;
                }
            }

            if ((moveName = set.getMove4()) != null) {
                if (isMoveValid(moveName)) {
                    moveset.add(new Attack(moveName));
                } else {
                    logger.warn("No attack with name '" + moveName + "' found, make sure it is spelled correctly (Error in: RandomMoveSets" + type + ".json)");
                    wrongNames++;
                }
            }

            return wrongNames != 4;
        } else {
            return false;
        }
    }

    // Function returns true if the provided list's size is > 0 and if there was at least one move found that was legal
    private boolean addUpToFourRandomMovesFromListToMoveset(EntityPixelmon pokemon, ArrayList<String> moves, ActionType type) {
        // Checks if the provided list's size is bigger than 0, returns false if not
        if (moves.size() > 0) {
            Moveset moveset = pokemon.getPokemonData().getMoveset();
            int wrongMoves = 0;

            // Create a list of random numbers up until the size of the provided list
            // Add them to a list, shuffle them to get x (up to 4) random numbers which do not repeat
            List<Integer> randomNums = IntStream.range(0, moves.size()).boxed().collect(Collectors.toCollection(ArrayList::new));
            Collections.shuffle(randomNums);

            moveset.clear();
            for (int i = 0; i < (Math.min(randomNums.size(), 4)); i++) {
                String moveName = moves.get(randomNums.get(i));

                if (isMoveValid(moveName)) {
                    moveset.add(new Attack(moveName));
                } else {
                    LogManager.getLogger().warn("No attack with name '" + moveName + "' found, make sure it is spelled correctly (Error in RandomMoves" + type + ".json)");
                    wrongMoves++;
                }
            }

            return wrongMoves != 4;
        }
        return false;
    }

    private boolean isMoveValid(String name) {
        return AttackBase.getAttackBaseFromEnglishName(name).isPresent();
    }
}
