package dev.notkili.pixelmon.bossconfigurator.Config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.notkili.pixelmon.bossconfigurator.Data.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Loader {
    private final Gson gson;
    private final String folderPath;
    private final String mainConfigName = "config.json";
    private final String dataFolderPath;

    private final String fileNameRandomMoveSetsForAllBosses = "RandomMoveSetsForAllBosses.json";
    private final String fileNameRandomMovesForAllBosses = "RandomMovesForAllBosses.json";
    private final String fileNameRandomMoveSetsPerBossType = "RandomMoveSetsPerBossType.json";
    private final String fileNameRandomMovesPerBossType = "RandomMovesPerBossType.json";
    private final String fileNameRandomMoveSetsPerPokemon = "RandomMoveSetsPerPokemon.json";
    private final String fileNameRandomMovesPerPokemon = "RandomMovesPerPokemon.json";

    private MainConfig config;
    private Data data;

    public Loader(String configPath) {
        this.gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        folderPath = configPath + File.separator + "BossConfigurator";
        dataFolderPath = folderPath + File.separator + "Data";
    }

    public boolean init() {
        Logger logger = LogManager.getLogger();
        /*
        Check if the \BossConfigurator folder exists.
        Create a new folder if it doesnt exist
         */
        File file = new File(folderPath);
        if (!file.exists()) {
            if (file.mkdir()) {
                logger.info("Successfully created folder " + folderPath);
                /*
                Create default main config and write it to file
                 */
                if (!createDefaultConfig()) {
                    return false;
                }

                /*
                Now initializing default data-set values per file (Default = all enabled)
                 */
                return createAllDataAndLoad();
            } else {
                logger.error("Folder " + folderPath + " could not be created");
                return false;
            }
        } else {
            /*
            Folder exists already, checking if main.config exists
            If it exists, attempt to load data
            If it doesn't exist, gen default values again
             */
            file = new File(folderPath + File.separator + mainConfigName);

            if (file.exists()) {
                return loadAllData();
            } else {
                if (!createDefaultConfig()) {
                    return false;
                }

                return createAllDataAndLoad();
            }
        }
    }

    // Function to create a default config & write it to file.
    private boolean createDefaultConfig() {
        try {
            config = MainConfig.getDefaultValuedConfig();

            File file = new File(folderPath + File.separator + mainConfigName);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(gson.toJson(config));
            writer.close();
            return true;
        } catch (IOException e) {
            LogManager.getLogger().warn(e.getMessage());
            return false;
        }
    }

    public boolean createAllDataAndLoad() {
        File file = new File(dataFolderPath);

        if (!file.isDirectory()) {
            if (file.mkdir()) {
                LogManager.getLogger().info("Successfully created folder " + dataFolderPath);
            }
        }

        data = Data.getDefaultData();
        return writeAllData();
    }

    /*
    Checks the default config for enabled features, then loads the feature (if its enabled)
     */
    public boolean loadAllData() {
        Data.Builder builder = new Data.Builder();
        File file;
        Logger logger = LogManager.getLogger();
        int count = 0;

        try {
            BufferedReader jsonReader = new BufferedReader(new FileReader(folderPath + File.separator + mainConfigName));
            config = gson.fromJson(jsonReader, MainConfig.class);

            file = new File(dataFolderPath + File.separator + fileNameRandomMovesForAllBosses);
            if (file.exists() && config.areRandomMovesForAllEnabled()) {
                builder.addRandomMovesForAllBosses(gson.fromJson(new BufferedReader(new FileReader(file)), new TypeToken<ArrayList<String>>(){}.getType()));
                count++;
            }

            file = new File(dataFolderPath + File.separator + fileNameRandomMoveSetsForAllBosses);
            if (file.exists() && config.areRandomSetsForAllEnabled()) {
                builder.addRandomMoveSetsForAllBosses(gson.fromJson(new BufferedReader(new FileReader(file)), new TypeToken<ArrayList<Set>>(){}.getType()));
                count++;
            }

            file = new File(dataFolderPath + File.separator + fileNameRandomMovesPerBossType);
            if (file.exists() && config.areRandomMovesPerBossTypeEnabled()) {
                builder.addRandomMovesPerBossType(gson.fromJson(new BufferedReader(new FileReader(file)), new TypeToken<HashMap<String, ArrayList<String>>>(){}.getType()));
                count++;
            }

            file = new File(dataFolderPath + File.separator + fileNameRandomMoveSetsPerBossType);
            if (file.exists() && config.areRandomSetsPerBossTypeEnabled()) {
                builder.addRandomMoveSetsPerBossType(gson.fromJson(new BufferedReader(new FileReader(file)), new TypeToken<HashMap<String, ArrayList<Set>>>(){}.getType()));
                count++;
            }

            file = new File(dataFolderPath + File.separator + fileNameRandomMovesPerPokemon);
            if (file.exists() && config.areRandomMovesPerPokemonEnabled()) {
                builder.addRandomMovesPerPokemon(gson.fromJson(new BufferedReader(new FileReader(file)), new TypeToken<HashMap<String, ArrayList<String>>>(){}.getType()));
                count++;
            }

            file = new File(dataFolderPath + File.separator + fileNameRandomMoveSetsPerPokemon);
            if (file.exists() && config.areRandomSetsPerPokemonEnabled()) {
                builder.addRandomMoveSetsPerPokemon(gson.fromJson(new BufferedReader(new FileReader(file)), new TypeToken<HashMap<String, ArrayList<Set>>>(){}.getType()));
                count++;
            }

            data = builder.build();
            logger.info("Successfully loaded " + count + " elements from the \\Data Folder");
            return true;

        } catch (IOException e) {
            logger.warn(e.getMessage());
            return false;
        }
    }

    public boolean writeAllData() {
        File file;
        String json;
        BufferedWriter writer;

        try {
            if (data.getRandomMoveSetsForAllBosses() != null) {
                file = new File(dataFolderPath + File.separator + fileNameRandomMoveSetsForAllBosses);
                json = gson.toJson(data.getRandomMoveSetsForAllBosses(), new TypeToken<ArrayList<Set>>(){}.getType());
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(json);
                writer.close();
            }

            if (data.getRandomMovesForAllBosses() != null) {
                file = new File(dataFolderPath + File.separator + fileNameRandomMovesForAllBosses);
                json = gson.toJson(data.getRandomMovesForAllBosses(), new TypeToken<ArrayList<String>>(){}.getType());
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(json);
                writer.close();
            }

            if (data.getRandomMoveSetsPerBossType() != null) {
                file = new File(dataFolderPath + File.separator + fileNameRandomMoveSetsPerBossType);
                json = gson.toJson(data.getRandomMoveSetsPerBossType(), new TypeToken<HashMap<String, ArrayList<Set>>>(){}.getType());
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(json);
                writer.close();
            }

            if (data.getRandomMovesPerBossType() != null) {
                file = new File(dataFolderPath + File.separator + fileNameRandomMovesPerBossType);
                json = gson.toJson(data.getRandomMovesPerBossType(), new TypeToken<HashMap<String, ArrayList<String>>>(){}.getType());
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(json);
                writer.close();
            }

            if (data.getRandomMoveSetsPerPokemon() != null) {
                file = new File(dataFolderPath + File.separator + fileNameRandomMoveSetsPerPokemon);
                json = gson.toJson(data.getRandomMoveSetsPerPokemon(), new TypeToken<HashMap<String, ArrayList<Set>>>(){}.getType());
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(json);
                writer.close();
            }

            if (data.getRandomMovesPerPokemon() != null) {
                file = new File(dataFolderPath + File.separator + fileNameRandomMovesPerPokemon);
                json = gson.toJson(data.getRandomMovesPerPokemon(), new TypeToken<HashMap<String, ArrayList<String>>>(){}.getType());
                writer = new BufferedWriter(new FileWriter(file));
                writer.write(json);
                writer.close();
            }
            return true;

        } catch (IOException e) {
            LogManager.getLogger().warn(e.getMessage());
            return false;
        }
    }

    public MainConfig getConfig() {
        return config;
    }
    public Data getData() {
        return data;
    }
}
