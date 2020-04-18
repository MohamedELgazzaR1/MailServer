package InterFaces;


public interface IContact {
	/*
	 * check if the email and password are found in indexfile
	 * 
	 * open indexfile
	 * loop until email and password are found
	 * 	retrun email
	 * return null
	 */
	public String checkIn(String email, String password);
	/*
	 * in registration check that password doesn't have space or the password and repassword are different
	 */
	public Boolean checkpassword();
	/*
	 * in registration check that email doesn't have space or it is already exists
	 */
	public Boolean checkemail();
	/*
	 * add contact after you check that the information is right and create folder to that email on the system
	 * 
	 * open the contacts file
	 * append the mail and the password 
	 * create folder with the email
	 * create contact file that contain :
	 * 					user name 
	 * 					password 
	 * 					email
	 * 					4 main folder names : draft inbox sent trash 
	 * create the 4 folders each has empty emails file
	 * stay at inbox folder  					
	 */
	public Boolean addcontact();
	
	public void setrepassword(String entered);
	
	public void setemail(String entered);
	
	public void setpassword(String entered);
	
	public void setname(String entered);
	
	public String getrepassword();
	
	public String getemail();
	
	public String getpassword();
	
	public String getname();
	
}
