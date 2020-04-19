package Classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import InterFaces.IApp;
import InterFaces.IContact;

public class App implements IApp{

	@Override
	public boolean signin(String email, String password) {

		return false;
	}

	@Override
	public boolean signup(IContact contact) {
		
		
		//even emails odd passwords
		int linecounter=0;
		
		try {
			File myobj =new File("D:\\MailServerData\\database.txt");
			Scanner myreader= new Scanner(myobj);
			while(myreader.hasNextLine()) {
				String data=myreader.next();
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
			if (!contact.newcontact()) {
				return false;
			}
		} catch (FileNotFoundException e) {
			return false;
		}
		
		return true;
	}

}
