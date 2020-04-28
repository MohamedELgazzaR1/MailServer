package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Interfaces.IContact;

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
	
	public static boolean existContact(String AddedAccount) {
		Scanner myreader;
			try {
				int linecounter=0;
				File myobj =new File("D:\\MailServerData\\database.txt");
				myreader = new Scanner(myobj);
				while(myreader.hasNextLine()) {
					String data=myreader.nextLine();
					if(linecounter%2==0) {
						if(AddedAccount.compareTo(data)==0) {
							myreader.close();
							return true;
						}	
					}
					linecounter++;
				}
			} catch (FileNotFoundException e) {
				return false;
			}
			myreader.close();
			return false;
		}
			
	void DeleteContact(String UserAccount,String Name) {
		
	}
	
	//Remove ??????
public static boolean addContact(String UserAccount,String AddedAccount) {
		
		try {
			File myobj =new File("D:\\MailServerData\\"+UserAccount+"\\contacts.txt");
			Scanner myreader= new Scanner(myobj);
			while(myreader.hasNextLine()) {
				String data=myreader.nextLine();
					if(AddedAccount.compareTo(data)==0) {
						myreader.close();
						return false;
					}	
			}
			myreader.close();
			try {
				FileWriter wrt = new FileWriter(myobj, true);
					wrt.write(AddedAccount+"\n");
		
				wrt.close();
			} catch (IOException e) {
				return false;
			}
		} catch (FileNotFoundException e) {
			return false;
		}
		return true;
	}
	
	
	
	

	@Override
	public Boolean newUser(){
		String[] folders = {"Inbox", "Sent", "Draft", "Trash"};
		File mailfolder = new File("D:\\MailServerData", this.email);
		mailfolder.mkdir();
		File allMailFolders = new File(mailfolder , "Mail Folders");
		allMailFolders.mkdir();
		File contactsFolder = new File(mailfolder , "Contacts");
		contactsFolder.mkdir();
		for (String folder : folders) {
			File defFolders = new File(allMailFolders, folder);
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
		File userInfo = new File(mailfolder, "userinfo.txt");
		try {
			
			userInfo.createNewFile();
		} catch (IOException e) {
			return false;
		}
		try {
			FileWriter wrt = new FileWriter(userInfo, true);
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
			File myobj =new File("D:\\MailServerData\\" + email + "\\userinfo.txt");
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
