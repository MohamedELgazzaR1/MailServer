package Classes;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

import javax.swing.JOptionPane;

import InterFaces.IFolder;
import InterFaces.IMail;
import classes.LinkedBased;
import interfaces.ILinkedList;
import interfaces.IQueue;

public class Mail implements IMail{
	final static int lines = 4;
	Boolean Draft;
	int prior;
	IQueue mails;
	String subject;
	ILinkedList files;
	String body;
	String currentmail;
	
	
	
	public static int getlines() {
		return lines;
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
						writetemp.write(input + '\n');
					}
				} else {
					if (arr[1].compareTo(target) != 0) {
						writetemp.write(input + '\n');
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

	@Override
	public Boolean getDraft() {
		// TODO Auto-generated method stub
		return Draft;
	}

	@Override
	public int getPriority() {
		// TODO Auto-generated method stub
		return prior;
	}

	@Override
	public IQueue getMails() {
		// TODO Auto-generated method stub
		return mails;
	}

	@Override
	public String getSubject() {
		// TODO Auto-generated method stub
		return subject;
	}

	@Override
	public ILinkedList getfiles() {
		// TODO Auto-generated method stub
		return files;
	}

	@Override
	public String getBody() {
		// TODO Auto-generated method stub
		return body;
	}

	@Override
	public String getCurrentMail() {
		// TODO Auto-generated method stub
		return currentmail;
	}

	@Override
	public void setDraft(Boolean Draft) {
		// TODO Auto-generated method stub
		this.Draft = Draft;
	}

	@Override
	public void setPriority(int prior) {
		// TODO Auto-generated method stub
		this.prior = prior;
	}

	@Override
	public void setMails(IQueue mails) {
		// TODO Auto-generated method stub
		this.mails = mails;
	}

	@Override
	public void setSubject(String subject) {
		// TODO Auto-generated method stub
		this.subject = subject;
	}

	@Override
	public void setfiles(ILinkedList files) {
		// TODO Auto-generated method stub
		this.files = files;
	}

	@Override
	public void setBody(String body) {
		// TODO Auto-generated method stub
		this.body = body;
	}

	@Override
	public void setCurrentMail(String currentmail) {
		// TODO Auto-generated method stub
		this.currentmail = currentmail;
	}
}
