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

	
	private static void appendUsingFileWriter(String filePath, String text) {
		File file = new File(filePath);
		FileWriter fr = null;
		try {
			// Below constructor argument decides whether to append or override
			fr = new FileWriter(file, true);
			fr.write(text);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	
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
			if(linecounter==0) {
				appendUsingFileWriter("D:\\MailServerData\\database.txt",contact.getemail()+"\n"+contact.getpassword());
			}else {
			appendUsingFileWriter("D:\\MailServerData\\database.txt","\n"+contact.getemail()+"\n"+contact.getpassword());
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
