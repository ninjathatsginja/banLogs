package me.zsturgess.banlogger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class LoggerMethods {
    public static BanLogger plugin;
    public static LoggerMethods lib;
    
    //Utility Methods
    public static boolean isInt(String str){
        try{
            Integer.parseInt(str);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    
    //Show Help
    public static boolean showHelp(CommandSender player, Integer page){
        player.sendMessage(ChatColor.GOLD + "[BanLogger]" + ChatColor.RESET + " Showing help page " + page + "/y:");
        if(page == 1){
            player.sendMessage(ChatColor.GOLD + ">>" + ChatColor.RED + "/log #" + ChatColor.RESET + ", Show the #'th page of help.");
            player.sendMessage(ChatColor.GOLD + ">>" + ChatColor.RED + "/log see" + ChatColor.RESET + ", Show the last " + plugin.getConfig().getString("cache-days") + " days of logs.");
            player.sendMessage(ChatColor.GOLD + ">>" + ChatColor.RED + "/log see last #" + ChatColor.RESET + ", Show the last # days of logs.");
            player.sendMessage(ChatColor.GOLD + ">>" + ChatColor.RED + "/log see <user>" + ChatColor.RESET + ", Show the last " + plugin.getConfig().getString("cache-days") + " days of user's logs.");
            player.sendMessage(ChatColor.GOLD + ">>" + ChatColor.RED + "/log see <user> #" + ChatColor.RESET + ", Show the last # days of user's logs.");
            player.sendMessage(ChatColor.GOLD + ">>" + ChatColor.RED + "/log post <msg>" + ChatColor.RESET + ", Post a message to all other administrators.");
            player.sendMessage(ChatColor.GOLD + ">>" + ChatColor.RED + "/log read" + ChatColor.RESET + ", Show all active messages. (Messages are deleted after 30 days)");
            player.sendMessage(ChatColor.GOLD + ">>" + ChatColor.RED + "/log #" + ChatColor.RESET + ", Show the #'th page of help.");
            player.sendMessage(ChatColor.GOLD + ">>" + ChatColor.RED + "/log #" + ChatColor.RESET + ", Show the #'th page of help.");
            player.sendMessage(ChatColor.GOLD + ">>" + ChatColor.RED + "/log #" + ChatColor.RESET + ", Show the #'th page of help.");
        }
        return true;
    }
}
