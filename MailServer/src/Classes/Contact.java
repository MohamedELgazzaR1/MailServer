package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import InterFaces.IContact;

public class Contact implements IContact {

	String email;
	String password;
	String repassword;
	String name;
	
	
	public void setemail(String entered){
		email=entered;
	}
	
	public void setpassword(String entered){
		password=entered;
	}
	
	public void setrepassword(String entered){
		repassword=entered;
	}
	public void setname(String entered){
		name=entered;
	}
	
	
	
	public String getemail(){
		return email;
	}
	
	public String getpassword(){
		return password;
	}
	
	public String getrepassword(){
		return repassword;
	}
	
	public String getname(){
		return name;
	}
	
	

	@Override
	public Boolean newcontact(){
		String[] folders = {"Inbox", "Sent", "Draft", "Trash"};
		File mailfolder = new File("D:\\MailServerData", this.email);
		mailfolder.mkdir();
		for (String folder : folders) {
			File defFolders = new File(mailfolder, folder);
			defFolders.mkdir();
			File mails = new File(defFolders, "mailsfile.txt");
			try {
				mails.createNewFile();
				if (folder.compareTo("Trash") == 0){
					mails = new File(defFolders, "Trashfile.txt");
					mails.createNewFile();
				}
				
			} catch (IOException e) {
				return false;
			}
		}
		File contactinfo = new File(mailfolder, "contactinfo.txt");
		try {
			contactinfo.createNewFile();
		} catch (IOException e) {
			return false;
		}
		try {
			FileWriter wrt = new FileWriter(contactinfo, true);
			wrt.write(this.name + '\n' + this.email + '\n' + this.password + '\n');
			wrt.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public static String[] getData(String email) {
		
		String[] data = new String[3];
		int linecounter=0;
		try {
			File myobj =new File("D:\\MailServerData\\" + email + "\\contactinfo.txt");
			Scanner myreader= new Scanner(myobj);
			while(myreader.hasNextLine()) {
				String datapoint=myreader.nextLine();
				if(linecounter<3) {
					data[linecounter] = datapoint;
				}
				else {
					break;
				}
				linecounter++;
			}
	
			myreader.close();
		} catch (FileNotFoundException e) {
			
		}
		
		return data;
		
	}
	
}
