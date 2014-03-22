package pl.redstonefun.rsutils.message;

import org.bukkit.ChatColor;

public enum I18n {
	
	UCANT(ChatColor.RED + "Nie możesz tego zrobić!"),
	UHAVENTPERM(ChatColor.RED + "Nie masz dostępu do tego!"),
	UBANNED("Zostałeś zbanowany na stałe za: %"),
	USBANNED(ChatColor.RED + "Gracz % został zbanowany na stałe za: %"),
	SAY(ChatColor.LIGHT_PURPLE + "[Powiadomienie] %: %"),
	CLEAREDINV(ChatColor.GREEN + "Wyczyszczono ekwipunek!"),
	USKICK(ChatColor.RED + "Gracz % został wyrzucony z serwera za: " + ChatColor.RESET +" %"),
	UKICK("Zostałeś wyrzucony z serwera za: %"),
	UOFF(ChatColor.RED + "Gracz jest niedostępny!"),
	KICKDERROR("Problem z rozpoznaniem daty! Zgłoś to administracji, np. na stronie redstonefun.pl!"),
	JOIN(ChatColor.AQUA + "% dołączył do serwera!"),
	FJOIN(ChatColor.AQUA + "Witamy %" + ChatColor.AQUA + " pierwszy raz na serwerze!"),
	UTBANNED("Zostałeś zbanowany do: % za: %"),
	WRONGARGS(ChatColor.RED + "Zła ilość argumentów!"),
	INCORRECTARG(ChatColor.RED + "Niepoprawny argument!"),
	WODOESNTEXIST(ChatColor.RED + "Podany świat nie istnieje!"),
	UAFK(ChatColor.GRAY + "Gracz %" + ChatColor.GRAY + " jest AFK"),
	UNOAFK(ChatColor.GRAY + "Gracz %" + ChatColor.GRAY + " nie jest już AFK"),
	NEWHAT(ChatColor.GREEN + "Ciesz się nową czapką!");
	
	private String message;
	
	I18n(String message){
		this.message = message;
	}
	
	public StringEditor getE(){
		return new StringEditor(message);
	}
	
	public String get(){
		return message;
	}
	
}
