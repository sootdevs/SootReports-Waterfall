package dev.codeerror.sootreports.util;

import dev.codeerror.sootreports.SootReports;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ConfigurationUtils {

    private final String configName;
    private static final Path dataFolder = SootReports.getInstance().getDataFolder().toPath();

    public ConfigurationUtils(String configName) {

        this.configName = configName + ".yml";

    }

    public Configuration getConfig() {

        try {

            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(dataFolder + "/" + configName));

        } catch (Exception e) {

            SootReports.getInstance().getLogger().severe("Unable to locate configuration file. Directory permissions may not allow read access or default configuration was not automatically copied.");
            return null;

        }

    }

    public void saveDefaultConfig() {

        File configFile = new File(dataFolder + "/" + configName);

        if (!dataFolder.toFile().exists()) { dataFolder.toFile().mkdirs(); }

        if (!configFile.exists()) {

            try {

                Files.copy(Objects.requireNonNull(getClass().getResourceAsStream("/config.yml")), Path.of(configFile.getPath()));
                SootReports.getInstance().getLogger().info("Created default configuration!");

            } catch (Exception e) {

                SootReports.getInstance().getLogger().severe("Unable to copy default configuration file to data directory. Jar may be corrupted or directory permissions may not allow write access.");

            }

        }

    }

}
