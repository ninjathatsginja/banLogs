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
        this.saveDefaultConfig();
    }
    
    @Override
    public boolean onCommand(CommandSender player, Command cmd, String commandLabel, String[] args){ 
        if(!(player instanceof Player)){
            player.sendMessage(ChatColor.GOLD + "[Ban Logs]" + ChatColor.RED + "You cannot use these commands from the console.");
            return false;
        }
        
        if(commandLabel.equalsIgnoreCase("log")){
            if(args.length == 0){ return showHelp(player, 1); }
            
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("see")){  }
                if(args[0].equalsIgnoreCase("read")){  }
                if(args[0].equalsIgnoreCase("?") | args[0].equalsIgnoreCase("help")){ return showHelp(player, 1); }
                if(isInt(args[0])){ return showHelp(player, Integer.parseInt(args[0])); }
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
    
    public boolean isInt(String str){
        try{
            Integer.parseInt(str);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    public boolean showHelp(CommandSender player, Integer page){
        player.sendMessage(ChatColor.GOLD + "[BanLogger]" + ChatColor.RESET + " Showing help page " + page + "/2:");
        if(page == 1){
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log #" + ChatColor.RESET + ", Show the #'th page of help.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log see" + ChatColor.RESET + ", Show the last " + this.getConfig().getInt("cache-days") + " days of logs.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log see last #" + ChatColor.RESET + ", Show the last # days of logs.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log see <user>" + ChatColor.RESET + ", Show the last " + this.getConfig().getInt("cache-days") + " days of user's logs.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log see <user> #" + ChatColor.RESET + ", Show the last # days of user's logs.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log remove #" + ChatColor.RESET + ", Remove log entry #. (Does not undo the action)");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log post <msg>" + ChatColor.RESET + ", Post a message to all other administrators.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log read" + ChatColor.RESET + ", Show all messages. (Messages deleted after " + this.getConfig().getInt("cache-days") + " days)");
        }else if(page==2){
            String actions = "";
            for(String key : this.getConfig().getConfigurationSection("actions").getKeys(false)){
                actions = actions + ", " + key;
            }
            
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log <action> <user> <reason>" + ChatColor.RESET + ", Log action for reason to user.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RESET + "Possible Actions: " + actions);
        }
        return true;
    }
}
