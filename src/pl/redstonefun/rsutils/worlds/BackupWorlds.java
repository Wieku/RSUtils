package pl.redstonefun.rsutils.worlds;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.World;

import pl.redstonefun.rsutils.main.RSUtils;

public class BackupWorlds implements Runnable{

	File fl = new File("backups");
	List<File> toPack = new ArrayList<File>();
	
	
	@Override
	public void run() {
		RSUtils.logger.warning("Started making backups!");
		Bukkit.broadcastMessage("              Zaczynam tworzenie backupa! Mogą być lagi!");
		
		fl.mkdirs();
		System.out.println(fl.getAbsolutePath());
		for(File file : fl.listFiles()){
			String name = file.getName().split("\\.")[0];
			long time = Long.parseLong(name);
			
			if(new Date().getTime() - time >= 604800000L){
				file.delete();
			}	
		}
		
		RSUtils.logger.info("Saving worlds...");
		for(World world : Bukkit.getWorlds()){
			world.save();
		}
		RSUtils.logger.info("Saved...");
		
		RSUtils.logger.info("Getting files for compression...");
		for(File file : new File(".").listFiles()){
			if(!file.getAbsolutePath().equals(fl.getAbsolutePath())){
				makeFilesList(file);
			}
		}
		
		try {
			File zipFile = new File(fl.getAbsolutePath() + File.separator + new Date().getTime() + ".zip");		
			zipFile.createNewFile();	
			ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
			byte[] buffer = new byte[8192];
		
			for(File file : toPack){
			
				ZipEntry zipEntry = new ZipEntry(file.getAbsolutePath().replace(new File("").getAbsolutePath()+File.separator, ""));
				zipStream.putNextEntry(zipEntry);
				FileInputStream in = new FileInputStream(file);
				RSUtils.logger.info("Kompresowanie: " + file.getName());
				int len;
				while ((len = in.read(buffer)) > 0) {
					zipStream.write(buffer, 0, len);
				}
 
				in.close();
				zipStream.closeEntry();
			}
		
			zipStream.close();
		} catch(Exception e){
			RSUtils.logger.severe("Problem ze stworzeniem backupa!");
			e.printStackTrace();
		}
		
		
	}
	
	private void makeFilesList(File start) {
		if(start.isFile()){
			toPack.add(start);
		} else {
			for(File fl : start.listFiles()){
				makeFilesList(fl);
			}
		}
	}

	public void pack(File path){
		
	}
	
}
