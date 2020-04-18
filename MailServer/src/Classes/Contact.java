package Classes;

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
	public Boolean addcontact() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
