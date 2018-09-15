package de.superklug.mygames.rankapi.spigot;

import com.google.gson.Gson;
import de.superklug.mygames.rankapi.spigot.managers.BackendManager;
import de.superklug.mygames.rankapi.spigot.managers.ConfigManager;
import de.superklug.mygames.rankapi.spigot.managers.MongoManager;
import java.text.MessageFormat;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class RankAPI extends JavaPlugin {
    
    //Spigot
    
    private @Getter Gson gson;
    
    private @Getter ConfigManager configManager;
    private @Getter MongoManager mongoManager;
    private @Getter BackendManager backendManager;
    
    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        
    }

    private void init() {
        this.gson = new Gson();
        
        this.configManager = new ConfigManager(this);
        this.mongoManager = new MongoManager(this);
        this.backendManager = new BackendManager(this);
    }
    
    //<editor-fold defaultstate="collapsed" desc="format">
    public String format(final String pattern, final Object... objects) {
        final String string = MessageFormat.format(pattern, objects);

        assert string != null;

        return string;
    }
    //</editor-fold>

}
