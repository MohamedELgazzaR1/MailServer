package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Interfaces.IApp;
import Interfaces.IContact;
import Interfaces.IFolder;
import Interfaces.IMail;
import classes.LinkedBased;
import classes.SinglyLinkedList;
import interfaces.ILinkedList;
import interfaces.IQueue;

public class App implements IApp{

	final int lines = Mail.getlines();
	
	@Override
	public boolean signin(String email, String password) {

		Scanner myreader;
		try {
			int linecounter=0;
			File myobj =new File("D:\\MailServerData\\database.txt");
			
			myreader = new Scanner(myobj);
			while(myreader.hasNextLine()) {
				String data=myreader.next();
				if(linecounter%2==0) {
					if(email.compareTo(data)==0) {
						if(password.compareTo(myreader.next())==0) {
						myreader.close();
						return true;
						}
					}	
				}
				linecounter++;
			}
			myreader.close();
		} catch (FileNotFoundException e) {
			return false;
		}
		return false;
	}

	@Override
	public boolean signup(IContact contact) {
		
		//even emails odd passwords
		int linecounter=0;
		
		try {
			File start =new File("D:\\MailServerData");
			start.mkdir();
			File empty = new File(start,"database.txt");
			empty.createNewFile();
		}catch (Exception e) {
			
		}
		
		try {
			File myobj =new File("D:\\MailServerData\\database.txt");
			Scanner myreader= new Scanner(myobj);
			while(myreader.hasNextLine()) {
				String data=myreader.nextLine();
				if(linecounter%2==0) {
					if(contact.getemail().compareTo(data)==0) {
						myreader.close();
						return false;
					}	
				}
				linecounter++;
			}
			myreader.close();
			try {
				FileWriter wrt = new FileWriter(myobj, true);
				if(linecounter==0) {
					wrt.write(contact.getemail()+"\n"+contact.getpassword());
				}else {
					wrt.write("\n"+contact.getemail()+"\n"+contact.getpassword());
				}
				wrt.close();
			} catch (IOException e) {
				return false;
			}
			if (!contact.newUser()) {
				return false;
			}
		} catch (FileNotFoundException e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean compose(IMail email) {
		Long time = System.currentTimeMillis();
		String mailname = Long.toString(time);
		IQueue mails2 = new LinkedBased();
		File mail = null;
		int sz = email.getMails().size();
		if (email.getDraft()) {
			mail = new File("D:\\MailServerData\\" + email.getCurrentMail() + "\\Mail Folders\\Draft", mailname);
		} else {
			mail = new File("D:\\MailServerData\\" + email.getCurrentMail() + "\\Mail Folders\\Sent", mailname);
		}
		Mail.mkmail(mail);
		File file = new File (mail,"indexfile.txt");
		try {
			FileWriter wrt = new FileWriter(file, true);
			wrt.write(mailname + '\n' + email.getCurrentMail() + '\n');
			for (int i = 1; i <= sz; i++) {
				String temp = (String)email.getMails().dequeue();
				wrt.write(temp + ' ');
				mails2.enqueue(temp);
			}
			wrt.write('\n' + Integer.toString(email.getPriority()) + '\n' + email.getSubject() + '\n');
			if (email.getfiles() != null) {
				int atnum = email.getfiles().size();
				for (int i = 1; i <= atnum; i++) {
					File attach = (File) email.getfiles().get(i);
					file = new File (mail.getAbsolutePath() + "\\Attachments\\" + attach.getName());
					wrt.write(attach.getName() + ' ');
					try {
						Files.copy(attach.toPath(), file.toPath());
					} catch (IOException e) {
						wrt.close();
						return false;
					}
				}
			}
			wrt.write('\n' + email.getBody());
			wrt.close();
		} catch (IOException e) {
			return false;
		}
		if (!email.getDraft()) {
			for (int i = 1; i <= sz; i++) {
				String temp = (String)mails2.dequeue();
				email.getMails().enqueue(temp);
				File dest = new File("D:\\MailServerData\\" + temp + "\\Mail Folders\\Inbox\\");
				File dest0 = new File(dest, mailname);
				Folder.copyFolder(mail, dest0);
				dest0 = new File(dest, "mailsfile.txt");
				try {
					FileWriter wrt = new FileWriter(dest0, true);
					wrt.write(mailname + '\n' + Integer.toString(email.getPriority()) + '\n' + email.getCurrentMail() + '\n' + temp + '\n' + email.getSubject() +'\n');
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
			wrt.write(mailname + '\n' + Integer.toString(email.getPriority()) + '\n' + email.getCurrentMail() + '\n' );
			if (!email.getMails().isEmpty() && !email.getDraft()) {
				wrt.write((String) email.getMails().dequeue());
			} else if (!mails2.isEmpty() && email.getDraft()) {
				wrt.write((String) mails2.dequeue());
			}
			wrt.write('\n' + email.getSubject() +'\n');
			wrt.close();
		} catch (IOException e) {
			return false;
		}	
		return true;
	}

	@Override
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
					trashfilewriter.write(tim + ' ' + src.getName() + ' ' + folder.getName() + '\n');
					trashfilewriter.close();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"System Files do not exist!","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}else {
				Folder.deleteData(mailsfilesrc, src.getName(), lines,false, null);
				Mail.deleteFromTrash(trash, src.getName(), false);
			}
			Folder.deleteFolder(src);
		}
	}

	@Override
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
				Mail.deleteFromTrash(srcfolder, src.getName(), false);
			}
		}
		
	}
	
	public Boolean restoreEmails(ILinkedList mails) {
		File firstmail = (File)mails.get(1);
		File trash = firstmail.getParentFile();
		File trashfile = new File(trash, "Trashfile.txt");
		File currentFolder = trash.getParentFile();
		int sz = mails.size();
		for (int i = 1; i <= sz; i++) {
			try {
				Scanner scan = new Scanner(trashfile);
				File targetfile = (File)mails.get(i);
				String target = targetfile.getName();
				while (scan.hasNextLine()) {
					String line = scan.nextLine();
					String[] arr = line.split(" ");
					if (arr[1].compareTo(target) == 0) {
						scan.close();
						ILinkedList move = new SinglyLinkedList();
						move.add(new File (trash, target));
						IFolder des = new Folder(new File (currentFolder, arr[2]).getAbsolutePath());
						moveEmails(move, des);
						break;
					}
				}
				scan.close();
			} catch (FileNotFoundException e) {
				return false;
			}
		}
		return true;
	}
	
	

}
