package de.superklug.mygames.rankapi.spigot.managers;

import com.google.common.collect.Lists;
import com.mongodb.Block;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import de.superklug.mygames.rankapi.entities.Rank;
import de.superklug.mygames.rankapi.spigot.RankAPI;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import org.bson.Document;

public class BackendManager {
    
    private final RankAPI module;

    public BackendManager(final RankAPI module) {
        this.module = module;
    }
    
    //<editor-fold defaultstate="collapsed" desc="doesRankExists">
    /**
     * Checks if the rank is already existing
     *
     * @param name The name of the rank
     * @param consumer The consumer
     * @return A boolean- flag
     */
    public boolean doesRankExists(final String name, Consumer<Rank> consumer) {
        final AtomicBoolean result = new AtomicBoolean(false);
        
        module.getMongoManager().getRankGroups().find(Filters.eq("name", name)).first((Document t, Throwable thrwbl) -> {
            
            if(t != null) {
                result.set(true);
            }
            
        });
        
        return result.get();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="createRank">
    /**
     * Creates a new rank
     *
     * @param name The name of the rank
     * @param prefix The prefix (e.g. [PREFIX] {Player})
     * @param suffix The suffix (e.g. {Player} [SUFFIX])
     * @param inheritance The inheritances (e.g. Supporter, Youtuber, Premium)
     * @param bungeePermissions The permissions for bungeecord
     * @param spigotPermissions The permissions for spigot/bukkit
     */
    public void createRank(final String name, final String prefix, final String suffix, final List<String> inheritance, final List<String> bungeePermissions, final List<String> spigotPermissions) {
        if(doesRankExists(name, (Rank t) -> {})) return;
        
        module.getMongoManager().getRankGroups().find(Filters.eq("name", name)).first((Document t, Throwable thrwbl) -> {
            
            final Rank rank = new Rank();
            
            rank.setName(name);
            rank.setPrefix(prefix);
            rank.setSuffix(suffix);
            rank.setInheritance(inheritance);
            rank.setBungeePermissions(bungeePermissions);
            rank.setSpigotPermissions(spigotPermissions);
            
            t = module.getGson().fromJson(module.getGson().toJson(rank), Document.class);
            
            module.getMongoManager().getRankGroups().insertOne(t, (Void t1, Throwable thrwbl1) -> {
                
            });
            
        });
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="deleteRank">
    /**
     * Deletes a rank
     *
     * @param name The name of the rank
     */
    public void deleteRank(final String name) {
        if(!doesRankExists(name, (Rank t) -> {})) return;
        
        module.getMongoManager().getRankGroups().deleteOne(Filters.eq("name", name), (DeleteResult t, Throwable thrwbl) -> {
            
        });
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getRank">
    /**
     * Gets the rank out of the server database
     *
     * @param name The name of the rank
     * @param consumer The consumer
     */
    public void getRank(final String name, Consumer<Rank> consumer) {
        
        if(doesRankExists(name, (Rank rank) -> {
            consumer.accept(rank);
        }));
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="updateRank">
    /**
     * Updates a rank into the server database
     *
     * @param rank The rank
     * @param consumer The consumer
     */
    public void updateRank(final Rank rank, Consumer<Rank> consumer) {
        final Document document = module.getGson().fromJson(module.getGson().toJson(rank), Document.class);
        
        module.getMongoManager().getRankGroups().replaceOne(Filters.eq("name", rank.getName()), document, (UpdateResult t, Throwable thrwbl) -> {
            consumer.accept(rank);
        });
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="getPlayerRanks">
    /**
     * Gets all player ranks
     *
     * @param uuid The UUID of a player
     * @param consumer The consumer
     */
    public void getPlayerRanks(final String uuid, Consumer<List<Rank>> consumer) {
        final List<Rank> list = Lists.newArrayList();
        
        Block<Document> block = (Document document) -> {
            Rank rank = module.getGson().fromJson(document.toJson(), Rank.class);
            list.add(rank);
        };
        
        module.getMongoManager().getRankUsers().find(Filters.eq("uuid", uuid)).forEach(block, (Void t, Throwable thrwbl) -> {
            consumer.accept(list);
        });
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="setPlayerRank">
    /**
     * Sets a player only this rank
     *
     * @param uuid The UUID of a player
     * @param rankName The rank name
     * @param time The time
     */
    public void setPlayerRank(final String uuid, final String rankName, final long time) {
        
        module.getMongoManager().getRankUsers().deleteMany(Filters.eq("uuid", uuid), (DeleteResult t, Throwable thrwbl) -> {
            
        });
        
        final Document document = new Document("uuid", uuid).append("rank", rankName).append("time", time);
        
        module.getMongoManager().getRankUsers().insertOne(document, (Void t, Throwable thrwbl) -> {
            
        });
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="addPlayerRank">
    /**
     * Adds a rank to a player
     *
     * @param uuid The UUID of a player
     * @param rankName The rank name
     * @param time The time
     */
    public void addPlayerRank(final String uuid, final String rankName, final long time) {
        
        final Document document = new Document("uuid", uuid).append("rank", rankName).append("time", time);
        
        module.getMongoManager().getRankUsers().insertOne(document, (Void t, Throwable thrwbl) -> {
            
        });
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="removePlayerRank">
    /**
     * Removes a rank from a player
     *
     * @param uuid The UUID of a player
     * @param rankName The rank name
     */
    public void removePlayerRank(final String uuid, final String rankName) {
        
        final Document document = new Document("uuid", uuid).append("rank", rankName);
        
        module.getMongoManager().getRankUsers().deleteOne(document, (DeleteResult t, Throwable thrwbl) -> {
            
        });
        
    }
    //</editor-fold>

}
