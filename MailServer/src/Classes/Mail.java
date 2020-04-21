package Classes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import javax.swing.JOptionPane;

import InterFaces.IFolder;
import classes.LinkedBased;
import interfaces.ILinkedList;
import interfaces.IQueue;

public class Mail {
	final static int lines = 4;
	
	/**
	 * 
	 * @param mails
	 */
	public void deleteEmails(ILinkedList mails) {
		Boolean trs = false;
		File firstmail = (File)mails.get(1);
		File folder = firstmail.getParentFile();
		File currentmail = folder.getParentFile();
		File trash = new File(currentmail, "Trash");
		if (folder.getName().compareTo("Trash") == 0) {
			trs = true;
		}
		int sz = mails.size();
		for(int i = 1; i <=sz; i++) {
			File src = (File) mails.get(i);
			File mailsfilesrc = new File(folder, "mailsfile.txt");
			File trashtime = new File (trash, "Trashfile.txt");
			if (!trs) {
				File dest = new File (trash, src.getName());
				Folder.copyFolder(src, dest);
				File mailsfiletrash = new File(trash, "mailsfile.txt");
				Folder.deleteData(mailsfilesrc, src.getName(), lines,true, mailsfiletrash);
				Long t = System.currentTimeMillis();
				String tim = Long.toString(t);
				try {
					FileWriter trashfilewriter = new FileWriter(trashtime, true);
					trashfilewriter.write(tim + ' ' + src.getName());
					trashfilewriter.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"System Files do not exist!","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}else {
				Folder.deleteData(mailsfilesrc, src.getName(), lines,false, null);
				deleteFromTrash(trash, src.getName(), false);
			}
			Folder.deleteFolder(src);
		}
	}
	
	/**
	 * 
	 * @param mails
	 * @param des
	 */
	public void moveEmails(ILinkedList mails, IFolder des) {
		if (des.get().getName().compareTo("Trash") == 0){
			deleteEmails(mails);
			return;
		}
		File firstmail = (File)mails.get(1);
		File srcfolder = firstmail.getParentFile();
		Boolean trs = false;
		if (srcfolder.getName().compareTo("Trash") == 0) {
			trs = true;
		}
		int sz = mails.size();
		for(int i = 1; i <=sz; i++) {
			File src = (File) mails.get(i);
			File dest = new File (des.get(), src.getName());
			Folder.copyFolder(src, dest);
			Folder.deleteFolder(src);
			File mailsfilesrc = new File(srcfolder, "mailsfile.txt");
			File mailsfiledest = new File(des.get(), "mailsfile.txt");
			Folder.deleteData(mailsfilesrc, src.getName(), lines, true, mailsfiledest);
			if (trs) {
				deleteFromTrash(srcfolder, src.getName(), false);
			}
		}
	}
	
	/**
	 * 
	 * @param Draft
	 * @param prior
	 * @param mails
	 * @param obj
	 * @param files
	 * @param body
	 * @param currentmail
	 * @return
	 */
	public static Boolean newEmail(Boolean Draft, int prior, IQueue mails, String obj ,ILinkedList files, String body, String currentmail) {
		Long time = System.currentTimeMillis();
		String mailname = Long.toString(time);
		IQueue mails2 = new LinkedBased();
		File mail = null;
		int sz = mails.size();
		if (Draft) {
			mail = new File("D:\\MailServerData\\" + currentmail + "\\Draft", mailname);
		} else {
			mail = new File("D:\\MailServerData\\" + currentmail + "\\Sent", mailname);
		}
		mkmail(mail);
		File file = new File (mail,"body.txt");
		try {
			FileWriter wrt = new FileWriter(file);
			wrt.write(body);
			wrt.close();
		} catch (IOException e) {
			return false;
		}
		try {
			file = new File (mail,"indexfile.txt");
			FileWriter wrt = new FileWriter(file, true);
			wrt.write(mailname + '\n' + currentmail + '\n');
			for (int i = 1; i <= sz; i++) {
				String temp = (String)mails.dequeue();
				wrt.write(temp + ' ');
				mails2.enqueue(temp);
			}
			wrt.write('\n' + Integer.toString(prior) + '\n' + obj);
			wrt.close();
		} catch (IOException e) {
			return false;
		}
		int atnum = files.size();
		for (int i = 1; i <= atnum; i++) {
			File attach = (File) files.get(i);
			file = new File (mail.getAbsolutePath() + "\\Attachments\\" + attach.getName());
			try {
				Files.copy(attach.toPath(), file.toPath());
			} catch (IOException e) {
				return false;
			}
		}
		if (!Draft) {
			for (int i = 1; i <= sz; i++) {
				String temp = (String)mails2.dequeue();
				mails.enqueue(temp);
				File dest = new File("D:\\MailServerData\\" + temp + "\\Inbox\\");
				File dest0 = new File(dest, mailname);
				Folder.copyFolder(mail, dest0);
				dest0 = new File(dest, "mailsfile.txt");
				try {
					FileWriter wrt = new FileWriter(dest0, true);
					wrt.write(mailname + '\n' + Integer.toString(prior) + '\n' + currentmail + '\n' + obj +'\n');
					wrt.close();
				} catch (IOException e) {
					return false;
				}	
			}
		}
		mail = mail.getParentFile();
		File sent = new File(mail, "mailsfile.txt");
		try {
			FileWriter wrt = new FileWriter(sent, true);
			wrt.write(mailname + '\n' + Integer.toString(prior) + '\n');
			if (!mails.isEmpty() && !Draft) {
				wrt.write((String) mails.dequeue());
			} else if (!mails2.isEmpty() && Draft) {
				wrt.write((String) mails2.dequeue());
			}
			wrt.write('\n' + obj +'\n');
			wrt.close();
		} catch (IOException e) {
			return false;
		}	
		return true;
	}
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	public static Boolean mkmail(File file) {
		file.mkdir();
		File files = new File(file, "Attachments");
		files.mkdir();
		files = new File (file, "body.txt");
		try {
			files.createNewFile();
		} catch (IOException e) {
			return false;
		}
		files = new File (file, "indexfile.txt");
		try {
			files.createNewFile();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param trash
	 * @param target
	 * @param auto
	 * @return
	 */
	public static Boolean deleteFromTrash(File trash, String target, Boolean auto) {
		final long days30 = (long) 2.592e9;
		File trashfile = new File(trash, "Trashfile.txt");
		File temp = new File(trash, "temp.txt");
		Long currenttime = System.currentTimeMillis();
		try {
			Scanner scan = new Scanner(trashfile);
			FileWriter writetemp = new FileWriter(temp, true);
			while(scan.hasNext()) {
				String input = scan.nextLine();
				String[] arr = input.split(" ");
				if (auto) {
					if (currenttime - Long.parseLong(arr[0]) > days30) {
						File d = new File(trash, arr[1]);
						File m = new File(trash, "mailsfile.txt");
						Folder.deleteFolder(d);
						Folder.deleteData(m, arr[1], lines, false, null);
					} else {
						writetemp.write(input);
					}
				} else {
					if (arr[1].compareTo(target) != 0) {
						writetemp.write(input);
					}
				}
			}
			scan.close();
			writetemp.close();
			String P = trashfile.getAbsolutePath();
			trashfile.delete();
			File dump = new File(P);
			temp.renameTo(dump);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"System Files do not exist!","Error",JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
}
