package me.zsturgess.banlogger;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class LoggerMethods {
    public static BanLogger plugin;
    
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
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log #" + ChatColor.RESET + ", Show the #'th page of help.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log see" + ChatColor.RESET + ", Show the last few days of logs.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log see last #" + ChatColor.RESET + ", Show the last # days of logs.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log see <user>" + ChatColor.RESET + ", Show the last few days of user's logs.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log see <user> #" + ChatColor.RESET + ", Show the last # days of user's logs.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log remove #" + ChatColor.RESET + ", Remove log entry #. (Does not undo the action)");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log post <msg>" + ChatColor.RESET + ", Post a message to all other administrators.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log read" + ChatColor.RESET + ", Show all active messages. (Messages are deleted after 30 days)");
        }else if(page==2){
            String stuff = "";
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RED + "/log <action> <user> <reason>" + ChatColor.RESET + ", Log & Do action for reason to user.");
            player.sendMessage(ChatColor.GOLD + ">> " + ChatColor.RESET + "Possible Actions: " + stuff);
        }
        return true;
    }
}
