package main;

public class Country {
	private String code;
	private String name;
	private String capital;
	private String currencyCode;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	@Override
	public String toString() {
		return "Country [code=" + code + ", name=" + name + ", capital=" + capital + ", currencyCode=" + currencyCode
				+ "]";
	}
}
