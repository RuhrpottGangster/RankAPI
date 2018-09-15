package de.superklug.mygames.rankapi.bungeecord.managers;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import de.superklug.mygames.rankapi.bungeecord.RankAPI;
import lombok.Getter;
import org.bson.Document;

public class MongoManager {

    private final RankAPI module;
    
    private final String hostname, cDatabase, username, password;
    private final int port;

    private MongoClient client;
    private MongoDatabase database;

    private @Getter MongoCollection<Document> rankUsers;
    private @Getter MongoCollection<Document> rankGroups;

    public MongoManager(final RankAPI module) {
        this.module = module;
        
        this.hostname = module.getConfigManager().getConfiguration().getString("MongoDB.Hostname");
        this.port = module.getConfigManager().getConfiguration().getInt("MongoDB.Port");
        this.cDatabase = module.getConfigManager().getConfiguration().getString("MongoDB.Database");
        this.username = module.getConfigManager().getConfiguration().getString("MongoDB.Username");
        this.password = module.getConfigManager().getConfiguration().getString("MongoDB.Password");
        
        if(this.cDatabase == null && this.username == null && this.password == null) {
            connect();
        } else {
            connectWith();
        }
        
    }

    private void connect() {
        this.client = MongoClients.create(new ConnectionString(module.format("mongodb://{0}:{1}", hostname, String.valueOf(port))));
        this.database = this.client.getDatabase("ranks");

        this.rankUsers = this.database.getCollection("users");
        this.rankGroups = this.database.getCollection("groups");
    }

    private void connectWith() {
        this.client = MongoClients.create(new ConnectionString(module.format("mongodb://{0}:{1}@{2}:{3}/{4}", username, password, hostname, String.valueOf(port), database)));
        this.database = this.client.getDatabase("ranks");

        this.rankUsers = this.database.getCollection("users");
        this.rankGroups = this.database.getCollection("groups");
    }

}
