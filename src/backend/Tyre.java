package backend;

public class Tyre {

	public String product_code;

	public String product_name;

	public int total;

	public int width;
	
	private int alert_value;

	public Tyre left;

	public Tyre right;

	// Constructor
	public Tyre(String product_code_in, String product_name_in, int totalIn, int widthIn, int aler_valueIn) {

		product_code = product_code_in;

		product_name = product_name_in;

		total = totalIn;

		width = widthIn;
		
		setAlert_value(aler_valueIn);

	}

	public int getAlert_value() {
		return alert_value;
	}

	public void setAlert_value(int alert_value) {
		this.alert_value = alert_value;
	}

}
