package Classes;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import InterFaces.IFolder;
import classes.LinkedBased;
import interfaces.ILinkedList;
import interfaces.IQueue;

public class Mail {
	public void deleteEmails(ILinkedList mails) {
		
	}
	public void moveEmails(ILinkedList mails, IFolder des) {
		
	}
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
	
	
}
