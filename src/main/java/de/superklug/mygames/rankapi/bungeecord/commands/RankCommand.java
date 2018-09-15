package de.superklug.mygames.rankapi.bungeecord.commands;

import com.google.common.collect.Lists;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import de.superklug.mygames.rankapi.bungeecord.RankAPI;
import de.superklug.mygames.rankapi.entities.Rank;
import de.superklug.mygames.rankapi.entities.mojang.UUIDFetcher;
import de.superklug.mygames.rankapi.utils.TimeUtil;
import java.util.Arrays;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class RankCommand extends Command {
    
    private final RankAPI module;
    
    private final String prefix;
    private final String unknownCommand;

    public RankCommand(final RankAPI module) {
        super("rank", "ranks.command.use.rank", "rang");
        
        this.module = module;
        
        this.prefix = ChatColor.translateAlternateColorCodes('&', module.getConfigManager().getConfiguration().getString("Messages.Prefix"));
        this.unknownCommand = ChatColor.translateAlternateColorCodes('&', module.getConfigManager().getConfiguration().getString("Messages.UnknownCommand"));
    }

    @Override
    public void execute(CommandSender sender, String[] arguments) {
        final String command = arguments[0].toLowerCase().trim();
        final String rank = arguments[1].trim();
        final String modus = arguments[2].toLowerCase().trim();
        
        switch(arguments.length) {
            
            case 0:
                sendHelpMap(sender);
                break;
                
            case 1:
                switch(command) {
                    
                    case "group":
                        sender.sendMessage(prefix + "Gebe einen §cRang an§8, §7um ihn zu verwalten§8!");
                        break;
                        
                    case "groups":
                        
                        //
                        
                        break;
                        
                    case "user":
                        sender.sendMessage(prefix + "Gebe einen §cSpieler an§8, §7um ihn zu verwalten§8!");
                        break;
                        
                    case "users":
                        
                        //
                        
                        break;
                        
                    default:
                        sendHelpMap(sender);
                        break;
                    
                }
                break;
                
            case 2:
                switch(command) {
                    
                    case "group":
                        sender.sendMessage(prefix + "§8§m---------------§r§8[ §cRanks §8]§m---------------");
                        sender.sendMessage(prefix + "§8/§7rank §7" + command + " " + rank + " §8» §c" + "create");
                        sender.sendMessage(prefix + "§8/§7rank §7" + command + " " + rank + " §8» §c" + "delete");
                        sender.sendMessage(prefix + "§8/§7rank §7" + command + " " + rank + " §8» §c" + "name" + " §8<§7Name§8>");
                        sender.sendMessage(prefix + "§8/§7rank §7" + command + " " + rank + " §8» §c" + "prefix" + " §8<§7Prefix§8>");
                        sender.sendMessage(prefix + "§8/§7rank §7" + command + " " + rank + " §8» §c" + "suffix" + " §8<§7Suffix§8>");
                        sender.sendMessage(prefix + "§8/§7rank §7" + command + " " + rank + " §8» §c" + "inheritance" + " §8<§7Rank§8>");
                        sender.sendMessage(prefix + "§8/§7rank §7" + command + " " + rank + " §8» §c" + "add" + " §8<§7Bungee§8/§7Spigot§8>");
                        sender.sendMessage(prefix + "§8/§7rank §7" + command + " " + rank + " §8» §c" + "remove" + " §8<§7Bungee§8/§7Spigot§8>");
                        sender.sendMessage(" ");
                        break;
                        
                    case "user":
                        sender.sendMessage(prefix + "§8§m---------------§r§8[ §cRanks §8]§m---------------");
                        sender.sendMessage(prefix + "§8/§7rank §7" + command + " " + arguments[1] + " §8» §c" + "delete");
                        sender.sendMessage(prefix + "§8/§7rank §7" + command + " " + arguments[1] + " §8» §c" + "set" + " §8<§7Rank§8> §8[§7Time§8]");
                        sender.sendMessage(prefix + "§8/§7rank §7" + command + " " + arguments[1] + " §8» §c" + "add" + " §8<§7Rank§8> §8[§7Time§8]");
                        sender.sendMessage(prefix + "§8/§7rank §7" + command + " " + arguments[1] + " §8» §c" + "remove" + " §8<§7Rank§8> §8[§7Time§8]");
                        sender.sendMessage(" ");
                        break;
                        
                    default:
                        sendHelpMap(sender);
                        break;
                    
                }
                break;
                
            case 3:
                switch(command) {
                    
                    case "group":
                        switch(modus) {
                            
                            case "create":
                                module.getBackendManager().createRank(arguments[1], "", "", Lists.newArrayList(), Lists.newArrayList(), Lists.newArrayList());
                                sender.sendMessage(prefix + "Du hast den Rang §c" + arguments[1] + "§7 erstellt§8! §8(§7Setzte noch Prefix etc.§8)");
                                break;
                                
                            case "delete":
                                module.getBackendManager().deleteRank(arguments[1]);
                                sender.sendMessage(prefix + "Du hast den Rang §c" + arguments[1] + "§7 gelöscht§8!");
                                break;
                                
                            case "name":
                                sender.sendMessage(prefix + "Du musst einen §cNamen §7eingeben§8!");
                                break;
                                
                            case "prefix":
                                sender.sendMessage(prefix + "Du musst einen §cPrefix §7eingeben§8!");
                                break;
                                
                            case "suffix":
                                sender.sendMessage(prefix + "Du musst einen §cSuffix §7eingeben§8!");
                                break;
                                
                            case "inheritance":
                                sender.sendMessage(prefix + "Du musst eine§8/§7mehrere §cGruppen §7eingeben§8!");
                                break;
                                
                            case "add":
                                sender.sendMessage(prefix + "Du musst eine neue §cPermission §7eingeben§8!");
                                break;
                                
                            case "remove":
                                sender.sendMessage(prefix + "Du musst eine §cPermission §7eingeben§8!");
                                break;
                            
                        }
                        break;
                        
                    case "user":
                        switch(modus) {
                            
                            case "delete":
                                module.getMongoManager().getRankUsers().deleteMany(Filters.eq("uuid", UUIDFetcher.getUUID(arguments[1]).toString()), (DeleteResult t, Throwable thrwbl) -> {
                                    sender.sendMessage(prefix + "Du hast den Spieler §c" + UUIDFetcher.getName(UUIDFetcher.getUUID(arguments[1])) + " §7gelöscht§8!");
                                });
                                break;

                            case "set":
                                sender.sendMessage(prefix + "Du musst einen §cRang §7eingeben§8!");
                                break;

                            case "add":
                                sender.sendMessage(prefix + "Du musst einen §cRang §7eingeben§8!");
                                break;

                            case "remove":
                                sender.sendMessage(prefix + "Du musst einen §cRang §7eingeben§8!");
                                break;
                            
                        }
                        break;
                        
                    default:
                        sendHelpMap(sender);
                        break;
                    
                }
                break;
                
            case 4:
                switch (command) {

                    case "group":
                        switch (modus) {

                            case "name":
                                module.getBackendManager().doesRankExists(rank, (Rank t) -> {
                                    t.setName(arguments[4]);
                                    
                                    module.getBackendManager().updateRank(t, (Rank t1) -> {
                                        
                                    });
                                    
                                });
                                break;

                            case "prefix":
                                module.getBackendManager().doesRankExists(rank, (Rank t) -> {
                                    t.setPrefix(arguments[4]);

                                    module.getBackendManager().updateRank(t, (Rank t1) -> {

                                    });

                                });
                                break;

                            case "suffix":
                                module.getBackendManager().doesRankExists(rank, (Rank t) -> {
                                    t.setSuffix(arguments[4]);

                                    module.getBackendManager().updateRank(t, (Rank t1) -> {

                                    });

                                });
                                break;

                            case "inheritance":
                                module.getBackendManager().doesRankExists(rank, (Rank t) -> {
                                    final List<String> list = t.getInheritance();
                                    
                                        list.addAll(Arrays.asList(arguments[4].split(",")));
                                    
                                    t.setInheritance(list);

                                    module.getBackendManager().updateRank(t, (Rank t1) -> {

                                    });

                                });
                                break;

                            case "add":
                                sender.sendMessage(prefix + "Gebe §cBungee§8/§cSpigot §7an und dann eine §cPermission§8!");
                                break;

                            case "remove":
                                sender.sendMessage(prefix + "Gebe §cBungee§8/§cSpigot §7an und dann eine §cPermission§8!");
                                break;

                        }
                        break;

                    case "user":
                        switch (modus) {

                            case "set":
                                module.getBackendManager().setPlayerRank(UUIDFetcher.getUUID(arguments[1]).toString(), arguments[4], -1);
                                sender.sendMessage(prefix + "Du hast §c" + UUIDFetcher.getName(UUIDFetcher.getUUID(arguments[1])) + " §7den Rang §c" + arguments[4] + " §7gesetzt§8!");
                                break;

                            case "add":
                                module.getBackendManager().addPlayerRank(UUIDFetcher.getUUID(arguments[1]).toString(), arguments[4], -1);
                                sender.sendMessage(prefix + "Du hast §c" + UUIDFetcher.getName(UUIDFetcher.getUUID(arguments[1])) + " §7den Rang §c" + arguments[4] + " §7hinzugefügt§8!");
                                break;

                            case "remove":
                                module.getBackendManager().removePlayerRank(UUIDFetcher.getUUID(arguments[1]).toString(), arguments[4]);
                                sender.sendMessage(prefix + "Du hast §c" + UUIDFetcher.getName(UUIDFetcher.getUUID(arguments[1])) + " §7den Rang §c" + arguments[4] + " §7entfernt§8!");
                                break;

                        }
                        break;

                    default:
                        sendHelpMap(sender);
                        break;

                }
                break;
                
            case 5:
                switch(command) {
                    
                    case "group":
                        switch(modus) {
                            
                            case "add":
                                switch(arguments[4].toLowerCase()) {
                                  
                                    case "bungee": case "bungeecord":
                                        module.getBackendManager().doesRankExists(rank, (Rank t) -> {
                                            final List<String> list = t.getBungeePermissions();
                                            
                                                list.add(arguments[4].toLowerCase());
                                                
                                            t.setBungeePermissions(list);
                                                
                                            module.getBackendManager().updateRank(t, (Rank t1) -> {
                                                sender.sendMessage(prefix + "Du hast dem Rang §c" + rank + " §7das Bungeecord-Recht §c" + arguments[4].toLowerCase() + " §7gegeben§8!");
                                            });
                                            
                                        });
                                        break;
                                        
                                    case "spigot": case "bukkit":
                                        module.getBackendManager().doesRankExists(rank, (Rank t) -> {
                                            final List<String> list = t.getSpigotPermissions();

                                                list.add(arguments[4].toLowerCase());

                                            t.setSpigotPermissions(list);

                                            module.getBackendManager().updateRank(t, (Rank t1) -> {
                                                sender.sendMessage(prefix + "Du hast dem Rang §c" + rank + " §7das Spigot-Recht §c" + arguments[4].toLowerCase() + " §7gegeben§8!");
                                            });

                                        });
                                        break;
                                    
                                }
                                break;
                                
                            case "remove":
                                switch (arguments[4].toLowerCase()) {
                                    
                                    case "bungee": case "bungeecord":
                                        module.getBackendManager().doesRankExists(rank, (Rank t) -> {
                                            final List<String> list = t.getBungeePermissions();
                                            
                                            if(list.contains(arguments[4].toLowerCase()))
                                                list.remove(arguments[4].toLowerCase());

                                            t.setBungeePermissions(list);

                                            module.getBackendManager().updateRank(t, (Rank t1) -> {
                                                sender.sendMessage(prefix + "Du hast dem Rang §c" + rank + " §7das Bungeecord-Recht §c" + arguments[4].toLowerCase() + " §7entfernt§8!");
                                            });

                                        });
                                        break;

                                    case "spigot": case "bukkit":
                                        module.getBackendManager().doesRankExists(rank, (Rank t) -> {
                                            final List<String> list = t.getSpigotPermissions();
                                            
                                            if(list.contains(arguments[4].toLowerCase()))
                                                list.remove(arguments[4].toLowerCase());

                                            t.setSpigotPermissions(list);

                                            module.getBackendManager().updateRank(t, (Rank t1) -> {
                                                sender.sendMessage(prefix + "Du hast dem Rang §c" + rank + " §7das Spigot-Recht §c" + arguments[4].toLowerCase() + " §7entfernt§8!");
                                            });

                                        });
                                        break;
                                    
                                }
                                break;
                            
                        }
                        break;
                        
                    case "user":
                        switch(modus) {
                            
                            case "set":
                                module.getBackendManager().setPlayerRank(UUIDFetcher.getUUID(arguments[1]).toString(), arguments[4], TimeUtil.getTimeInMillis(arguments[5]));
                                sender.sendMessage(prefix + "Du hast §c" + UUIDFetcher.getName(UUIDFetcher.getUUID(arguments[1])) + " §7den Rang §c" + arguments[4] + " §8[§c" + arguments[5].toLowerCase() + "§8] §7gesetzt§8!");
                                break;
                                
                            case "add":
                                module.getBackendManager().addPlayerRank(UUIDFetcher.getUUID(arguments[1]).toString(), arguments[4], TimeUtil.getTimeInMillis(arguments[5]));
                                sender.sendMessage(prefix + "Du hast §c" + UUIDFetcher.getName(UUIDFetcher.getUUID(arguments[1])) + " §7den Rang §c" + arguments[4] + " §8[§c" + arguments[5].toLowerCase() + "§8] §7hinzugefügt§8!");
                                break;
                            
                        }
                        break;
                        
                    default:
                        sendHelpMap(sender);
                        break;
                    
                }
                break;
            
            default:
                sendHelpMap(sender);
                break;
            
        }
        
    }
    
    private void sendHelpMap(final CommandSender sender) {
        sender.sendMessage(prefix + "§8§m---------------§r§8[ §cRanks §8]§m---------------");
        sender.sendMessage(prefix + "§8/§7rank §c" + "group" + " §8<§7Group§8>");
        sender.sendMessage(prefix + "§8/§7rank §c" + "groups" + "");
        sender.sendMessage(prefix + "§8/§7rank §c" + "user" + " §8<§7Player§8>");
        sender.sendMessage(prefix + "§8/§7rank §c" + "users" + "");
        sender.sendMessage(" ");
    }

}