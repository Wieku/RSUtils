package pl.redstonefun.rsutils.message;

import org.bukkit.ChatColor;

public class Messages {

	public static String hasNoPermission = ChatColor.RED + "Nie masz dostêpu do tej komendy!";
	public static String notEnoughArguments = ChatColor.RED + "Z³a iloœæ argumentów!";
	public static String saveFileError = ChatColor.RED + "Problem z zapisem pliku!";
	public static String youAreBanned = "Zosta³eœ zbanowany na sta³e za: %reason";
	public static String youAreTempBanned = "Zosta³eœ zbanowany do: %time za: %reason";
	public static String userOffline = ChatColor.RED + "Gracz %user jest niedostêpny!";
	public static String userBanned = ChatColor.RED + "Gracz %user zosta³ zbanowany na sta³e za: %reason";
	public static String userTempBanned = ChatColor.RED + "Gracz %user zosta³ zbanowany do %time za: %reason";
	public static String unBanned = ChatColor.GREEN + "Gracz %user zosta³ odbanowany!";
	public static String join = ChatColor.AQUA + "%user do³¹czy³ do serwera!";
	public static String teleport = ChatColor.GREEN + "Teleportowanie do: %loc. ";
	public static String msgToMe = "&7&o[ %user &7&o-> Ja ]&r ";
	public static String msgToHim = "&7&o[ Ja &7-> %user &7&o]&r ";
	public static String warpNotFound = ChatColor.RED + "Warp nie istnieje!";
}
