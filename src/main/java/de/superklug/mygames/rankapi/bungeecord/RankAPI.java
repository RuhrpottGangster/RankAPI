package de.superklug.mygames.rankapi.bungeecord;

import com.google.gson.Gson;
import de.superklug.mygames.rankapi.bungeecord.commands.RankCommand;
import de.superklug.mygames.rankapi.bungeecord.managers.BackendManager;
import de.superklug.mygames.rankapi.bungeecord.managers.ConfigManager;
import de.superklug.mygames.rankapi.bungeecord.managers.MongoManager;
import java.text.MessageFormat;
import lombok.Getter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class RankAPI extends Plugin {
    
    //BungeeCord
    
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
        
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new RankCommand(this));
    }
    
    //<editor-fold defaultstate="collapsed" desc="format">
    public String format(final String pattern, final Object... objects) {
        final String string = MessageFormat.format(pattern, objects);

        assert string != null;

        return string;
    }
    //</editor-fold>

}
