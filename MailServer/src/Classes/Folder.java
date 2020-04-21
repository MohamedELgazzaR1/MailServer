package Classes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Folder {
	public static Boolean copyFolder(File src, File dest) {
		if (src.isDirectory()) {
			if (!dest.exists()) {
				dest.mkdir();
			}
			String[] files = src.list();
			for (String filename : files) {
				File srcFile = new File(src, filename);
                File destFile = new File(dest, filename);
                copyFolder(srcFile, destFile);
			}
		} else {
			try {
				Files.copy(src.toPath(), dest.toPath());
			} catch (IOException e) {
				return false;
			}
		}
		return true;
	}
	
	public static Boolean deleteFolder(File src) {
		if (src.isDirectory()) {
			String[] files = src.list();
			if (files.length == 0) {
				src.delete();
			} else {
				for (String filename : files) {
					File newfile = new File(src, filename);
					deleteFolder(newfile);
				}
			}
		} else {
			src.delete();
		}
		return true;
	}
}
