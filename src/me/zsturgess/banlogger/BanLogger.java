package me.zsturgess.banlogger;

import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class BanLogger extends JavaPlugin {
    public static BanLogger plugin;
    public static LoggerMethods lib;
    public final Logger logger = Logger.getLogger("Minecraft");
    
    @Override
    public void onDisable(){
        PluginDescriptionFile pdfFile = this.getDescription();
        this.saveConfig();
        this.logger.info(pdfFile.getName() + " was disabled.");
    }
    
    @Override
    public void onEnable(){
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(pdfFile.getName() + " " + pdfFile.getVersion() + " was enabled.");
    }
    
    @Override
    public boolean onCommand(CommandSender player, Command cmd, String commandLabel, String[] args){ 
        if(!(player instanceof Player)){
            player.sendMessage(ChatColor.GOLD + "[Ban Logs]" + ChatColor.RED + "You cannot use these commands from the console.");
            return false;
        }
        
        if(commandLabel.equalsIgnoreCase("log")){
            if(args.length == 0){ return lib.showHelp(player, 1); }
            
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("see")){  }
                if(args[0].equalsIgnoreCase("read")){  }
                if(args[0].equalsIgnoreCase("?") | args[0].equalsIgnoreCase("help")){ return lib.showHelp(player, 1); }
                if(lib.isInt(args[0])){ return lib.showHelp(player, Integer.parseInt(args[0])); }
                return false;
            }
            
            if(args[0].equalsIgnoreCase("post")){  }
            
            if(args[0].equalsIgnoreCase("remove")){  }
            
            if(args[0].equalsIgnoreCase("see")){
                if(args[1].equalsIgnoreCase("last")){  }
                else{  }
            }
            
            //log <action>
        }
        
        return false;
    }
}
