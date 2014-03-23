package pl.redstonefun.rsutils.listeners;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.bukkit.event.Listener;

import pl.redstonefun.rsutils.api.RSListener;
import pl.redstonefun.rsutils.main.RSUtils;

public class Listeners {

	public void register() throws Exception{
		URL filePath = getClass().getProtectionDomain().getCodeSource().getLocation();
		ZipFile file = new ZipFile(new File(filePath.toURI()));
		Enumeration<? extends ZipEntry> entries = file.entries();
		while(entries.hasMoreElements()){
			ZipEntry entry = entries.nextElement();
			String name = entry.getName();
			if(!entry.isDirectory()){
				if(name.split("\\.")[name.split("\\.").length -1].equals("class")){
					name = name.replace(".class", "").replace("/", ".");
					if(name.startsWith("pl.redstonefun")){
						Class<?> forCheck = Class.forName(name);
						for(Annotation an : forCheck.getAnnotations()){
							if(an instanceof RSListener){
								RSUtils.instance.manager.registerEvents((Listener) forCheck.newInstance(), RSUtils.instance);
								RSUtils.logger.info("Zaladowano Listener: " + forCheck.getSimpleName());
								break;
							}
						}
					}
				}
			}
			
		}	
		file.close();
	}
}