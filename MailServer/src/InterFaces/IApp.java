package InterFaces;

public interface IApp {
	/**
	* Sign in to the application
	* @param email
	* @param password
	* @return false if the email name not exist
	*/
	public boolean signin(String email, String password);
	/**
	* Create new account
	* @param contact
	* @return false if the email name already exist
	*/
	public boolean signup(IContact contact);
	
}
