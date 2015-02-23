
public class Country {
	private String countryName;
	private String currencyName;
	private String currency;
	
	public Country(String countryName, String currencyName, String currency) {
		this.countryName = countryName;
		this.currencyName = currencyName;
		this.currency = currency;
	}
	
	public String toString() {
		return "<" + countryName + ", " + currencyName + ", " + currency + ">";
	}
}
