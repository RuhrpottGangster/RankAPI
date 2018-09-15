package de.superklug.mygames.rankapi.spigot.managers;

import de.superklug.mygames.rankapi.spigot.RankAPI;

public class ConfigManager {
    
    private final RankAPI module;

    public ConfigManager(final RankAPI module) {
        this.module = module;
        
        init();
    }
    
    private void init() {
        
        module.getConfig().options().copyDefaults(true);
        module.saveConfig();
        
    }

}
