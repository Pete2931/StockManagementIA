package backend;

public class Account {

	public String username;
	
	public String password;
	
	public String permission;
	
	public Account next;
	
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
