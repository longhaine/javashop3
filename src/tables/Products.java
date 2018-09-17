package tables;

public class Products {
	private int id;
	private String name;
	private int id_categories;
	private float price;
	private String image;
	private String gender;
	private int id_brands;
	private String name_brands;
	private String name_categories;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getId_categories() {
		return id_categories;
	}
	public float getPrice() {
		return price;
	}
	public String getImage() {
		return image;
	}
	public String getGender() {
		return gender;
	}
	public int getId_brands() {
		return id_brands;
	}
	public String getName_Brands() {
		return name_brands;
	}
	public String getName_Categories() {
		return name_categories;
	}
	public static class builder{
		private int id;
		private String name;
		private int id_categories;
		private float price;
		private String image;
		private String gender;
		private int id_brands;
		private String name_brands;
		private String name_categories;
		public builder(int id, String name, int id_categories, float price, String image, String gender, int id_brands)
		{
			this.id = id;
			this.name = name;
			this.id_categories = id_categories;
			this.price = price;
			this.image = image;
			this.gender = gender;
			this.id_brands = id_brands;
		}
		public builder(int id, String name,float price, String image, String gender)
		{
			this.id = id;
			this.name = name;
			this.price = price;
			this.image = image;
			this.gender = gender;
		}
		public builder setName_Brands(String name_brands) {
			this.name_brands = name_brands;
			return this;
		}
		public builder setName_Categories(String name_categories)
		{
			this.name_categories = name_categories;
			return this;
		}
		public Products add(){
			return new Products(this);
		}
	}
	public Products(builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.id_categories = builder.id_categories;
		this.price = builder.price;
		this.image = builder.image;
		this.gender = builder.gender;
		this.id_brands = builder.id_brands;
		this.name_brands = builder.name_brands;
		this.name_categories = builder.name_categories;
	}
}
