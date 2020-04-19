package Classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
	public String checkIn(String email, String password) {
		return null;
	}

	@Override
	public Boolean checkpassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean checkemail() {
		// TODO Auto-generated method stub
		return null;
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
			for(String w : folders) {
				wrt.write(w + '\n');
			}
			wrt.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	
	
}
