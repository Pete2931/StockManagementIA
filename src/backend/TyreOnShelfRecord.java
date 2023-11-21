package backend;

public class TyreOnShelfRecord{
	
	public int id;
	
	public String product_code;
	
	private String bin_number;
	
	private int total;
	
	public TyreOnShelfRecord next;

	public String getBin_number() {
		return bin_number;
	}

	public void setBin_number(String bin_number) {
		this.bin_number = bin_number;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}
