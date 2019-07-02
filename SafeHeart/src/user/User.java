package user;

public abstract class User {
	protected String id;
	protected String firstName;
	protected String lastName;

	public User(String userId) {
		this.id = userId;
	}
	
	public String getId() {
		return this.id;
	}
}
