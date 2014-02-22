package pl.redstonefun.rsutils.message;

import org.bukkit.ChatColor;

public class Messages {

	public static String hasNoPermission = ChatColor.RED + "Nie masz dostêpu do tej komendy!";
	public static String notEnoughArguments = ChatColor.RED + "Zbyt ma³o argumentów!";
	public static String saveFileError = ChatColor.RED + "Problem z zapisem pliku!";
	public static String youAreBanned = "Zosta³eœ zbanowany na sta³e za: %reason";
	public static String youAreTempBanned = "Zosta³eœ zbanowany do: %time za: %reason";
	public static String userBanned = ChatColor.RED + "Gracz %user zosta³ zbanowany na sta³e za: %reason";
	public static String userTempBanned = ChatColor.RED + "Gracz %user zosta³ zbanowany do %time za: %reason";
	public static String unBanned = ChatColor.GREEN + "Gracz %user zosta³ odbanowany!";
	public static String join = ChatColor.AQUA + "%user do³¹czy³ do serwera!";
}
