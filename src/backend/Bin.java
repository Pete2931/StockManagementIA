package backend;

public class Bin {
	
	public int shelf;
	
	public int row;
	
	public int bin;
	
	int length;
	
	private String bin_number;
	
	public int length_left;
	
	public Bin next;

	public String getBin_number() {
		return bin_number;
	}

	public void setBin_number(String bin_number) {
		this.bin_number = bin_number;
	}

}
