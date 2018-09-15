package de.superklug.mygames.rankapi.entities;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class Rank {
    
    private @Getter @Setter String name;
    
    private @Getter @Setter String prefix;
    private @Getter @Setter String suffix;
    
    private @Getter @Setter List<String> inheritance;
    private @Getter @Setter List<String> bungeePermissions;
    private @Getter @Setter List<String> spigotPermissions;

    public Rank() {
    }

}
