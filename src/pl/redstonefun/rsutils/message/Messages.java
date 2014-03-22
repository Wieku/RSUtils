package pl.redstonefun.rsutils.message;

import org.bukkit.ChatColor;

public class Messages {

	public static String hasNoPermission = ChatColor.RED + "Nie masz dostępu do tej komendy!";
	public static String notEnoughArguments = ChatColor.RED + "Zła ilość argumentów!";
	public static String saveFileError = ChatColor.RED + "Problem z zapisem pliku!";
	public static String youAreTempBanned = "Zostałeś zbanowany do: %time za: %reason";
	public static String userOffline = ChatColor.RED + "Gracz jest niedostępny!";
	public static String userBanned = ChatColor.RED + "Gracz %user został zbanowany na stałe za: %reason";
	public static String userKicked = ChatColor.RED + "Gracz %user został wyrzucony z serwera za: %reason";
	public static String userTempBanned = ChatColor.RED + "Gracz %user został zbanowany do %time za: %reason";
	public static String unBanned = ChatColor.GREEN + "Gracz %user został odbanowany!";
	public static String teleport = ChatColor.GREEN + "Teleportowanie do: %loc. ";
	public static String msgToMe = "&7&o[ %user &7&o-> Ja ]&r ";
	public static String msgToHim = "&7&o[ Ja &7-> %user &7&o]&r ";
	public static String warpNotFound = ChatColor.RED + "Warp nie istnieje!";
}
