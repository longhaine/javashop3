package tables;

public class Orders_Details {
	private int id;
	private int id_orders;
	private int id_products;
	public Orders_Details(int id, int id_orders, int id_products) {
		super();
		this.id = id;
		this.id_orders = id_orders;
		this.id_products = id_products;
	}
	public int getId() {
		return id;
	}
	public int getId_orders() {
		return id_orders;
	}
	public int getId_products() {
		return id_products;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId_orders(int id_orders) {
		this.id_orders = id_orders;
	}
	public void setId_products(int id_products) {
		this.id_products = id_products;
	}
	
}
