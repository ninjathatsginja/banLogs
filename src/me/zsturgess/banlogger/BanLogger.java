package me.zsturgess.banlogger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    private ConfigAccessor msgs;
    private ConfigAccessor cache;
    
    @Override
    public void onDisable(){
        PluginDescriptionFile pdfFile = this.getDescription();
        this.saveConfig();
        msgs.saveConfig();
        cache.saveConfig();
        
        this.logger.info(pdfFile.getName() + " was disabled.");
    }
    
    @Override
    public void onEnable(){
        cache = new ConfigAccessor(this, "cache.yml");
        msgs = new ConfigAccessor(this, "msgs.yml");
        
        this.saveDefaultConfig();
        
        checkMsgs();
        
        PluginDescriptionFile pdfFile = this.getDescription();
        this.logger.info(pdfFile.getName() + " " + pdfFile.getVersion() + " was enabled.");
    }
    
    @Override
    public boolean onCommand(CommandSender player, Command cmd, String commandLabel, String[] args){ 
        if(!(player instanceof Player)){
            player.sendMessage(ChatColor.GOLD + "[BanLogger]" + ChatColor.RED + "You cannot use these commands from the console.");
            return false;
        }
        
        if(commandLabel.equalsIgnoreCase("log")){
            if(args.length == 0){ return showHelp(player, 1); }
            
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("see")){ return seelast(player, this.getConfig().getInt("cache-days")); }
                if(args[0].equalsIgnoreCase("read")){ return readMsgs(player);  }
                if(args[0].equalsIgnoreCase("?") | args[0].equalsIgnoreCase("help")){ return showHelp(player, 1); }
                if(isInt(args[0])){ return showHelp(player, Integer.parseInt(args[0])); }
                return false;
            }
            
            if(args[0].equalsIgnoreCase("?") && isInt(args[1])){ return showHelp(player, Integer.parseInt(args[1])); }
            
            if(args[0].equalsIgnoreCase("post")){ 
                String msg = "";
                Integer counter;
                for(counter=1; counter<args.length; counter++){
                    msg = msg + " " + args[counter];
                }
                return postMsg(player, msg); 
            }
            
            if(args[0].equalsIgnoreCase("remove")){  }
            
            if(args[0].equalsIgnoreCase("see")){
                if(args[1].equalsIgnoreCase("last") && isInt(args[2])){ return seelast(player, Integer.parseInt(args[2])); }
                else{  }
            }
            
            if(args.length >= 3){
                Boolean result = false;
                for(String key : this.getConfig().getConfigurationSection("actions").getKeys(false)){
                    if(key.equalsIgnoreCase(args[0])){
                        result = logEntry();
                    }
                }
                return result;
            }
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
        int maxPage = 2;
        if(page > maxPage | page < 0){
            player.sendMessage(ChatColor.GOLD + "[BanLogger] " + ChatColor.RED + "That page of help does not exist!" + ChatColor.RESET + " There are only " + maxPage + " pages of help.");
            return false;
        }
        player.sendMessage(ChatColor.GOLD + "[BanLogger]" + ChatColor.RESET + " Showing help page " + page + "/" + maxPage + ":");
        if(page == 1){
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log #" + ChatColor.RESET + ", Show the #'th page of help.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log see" + ChatColor.RESET + ", Show the last " + this.getConfig().getInt("cache-days") + " days of logs.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log see last #" + ChatColor.RESET + ", Show the last # days of logs.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log see <user>" + ChatColor.RESET + ", Show the last " + this.getConfig().getInt("cache-days") + " days of user's logs.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log see <user> #" + ChatColor.RESET + ", Show the last # days of user's logs.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log remove #" + ChatColor.RESET + ", Remove log entry #. (Does not undo the action)");
        }else if(page==2){
            String actions = "";
            for(String key : this.getConfig().getConfigurationSection("actions").getKeys(false)){
                if(actions.isEmpty()){
                    actions = key;
                }else{ 
                  actions = actions + ", " + key;
                }
            }
            
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log post <msg>" + ChatColor.RESET + ", Post a message to all other administrators.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log read" + ChatColor.RESET + ", Show all messages.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RESET + "(Messages deleted after " + this.getConfig().getInt("cache-days") + " days.)");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log <action> <user> <reason>" + ChatColor.RESET + ", Log action for reason to user.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RESET + "Possible Actions: " + actions);
        }
        return true;
    }
    
    public boolean logEntry(){
        return true; //todo
    }
    
    public void checkMsgs(){
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMDDHHmmss");
        
        for(String key : msgs.getConfig().getKeys(false)){
            try{
                Calendar cal = Calendar.getInstance();
                cal.setTime(format.parse(key));
                cal.add(Calendar.DATE, this.getConfig().getInt("cache-days"));
                if(today.after(cal.getTime())){
                    msgs.getConfig().set(key, null);
                }
            }catch(ParseException e){
                this.logger.info("An internal error occured whilst attempting to look through msgs.yml. The file may be corrupt. If this message keeps appearing, please delete your msgs.yml.");
            }
        }
    }
    
    public boolean postMsg(CommandSender sender, String msg){
        String author;
        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMDDHHmmss");
        
        if(!(sender instanceof Player)){
            author = "Console";
        }else{
            Player player = (Player) sender;
            author = player.getDisplayName();
        }
        
        try{
          msgs.getConfig().createSection(format.parse(today.toString()) + ".msg");
          msgs.getConfig().createSection(format.parse(today.toString()) + ".author");
          msgs.getConfig().set(format.parse(today.toString()) + ".msg", msg);
          msgs.getConfig().set(format.parse(today.toString()) + ".author", author);
          return true;
        }catch(ParseException e){
            sender.sendMessage(ChatColor.GOLD + "[BanLogger] " + ChatColor.RED + "An error occured whilst trying to post that message.");
            this.logger.info("Dafuq? Yeah, if this has happened, something's gone badly wrong. Why does java even make me catch this? iunno.");
            return false;
        }
    }
    
    public boolean readMsgs(CommandSender player){
        player.sendMessage(ChatColor.GOLD + "[BanLogger]" + ChatColor.RESET + " Showing all active messages:");
        for(String key : msgs.getConfig().getKeys(false)){
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM d, h:mma");
            try{
                player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + format.parse(key).toString() + " " + msgs.getConfig().getString(key + ".author") + ChatColor.RESET + msgs.getConfig().getString(key + ".msg"));
            }catch(ParseException e){
                player.sendMessage(ChatColor.GOLD + "[BanLogger] " + ChatColor.RED + "An error occured whilst trying to display some messages." + ChatColor.RESET + " Check the console for a more detailed information.");
                this.logger.info("An internal error occured whilst attempting to look through msgs.yml. The file may be corrupt. If this message keeps appearing, please delete your msgs.yml.");
            }
        }
        return true;
    }
    
    public boolean seelast(CommandSender player, Integer days){
        mysqlCall db = new mysqlCall(this);
        if(days > this.getConfig().getInt("cache-days")){
            ResultSet rs = db.select(days);
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM d, h:mma");
            try{
                player.sendMessage(ChatColor.GOLD + "[BanLogger]" + ChatColor.RESET + " Last " + days + " days of logs:");
                while(rs.next()){
                    player.sendMessage(ChatColor.GOLD + ">> #" + rs.getInt("ID") + ChatColor.RED + format.parse(rs.getDate("date").toString()) + ChatColor.RESET + rs.getString("admin") + ":" + rs.getString("action") + " " + rs.getString("user") + ChatColor.ITALIC + " (" + rs.getString("reason") + ")");
                }
            }catch(SQLException|ParseException e){
                
            }
        }else{
            
        }
        return true;
    }
}
