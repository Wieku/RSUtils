package pl.redstonefun.rsutils.yaml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.file.YamlConfiguration;

import pl.redstonefun.rsutils.main.RSUtils;

public class YAML {
	
	public enum type{
		WARPS,
		PWARPS,
		HOMES,
		CONFIG,
		BANS,
		DATABASE;
	}
	
	private static HashMap<type, YamlConfiguration> configs = new HashMap<type, YamlConfiguration>();
	
	public static void loadConfigs(){
		for(type t : type.values()){
			loadConf(t);
		}
	}
	
	public static void reloadConfigs(){
		configs.clear();
		loadConfigs();
	}
	
	public static void reloadConfig(type type){
		configs.remove(type);
		loadConf(type);
	}
	
	public static void saveAll() throws Exception{
		for(type t : configs.keySet()){
			save(t);
		}
	}
	
	public static void save(type type) throws Exception{
		File file = new File(RSUtils.pluginFolder + type.toString().toLowerCase() + ".yml");
		configs.get(type).save(file);
	}
	
	public static void saveAndReload(type type) throws Exception{
		save(type);
		loadConf(type);
	}
	
	public static void saveAndReloadAll() throws Exception{
		saveAll();
		reloadConfigs();
	}
	
	protected static void loadConf(type t){
		File file = new File(RSUtils.pluginFolder + t.toString().toLowerCase() + ".yml");
		if(!file.exists()){
			if(t != type.CONFIG){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				RSUtils.instance.saveDefaultConfig();
			}
		}
		configs.put(t, YamlConfiguration.loadConfiguration(file));
		RSUtils.logger.info("Zaladowano plik yaml: " + t.toString().toLowerCase());
	}
	
	public static String getString(type t, String path){
		return configs.get(t).getString(path);
	}
	
	public static double getDouble(type t, String path){
		return configs.get(t).getDouble(path);
	}
	
	public static float getFloat(type t, String path){
		return (float)configs.get(t).getDouble(path);
	}
	
	public static int getInteger(type t, String path){
		return configs.get(t).getInt(path);
	}
	
	public static void set(type t, String path, Object value){
		YamlConfiguration con = configs.get(t);
		con.set(path, value);
		configs.put(t, con);
	}
	
	public static boolean isSet(type t, String path){
		return configs.get(t).isSet(path);
	}
	
	public static Set<String> getMainKeys(type t){
		return configs.get(t).getKeys(false);
	}
}
