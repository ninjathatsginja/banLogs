package me.zsturgess.banlogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.bukkit.plugin.java.JavaPlugin;

public class mysqlCall {
    private JavaPlugin plugin;
    private ConfigAccessor cache;
    
    private String url;
    private String user;
    private String pass;
    
    private Connection con = null;
    private PreparedStatement pst;
    private ResultSet rs;
    
    public mysqlCall(JavaPlugin myplugin){
       plugin = myplugin;
       url = "jdbc:mysql://" + plugin.getConfig().getString("db.h") + "/" + plugin.getConfig().getString("db.d");
       user = plugin.getConfig().getString("db.u");
       pass = plugin.getConfig().getString("db.p");
       cache = new ConfigAccessor(plugin, "cache.yml");
    }
    
    private boolean connect(){
        if(con != null){ return true; }
        try{
          con = DriverManager.getConnection(url, user, pass);
          return true;
        }catch(SQLException e){ return false; }
    }
    
    public ResultSet select(String user, Integer days){
        if(!connect()){ return null; }
        
        SimpleDateFormat format = new SimpleDateFormat("YYYY-M-D HH:m:s");
        Date now = new Date();
        
        try{
            if(user==null){
                pst = con.prepareStatement("SELECT ID, closed FROM pad WHERE date > '?'"); 
            }else{
                pst = con.prepareStatement("SELECT ID, closed FROM pad WHERE user = '?' AND date > '?'"); 
            }
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(now);
            cal.add(Calendar.DATE, -days);
            
            pst.setString(1, user);
            pst.setString(2, format.parse(cal.getTime().toString()).toString());
            rs = pst.executeQuery();
            
            return rs;
        }catch(SQLException|ParseException ex){
            return null;
        }
    }
    
    public ResultSet select(Integer days){ return select(null, days); }
}
