package de.superklug.mygames.rankapi.bungeecord.managers;

import de.superklug.mygames.rankapi.bungeecord.RankAPI;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import lombok.Getter;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigManager {
    
    private final RankAPI module;
    
    private final @Getter File file = new File("plugins/RankSystem", "config.yml");
    private @Getter Configuration configuration;

    public ConfigManager(final RankAPI module) {
        this.module = module;
        
        try {
            init();
        } catch (IOException ex) {}
    }
    
    private void init() throws IOException {
        
        if(!this.file.exists())
            Files.copy(module.getResourceAsStream("config.yml"), this.file.toPath());
        
        this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.file);
        
    }
    
    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.configuration, this.file);
        } catch (IOException ex) {}
    }

}
