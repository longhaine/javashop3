package tables;

public class Orders {
	private int id;
	private String email;
	private String date;
	private float price;
	private String name;
	private String address;
	private String phone;
	private String sessionId;
	
	public Orders(int id, String email, String date, float price, String name, String address, String phone,
			String sessionId) {
		this.id = id;
		this.email = email;
		this.date = date;
		this.price = price;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.sessionId = sessionId;
	}
	
	public Orders(int id,String date, float price) {
		this.id = id;
		this.date = date;
		this.price = price;
	}
	
	public int getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	public String getDate() {
		return date;
	}
	public float getPrice() {
		return price;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getPhone() {
		return phone;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
