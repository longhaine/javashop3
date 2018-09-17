package tables;

public class Forgot {
	private String email;
	private String pathInfo;
	private String date;
	
	public Forgot(String email, String pathInfo, String date) {
		super();
		this.email = email;
		this.pathInfo = pathInfo;
		this.date = date;
	}
	
	public String getEmail() {
		return email;
	}
	public String getPathInfo() {
		return pathInfo;
	}
	public String getDate() {
		return date;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPathInfo(String pathInfo) {
		this.pathInfo = pathInfo;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
